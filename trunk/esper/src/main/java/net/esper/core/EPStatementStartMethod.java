package net.esper.core;

import net.esper.client.EPStatementException;
import net.esper.collection.Pair;
import net.esper.collection.FlushedEventBuffer;
import net.esper.eql.core.*;
import net.esper.eql.db.PollingViewableFactory;
import net.esper.eql.expression.*;
import net.esper.eql.join.*;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.PropertyIndTableCoerceAdd;
import net.esper.eql.join.plan.QueryGraph;
import net.esper.eql.join.plan.FilterExprAnalyzer;
import net.esper.eql.spec.*;
import net.esper.eql.view.*;
import net.esper.eql.lookup.*;
import net.esper.eql.named.*;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.pattern.*;
import net.esper.util.StopCallback;
import net.esper.util.JavaClassHelper;
import net.esper.util.ManagedLock;
import net.esper.view.*;
import net.esper.view.internal.BufferView;
import net.esper.view.internal.BufferObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Starts and provides the stop method for EQL statements.
 */
public class EPStatementStartMethod
{
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
     * Starts the EQL statement.
     * @return a viewable to attach to for listening to events, and a stop method to invoke to clean up
     * @throws ExprValidationException when the expression validation fails
     * @throws ViewProcessingException when views cannot be started
     */
    public Pair<Viewable, EPStatementStopMethod> start()
        throws ExprValidationException, ViewProcessingException
    {
        // For named windows, register the window name and event type
        if (statementSpec.getCreateWindowDesc() != null)
        {
            String windowName = statementSpec.getCreateWindowDesc().getWindowName();
            FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) statementSpec.getStreamSpecs().get(0);
            EventType windowType = filterStreamSpec.getFilterSpec().getEventType();
            services.getNamedWindowService().addProcessor(windowName, windowType);
        }

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
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);

            // Create view factories and parent view based on a filter specification
            if (streamSpec instanceof FilterStreamSpecCompiled)
            {
                FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;

                // Since only for non-joins we get the existing stream's lock and try to reuse it's views
                Pair<EventStream, ManagedLock> streamLockPair = services.getStreamService().createStream(filterStreamSpec.getFilterSpec(),
                        services.getFilterService(), statementContext.getEpStatementHandle(), isJoin);
                eventStreamParentViewable[i] = streamLockPair.getFirst();

                // Use the re-used stream's lock for all this statement's locking needs
                if (streamLockPair.getSecond() != null)
                {
                    statementContext.getEpStatementHandle().setStatementLock(streamLockPair.getSecond());
                }
                
                unmaterializedViewChain[i] = services.getViewService().createFactories(i, eventStreamParentViewable[i].getEventType(), streamSpec.getViewSpecs(), statementContext);
            }
            // Create view factories and parent view based on a pattern expression
            else if (streamSpec instanceof PatternStreamSpecCompiled)
            {
                PatternStreamSpecCompiled patternStreamSpec = (PatternStreamSpecCompiled) streamSpec;
                final EventType eventType = services.getEventAdapterService().createAnonymousCompositeType(patternStreamSpec.getTaggedEventTypes());
                final EventStream sourceEventStream = new ZeroDepthStream(eventType);
                eventStreamParentViewable[i] = sourceEventStream;
                unmaterializedViewChain[i] = services.getViewService().createFactories(i, sourceEventStream.getEventType(), streamSpec.getViewSpecs(), statementContext);

                EvalRootNode rootNode = new EvalRootNode();
                rootNode.addChildNode(patternStreamSpec.getEvalNode());

                PatternMatchCallback callback = new PatternMatchCallback() {
                    public void matchFound(Map<String, EventBean> matchEvent)
                    {
                        EventBean compositeEvent = statementContext.getEventAdapterService().adapterForCompositeEvent(eventType, matchEvent);
                        sourceEventStream.insert(compositeEvent);
                    }
                };

                PatternContext patternContext = statementContext.getPatternContextFactory().createContext(statementContext,
                        i, rootNode);
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
                HistoricalEventViewable historicalEventViewable = PollingViewableFactory.createDBStatementView(i, sqlStreamSpec, services.getDatabaseRefService(), services.getEventAdapterService(), statementContext.getEpStatementHandle());
                unmaterializedViewChain[i] = new ViewFactoryChain(historicalEventViewable.getEventType(), new LinkedList<ViewFactory>());
                eventStreamParentViewable[i] = historicalEventViewable;
                stopCallbacks.add(historicalEventViewable);
            }
            else if (streamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) streamSpec;
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowConsumerView consumerView = processor.addConsumer(statementContext.getEpStatementHandle(), statementContext.getStatementStopService());
                eventStreamParentViewable[i] = consumerView;
                unmaterializedViewChain[i] = services.getViewService().createFactories(i, consumerView.getEventType(), namedSpec.getViewSpecs(), statementContext);

                // Consumers to named windows cannot declare a data window view onto the named window to avoid duplicate remove streams
                ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(unmaterializedViewChain, statementContext);
                viewResourceDelegate.requestCapability(i, new NotADataWindowViewCapability(), null);
            }
            else
            {
                throw new ExprValidationException("Unknown stream specification type: " + streamSpec);
            }
        }

        // Obtain event types from ViewFactoryChains
        EventType[] streamEventTypes = new EventType[statementSpec.getStreamSpecs().size()];
        for (int i = 0; i < unmaterializedViewChain.length; i++)
        {
            streamEventTypes[i] = unmaterializedViewChain[i].getEventType();
        }

        // Materialize sub-select views
        startSubSelect(subSelectStreamDesc, streamNames, streamEventTypes, stopCallbacks);

        // List of statement streams
        final List<StreamSpecCompiled> statementStreamSpecs = new ArrayList<StreamSpecCompiled>();
        statementStreamSpecs.addAll(statementSpec.getStreamSpecs());

        // Construct type information per stream
        StreamTypeService typeService = new StreamTypeServiceImpl(streamEventTypes, streamNames);
        ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(unmaterializedViewChain, statementContext);

        // For named windows, request remove stream handling from views, and set the top view to the index view
        if (statementSpec.getCreateWindowDesc() != null)
        {
            NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(statementSpec.getCreateWindowDesc().getWindowName());
            View rootView = processor.getRootView();
            eventStreamParentViewable[0].addView(rootView);
            eventStreamParentViewable[0] = rootView;

            // request remove stream capability from views
            if (!viewResourceDelegate.requestCapability(0, new RemoveStreamViewCapability(), null))
            {
                throw new ExprValidationException(NamedWindowService.ERROR_MSG_DATAWINDOWS);
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
                        services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), services.getFilterService(), isJoin);
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
                        services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), services.getFilterService(), isJoin);
                    }
                }

                if (statementSpec.getCreateWindowDesc() != null)
                {
                    String windowName = statementSpec.getCreateWindowDesc().getWindowName();
                    services.getNamedWindowService().removeProcessor(windowName);
                }
            }
        };

        // Validate views that require validation, specifically streams that don't have
        // sub-views such as DB SQL joins
        for (Viewable viewable : eventStreamParentViewable)
        {
            if (viewable instanceof ValidatedView)
            {
                ValidatedView validatedView = (ValidatedView) viewable;
                validatedView.validate(typeService);
            }
        }

        // Construct a processor for results posted by views and joins, which takes care of aggregation if required.
        // May return null if we don't need to post-process results posted by views or joins.
        ResultSetProcessor optionalResultSetProcessor = ResultSetProcessorFactory.getProcessor(
                statementSpec.getSelectClauseSpec(),
                statementSpec.getInsertIntoDesc(),
                statementSpec.getGroupByExpressions(),
                statementSpec.getHavingExprRootNode(),
                statementSpec.getOutputLimitSpec(),
                statementSpec.getOrderByList(),
                typeService,
                services.getEventAdapterService(),
                statementContext.getMethodResolutionService(),
                viewResourceDelegate,
                statementContext.getSchedulingService());

        // Validate where-clause filter tree and outer join clause
        validateNodes(typeService, statementContext.getMethodResolutionService(), viewResourceDelegate);

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
            finalView = handleSimpleSelect(streamViews[0], optionalResultSetProcessor, statementContext);
        }
        else
        {
            Pair<Viewable, JoinPreloadMethod> pair = handleJoin(streamNames, streamEventTypes, streamViews, optionalResultSetProcessor, statementSpec.getSelectStreamSelectorEnum(), statementContext, stopCallbacks);
            finalView = pair.getFirst();
            joinPreloadMethod = pair.getSecond();
        }

        // Replay any named window data, for later consumers of named data windows
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);
            if (streamSpec instanceof NamedWindowConsumerStreamSpec)
            {
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) streamSpec;
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowTailView consumerView = processor.getTailView();
                NamedWindowConsumerView view = (NamedWindowConsumerView) eventStreamParentViewable[i];

                // preload view for stream
                ArrayList<EventBean> eventsInWindow = new ArrayList<EventBean>();
                for(Iterator<EventBean> it = consumerView.iterator(); it.hasNext();)
                {
                    eventsInWindow.add(it.next());
                }
                EventBean[] newEvents = eventsInWindow.toArray(new EventBean[0]);
                view.update(newEvents, null);

                // in a join, preload indexes, if any
                if (joinPreloadMethod != null)
                {
                    joinPreloadMethod.preloadFromBuffer(i);
                }
            }
        }

        // Hook up internal event route for insert-into if required
        if (statementSpec.getInsertIntoDesc() != null)
        {
            InternalRouteView routeView = new InternalRouteView(statementSpec.getInsertIntoDesc().isIStream(), services.getInternalEventRouter(), statementContext.getEpStatementHandle());
            finalView.addView(routeView);
            finalView = routeView;
        }

        if (statementSpec.getSelectStreamSelectorEnum() != SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
        {
            IStreamRStreamSelectorView streamSelectorView = new IStreamRStreamSelectorView(statementSpec.getSelectStreamSelectorEnum());
            finalView.addView(streamSelectorView);
            finalView = streamSelectorView;
        }

        if (statementSpec.getCreateWindowDesc() != null)
        {
            NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(statementSpec.getCreateWindowDesc().getWindowName());
            NamedWindowTailView tailView = processor.getTailView();
            finalView.addView(tailView);
            finalView = tailView;
        }

        if (statementSpec.getOnDeleteDesc() != null)
        {
            OnDeleteDesc onDeleteDesc = statementSpec.getOnDeleteDesc();
            NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(onDeleteDesc.getWindowName());

            // validate join expression
            EventType namedWindowType = processor.getNamedWindowType();
            FilterStreamSpecCompiled streamSpec = (FilterStreamSpecCompiled) statementSpec.getStreamSpecs().get(0);
            ExprNode validatedJoin = validateJoinNamedWindow(statementSpec.getOnDeleteDesc().getJoinExpr(),
                    namedWindowType, onDeleteDesc.getOptionalAsName(),
                    streamSpec.getFilterSpec().getEventType(), streamSpec.getOptionalStreamName());
            onDeleteDesc.setJoinExpr(validatedJoin);

            NamedWindowDeleteView deleteView = processor.addDeleter(onDeleteDesc, streamSpec.getFilterSpec().getEventType(), statementContext.getStatementStopService());
            finalView.addView(deleteView);
            finalView = deleteView;
        }

        log.debug(".start Statement start completed");

        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
    }

    private Pair<Viewable, JoinPreloadMethod> handleJoin(String[] streamNames,
                                EventType[] streamTypes,
                                Viewable[] streamViews,
                                ResultSetProcessor optionalResultSetProcessor,
                                SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
                                StatementContext statementContext,
                                List<StopCallback> stopCallbacks)
            throws ExprValidationException
    {
        // Handle joins
        final JoinSetComposer composer = statementContext.getJoinSetComposerFactory().makeComposer(statementSpec.getOuterJoinDescList(), statementSpec.getFilterRootNode(), streamTypes, streamNames, streamViews, selectStreamSelectorEnum);

        stopCallbacks.add(new StopCallback(){
            public void stop()
            {
                composer.destroy();
            }
        });
        
        JoinSetFilter filter = new JoinSetFilter(statementSpec.getFilterRootNode());
        OutputProcessView indicatorView = OutputProcessViewFactory.makeView(optionalResultSetProcessor, statementSpec.getStreamSpecs().size(), statementSpec.getOutputLimitSpec(), statementContext);

        // Create strategy for join execution
        JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView);

        // The view needs a reference to the join execution to pull iterator values
        indicatorView.setJoinExecutionStrategy(execution);

        // Hook up dispatchable with buffer and execution strategy
        JoinExecStrategyDispatchable joinStatementDispatch = new JoinExecStrategyDispatchable(execution, statementSpec.getStreamSpecs().size());
        statementContext.getEpStatementHandle().setOptionalDispatchable(joinStatementDispatch);

        JoinPreloadMethodImpl preloadMethod = new JoinPreloadMethodImpl(streamNames.length, composer);

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
            if ((streamNames[i] == null) && (streams.size() > 1))
            {
                streamNames[i] = "stream_" + i;
            }
        }
        return streamNames;
    }

    private void validateNodes(StreamTypeService typeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
    {
        if (statementSpec.getFilterRootNode() != null)
        {
            ExprNode optionalFilterNode = statementSpec.getFilterRootNode();

            // Validate where clause, initializing nodes to the stream ids used
            try
            {
                optionalFilterNode = optionalFilterNode.getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, statementContext.getSchedulingService());
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

        for (int outerJoinCount = 0; outerJoinCount < statementSpec.getOuterJoinDescList().size(); outerJoinCount++)
        {
            OuterJoinDesc outerJoinDesc = statementSpec.getOuterJoinDescList().get(outerJoinCount);

            // Validate the outer join clause using an artificial equals-node on top.
            // Thus types are checked via equals.
            // Sets stream ids used for validated nodes.
            ExprNode equalsNode = new ExprEqualsNode(false);
            equalsNode.addChildNode(outerJoinDesc.getLeftNode());
            equalsNode.addChildNode(outerJoinDesc.getRightNode());
            try
            {
                equalsNode = equalsNode.getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, statementContext.getSchedulingService());
            }
            catch (ExprValidationException ex)
            {
                log.debug("Validation exception for outer join node=" + equalsNode.toExpressionString(), ex);
                throw new EPStatementException("Error validating expression: " + ex.getMessage(), statementContext.getExpression());
            }

            // Make sure we have left-hand-side and right-hand-side refering to different streams
            int streamIdLeft = outerJoinDesc.getLeftNode().getStreamId();
            int streamIdRight = outerJoinDesc.getRightNode().getStreamId();
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
                badPropertyName = outerJoinDesc.getLeftNode().getResolvedPropertyName();
            }
            if (streamIdRight > outerJoinCount + 1)
            {
                badPropertyName = outerJoinDesc.getRightNode().getResolvedPropertyName();
            }
            if (badPropertyName != null)
            {
                String message = "Outer join ON-clause invalid scope for property" +
                        " '" + badPropertyName + "', expecting the current or a prior stream scope";
                throw new EPStatementException("Error validating expression: " + message, statementContext.getExpression());
            }

        }
    }

    private Viewable handleSimpleSelect(Viewable view,
                                        ResultSetProcessor optionalResultSetProcessor,
                                        StatementContext statementContext)
    {
        Viewable finalView = view;

        // Add filter view that evaluates the filter expression
        if (statementSpec.getFilterRootNode() != null)
        {
            FilterExprView filterView = new FilterExprView(statementSpec.getFilterRootNode());
            finalView.addView(filterView);
            finalView = filterView;
        }

        // Add select expression view if there is any
       if (optionalResultSetProcessor != null || statementSpec.getOutputLimitSpec() != null)
        {
            OutputProcessView selectView = OutputProcessViewFactory.makeView(optionalResultSetProcessor, statementSpec.getStreamSpecs().size(), statementSpec.getOutputLimitSpec(), statementContext);
            finalView.addView(selectView);
            finalView = selectView;
        }

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
            SelectClauseSpec selectClauseSpec = statementSpec.getSelectClauseSpec();

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
                Pair<EventStream, ManagedLock> streamLockPair = services.getStreamService().createStream(filterStreamSpec.getFilterSpec(),
                        services.getFilterService(), statementContext.getEpStatementHandle(), isJoin);
                Viewable viewable = streamLockPair.getFirst();
                ViewFactoryChain viewFactoryChain = services.getViewService().createFactories(subselectStreamNumber, viewable.getEventType(), filterStreamSpec.getViewSpecs(), statementContext);
                subselect.setRawEventType(viewFactoryChain.getEventType());

                // Add lookup to list, for later starts
                subSelectStreamDesc.add(subselect, subselectStreamNumber, viewable, viewFactoryChain);
            }
            else
            {
                NamedWindowConsumerStreamSpec namedSpec = (NamedWindowConsumerStreamSpec) statementSpec.getStreamSpecs().get(0);
                NamedWindowProcessor processor = services.getNamedWindowService().getProcessor(namedSpec.getWindowName());
                NamedWindowConsumerView consumerView = processor.addConsumer(statementContext.getEpStatementHandle(), statementContext.getStatementStopService());
                ViewFactoryChain viewFactoryChain = services.getViewService().createFactories(0, consumerView.getEventType(), namedSpec.getViewSpecs(), statementContext);
                subSelectStreamDesc.add(subselect, subselectStreamNumber, consumerView, viewFactoryChain);
            }

            // no aggregation functions allowed in select
            if (selectClauseSpec.getSelectList().size() > 0)
            {
                ExprNode selectExpression = selectClauseSpec.getSelectList().get(0).getSelectExpression();
                List<ExprAggregateNode> aggExprNodes = new LinkedList<ExprAggregateNode>();
                ExprAggregateNode.getAggregatesBottomUp(selectExpression, aggExprNodes);
                if (aggExprNodes.size() > 0)
                {
                    throw new ExprValidationException("Aggregation functions are not supported within subqueries, consider using insert-into instead");
                }
            }

            // no aggregation functions allowed in filter
            if (statementSpec.getFilterRootNode() != null)
            {
                List<ExprAggregateNode> aggExprNodes = new LinkedList<ExprAggregateNode>();
                ExprAggregateNode.getAggregatesBottomUp(statementSpec.getFilterRootNode(), aggExprNodes);
                if (aggExprNodes.size() > 0)
                {
                    throw new ExprValidationException("Aggregation functions are not supported within subqueries, consider using insert-into instead");
                }
            }
        }

        return subSelectStreamDesc;
    }

    private void startSubSelect(SubSelectStreamCollection subSelectStreamDesc, String[] outerStreamNames, EventType outerEventTypes[], List<StopCallback> stopCallbacks)
            throws ExprValidationException
    {
        for (ExprSubselectNode subselect : statementSpec.getSubSelectExpressions())
        {
            StatementSpecCompiled statementSpec = subselect.getStatementSpecCompiled();
            StreamSpecCompiled filterStreamSpec = statementSpec.getStreamSpecs().get(0);
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
            LinkedHashMap<String, EventType> namesAndTypes = new LinkedHashMap<String, EventType>();
            namesAndTypes.put(subexpressionStreamName, eventType);
            for (int i = 0; i < outerEventTypes.length; i++)
            {
                namesAndTypes.put(outerStreamNames[i], outerEventTypes[i]);
            }
            StreamTypeService subselectTypeService = new StreamTypeServiceImpl(namesAndTypes, true, true);
            ViewResourceDelegate viewResourceDelegateSubselect = new ViewResourceDelegateImpl(new ViewFactoryChain[] {viewFactoryChain}, statementContext);

            // Validate select expression
            SelectClauseSpec selectClauseSpec = subselect.getStatementSpecCompiled().getSelectClauseSpec();
            if (selectClauseSpec.getSelectList().size() > 0)
            {
                ExprNode selectExpression = selectClauseSpec.getSelectList().get(0).getSelectExpression();
                selectExpression = selectExpression.getValidatedSubtree(subselectTypeService, statementContext.getMethodResolutionService(), viewResourceDelegateSubselect, statementContext.getSchedulingService());
                subselect.setSelectClause(selectExpression);
                subselect.setSelectAsName(selectClauseSpec.getSelectList().get(0).getOptionalAsName());
            }

            // Validate filter expression, if there is one
            ExprNode filterExpr = statementSpec.getFilterRootNode();
            if (filterExpr != null)
            {
                filterExpr = filterExpr.getValidatedSubtree(subselectTypeService, statementContext.getMethodResolutionService(), viewResourceDelegateSubselect, statementContext.getSchedulingService());
                if (JavaClassHelper.getBoxedType(filterExpr.getType()) != Boolean.class)
                {
                    throw new ExprValidationException("Subselect filter expression must return a boolean value");
                }
                subselect.setFilterExpr(filterExpr);
            }

            // Finally create views
            Viewable viewableRoot = subSelectStreamDesc.getRootViewable(subselect);
            Viewable subselectView = services.getViewService().createViews(viewableRoot, viewFactoryChain.getViewFactoryChain(), statementContext);

            // Determine indexing of the filter expression
            Pair<EventTable, TableLookupStrategy> indexPair = determineSubqueryIndex(filterExpr, eventType,
                    outerEventTypes, subselectTypeService);
            subselect.setStrategy(indexPair.getSecond());
            final EventTable eventIndex = indexPair.getFirst();

            // Clear out index on statement stop
            stopCallbacks.add(new StopCallback() {
                public void stop()
                {
                    eventIndex.clear();
                }
            });

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
                EventBean[] newEvents = eventsInWindow.toArray(new EventBean[0]);
                ((View)viewableRoot).update(newEvents, null); // fill view
                eventIndex.add(newEvents);  // fill index
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
                    eventIndex.add(preloadEvents.toArray(new EventBean[0]));
                }
            }

            // hook up subselect viewable and event table
            BufferView bufferView = new BufferView(subselectStreamNumber);
            bufferView.setObserver(new BufferObserver() {
                public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
                {
                    eventIndex.add(newEventBuffer.getAndFlush());
                    eventIndex.remove(oldEventBuffer.getAndFlush());
                }

            });
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
            String indexedProps[] = joinProps.keySet().toArray(new String[0]);
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
                                         String optNamedWindowStreamName,
                                         EventType filteredType,
                                         String optFilterStreamName) throws ExprValidationException
    {
        if (deleteJoinExpr == null)
        {
            return null;
        }
        
        String namedWindowStreamName = optNamedWindowStreamName;
        if (namedWindowStreamName == null)
        {
            namedWindowStreamName = "stream_0";
        }

        String filterStreamName = optFilterStreamName;
        if (filterStreamName == null)
        {
            filterStreamName = "stream_1";
        }

        LinkedHashMap<String, EventType> namesAndTypes = new LinkedHashMap<String, EventType>();
        namesAndTypes.put(namedWindowStreamName, namedWindowType);
        namesAndTypes.put(filterStreamName, filteredType);
        StreamTypeService typeService = new StreamTypeServiceImpl(namesAndTypes, false, false);

        return deleteJoinExpr.getValidatedSubtree(typeService, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService());
    }

    private static final Log log = LogFactory.getLog(EPStatementStartMethod.class);
}
