using System;

using net.esper.compat;
using net.esper.events;
using net.esper.type;
using net.esper.util;

namespace net.esper.eql.join.plan
{
	/// <summary>
    /// Builds a query plan for the simple 2-stream scenario.
    /// </summary>
	
    public class TwoStreamQueryPlanBuilder
	{
		///<summary>
        /// Build query plan.
		///</summary>
        ///<param name="typesPerStream">event types for each stream</param>
        ///<param name="queryGraph">navigability info</param>
        ///<param name="optionalOuterJoinType">outer join type, null if not an outer join</param>
		///<returns></returns>
		public static QueryPlan Build(EventType[] typesPerStream, QueryGraph queryGraph, OuterJoinType? optionalOuterJoinType)
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
				if ((optionalOuterJoinType == OuterJoinType.LEFT) ||
                    (optionalOuterJoinType == OuterJoinType.FULL))
				{
					execNodeSpecs[0] = new TableOuterLookupNode(lookupPlans[0]);
				}
				
                if ((optionalOuterJoinType == OuterJoinType.RIGHT) ||
                    (optionalOuterJoinType == OuterJoinType.FULL))
				{
					execNodeSpecs[1] = new TableOuterLookupNode(lookupPlans[1]);
				}
			}
			
			return new QueryPlan(indexSpecs, execNodeSpecs);
		}
		
	    ///<summary>
        /// Returns null if no coercion is required, or an array of classes for use in coercing the
        /// lookup keys and index keys into a common type.
	    ///</summary>
        ///<param name="typesPerStream">the event types for each stream</param>
        ///<param name="lookupStream">the stream looked up from</param>
        ///<param name="indexedStream">the indexed stream</param>
        ///<param name="keyProps">the properties to use to look up</param>
        ///<param name="indexProps">the properties to index on</param>
        ///<returns>coercion types, or null if none required</returns>
	    ///<exception cref="IllegalStateException"></exception>
	    public static Type[] GetCoercionTypes(EventType[] typesPerStream,
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
