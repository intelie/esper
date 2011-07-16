/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.window;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.collection.ViewUpdatedCollection;
import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.ExtensionServicesContext;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.variable.VariableChangeCallback;
import com.espertech.esper.epl.variable.VariableReader;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleSlot;
import com.espertech.esper.view.*;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;

/**
 * This view is a moving window extending the into the past until the expression passed to it returns false.
 */
public final class ExpressionWindowView extends ViewSupport implements DataWindowView, CloneableView, StoppableView, VariableChangeCallback {
    public final static String CURRENT_COUNT = "current_count";
    public final static String OLDEST_TIMESTAMP = "oldest_timestamp";
    public final static String NEWEST_TIMESTAMP = "newest_timestamp";
    public final static String EXPIRED_COUNT = "expired_count";
    public final static String VIEW_REFERENCE = "view_reference";

    private final ExpressionWindowViewFactory dataWindowViewFactory;
    private final ViewUpdatedCollection viewUpdatedCollection;
    private final ArrayDeque<TimestampEventPair> window = new ArrayDeque<TimestampEventPair>();
    private final ExprEvaluator expiryExpression;
    private final MapEventBean builtinEventProps;
    private final EventBean[] eventsPerStream;
    private final ExprEvaluatorContext exprEvaluatorContext;
    private final Set<String> variableNames;
    private final StatementContext statementContext;
    private final ScheduleSlot scheduleSlot;
    private final EPStatementHandleCallback scheduleHandle;

    /**
     * Constructor creates a moving window extending the specified number of elements into the past.
     * @param dataWindowViewFactory for copying this view in a group-by
     * @param viewUpdatedCollection is a collection that the view must update when receiving events
     * @param variableNames variable names
     */
    public ExpressionWindowView(ExpressionWindowViewFactory dataWindowViewFactory,
                                ViewUpdatedCollection viewUpdatedCollection,
                                ExprEvaluator expiryExpression,
                                ExprEvaluatorContext exprEvaluatorContext,
                                MapEventBean builtinEventProps,
                                Set<String> variableNames,
                                StatementContext statementContext)
    {
        this.dataWindowViewFactory = dataWindowViewFactory;
        this.viewUpdatedCollection = viewUpdatedCollection;
        this.expiryExpression = expiryExpression;
        this.exprEvaluatorContext = exprEvaluatorContext;
        this.builtinEventProps = builtinEventProps;
        this.eventsPerStream = new EventBean[] {null, builtinEventProps};
        this.variableNames = variableNames;
        this.statementContext = statementContext;

        if (variableNames != null && !variableNames.isEmpty()) {
            for (String variable : variableNames) {
                final VariableService variableService = statementContext.getVariableService();
                final VariableReader reader = variableService.getReader(variable);
                statementContext.getVariableService().registerCallback(reader.getVariableNumber(), this);
                statementContext.getStatementStopService().addSubscriber(new StatementStopCallback() {
                    public void statementStopped()
                    {
                        variableService.unregisterCallback(reader.getVariableNumber(), ExpressionWindowView.this);
                    }
                });
            }

            ScheduleHandleCallback callback = new ScheduleHandleCallback() {
                public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
                {
                    ExpressionWindowView.this.expire(null, null);
                }
            };
            scheduleSlot = statementContext.getScheduleBucket().allocateSlot();
            scheduleHandle = new EPStatementHandleCallback(statementContext.getEpStatementHandle(), callback);
        }
        else {
            scheduleSlot = null;
            scheduleHandle = null;
        }
    }

    public View cloneView(StatementContext statementContext)
    {
        return dataWindowViewFactory.makeView(statementContext);
    }

    /**
     * Returns true if the window is empty, or false if not empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return window.isEmpty();
    }

    /**
     * Returns the (optional) collection handling random access to window contents for prior or previous events.
     * @return buffer for events
     */
    public ViewUpdatedCollection getViewUpdatedCollection()
    {
        return viewUpdatedCollection;
    }

    public final EventType getEventType()
    {
        // The event type is the parent view's event type
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        // add data points to the window
        if (newData != null)
        {
            for (EventBean newEvent : newData) {
                window.add(new TimestampEventPair(exprEvaluatorContext.getTimeProvider().getTime(), newEvent));
            }
        }

        if (oldData != null) {
            Iterator<TimestampEventPair> it = window.iterator();
            for (;it.hasNext();) {
                TimestampEventPair pair = it.next();
                for (EventBean anOldData : oldData) {
                    if (pair.getEvent() == anOldData) {
                        it.remove();
                        break;
                    }
                }
            }
        }

        // expire events
        expire(newData, oldData);
    }

    // Called based on schedule evaluation registered when a variable changes (new data is null).
    // Called when new data arrives.
    private void expire(EventBean[] newData, EventBean[] oldData) {

        OneEventCollection expired = null;
        if (oldData != null) {
            expired = new OneEventCollection();
            expired.add(oldData);
        }
        int expiredCount = 0;
        if (!window.isEmpty()) {
            TimestampEventPair newest = window.getLast();

            while (true) {
                TimestampEventPair first = window.getFirst();

                boolean pass = checkEvent(first, newest, expiredCount);
                if (!pass) {
                    if (expired == null) {
                         expired = new OneEventCollection();
                    }
                    expired.add(window.removeFirst().getEvent());
                    expiredCount++;
                }
                else {
                    break;
                }

                if (window.isEmpty()) {
                    break;
                }
            }
        }

        // Check for any events that get pushed out of the window
        EventBean[] expiredArr = null;
        if (expired != null)
        {
            expiredArr = expired.toArray();
        }

        // update event buffer for access by expressions, if any
        if (viewUpdatedCollection != null)
        {
            viewUpdatedCollection.update(newData, expiredArr);
        }

        // If there are child views, call update method
        if (this.hasViews())
        {
            updateChildren(newData, expiredArr);
        }
    }

    private boolean checkEvent(TimestampEventPair pair, TimestampEventPair newest, int numExpired) {

        builtinEventProps.getProperties().put(CURRENT_COUNT, window.size());
        builtinEventProps.getProperties().put(OLDEST_TIMESTAMP, pair.getTimestamp());
        builtinEventProps.getProperties().put(NEWEST_TIMESTAMP, newest.getTimestamp());
        builtinEventProps.getProperties().put(VIEW_REFERENCE, this);
        builtinEventProps.getProperties().put(EXPIRED_COUNT, numExpired);
        eventsPerStream[0] = pair.getEvent();

        Boolean result = (Boolean) expiryExpression.evaluate(eventsPerStream, true, exprEvaluatorContext);
        if (result == null) {
            return false;
        }
        return result;
    }

    public final Iterator<EventBean> iterator()
    {
        return new TimestampEventPairIterator(window.iterator());
    }

    public final String toString()
    {
        return this.getClass().getName();
    }

    public void stop() {
        if (variableNames != null && !variableNames.isEmpty()) {
            for (String variable : variableNames) {
                VariableReader reader = statementContext.getVariableService().getReader(variable);
                if (reader != null) {
                    statementContext.getVariableService().unregisterCallback(reader.getVariableNumber(), this);
                }
            }
            
            if (statementContext.getSchedulingService().isScheduled(scheduleHandle)) {
                statementContext.getSchedulingService().remove(scheduleHandle, scheduleSlot);
            }
        }
    }

    // Handle variable updates by scheduling a re-evaluation with timers
    public void update(Object newValue, Object oldValue) {
        if (!statementContext.getSchedulingService().isScheduled(scheduleHandle)) {
            statementContext.getSchedulingService().add(0, scheduleHandle, scheduleSlot);
        }
    }

    private static class TimestampEventPair {
        private final long timestamp;
        private final EventBean event;

        private TimestampEventPair(long timestamp, EventBean event) {
            this.timestamp = timestamp;
            this.event = event;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public EventBean getEvent() {
            return event;
        }
    }

    private static class TimestampEventPairIterator implements Iterator<EventBean> {
        private final Iterator<TimestampEventPair> events;

        private TimestampEventPairIterator(Iterator<TimestampEventPair> events) {
            this.events = events;
        }

        public boolean hasNext() {
            return events.hasNext();
        }

        public EventBean next() {
            return events.next().getEvent();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
