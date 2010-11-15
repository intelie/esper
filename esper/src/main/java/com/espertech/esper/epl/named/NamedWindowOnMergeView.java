/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.ArrayEventIterator;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.core.StatementResultService;
import com.espertech.esper.epl.core.*;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.view.StatementStopService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * View for the on-delete statement that handles removing events from a named window.
 */
public class NamedWindowOnMergeView extends NamedWindowOnExprBaseView
{
    private static final Log log = LogFactory.getLog(NamedWindowOnMergeView.class);
    private EventBean[] lastResult;
    private final StatementResultService statementResultService;
    private final EventTypeSPI eventTypeSPI;
    private final NamedWindowUpdateHelper updateHelper;
    private final SelectExprProcessor insertHelper;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param removeStreamView to indicate which events to delete
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     * @param statementContext context for expression evaluation and statement-level services
     * @param onTriggerDesc describes on-update
     * @throws com.espertech.esper.epl.expression.ExprValidationException when expression validation fails
     */
    public NamedWindowOnMergeView(StatementStopService statementStopService,
                                 LookupStrategy lookupStrategy,
                                 NamedWindowRootView removeStreamView,
                                 StatementResultService statementResultService,
                                 StatementContext statementContext,
                                 OnTriggerMergeDesc onTriggerDesc,
                                 EventType triggeringEventType)
            throws ExprValidationException
    {
        super(statementStopService, lookupStrategy, removeStreamView, statementContext);
        this.statementResultService = statementResultService;
        eventTypeSPI = (EventTypeSPI) removeStreamView.getEventType();
        if (onTriggerDesc.getUpdate() != null) {
            updateHelper = NamedWindowUpdateHelper.make(eventTypeSPI, onTriggerDesc.getUpdate().getAssignments());
        }
        else {
            updateHelper = null;
        }
        if (onTriggerDesc.getInsert() != null) {

            List<SelectClauseElementCompiled> selectClause = onTriggerDesc.getInsert().getSelectClauseCompiled();
            InsertIntoDesc desc = new InsertIntoDesc(true, eventTypeSPI.getName());
            for (String col : onTriggerDesc.getInsert().getColumns()) {
                desc.add(col);
            }
            boolean isUsingWildcard = false;    // Refactor me
            for (SelectClauseElementCompiled element : selectClause)
            {
                if (element instanceof SelectClauseElementWildcard)
                {
                    isUsingWildcard = true;
                }
            }
            // TODO event type registry
            // TODO triggering event type alias
            SelectExprEventTypeRegistry selectExprEventTypeRegistry = new SelectExprEventTypeRegistry(new HashSet<String>());
            StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {triggeringEventType}, new String[] {null}, new boolean[1], statementContext.getEngineURI(), false);
            insertHelper = SelectExprProcessorFactory.getProcessor(selectClause, isUsingWildcard, desc, null, streamTypeService,
                    statementContext.getEventAdapterService(), statementResultService, statementContext.getValueAddEventService(), selectExprEventTypeRegistry,
                    statementContext.getMethodResolutionService(), statementContext, statementContext.getVariableService(), statementContext.getTimeProvider(), statementContext.getEngineURI());
        }
        else {
            insertHelper = null;
        }
    }

    public void handleMatching(EventBean[] triggerEvents, EventBean[] matchingEvents)
    {
        if ((matchingEvents == null) || (matchingEvents.length == 0)){
            if (insertHelper != null) {
                EventBean event = insertHelper.process(triggerEvents, true, false);
                EventBean[] newData = new EventBean[] {event};

                // Events to delete are indicated via old data
                this.rootView.update(newData, null);

                // The on-delete listeners receive the events deleted, but only if there is interest
                if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic()) {
                    updateChildren(newData, null);
                }

                lastResult = newData;
            }
            return;
        }

        if (updateHelper == null) {
            return;
        }

        // handle update
        EventBean[] eventsPerStream = new EventBean[2];

        OneEventCollection newData = new OneEventCollection();
        OneEventCollection oldData = new OneEventCollection();

        for (EventBean triggerEvent : triggerEvents) {
            eventsPerStream[1] = triggerEvent;
            for (EventBean matchingEvent : matchingEvents) {
                EventBean copy = updateHelper.update(matchingEvent, eventsPerStream, super.getExprEvaluatorContext());
                newData.add(copy);
                oldData.add(matchingEvent);
            }
        }

        if (!newData.isEmpty())
        {
            // Events to delete are indicated via old data
            this.rootView.update(newData.toArray(), oldData.toArray());

            // The on-delete listeners receive the events deleted, but only if there is interest
            if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic()) {
                updateChildren(newData.toArray(), oldData.toArray());
            }
        }

        // Keep the last delete records
        lastResult = matchingEvents;
    }

    public EventType getEventType()
    {
        return namedWindowEventType;
    }

    public Iterator<EventBean> iterator()
    {
        return new ArrayEventIterator(lastResult);
    }
}