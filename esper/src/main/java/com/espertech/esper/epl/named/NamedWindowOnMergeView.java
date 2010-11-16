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
import com.espertech.esper.epl.expression.ExprEvaluator;
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
    private SelectExprProcessor insertHelper;
    private List<NamedWindowOnMergeAction> updateDeleteActions;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param removeStreamView to indicate which events to delete
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     * @param statementContext context for expression evaluation and statement-level services
     * @param onTriggerDesc describes on-update
     * @throws ExprValidationException when expression validation fails
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

        setup(onTriggerDesc, triggeringEventType, statementContext);
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

        // handle update
        EventBean[] eventsPerStream = new EventBean[2];

        OneEventCollection newData = new OneEventCollection();
        OneEventCollection oldData = new OneEventCollection();

        for (EventBean triggerEvent : triggerEvents) {
            eventsPerStream[1] = triggerEvent;
            for (EventBean matchingEvent : matchingEvents) {
                eventsPerStream[0] = matchingEvent;
                for (NamedWindowOnMergeAction action : updateDeleteActions) {
                    if (!action.isApplies(eventsPerStream, super.getExprEvaluatorContext())) {
                        continue;
                    }
                    action.apply(matchingEvent, eventsPerStream, newData, oldData, super.getExprEvaluatorContext());
                    break;  // apply no other actions
                }
            }
        }

        if (!newData.isEmpty() || !oldData.isEmpty())
        {
            // Events to delete are indicated via old data
            this.rootView.update(newData.isEmpty() ? null : newData.toArray(), oldData.isEmpty() ? null : oldData.toArray());

            // The on-delete listeners receive the events deleted, but only if there is interest
            if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic()) {
                updateChildren(newData.isEmpty() ? null : newData.toArray(), oldData.isEmpty() ? null : oldData.toArray());
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


    private void setup(OnTriggerMergeDesc onTriggerDesc, EventType triggeringEventType, StatementContext statementContext)
        throws ExprValidationException {

        updateDeleteActions = new ArrayList<NamedWindowOnMergeAction>();

        for (OnTriggerMergeItem item : onTriggerDesc.getItems()) {
            if (item instanceof OnTriggerMergeItemInsert) {
                OnTriggerMergeItemInsert insertDesc = (OnTriggerMergeItemInsert) item;
                setupInsert(insertDesc, triggeringEventType, statementContext);
            }
            else if (item instanceof OnTriggerMergeItemUpdate) {
                OnTriggerMergeItemUpdate updateDesc = (OnTriggerMergeItemUpdate) item;
                NamedWindowUpdateHelper updateHelper = NamedWindowUpdateHelper.make(eventTypeSPI, updateDesc.getAssignments());
                ExprEvaluator filterEval = updateDesc.getOptionalMatchCond() == null ? null : updateDesc.getOptionalMatchCond().getExprEvaluator();
                updateDeleteActions.add(new NamedWindowOnMergeActionUpd(updateHelper, filterEval));
            }
            else if (item instanceof OnTriggerMergeItemDelete) {
                OnTriggerMergeItemDelete deleteDesc = (OnTriggerMergeItemDelete) item;
                ExprEvaluator filterEval = deleteDesc.getOptionalMatchCond() == null ? null : deleteDesc.getOptionalMatchCond().getExprEvaluator();
                updateDeleteActions.add(new NamedWindowOnMergeActionDel(filterEval));
            }
            else {
                throw new IllegalArgumentException("Invalid type of merge item '" + item.getClass() + "'");
            }
        }
    }

    private void setupInsert(OnTriggerMergeItemInsert onTriggerInsertDesc, EventType triggeringEventType, StatementContext statementContext)
        throws ExprValidationException {

        List<SelectClauseElementCompiled> selectClause = onTriggerInsertDesc.getSelectClauseCompiled();
        InsertIntoDesc desc = new InsertIntoDesc(true, eventTypeSPI.getName());
        for (String col : onTriggerInsertDesc.getColumns()) {
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
        SelectExprEventTypeRegistry selectExprEventTypeRegistry = new SelectExprEventTypeRegistry(new HashSet<String>());
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {triggeringEventType}, new String[] {null}, new boolean[1], statementContext.getEngineURI(), false);
        insertHelper = SelectExprProcessorFactory.getProcessor(selectClause, isUsingWildcard, desc, null, streamTypeService,
                statementContext.getEventAdapterService(), statementResultService, statementContext.getValueAddEventService(), selectExprEventTypeRegistry,
                statementContext.getMethodResolutionService(), statementContext, statementContext.getVariableService(), statementContext.getTimeProvider(), statementContext.getEngineURI());
    }    
}