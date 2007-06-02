using System;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.eql.join.plan
{
	/// <summary> 
	/// 2 Stream query strategy/execution tree
	/// (stream 0)         Lookup in stream 1
	/// (stream 1)         Lookup in stream 0
	/// ------ Example 1   a 3 table join
	/// 
	/// " where streamA.id = streamB.id " +
	/// "   and streamB.id = streamC.id";
	/// => Index propery names for each stream
	/// for stream 0 to 4 = "id"
	/// => join order, ie.
	/// for stream 0 = {1, 2}
	/// for stream 1 = {factor [0,2]}
	/// for stream 2 = {1, 0}
	/// => IndexKeyGen optionalIndexKeyGen, created by nested query plan nodes
	/// 3 Stream query strategy
	/// (stream 0)          Nested iteration
	/// Lookup in stream 1        Lookup in stream 2
	/// (stream 1)         Factor
	/// Lookup in stream 0        Lookup in stream 2
	/// (stream 2)         Nested iteration
	/// Lookup in stream 1        Lookup in stream 0
	/// ------ Example 2  a 4 table join
	/// 
	/// " where streamA.id = streamB.id " +
	/// "   and streamB.id = streamC.id";
	/// "   and streamC.id = streamD.id";
	/// => join order, ie.
	/// for stream 0 = {1, 2, 3}
	/// for stream 1 = {factor [0,2], use 2 for 3}
	/// for stream 2 = {factor [1,3], use 1 for 0}
	/// for stream 3 = {2, 1, 0}
	/// concepts... nested iteration, inner loop
	/// select * from s1, s2, s3, s4 where s1.id=s2.id and s2.id=s3.id and s3.id=s4.id
	/// (stream 0)              Nested iteration
	/// Lookup in stream 1        Lookup in stream 2        Lookup in stream 3
	/// (stream 1)              Factor
	/// lookup in stream 0                 Nested iteration
	/// Lookup in stream 2        Lookup in stream 3
	/// (stream 2)              Factor
	/// lookup in stream 3                 Nested iteration
	/// Lookup in stream 1        Lookup in stream 0
	/// (stream 3)              Nested iteration
	/// Lookup in stream 2        Lookup in stream 1        Lookup in stream 0
	/// ------ Example 4  a 4 table join, orphan table
	/// 
	/// " where streamA.id = streamB.id " +
	/// "   and streamB.id = streamC.id"; (no table D join criteria)
	/// ------ Example 5  a 3 table join with 2 indexes for stream B
	/// 
	/// " where streamA.A1 = streamB.B1 " +
	/// "   and streamB.B2 = streamC.C1"; (no table D join criteria)
    /// <para>
	/// Builds a query plan for 3 or more streams in a join.
    /// </para>
    /// </summary>
	public class NStreamQueryPlanBuilder
	{
        /// <summary>
        /// Build a query plan based on the stream property relationships indicated in queryGraph.
        /// </summary>
        /// <param name="queryGraph">navigation info between streams</param>
        /// <returns>query plan</returns>
		public static QueryPlan Build(QueryGraph queryGraphqueryGraph, EventType[] typesPerStream)
		{
			log.Debug(".build queryGraph=" + queryGraph);
			
			int numStreams = queryGraph.NumStreams;
			QueryPlanIndex[] indexSpecs = QueryPlanIndexBuilder.BuildIndexSpec(queryGraph);
			log.Debug(".build Index build completed, indexes=" + QueryPlanIndex.Print(indexSpecs));
			
			QueryPlanNode[] planNodeSpecs = new QueryPlanNode[numStreams];
			for (int streamNo = 0; streamNo < numStreams; streamNo++)
			{
				BestChainResult bestChainResult = ComputeBestPath(streamNo, queryGraph);
				int[] bestChain = bestChainResult.Chain;
				log.Debug(".build For stream " + streamNo + " bestChain=" + CollectionHelper.Render(bestChain));
				
				planNodeSpecs[streamNo] = CreateStreamPlan(streamNo, bestChain, queryGraph, indexSpecs, typesPerStream);
				log.Debug(".build spec=" + planNodeSpecs[streamNo]);
			}
			
			return new QueryPlan(indexSpecs, planNodeSpecs);
		}

	    /**
	     * Walks the chain of lookups and constructs lookup strategy and plan specification based
	     * on the index specifications.
	     * @param lookupStream - the stream to construct the query plan for
	     * @param bestChain - the chain that the lookup follows to make best use of indexes
	     * @param queryGraph - the repository for key properties to indexes
	     * @param indexSpecsPerStream - specifications of indexes
	     * @param typesPerStream - event types for each stream
	     * @return NestedIterationNode with lookups attached underneath
	     */
	    protected static QueryPlanNode CreateStreamPlan(
			int lookupStream,
			int[] bestChain,
			QueryGraph queryGraph,
			QueryPlanIndex[] indexSpecsPerStream,
			EventType[] typesPerStream)
	    {
			NestedIterationNode nestedIterNode = new NestedIterationNode(bestChain);
			int currentLookupStream = lookupStream;
			
			// Walk through each successive lookup
			for (int i = 0; i < bestChain.Length; i++)
			{
				int indexedStream = bestChain[i];

				TableLookupPlan tableLookupPlan = CreateLookupPlan(queryGraph, currentLookupStream, indexedStream, indexSpecsPerStream[indexedStream], typesPerStream);
				TableLookupNode tableLookupNode = new TableLookupNode(tableLookupPlan);
				nestedIterNode.AddChildNode(tableLookupNode);
				
				currentLookupStream = bestChain[i];
			}
			
			return nestedIterNode;
		}

	    /**
	     * Create the table lookup plan for a from-stream to look up in an indexed stream
	     * using the columns supplied in the query graph and looking at the actual indexes available
	     * and their index number.
	     * @param queryGraph - contains properties joining the 2 streams
	     * @param currentLookupStream - stream to use key values from
	     * @param indexedStream - stream to look up in
	     * @param indexSpecs - index specification defining indexes to be created for stream
	     * @param typesPerStream - event types for each stream
	     * @return plan for performing a lookup in a given table using one of the indexes supplied
	     */
	    protected static TableLookupPlan CreateLookupPlan(
			QueryGraph queryGraph,
			int currentLookupStream,
			int indexedStream,
			QueryPlanIndex indexSpecs, 
			EventType[] typesPerStream)
	    {
			String[] indexedStreamIndexProps = queryGraph.GetIndexProperties(currentLookupStream, indexedStream);
			int indexNum = - 1;
			
			// We use an index if there are index properties for the 2 streams
			TableLookupPlan tableLookupPlan ;
			
			if (indexedStreamIndexProps != null)
			{
				// Determine the index number assigned by looking at the index specifications
				indexNum = indexSpecs.GetIndexNum(indexedStreamIndexProps);
				
				// Constructed keyed lookup strategy
				String[] keyGenFields = queryGraph.GetKeyProperties(currentLookupStream, indexedStream);
				tableLookupPlan = new IndexedTableLookupPlan(currentLookupStream, indexedStream, indexNum, keyGenFields);

	            // Determine coercion required
	            Type[] coercionTypes = TwoStreamQueryPlanBuilder.GetCoercionTypes(typesPerStream, currentLookupStream, indexedStream, keyGenFields, indexedStreamIndexProps);
	            if (coercionTypes != null)
	            {
	                // check if there already are coercion types for this index
	                Type[] existCoercionTypes = indexSpecs.GetCoercionTypes(indexedStreamIndexProps);
	                if (existCoercionTypes != null)
	                {
	                    for (int i = 0; i < existCoercionTypes.Length; i++)
	                    {
	                        coercionTypes[i] = TypeHelper.GetCompareToCoercionType(existCoercionTypes[i], coercionTypes[i]);
	                    }
	                }
	                indexSpecs.SetCoercionTypes(indexedStreamIndexProps, coercionTypes);
	            }
			}
			else
			{
				// We don't use a keyed index but use the full stream set as the stream does not have any indexes
				indexNum = indexSpecs.GetIndexNum(new String[0]);
				
				// If no such full set index exists yet, add to specs
				if (indexNum == - 1)
				{
					indexNum = indexSpecs.AddIndex(new String[0], null);
				}
				
				tableLookupPlan = new FullTableScanLookupPlan(currentLookupStream, indexedStream, indexNum);
			}
			
			return tableLookupPlan;
		}


        /// <summary>
        /// Compute a best chain or path for lookups to take for the lookup stream passed in and the query
        /// property relationships.
        /// The method runs through all possible permutations of lookup path <seealso cref="NumberSetPermutationEnumeration"/>
        /// until a path is found in which all streams can be accessed via an index.
        /// If not such path is found, the method returns the path with the greatest depth, ie. where
        /// the first one or more streams are index accesses.
        /// If no depth other then zero is found, returns the default nesting order.
        /// </summary>
        /// <param name="lookupStream">stream to Start look up</param>
        /// <param name="queryGraph">navigability between streams</param>
        /// <returns>chain and chain depth</returns>
		public static BestChainResult ComputeBestPath(int lookupStream, QueryGraph queryGraph)
		{
			int[] defNestingorder = BuildDefaultNestingOrder(queryGraph.NumStreams, lookupStream);
			NumberSetPermutationEnumeration permutations = new NumberSetPermutationEnumeration(defNestingorder);
			int[] bestPermutation = null;
			int bestDepth = - 1;
			
			while (permutations.MoveNext())
			{
				int[] permutation = permutations.Current;
				int permutationDepth = ComputeNavigableDepth(lookupStream, permutation, queryGraph);
				
				if (permutationDepth > bestDepth)
				{
					bestPermutation = permutation;
					bestDepth = permutationDepth;
				}
				
				// Stop when the permutation yielding the full depth (length of stream chain) was hit
				if (permutationDepth == queryGraph.NumStreams - 1)
				{
					break;
				}
			}
			
			return new BestChainResult(bestDepth, bestPermutation);
		}

        /// <summary>
        /// Given a chain of streams to look up and indexing information, compute the index within the
        /// chain of the first non-index lookup.
        /// </summary>
        /// <param name="lookupStream">stream to Start lookup for</param>
        /// <param name="nextStreams">list of stream numbers next in lookup</param>
        /// <param name="queryGraph">indexing information</param>
        /// <returns>
        /// value between 0 and (nextStreams.length - 1)
        /// </returns>
		public static int ComputeNavigableDepth(int lookupStream, int[] nextStreams, QueryGraph queryGraph)
		{
			int currentStream = lookupStream;
			int currentDepth = 0;
			
			for (int i = 0; i < nextStreams.Length; i++)
			{
				int nextStream = nextStreams[i];
				if (!queryGraph.IsNavigable(currentStream, nextStream))
				{
					break;
				}
				currentStream = nextStream;
				currentDepth++;
			}
			
			return currentDepth;
		}

        /// <summary>
        /// Returns query plan based on all unindexed full table lookups and lookups based
        /// on a simple nesting order.
        /// </summary>
        /// <param name="eventTypes">stream event types</param>
        /// <returns>query plan</returns>
		public static QueryPlan BuildNStreamDefaultQuerySpec(EventType[] eventTypes)
		{
			QueryPlanIndex[] indexSpecs = new QueryPlanIndex[eventTypes.Length];
			QueryPlanNode[] execNodeSpecs = new QueryPlanNode[eventTypes.Length];
			
			// Build indexes without key properties
			for (int i = 0; i < indexSpecs.Length; i++)
			{
				indexSpecs[i] = new QueryPlanIndex(null, null);
			}
			
			// Handle N-stream queries
			for (int streamNo = 0; streamNo < eventTypes.Length; streamNo++)
			{
				int[] nestingOrder = BuildDefaultNestingOrder(eventTypes.Length, streamNo);
				NestedIterationNode nestedNode = new NestedIterationNode(nestingOrder);
				execNodeSpecs[streamNo] = nestedNode;
				int lookupStream = streamNo;
				
				for (int j = 0; j < nestingOrder.Length; j++)
				{
					int indexedStream = nestingOrder[j];
					FullTableScanLookupPlan scanLookupStrategy = new FullTableScanLookupPlan(lookupStream, indexedStream, 0);
					nestedNode.AddChildNode(new TableLookupNode(scanLookupStrategy));
					lookupStream = indexedStream;
				}
			}
			
			return new QueryPlan(indexSpecs, execNodeSpecs);
		}

	    private static Type[] getCoercionTypes(
			EventType[] typesPerStream,
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

	        Type[] coercionTypes = new Type[indexProps.length];
	        bool mustCoerce = false;
	        for (int i = 0; i < keyProps.Length; i++)
	        {
	            Type keyPropType = TypeHelper.GetBoxedType(typesPerStream[lookupStream].GetPropertyType(keyProps[i]));
	            Type indexedPropType = TypeHelper.GetBoxedType(typesPerStream[indexedStream].GetPropertyType(indexProps[i]));
	            Type coercionType = indexedPropType;
	            if (keyPropType != indexedPropType)
	            {
	                coercionType = TypeHelper.GetCompareToCoercionType(keyPropType, keyPropType);
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
		
        /// <summary>
        /// Returns default nesting order for a given number of streams for a certain stream.
        /// Example: numStreams = 5, forStream = 2, result = {0, 1, 3, 4}
        /// The resulting array has all streams except the forStream, in ascdending order.
        /// </summary>
        /// <param name="numStreams">number of streams</param>
        /// <param name="forStream">stream to generate a nesting order for</param>
        /// <returns>
        /// int array with all stream numbers Starting at 0 to (numStreams - 1) leaving the
        /// forStream out
        /// </returns>
		public static int[] BuildDefaultNestingOrder(int numStreams, int forStream)
		{
			int[] nestingOrder = new int[numStreams - 1];
			
			int count = 0;
			for (int i = 0; i < numStreams; i++)
			{
				if (i == forStream)
				{
					continue;
				}
				nestingOrder[count++] = i;
			}
			
			return nestingOrder;
		}
		
		/// <summary> Encapsulates the chain information.</summary>
		public class BestChainResult
		{
            /// <summary>
            /// Returns depth of lookups via index in chain.
            /// </summary>
            /// <value>The depth.</value>
            /// <returns> depth
            /// </returns>
			virtual public int Depth
			{
				get
				{
					return depth;
				}				
			}
            /// <summary>
            /// Returns chain of stream numbers.
            /// </summary>
            /// <value>The chain.</value>
            /// <returns> array of stream numbers
            /// </returns>
			virtual public int[] Chain
			{
				get
				{
					return chain;
				}
				
			}
			private int depth;
			private int[] chain;
			
			/// <summary> Ctor.</summary>
			/// <param name="depth">depth this chain resolves into a indexed lookup
			/// </param>
			/// <param name="chain">chain for nested lookup
			/// </param>
			public BestChainResult(int depth, int[] chain)
			{
				this.depth = depth;
				this.chain = chain;
			}

            /// <summary>
            /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
            /// </summary>
            /// <returns>
            /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
            /// </returns>
			public override String ToString()
			{
				return "depth=" + depth + " chain=" + CollectionHelper.Render(chain);
			}
		}
		
		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
