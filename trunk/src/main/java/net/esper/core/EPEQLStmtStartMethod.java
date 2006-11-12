package net.esper.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.esper.client.EPStatementException;
import net.esper.collection.Pair;
import net.esper.eql.expression.*;
import net.esper.eql.join.JoinExecStrategyDispatchable;
import net.esper.eql.join.JoinExecutionStrategy;
import net.esper.eql.join.JoinExecutionStrategyImpl;
import net.esper.eql.join.JoinSetComposer;
import net.esper.eql.join.JoinSetComposerFactory;
import net.esper.eql.join.JoinSetFilter;
import net.esper.eql.view.FilterExprView;
import net.esper.eql.view.OutputProcessView;
import net.esper.eql.view.InternalRouteView;
import net.esper.eql.view.IStreamRStreamSelectorView;
import net.esper.eql.spec.*;
import net.esper.eql.core.*;
import net.esper.eql.db.PollingViewableFactory;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.view.*;
import net.esper.view.internal.BufferView;
import net.esper.schedule.ScheduleBucket;
import net.esper.pattern.PatternContext;
import net.esper.pattern.PatternStopCallback;
import net.esper.pattern.EvalRootNode;
import net.esper.pattern.PatternMatchCallback;
import net.esper.util.StopCallback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Starts and provides the stop method for EQL statements.
 */
public class EPEQLStmtStartMethod
{
    private final StatementSpec statementSpec;
    private final String eqlStatement;
    private final ScheduleBucket scheduleBucket;
    private final EPServicesContext services;
    private final ViewServiceContext viewContext;

    /**
     * Ctor.
     * @param statementSpec is a container for the definition of all statement constructs that
     * may have been used in the statement, i.e. if defines the select clauses, insert into, outer joins etc.
     * @param eqlStatement is the expression text
     * @param services is the service instances for dependency injection
     */
    public EPEQLStmtStartMethod(StatementSpec statementSpec,
                                String eqlStatement,
                                EPServicesContext services)
    {
        this.statementSpec = statementSpec;
        this.services = services;
        this.eqlStatement = eqlStatement;

        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
        scheduleBucket = services.getSchedulingService().allocateBucket();

        viewContext = new ViewServiceContext(services.getSchedulingService(), scheduleBucket, services.getEventAdapterService());
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
        EventType[] streamTypes = new EventType[statementSpec.getStreamSpecs().size()];
        Viewable[] streamViews = new Viewable[streamTypes.length];
        final List<StopCallback> stopCallbacks = new LinkedList<StopCallback>();

        // Create streams and views
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            StreamSpec streamSpec = statementSpec.getStreamSpecs().get(i);

            // Create stream based on a filter specification
            if (streamSpec instanceof FilterStreamSpec)
            {
                FilterStreamSpec filterStreamSpec = (FilterStreamSpec) streamSpec;
                EventStream eventStream = services.getStreamService().createStream(filterStreamSpec.getFilterSpec(), services.getFilterService());

                // Cascade views onto the (filter or pattern) stream
                streamViews[i] = services.getViewService().createView(eventStream, streamSpec.getViewSpecs(), viewContext);
            }
            // Create stream based on a pattern expression
            else if (streamSpec instanceof PatternStreamSpec)
            {
                PatternStreamSpec patternStreamSpec = (PatternStreamSpec) streamSpec;
                final EventType eventType = services.getEventAdapterService().createAnonymousCompositeType(patternStreamSpec.getTaggedEventTypes());
                final EventStream sourceEventStream = new ZeroDepthStream(eventType);

                // Cascade views onto the (filter or pattern) stream
                streamViews[i] = services.getViewService().createView(sourceEventStream, streamSpec.getViewSpecs(), viewContext);

                EvalRootNode rootNode = new EvalRootNode();
                rootNode.addChildNode(patternStreamSpec.getEvalNode());
                final PatternContext patternContext = new PatternContext(services.getFilterService(), services.getSchedulingService(), scheduleBucket, services.getEventAdapterService());

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
            else if (streamSpec instanceof DBStatementStreamSpec)
            {
                DBStatementStreamSpec sqlStreamSpec = (DBStatementStreamSpec) streamSpec;
                HistoricalEventViewable historicalEventViewable = PollingViewableFactory.createDBStatementView(i, sqlStreamSpec, services.getDatabaseRefService(), services.getEventAdapterService());
                streamViews[i] = historicalEventViewable;
                if (streamSpec.getViewSpecs().size() > 0)
                {
                    throw new ExprValidationException("Historical data joins do not allow views onto the data, view '"
                            + streamSpec.getViewSpecs().get(0).getObjectNamespace() + ":" + streamSpec.getViewSpecs().get(0).getObjectName() + "' is not valid in this context");
                }
                stopCallbacks.add(historicalEventViewable);
            }
            else
            {
                throw new ExprValidationException("Unknown stream specification");
            }

            streamTypes[i] = streamViews[i].getEventType();
        }

        // create stop method
        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                for (StreamSpec streamSpec : statementSpec.getStreamSpecs())
                {
                    if (streamSpec instanceof FilterStreamSpec)
                    {
                        FilterStreamSpec filterStreamSpec = (FilterStreamSpec) streamSpec;
                        services.getStreamService().dropStream(filterStreamSpec.getFilterSpec(), services.getFilterService());
                    }
                }
                for (StopCallback stopCallback : stopCallbacks)
                {
                    stopCallback.stop();
                }
            }
        };

        // Construct type information per stream
        StreamTypeService typeService = new StreamTypeServiceImpl(streamTypes, streamNames);

        // Validate any views that require validation
        for (Viewable viewable : streamViews)
        {
            if (viewable instanceof ValidatedView)
            {
                ValidatedView validatedView = (ValidatedView) viewable;
                validatedView.validate(typeService);
            }
        }

        // Get the service for resolving class names 
        AutoImportService autoImportService = services.getAutoImportService();
        
        // Construct a processor for results posted by views and joins, which takes care of aggregation if required.
        // May return null if we don't need to post-process results posted by views or joins.
        ResultSetProcessor optionalResultSetProcessor = ResultSetProcessorFactory.getProcessor(
                statementSpec.getSelectListExpressions(),
                statementSpec.getInsertIntoDesc(),
                statementSpec.getGroupByExpressions(),
                statementSpec.getHavingExprRootNode(),
                statementSpec.getOutputLimitSpec(),
                statementSpec.getOrderByList(),
                typeService,
                services.getEventAdapterService(),
                autoImportService);

        // Validate where-clause filter tree and outer join clause
        validateNodes(typeService, autoImportService);

        // For just 1 event stream without joins, handle the one-table process separatly.
        Viewable finalView = null;
        if (streamNames.length == 1)
        {
            finalView = handleSimpleSelect(streamViews[0], optionalResultSetProcessor, viewContext);
        }
        else
        {
            finalView = handleJoin(streamNames, streamTypes, streamViews, optionalResultSetProcessor, statementSpec.getSelectStreamSelectorEnum());
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

        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
    }

    private Viewable handleJoin(String[] streamNames,
                                EventType[] streamTypes,
                                Viewable[] streamViews,
                                ResultSetProcessor optionalResultSetProcessor,
                                SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
            throws ExprValidationException
    {
        // Handle joins
        JoinSetComposer composer = JoinSetComposerFactory.makeComposer(statementSpec.getOuterJoinDescList(), statementSpec.getFilterRootNode(), streamTypes, streamNames, streamViews, selectStreamSelectorEnum);
        JoinSetFilter filter = new JoinSetFilter(statementSpec.getFilterRootNode());
        OutputProcessView indicatorView = new OutputProcessView(optionalResultSetProcessor, statementSpec.getStreamSpecs().size(), statementSpec.getOutputLimitSpec(), viewContext);

        // Create strategy for join execution
        JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView);

        // Hook up dispatchable with buffer and execution strategy
        JoinExecStrategyDispatchable dispatchable = new JoinExecStrategyDispatchable(services.getDispatchService(), execution, statementSpec.getStreamSpecs().size());

        // Create buffer for each view. Point buffer to dispatchable for join.
        for (int i = 0; i < statementSpec.getStreamSpecs().size(); i++)
        {
            BufferView buffer = new BufferView(i);
            streamViews[i].addView(buffer);
            buffer.setObserver(dispatchable);
        }

        return indicatorView;
    }

    /**
     * Returns a stream name assigned for each stream, generated if none was supplied.
     * @param streams - stream specifications
     * @return array of stream names
     */
    protected static String[] determineStreamNames(List<StreamSpec> streams)
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

    private void validateNodes(StreamTypeService typeService, AutoImportService autoImportService)
    {
        if (statementSpec.getFilterRootNode() != null)
        {
            ExprNode optionalFilterNode = statementSpec.getFilterRootNode();

            // Validate where clause, initializing nodes to the stream ids used
            try
            {
                optionalFilterNode = optionalFilterNode.getValidatedSubtree(typeService, autoImportService);
                statementSpec.setFilterExprRootNode(optionalFilterNode);

                // Make sure there is no aggregation in the where clause
                List<ExprAggregateNode> aggregateNodes = new LinkedList<ExprAggregateNode>();
                ExprAggregateNode.getAggregatesBottomUp(optionalFilterNode, aggregateNodes);
                if (aggregateNodes.size() > 0)
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
                equalsNode = equalsNode.getValidatedSubtree(typeService, autoImportService);
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
                                        ViewServiceContext viewContext)
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
            OutputProcessView selectView = new OutputProcessView(optionalResultSetProcessor, statementSpec.getStreamSpecs().size(), statementSpec.getOutputLimitSpec(), viewContext);
            finalView.addView(selectView);
            finalView = selectView;
        }

        return finalView;
    }

    private static final Log log = LogFactory.getLog(EPEQLStmtStartMethod.class);
}
