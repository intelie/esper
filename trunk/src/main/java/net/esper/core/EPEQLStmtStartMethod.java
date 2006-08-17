package net.esper.core;

import java.util.LinkedList;
import java.util.List;

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
import net.esper.event.EventType;
import net.esper.view.EventStream;
import net.esper.view.View;
import net.esper.view.ViewProcessingException;
import net.esper.view.ViewServiceContext;
import net.esper.view.Viewable;
import net.esper.view.internal.BufferView;
import net.esper.schedule.ScheduleBucket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Starts and provides the stop method for EQL statements.
 */
public class EPEQLStmtStartMethod
{
    private InsertIntoDesc optionalInsertIntoDesc;
    private List<SelectExprElement> selectionList;
    private List<StreamSpec> streams;
    private ExprNode optionalFilterNode;
    private ExprNode optionalHavingNode;
    private OutputLimitSpec optionalOutputLimitSpec;
    private List<OuterJoinDesc> outerJoinDescList;
    private List<ExprNode> groupByNodes;
    private List<Pair<ExprNode, Boolean>> orderByNodes;
    private String eqlStatement;
    private EPServicesContext services;
    private ScheduleBucket scheduleBucket;

    /**
     * Ctor.
     * @param insertIntoDesc describes the insert-into information supplied, or null if no insert into defined
     * @param selectionList describes the list of selected fields, empty list if wildcarded (SELECT-clause)
     * @param streams is a definition of the event streams (FROM-clause)
     * @param outerJoinDescList is a list of outer join descriptors indicating join type and properties (OUTER-JOIN clauses)
     * @param optionalFilterNode is filter conditions that result sets must meet (WHERE clause)
     * @param groupByNodes is a list of expressions that represent the grouping criteria in a group by clause,
     *        empty list if none supplied (GROUP BY)
     * @param optionalHavingNode is filter conditions that grouped-by results must meet (HAVING clause)
     * @param optionalOutputLimitViewSpecs is a list of the output rate limiting views,
     *        empty list if none supplied (OUTPUT clause)
     * @param orderByNodes is the order-by expression nodes
     * @param eqlStatement is the expression text
     * @param services is the service instances for dependency injection
     */
    public EPEQLStmtStartMethod(InsertIntoDesc insertIntoDesc,
                                List<SelectExprElement> selectionList,
                                List<StreamSpec> streams,
                                List<OuterJoinDesc> outerJoinDescList,
                                ExprNode optionalFilterNode,
                                List<ExprNode> groupByNodes,
                                ExprNode optionalHavingNode,
                                OutputLimitSpec optionalOutputLimitViewSpecs,
                                List<Pair<ExprNode, Boolean>> orderByNodes,
                                String eqlStatement,
                                EPServicesContext services)
    {
        this.optionalInsertIntoDesc = insertIntoDesc;
        this.selectionList = selectionList;
        this.streams = streams;
        this.outerJoinDescList = outerJoinDescList;
        this.optionalFilterNode = optionalFilterNode;
        this.groupByNodes = groupByNodes;
        this.optionalHavingNode = optionalHavingNode;
        this.optionalOutputLimitSpec = optionalOutputLimitViewSpecs;
        this.orderByNodes = orderByNodes;
        this.services = services;
        this.eqlStatement = eqlStatement;

        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
        scheduleBucket = services.getSchedulingService().allocateBucket();
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
        ViewServiceContext viewContext = new ViewServiceContext(services.getSchedulingService(), scheduleBucket, services.getEventAdapterService());

        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                for (StreamSpec streamSpec : streams)
                {
                    services.getStreamService().dropStream(streamSpec.getFilterSpec(), services.getFilterService());
                }
            }
        };

        // Determine stream names for each stream - some streams may not have a name given
        String[] streamNames = determineStreamNames(streams);
        EventType[] streamTypes = new EventType[streams.size()];
        View[] streamViews = new View[streams.size()];

        // Create streams and views
        for (int i = 0; i < streams.size(); i++)
        {
            EventStream eventStream = services.getStreamService().createStream(streams.get(i).getFilterSpec(), services.getFilterService());
            streamViews[i] = services.getViewService().createView(eventStream, streams.get(i).getViewSpecs(), viewContext);
            streamTypes[i] = streamViews[i].getEventType();
        }

        // Construct type information per stream
        StreamTypeService typeService = new StreamTypeServiceImpl(streamTypes, streamNames);

        // Get the service for resolving class names 
        AutoImportService autoImportService = services.getAutoImportService();
        
        // Construct a processor for results posted by views and joins, which takes care of aggregation if required.
        // May return null if we don't need to post-process results posted by views or joins.
        ResultSetProcessor optionalResultSetProcessor = ResultSetProcessorFactory.getProcessor(selectionList,
                optionalInsertIntoDesc,
                groupByNodes,
                optionalHavingNode,
                optionalOutputLimitSpec,
                orderByNodes,
                typeService,
                services.getEventAdapterService(),
                autoImportService);

        // Validate where-clause filter tree and outer join clause
        validateNodes(typeService, autoImportService);

        // For just 1 event stream without joins, handle the one-table process separatly.
        if (streams.size() == 1)
        {
            Viewable finalView = handleSimpleSelect(streamViews[0], optionalResultSetProcessor, optionalInsertIntoDesc, viewContext);

            return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
        }

        // Handle joins
        JoinSetComposer composer = JoinSetComposerFactory.makeComposer(outerJoinDescList, optionalFilterNode, streamTypes, streamNames);
        JoinSetFilter filter = new JoinSetFilter(optionalFilterNode);
        OutputProcessView indicatorView = new OutputProcessView(optionalResultSetProcessor, streams.size(), optionalOutputLimitSpec, viewContext);

        // Create strategy for join execution
        JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView);

        // Hook up dispatchable with buffer and execution strategy
        JoinExecStrategyDispatchable dispatchable = new JoinExecStrategyDispatchable(services.getDispatchService(), execution, streams.size());

        // Create buffer for each view. Point buffer to dispatchable for join.
        for (int i = 0; i < streams.size(); i++)
        {
            BufferView buffer = new BufferView(i);
            streamViews[i].addView(buffer);
            buffer.setObserver(dispatchable);
        }

        // Hook up internal event route for insert-into if required
        View finalView = indicatorView;
        if (optionalInsertIntoDesc != null)
        {
            InternalRouteView routeView = new InternalRouteView(optionalInsertIntoDesc.isIStream(), services.getInternalEventRouter());
            finalView.addView(routeView);
            finalView = routeView;
        }

        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
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
        if (optionalFilterNode != null)
        {
            // Validate where clause, initializing nodes to the stream ids used
            try
            {
                optionalFilterNode = optionalFilterNode.getValidatedSubtree(typeService, autoImportService);

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

        for (int outerJoinCount = 0; outerJoinCount < outerJoinDescList.size(); outerJoinCount++)
        {
            OuterJoinDesc outerJoinDesc = outerJoinDescList.get(outerJoinCount);

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
                String message = "Outer join ON-clause must cannot refer to properties of the same stream";
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

    private Viewable handleSimpleSelect(View view,
                                        ResultSetProcessor optionalResultSetProcessor,
                                        InsertIntoDesc insertIntoDesc,
                                        ViewServiceContext viewContext)
    {
        View finalView = view;

        // Add filter view that evaluates the filter expression
        if (optionalFilterNode != null)
        {
            FilterExprView filterView = new FilterExprView(optionalFilterNode);
            finalView.addView(filterView);
            finalView = filterView;
        }

        // Add select expression view if there is any
       if (optionalResultSetProcessor != null || optionalOutputLimitSpec != null)
        {
            OutputProcessView selectView = new OutputProcessView(optionalResultSetProcessor, streams.size(), optionalOutputLimitSpec, viewContext);
            finalView.addView(selectView);
            finalView = selectView;
        }

        // Add insert-into view for internal event routing if required
        if (insertIntoDesc != null)
        {
            InternalRouteView routeView = new InternalRouteView(insertIntoDesc.isIStream(), services.getInternalEventRouter());
            finalView.addView(routeView);
            finalView = routeView;
        }

        return finalView;
    }

    private static final Log log = LogFactory.getLog(EPEQLStmtStartMethod.class);
}
