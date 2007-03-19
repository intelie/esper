/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.observer;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.schedule.ScheduleSpec;
import net.esper.schedule.ScheduleSlot;
import net.esper.core.EPStatementHandleCallback;
import net.esper.core.ExtensionServicesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Observer implementation for indicating that a certain time arrived, similar to "crontab".
 */
public class TimerAtObserver implements EventObserver, ScheduleHandleCallback
{
    private final ScheduleSpec scheduleSpec;
    private final PatternContext context;
    private final ScheduleSlot scheduleSlot;
    private final MatchedEventMap beginState;
    private final ObserverEventEvaluator observerEventEvaluator;

    private boolean isTimerActive = false;
    private EPStatementHandleCallback scheduleHandle;

    /**
     * Ctor.
     * @param scheduleSpec - specification containing the crontab schedule
     * @param context - timer serive to use
     * @param beginState - start state
     * @param observerEventEvaluator - receiver for events
     */
    public TimerAtObserver(ScheduleSpec scheduleSpec, PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
    {
        this.scheduleSpec = scheduleSpec;
        this.context = context;
        this.beginState = beginState;
        this.observerEventEvaluator = observerEventEvaluator;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();
    }

    public final void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".scheduledTrigger");
        }

        observerEventEvaluator.observerEvaluateTrue(beginState);
        isTimerActive = false;
    }

    public void startObserve()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".startObserve Starting at, spec=" + scheduleSpec);
        }

        if (isTimerActive == true)
        {
            throw new IllegalStateException("Timer already active");
        }

        scheduleHandle = new EPStatementHandleCallback(context.getEpStatementHandle(), this);
        context.getSchedulingService().add(scheduleSpec, scheduleHandle, scheduleSlot);
        isTimerActive = true;
    }

    public void stopObserve()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".stopObserve");
        }

        if (isTimerActive)
        {
            context.getSchedulingService().remove(scheduleHandle, scheduleSlot);
            isTimerActive = false;
            scheduleHandle = null;
        }
    }

    private static final Log log = LogFactory.getLog(TimerAtObserver.class);
}
