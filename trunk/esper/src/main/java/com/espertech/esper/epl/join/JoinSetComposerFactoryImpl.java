/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.expression.ExprAndNode;
import com.espertech.esper.epl.expression.ExprEqualsNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.join.exec.ExecNode;
import com.espertech.esper.epl.join.plan.*;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndTableCoerceAll;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTable;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.event.EventType;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.HistoricalEventViewable;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Factory for building a {@link JoinSetComposer} from analyzing filter nodes, for
 * fast join tuple result set composition.
 */
public class JoinSetComposerFactoryImpl implements JoinSetComposerFactory
{
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
                                                   boolean[] isUnidirectional,
                                                   boolean[] hasChildViews)
            throws ExprValidationException
    {
        // Determine if there is a historical
        boolean hasHistorical = false;
        for (int i = 0; i < streamViews.length; i++)
        {
            if (streamViews[i] instanceof HistoricalEventViewable)
            {
                if (hasHistorical)
                {
                    throw new ExprValidationException("Joins between historical data streams are not supported");
                }
                hasHistorical = true;
                if (streamTypes.length > 2)
                {
                    throw new ExprValidationException("Joins between historical data require a only one event stream in the join");
                }
            }
        }

        EventTable[][] indexes;
        QueryStrategy[] queryStrategies;

        // Handle a join with a database or other historical data source
        if (hasHistorical)
        {
            Pair<EventTable[][], QueryStrategy[]> indexAndStrategies =
                    makeComposerHistorical(outerJoinDescList, optionalFilterNode, streamTypes, streamViews);
            indexes = indexAndStrategies.getFirst();
            queryStrategies = indexAndStrategies.getSecond();

            return new JoinSetComposerImpl(indexes, queryStrategies, selectStreamSelectorEnum);
        }

        // Determine if any stream has a unidirectional keyword
        int unidirectionalStreamNumber = -1;
        for (int i = 0; i < isUnidirectional.length; i++)
        {
            if (isUnidirectional[i])
            {
                if (unidirectionalStreamNumber != -1)
                {
                    throw new ExprValidationException("The unidirectional keyword can only apply to one stream in a join");
                }
                unidirectionalStreamNumber = i;
            }
        }
        if ((unidirectionalStreamNumber != -1) && (hasChildViews[unidirectionalStreamNumber]))
        {
            throw new ExprValidationException("The unidirectional keyword requires that no views are declared onto the stream");
        }

        QueryPlan queryPlan = QueryPlanBuilder.getPlan(streamTypes, outerJoinDescList, optionalFilterNode, streamNames);

        // Build indexes
        QueryPlanIndex[] indexSpecs = queryPlan.getIndexSpecs();
        indexes = new EventTable[indexSpecs.length][];
        for (int streamNo = 0; streamNo < indexSpecs.length; streamNo++)
        {
            String[][] indexProps = indexSpecs[streamNo].getIndexProps();
            Class[][] coercionTypes = indexSpecs[streamNo].getCoercionTypesPerIndex();
            indexes[streamNo] = new EventTable[indexProps.length];
            for (int indexNo = 0; indexNo < indexProps.length; indexNo++)
            {
                indexes[streamNo][indexNo] = buildIndex(streamNo, indexProps[indexNo], coercionTypes[indexNo], streamTypes[streamNo]);
            }
        }

        // Build strategies
        QueryPlanNode[] queryExecSpecs = queryPlan.getExecNodeSpecs();
        queryStrategies = new QueryStrategy[queryExecSpecs.length];
        for (int i = 0; i < queryExecSpecs.length; i++)
        {
            QueryPlanNode planNode = queryExecSpecs[i];
            ExecNode executionNode = planNode.makeExec(indexes, streamTypes);

            if (log.isDebugEnabled())
            {
                log.debug(".makeComposer Execution nodes for stream " + i + " '" + streamNames[i] +
                    "' : \n" + ExecNode.print(executionNode));
            }

            queryStrategies[i] = new ExecNodeQueryStrategy(i, streamTypes.length, executionNode);
        }

        // If all streams have views, normal business is a query plan for each stream
        if (unidirectionalStreamNumber == -1)
        {
            return new JoinSetComposerImpl(indexes, queryStrategies, selectStreamSelectorEnum);
        }
        else
        {
            return new JoinSetComposerStreamToWinImpl(indexes, unidirectionalStreamNumber, queryStrategies[unidirectionalStreamNumber]);
        }
    }

    private Pair<EventTable[][], QueryStrategy[]> makeComposerHistorical(List<OuterJoinDesc> outerJoinDescList,
                                                                         ExprNode optionalFilterNode,
                                                                         EventType[] streamTypes,
                                                                         Viewable[] streamViews)
            throws ExprValidationException
    {
        EventTable[][] indexes;
        QueryStrategy[] queryStrategies;

        // No tables for any streams
        indexes = new EventTable[streamTypes.length][];
        queryStrategies = new QueryStrategy[streamTypes.length];
        for (int streamNo = 0; streamNo < streamTypes.length; streamNo++)
        {
            indexes[streamNo] = new EventTable[0];
        }

        int polledView = 0;
        int streamView = 1;
        if (streamViews[1] instanceof HistoricalEventViewable)
        {
            streamView = 0;
            polledView = 1;
        }

        // Build an outer join expression node
        boolean isOuterJoin = false;
        ExprEqualsNode outerJoinEqualsNode = null;
        if (!outerJoinDescList.isEmpty())
        {
            OuterJoinDesc outerJoinDesc = outerJoinDescList.get(0);
            if (outerJoinDesc.getOuterJoinType().equals(OuterJoinType.FULL))
            {
                isOuterJoin = true;
            }
            else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.LEFT)) &&
                    (streamView == 0))
            {
                    isOuterJoin = true;
            }
            else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.RIGHT)) &&
                    (streamView == 1))
            {
                    isOuterJoin = true;
            }

            outerJoinEqualsNode = new ExprEqualsNode(false);
            outerJoinEqualsNode.addChildNode(outerJoinDesc.getLeftNode());
            outerJoinEqualsNode.addChildNode(outerJoinDesc.getRightNode());
            outerJoinEqualsNode.validate(null, null, null, null, null);
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
                determineIndexing(filterForIndexing, streamTypes[polledView], streamTypes[streamView], polledView, streamView);

        HistoricalEventViewable viewable = (HistoricalEventViewable) streamViews[polledView];
        queryStrategies[streamView] = new HistoricalDataQueryStrategy(streamView, polledView, viewable, isOuterJoin, outerJoinEqualsNode,
                indexStrategies.getFirst(), indexStrategies.getSecond());

        return new Pair<EventTable[][], QueryStrategy[]>(indexes, queryStrategies);
    }

    private Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy> determineIndexing(ExprNode filterForIndexing,
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

        // index and key property names
        String[] keyPropertiesJoin = queryGraph.getKeyProperties(streamViewStreamNum, polledViewStreamNum);
        String[] indexPropertiesJoin = queryGraph.getIndexProperties(streamViewStreamNum, polledViewStreamNum);

        // If the analysis revealed no join columns, must use the brute-force full table scan
        if ((keyPropertiesJoin == null) || (keyPropertiesJoin.length == 0))
        {
            return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(
                            new HistoricalIndexLookupStrategyNoIndex(), new PollResultIndexingStrategyNoIndex());
        }

        // Build a set of index descriptors with property name and coercion type
        boolean mustCoerce = false;
        Class[] coercionTypes = new Class[indexPropertiesJoin.length];
        for (int i = 0; i < keyPropertiesJoin.length; i++)
        {
            Class keyPropType = JavaClassHelper.getBoxedType(streamViewType.getPropertyType(keyPropertiesJoin[i]));
            Class indexedPropType = JavaClassHelper.getBoxedType(polledViewType.getPropertyType(indexPropertiesJoin[i]));
            Class coercionType = indexedPropType;
            if (keyPropType != indexedPropType)
            {
                coercionType = JavaClassHelper.getCompareToCoercionType(keyPropType, keyPropType);
                mustCoerce = true;
            }

            coercionTypes[i] = coercionType;
        }

        // No coercion
        if (!mustCoerce)
        {
            PollResultIndexingStrategyIndex indexing = new PollResultIndexingStrategyIndex(polledViewStreamNum, polledViewType, indexPropertiesJoin);
            HistoricalIndexLookupStrategy strategy = new HistoricalIndexLookupStrategyIndex(streamViewType, keyPropertiesJoin);
            return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(strategy, indexing);
        }

        // With coercion, same lookup strategy as the index coerces
        PollResultIndexingStrategy indexing = new PollResultIndexingStrategyIndexCoerce(polledViewStreamNum, polledViewType, indexPropertiesJoin, coercionTypes);
        HistoricalIndexLookupStrategy strategy = new HistoricalIndexLookupStrategyIndex(streamViewType, keyPropertiesJoin);
        return new Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy>(strategy, indexing);
    }

    /**
     * Build an index/table instance using the event properties for the event type.
     * @param indexedStreamNum - number of stream indexed
     * @param indexProps - properties to index
     * @param optCoercionTypes - optional array of coercion types, or null if no coercion is required
     * @param eventType - type of event to expect
     * @return table build
     */
    protected static EventTable buildIndex(int indexedStreamNum, String[] indexProps, Class[] optCoercionTypes, EventType eventType)
    {
        EventTable table = null;
        if (indexProps.length == 0)
        {
            table = new UnindexedEventTable(indexedStreamNum);
        }
        else
        {
            if (optCoercionTypes == null)
            {
                table = new PropertyIndexedEventTable(indexedStreamNum, eventType, indexProps);
            }
            else
            {
                table = new PropertyIndTableCoerceAll(indexedStreamNum, eventType, indexProps, optCoercionTypes);
            }

        }
        return table;
    }

    private static final Log log = LogFactory.getLog(JoinSetComposerFactoryImpl.class);
}
