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
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.core.StatementResultService;
import com.espertech.esper.epl.core.*;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.metric.MetricReportingPath;
import com.espertech.esper.epl.metric.MetricReportingService;
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
    private List<NamedWindowOnMergeMatch> matched;
    private List<NamedWindowOnMergeMatch> unmatched;
    private EPStatementHandle createWindowStatementHandle;
    private MetricReportingService metricReportingService;
    private InternalEventRouter internalEventRouter;
    private String namedWindowName;

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
                                 NamedWindowLookupStrategy lookupStrategy,
                                 NamedWindowRootView removeStreamView,
                                 StatementResultService statementResultService,
                                 StatementContext statementContext,
                                 OnTriggerMergeDesc onTriggerDesc,
                                 EventType triggeringEventType,
                                 EPStatementHandle createWindowStatementHandle,
                                 MetricReportingService metricReportingService,
                                 InternalEventRouter internalEventRouter,
                                 String namedWindowName)
            throws ExprValidationException
    {
        super(statementStopService, lookupStrategy, removeStreamView, statementContext);
        this.statementResultService = statementResultService;
        this.createWindowStatementHandle = createWindowStatementHandle;
        this.metricReportingService = metricReportingService;
        this.internalEventRouter = internalEventRouter;
        this.namedWindowName = namedWindowName;
        eventTypeSPI = (EventTypeSPI) removeStreamView.getEventType();

        setup(onTriggerDesc, triggeringEventType, statementContext);
    }

    public void handleMatching(EventBean[] triggerEvents, EventBean[] matchingEvents)
    {
        OneEventCollection newData = new OneEventCollection();
        OneEventCollection oldData = null;
        EventBean[] eventsPerStream = new EventBean[2];

        if ((matchingEvents == null) || (matchingEvents.length == 0)){
            for (EventBean triggerEvent : triggerEvents) {
                eventsPerStream[0] = triggerEvent;
                for (NamedWindowOnMergeMatch action : unmatched) {
                    if (!action.isApplies(eventsPerStream, super.getExprEvaluatorContext())) {
                        continue;
                    }
                    action.apply(null, eventsPerStream, newData, oldData, super.getExprEvaluatorContext());
                    break;  // apply no other actions
                }
            }
        }
        else {

            // handle update/
            oldData = new OneEventCollection();

            for (EventBean triggerEvent : triggerEvents) {
                eventsPerStream[1] = triggerEvent;
                for (EventBean matchingEvent : matchingEvents) {
                    eventsPerStream[0] = matchingEvent;
                    for (NamedWindowOnMergeMatch action : matched) {
                        if (!action.isApplies(eventsPerStream, super.getExprEvaluatorContext())) {
                            continue;
                        }
                        action.apply(matchingEvent, eventsPerStream, newData, oldData, super.getExprEvaluatorContext());
                        break;  // apply no other actions
                    }
                }
            }
        }

        if (!newData.isEmpty() || (oldData != null && !oldData.isEmpty()))
        {
            if ((MetricReportingPath.isMetricsEnabled) && (createWindowStatementHandle.getMetricsHandle().isEnabled()) && !newData.isEmpty())
            {
                metricReportingService.accountTime(createWindowStatementHandle.getMetricsHandle(), 0, 0, newData.toArray().length);
            }

            // Events to delete are indicated via old data
            this.rootView.update(newData.isEmpty() ? null : newData.toArray(), (oldData == null || oldData.isEmpty()) ? null : oldData.toArray());

            // The on-delete listeners receive the events deleted, but only if there is interest
            if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic()) {
                updateChildren(newData.isEmpty() ? null : newData.toArray(), (oldData == null || oldData.isEmpty()) ? null : oldData.toArray());
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

        matched = new ArrayList<NamedWindowOnMergeMatch>();
        unmatched = new ArrayList<NamedWindowOnMergeMatch>();

        int count = 1;
        for (OnTriggerMergeMatched matchedItem : onTriggerDesc.getItems()) {
            List<NamedWindowOnMergeAction> actions = new ArrayList<NamedWindowOnMergeAction>();
            for (OnTriggerMergeAction item : matchedItem.getActions()) {
                try {
                    if (item instanceof OnTriggerMergeActionInsert) {
                        OnTriggerMergeActionInsert insertDesc = (OnTriggerMergeActionInsert) item;
                        actions.add(setupInsert(insertDesc, triggeringEventType, statementContext));
                    }
                    else if (item instanceof OnTriggerMergeActionUpdate) {
                        OnTriggerMergeActionUpdate updateDesc = (OnTriggerMergeActionUpdate) item;
                        NamedWindowUpdateHelper updateHelper = NamedWindowUpdateHelper.make(eventTypeSPI, updateDesc.getAssignments(), onTriggerDesc.getOptionalAsName());
                        ExprEvaluator filterEval = updateDesc.getOptionalWhereClause() == null ? null : updateDesc.getOptionalWhereClause().getExprEvaluator();
                        actions.add(new NamedWindowOnMergeActionUpd(filterEval, updateHelper));
                    }
                    else if (item instanceof OnTriggerMergeActionDelete) {
                        OnTriggerMergeActionDelete deleteDesc = (OnTriggerMergeActionDelete) item;
                        ExprEvaluator filterEval = deleteDesc.getOptionalWhereClause() == null ? null : deleteDesc.getOptionalWhereClause().getExprEvaluator();
                        actions.add(new NamedWindowOnMergeActionDel(filterEval));
                    }
                    else {
                        throw new IllegalArgumentException("Invalid type of merge item '" + item.getClass() + "'");
                    }
                    count++;
                }
                catch (ExprValidationException ex) {
                    boolean isNot = item instanceof OnTriggerMergeActionInsert;
                    String message = "Exception encountered in when-" + (isNot?"not-":"") + "matched (clause " + count + "): " + ex.getMessage();
                    throw new ExprValidationException(message, ex);
                }
            }

            if (matchedItem.isMatchedUnmatched()) {
                matched.add(new NamedWindowOnMergeMatch(matchedItem.getOptionalMatchCond(), actions));
            }
            else {
                unmatched.add(new NamedWindowOnMergeMatch(matchedItem.getOptionalMatchCond(), actions));
            }
        }
    }

    private NamedWindowOnMergeActionIns setupInsert(OnTriggerMergeActionInsert desc, EventType triggeringEventType, StatementContext statementContext)
        throws ExprValidationException {

        // Compile insert-into info
        String streamName = desc.getOptionalStreamName() != null ? desc.getOptionalStreamName() : eventTypeSPI.getName();
        List<SelectClauseElementCompiled> selectClause = desc.getSelectClauseCompiled();
        InsertIntoDesc insertIntoDesc = new InsertIntoDesc(true, streamName);
        for (String col : desc.getColumns()) {
            insertIntoDesc.add(col);
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
        SelectExprProcessor insertHelper = SelectExprProcessorFactory.getProcessor(selectClause, isUsingWildcard, insertIntoDesc, null, streamTypeService,
                statementContext.getEventAdapterService(), statementResultService, statementContext.getValueAddEventService(), selectExprEventTypeRegistry,
                statementContext.getMethodResolutionService(), statementContext, statementContext.getVariableService(), statementContext.getTimeProvider(), statementContext.getEngineURI(), statementContext.getStatementId());
        ExprEvaluator filterEval = desc.getOptionalWhereClause() == null ? null : desc.getOptionalWhereClause().getExprEvaluator();

        InternalEventRouter routerToUser = streamName.equals(namedWindowName) ? null : internalEventRouter;
        return new NamedWindowOnMergeActionIns(filterEval, insertHelper, routerToUser, statementContext.getEpStatementHandle(), statementContext.getInternalEventEngineRouteDest());
    }    
}