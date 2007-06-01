///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.eql.expression;
using net.esper.eql.join;
using net.esper.eql.join.plan;
using net.esper.eql.join.table;
using net.esper.eql.spec;
using net.esper.eql.view;
using net.esper.eql.subquery;
using net.esper.events;
using net.esper.pattern;
using net.esper.util;
using net.esper.view;
using net.esper.view.internals;

using org.apache.commons.logging;

using Properties = net.esper.compat.EDataDictionary;

namespace net.esper.core
{
	/// <summary>
    /// Starts and provides the stop method for EQL statements.
    /// </summary>
	public class EPStatementStartMethod
	{
	    private readonly StatementSpecCompiled statementSpec;
	    private readonly EPServicesContext services;
	    private readonly StatementContext statementContext;

	    /// <summary>Ctor.</summary>
	    /// <param name="statementSpec">
	    /// is a container for the definition of all statement constructs that
	    /// may have been used in the statement, i.e. if defines the select clauses, insert into, outer joins etc.
	    /// </param>
	    /// <param name="services">is the service instances for dependency injection</param>
	    /// <param name="statementContext">
	    /// is statement-level information and statement services
	    /// </param>
	    public EPStatementStartMethod(StatementSpecCompiled statementSpec,
	                                EPServicesContext services,
	                                StatementContext statementContext)
	    {
	        this.statementSpec = statementSpec;
	        this.services = services;
	        this.statementContext = statementContext;
	    }

	    /// <summary>Starts the EQL statement.</summary>
	    /// <returns>
	    /// a viewable to attach to for listening to events, and a stop method to invoke to clean up
	    /// </returns>
	    /// <throws>ExprValidationException when the expression validation fails</throws>
	    /// <throws>ViewProcessingException when views cannot be started</throws>
	    public Pair<Viewable, EPStatementStopMethod> Start()
	    {
	        // Determine stream names for each stream - some streams may not have a name given
	        String[] streamNames = DetermineStreamNames(statementSpec.StreamSpecs);
	        bool isJoin = statementSpec.StreamSpecs.Size() > 1;

	        // First we create streams for subselects, if there are any
	        SubSelectStreamCollection subSelectStreamDesc = CreateSubSelectStreams(isJoin);

	        int numStreams = streamNames.length;
	        List<StopCallback> stopCallbacks = new LinkedList<StopCallback>();

	        // Create streams and views
	        Viewable[] eventStreamParentViewable = new Viewable[numStreams];
	        ViewFactoryChain[] unmaterializedViewChain = new ViewFactoryChain[numStreams];
	        for (int i = 0; i < statementSpec.StreamSpecs.Size(); i++)
	        {
	            StreamSpecCompiled streamSpec = statementSpec.StreamSpecs.Get(i);

	            // Create view factories and parent view based on a filter specification
	            if (streamSpec is FilterStreamSpecCompiled)
	            {
	                FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
	                eventStreamParentViewable[i] = services.StreamService.CreateStream(filterStreamSpec.FilterSpec,
	                        services.FilterService, statementContext.EpStatementHandle, isJoin);
	                unmaterializedViewChain[i] = services.ViewService.CreateFactories(i, eventStreamParentViewable[i].EventType, streamSpec.ViewSpecs, statementContext);
	            }
	            // Create view factories and parent view based on a pattern expression
	            else if (streamSpec is PatternStreamSpecCompiled)
	            {
	                PatternStreamSpecCompiled patternStreamSpec = (PatternStreamSpecCompiled) streamSpec;
	                EventType eventType = services.EventAdapterService.CreateAnonymousCompositeType(patternStreamSpec.TaggedEventTypes);
	                EventStream sourceEventStream = new ZeroDepthStream(eventType);
	                eventStreamParentViewable[i] = sourceEventStream;
	                unmaterializedViewChain[i] = services.ViewService.CreateFactories(i, sourceEventStream.EventType, streamSpec.ViewSpecs, statementContext);

	                EvalRootNode rootNode = new EvalRootNode();
	                rootNode.AddChildNode(patternStreamSpec.EvalNode);

	                PatternMatchCallback callback = new PatternMatchCallbackImpl(
                        delegate(IDictionary<String, EventBean> matchEvent)
	                    {
	                        EventBean compositeEvent = statementContext.EventAdapterService.AdapterForCompositeEvent(eventType, matchEvent);
	                        sourceEventStream.Insert(compositeEvent);
	                    });

	                PatternContext patternContext = statementContext.PatternContextFactory.CreateContext(statementContext,
	                        i, rootNode);
	                PatternStopCallback patternStopCallback = rootNode.Start(callback, patternContext);
	                stopCallbacks.Add(patternStopCallback);
	            }
	            // Create view factories and parent view based on a database SQL statement
	            else if (streamSpec is DBStatementStreamSpec)
	            {
	                if (!streamSpec.ViewSpecs.IsEmpty())
	                {
	                    throw new ExprValidationException("Historical data joins do not allow views onto the data, view '"
	                            + streamSpec.ViewSpecs.Get(0).ObjectNamespace + ':' + streamSpec.ViewSpecs.Get(0).ObjectName + "' is not valid in this context");
	                }

	                DBStatementStreamSpec sqlStreamSpec = (DBStatementStreamSpec) streamSpec;
	                HistoricalEventViewable historicalEventViewable = PollingViewableFactory.CreateDBStatementView(i, sqlStreamSpec, services.DatabaseRefService, services.EventAdapterService, statementContext.EpStatementHandle);
	                unmaterializedViewChain[i] = new ViewFactoryChain(historicalEventViewable.EventType, new LinkedList<ViewFactory>());
	                eventStreamParentViewable[i] = historicalEventViewable;
	                stopCallbacks.Add(historicalEventViewable);
	            }
	            else
	            {
	                throw new ExprValidationException("Unknown stream specification type: " + streamSpec);
	            }
	        }

	        // Obtain event types from ViewFactoryChains
	        EventType[] streamEventTypes = new EventType[statementSpec.StreamSpecs.Size()];
	        for (int i = 0; i < unmaterializedViewChain.length; i++)
	        {
	            streamEventTypes[i] = unmaterializedViewChain[i].EventType;
	        }

	        // Materialize sub-select views
	        StartSubSelect(subSelectStreamDesc, streamNames, streamEventTypes);

	        // List of statement streams
	        List<StreamSpecCompiled> statementStreamSpecs = new ArrayList<StreamSpecCompiled>();
	        statementStreamSpecs.AddAll(statementSpec.StreamSpecs);

	        // Construct type information per stream
	        StreamTypeService typeService = new StreamTypeServiceImpl(streamEventTypes, streamNames);
	        ViewResourceDelegate viewResourceDelegate = new ViewResourceDelegateImpl(unmaterializedViewChain);

	        // create stop method using statement stream specs
	        EPStatementStopMethod stopMethod = new EPStatementStopMethod(
                delegate()
	            {
	                statementContext.StatementStopService.FireStatementStopped();

	                foreach (StreamSpecCompiled streamSpec in statementStreamSpecs)
	                {
	                    if (streamSpec is FilterStreamSpecCompiled)
	                    {
	                        FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) streamSpec;
	                        services.StreamService.DropStream(filterStreamSpec.FilterSpec, services.FilterService, isJoin);
	                    }
	                }
	                foreach (StopCallback stopCallback in stopCallbacks)
	                {
	                    stopCallback.Stop();
	                }
	                foreach (ExprSubselectNode subselect in statementSpec.SubSelectExpressions)
	                {
	                    FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) subselect.StatementSpecCompiled.StreamSpecs.Get(0);
	                    services.StreamService.DropStream(filterStreamSpec.FilterSpec, services.FilterService, isJoin);
	                }
	            });

	        // Validate views that require validation, specifically streams that don't have
	        // sub-views such as DB SQL joins
	        foreach (Viewable viewable in eventStreamParentViewable)
	        {
	            if (viewable is ValidatedView)
	            {
	                ValidatedView validatedView = (ValidatedView) viewable;
	                validatedView.Validate(typeService);
	            }
	        }

	        // Construct a processor for results posted by views and joins, which takes care of aggregation if required.
	        // May return null if we don't need to post-process results posted by views or joins.
	        ResultSetProcessor optionalResultSetProcessor = ResultSetProcessorFactory.GetProcessor(
	                statementSpec.SelectClauseSpec,
	                statementSpec.InsertIntoDesc,
	                statementSpec.GroupByExpressions,
	                statementSpec.HavingExprRootNode,
	                statementSpec.OutputLimitSpec,
	                statementSpec.OrderByList,
	                typeService,
	                services.EventAdapterService,
	                statementContext.MethodResolutionService,
	                viewResourceDelegate);

	        // Validate where-clause filter tree and outer join clause
	        ValidateNodes(typeService, statementContext.MethodResolutionService, viewResourceDelegate);

	        // Materialize views
	        Viewable[] streamViews = new Viewable[streamEventTypes.length];
	        for (int i = 0; i < streamViews.length; i++)
	        {
	            streamViews[i] = services.ViewService.CreateViews(eventStreamParentViewable[i], unmaterializedViewChain[i].ViewFactoryChain, statementContext);
	        }

	        // For just 1 event stream without joins, handle the one-table process separatly.
	        Viewable finalView;
	        if (streamNames.length == 1)
	        {
	            finalView = HandleSimpleSelect(streamViews[0], optionalResultSetProcessor, statementContext);
	        }
	        else
	        {
	            finalView = HandleJoin(streamNames, streamEventTypes, streamViews, optionalResultSetProcessor, statementSpec.SelectStreamSelectorEnum, statementContext.EpStatementHandle);
	        }

	        // Hook up internal event route for insert-into if required
	        if (statementSpec.InsertIntoDesc != null)
	        {
	            InternalRouteView routeView = new InternalRouteView(statementSpec.InsertIntoDesc.IsIStream(), services.InternalEventRouter);
	            finalView.AddView(routeView);
	            finalView = routeView;
	        }

	        if (statementSpec.SelectStreamSelectorEnum != SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
	        {
	            IStreamRStreamSelectorView streamSelectorView = new IStreamRStreamSelectorView(statementSpec.SelectStreamSelectorEnum);
	            finalView.AddView(streamSelectorView);
	            finalView = streamSelectorView;
	        }

	        log.Debug(".start Statement start completed");

	        return new Pair<Viewable, EPStatementStopMethod>(finalView, stopMethod);
	    }

	    private Viewable HandleJoin(String[] streamNames,
	                                EventType[] streamTypes,
	                                Viewable[] streamViews,
	                                ResultSetProcessor optionalResultSetProcessor,
	                                SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
	                                EPStatementHandle epStatementHandle)
	    {
	        // Handle joins
	        JoinSetComposer composer = JoinSetComposerFactory.MakeComposer(statementSpec.OuterJoinDescList, statementSpec.FilterRootNode, streamTypes, streamNames, streamViews, selectStreamSelectorEnum);
	        JoinSetFilter filter = new JoinSetFilter(statementSpec.FilterRootNode);
	        OutputProcessView indicatorView = OutputProcessViewFactory.MakeView(optionalResultSetProcessor, statementSpec.StreamSpecs.Size(), statementSpec.OutputLimitSpec, statementContext);

	        // Create strategy for join execution
	        JoinExecutionStrategy execution = new JoinExecutionStrategyImpl(composer, filter, indicatorView);

	        // Hook up dispatchable with buffer and execution strategy
	        JoinExecStrategyDispatchable joinStatementDispatch = new JoinExecStrategyDispatchable(execution, statementSpec.StreamSpecs.Size());
	        epStatementHandleOptionalDispatchable = joinStatementDispatch;

	        // Create buffer for each view. Point buffer to dispatchable for join.
	        for (int i = 0; i < statementSpec.StreamSpecs.Size(); i++)
	        {
	            BufferView buffer = new BufferView(i);
	            streamViews[i].AddView(buffer);
	            bufferObserver = joinStatementDispatch;
	        }

	        return indicatorView;
	    }

	    /// <summary>
	    /// Returns a stream name assigned for each stream, generated if none was supplied.
	    /// </summary>
	    /// <param name="streams">stream specifications</param>
	    /// <returns>array of stream names</returns>
	    protected static String[] DetermineStreamNames(List<StreamSpecCompiled> streams)
	    {
	        String[] streamNames = new String[streams.Size()];
	        for (int i = 0; i < streams.Size(); i++)
	        {
	            // Assign a stream name for joins, if not supplied
	            streamNames[i] = streams.Get(i).OptionalStreamName;
	            if ((streamNames[i] == null) && (streams.Size() > 1))
	            {
	                streamNames[i] = "stream_" + i;
	            }
	        }
	        return streamNames;
	    }

	    private void ValidateNodes(StreamTypeService typeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        if (statementSpec.FilterRootNode != null)
	        {
	            ExprNode optionalFilterNode = statementSpec.FilterRootNode;

	            // Validate where clause, initializing nodes to the stream ids used
	            try
	            {
	                optionalFilterNode = optionalFilterNode.GetValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate);
	                statementSpecFilterExprRootNode = optionalFilterNode;

	                // Make sure there is no aggregation in the where clause
	                List<ExprAggregateNode> aggregateNodes = new LinkedList<ExprAggregateNode>();
	                ExprAggregateNode.GetAggregatesBottomUp(optionalFilterNode, aggregateNodes);
	                if (!aggregateNodes.IsEmpty())
	                {
	                    throw new ExprValidationException("An aggregate function may not appear in a WHERE clause (use the HAVING clause)");
	                }
	            }
	            catch (ExprValidationException ex)
	            {
	                log.Debug(".validateNodes Validation exception for filter=" + optionalFilterNode.ToExpressionString(), ex);
	                throw new EPStatementException("Error validating expression: " + ex.Message, statementContext.Expression);
	            }
	        }

	        for (int outerJoinCount = 0; outerJoinCount < statementSpec.OuterJoinDescList.Size(); outerJoinCount++)
	        {
	            OuterJoinDesc outerJoinDesc = statementSpec.OuterJoinDescList.Get(outerJoinCount);

	            // Validate the outer join clause using an artificial equals-node on top.
	            // Thus types are checked via equals.
	            // Sets stream ids used for validated nodes.
	            ExprNode equalsNode = new ExprEqualsNode(false);
	            equalsNode.AddChildNode(outerJoinDesc.LeftNode);
	            equalsNode.AddChildNode(outerJoinDesc.RightNode);
	            try
	            {
	                equalsNode = equalsNode.GetValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate);
	            }
	            catch (ExprValidationException ex)
	            {
	                log.Debug("Validation exception for outer join node=" + equalsNode.ToExpressionString(), ex);
	                throw new EPStatementException("Error validating expression: " + ex.Message, statementContext.Expression);
	            }

	            // Make sure we have left-hand-side and right-hand-side refering to different streams
	            int streamIdLeft = outerJoinDesc.LeftNode.StreamId;
	            int streamIdRight = outerJoinDesc.RightNode.StreamId;
	            if (streamIdLeft == streamIdRight)
	            {
	                String message = "Outer join ON-clause cannot refer to properties of the same stream";
	                throw new EPStatementException("Error validating expression: " + message, statementContext.Expression);
	            }

	            // Make sure one of the properties refers to the acutual stream currently being joined
	            int expectedStreamJoined = outerJoinCount + 1;
	            if ((streamIdLeft != expectedStreamJoined) && (streamIdRight != expectedStreamJoined))
	            {
	                String message = "Outer join ON-clause must refer to at least one property of the joined stream" +
	                        " for stream " + expectedStreamJoined;
	                throw new EPStatementException("Error validating expression: " + message, statementContext.Expression);
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
	                String message = "Outer join ON-clause invalid scope for property" +
	                        " '" + badPropertyName + "', expecting the current or a prior stream scope";
	                throw new EPStatementException("Error validating expression: " + message, statementContext.Expression);
	            }

	        }
	    }

	    private Viewable HandleSimpleSelect(Viewable view,
	                                        ResultSetProcessor optionalResultSetProcessor,
	                                        StatementContext statementContext)
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
	            OutputProcessView selectView = OutputProcessViewFactory.MakeView(optionalResultSetProcessor, statementSpec.StreamSpecs.Size(), statementSpec.OutputLimitSpec, statementContext);
	            finalView.AddView(selectView);
	            finalView = selectView;
	        }

	        return finalView;
	    }

        private SubSelectStreamCollection CreateSubSelectStreams(bool isJoin)
	    {
	        SubSelectStreamCollection subSelectStreamDesc = new SubSelectStreamCollection();
	        int subselectStreamNumber = 1024;

	        // Process all subselect expression nodes
	        foreach (ExprSubselectNode subselect in statementSpec.SubSelectExpressions)
	        {
	            StatementSpecCompiled statementSpec = subselect.StatementSpecCompiled;
	            SelectClauseSpec selectClauseSpec = statementSpec.SelectClauseSpec;
	            FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) statementSpec.StreamSpecs.Get(0);

	            // Validate select clause wildcard use
	            if ((!subselect.IsAllowWildcardSelect) && (selectClauseSpec.IsUsingWildcard))
	            {
	                throw new ExprValidationException("Invalid use of wildcard in subquery");
	            }

	            // no aggregation functions allowed in select
	            if (selectClauseSpec.SelectList.Size() > 0)
	            {
	                ExprNode selectExpression = selectClauseSpec.SelectList.Get(0).SelectExpression;
	                List<ExprAggregateNode> aggExprNodes = new LinkedList<ExprAggregateNode>();
	                ExprAggregateNode.GetAggregatesBottomUp(selectExpression, aggExprNodes);
	                if (aggExprNodes.Size() > 0)
	                {
	                    throw new ExprValidationException("Aggregation functions are not supported within subqueries, consider using insert-into instead");
	                }
	            }

	            // no aggregation functions allowed in filter
	            if (statementSpec.FilterRootNode != null)
	            {
	                List<ExprAggregateNode> aggExprNodes = new LinkedList<ExprAggregateNode>();
	                ExprAggregateNode.GetAggregatesBottomUp(statementSpec.FilterRootNode, aggExprNodes);
	                if (aggExprNodes.Size() > 0)
	                {
	                    throw new ExprValidationException("Aggregation functions are not supported within subqueries, consider using insert-into instead");
	                }
	            }

	            // A child view is required to limit the stream
	            if (filterStreamSpec.ViewSpecs.Size() == 0)
	            {
	                throw new ExprValidationException("Subqueries require one or more views to limit the stream, consider declaring a length or time window");
	            }

	            subselectStreamNumber++;

	            // Register filter, create view factories
	            Viewable viewable = services.StreamService.CreateStream(filterStreamSpec.FilterSpec,
	                    services.FilterService, statementContext.EpStatementHandle, isJoin);
	            ViewFactoryChain viewFactoryChain = services.ViewService.CreateFactories(subselectStreamNumber, viewable.EventType, filterStreamSpec.ViewSpecs, statementContext);

	            // Add subquery to list, for later starts
	            subSelectStreamDesc.Add(subselect, subselectStreamNumber, viewable, viewFactoryChain);
	        }

	        return subSelectStreamDesc;
	    }

	    private void StartSubSelect(SubSelectStreamCollection subSelectStreamDesc, String[] outerStreamNames, EventType[] outerEventTypes)
	    {
	        foreach (ExprSubselectNode subselect in statementSpec.SubSelectExpressions)
	        {
	            StatementSpecCompiled statementSpec = subselect.StatementSpecCompiled;
	            FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) statementSpec.StreamSpecs.Get(0);
	            ViewFactoryChain viewFactoryChain = subSelectStreamDesc.GetViewFactoryChain(subselect);
	            EventType eventType = viewFactoryChain.EventType;

	            // determine a stream name unless one was supplied
	            String subexpressionStreamName = filterStreamSpec.OptionalStreamName;
	            int subselectStreamNumber = subSelectStreamDesc.GetStreamNumber(subselect);
	            if (subexpressionStreamName == null)
	            {
	                subexpressionStreamName = "$subselect_" + subselectStreamNumber;
	            }

	            // Streams event types are the original stream types with the stream zero the subselect stream
	            LinkedDictionary<String, EventType> namesAndTypes = new LinkedDictionary<String, EventType>();
	            namesAndTypes.Put(subexpressionStreamName, eventType);
	            for (int i = 0; i < outerEventTypes.length; i++)
	            {
	                namesAndTypes.Put(outerStreamNames[i], outerEventTypes[i]);
	            }
	            StreamTypeService subselectTypeService = new StreamTypeServiceImpl(namesAndTypes, true, true);
	            ViewResourceDelegate viewResourceDelegateSubselect = new ViewResourceDelegateImpl(new ViewFactoryChain[] {viewFactoryChain});

	            // Validate select expression
	            SelectClauseSpec selectClauseSpec = subselect.StatementSpecCompiled.SelectClauseSpec;
	            if (selectClauseSpec.SelectList.Size() > 0)
	            {
	                ExprNode selectExpression = selectClauseSpec.SelectList.Get(0).SelectExpression;
	                selectExpression = selectExpression.GetValidatedSubtree(subselectTypeService, statementContext.MethodResolutionService, viewResourceDelegateSubselect);
	                subselectSelectClause = selectExpression;
	                subselectSelectAsName = selectClauseSpec.SelectList.Get(0).OptionalAsName;
	            }

	            // Validate filter expression, if there is one
	            ExprNode filterExpr = statementSpec.FilterRootNode;
	            if (filterExpr != null)
	            {
	                filterExpr = filterExpr.GetValidatedSubtree(subselectTypeService, statementContext.MethodResolutionService, viewResourceDelegateSubselect);
	                if (TypeHelper.GetBoxedType(filterExpr.Type) != typeof(bool?))
	                {
	                    throw new ExprValidationException("Subselect filter expression must return a bool value");
	                }
	                subselectFilterExpr = filterExpr;
	            }

	            // Finally create views
	            Viewable viewableRoot = subSelectStreamDesc.GetRootViewable(subselect);
	            Viewable subselectView = services.ViewService.CreateViews(viewableRoot, viewFactoryChain.ViewFactoryChain, statementContext);

	            // Determine indexing of the filter expression
	            Pair<EventTable, SubqueryTableLookupStrategy> indexPair = DetermineSubqueryIndex(filterExpr, eventType,
	                    outerEventTypes, subselectTypeService);
	            subselectStrategy = indexPair.Second;
	            EventTable eventIndex = indexPair.First;

	            // hook up subselect viewable and event table
	            BufferView bufferView = new BufferView(subselectStreamNumber);
                bufferView.SetObserver(new BufferObserver(
                    delegate(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
                    {
                        eventIndex.Remove(oldEventBuffer.AndFlush);
                        eventIndex.Add(newEventBuffer.AndFlush);
                    }));
	            subselectView.AddView(bufferView);
	        }
	    }

	    private Pair<EventTable, SubqueryTableLookupStrategy> DetermineSubqueryIndex(ExprNode filterExpr,
	                                                                                 EventType viewableEventType,
	                                                                                 EventType[] outerEventTypes,
	                                                                                 StreamTypeService subselectTypeService)
	    {
	        // No filter expression means full table scan
	        if (filterExpr == null)
	        {
	            UnindexedEventTable table = new UnindexedEventTable(0);
	            FullTableScanLookupStrategy strategy = new FullTableScanLookupStrategy(table);
	            return new Pair<EventTable, SubqueryTableLookupStrategy>(table, strategy);
	        }

	        // analyze query graph
	        QueryGraph queryGraph = new QueryGraph(outerEventTypes.length + 1);
	        FilterExprAnalyzer.Analyze(filterExpr, queryGraph);

	        // Build a list of streams and indexes
	        IDictionary<String, SubqueryJoinedPropDesc> joinProps = new LinkedDictionary<String, SubqueryJoinedPropDesc>();
            bool mustCoerce = false;
	        for (int stream = 0; stream <  outerEventTypes.length; stream++)
	        {
	            int lookupStream = stream + 1;
	            String[] keyPropertiesJoin = queryGraph.GetKeyProperties(lookupStream, 0);
	            String[] indexPropertiesJoin = queryGraph.GetIndexProperties(lookupStream, 0);
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
	                Class keyPropType = TypeHelper.GetBoxedType(subselectTypeService.EventTypes[lookupStream].GetPropertyType(keyPropertiesJoin[i]));
	                Class indexedPropType = TypeHelper.GetBoxedType(subselectTypeService.EventTypes[0].GetPropertyType(indexPropertiesJoin[i]));
	                Class coercionType = indexedPropType;
	                if (keyPropType != indexedPropType)
	                {
	                    coercionType = TypeHelper.GetCompareToCoercionType(keyPropType, keyPropType);
	                    mustCoerce = true;
	                }

	                SubqueryJoinedPropDesc desc = new SubqueryJoinedPropDesc(indexPropertiesJoin[i],
	                        coercionType, keyPropertiesJoin[i], stream);
	                joinProps.Put(indexPropertiesJoin[i], desc);
	            }
	        }

	        if (joinProps.Size() != 0)
	        {
	            String[] indexedProps = joinProps.KeySet().ToArray(new String[0]);
	            int[] keyStreamNums = SubqueryJoinedPropDesc.GetKeyStreamNums(joinProps.Values());
	            String[] keyProps = SubqueryJoinedPropDesc.GetKeyProperties(joinProps.Values());

	            if (!mustCoerce)
	            {
	                PropertyIndexedEventTable table = new PropertyIndexedEventTable(0, viewableEventType, indexedProps);
	                SubqueryTableLookupStrategy strategy = new IndexedTableLookupStrategy( outerEventTypes,
	                        keyStreamNums, keyProps, table);
	                return new Pair<EventTable, SubqueryTableLookupStrategy>(table, strategy);
	            }
	            else
	            {
	                Type[] coercionTypes = SubqueryJoinedPropDesc.GetCoercionTypes(joinProps.Values());
	                PropertyIndTableCoerceAdd table = new PropertyIndTableCoerceAdd(0, viewableEventType, indexedProps, coercionTypes);
	                SubqueryTableLookupStrategy strategy = new IndexedTableLookupStrategyCoercing( outerEventTypes, keyStreamNums, keyProps, table, coercionTypes);
	                return new Pair<EventTable, SubqueryTableLookupStrategy>(table, strategy);
	            }
	        }
	        else
	        {
	            UnindexedEventTable table = new UnindexedEventTable(0);
	            return new Pair<EventTable, SubqueryTableLookupStrategy>(table, new FullTableScanLookupStrategy(table));
	        }
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
