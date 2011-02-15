/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.StreamJoinAnalysisResult;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.join.exec.ExecNode;
import com.espertech.esper.epl.join.plan.*;
import com.espertech.esper.epl.join.table.*;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.DependencyGraph;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.HistoricalEventViewable;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Factory for building a {@link JoinSetComposer} from analyzing filter nodes, for
 * fast join tuple result set composition.
 */
public class JoinSetComposerFactoryImpl implements JoinSetComposerFactory
{
    private static final Log queryPlanLog = LogFactory.getLog(AuditPath.QUERYPLAN_LOG);

    /**
     * Builds join tuple composer.
     * @param outerJoinDescList - list of descriptors for outer join criteria
     * @param optionalFilterNode - filter tree for analysis to build indexes for fast access
     * @param streamTypes - types of streams
     * @param streamNames - names of streams
     * @param streamViews - leaf view per stream
     * @param selectStreamSelectorEnum - indicator for rstream or istream-only, for optimization
     * @return composer implementation
     * @throws ExprValidationException is thrown to indicate that
     * validation of view use in joins failed.
     */
    public JoinSetComposer makeComposer(List<OuterJoinDesc> outerJoinDescList,
                                                   ExprNode optionalFilterNode,
                                                   EventType[] streamTypes,
                                                   String[] streamNames,
                                                   Viewable[] streamViews,
                                                   SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
                                                   StreamJoinAnalysisResult streamJoinAnalysisResult,
                                                   ExprEvaluatorContext exprEvaluatorContext,
                                                   boolean queryPlanLogging)
            throws ExprValidationException
    {
        // Determine if there is a historical stream, and what dependencies exist
        DependencyGraph historicalDependencyGraph = new DependencyGraph(streamTypes.length);
        boolean[] isHistorical = new boolean[streamViews.length];
        boolean hasHistorical = false;
        for (int i = 0; i < streamViews.length; i++)
        {
            if (streamViews[i] instanceof HistoricalEventViewable)
            {
                HistoricalEventViewable historicalViewable = (HistoricalEventViewable) streamViews[i];
                isHistorical[i] = true;
                hasHistorical = true;
                SortedSet<Integer> streamsThisStreamDependsOn = historicalViewable.getRequiredStreams();
                historicalDependencyGraph.addDependency(i, streamsThisStreamDependsOn);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Dependency graph: " + historicalDependencyGraph);
        }

        QueryStrategy[] queryStrategies;

        // Handle a join with a database or other historical data source for 2 streams
        if ((hasHistorical) && (streamViews.length == 2))
        {
            return makeComposerHistorical2Stream(outerJoinDescList, optionalFilterNode, streamTypes, streamViews, exprEvaluatorContext, queryPlanLogging);
        }

        // Query graph for graph relationships between streams/historicals
        // For outer joins the query graph will just contain outer join relationships
        QueryGraph queryGraph = new QueryGraph(streamTypes.length);
        if (!outerJoinDescList.isEmpty())
        {
            OuterJoinAnalyzer.analyze(outerJoinDescList, queryGraph);
            if (log.isDebugEnabled())
            {
                log.debug(".makeComposer After outer join queryGraph=\n" + queryGraph);
            }
        }

        // Let the query graph reflect the where-clause
        if (optionalFilterNode != null)
        {
            // Analyze relationships between streams using the optional filter expression.
            // Relationships are properties in AND and EQUALS nodes of joins.
            FilterExprAnalyzer.analyze(optionalFilterNode, queryGraph);
            if (log.isDebugEnabled())
            {
                log.debug(".makeComposer After filter expression queryGraph=\n" + queryGraph);
            }

            // Add navigation entries based on key and index property equivalency (a=b, b=c follows a=c)
            QueryGraph.fillEquivalentNav(queryGraph);
            if (log.isDebugEnabled())
            {
                log.debug(".makeComposer After fill equiv. nav. queryGraph=\n" + queryGraph);
            }
        }

        // Historical index lists
        HistoricalStreamIndexList[] historicalStreamIndexLists = new HistoricalStreamIndexList[streamTypes.length];

        QueryPlan queryPlan = QueryPlanBuilder.getPlan(streamTypes, outerJoinDescList, queryGraph, streamNames,
                hasHistorical, isHistorical, historicalDependencyGraph, historicalStreamIndexLists, exprEvaluatorContext,
                streamJoinAnalysisResult);

        // remove unused indexes - consider all streams or all unidirectional
        HashSet<String> usedIndexes = new HashSet<String>();
        QueryPlanIndex[] indexSpecs = queryPlan.getIndexSpecs();
        for (int streamNum = 0; streamNum < queryPlan.getExecNodeSpecs().length; streamNum++) {
            QueryPlanNode planNode = queryPlan.getExecNodeSpecs()[streamNum];
            if (planNode != null) {
                planNode.addIndexes(usedIndexes);
            }
        }
        for (QueryPlanIndex indexSpec : indexSpecs) {
            if (indexSpec == null) {
                continue;
            }
            Map<String, QueryPlanIndexItem> items = indexSpec.getItems();
            String[] indexNames = items.keySet().toArray(new String[items.size()]);
            for (String indexName : indexNames) {
                if (!usedIndexes.contains(indexName)) {
                    items.remove(indexName);
                }
            }
        }

        if (queryPlanLogging && queryPlanLog.isInfoEnabled()) {
            queryPlanLog.info("Query plan: " + queryPlan);
        }

        // Build indexes
        Map<String, EventTable>[] indexesPerStream = new HashMap[indexSpecs.length];
        for (int streamNo = 0; streamNo < indexSpecs.length; streamNo++)
        {
            if (indexSpecs[streamNo] == null)
            {
                continue;
            }

            Map<String, QueryPlanIndexItem> items = indexSpecs[streamNo].getItems();
            indexesPerStream[streamNo] = new LinkedHashMap<String, EventTable>();

            for (Map.Entry<String, QueryPlanIndexItem> entry : items.entrySet()) {
                EventTable index = buildIndex(streamNo, items.get(entry.getKey()), streamTypes[streamNo]);
                indexesPerStream[streamNo].put(entry.getKey(), index);
            }
        }

        // Build strategies
        QueryPlanNode[] queryExecSpecs = queryPlan.getExecNodeSpecs();
        queryStrategies = new QueryStrategy[queryExecSpecs.length];
        for (int i = 0; i < queryExecSpecs.length; i++)
        {
            QueryPlanNode planNode = queryExecSpecs[i];
            if (planNode == null)
            {
                log.debug(".makeComposer No execution node for stream " + i + " '" + streamNames[i] + "'");
                continue;
            }

            ExecNode executionNode = planNode.makeExec(indexesPerStream, streamTypes, streamViews, historicalStreamIndexLists);

            if (log.isDebugEnabled())
            {
                log.debug(".makeComposer Execution nodes for stream " + i + " '" + streamNames[i] +
                    "' : \n" + ExecNode.print(executionNode));
            }

            queryStrategies[i] = new ExecNodeQueryStrategy(i, streamTypes.length, executionNode);
        }

        // If this is not unidirectional and not a self-join (excluding self-outer-join)
        if ((!streamJoinAnalysisResult.isUnidirectional()) &&
            (!streamJoinAnalysisResult.isPureSelfJoin() || !outerJoinDescList.isEmpty()))
        {
            if (hasHistorical)
            {
                return new JoinSetComposerHistoricalImpl(indexesPerStream, queryStrategies, streamViews, exprEvaluatorContext);
            }
            else
            {
                return new JoinSetComposerImpl(indexesPerStream, queryStrategies, streamJoinAnalysisResult.isPureSelfJoin(), exprEvaluatorContext);
            }
        }
        else
        {
            QueryStrategy driver;
            int unidirectionalStream;
            if (streamJoinAnalysisResult.getUnidirectionalStreamNumber() != -1)
            {
                unidirectionalStream = streamJoinAnalysisResult.getUnidirectionalStreamNumber();
                driver = queryStrategies[unidirectionalStream];
            }
            else
            {
                unidirectionalStream = 0;
                driver = queryStrategies[0];
            }
            return new JoinSetComposerStreamToWinImpl(indexesPerStream, streamJoinAnalysisResult.isPureSelfJoin(),
                    unidirectionalStream, driver, streamJoinAnalysisResult.getUnidirectionalNonDriving());
        }
    }

    private JoinSetComposer makeComposerHistorical2Stream(List<OuterJoinDesc> outerJoinDescList,
                                                   ExprNode optionalFilterNode,
                                                   EventType[] streamTypes,
                                                   Viewable[] streamViews,
                                                   ExprEvaluatorContext exprEvaluatorContext,
                                                   boolean queryPlanLogging)
            throws ExprValidationException
    {
        QueryStrategy[] queryStrategies;

        // No tables for any streams
        queryStrategies = new QueryStrategy[streamTypes.length];

        int polledViewNum = 0;
        int streamViewNum = 1;
        if (streamViews[1] instanceof HistoricalEventViewable)
        {
            streamViewNum = 0;
            polledViewNum = 1;
        }

        // if all-historical join, check dependency
        boolean isAllHistoricalNoSubordinate = false;
        if ((streamViews[0] instanceof HistoricalEventViewable) && (streamViews[1] instanceof HistoricalEventViewable))
        {
            DependencyGraph graph = new DependencyGraph(2);
            graph.addDependency(0, ((HistoricalEventViewable) streamViews[0]).getRequiredStreams());
            graph.addDependency(1, ((HistoricalEventViewable) streamViews[1]).getRequiredStreams());
            if (graph.getFirstCircularDependency() != null)
            {
                throw new ExprValidationException("Circular dependency detected between historical streams");
            }

            // if both streams are independent
            if (graph.getRootNodes().size() == 2)
            {
                isAllHistoricalNoSubordinate = true; // No parameters used by either historical
            }
            else
            {
                if ((graph.getDependenciesForStream(0).size() == 0))
                {
                    streamViewNum = 0;
                    polledViewNum = 1;
                }
                else
                {
                    streamViewNum = 1;
                    polledViewNum = 0;
                }                
            }
        }

        // Build an outer join expression node
        boolean isOuterJoin = false;
        ExprNode outerJoinEqualsNode = null;
        if (!outerJoinDescList.isEmpty())
        {
            OuterJoinDesc outerJoinDesc = outerJoinDescList.get(0);
            if (outerJoinDesc.getOuterJoinType().equals(OuterJoinType.FULL))
            {
                isOuterJoin = true;
            }
            else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.LEFT)) &&
                    (streamViewNum == 0))
            {
                    isOuterJoin = true;
            }
            else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.RIGHT)) &&
                    (streamViewNum == 1))
            {
                    isOuterJoin = true;
            }

            outerJoinEqualsNode  = outerJoinDesc.makeExprNode(exprEvaluatorContext);
        }

        // Determine filter for indexing purposes
        ExprNode filterForIndexing = null;
        if ((outerJoinEqualsNode != null) && (optionalFilterNode != null))  // both filter and outer join, add
        {
            filterForIndexing = new ExprAndNode();
            filterForIndexing.addChildNode(optionalFilterNode);
            filterForIndexing.addChildNode(outerJoinEqualsNode);
        }
        else if ((outerJoinEqualsNode == null) && (optionalFilterNode != null))
        {
            filterForIndexing = optionalFilterNode;
        }
        else if (outerJoinEqualsNode != null)
        {
            filterForIndexing = outerJoinEqualsNode;
        }

        Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy> indexStrategies =
                determineIndexing(filterForIndexing, streamTypes[polledViewNum], streamTypes[streamViewNum], polledViewNum, streamViewNum);

        HistoricalEventViewable viewable = (HistoricalEventViewable) streamViews[polledViewNum];
        ExprEvaluator outerJoinEqualsNodeEval = outerJoinEqualsNode == null ? null : outerJoinEqualsNode.getExprEvaluator();
        queryStrategies[streamViewNum] = new HistoricalDataQueryStrategy(streamViewNum, polledViewNum, viewable, isOuterJoin, outerJoinEqualsNodeEval,
                indexStrategies.getFirst(), indexStrategies.getSecond());

        // for strictly historical joins, create a query strategy for the non-subordinate historical view
        if (isAllHistoricalNoSubordinate)
        {
            isOuterJoin = false;
            if (!outerJoinDescList.isEmpty())
            {
                OuterJoinDesc outerJoinDesc = outerJoinDescList.get(0);
                if (outerJoinDesc.getOuterJoinType().equals(OuterJoinType.FULL))
                {
                    isOuterJoin = true;
                }
                else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.LEFT)) &&
                        (polledViewNum == 0))
                {
                        isOuterJoin = true;
                }
                else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.RIGHT)) &&
                        (polledViewNum == 1))
                {
                        isOuterJoin = true;
                }
            }

            viewable = (HistoricalEventViewable) streamViews[streamViewNum];
            queryStrategies[polledViewNum] = new HistoricalDataQueryStrategy(polledViewNum, streamViewNum, viewable, isOuterJoin, outerJoinEqualsNodeEval,
                    new HistoricalIndexLookupStrategyNoIndex(), new PollResultIndexingStrategyNoIndex());
        }

        return new JoinSetComposerHistoricalImpl(null, queryStrategies, streamViews, exprEvaluatorContext);
    }

    private static Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy> determineIndexing(ExprNode filterForIndexing,
                                                                                              EventType polledViewType,
                                                                                              EventType streamViewType,
                                                                                              int polledViewStreamNum,
                                                                                              int streamViewStreamNum)
    {
        // No filter means unindexed event tables
        if (filterForIndexing == null)
        {
            return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(
                            new HistoricalIndexLookupStrategyNoIndex(), new PollResultIndexingStrategyNoIndex());
        }

        // analyze query graph; Whereas stream0=named window, stream1=delete-expr filter
        QueryGraph queryGraph = new QueryGraph(2);
        FilterExprAnalyzer.analyze(filterForIndexing, queryGraph);

        return determineIndexing(queryGraph, polledViewType, streamViewType, polledViewStreamNum, streamViewStreamNum);
    }

    /**
     * Constructs indexing and lookup strategy for a given relationship that a historical stream may have with another
     * stream (historical or not) that looks up into results of a poll of a historical stream.
     * <p>
     * The term "polled" refers to the assumed-historical stream.
     * @param queryGraph relationship representation of where-clause filter and outer join on-expressions
     * @param polledViewType the event type of the historical that is indexed
     * @param streamViewType the event type of the stream looking up in indexes
     * @param polledViewStreamNum the stream number of the historical that is indexed
     * @param streamViewStreamNum the stream number of the historical that is looking up
     * @return indexing and lookup strategy pair
     */
    public static Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy> determineIndexing(QueryGraph queryGraph,
                                                                                                  EventType polledViewType,
                                                                                                  EventType streamViewType,
                                                                                                  int polledViewStreamNum,
                                                                                                  int streamViewStreamNum)
    {
        // index and key property names
        String[] keyPropertiesJoin = queryGraph.getKeyProperties(streamViewStreamNum, polledViewStreamNum);
        String[] indexPropertiesJoin = queryGraph.getIndexProperties(streamViewStreamNum, polledViewStreamNum);
        List<QueryGraphValueRange> rangeEntries = queryGraph.getGraphValue(streamViewStreamNum, polledViewStreamNum, false).getRangeEntries();

        // If the analysis revealed no join columns, must use the brute-force full table scan
        if (((keyPropertiesJoin == null) || (keyPropertiesJoin.length == 0)) && rangeEntries.isEmpty())
        {
            return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(
                            new HistoricalIndexLookupStrategyNoIndex(), new PollResultIndexingStrategyNoIndex());
        }

        Class[] keyCoercionTypes = QueryPlanIndexBuilder.getCoercionTypes(new EventType[] {streamViewType, polledViewType}, 0, 1, keyPropertiesJoin, indexPropertiesJoin);

        if (rangeEntries.isEmpty()) {
            // No coercion
            if (keyCoercionTypes == null)
            {
                PollResultIndexingStrategyIndex indexing = new PollResultIndexingStrategyIndex(polledViewStreamNum, polledViewType, indexPropertiesJoin);
                HistoricalIndexLookupStrategy strategy = new HistoricalIndexLookupStrategyIndex(streamViewType, keyPropertiesJoin);
                return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(strategy, indexing);
            }

            // With coercion, same lookup strategy as the index coerces
            PollResultIndexingStrategy indexing = new PollResultIndexingStrategyIndexCoerce(polledViewStreamNum, polledViewType, indexPropertiesJoin, keyCoercionTypes);
            HistoricalIndexLookupStrategy strategy = new HistoricalIndexLookupStrategyIndex(streamViewType, keyPropertiesJoin);
            return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(strategy, indexing);
        }
        else {
            Class[] rangeCoercionTypes = QueryPlanIndexBuilder.getCoercionTypes(new EventType[] {streamViewType, polledViewType}, 0, 1, rangeEntries);
            if (rangeEntries.size() == 1 && (keyPropertiesJoin == null) || (keyPropertiesJoin.length == 0)) {
                Class rangeCoercionType = rangeCoercionTypes == null ? null : rangeCoercionTypes[0];
                PollResultIndexingStrategySorted indexing = new PollResultIndexingStrategySorted(polledViewStreamNum, polledViewType, QueryGraphValueRange.getPropertyNamesValues(rangeEntries)[0], rangeCoercionType);
                HistoricalIndexLookupStrategy strategy = new HistoricalIndexLookupStrategySorted(streamViewType, QueryGraphValueRange.getPropertyNamesRangeKey(rangeEntries).get(0));
                return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(strategy, indexing);
            }
            else {
                PollResultIndexingStrategyComposite indexing = new PollResultIndexingStrategyComposite(polledViewStreamNum, polledViewType, indexPropertiesJoin, keyCoercionTypes, QueryGraphValueRange.getPropertyNamesValues(rangeEntries), rangeCoercionTypes);
                HistoricalIndexLookupStrategy strategy = new HistoricalIndexLookupStrategyComposite(streamViewType, keyPropertiesJoin, keyCoercionTypes, QueryGraphValueRange.getPropertyNamesRangeKey(rangeEntries), rangeCoercionTypes);
                return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(strategy, indexing);
            }
        }
    }

    /**
     * Build an index/table instance using the event properties for the event type.
     * @param indexedStreamNum - number of stream indexed
     * @param eventType - type of event to expect
     * @return table build
     */
    protected static EventTable buildIndex(int indexedStreamNum, QueryPlanIndexItem item, EventType eventType)
    {
        String[] indexProps = item.getIndexProps();
        Class[] indexCoercionTypes = item.getOptIndexCoercionTypes();
        String[] rangeProps = item.getRangeProps();
        Class[] rangeCoercionTypes = item.getOptRangeCoercionTypes();

        EventTable table;
        if (rangeProps == null || rangeProps.length == 0) {
            if (indexProps == null || indexProps.length == 0)
            {
                table = new UnindexedEventTable(indexedStreamNum);
            }
            else
            {
                if (indexCoercionTypes == null || indexCoercionTypes.length == 0)
                {
                    table = new PropertyIndexedEventTable(indexedStreamNum, eventType, indexProps, null);
                }
                else
                {
                    table = new PropertyIndTableCoerceAll(indexedStreamNum, eventType, indexProps, indexCoercionTypes);
                }
            }
        }
        else {
            if ((rangeProps.length == 1) && (indexProps == null || indexProps.length == 0)) {
                if (rangeCoercionTypes == null) {
                    return new PropertySortedEventTable(indexedStreamNum, eventType, rangeProps[0]);
                }
                else {
                    return new PropertySortedEventTableCoerced(indexedStreamNum, eventType, rangeProps[0], rangeCoercionTypes[0]);
                }
            }
            else {
                return new PropertyCompositeEventTable(indexedStreamNum, eventType, indexProps, indexCoercionTypes, rangeProps, rangeCoercionTypes);
            }
        }
        return table;
    }

    private static final Log log = LogFactory.getLog(JoinSetComposerFactoryImpl.class);
}
