package net.esper.eql.join.plan;

import net.esper.type.OuterJoinType;

/**
 * Builds a query plan for the simple 2-stream scenario.
 */
public class TwoStreamQueryPlanBuilder
{
    /**
     * Build query plan.
     * @param queryGraph - navigability info
     * @param optionalOuterJoinType - outer join type, null if not an outer join
     * @return query plan
     */
    public static QueryPlan build(QueryGraph queryGraph, OuterJoinType optionalOuterJoinType)
    {
        QueryPlanIndex[] indexSpecs = new QueryPlanIndex[2];
        QueryPlanNode[] execNodeSpecs = new QueryPlanNode[2];

        TableLookupPlan lookupPlans[] = new TableLookupPlan[2];

        // Without navigability, use full set indexes and lookup without key use
        if (!queryGraph.isNavigable(0, 1))
        {
            String[][] unkeyedIndexProps = new String[][] {new String[0]};

            indexSpecs[0] = new QueryPlanIndex(unkeyedIndexProps);
            indexSpecs[1] = new QueryPlanIndex(unkeyedIndexProps);
            lookupPlans[0] = new FullTableScanLookupPlan(0, 1, 0);
            lookupPlans[1] = new FullTableScanLookupPlan(1, 0, 0);
        }
        else
        {
            indexSpecs[0] = new QueryPlanIndex(new String[][] {queryGraph.getIndexProperties(1, 0)});
            indexSpecs[1] = new QueryPlanIndex(new String[][] {queryGraph.getIndexProperties(0, 1)});
            lookupPlans[0] = new IndexedTableLookupPlan(0, 1, 0, queryGraph.getKeyProperties(0, 1));
            lookupPlans[1] = new IndexedTableLookupPlan(1, 0, 0, queryGraph.getKeyProperties(1, 0));
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
