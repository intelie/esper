using System;

using net.esper.compat;
using net.esper.events;
using net.esper.type;

namespace net.esper.eql.join.plan
{
	/// <summary>
    /// Builds a query plan for the simple 2-stream scenario.
    /// </summary>
	
    public class TwoStreamQueryPlanBuilder
	{
	    /**
	     * Build query plan.
	     * @param queryGraph - navigability info
	     * @param optionalOuterJoinType - outer join type, null if not an outer join
	     * @param typesPerStream - event types for each stream
	     * @return query plan
	     */
		public static QueryPlan Build(EventType[] typesPerStream, QueryGraph queryGraph, OuterJoinType optionalOuterJoinType)
		{
			QueryPlanIndex[] indexSpecs = new QueryPlanIndex[2];
			QueryPlanNode[] execNodeSpecs = new QueryPlanNode[2];
			
			TableLookupPlan[] lookupPlans = new TableLookupPlan[2];
			
			// Without navigability, use full set indexes and lookup without key use
			if (!queryGraph.IsNavigable(0, 1))
			{
				String[][] unkeyedIndexProps = new String[][]{new String[0]};
				
				indexSpecs[0] = new QueryPlanIndex(unkeyedIndexProps, new Type[][] {null});
				indexSpecs[1] = new QueryPlanIndex(unkeyedIndexProps, new Type[][] {null});
				lookupPlans[0] = new FullTableScanLookupPlan(0, 1, 0);
				lookupPlans[1] = new FullTableScanLookupPlan(1, 0, 0);
			}
			else
			{
	            String[] keyProps = queryGraph.GetKeyProperties(0, 1);
	            String[] indexProps = queryGraph.GetIndexProperties(0, 1);
	            Type[] coercionTypes = GetCoercionTypes(typesPerStream, 0, 1, keyProps, indexProps);

	            indexSpecs[0] = new QueryPlanIndex(new String[][] {queryGraph.GetIndexProperties(1, 0)}, new Type[][] {coercionTypes});
	            indexSpecs[1] = new QueryPlanIndex(new String[][] {queryGraph.GetIndexProperties(0, 1)}, new Type[][] {coercionTypes});
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
		
	    /**
	     * Returns null if no coercion is required, or an array of classes for use in coercing the
	     * lookup keys and index keys into a common type.
	     * @param typesPerStream is the event types for each stream
	     * @param lookupStream is the stream looked up from
	     * @param indexedStream is the indexed stream
	     * @param keyProps is the properties to use to look up
	     * @param indexProps is the properties to index on
	     * @return coercion types, or null if none required
	     */
	    protected static Type[] getCoercionTypes(EventType[] typesPerStream,
												 int lookupStream,
	                                             int indexedStream,
	                                             String[] keyProps,
	                                             String[] indexProps)
	    {
	        // Determine if any coercion is required
	        if (indexProps.Length != keyProps.Length)
	        {
	            throw new IllegalStateException("Mismatch in the number of key and index properties");
	        }

	        Type[] coercionTypes = new Type[indexProps.Length];
	        bool mustCoerce = false;
	        for (int i = 0; i < keyProps.Length; i++)
	        {
	            Type keyPropType = TypeHelper.GetBoxedType(typesPerStream[lookupStream].GetPropertyType(keyProps[i]));
	            Type indexedPropType = TypeHelper.GetBoxedType(typesPerStream[indexedStream].GetPropertyType(indexProps[i]));
	            Type coercionType = indexedPropType;
	            if (keyPropType != indexedPropType)
	            {
	                coercionType = TypeHelper.GetCompareToCoercionType(keyPropType, indexedPropType);
	                mustCoerce = true;
	            }
	            coercionTypes[i] = coercionType;
	        }
	        if (!mustCoerce)
	        {
	            return null;
	        }
	        return coercionTypes;
	    }
	}
}
