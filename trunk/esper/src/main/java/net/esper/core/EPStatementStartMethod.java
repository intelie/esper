package net.esper.core;

import net.esper.client.EPStatementException;
import net.esper.collection.Pair;
import net.esper.eql.core.*;
import net.esper.eql.db.PollingViewableFactory;
import net.esper.eql.expression.ExprAggregateNode;
import net.esper.eql.expression.ExprEqualsNode;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.join.*;
import net.esper.eql.spec.*;
import net.esper.eql.view.FilterExprView;
import net.esper.eql.view.IStreamRStreamSelectorView;
import net.esper.eql.view.InternalRouteView;
import net.esper.eql.view.OutputProcessView;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.pattern.EvalRootNode;
import net.esper.pattern.PatternContext;
import net.esper.pattern.PatternMatchCallback;
import net.esper.pattern.PatternStopCallback;
import net.esper.schedule.ScheduleBucket;
import net.esper.util.StopCallback;
import net.esper.view.*;
import net.esper.view.internal.BufferView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Starts and provides the stop method for EQL statements.
 */
public class EPStatementStartMethod
{
    private final String statementId;
    private final String statementName;
    private final StatementSpecCompiled statementSpec;
    private final String eqlStatement;
    private final ScheduleBucket scheduleBucket;
    private final EPServicesContext services;
    private final StatementServiceContext statementContext;
    private final EPStatementHandle epStatementHandle;

    /**
     * Ctor.
     * @param statementId is the statement is assigned to the statement
     * @param statementName is the statement name assigned
     * @param statementSpec is a container for the definition of all statement constructs that
     * may have been used in the statement, i.e. if defines the select clauses, insert into, outer joins etc.
     * @param eqlStatement is the expression text
     * @param services is the service instances for dependency injection
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     */
    public EPStatementStartMethod(String statementId,
                                String statementName,
                                StatementSpecCompiled statementSpec,
                                String eqlStatement,
                                EPServicesContext services,
                                EPStatementHandle epStatementHandle)
    {
        this.statementId = statementId;
        this.statementName = statementName;
        this.statementSpec = statementSpec;
        this.services = services;
        this.eqlStatement = eqlStatement;
        this.epStatementHandle = epStatementHandle;

        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
        scheduleBucket = services.getSchedulingService().allocateBucket();

        statementContext = new StatementServiceContext(statementId, statementName, services.getSchedulingService(),
                scheduleBucket, services.getEventAdapterService(), epStatementHandle,
                services.getViewResolutionService(), services.getExtensionServicesContext(),
                new StatementStopServiceImpl());
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
        // Determine stream names for each stream - some streams may not have a name given
        String[] streamNames = determineStreamNames(statementSpec.getStreamSpecs());
        final boolean isJoin = statementSpec.getStreamSpecs().size() > 1;

        int numStreams = streamNames.length;
        final List<StopCallback> stopCallbacks = new LinkedList<StopCallback>();
        Viewable[] eventStreamParentViewable = new Viewable[numStreams];
        ViewFactoryChain[] unmaterializedViewChain = new ViewFactoryChain[numStreams];

        // Create streams and views
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpecCompiled streamSpec = statementSpec.getStreamSpecs().get(i);

            // Create view factories and parent view based on a filter specification
            if (streamSpec instanceof FilterStreamSpecCompiled)
            {
                FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
                eventStreamParentViewable[i] = services.getStreamService().createStream(filterStreamSpec.getFilterSpec(), services.getFilterService(), epStatementHandle, isJoin);
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
                final PatternContext patternContext = new PatternContext(services.getFilterService(), services.getSchedulingService(), scheduleBucket, services.getEventAdapterService(), epStatementHandle);

                PatternMatchCallback callback = new PatternMatchCallback() {
                    public void matchFound(Map<String, EventBean> matchEvent)
                    {
                        EventBean compositeEvent = patternContext.getEventAdapterService().adapterForCompositeEvent(eventType, matchEvent);
                        sourceEventStream.insert(compositeEvent);
                    }
                };

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
                HistoricalEventViewable historicalEventViewable = PollingViewableFactory.createDBStatementView(i, sqlStreamSpec, services.getDatabaseRefService(), services.getEventAdapterService(), epStatementHandle);
                unmaterializedViewChain[i] = new ViewFactoryChain(historicalEventViewable.getEventType(), new LinkedList<ViewFactory>());
                eventStreamParentViewable[i] = historicalEventViewable;
                stopCallbacks.add(historicalEventViewable);
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

        // create stop method
        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                statementContext.getStatementStopService().fireStatementStopped();

                for (StreamSpecCompiled streamSpec : statementSpec.getStreamSpecs())
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
            }
        };

        // Construct type information per stream
        StreamTypeService typeService = new StreamTypeServiceImpl(streamEventTypes, streamNames);
        ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(unmaterializedViewChain);

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
                services.getAutoImportService(),
                viewResourceDelegate);

        // Validate where-clause filter tree and outer join clause
        validateNodes(typeService, services.getAutoImportService(), viewResourceDelegate);

        // Materialize views
        Viewable[] streamViews = new Viewable[streamEventTypes.length];
        for (int i = 0; i < streamViews.length; i++)
        {
            streamViews[i] = services.getViewService().createViews(eventStreamParentViewable[i], unmaterializedViewChain[i].getViewFactoryChain(), statementContext);
        }

        // For just 1 event stream without joins, handle the one-table process separatly.
        Viewable finalView = null;
        if (streamNames.length == 1)
        {
            finalView = handleSimpleSelect(streamViews[0], optionalResultSetProcessor, statementContext);
        }
        else
        {
            finalView = handleJoin(streamNames, streamEventTypes, streamViews, optionalResultSetProcessor, statementSpec.getSelectStreamSelectorEnum(), epStatementHandle);
        }

        // Hook up internal event route for insert-into if required
        if (statementSpec.getInsertIntoDesc() != null)
        {
            InternalRouteView routeView = new InternalRouteView(statementSpec.getInsertIntoDesc().isIStream(), services.getInternalEventRouter());
            finalView.addView(routeView);
            finalView = routeView;
        }

        if (statementSpec.getSelectStreamSelectorEnum() != SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
        {
            IStreamRStreamSelectorView streamSelectorView = new IStreamRStreamSelectorView(statementSpec.getSelectStreamSelectorEnum());
            finalView.addView(streamSelectorView);
            finalView = streamSelectorView;
        }

        log.debug(".start Statement start completed");

        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
    }

    @SuppressWarnings({"ObjectAllocationInLoop"})
    private Viewable handleJoin(String[] streamNames,
                                EventType[] streamTypes,
                                Viewable[] streamViews,
                                ResultSetProcessor optionalResultSetProcessor,
                                SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
                                EPStatementHandle epStatementHandle)
            throws ExprValidationException
    {
        // Handle joins
        JoinSetComposer composer = JoinSetComposerFactory.makeComposer(statementSpec.getOuterJoinDescList(), statementSpec.getFilterRootNode(), streamTypes, streamNames, streamViews, selectStreamSelectorEnum);
        JoinSetFilter filter = new JoinSetFilter(statementSpec.getFilterRootNode());
        OutputProcessView indicatorView = new OutputProcessView(optionalResultSetProcessor, statementSpec.getStreamSpecs().size(), statementSpec.getOutputLimitSpec(), statementContext);

        // Create strategy for join execution
        JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView);

        // Hook up dispatchable with buffer and execution strategy
        JoinExecStrategyDispatchable joinStatementDispatch = new JoinExecStrategyDispatchable(execution, statementSpec.getStreamSpecs().size());
        epStatementHandle.setOptionalDispatchable(joinStatementDispatch);

        // Create buffer for each view. Point buffer to dispatchable for join.
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            BufferView buffer = new BufferView(i);
            streamViews[i].addView(buffer);
            buffer.setObserver(joinStatementDispatch);
        }

        return indicatorView;
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

    @SuppressWarnings({"StringContatenationInLoop"})
    private void validateNodes(StreamTypeService typeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate)
    {
        if (statementSpec.getFilterRootNode() != null)
        {
            ExprNode optionalFilterNode = statementSpec.getFilterRootNode();

            // Validate where clause, initializing nodes to the stream ids used
            try
            {
                optionalFilterNode = optionalFilterNode.getValidatedSubtree(typeService, autoImportService, viewResourceDelegate);
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
                throw new EPStatementException("Error validating expression: " + ex.getMessage(), eqlStatement);
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
                equalsNode = equalsNode.getValidatedSubtree(typeService, autoImportService, viewResourceDelegate);
            }
            catch (ExprValidationException ex)
            {
                log.debug("Validation exception for outer join node=" + equalsNode.toExpressionString(), ex);
                throw new EPStatementException("Error validating expression: " + ex.getMessage(), eqlStatement);
            }

            // Make sure we have left-hand-side and right-hand-side refering to different streams
            int streamIdLeft = outerJoinDesc.getLeftNode().getStreamId();
            int streamIdRight = outerJoinDesc.getRightNode().getStreamId();
            if (streamIdLeft == streamIdRight)
            {
                String message = "Outer join ON-clause cannot refer to properties of the same stream";
                throw new EPStatementException("Error validating expression: " + message, eqlStatement);
            }

            // Make sure one of the properties refers to the acutual stream currently being joined
            int expectedStreamJoined = outerJoinCount + 1;
            if ((streamIdLeft != expectedStreamJoined) && (streamIdRight != expectedStreamJoined))
            {
                String message = "Outer join ON-clause must refer to at least one property of the joined stream" +
                        " for stream " + expectedStreamJoined;
                throw new EPStatementException("Error validating expression: " + message, eqlStatement);
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
                throw new EPStatementException("Error validating expression: " + message, eqlStatement);
            }

        }
    }

    private Viewable handleSimpleSelect(Viewable view,
                                        ResultSetProcessor optionalResultSetProcessor,
                                        StatementServiceContext statementContext)
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
            OutputProcessView selectView = new OutputProcessView(optionalResultSetProcessor, statementSpec.getStreamSpecs().size(), statementSpec.getOutputLimitSpec(), statementContext);
            finalView.addView(selectView);
            finalView = selectView;
        }

        return finalView;
    }

    private static final Log log = LogFactory.getLog(EPStatementStartMethod.class);
}
