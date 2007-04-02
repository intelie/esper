using System;

using net.esper.type;

namespace net.esper.eql.join.plan
{
	/// <summary>
    /// Builds a query plan for the simple 2-stream scenario.
    /// </summary>
	
    public class TwoStreamQueryPlanBuilder
	{
		/// <summary> Build query plan.</summary>
		/// <param name="queryGraph">navigability info
		/// </param>
		/// <param name="optionalOuterJoinType">outer join type, null if not an outer join
		/// </param>
		/// <returns> query plan
		/// </returns>
		public static QueryPlan Build(QueryGraph queryGraph, OuterJoinType? optionalOuterJoinType)
		{
			QueryPlanIndex[] indexSpecs = new QueryPlanIndex[2];
			QueryPlanNode[] execNodeSpecs = new QueryPlanNode[2];
			
			TableLookupPlan[] lookupPlans = new TableLookupPlan[2];
			
			// Without navigability, use full set indexes and lookup without key use
			if (!queryGraph.IsNavigable(0, 1))
			{
				String[][] unkeyedIndexProps = new String[][]{new String[0]};
				
				indexSpecs[0] = new QueryPlanIndex(unkeyedIndexProps);
				indexSpecs[1] = new QueryPlanIndex(unkeyedIndexProps);
				lookupPlans[0] = new FullTableScanLookupPlan(0, 1, 0);
				lookupPlans[1] = new FullTableScanLookupPlan(1, 0, 0);
			}
			else
			{
				indexSpecs[0] = new QueryPlanIndex(new String[][]{queryGraph.GetIndexProperties(1, 0)});
				indexSpecs[1] = new QueryPlanIndex(new String[][]{queryGraph.GetIndexProperties(0, 1)});
				lookupPlans[0] = new IndexedTableLookupPlan(0, 1, 0, queryGraph.GetKeyProperties(0, 1));
				lookupPlans[1] = new IndexedTableLookupPlan(1, 0, 0, queryGraph.GetKeyProperties(1, 0));
			}
			
			execNodeSpecs[0] = new TableLookupNode(lookupPlans[0]);
			execNodeSpecs[1] = new TableLookupNode(lookupPlans[1]);
			
			if (optionalOuterJoinType != null)
			{
                OuterJoinType _optionalOuterJoinType = optionalOuterJoinType.Value;

				if ((_optionalOuterJoinType == OuterJoinType.LEFT) ||
                    (_optionalOuterJoinType == OuterJoinType.FULL))
				{
					execNodeSpecs[0] = new TableOuterLookupNode(lookupPlans[0]);
				}
				
                if ((_optionalOuterJoinType == OuterJoinType.RIGHT) ||
                    (_optionalOuterJoinType == OuterJoinType.FULL))
				{
					execNodeSpecs[1] = new TableOuterLookupNode(lookupPlans[1]);
				}
			}
			
			return new QueryPlan(indexSpecs, execNodeSpecs);
		}
	}
}