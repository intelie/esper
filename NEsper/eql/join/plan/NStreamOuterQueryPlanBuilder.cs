using System;
using System.Collections.Generic;
using System.IO;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.eql.join.assemble;
using net.esper.events;
using net.esper.type;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.join.plan
{
	/// <summary>
	/// Builds a query plan for 3 or more streams in a outer join.
	/// </summary>

	public class NStreamOuterQueryPlanBuilder
	{
	    /**
	     * Build a query plan based on the stream property relationships indicated in queryGraph.
	     * @param queryGraph - navigation info between streams
	     * @param streamNames - stream names or aliases
	     * @param outerJoinDescList - descriptors for all outer joins
	     * @param typesPerStream - event types for each stream
	     * @return query plan
	     */
	    protected static QueryPlan Build(QueryGraph queryGraph,
	                                     IList<OuterJoinDesc> outerJoinDescList,
	                                     String[] streamNames,
	                                     EventType[] typesPerStream)
	    {
            log.Debug(".Build queryGraph=" + queryGraph);

			int numStreams = queryGraph.NumStreams;
			QueryPlanNode[] planNodeSpecs = new QueryPlanNode[numStreams];

			// Build index specifications
			QueryPlanIndex[] indexSpecs = QueryPlanIndexBuilder.BuildIndexSpec( queryGraph );
            log.Debug(".Build Index build completed, indexes=" + QueryPlanIndex.Print(indexSpecs));

			// Build graph of the outer and inner joins
			OuterInnerDirectionalGraph outerInnerGraph = GraphOuterJoins( numStreams, outerJoinDescList );
            log.Debug(".Build directional graph=" + outerInnerGraph.Print());

			// For each stream determine the query plan
			for ( int streamNo = 0 ; streamNo < numStreams ; streamNo++ )
			{
				QueryPlanNode queryPlanNode = Build( numStreams, streamNo, streamNames, queryGraph, outerInnerGraph, indexSpecs, typesPerStream );

				if ( log.IsDebugEnabled )
				{
                    log.Debug(".Build spec for stream '" + streamNames[streamNo] +
							"' number " + streamNo + " is " + queryPlanNode );
				}

				planNodeSpecs[streamNo] = queryPlanNode;
			}

			QueryPlan queryPlan = new QueryPlan( indexSpecs, planNodeSpecs );
            log.Debug(".Build query plan=" + queryPlan.ToString());

			return queryPlan;
		}

        /// <summary>
        /// Builds the specified num streams.
        /// </summary>
        /// <param name="numStreams">The num streams.</param>
        /// <param name="streamNo">The stream no.</param>
        /// <param name="streamNames">The stream names.</param>
        /// <param name="queryGraph">The query graph.</param>
        /// <param name="outerInnerGraph">The outer inner graph.</param>
        /// <param name="indexSpecs">The index specs.</param>
        /// <returns></returns>
        public static QueryPlanNode Build(
			int numStreams,
			int streamNo,
			String[] streamNames,
			QueryGraph queryGraph,
			OuterInnerDirectionalGraph outerInnerGraph,
			QueryPlanIndex[] indexSpecs,
			EventType[] typesPerStream)

		{
			// For each stream build an array of substreams, considering required streams (inner joins) first
			// The order is relevant therefore preserving order via a LinkedHashMap.
			LinkedDictionary<Int32, int[]> substreamsPerStream = new LinkedDictionary<Int32, int[]>();
			bool[] requiredPerStream = new bool[numStreams];

			// Recursive populating the required (outer) and optional (inner) relationships
			// of this stream and the substream
			Set<Int32> completedStreams = new EHashSet<Int32>();
			RecursiveBuild( streamNo, queryGraph, outerInnerGraph, completedStreams, substreamsPerStream, requiredPerStream );

			// verify the substreamsPerStream, all streams must exists and be linked
			VerifyJoinedPerStream( streamNo, substreamsPerStream );

			// build list of instructions for lookup
			IList<LookupInstructionPlan> lookupInstructions = BuildLookupInstructions( substreamsPerStream, requiredPerStream,
				streamNames, queryGraph, indexSpecs, typesPerStream );

			// build strategy tree for putting the result back together
            BaseAssemblyNode assemblyTopNode = AssemblyStrategyTreeBuilder.Build(streamNo, substreamsPerStream, requiredPerStream);
			IList<BaseAssemblyNode> assemblyInstructions = BaseAssemblyNode.GetDescendentNodesBottomUp( assemblyTopNode );

			QueryPlanNode planNode = new LookupInstructionQueryPlanNode( streamNo, streamNames[streamNo], numStreams, requiredPerStream, lookupInstructions, assemblyInstructions );

			return planNode;
		}

        /// <summary>
        /// Builds the lookup instructions.
        /// </summary>
        /// <param name="substreamsPerStream">The substreams per stream.</param>
        /// <param name="requiredPerStream">The required per stream.</param>
        /// <param name="streamNames">The stream names.</param>
        /// <param name="queryGraph">The query graph.</param>
        /// <param name="indexSpecs">The index specs.</param>
        /// <returns></returns>
		public static IList<LookupInstructionPlan> BuildLookupInstructions(
					LinkedDictionary<Int32, int[]> substreamsPerStream,
					Boolean[] requiredPerStream,
					String[] streamNames,
					QueryGraph queryGraph,
					QueryPlanIndex[] indexSpecs,
					EventType[] typesPerStream)
		{
			IList<LookupInstructionPlan> result = new List<LookupInstructionPlan>();

			foreach ( KeyValuePair<int,int[]> kvPair in substreamsPerStream )
			{
				int fromStream = kvPair.Key;
				int[] substreams = kvPair.Value;

				// for streams with no substreams we don't need to look up
				if ( substreams.Length == 0 )
				{
					continue;
				}

				TableLookupPlan[] plans = new TableLookupPlan[substreams.Length];
				for ( int i = 0 ; i < substreams.Length ; i++ )
				{
					int toStream = substreams[i];
					TableLookupPlan tableLookupPlan = NStreamQueryPlanBuilder.CreateLookupPlan(queryGraph, fromStream, toStream, indexSpecs[toStream], typesPerStream);
					plans[i] = tableLookupPlan;
				}

				String fromStreamName = streamNames[fromStream];
				LookupInstructionPlan instruction = new LookupInstructionPlan( fromStream, fromStreamName, substreams, plans, requiredPerStream );
				result.Add( instruction );
			}

			return result;
		}

		/// <summary> Recusivly builds a substream-per-stream ordered tree graph using the
		/// join information supplied for outer joins and from the query graph (where clause).
		/// <para>
		/// Required streams are considered first and their lookup is placed first in the list
		/// to gain performance.
        /// </para>
		/// </summary>
		/// <param name="streamNum">is the root stream number that supplies the incoming event to build the tree for
		/// </param>
		/// <param name="queryGraph">contains where-clause stream relationship info
		/// </param>
		/// <param name="outerInnerGraph">contains the outer join stream relationship info
		/// </param>
		/// <param name="completedStreams">is a temporary holder for streams already considered
		/// </param>
		/// <param name="substreamsPerStream">is the ordered, tree-like structure to be filled
		/// </param>
		/// <param name="requiredPerStream">indicates which streams are required and which are optional
		/// </param>
		
        public static void RecursiveBuild( int streamNum,
			QueryGraph queryGraph,
			OuterInnerDirectionalGraph outerInnerGraph,
			Set<Int32> completedStreams,
			LinkedDictionary<Int32, int[]> substreamsPerStream,
			Boolean[] requiredPerStream )
		{
			// add this stream to the set of completed streams
            completedStreams.Add(streamNum);

			// Determine the streams we can navigate to from this stream
			Set<Int32> navigableStreams = queryGraph.GetNavigableStreams( streamNum );

			// remove those already done
			navigableStreams.RemoveAll( completedStreams );

			// Which streams are inner streams to this stream (optional), which ones are outer to the stream (required)
			Set<Int32> requiredStreams = GetOuterStreams( streamNum, navigableStreams, outerInnerGraph );
			Set<Int32> optionalStreams = GetInnerStreams( streamNum, navigableStreams, outerInnerGraph );

			// Remove from the required streams the optional streams which places 'full' joined streams
			// into the optional stream category
			requiredStreams.RemoveAll( optionalStreams );

			if ( navigableStreams.Count != ( requiredStreams.Count + optionalStreams.Count ) )
			{
				throw new ArgumentException( "Navigable streams size not constisting of inner and outer streams" );
			}

			// if we are a leaf node, we are done
			if ( navigableStreams.Count == 0 )
			{
				substreamsPerStream[streamNum] = new int[0];
				return;
			}

			// First the outer (required) streams to this stream, then the inner (optional) streams
			int[] substreams = new int[requiredStreams.Count + optionalStreams.Count];
			substreamsPerStream[streamNum] = substreams;
			int count = 0;
			foreach ( int stream in requiredStreams )
			{
				substreams[count++] = stream;
				requiredPerStream[stream] = true;
			}
			foreach ( int stream in optionalStreams )
			{
				substreams[count++] = stream;
			}

			// next we look at all the required streams and add their dependent streams
			foreach ( int stream in requiredStreams )
			{
				RecursiveBuild( stream, queryGraph, outerInnerGraph,
							   completedStreams, substreamsPerStream, requiredPerStream );
			}
			// look at all the optional streams and add their dependent streams
			foreach ( int stream in optionalStreams )
			{
				RecursiveBuild( stream, queryGraph, outerInnerGraph,
							   completedStreams, substreamsPerStream, requiredPerStream );
			}
		}

        /// <summary>
        /// Gets the inner streams.
        /// </summary>
        /// <param name="fromStream">From stream.</param>
        /// <param name="toStreams">To streams.</param>
        /// <param name="outerInnerGraph">The outer inner graph.</param>
        /// <returns></returns>
		public static Set<Int32> GetInnerStreams(
            int fromStream,
            Set<Int32> toStreams,
            OuterInnerDirectionalGraph outerInnerGraph )
		{
			Set<Int32> innerStreams = new EHashSet<Int32>();
			foreach ( int toStream in toStreams )
			{
				if ( outerInnerGraph.IsInner( fromStream, toStream ) )
				{
                    innerStreams.Add(toStream);
				}
			}
			return innerStreams;
		}

		// which streams are to this table an outer stream
		private static Set<Int32> GetOuterStreams( int fromStream, Set<Int32> toStreams, OuterInnerDirectionalGraph outerInnerGraph )
		{
			Set<Int32> outerStreams = new EHashSet<Int32>();
			foreach ( int toStream in toStreams )
			{
				if ( outerInnerGraph.IsOuter( toStream, fromStream ) )
				{
                    outerStreams.Add(toStream);
				}
			}
			return outerStreams;
		}

		/// <summary> Builds a graph of outer joins given the outer join information from the statement.
		/// Eliminates right and left joins and full joins by placing the information in a graph object.
		/// </summary>
		/// <param name="numStreams">is the number of streams
		/// </param>
		/// <param name="outerJoinDescList">list of outer join stream numbers and property names
		/// </param>
		/// <returns> graph object
		/// </returns>

		public static OuterInnerDirectionalGraph GraphOuterJoins( int numStreams, IList<OuterJoinDesc> outerJoinDescList )
		{
			if ( ( outerJoinDescList.Count + 1 ) != numStreams )
			{
				throw new ArgumentException( "Number of outer join descriptors and number of streams not matching up" );
			}

			OuterInnerDirectionalGraph graph = new OuterInnerDirectionalGraph( numStreams );

			for ( int i = 0 ; i < outerJoinDescList.Count ; i++ )
			{
				OuterJoinDesc desc = outerJoinDescList[i];
				int streamMax = i + 1;       // the outer join must references streams less then streamMax

				// Check outer join
                int streamOne = desc.LeftNode.StreamId;
				int streamTwo = desc.RightNode.StreamId;

				if ( ( streamOne > streamMax ) || ( streamTwo > streamMax ) ||
					( streamOne == streamTwo ) )
				{
					throw new ArgumentException( "Outer join descriptors reference future streams, or same streams" );
				}

				// Determine who is the first stream in the streams listed
				int lowerStream = streamOne;
				int higherStream = streamTwo;
				if ( streamOne > streamTwo )
				{
					lowerStream = streamTwo;
					higherStream = streamOne;
				}

				// Add to graph
				if ( desc.OuterJoinType == OuterJoinType.FULL )
				{
					graph.Add( streamOne, streamTwo );
					graph.Add( streamTwo, streamOne );
				}
				else if ( desc.OuterJoinType == OuterJoinType.LEFT )
				{
					graph.Add( lowerStream, higherStream );
				}
				else if ( desc.OuterJoinType == OuterJoinType.RIGHT )
				{
					graph.Add( higherStream, lowerStream );
				}
				else
				{
					throw new ArgumentException( "Outer join descriptors join type not handled, type=" + desc.OuterJoinType );
				}
			}

			return graph;
		}

		/// <summary> Verifies that the tree-like structure representing which streams join (lookup) into which sub-streams
		/// is correct, ie. all streams are included and none are listed twice.
		/// </summary>
		/// <param name="rootStream">is the stream supplying the incoming event
		/// </param>
		/// <param name="streamsJoinedPerStream">is keyed by the from-stream number and contains as values all
		/// stream numbers of lookup into to-streams. 
		/// </param>
		public static void VerifyJoinedPerStream( int rootStream, IDictionary<Int32, int[]> streamsJoinedPerStream )
		{
			Set<Int32> streams = new EHashSet<Int32>();
            streams.Add(rootStream);

			RecursiveAdd( rootStream, streamsJoinedPerStream, streams );

			if ( streams.Count != streamsJoinedPerStream.Count )
			{
				throw new ArgumentException( "Not all streams found, streamsJoinedPerStream=" +
						Print( streamsJoinedPerStream ) );
			}
		}

		private static void RecursiveAdd( int currentStream, IDictionary<Int32, int[]> streamsJoinedPerStream, Set<Int32> streams )
		{
			if ( currentStream >= streamsJoinedPerStream.Count )
			{
				throw new ArgumentException( "Error in stream " + currentStream + " streamsJoinedPerStream=" +
						Print( streamsJoinedPerStream ) );
			}
			int[] joinedStreams = streamsJoinedPerStream[currentStream];
			for ( int i = 0 ; i < joinedStreams.Length ; i++ )
			{
				int addStream = joinedStreams[i];
				if ( streams.Contains( addStream ) )
				{
					throw new ArgumentException( "Stream " + addStream + " found twice" );
				}
                streams.Add(addStream);
				RecursiveAdd( addStream, streamsJoinedPerStream, streams );
			}
		}

		/// <summary> Returns textual presentation of stream-substream relationships.</summary>
		/// <param name="streamsJoinedPerStream">is the tree-like structure of stream-substream
		/// </param>
		/// <returns> textual presentation
		/// </returns>
		public static String Print( IDictionary<Int32, int[]> streamsJoinedPerStream )
		{
			StringWriter writer = new StringWriter();

			foreach ( KeyValuePair<int,int[]> kvPair in streamsJoinedPerStream )
			{
				int stream = kvPair.Key;
				int[] substreams = kvPair.Value;
				List<int> tempList = new List<int>( substreams ) ;
				writer.WriteLine( "stream " + stream + " : " + tempList ) ;
			}

			return writer.ToString();
		}

		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
