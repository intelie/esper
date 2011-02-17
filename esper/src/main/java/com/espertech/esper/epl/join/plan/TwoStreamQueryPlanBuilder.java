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

        // not navigable, full table scan
        if (!queryGraph.isNavigableAtAll(0, 1)) {

            indexSpecs[0] = QueryPlanIndex.makeIndex(new QueryPlanIndexItem(null, null, null, null));
            indexSpecs[1] = QueryPlanIndex.makeIndex(new QueryPlanIndexItem(null, null, null, null));
            lookupPlans[0] = new FullTableScanLookupPlan(0, 1, indexSpecs[1].getFirstIndexNum());
            lookupPlans[1] = new FullTableScanLookupPlan(1, 0, indexSpecs[0].getFirstIndexNum());
        }
        else {
            String[] keyProps = queryGraph.getKeyProperties(0, 1);
            String[] indexProps = queryGraph.getIndexProperties(0, 1);
            CoercionDesc keyCoercionTypes = CoercionUtil.getCoercionTypes(typesPerStream, 0, 1, keyProps, indexProps);

            QueryGraphValue valueZeroOne = queryGraph.getGraphValue(0, 1, false);
            QueryGraphValue valueOneZero = queryGraph.getGraphValue(1, 0, false);
            String[] oneRangeIndexedProps = QueryGraphValueRange.getPropertyNamesValues(valueZeroOne.getRangeEntries());
            String[] zeroRangeIndexedProps = QueryGraphValueRange.getPropertyNamesValues(valueOneZero.getRangeEntries());
            CoercionDesc oneRangeCoercionTypes = CoercionUtil.getCoercionTypes(typesPerStream, 0, 1, valueZeroOne.getRangeEntries());
            CoercionDesc zeroRangeCoercionTypes = CoercionUtil.getCoercionTypes(typesPerStream, 1, 0, valueOneZero.getRangeEntries());

            QueryPlanIndexItem itemZero = new QueryPlanIndexItem(queryGraph.getIndexProperties(1, 0), keyCoercionTypes.isCoerce() ? keyCoercionTypes.getCoercionTypes() : null, zeroRangeIndexedProps,
                    zeroRangeCoercionTypes.isCoerce() ? zeroRangeCoercionTypes.getCoercionTypes() : null);
            indexSpecs[0] = QueryPlanIndex.makeIndex(itemZero);
            QueryPlanIndexItem itemOne = new QueryPlanIndexItem(queryGraph.getIndexProperties(0, 1), keyCoercionTypes.isCoerce() ? keyCoercionTypes.getCoercionTypes() : null, oneRangeIndexedProps, 
                    oneRangeCoercionTypes.isCoerce() ? oneRangeCoercionTypes.getCoercionTypes() : null);
            indexSpecs[1] = QueryPlanIndex.makeIndex(itemOne);

            String indexOneName = indexSpecs[1].getFirstIndexNum();
            String indexZeroName = indexSpecs[0].getFirstIndexNum();

            // straight no-range case means direct index lookup
            if (valueZeroOne.getRangeEntries().isEmpty()) {
                lookupPlans[0] = new IndexedTableLookupPlan(0, 1, indexOneName, queryGraph.getKeyProperties(0, 1));
                lookupPlans[1] = new IndexedTableLookupPlan(1, 0, indexZeroName, queryGraph.getKeyProperties(1, 0));
            }
            // we have ranges
            else {
                // stream zero-to-one
                if (keyProps.length == 0 && valueZeroOne.getRangeEntries().size() == 1) {
                    List<QueryGraphValueRange> zeroRangeKeyPairs = valueZeroOne.getRangeEntries();
                    lookupPlans[0] = new SortedTableLookupPlan(0, 1, indexOneName, zeroRangeKeyPairs.get(0));
                }
                else {
                    List<QueryGraphValueRange> zeroRangeKeyPairs = valueZeroOne.getRangeEntries();
                    lookupPlans[0] = new CompositeTableLookupPlan(0, 1, indexOneName, queryGraph.getKeyProperties(0, 1), zeroRangeKeyPairs);
                }

                // stream one-to-zero
                if (keyProps.length == 0 && valueOneZero.getRangeEntries().size() == 1) {
                    List<QueryGraphValueRange> oneRangeKeyPairs = valueOneZero.getRangeEntries();
                    lookupPlans[1] = new SortedTableLookupPlan(1, 0, indexZeroName, oneRangeKeyPairs.get(0));
                }
                else {
                    List<QueryGraphValueRange> oneRangeKeyPairs = valueOneZero.getRangeEntries();
                    lookupPlans[1] = new CompositeTableLookupPlan(1, 0, indexZeroName, queryGraph.getKeyProperties(1, 0), oneRangeKeyPairs);
                }
            }                
        }
        
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
}
