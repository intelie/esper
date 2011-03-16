/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.type.OuterJoinType;

import java.util.List;

/**
 * Builds a query plan for the simple 2-stream scenario.
 */
public class TwoStreamQueryPlanBuilder
{
    /**
     * Build query plan.
     * @param queryGraph - navigability info
     * @param optionalOuterJoinType - outer join type, null if not an outer join
     * @param typesPerStream - event types for each stream
     * @return query plan
     */
    public static QueryPlan build(EventType[] typesPerStream, QueryGraph queryGraph, OuterJoinType optionalOuterJoinType)
    {
        QueryPlanIndex[] indexSpecs = new QueryPlanIndex[2];
        QueryPlanNode[] execNodeSpecs = new QueryPlanNode[2];

        TableLookupPlan lookupPlans[] = new TableLookupPlan[2];

        // plan lookup from 1 to zero
        Pair<QueryPlanIndex, TableLookupPlan> plan = planQuery(1, 0, typesPerStream, queryGraph);
        indexSpecs[0] = plan.getFirst();
        lookupPlans[1] = plan.getSecond();

        // plan lookup from zero to 1
        plan = planQuery(0, 1, typesPerStream, queryGraph);
        indexSpecs[1] = plan.getFirst();
        lookupPlans[0] = plan.getSecond();

        execNodeSpecs[0] = new TableLookupNode(lookupPlans[0]);
        execNodeSpecs[1] = new TableLookupNode(lookupPlans[1]);

        if (optionalOuterJoinType != null)
        {
            if ( (optionalOuterJoinType.equals(OuterJoinType.LEFT)) ||
                 (optionalOuterJoinType.equals(OuterJoinType.FULL)) )
            {
                execNodeSpecs[0] = new TableOuterLookupNode(lookupPlans[0]);
            }
            if ( (optionalOuterJoinType.equals(OuterJoinType.RIGHT)) ||
                 (optionalOuterJoinType.equals(OuterJoinType.FULL)) )
            {
                execNodeSpecs[1] = new TableOuterLookupNode(lookupPlans[1]);
            }
        }

        return new QueryPlan(indexSpecs, execNodeSpecs);
    }

    private static Pair<QueryPlanIndex, TableLookupPlan> planQuery(int lookupStream, int indexedStream, EventType[] typesPerStream, QueryGraph queryGraph) {

        // not navigable, full table scan
        if (!queryGraph.isNavigableAtAll(lookupStream, indexedStream)) {
            QueryPlanIndex index = QueryPlanIndex.makeIndex(new QueryPlanIndexItem(null, null, null, null));
            FullTableScanLookupPlan plan = new FullTableScanLookupPlan(lookupStream, indexedStream, index.getFirstIndexNum());
            return new Pair<QueryPlanIndex, TableLookupPlan>(index, plan);
        }

        QueryGraphValue queryGraphValue = queryGraph.getGraphValue(lookupStream, indexedStream);

        // determine hash coercion types - these are the same regardless of direction
        QueryGraphValuePairHashKeyIndex hashKeyIndexPair = queryGraphValue.getHashKeyProps();
        List<QueryGraphValueEntryHashKeyed> hashKeys = hashKeyIndexPair.getKeys();
        String[] hashIndexProps = hashKeyIndexPair.getIndexed();
        CoercionDesc hashCoercionTypesDesc = CoercionUtil.getCoercionTypesHash(typesPerStream, lookupStream, indexedStream, hashKeys, hashIndexProps);
        Class[] hashCoercionTypes = hashCoercionTypesDesc.isCoerce() ? hashCoercionTypesDesc.getCoercionTypes() : null;

        // determine range coercion types, these may not be the same
        QueryGraphValuePairRangeIndex rangeKeyIndexPair = queryGraphValue.getRangeProps();
        String[] rangeIndexedProps = rangeKeyIndexPair.getIndexed();
        CoercionDesc rangeCoercionTypeDesc = CoercionUtil.getCoercionTypesRange(typesPerStream, indexedStream, rangeIndexedProps, rangeKeyIndexPair.getKeys());
        Class[] rangeCoercionType = rangeCoercionTypeDesc.isCoerce() ? rangeCoercionTypeDesc.getCoercionTypes() : null;

        // build index description
        QueryPlanIndexItem indexItem = new QueryPlanIndexItem(hashIndexProps, hashCoercionTypes, rangeIndexedProps, rangeCoercionType);
        QueryPlanIndex queryPlanIndex = QueryPlanIndex.makeIndex(indexItem);
        String indexName = queryPlanIndex.getFirstIndexNum();

        // straight no-range case means direct index lookup
        TableLookupPlan lookupPlan = null;
        if (rangeKeyIndexPair.getKeys().isEmpty()) {
            if (hashKeyIndexPair.getKeys().size() == 1) {
                QueryGraphValueEntryHashKeyed first = hashKeyIndexPair.getKeys().get(0);
                lookupPlan = new IndexedTableLookupPlanSingle(lookupStream, indexedStream, indexName, first);
            }
            else {
                lookupPlan = new IndexedTableLookupPlanMulti(lookupStream, indexedStream, indexName, hashKeyIndexPair.getKeys());
            }
        }
        // we have ranges
        else {
            // stream zero-to-one
            if (hashKeyIndexPair.getKeys().isEmpty() && rangeKeyIndexPair.getKeys().size() == 1) {
                lookupPlan = new SortedTableLookupPlan(lookupStream, indexedStream, indexName, rangeKeyIndexPair.getKeys().get(0));
            }
            else {
                lookupPlan = new CompositeTableLookupPlan(lookupStream, indexedStream, indexName, hashKeyIndexPair.getKeys(), rangeKeyIndexPair.getKeys());
            }
        }
        return new Pair<QueryPlanIndex, TableLookupPlan>(queryPlanIndex, lookupPlan);
    }
}
