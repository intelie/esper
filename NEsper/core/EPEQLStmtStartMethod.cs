using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.collection;
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.eql.expression;
using net.esper.eql.join;
using net.esper.eql.spec;
using net.esper.eql.view;
using net.esper.events;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.util;
using net.esper.view;
using net.esper.view.internal_Renamed;

using org.apache.commons.logging;

namespace net.esper.core
{
    /// <summary>
    /// Starts and provides the Stop method for EQL statements.
    /// </summary>

    public class EPEQLStmtStartMethod
    {
        private readonly StatementSpec statementSpec;
        private readonly String eqlStatement;
        private readonly ScheduleBucket scheduleBucket;
        private readonly EPServicesContext services;
        private readonly ViewServiceContext viewContext;

        /// <summary> Ctor.</summary>
        /// <param name="statementSpec">
        /// is a container for the definition of all statement constructs that
        /// may have been used in the statement, i.e. if defines the select clauses,
        /// insert into, outer joins etc.
        /// </param>
        /// <param name="eqlStatement">is the expression text</param>
        /// <param name="services">is the service instances for dependency injection</param>

        public EPEQLStmtStartMethod(StatementSpec statementSpec, String eqlStatement, EPServicesContext services)
        {
            this.statementSpec = statementSpec;
            this.services = services;
            this.eqlStatement = eqlStatement;

            // Allocate the statement's schedule bucket which stays constant over it's lifetime.
            // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
            scheduleBucket = services.SchedulingService.allocateBucket();

            viewContext = new ViewServiceContext(services.SchedulingService, scheduleBucket, services.EventAdapterService);
        }

        /// <summary> Starts the EQL statement.</summary>
        /// <returns> a viewable to attach to for listening to events, and a Stop method to invoke to clean up
        /// </returns>
        /// <throws>  ExprValidationException when the expression validation fails </throws>
        /// <throws>  ViewProcessingException when views cannot be Started </throws>

        public Pair<Viewable, EPStatementStopMethod> Start()
        {
            // Determine stream names for each stream - some streams may not have a name given
            String[] streamNames = determineStreamNames(statementSpec.StreamSpecs);
            EventType[] streamTypes = new EventType[statementSpec.StreamSpecs.Count];
            Viewable[] streamViews = new Viewable[streamTypes.Length];
            IList<StopCallback> stopCallbacks = new List<StopCallback>();

            // Create streams and views
            for (int i = 0; i < statementSpec.StreamSpecs.Count; i++)
            {
            	StreamSpec streamSpec = statementSpec.StreamSpecs[i];

                // Create stream based on a filter specification
                if (streamSpec is FilterStreamSpec)
                {
                    FilterStreamSpec filterStreamSpec = (FilterStreamSpec)streamSpec;
                    EventStream eventStream = services.StreamService.createStream(filterStreamSpec.FilterSpec, services.FilterService);

                    // Cascade views onto the (filter or pattern) stream
                    streamViews[i] = services.ViewService.CreateView(eventStream, streamSpec.ViewSpecs, viewContext);
                }
                // Create stream based on a pattern expression
                else if (streamSpec is PatternStreamSpec)
                {
                    PatternStreamSpec patternStreamSpec = (PatternStreamSpec)streamSpec;
                    EventType eventType = services.EventAdapterService.CreateAnonymousCompositeType(patternStreamSpec.TaggedEventTypes);
                    EventStream sourceEventStream = new ZeroDepthStream(eventType);

                    // Cascade views onto the (filter or pattern) stream
                    streamViews[i] = services.ViewService.CreateView(sourceEventStream, streamSpec.ViewSpecs, viewContext);

                    EvalRootNode rootNode = new EvalRootNode();
                    rootNode.AddChildNode(patternStreamSpec.EvalNode);
                    PatternContext patternContext = new PatternContext(services.FilterService, services.SchedulingService, scheduleBucket, services.EventAdapterService);

                    PatternMatchCallback callback = new PatternMatchCallbackImpl(
                        delegate(EDictionary<String, EventBean> matchEvent)
                        {
                            EventBean compositeEvent = patternContext.EventAdapterService.AdapterForCompositeEvent(eventType, matchEvent);
                            sourceEventStream.Insert(compositeEvent);
                        });

                    PatternStopCallback patternStopCallback = rootNode.Start(callback, patternContext);
                    stopCallbacks.Add(patternStopCallback);
                }
                else if (streamSpec is DBStatementStreamSpec)
                {
                    DBStatementStreamSpec sqlStreamSpec = (DBStatementStreamSpec)streamSpec;
                    HistoricalEventViewable historicalEventViewable = PollingViewableFactory.createDBStatementView(
                        i,
                        sqlStreamSpec,
                        services.DatabaseRefService,
                        services.EventAdapterService);

                    streamViews[i] = historicalEventViewable;
                    if (streamSpec.ViewSpecs.Count > 0)
                    {
                        throw new ExprValidationException(
							"Historical data joins do not allow views onto the data, view '" + streamSpec.ViewSpecs[0].ObjectNamespace + 
							":" + streamSpec.ViewSpecs[0].ObjectName + 
							"' is not valid in this context");
                    }
                    stopCallbacks.Add(historicalEventViewable);
                }
                else
                {
                    throw new ExprValidationException("Unknown stream specification");
                }

                streamTypes[i] = streamViews[i].EventType;
            }

            // create Stop method
            EPStatementStopMethod stopMethod = new EPStatementStopMethod(
                delegate()
                {
                    foreach (StreamSpec streamSpec in statementSpec.StreamSpecs)
                    {
                        if (streamSpec is FilterStreamSpec)
                        {
                            FilterStreamSpec filterStreamSpec = (FilterStreamSpec)streamSpec;
                            services.StreamService.dropStream(filterStreamSpec.FilterSpec, services.FilterService);
                        }
                    }
                    foreach (StopCallback stopCallback in stopCallbacks)
                    {
                        stopCallback.Stop();
                    }
                });

            // Construct type information per stream
            StreamTypeService typeService = new StreamTypeServiceImpl(streamTypes, streamNames);

            // Validate any views that require validation
            foreach (Viewable viewable in streamViews)
            {
                if (viewable is ValidatedView)
                {
                    ValidatedView validatedView = (ValidatedView)viewable;
                    validatedView.validate(typeService);
                }
            }

            // Get the service for resolving class names 
            AutoImportService autoImportService = services.AutoImportService;

            // Construct a processor for results posted by views and joins, which takes care of aggregation if required.
            // May return null if we don't need to post-process results posted by views or joins.
            ResultSetProcessor optionalResultSetProcessor = ResultSetProcessorFactory.getProcessor(
                    statementSpec.SelectListExpressions,
                    statementSpec.InsertIntoDesc,
                    statementSpec.GroupByExpressions,
                    statementSpec.HavingExprRootNode,
                    statementSpec.OutputLimitSpec,
                    statementSpec.OrderByList,
                    typeService,
                    services.EventAdapterService,
                    autoImportService);

            // Validate where-clause filter tree and outer join clause
            validateNodes(typeService, autoImportService);

            // For just 1 event stream without joins, handle the one-table process separatly.
            Viewable finalView = null;
            if (streamNames.Length == 1)
            {
                finalView = handleSimpleSelect(streamViews[0], optionalResultSetProcessor, viewContext);
            }
            else
            {
                finalView = handleJoin(streamNames, streamTypes, streamViews, optionalResultSetProcessor, statementSpec.SelectStreamSelectorEnum);
            }

            // Hook up internal event route for insert-into if required
            if (statementSpec.InsertIntoDesc != null)
            {
                InternalRouteView routeView = new InternalRouteView(statementSpec.InsertIntoDesc.IsStream, services.InternalEventRouter);
                finalView.AddView(routeView);
                finalView = routeView;
            }

            if (statementSpec.SelectStreamSelectorEnum != SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
            {
                IStreamRStreamSelectorView streamSelectorView = new IStreamRStreamSelectorView(statementSpec.SelectStreamSelectorEnum);
                finalView.AddView(streamSelectorView);
                finalView = streamSelectorView;
            }

            return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
        }

        private Viewable handleJoin(String[] streamNames, EventType[] streamTypes, Viewable[] streamViews, ResultSetProcessor optionalResultSetProcessor, SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
        {
            // Handle joins
            JoinSetComposer composer = JoinSetComposerFactory.makeComposer(statementSpec.OuterJoinDescList, statementSpec.FilterRootNode, streamTypes, streamNames, streamViews, selectStreamSelectorEnum);
            JoinSetFilter filter = new JoinSetFilter(statementSpec.FilterRootNode);
            OutputProcessView indicatorView = new OutputProcessView(optionalResultSetProcessor, statementSpec.StreamSpecs.Count, statementSpec.OutputLimitSpec, viewContext);

            // Create strategy for join execution
            JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView);

            // Hook up dispatchable with buffer and execution strategy
            JoinExecStrategyDispatchable dispatchable = new JoinExecStrategyDispatchable(services.DispatchService, execution, statementSpec.StreamSpecs.Count);

            // Create buffer for each view. Point buffer to dispatchable for join.
            for (int i = 0; i < statementSpec.StreamSpecs.Count; i++)
            {
                BufferView buffer = new BufferView(i);
                streamViews[i].AddView(buffer);
                buffer.Observer = dispatchable;
            }

            return indicatorView;
        }

        /// <summary> Returns a stream name assigned for each stream, generated if none was supplied.</summary>
        /// <param name="streams">- stream specifications
        /// </param>
        /// <returns> array of stream names
        /// </returns>
        internal static String[] determineStreamNames(IList<StreamSpec> streams)
        {
            String[] streamNames = new String[streams.Count];

            for (int i = 0; i < streams.Count; i++)
            {
                // Assign a stream name for joins, if not supplied
                streamNames[i] = streams[i].OptionalStreamName;
                if ((streamNames[i] == null) && (streams.Count > 1))
                {
                    streamNames[i] = "stream_" + i;
                }
            }

            return streamNames;
        }

        private void validateNodes(StreamTypeService typeService, AutoImportService autoImportService)
        {
            if (statementSpec.FilterRootNode != null)
            {
                ExprNode optionalFilterNode = statementSpec.FilterRootNode;

                // Validate where clause, initializing nodes to the stream ids used
                try
                {
                    optionalFilterNode = optionalFilterNode.GetValidatedSubtree(typeService, autoImportService);
                    statementSpec.FilterExprRootNode = optionalFilterNode;

                    // Make sure there is no aggregation in the where clause
                    IList<ExprAggregateNode> aggregateNodes = new List<ExprAggregateNode>();
                    ExprAggregateNode.getAggregatesBottomUp(optionalFilterNode, aggregateNodes);
                    if (aggregateNodes.Count > 0)
                    {
                        throw new ExprValidationException("An aggregate function may not appear in a WHERE clause (use the HAVING clause)");
                    }
                }
                catch (ExprValidationException ex)
                {
                    log.Debug(".validateNodes Validation exception for filter=" + optionalFilterNode.ExpressionString, ex);
                    throw new EPStatementException("Error validating expression: " + ex.Message, eqlStatement);
                }
            }

            for (int outerJoinCount = 0; outerJoinCount < statementSpec.OuterJoinDescList.Count; outerJoinCount++)
            {
                OuterJoinDesc outerJoinDesc = statementSpec.OuterJoinDescList[outerJoinCount];

                // Validate the outer join clause using an artificial equals-node on top.
                // Thus types are checked via equals.
                // Sets stream ids used for validated nodes.
                ExprNode EqualsNode = new ExprEqualsNode(false);
                EqualsNode.AddChildNode(outerJoinDesc.LeftNode);
                EqualsNode.AddChildNode(outerJoinDesc.RightNode);
                try
                {
                    EqualsNode = EqualsNode.GetValidatedSubtree(typeService, autoImportService);
                }
                catch (ExprValidationException ex)
                {
                    log.Debug("Validation exception for outer join node=" + EqualsNode.ExpressionString, ex);
                    throw new EPStatementException("Error validating expression: " + ex.Message, eqlStatement);
                }

                // Make sure we have left-hand-side and right-hand-side refering to different streams
                int streamIdLeft = outerJoinDesc.LeftNode.StreamId;
                int streamIdRight = outerJoinDesc.RightNode.StreamId;
                if (streamIdLeft == streamIdRight)
                {
                    String message = "Outer join ON-clause cannot refer to properties of the same stream";
                    throw new EPStatementException("Error validating expression: " + message, eqlStatement);
                }

                // Make sure one of the properties refers to the acutual stream currently being joined
                int expectedStreamJoined = outerJoinCount + 1;
                if ((streamIdLeft != expectedStreamJoined) && (streamIdRight != expectedStreamJoined))
                {
                    String message = "Outer join ON-clause must refer to at least one property of the joined stream" + " for stream " + expectedStreamJoined;
                    throw new EPStatementException("Error validating expression: " + message, eqlStatement);
                }

                // Make sure neither of the streams refer to a 'future' stream
                String badPropertyName = null;
                if (streamIdLeft > outerJoinCount + 1)
                {
                    badPropertyName = outerJoinDesc.LeftNode.ResolvedPropertyName;
                }
                if (streamIdRight > outerJoinCount + 1)
                {
                    badPropertyName = outerJoinDesc.RightNode.ResolvedPropertyName;
                }
                if (badPropertyName != null)
                {
                    String message = "Outer join ON-clause invalid scope for property" + " '" + badPropertyName + "', expecting the current or a prior stream scope";
                    throw new EPStatementException("Error validating expression: " + message, eqlStatement);
                }
            }
        }

        private Viewable handleSimpleSelect(Viewable view, ResultSetProcessor optionalResultSetProcessor, ViewServiceContext viewContext)
        {
            Viewable finalView = view;

            // Add filter view that evaluates the filter expression
            if (statementSpec.FilterRootNode != null)
            {
                FilterExprView filterView = new FilterExprView(statementSpec.FilterRootNode);
                finalView.AddView(filterView);
                finalView = filterView;
            }

            // Add select expression view if there is any
            if (optionalResultSetProcessor != null || statementSpec.OutputLimitSpec != null)
            {
                OutputProcessView selectView = new OutputProcessView(optionalResultSetProcessor, statementSpec.StreamSpecs.Count, statementSpec.OutputLimitSpec, viewContext);
                finalView.AddView(selectView);
                finalView = selectView;
            }

            return finalView;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EPEQLStmtStartMethod));
    }
}
