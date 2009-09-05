/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.agg.AggregationService;
import com.espertech.esper.epl.agg.AggregationServiceFactory;
import com.espertech.esper.epl.core.*;
import com.espertech.esper.epl.db.DatabasePollingViewableFactory;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.join.*;
import com.espertech.esper.epl.join.plan.FilterExprAnalyzer;
import com.espertech.esper.epl.join.plan.QueryGraph;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndTableCoerceAdd;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTable;
import com.espertech.esper.epl.lookup.*;
import com.espertech.esper.epl.named.*;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.epl.subquery.SubqueryStopCallback;
import com.espertech.esper.epl.subquery.SubselectAggregatorView;
import com.espertech.esper.epl.subquery.SubselectBufferObserver;
import com.espertech.esper.epl.variable.CreateVariableView;
import com.espertech.esper.epl.variable.OnSetVariableView;
import com.espertech.esper.epl.variable.VariableDeclarationException;
import com.espertech.esper.epl.variable.VariableExistsException;
import com.espertech.esper.epl.view.FilterExprView;
import com.espertech.esper.epl.view.OutputConditionExpression;
import com.espertech.esper.epl.view.OutputProcessView;
import com.espertech.esper.epl.view.OutputProcessViewFactory;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.pattern.EvalRootNode;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.PatternMatchCallback;
import com.espertech.esper.pattern.PatternStopCallback;
import com.espertech.esper.rowregex.EventRowRegexNFAViewFactory;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.ManagedLock;
import com.espertech.esper.util.StopCallback;
import com.espertech.esper.view.*;
import com.espertech.esper.view.internal.BufferView;
import com.espertech.esper.view.internal.RouteResultView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.lang.annotation.Annotation;

/**
 * Starts and provides the stop method for EPL statements.
 */
public class EPStatementStartMethod
{
    private static final Log log = LogFactory.getLog(EPStatementStartMethod.class);

    private final StatementSpecCompiled statementSpec;
    private final EPServicesContext services;
    private final StatementContext statementContext;

    /**
     * Ctor.
     * @param statementSpec is a container for the definition of all statement constructs that
     * may have been used in the statement, i.e. if defines the select clauses, insert into, outer joins etc.
     * @param services is the service instances for dependency injection
     * @param statementContext is statement-level information and statement services
     */
    public EPStatementStartMethod(StatementSpecCompiled statementSpec,
                                EPServicesContext services,
                                StatementContext statementContext)
    {
        this.statementSpec = statementSpec;
        this.services = services;
        this.statementContext = statementContext;
    }

    /**
     * Starts the EPL statement.
     * @return a viewable to attach to for listening to events, and a stop method to invoke to clean up
     * @param isNewStatement indicator whether the statement is new or a stop-restart statement
     * @param isRecoveringStatement true to indicate the statement is in the process of being recovered
     * @param isRecoveringResilient true to indicate the statement is in the process of being recovered and that statement is resilient
     * @throws ExprValidationException when the expression validation fails
     * @throws ViewProcessingException when views cannot be started
     */
    public Pair<Viewable, EPStatementStopMethod> start(boolean isNewStatement, boolean isRecoveringStatement, boolean isRecoveringResilient)
        throws ExprValidationException, ViewProcessingException
    {
        statementContext.getVariableService().setLocalVersion();    // get current version of variables

        if (statementSpec.getUpdateSpec() != null)
        {
            return startUpdate();
        }
        if (statementSpec.getOnTriggerDesc() != null)
        {
            return startOnTrigger();
        }
        else if (statementSpec.getCreateWindowDesc() != null)
        {
            return startCreateWindow(isNewStatement, isRecoveringStatement);
        }
        else if (statementSpec.getCreateVariableDesc() != null)
        {
            return startCreateVariable(isNewStatement);
        }
        else
        {
            return startSelect(isRecoveringResilient);
        }
    }

    private Pair<Viewable, EPStatementStopMethod> startOnTrigger()
        throws ExprValidationException, ViewProcessingException
    {
        final List<StopCallback> stopCallbacks = new LinkedList<StopCallback>();

        // Create streams
        Viewable eventStreamParentViewable;
        final StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(0);
        String triggereventTypeName = null;

        if (streamSpec instanceof FilterStreamSpecCompiled)
        {
            FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
            triggereventTypeName = filterStreamSpec.getFilterSpec().getFilterForEventTypeName();

            // Since only for non-joins we get the existing stream's lock and try to reuse it's views
            Pair<EventStream, ManagedLock> streamLockPair = services.getStreamService().createStream(statementContext.getStatementId(), filterStreamSpec.getFilterSpec(),
                    statementContext.getFilterService(), statementContext.getEpStatementHandle(), false, false, statementContext, true);
            eventStreamParentViewable = streamLockPair.getFirst();

            // Use the re-used stream's lock for all this statement's locking needs
            if (streamLockPair.getSecond() != null)
            {
                statementContext.getEpStatementHandle().setStatementLock(streamLockPair.getSecond());
            }
        }
        else if (streamSpec instanceof PatternStreamSpecCompiled)
        {
            PatternStreamSpecCompiled patternStreamSpec = (PatternStreamSpecCompiled) streamSpec;
            boolean usedByChildViews = !streamSpec.getViewSpecs().isEmpty() || (statementSpec.getInsertIntoDesc() != null);
            final EventType eventType = services.getEventAdapterService().createSemiAnonymousMapType(patternStreamSpec.getTaggedEventTypes(), patternStreamSpec.getArrayEventTypes(), usedByChildViews);
            final EventStream sourceEventStream = new ZeroDepthStream(eventType);
            eventStreamParentViewable = sourceEventStream;

            EvalRootNode rootNode = new EvalRootNode();
            rootNode.addChildNode(patternStreamSpec.getEvalNode());

            PatternMatchCallback callback = new PatternMatchCallback() {
                public void matchFound(Map<String, Object> matchEvent)
                {
                    EventBean compositeEvent = statementContext.getEventAdapterService().adaptorForTypedMap(matchEvent, eventType);
                    sourceEventStream.insert(compositeEvent);
                }
            };

            PatternContext patternContext = statementContext.getPatternContextFactory().createContext(statementContext, 0, rootNode, !patternStreamSpec.getArrayEventTypes().isEmpty());
            PatternStopCallback patternStopCallback = rootNode.start(callback, patternContext);
            stopCallbacks.add(patternStopCallback);
        }
        else if (streamSpec instanceof NamedWindowConsumerStreamSpec)
        {
            NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) streamSpec;
            NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
            eventStreamParentViewable = processor.addConsumer(namedSpec.getFilterExpressions(), statementContext.getEpStatementHandle(), statementContext.getStatementStopService());
            triggereventTypeName = namedSpec.getWindowName();
        }
        else
        {
            throw new ExprValidationException("Unknown stream specification type: " + streamSpec);
        }

        View onExprView;
        final EventType streamEventType = eventStreamParentViewable.getEventType();

        ResultSetProcessor resultSetProcessor;
        // For on-delete and on-select triggers
        if (statementSpec.getOnTriggerDesc() instanceof OnTriggerWindowDesc)
        {
            // Determine event types
            OnTriggerWindowDesc onTriggerDesc = (OnTriggerWindowDesc) statementSpec.getOnTriggerDesc();
            NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(onTriggerDesc.getWindowName());
            EventType namedWindowType = processor.getNamedWindowType();
            statementContext.getDynamicReferenceEventTypes().add(onTriggerDesc.getWindowName());

            String namedWindowName = onTriggerDesc.getOptionalAsName();
            if (namedWindowName == null)
            {
                namedWindowName = "stream_0";
            }
            String streamName = streamSpec.getOptionalStreamName();
            if (streamName == null)
            {
                streamName = "stream_1";
            }
            String namedWindowTypeName = onTriggerDesc.getWindowName();

            StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[] {namedWindowType, streamEventType}, new String[] {namedWindowName, streamName}, services.getEngineURI());

            // validate join expression
            ExprNode validatedJoin = validateJoinNamedWindow(statementSpec.getFilterRootNode(),
                    namedWindowType, namedWindowName, namedWindowTypeName,
                    streamEventType, streamName, triggereventTypeName);

            // validate filter, output rate limiting
            validateNodes(statementSpec, statementContext, typeService, null, true);

            // Construct a processor for results; for use in on-select to process selection results
            // Use a wildcard select if the select-clause is empty, such as for on-delete.
            // For on-select the select clause is not empty.
            if (statementSpec.getSelectClauseSpec().getSelectExprList().size() == 0)
            {
                statementSpec.getSelectClauseSpec().add(new SelectClauseElementWildcard());
            }
            resultSetProcessor = ResultSetProcessorFactory.getProcessor(
                    statementSpec, statementContext, typeService, null, new boolean[0], true);

            InternalEventRouter routerService = (statementSpec.getInsertIntoDesc() == null)?  null : services.getInternalEventRouter();
            onExprView = processor.addOnExpr(onTriggerDesc, validatedJoin, streamEventType, statementContext.getStatementStopService(), routerService, resultSetProcessor, statementContext.getEpStatementHandle(), statementContext.getStatementResultService(), statementContext, statementSpec.getSelectClauseSpec().isDistinct());
            eventStreamParentViewable.addView(onExprView);
        }
        // variable assignments
        else if (statementSpec.getOnTriggerDesc() instanceof OnTriggerSetDesc)
        {
            OnTriggerSetDesc desc = (OnTriggerSetDesc) statementSpec.getOnTriggerDesc();
            StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[] {streamEventType}, new String[] {streamSpec.getOptionalStreamName()}, services.getEngineURI());

            for (OnTriggerSetAssignment assignment : desc.getAssignments())
            {
                ExprNode validated = assignment.getExpression().getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
                assignment.setExpression(validated);
            }

            onExprView = new OnSetVariableView(desc, statementContext.getEventAdapterService(), statementContext.getVariableService(), statementContext.getStatementResultService(), statementContext);
            eventStreamParentViewable.addView(onExprView);
        }
        // split-stream use case
        else 
        {
            OnTriggerSplitStreamDesc desc = (OnTriggerSplitStreamDesc) statementSpec.getOnTriggerDesc();
            String streamName = streamSpec.getOptionalStreamName();
            if (streamName == null)
            {
                streamName = "stream_0";
            }
            StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[] {streamEventType}, new String[] {streamName}, services.getEngineURI());
            if (statementSpec.getInsertIntoDesc() == null)
            {
                throw new ExprValidationException("Required insert-into clause is not provided, the clause is required for split-stream syntax");
            }
            if ((!statementSpec.getGroupByExpressions().isEmpty()) || (statementSpec.getHavingExprRootNode() != null) || (!statementSpec.getOrderByList().isEmpty()))
            {
                throw new ExprValidationException("A group-by clause, having-clause or order-by clause is not allowed for the split stream syntax");
            }
            if (statementSpec.getSubSelectExpressions().size() > 0)
            {
                throw new ExprValidationException("Subqueries are not a supported feature of split stream syntax");
            }

            validateNodes(statementSpec, statementContext, typeService, null, true);

            ResultSetProcessor[] processors = new ResultSetProcessor[desc.getSplitStreams().size() + 1];
            ExprNode[] whereClauses = new ExprNode[desc.getSplitStreams().size() + 1];
            processors[0] = ResultSetProcessorFactory.getProcessor(
                    statementSpec, statementContext, typeService, null, new boolean[0], false);
            whereClauses[0] = statementSpec.getFilterRootNode();

            int index = 1;
            for (OnTriggerSplitStream splits : desc.getSplitStreams())
            {
                StatementSpecCompiled splitSpec = new StatementSpecCompiled();
                splitSpec.setInsertIntoDesc(splits.getInsertInto());
                splitSpec.setSelectClauseSpec(StatementLifecycleSvcImpl.compileSelectNoSubselect(splits.getSelectClause()));
                splitSpec.setFilterExprRootNode(splits.getWhereClause());
                validateNodes(splitSpec, statementContext, typeService, null, false);

                processors[index] = ResultSetProcessorFactory.getProcessor(
                    splitSpec, statementContext, typeService, null, new boolean[0], false);
                whereClauses[index] = splitSpec.getFilterRootNode();

                index++;
            }

            onExprView = new RouteResultView(desc.isFirst(), streamEventType, statementContext.getEpStatementHandle(), services.getInternalEventRouter(), processors, whereClauses, statementContext);
            eventStreamParentViewable.addView(onExprView);
        }

        // create stop method using statement stream specs
        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                statementContext.getStatementStopService().fireStatementStopped();

                if (streamSpec instanceof FilterStreamSpecCompiled)
                {
                    FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
                    services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), statementContext.getFilterService(), false, false, true);
                }
                for (StopCallback stopCallback : stopCallbacks)
                {
                    stopCallback.stop();
                }
            }
        };

        // For on-delete, create an output processor that passes on as a wildcard the underlying event
        if ((statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_DELETE) ||
            (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_SET))
        {
            StatementSpecCompiled defaultSelectAllSpec = new StatementSpecCompiled();
            defaultSelectAllSpec.getSelectClauseSpec().add(new SelectClauseElementWildcard());

            StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {onExprView.getEventType()}, new String[] {"trigger_stream"}, services.getEngineURI());
            ResultSetProcessor outputResultSetProcessor = ResultSetProcessorFactory.getProcessor(
                    defaultSelectAllSpec, statementContext, streamTypeService, null, new boolean[0], true);

            // Attach output view
            OutputProcessView outputView = OutputProcessViewFactory.makeView(outputResultSetProcessor, defaultSelectAllSpec, statementContext, services.getInternalEventRouter());
            onExprView.addView(outputView);
            onExprView = outputView;
        }

        log.debug(".start Statement start completed");

        return new Pair<Viewable, EPStatementStopMethod>(onExprView, stopMethod);
    }

    private Pair<Viewable, EPStatementStopMethod> startUpdate()
        throws ExprValidationException, ViewProcessingException
    {
        final List<StopCallback> stopCallbacks = new LinkedList<StopCallback>();

        // First we create streams for subselects, if there are any
        SubSelectStreamCollection subSelectStreamDesc = createSubSelectStreams(false);

        final StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(0);
        final UpdateDesc desc = statementSpec.getUpdateSpec();
        String triggereventTypeName;

        if (streamSpec instanceof FilterStreamSpecCompiled)
        {
            FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
            triggereventTypeName = filterStreamSpec.getFilterSpec().getFilterForEventTypeName();
        }
        else if (streamSpec instanceof NamedWindowConsumerStreamSpec)
        {
            NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) streamSpec;
            triggereventTypeName = namedSpec.getWindowName();
        }
        else
        {
            throw new ExprValidationException("Unknown stream specification streamEventType: " + streamSpec);
        }

        // determine a stream name
        String streamName = triggereventTypeName;
        if (desc.getOptionalStreamName() != null)
        {
            streamName = desc.getOptionalStreamName();
        }

        final EventType streamEventType = services.getEventAdapterService().getExistsTypeByName(triggereventTypeName);
        StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[] {streamEventType}, new String[] {streamName}, services.getEngineURI());

        // determine subscriber result types
        statementContext.getStatementResultService().setSelectClause(new Class[] {streamEventType.getUnderlyingType()}, new String[] {"*"});

        // Materialize sub-select views
        startSubSelect(subSelectStreamDesc, new String[]{streamName}, new EventType[] {streamEventType}, new String[]{triggereventTypeName}, stopCallbacks, statementSpec.getAnnotations());

        for (OnTriggerSetAssignment assignment : desc.getAssignments())
        {
            ExprNode validated = assignment.getExpression().getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
            assignment.setExpression(validated);
            validateNoAggregations(validated, "Aggregation functions may not be used within an update-clause");
        }
        if (desc.getOptionalWhereClause() != null)
        {
            ExprNode validated = desc.getOptionalWhereClause().getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
            desc.setOptionalWhereClause(validated);
            validateNoAggregations(validated, "Aggregation functions may not be used within an update-clause");
        }

        InternalRoutePreprocessView onExprView = new InternalRoutePreprocessView(streamEventType, statementContext.getStatementResultService());
        services.getInternalEventRouter().addPreprocessing(streamEventType, desc, statementSpec.getAnnotations(), onExprView);
        stopCallbacks.add(new StopCallback()
        {
            public void stop()
            {
                services.getInternalEventRouter().removePreprocessing(streamEventType, desc);
            }
        });

        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                statementContext.getStatementStopService().fireStatementStopped();

                for (StopCallback stopCallback : stopCallbacks)
                {
                    stopCallback.stop();
                }
            }
        };
        return new Pair<Viewable, EPStatementStopMethod>(onExprView, stopMethod);
    }

    private Pair<Viewable, EPStatementStopMethod> startCreateWindow(boolean isNewStatement, boolean isRecoveringStatement)
        throws ExprValidationException, ViewProcessingException
    {
        final FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) statementSpec.getStreamSpecs().get(0);
        String windowName = statementSpec.getCreateWindowDesc().getWindowName();
        EventType windowType = filterStreamSpec.getFilterSpec().getFilterForEventType();

        ValueAddEventProcessor optionalRevisionProcessor = statementContext.getValueAddEventService().getValueAddProcessor(windowName);
        boolean isPrioritized = services.getEngineSettingsService().getEngineSettings().getExecution().isPrioritized();
        services.getNamedWindowService().addProcessor(windowName, windowType, statementContext.getEpStatementHandle(), statementContext.getStatementResultService(), optionalRevisionProcessor, statementContext.getExpression(), statementContext.getStatementName(), isPrioritized, statementContext);

        // Create streams and views
        Viewable eventStreamParentViewable;
        ViewFactoryChain unmaterializedViewChain;

        // Create view factories and parent view based on a filter specification
        // Since only for non-joins we get the existing stream's lock and try to reuse it's views
        Pair<EventStream, ManagedLock> streamLockPair = services.getStreamService().createStream(statementContext.getStatementId(), filterStreamSpec.getFilterSpec(),
                statementContext.getFilterService(), statementContext.getEpStatementHandle(), false, false, statementContext, true);
        eventStreamParentViewable = streamLockPair.getFirst();

        // Use the re-used stream's lock for all this statement's locking needs
        if (streamLockPair.getSecond() != null)
        {
            statementContext.getEpStatementHandle().setStatementLock(streamLockPair.getSecond());
        }

        // Create data window view factories
        unmaterializedViewChain = services.getViewService().createFactories(0, eventStreamParentViewable.getEventType(), filterStreamSpec.getViewSpecs(), filterStreamSpec.getOptions(), statementContext);

        // The root view of the named window
        NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(statementSpec.getCreateWindowDesc().getWindowName());
        View rootView = processor.getRootView();
        eventStreamParentViewable.addView(rootView);

        // request remove stream capability from views
        ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(new ViewFactoryChain[] {unmaterializedViewChain}, statementContext);
        if (!viewResourceDelegate.requestCapability(0, new RemoveStreamViewCapability(false), null))
        {
            throw new ExprValidationException(NamedWindowService.ERROR_MSG_DATAWINDOWS);
        }

        // create stop method using statement stream specs
        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                statementContext.getStatementStopService().fireStatementStopped();
                services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), statementContext.getFilterService(), false,false, true);
                String windowName = statementSpec.getCreateWindowDesc().getWindowName();
                services.getNamedWindowService().removeProcessor(windowName);
            }
        };

        // Materialize views
        Viewable finalView = services.getViewService().createViews(rootView, unmaterializedViewChain.getViewFactoryChain(), statementContext);

        // Attach tail view
        NamedWindowTailView tailView = processor.getTailView();
        finalView.addView(tailView);
        finalView = tailView;

        // Add a wildcard to the select clause as subscribers received the window contents
        statementSpec.getSelectClauseSpec().getSelectExprList().clear();
        statementSpec.getSelectClauseSpec().add(new SelectClauseElementWildcard());
        statementSpec.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);

        StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[] {windowType}, new String[] {windowName}, services.getEngineURI());
        ResultSetProcessor resultSetProcessor = ResultSetProcessorFactory.getProcessor(
                statementSpec, statementContext, typeService, null, new boolean[0], true);

        // Attach output view
        OutputProcessView outputView = OutputProcessViewFactory.makeView(resultSetProcessor, statementSpec, statementContext, services.getInternalEventRouter());
        finalView.addView(outputView);
        finalView = outputView;

        // Handle insert case
        if (statementSpec.getCreateWindowDesc().isInsert() && !isRecoveringStatement)
        {
            String insertFromWindow = statementSpec.getCreateWindowDesc().getInsertFromWindow();
            NamedWindowProcessor sourceWindow = services.getNamedWindowService().getProcessor(insertFromWindow);
            List<EventBean> events = new ArrayList<EventBean>();
            if (statementSpec.getCreateWindowDesc().getInsertFilter() != null)
            {
                EventBean[] eventsPerStream = new EventBean[1];
                ExprNode filter = statementSpec.getCreateWindowDesc().getInsertFilter();
                for (Iterator<EventBean> it = sourceWindow.getTailView().iterator(); it.hasNext();)
                {
                    EventBean candidate = it.next();
                    eventsPerStream[0] = candidate;
                    Boolean result = (Boolean) filter.evaluate(eventsPerStream, true, statementContext);
                    if ((result == null) || (!result))
                    {
                        continue;
                    }
                    events.add(candidate);
                }
            }
            else
            {
                for (Iterator<EventBean> it = sourceWindow.getTailView().iterator(); it.hasNext();)
                {
                    events.add(it.next());
                }
            }
            if (events.size() > 0)
            {
                EventType rootViewType = rootView.getEventType();
                EventBean[] convertedEvents = services.getEventAdapterService().typeCast(events, rootViewType);
                rootView.update(convertedEvents, null);
            }
        }

        log.debug(".start Statement start completed");

        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
    }

    private Pair<Viewable, EPStatementStopMethod> startCreateVariable(boolean isNewStatement)
        throws ExprValidationException, ViewProcessingException
    {
        CreateVariableDesc createDesc = statementSpec.getCreateVariableDesc();

        // Determime the variable type
        Class type;
        try
        {
            type = JavaClassHelper.getClassForSimpleName(createDesc.getVariableType());
        }
        catch (Throwable t)
        {
            throw new ExprValidationException("Cannot create variable '" + createDesc.getVariableName() + "', type '" +
                createDesc.getVariableType() + "' is not a recognized type");
        }

        // Get assignment value
        Object value = null;
        if (createDesc.getAssignment() != null)
        {
            // Evaluate assignment expression
            StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[0], new String[0], services.getEngineURI());
            ExprNode validated = createDesc.getAssignment().getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
            value = validated.evaluate(null, true, statementContext);
        }

        // Create variable
        try
        {
            services.getVariableService().createNewVariable(createDesc.getVariableName(), type, value, statementContext.getExtensionServicesContext());
        }
        catch (VariableExistsException ex)
        {
            // for new statement we don't allow creating the same variable
            if (isNewStatement)
            {
                throw new ExprValidationException("Cannot create variable: " + ex.getMessage());
            }

            // compare the type
            if (services.getVariableService().getReader(createDesc.getVariableName()).getType() != type)
            {
                throw new ExprValidationException("Cannot create variable: " + ex.getMessage());
            }
        }
        catch (VariableDeclarationException ex)
        {
            throw new ExprValidationException("Cannot create variable: " + ex.getMessage());
        }

        final CreateVariableView createView = new CreateVariableView(services.getEventAdapterService(), services.getVariableService(), createDesc.getVariableName(), statementContext.getStatementResultService());
        final int variableNum = services.getVariableService().getReader(createDesc.getVariableName()).getVariableNumber();
        services.getVariableService().registerCallback(variableNum, createView);
        statementContext.getStatementStopService().addSubscriber(new StatementStopCallback() {
            public void statementStopped()
            {
                services.getVariableService().unregisterCallback(variableNum, createView);
            }
        });

        // Create result set processor, use wildcard selection
        statementSpec.getSelectClauseSpec().getSelectExprList().clear();
        statementSpec.getSelectClauseSpec().add(new SelectClauseElementWildcard());
        statementSpec.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);
        StreamTypeService typeService = new StreamTypeServiceImpl(new EventType[] {createView.getEventType()}, new String[] {"create_variable"}, services.getEngineURI());
        ResultSetProcessor resultSetProcessor = ResultSetProcessorFactory.getProcessor(
                statementSpec, statementContext, typeService, null, new boolean[0], true);

        // Attach output view
        OutputProcessView outputView = OutputProcessViewFactory.makeView(resultSetProcessor, statementSpec, statementContext, services.getInternalEventRouter());
        createView.addView(outputView);

        return new Pair<Viewable, EPStatementStopMethod>(outputView, new EPStatementStopMethod(){
            public void stop()
            {
            }
        });
    }

    private Pair<Viewable, EPStatementStopMethod> startSelect(boolean isRecoveringResilient)
        throws ExprValidationException, ViewProcessingException
    {
        // Determine stream names for each stream - some streams may not have a name given
        String[] streamNames = determineStreamNames(statementSpec.getStreamSpecs());
        final boolean isJoin = statementSpec.getStreamSpecs().size() > 1;

        // First we create streams for subselects, if there are any
        SubSelectStreamCollection subSelectStreamDesc = createSubSelectStreams(isJoin);

        int numStreams = streamNames.length;
        final List<StopCallback> stopCallbacks = new LinkedList<StopCallback>();

        // Create streams and views
        Viewable[] eventStreamParentViewable = new Viewable[numStreams];
        ViewFactoryChain[] unmaterializedViewChain = new ViewFactoryChain[numStreams];
        String[] eventTypeNamees = new String[numStreams];

        // verify for joins that required views are present
        StreamJoinAnalysisResult joinAnalysisResult = verifyJoinViews(statementSpec.getStreamSpecs());

        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);

            // Create view factories and parent view based on a filter specification
            if (streamSpec instanceof FilterStreamSpecCompiled)
            {
                FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
                eventTypeNamees[i] = filterStreamSpec.getFilterSpec().getFilterForEventTypeName();

                // Since only for non-joins we get the existing stream's lock and try to reuse it's views
                Pair<EventStream, ManagedLock> streamLockPair = services.getStreamService().createStream(statementContext.getStatementId(), filterStreamSpec.getFilterSpec(),
                        statementContext.getFilterService(), statementContext.getEpStatementHandle(), isJoin, false, statementContext, false);
                eventStreamParentViewable[i] = streamLockPair.getFirst();

                // Use the re-used stream's lock for all this statement's locking needs
                if (streamLockPair.getSecond() != null)
                {
                    statementContext.getEpStatementHandle().setStatementLock(streamLockPair.getSecond());
                }

                unmaterializedViewChain[i] = services.getViewService().createFactories(i, eventStreamParentViewable[i].getEventType(), streamSpec.getViewSpecs(), streamSpec.getOptions(), statementContext);
            }
            // Create view factories and parent view based on a pattern expression
            else if (streamSpec instanceof PatternStreamSpecCompiled)
            {
                PatternStreamSpecCompiled patternStreamSpec = (PatternStreamSpecCompiled) streamSpec;
                boolean usedByChildViews = !streamSpec.getViewSpecs().isEmpty() || (statementSpec.getInsertIntoDesc() != null);
                final EventType eventType = services.getEventAdapterService().createSemiAnonymousMapType(patternStreamSpec.getTaggedEventTypes(), patternStreamSpec.getArrayEventTypes(), usedByChildViews);
                final EventStream sourceEventStream = new ZeroDepthStream(eventType);
                eventStreamParentViewable[i] = sourceEventStream;
                unmaterializedViewChain[i] = services.getViewService().createFactories(i, sourceEventStream.getEventType(), streamSpec.getViewSpecs(), streamSpec.getOptions(), statementContext);

                EvalRootNode rootNode = new EvalRootNode();
                rootNode.addChildNode(patternStreamSpec.getEvalNode());

                PatternMatchCallback callback = new PatternMatchCallback() {
                    public void matchFound(Map<String, Object> matchEvent)
                    {
                        EventBean compositeEvent = statementContext.getEventAdapterService().adaptorForTypedMap(matchEvent, eventType);
                        sourceEventStream.insert(compositeEvent);
                    }
                };

                PatternContext patternContext = statementContext.getPatternContextFactory().createContext(statementContext,
                        i, rootNode, !patternStreamSpec.getArrayEventTypes().isEmpty());
                PatternStopCallback patternStopCallback = rootNode.start(callback, patternContext);
                stopCallbacks.add(patternStopCallback);
            }
            // Create view factories and parent view based on a database SQL statement
            else if (streamSpec instanceof DBStatementStreamSpec)
            {
                if (!streamSpec.getViewSpecs().isEmpty())
                {
                    throw new ExprValidationException("Historical data joins do not allow views onto the data, view '"
                            + streamSpec.getViewSpecs().get(0).getObjectNamespace() + ':' + streamSpec.getViewSpecs().get(0).getObjectName() + "' is not valid in this context");
                }

                DBStatementStreamSpec sqlStreamSpec = (DBStatementStreamSpec) streamSpec;
                HistoricalEventViewable historicalEventViewable = DatabasePollingViewableFactory.createDBStatementView(i, sqlStreamSpec, services.getDatabaseRefService(), services.getEventAdapterService(), statementContext.getEpStatementHandle());
                unmaterializedViewChain[i] = new ViewFactoryChain(historicalEventViewable.getEventType(), new LinkedList<ViewFactory>());
                eventStreamParentViewable[i] = historicalEventViewable;
                stopCallbacks.add(historicalEventViewable);
            }
            else if (streamSpec instanceof MethodStreamSpec)
            {
                if (!streamSpec.getViewSpecs().isEmpty())
                {
                    throw new ExprValidationException("Method data joins do not allow views onto the data, view '"
                            + streamSpec.getViewSpecs().get(0).getObjectNamespace() + ':' + streamSpec.getViewSpecs().get(0).getObjectName() + "' is not valid in this context");
                }

                MethodStreamSpec methodStreamSpec = (MethodStreamSpec) streamSpec;
                HistoricalEventViewable historicalEventViewable = MethodPollingViewableFactory.createPollMethodView(i, methodStreamSpec, services.getEventAdapterService(), statementContext.getEpStatementHandle(), statementContext.getMethodResolutionService(), services.getEngineImportService(), statementContext.getSchedulingService(), statementContext.getScheduleBucket(), statementContext);
                unmaterializedViewChain[i] = new ViewFactoryChain(historicalEventViewable.getEventType(), new LinkedList<ViewFactory>());
                eventStreamParentViewable[i] = historicalEventViewable;
                stopCallbacks.add(historicalEventViewable);
            }
            else if (streamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) streamSpec;
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowConsumerView consumerView = processor.addConsumer(namedSpec.getFilterExpressions(), statementContext.getEpStatementHandle(), statementContext.getStatementStopService());
                eventStreamParentViewable[i] = consumerView;
                unmaterializedViewChain[i] = services.getViewService().createFactories(i, consumerView.getEventType(), namedSpec.getViewSpecs(), namedSpec.getOptions(), statementContext);
                joinAnalysisResult.setNamedWindow(i);
                eventTypeNamees[i] = namedSpec.getWindowName();

                // Consumers to named windows cannot declare a data window view onto the named window to avoid duplicate remove streams
                ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(unmaterializedViewChain, statementContext);
                viewResourceDelegate.requestCapability(i, new NotADataWindowViewCapability(), null);
            }
            else
            {
                throw new ExprValidationException("Unknown stream specification type: " + streamSpec);
            }
        }

        if (statementSpec.getMatchRecognizeSpec() != null)
        {
            if (isJoin)
            {
                throw new ExprValidationException("Joins are not allowed when using match recognize");
            }
            boolean isUnbound = (unmaterializedViewChain[0].getViewFactoryChain().isEmpty()) && (!(statementSpec.getStreamSpecs().get(0) instanceof NamedWindowConsumerStreamSpec));
            EventRowRegexNFAViewFactory factory = new EventRowRegexNFAViewFactory(unmaterializedViewChain[0], statementSpec.getMatchRecognizeSpec(), statementContext, isUnbound, statementSpec.getAnnotations());
            unmaterializedViewChain[0].getViewFactoryChain().add(factory);
        }

        // Obtain event types from ViewFactoryChains
        EventType[] streamEventTypes = new EventType[statementSpec.getStreamSpecs().size()];
        for (int i = 0; i < unmaterializedViewChain.length; i++)
        {
            streamEventTypes[i] = unmaterializedViewChain[i].getEventType();
        }

        // Materialize sub-select views
        startSubSelect(subSelectStreamDesc, streamNames, streamEventTypes, eventTypeNamees, stopCallbacks, statementSpec.getAnnotations());

        // List of statement streams
        final List<StreamSpecCompiled> statementStreamSpecs = new ArrayList<StreamSpecCompiled>();
        statementStreamSpecs.addAll(statementSpec.getStreamSpecs());

        // Construct type information per stream
        StreamTypeService typeService = new StreamTypeServiceImpl(streamEventTypes, streamNames, services.getEngineURI());
        ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(unmaterializedViewChain, statementContext);

        // boolean multiple expiry policy
        for (int i = 0; i < unmaterializedViewChain.length; i++)
        {
            if (unmaterializedViewChain[i].getDataWindowViewFactoryCount() > 1)
            {
                if (!viewResourceDelegate.requestCapability(i, new RemoveStreamViewCapability(true), null))
                {
                    log.warn("Combination of multiple data window expiry policies with views that do not support remove streams is not allowed");
                }
            }
        }

        // create stop method using statement stream specs
        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                statementContext.getStatementStopService().fireStatementStopped();

                for (StreamSpecCompiled streamSpec : statementStreamSpecs)
                {
                    if (streamSpec instanceof FilterStreamSpecCompiled)
                    {
                        FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
                        services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), statementContext.getFilterService(), isJoin, false, false);
                    }
                }
                for (StopCallback stopCallback : stopCallbacks)
                {
                    stopCallback.stop();
                }
                for (ExprSubselectNode subselect : statementSpec.getSubSelectExpressions())
                {
                    StreamSpecCompiled subqueryStreamSpec = subselect.getStatementSpecCompiled().getStreamSpecs().get(0);
                    if (subqueryStreamSpec instanceof FilterStreamSpecCompiled)
                    {
                        FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) subselect.getStatementSpecCompiled().getStreamSpecs().get(0);
                        services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), statementContext.getFilterService(), isJoin, true, false);
                    }
                }
            }
        };

        // Validate views that require validation, specifically streams that don't have
        // sub-views such as DB SQL joins
        for (int stream = 0; stream < eventStreamParentViewable.length; stream++)
        {
            Viewable viewable = eventStreamParentViewable[stream];
            if (viewable instanceof ValidatedView)
            {
                ValidatedView validatedView = (ValidatedView) viewable;
                validatedView.validate(typeService,
                        statementContext.getMethodResolutionService(),
                        statementContext.getSchedulingService(),
                        statementContext.getVariableService(), statementContext);
            }
            if (viewable instanceof HistoricalEventViewable)
            {
                HistoricalEventViewable historicalView = (HistoricalEventViewable) viewable;
                if (historicalView.getRequiredStreams().contains(stream))
                {
                    throw new ExprValidationException("Parameters for historical stream " + stream + " indicate that the stream is subordinate to itself as stream parameters originate in the same stream");
                }
            }
        }

        // Construct a processor for results posted by views and joins, which takes care of aggregation if required.
        // May return null if we don't need to post-process results posted by views or joins.
        ResultSetProcessor resultSetProcessor = ResultSetProcessorFactory.getProcessor(
                statementSpec, statementContext, typeService, viewResourceDelegate, joinAnalysisResult.getUnidirectionalInd(), true);

        // Validate where-clause filter tree, outer join clause and output limit expression
        validateNodes(statementSpec, statementContext, typeService, viewResourceDelegate, true);

        // Materialize views
        Viewable[] streamViews = new Viewable[streamEventTypes.length];
        for (int i = 0; i < streamViews.length; i++)
        {
            streamViews[i] = services.getViewService().createViews(eventStreamParentViewable[i], unmaterializedViewChain[i].getViewFactoryChain(), statementContext);
        }

        // For just 1 event stream without joins, handle the one-table process separatly.
        Viewable finalView;
        JoinPreloadMethod joinPreloadMethod = null;
        if (streamNames.length == 1)
        {
            finalView = handleSimpleSelect(streamViews[0], resultSetProcessor, statementContext);
        }
        else
        {
            Pair<Viewable, JoinPreloadMethod> pair = handleJoin(streamNames, streamEventTypes, streamViews, resultSetProcessor, statementSpec.getSelectStreamSelectorEnum(), statementContext, stopCallbacks, joinAnalysisResult);
            finalView = pair.getFirst();
            joinPreloadMethod = pair.getSecond();
        }

        // Replay any named window data, for later consumers of named data windows
        boolean hasNamedWindow = false;
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);
            if (streamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                hasNamedWindow = true;
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) streamSpec;
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowTailView consumerView = processor.getTailView();
                NamedWindowConsumerView view = (NamedWindowConsumerView) eventStreamParentViewable[i];

                // preload view for stream unless the expiry policy is batch window
                ArrayList<EventBean> eventsInWindow = new ArrayList<EventBean>();
                if (!consumerView.isParentBatchWindow())
                {
                    for (EventBean aConsumerView : consumerView)
                    {
                        eventsInWindow.add(aConsumerView);
                    }
                }
                if (!eventsInWindow.isEmpty() && !isRecoveringResilient)
                {
                    EventBean[] newEvents = eventsInWindow.toArray(new EventBean[eventsInWindow.size()]);
                    view.update(newEvents, null);
                }

                // in a join, preload indexes, if any
                if (joinPreloadMethod != null)
                {
                    joinPreloadMethod.preloadFromBuffer(i);
                }
            }
        }
        // last, for aggregation we need to send the current join results to the result set processor
        if ((hasNamedWindow) && (joinPreloadMethod != null) && (!isRecoveringResilient))
        {
            joinPreloadMethod.preloadAggregation(resultSetProcessor);
        }

        log.debug(".start Statement start completed");

        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
    }

    /**
     * Joins require a remove stream: therefore a view is required for each stream, since all views post a remove stream.
     * <p>
     * If a view is polling or unidirectional, it does not require a view.
     * @param streamSpecs streams
     * @return analysis result
     * @throws ExprValidationException if constraints violated
     */
    private StreamJoinAnalysisResult verifyJoinViews(List<StreamSpecCompiled> streamSpecs)
            throws ExprValidationException
    {
        StreamJoinAnalysisResult analysisResult = new StreamJoinAnalysisResult(streamSpecs.size());
        if (streamSpecs.size() < 2)
        {
            return analysisResult;
        }

        // Determine if any stream has a unidirectional keyword

        // inspect unidirection indicator and named window flags
        int unidirectionalStreamNumber = -1;
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);
            if (streamSpec.getOptions().isUnidirectional())
            {
                analysisResult.setUnidirectionalInd(i);
                if (unidirectionalStreamNumber != -1)
                {
                    throw new ExprValidationException("The unidirectional keyword can only apply to one stream in a join");
                }
                unidirectionalStreamNumber = i;
            }
            if (!streamSpec.getViewSpecs().isEmpty())
            {
                analysisResult.setHasChildViews(i);
            }
            if (streamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                analysisResult.setNamedWindow(i);
            }
        }
        if ((unidirectionalStreamNumber != -1) && (analysisResult.getHasChildViews()[unidirectionalStreamNumber]))
        {
            throw new ExprValidationException("The unidirectional keyword requires that no views are declared onto the stream");
        }
        analysisResult.setUnidirectionalStreamNumber(unidirectionalStreamNumber);

        // count streams that provide data, excluding streams that poll data (DB and method)
        int countProviderNonpolling = 0;
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);
            if ((streamSpec instanceof MethodStreamSpec) ||
                (streamSpec instanceof DBStatementStreamSpec))
            {
                continue;
            }
            countProviderNonpolling++;
        }

        // if there is only one stream providing data, the analysis is done 
        if (countProviderNonpolling == 1)
        {
            return analysisResult;
        }
        // there are multiple driving streams, verify the presence of a view for insert/remove stream

        // validation of join views works differently for unidirectional as there can be self-joins that don't require a view
        // see if this is a self-join in which all streams are filters and filter specification is the same. 
        FilterSpecCompiled unidirectionalFilterSpec = null;
        FilterSpecCompiled lastFilterSpec = null;
        boolean pureSelfJoin = true;
        for (StreamSpecCompiled streamSpec : statementSpec.getStreamSpecs())
        {
            if (!(streamSpec instanceof FilterStreamSpecCompiled))
            {
                pureSelfJoin = false;
                continue;
            }

            FilterSpecCompiled filterSpec = ((FilterStreamSpecCompiled) streamSpec).getFilterSpec();
            if ((lastFilterSpec != null) && (!lastFilterSpec.equalsTypeAndFilter(filterSpec)))
            {
                pureSelfJoin = false;
            }
            if (!streamSpec.getViewSpecs().isEmpty())
            {
                pureSelfJoin = false;
            }
            lastFilterSpec = filterSpec;

            if (streamSpec.getOptions().isUnidirectional())
            {
                unidirectionalFilterSpec = filterSpec;
            }
        }        

        // self-join without views and not unidirectional
        if ((pureSelfJoin) && (unidirectionalFilterSpec == null))
        {
            analysisResult.setPureSelfJoin(true);
            return analysisResult;
        }

        // weed out filter and pattern streams that don't have a view in a join
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);
            if (!streamSpec.getViewSpecs().isEmpty())
            {
                continue;
            }

            String name = streamSpec.getOptionalStreamName();
            if ((name == null) && (streamSpec instanceof FilterStreamSpecCompiled))
            {
                name = ((FilterStreamSpecCompiled) streamSpec).getFilterSpec().getFilterForEventTypeName();
            }
            if ((name == null) && (streamSpec instanceof PatternStreamSpecCompiled))
            {
                name = "pattern event stream";
            }

            if (streamSpec.getOptions().isUnidirectional())
            {
                continue;
            }
            // allow a self-join without a child view, in that the filter spec is the same as the unidirection's stream filter
            if ((unidirectionalFilterSpec != null) &&
                (streamSpec instanceof FilterStreamSpecCompiled) &&
                (((FilterStreamSpecCompiled) streamSpec).getFilterSpec().equalsTypeAndFilter(unidirectionalFilterSpec)))
            {
                analysisResult.setUnidirectionalNonDriving(i);
                continue;
            }
            if ((streamSpec instanceof FilterStreamSpecCompiled) ||
                (streamSpec instanceof PatternStreamSpecCompiled))
            {
                throw new ExprValidationException("Joins require that at least one view is specified for each stream, no view was specified for " + name);
            }
        }

        return analysisResult;
    }

    private Pair<Viewable, JoinPreloadMethod> handleJoin(String[] streamNames,
                                                         EventType[] streamTypes,
                                                         Viewable[] streamViews,
                                                         ResultSetProcessor resultSetProcessor,
                                                         SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
                                                         StatementContext statementContext,
                                                         List<StopCallback> stopCallbacks,
                                                         StreamJoinAnalysisResult joinAnalysisResult)
            throws ExprValidationException
    {
        // Handle joins
        final JoinSetComposer composer = statementContext.getJoinSetComposerFactory().makeComposer(statementSpec.getOuterJoinDescList(), statementSpec.getFilterRootNode(), streamTypes, streamNames, streamViews, selectStreamSelectorEnum, joinAnalysisResult, statementContext);

        stopCallbacks.add(new StopCallback(){
            public void stop()
            {
                composer.destroy();
            }
        });

        JoinSetFilter filter = new JoinSetFilter(statementSpec.getFilterRootNode());
        OutputProcessView indicatorView = OutputProcessViewFactory.makeView(resultSetProcessor, statementSpec,
                statementContext, services.getInternalEventRouter());

        // Create strategy for join execution
        JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView, statementContext);

        // The view needs a reference to the join execution to pull iterator values
        indicatorView.setJoinExecutionStrategy(execution);

        // Hook up dispatchable with buffer and execution strategy
        JoinExecStrategyDispatchable joinStatementDispatch = new JoinExecStrategyDispatchable(execution, statementSpec.getStreamSpecs().size());
        statementContext.getEpStatementHandle().setOptionalDispatchable(joinStatementDispatch);

        JoinPreloadMethod preloadMethod;
        if (joinAnalysisResult.getUnidirectionalStreamNumber() >= 0)
        {
            preloadMethod = new JoinPreloadMethodNull();
        }
        else
        {
            preloadMethod = new JoinPreloadMethodImpl(streamNames.length, composer); 
        }

        // Create buffer for each view. Point buffer to dispatchable for join.
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            BufferView buffer = new BufferView(i);
            streamViews[i].addView(buffer);
            buffer.setObserver(joinStatementDispatch);
            preloadMethod.setBuffer(buffer, i);
        }

        return new Pair<Viewable, JoinPreloadMethod>(indicatorView, preloadMethod);
    }

    /**
     * Returns a stream name assigned for each stream, generated if none was supplied.
     * @param streams - stream specifications
     * @return array of stream names
     */
    @SuppressWarnings({"StringContatenationInLoop"})
    protected static String[] determineStreamNames(List<StreamSpecCompiled> streams)
    {
        String[] streamNames = new String[streams.size()];
        for (int i = 0; i < streams.size(); i++)
        {
            // Assign a stream name for joins, if not supplied
            streamNames[i] = streams.get(i).getOptionalStreamName();
            if (streamNames[i] == null)
            {
                streamNames[i] = "stream_" + i;
            }
        }
        return streamNames;
    }

    /**
     * Validate filter and join expression nodes.
     * @param statementSpec the compiled statement
     * @param statementContext the statement services
     * @param typeService the event types for streams
     * @param viewResourceDelegate the delegate to verify expressions that use view resources
     * @param allowSubqueryWhere true to allow subqueries
     */
    protected static void validateNodes(StatementSpecCompiled statementSpec,
                                        StatementContext statementContext,
                                        StreamTypeService typeService,
                                        ViewResourceDelegate viewResourceDelegate,
                                        boolean allowSubqueryWhere)
    {
        MethodResolutionService methodResolutionService = statementContext.getMethodResolutionService();

        if (statementSpec.getFilterRootNode() != null)
        {
            ExprNode optionalFilterNode = statementSpec.getFilterRootNode();

            // Validate where clause, initializing nodes to the stream ids used
            try
            {
                if (!allowSubqueryWhere)
                {
                    ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
                    optionalFilterNode.accept(visitor);
                    if (!visitor.getSubselects().isEmpty())
                    {
                        throw new ExprValidationException("Subqueries are not allowed in the where-clause in this context");
                    }
                }

                optionalFilterNode = optionalFilterNode.getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
                statementSpec.setFilterExprRootNode(optionalFilterNode);

                // Make sure there is no aggregation in the where clause
                List<ExprAggregateNode> aggregateNodes = new LinkedList<ExprAggregateNode>();
                ExprAggregateNode.getAggregatesBottomUp(optionalFilterNode, aggregateNodes);
                if (!aggregateNodes.isEmpty())
                {
                    throw new ExprValidationException("An aggregate function may not appear in a WHERE clause (use the HAVING clause)");
                }
            }
            catch (ExprValidationException ex)
            {
                log.debug(".validateNodes Validation exception for filter=" + optionalFilterNode.toExpressionString(), ex);
                throw new EPStatementException("Error validating expression: " + ex.getMessage(), statementContext.getExpression());
            }
        }

        if ((statementSpec.getOutputLimitSpec() != null) && (statementSpec.getOutputLimitSpec().getWhenExpressionNode() != null))
        {
            ExprNode outputLimitWhenNode = statementSpec.getOutputLimitSpec().getWhenExpressionNode();

            // Validate where clause, initializing nodes to the stream ids used
            try
            {
                EventType outputLimitType = OutputConditionExpression.getBuiltInEventType(statementContext.getEventAdapterService());
                StreamTypeService typeServiceOutputWhen = new StreamTypeServiceImpl(new EventType[] {outputLimitType}, new String[]{null}, statementContext.getEngineURI());
                outputLimitWhenNode = outputLimitWhenNode.getValidatedSubtree(typeServiceOutputWhen, methodResolutionService, null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
                statementSpec.getOutputLimitSpec().setWhenExpressionNode(outputLimitWhenNode);

                if (JavaClassHelper.getBoxedType(outputLimitWhenNode.getType()) != Boolean.class)
                {
                    throw new ExprValidationException("The when-trigger expression in the OUTPUT WHEN clause must return a boolean-type value");
                }
                validateNoAggregations(outputLimitWhenNode, "An aggregate function may not appear in a OUTPUT LIMIT clause");

                if (statementSpec.getOutputLimitSpec().getThenExpressions() != null)
                {
                    for (OnTriggerSetAssignment assign : statementSpec.getOutputLimitSpec().getThenExpressions())
                    {
                        ExprNode node = assign.getExpression().getValidatedSubtree(typeServiceOutputWhen, methodResolutionService, null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
                        assign.setExpression(node);
                        validateNoAggregations(node, "An aggregate function may not appear in a OUTPUT LIMIT clause");
                    }
                }
            }
            catch (ExprValidationException ex)
            {
                throw new EPStatementException("Error validating expression: " + ex.getMessage(), statementContext.getExpression());
            }
        }

        for (int outerJoinCount = 0; outerJoinCount < statementSpec.getOuterJoinDescList().size(); outerJoinCount++)
        {
            OuterJoinDesc outerJoinDesc = statementSpec.getOuterJoinDescList().get(outerJoinCount);

            UniformPair<Integer> streamIdPair = validateOuterJoinPropertyPair(statementContext, outerJoinDesc.getLeftNode(), outerJoinDesc.getRightNode(), outerJoinCount,
                    typeService, viewResourceDelegate);

            if (outerJoinDesc.getAdditionalLeftNodes() != null)
            {
                Set<Integer> streamSet = new HashSet<Integer>();
                streamSet.add(streamIdPair.getFirst());
                streamSet.add(streamIdPair.getSecond());
                for (int i = 0; i < outerJoinDesc.getAdditionalLeftNodes().length; i++)
                {
                    UniformPair<Integer> streamIdPairAdd = validateOuterJoinPropertyPair(statementContext, outerJoinDesc.getAdditionalLeftNodes()[i], outerJoinDesc.getAdditionalRightNodes()[i], outerJoinCount,
                            typeService, viewResourceDelegate);

                    // make sure all additional properties point to the same two streams
                    if ((!streamSet.contains(streamIdPairAdd.getFirst()) || (!streamSet.contains(streamIdPairAdd.getSecond()))))
                    {
                        String message = "Outer join ON-clause columns must refer to properties of the same joined streams" +
                                " when using multiple columns in the on-clause";
                        throw new EPStatementException("Error validating expression: " + message, statementContext.getExpression());
                    }

                }
            }
        }
    }

    private static void validateNoAggregations(ExprNode exprNode, String errorMsg)
            throws ExprValidationException
    {
        // Make sure there is no aggregation in the where clause
        List<ExprAggregateNode> aggregateNodes = new LinkedList<ExprAggregateNode>();
        ExprAggregateNode.getAggregatesBottomUp(exprNode, aggregateNodes);
        if (!aggregateNodes.isEmpty())
        {
            throw new ExprValidationException(errorMsg);
        }
    }

    private static UniformPair<Integer> validateOuterJoinPropertyPair(
            StatementContext statementContext,
            ExprIdentNode leftNode,
            ExprIdentNode rightNode,
            int outerJoinCount,
            StreamTypeService typeService,
            ViewResourceDelegate viewResourceDelegate)
    {
        // Validate the outer join clause using an artificial equals-node on top.
        // Thus types are checked via equals.
        // Sets stream ids used for validated nodes.
        ExprNode equalsNode = new ExprEqualsNode(false);
        equalsNode.addChildNode(leftNode);
        equalsNode.addChildNode(rightNode);
        try
        {
            equalsNode = equalsNode.getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), viewResourceDelegate, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
        }
        catch (ExprValidationException ex)
        {
            log.debug("Validation exception for outer join node=" + equalsNode.toExpressionString(), ex);
            throw new EPStatementException("Error validating expression: " + ex.getMessage(), statementContext.getExpression());
        }

        // Make sure we have left-hand-side and right-hand-side refering to different streams
        int streamIdLeft = leftNode.getStreamId();
        int streamIdRight = rightNode.getStreamId();
        if (streamIdLeft == streamIdRight)
        {
            String message = "Outer join ON-clause cannot refer to properties of the same stream";
            throw new EPStatementException("Error validating expression: " + message, statementContext.getExpression());
        }

        // Make sure one of the properties refers to the acutual stream currently being joined
        int expectedStreamJoined = outerJoinCount + 1;
        if ((streamIdLeft != expectedStreamJoined) && (streamIdRight != expectedStreamJoined))
        {
            String message = "Outer join ON-clause must refer to at least one property of the joined stream" +
                    " for stream " + expectedStreamJoined;
            throw new EPStatementException("Error validating expression: " + message, statementContext.getExpression());
        }

        // Make sure neither of the streams refer to a 'future' stream
        String badPropertyName = null;
        if (streamIdLeft > outerJoinCount + 1)
        {
            badPropertyName = leftNode.getResolvedPropertyName();
        }
        if (streamIdRight > outerJoinCount + 1)
        {
            badPropertyName = rightNode.getResolvedPropertyName();
        }
        if (badPropertyName != null)
        {
            String message = "Outer join ON-clause invalid scope for property" +
                    " '" + badPropertyName + "', expecting the current or a prior stream scope";
            throw new EPStatementException("Error validating expression: " + message, statementContext.getExpression());
        }

        return new UniformPair<Integer>(streamIdLeft, streamIdRight);
    }


    private Viewable handleSimpleSelect(Viewable view,
                                        ResultSetProcessor resultSetProcessor,
                                        StatementContext statementContext)
            throws ExprValidationException
    {
        Viewable finalView = view;

        // Add filter view that evaluates the filter expression
        if (statementSpec.getFilterRootNode() != null)
        {
            FilterExprView filterView = new FilterExprView(statementSpec.getFilterRootNode(), statementContext);
            finalView.addView(filterView);
            finalView = filterView;
        }

        OutputProcessView selectView = OutputProcessViewFactory.makeView(resultSetProcessor, statementSpec,
                statementContext, services.getInternalEventRouter());
        finalView.addView(selectView);
        finalView = selectView;

        return finalView;
    }

    private SubSelectStreamCollection createSubSelectStreams(boolean isJoin)
            throws ExprValidationException, ViewProcessingException
    {
        SubSelectStreamCollection subSelectStreamDesc = new SubSelectStreamCollection();
        int subselectStreamNumber = 1024;

        // Process all subselect expression nodes
        for (ExprSubselectNode subselect : statementSpec.getSubSelectExpressions())
        {
            StatementSpecCompiled statementSpec = subselect.getStatementSpecCompiled();

            if (statementSpec.getStreamSpecs().get(0) instanceof FilterStreamSpecCompiled)
            {
                FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) statementSpec.getStreamSpecs().get(0);

                // A child view is required to limit the stream
                if (filterStreamSpec.getViewSpecs().size() == 0)
                {
                    throw new ExprValidationException("Subqueries require one or more views to limit the stream, consider declaring a length or time window");
                }

                subselectStreamNumber++;

                // Register filter, create view factories
                Pair<EventStream, ManagedLock> streamLockPair = services.getStreamService().createStream(statementContext.getStatementId(), filterStreamSpec.getFilterSpec(),
                        statementContext.getFilterService(), statementContext.getEpStatementHandle(), isJoin, true, statementContext, false);
                Viewable viewable = streamLockPair.getFirst();
                ViewFactoryChain viewFactoryChain = services.getViewService().createFactories(subselectStreamNumber, viewable.getEventType(), filterStreamSpec.getViewSpecs(), filterStreamSpec.getOptions(), statementContext);
                subselect.setRawEventType(viewFactoryChain.getEventType());

                // Add lookup to list, for later starts
                subSelectStreamDesc.add(subselect, subselectStreamNumber, viewable, viewFactoryChain);
            }
            else
            {
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) statementSpec.getStreamSpecs().get(0);
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowConsumerView consumerView = processor.addConsumer(namedSpec.getFilterExpressions(), statementContext.getEpStatementHandle(), statementContext.getStatementStopService());
                ViewFactoryChain viewFactoryChain = services.getViewService().createFactories(0, consumerView.getEventType(), namedSpec.getViewSpecs(), namedSpec.getOptions(), statementContext);
                subSelectStreamDesc.add(subselect, subselectStreamNumber, consumerView, viewFactoryChain);
            }
        }

        return subSelectStreamDesc;
    }

    private void startSubSelect(SubSelectStreamCollection subSelectStreamDesc, String[] outerStreamNames, EventType[] outerEventTypes, String[] outereventTypeNamees, List<StopCallback> stopCallbacks, Annotation[] annotations)
            throws ExprValidationException
    {
        for (ExprSubselectNode subselect : statementSpec.getSubSelectExpressions())
        {
            StatementSpecCompiled statementSpec = subselect.getStatementSpecCompiled();
            StreamSpecCompiled filterStreamSpec = statementSpec.getStreamSpecs().get(0);

            String subselecteventTypeName = null;
            if (filterStreamSpec instanceof FilterStreamSpecCompiled)
            {
                subselecteventTypeName = ((FilterStreamSpecCompiled) filterStreamSpec).getFilterSpec().getFilterForEventTypeName();
            }
            else if (filterStreamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                subselecteventTypeName = ((NamedWindowConsumerStreamSpec) filterStreamSpec).getWindowName();
            }

            ViewFactoryChain viewFactoryChain = subSelectStreamDesc.getViewFactoryChain(subselect);
            EventType eventType = viewFactoryChain.getEventType();

            // determine a stream name unless one was supplied
            String subexpressionStreamName = filterStreamSpec.getOptionalStreamName();
            int subselectStreamNumber = subSelectStreamDesc.getStreamNumber(subselect);
            if (subexpressionStreamName == null)
            {
                subexpressionStreamName = "$subselect_" + subselectStreamNumber;
            }

            // Named windows don't allow data views
            if (filterStreamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(new ViewFactoryChain[] {viewFactoryChain}, statementContext);
                viewResourceDelegate.requestCapability(0, new NotADataWindowViewCapability(), null);
            }

            // Streams event types are the original stream types with the stream zero the subselect stream
            LinkedHashMap<String, Pair<EventType, String>> namesAndTypes = new LinkedHashMap<String, Pair<EventType, String>>();
            namesAndTypes.put(subexpressionStreamName, new Pair<EventType, String>(eventType, subselecteventTypeName));
            for (int i = 0; i < outerEventTypes.length; i++)
            {
                Pair<EventType, String> pair = new Pair<EventType, String>(outerEventTypes[i], outereventTypeNamees[i]);
                namesAndTypes.put(outerStreamNames[i], pair);
            }
            StreamTypeService subselectTypeService = new StreamTypeServiceImpl(namesAndTypes, services.getEngineURI(), true, true);
            ViewResourceDelegate viewResourceDelegateSubselect = new ViewResourceDelegateImpl(new ViewFactoryChain[] {viewFactoryChain}, statementContext);

            // Validate select expression
            SelectClauseSpecCompiled selectClauseSpec = subselect.getStatementSpecCompiled().getSelectClauseSpec();
            AggregationService aggregationService = null;
            if (selectClauseSpec.getSelectExprList().size() > 0)
            {
                SelectClauseElementCompiled element = selectClauseSpec.getSelectExprList().get(0);
                if (element instanceof SelectClauseExprCompiledSpec)
                {
                    // validate
                    SelectClauseExprCompiledSpec compiled = (SelectClauseExprCompiledSpec) element;
                    ExprNode selectExpression = compiled.getSelectExpression();
                    selectExpression = selectExpression.getValidatedSubtree(subselectTypeService, statementContext.getMethodResolutionService(), viewResourceDelegateSubselect, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
                    subselect.setSelectClause(selectExpression);
                    subselect.setSelectAsName(compiled.getAssignedName());

                    // handle aggregation
                    List<ExprAggregateNode> aggExprNodes = new LinkedList<ExprAggregateNode>();
                    ExprAggregateNode.getAggregatesBottomUp(selectExpression, aggExprNodes);
                    if (aggExprNodes.size() > 0)
                    {
                        List<ExprAggregateNode> havingAgg = Collections.emptyList();
                        List<ExprAggregateNode> orderByAgg = Collections.emptyList();
                        aggregationService = AggregationServiceFactory.getService(aggExprNodes, havingAgg, orderByAgg, false, null, statementContext, annotations);

                        // Other stream properties, if there is aggregation, cannot be under aggregation.
                        for (ExprAggregateNode aggNode : aggExprNodes)
                        {
                            List<Pair<Integer, String>> propertiesNodesAggregated = getExpressionProperties(aggNode, true);
                            for (Pair<Integer, String> pair : propertiesNodesAggregated)
                            {
                                if (pair.getFirst() != 0)
                                {
                                    throw new ExprValidationException("Subselect aggregation function cannot aggregate across correlated properties");
                                }
                            }
                        }

                        // This stream (stream 0) properties must either all be under aggregation, or all not be.
                        List<Pair<Integer, String>> propertiesNotAggregated = getExpressionProperties(selectExpression, false);
                        for (Pair<Integer, String> pair : propertiesNotAggregated)
                        {
                            if (pair.getFirst() == 0)
                            {
                                throw new ExprValidationException("Subselect properties must all be within aggregation functions");
                            }
                        }
                    }
                }
            }

            // no aggregation functions allowed in filter
            if (statementSpec.getFilterRootNode() != null)
            {
                List<ExprAggregateNode> aggExprNodesFilter = new LinkedList<ExprAggregateNode>();
                ExprAggregateNode.getAggregatesBottomUp(statementSpec.getFilterRootNode(), aggExprNodesFilter);
                if (aggExprNodesFilter.size() > 0)
                {
                    throw new ExprValidationException("Aggregation functions are not supported within subquery filters, consider using insert-into instead");
                }
            }

            // Validate filter expression, if there is one
            ExprNode filterExpr = statementSpec.getFilterRootNode();
            if (filterExpr != null)
            {
                filterExpr = filterExpr.getValidatedSubtree(subselectTypeService, statementContext.getMethodResolutionService(), viewResourceDelegateSubselect, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
                if (JavaClassHelper.getBoxedType(filterExpr.getType()) != Boolean.class)
                {
                    throw new ExprValidationException("Subselect filter expression must return a boolean value");
                }

                // check the presence of a correlated filter, not allowed with aggregation
                ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
                filterExpr.accept(visitor);
                List<Pair<Integer, String>> propertiesNodes = visitor.getExprProperties();
                for (Pair<Integer, String> pair : propertiesNodes)
                {
                    if ((pair.getFirst() != 0) && (aggregationService != null))
                    {
                        throw new ExprValidationException("Subselect filter expression cannot be a correlated expression when aggregating properties via aggregation function");
                    }
                }
            }

            // Finally create views
            Viewable viewableRoot = subSelectStreamDesc.getRootViewable(subselect);
            Viewable subselectView = services.getViewService().createViews(viewableRoot, viewFactoryChain.getViewFactoryChain(), statementContext);

            // If we do aggregation, then the view results must be added and removed from aggregation
            final EventTable eventIndex;
            // Under aggregation conditions, there is no lookup/corelated subquery strategy, and
            // the view-supplied events are simply aggregated, a null-event supplied to the stream for the select-clause, and not kept in index.
            // Note that "var1 + max(var2)" is not allowed as some properties are not under aggregation (which event to use?).
            if (aggregationService != null)
            {
                SubselectAggregatorView aggregatorView = new SubselectAggregatorView(aggregationService, filterExpr, statementContext);
                subselectView.addView(aggregatorView);
                subselectView = aggregatorView;

                eventIndex = null;
                subselect.setStrategy(new TableLookupStrategyNullRow());
                subselect.setFilterExpr(null);      // filter not evaluated by subselect expression as not correlated
            }
            else
            {
                // Determine indexing of the filter expression
                Pair<EventTable, TableLookupStrategy> indexPair = determineSubqueryIndex(filterExpr, eventType,
                        outerEventTypes, subselectTypeService);
                subselect.setStrategy(indexPair.getSecond());
                subselect.setFilterExpr(filterExpr);
                eventIndex = indexPair.getFirst();
            }

            // Clear out index on statement stop
            stopCallbacks.add(new SubqueryStopCallback(eventIndex));

            // Preload
            if (filterStreamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) filterStreamSpec ;
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowTailView consumerView = processor.getTailView();

                // preload view for stream
                ArrayList<EventBean> eventsInWindow = new ArrayList<EventBean>();
                for(Iterator<EventBean> it = consumerView.iterator(); it.hasNext();)
                {
                    eventsInWindow.add(it.next());
                }
                EventBean[] newEvents = eventsInWindow.toArray(new EventBean[eventsInWindow.size()]);
                ((View)viewableRoot).update(newEvents, null); // fill view
                if (eventIndex != null)
                {
                    eventIndex.add(newEvents);  // fill index
                }
            }
            else        // preload from the data window that site on top
            {
                // Start up event table from the iterator
                Iterator<EventBean> it = subselectView.iterator();
                if ((it != null) && (it.hasNext()))
                {
                    ArrayList<EventBean> preloadEvents = new ArrayList<EventBean>();
                    for (;it.hasNext();)
                    {
                        preloadEvents.add(it.next());
                    }
                    if (eventIndex != null)
                    {
                        eventIndex.add(preloadEvents.toArray(new EventBean[preloadEvents.size()]));
                    }
                }
            }

            // hook up subselect viewable and event table
            BufferView bufferView = new BufferView(subselectStreamNumber);
            bufferView.setObserver(new SubselectBufferObserver(eventIndex));
            subselectView.addView(bufferView);
        }
    }

    private Pair<EventTable, TableLookupStrategy> determineSubqueryIndex(ExprNode filterExpr,
                                                                                 EventType viewableEventType,
                                                                                 EventType[] outerEventTypes,
                                                                                 StreamTypeService subselectTypeService)
            throws ExprValidationException
    {
        // No filter expression means full table scan
        if (filterExpr == null)
        {
            UnindexedEventTable table = new UnindexedEventTable(0);
            FullTableScanLookupStrategy strategy = new FullTableScanLookupStrategy(table);
            return new Pair<EventTable, TableLookupStrategy>(table, strategy);
        }

        // analyze query graph
        QueryGraph queryGraph = new QueryGraph(outerEventTypes.length + 1);
        FilterExprAnalyzer.analyze(filterExpr, queryGraph);

        // Build a list of streams and indexes
        Map<String, JoinedPropDesc> joinProps = new LinkedHashMap<String, JoinedPropDesc>();
        boolean mustCoerce = false;
        for (int stream = 0; stream <  outerEventTypes.length; stream++)
        {
            int lookupStream = stream + 1;
            String[] keyPropertiesJoin = queryGraph.getKeyProperties(lookupStream, 0);
            String[] indexPropertiesJoin = queryGraph.getIndexProperties(lookupStream, 0);
            if ((keyPropertiesJoin == null) || (keyPropertiesJoin.length == 0))
            {
                continue;
            }
            if (keyPropertiesJoin.length != indexPropertiesJoin.length)
            {
                throw new IllegalStateException("Invalid query key and index property collection for stream " + stream);
            }

            for (int i = 0; i < keyPropertiesJoin.length; i++)
            {
                Class keyPropType = JavaClassHelper.getBoxedType(subselectTypeService.getEventTypes()[lookupStream].getPropertyType(keyPropertiesJoin[i]));
                Class indexedPropType = JavaClassHelper.getBoxedType(subselectTypeService.getEventTypes()[0].getPropertyType(indexPropertiesJoin[i]));
                Class coercionType = indexedPropType;
                if (keyPropType != indexedPropType)
                {
                    coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, keyPropType);
                    mustCoerce = true;
                }

                JoinedPropDesc desc = new JoinedPropDesc(indexPropertiesJoin[i],
                        coercionType, keyPropertiesJoin[i], stream);
                joinProps.put(indexPropertiesJoin[i], desc);
            }
        }

        if (joinProps.size() != 0)
        {
            String indexedProps[] = joinProps.keySet().toArray(new String[joinProps.keySet().size()]);
            int[] keyStreamNums = JoinedPropDesc.getKeyStreamNums(joinProps.values());
            String[] keyProps = JoinedPropDesc.getKeyProperties(joinProps.values());

            if (!mustCoerce)
            {
                PropertyIndexedEventTable table = new PropertyIndexedEventTable(0, viewableEventType, indexedProps);
                TableLookupStrategy strategy = new IndexedTableLookupStrategy( outerEventTypes,
                        keyStreamNums, keyProps, table);
                return new Pair<EventTable, TableLookupStrategy>(table, strategy);
            }
            else
            {
                Class coercionTypes[] = JoinedPropDesc.getCoercionTypes(joinProps.values());
                PropertyIndTableCoerceAdd table = new PropertyIndTableCoerceAdd(0, viewableEventType, indexedProps, coercionTypes);
                TableLookupStrategy strategy = new IndexedTableLookupStrategyCoercing( outerEventTypes, keyStreamNums, keyProps, table, coercionTypes);
                return new Pair<EventTable, TableLookupStrategy>(table, strategy);
            }
        }
        else
        {
            UnindexedEventTable table = new UnindexedEventTable(0);
            return new Pair<EventTable, TableLookupStrategy>(table, new FullTableScanLookupStrategy(table));
        }
    }

    // For delete actions from named windows
    private ExprNode validateJoinNamedWindow(ExprNode deleteJoinExpr,
                                         EventType namedWindowType,
                                         String namedWindowStreamName,
                                         String namedWindowName,
                                         EventType filteredType,
                                         String filterStreamName,
                                         String filteredTypeName) throws ExprValidationException
    {
        if (deleteJoinExpr == null)
        {
            return null;
        }

        LinkedHashMap<String, Pair<EventType, String>> namesAndTypes = new LinkedHashMap<String, Pair<EventType, String>>();
        namesAndTypes.put(namedWindowStreamName, new Pair<EventType, String>(namedWindowType, namedWindowName));
        namesAndTypes.put(filterStreamName, new Pair<EventType, String>(filteredType, filteredTypeName));
        StreamTypeService typeService = new StreamTypeServiceImpl(namesAndTypes, services.getEngineURI(), false, false);

        return deleteJoinExpr.getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService(), statementContext);
    }

    private List<Pair<Integer, String>> getExpressionProperties(ExprNode exprNode, boolean visitAggregateNodes)
    {
        ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(visitAggregateNodes);
        exprNode.accept(visitor);
        return visitor.getExprProperties();
    }
}
