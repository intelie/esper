using System;
using System.IO;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.plan;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.join.assemble
{
	/// <summary>
	/// Builds a tree of assembly nodes given a strategy for how to join streams.
	/// </summary>

	public class AssemblyStrategyTreeBuilder
	{
		/// <summary> Builds a tree of <seealso cref="BaseAssemblyNode" /> from join strategy information.</summary>
		/// <param name="rootStream">the root stream supplying the event to evaluate
		/// </param>
		/// <param name="streamsJoinedPerStream">a map in which the key is the stream number to supply an event,
		/// and the value is an array of streams to find events in for the given event
		/// </param>
		/// <param name="isRequiredPerStream">indicates which streams are required join streams versus optional streams
		/// </param>
		/// <returns> root assembly node
		/// </returns>

		public static BaseAssemblyNode Build( int rootStream, IDictionary<Int32, int[]> streamsJoinedPerStream, bool[] isRequiredPerStream )
		{
			if ( streamsJoinedPerStream.Count < 3 )
			{
				throw new ArgumentException( "Not a 3-way join" );
			}
			if ( ( rootStream < 0 ) || ( rootStream >= streamsJoinedPerStream.Count ) )
			{
				throw new ArgumentException( "Invalid root stream" );
			}
			if ( isRequiredPerStream.Length != streamsJoinedPerStream.Count )
			{
				throw new ArgumentException( "Arrays not matching up" );
			}

			NStreamOuterQueryPlanBuilder.VerifyJoinedPerStream( rootStream, streamsJoinedPerStream );

			if ( log.IsDebugEnabled )
			{
				log.Debug(
                    ".build Building node for root stream " + rootStream + 
                    " streamsJoinedPerStream=" + NStreamOuterQueryPlanBuilder.Print( streamsJoinedPerStream ) +
                    " isRequiredPerStream=" + CollectionHelper.Render( isRequiredPerStream ) );
			}

			BaseAssemblyNode topNode = CreateNode(
                true,
                rootStream,
                streamsJoinedPerStream.Count,
                streamsJoinedPerStream[rootStream],
                isRequiredPerStream );

			RecursiveBuild( rootStream, topNode, streamsJoinedPerStream, isRequiredPerStream );

			if ( log.IsDebugEnabled )
			{
				StringWriter buf = new StringWriter();
				IndentWriter indentWriter = new IndentWriter( buf, 0, 2 );
				topNode.PrintDescendends( indentWriter );

				log.Debug( ".build Dumping root node for stream " + rootStream + ": \n" + buf.ToString() );
			}

			return topNode;
		}

		private static void RecursiveBuild( int parentStreamNum, BaseAssemblyNode parentNode, IDictionary<Int32, int[]> streamsJoinedPerStream, bool[] isRequiredPerStream )
		{
			int numStreams = streamsJoinedPerStream.Count;

			for ( int i = 0 ; i < streamsJoinedPerStream[parentStreamNum].Length ; i++ )
			{
				int streamJoined = streamsJoinedPerStream[parentStreamNum][i];
				BaseAssemblyNode childNode = CreateNode(
                    false,
                    streamJoined,
                    numStreams,
                    streamsJoinedPerStream[streamJoined],
                    isRequiredPerStream );
				parentNode.AddChild( childNode );

				if ( streamsJoinedPerStream[streamJoined].Length > 0 )
				{
					RecursiveBuild( streamJoined, childNode, streamsJoinedPerStream, isRequiredPerStream );
				}
			}
		}

		private static BaseAssemblyNode CreateNode( bool isRoot, int streamNum, int numStreams, int[] joinedStreams, bool[] isRequiredPerStream )
		{
			if ( joinedStreams.Length == 0 )
			{
				return new LeafAssemblyNode( streamNum, numStreams );
			}
			if ( joinedStreams.Length == 1 )
			{
				int joinedStream = joinedStreams[0];
				bool isRequired = isRequiredPerStream[joinedStream];
				if ( isRequired )
				{
					if ( isRoot )
					{
						return new RootRequiredAssemblyNode( streamNum, numStreams );
					}
					else
					{
						return new BranchRequiredAssemblyNode( streamNum, numStreams );
					}
				}
				else
				{
					if ( isRoot )
					{
						return new RootOptionalAssemblyNode( streamNum, numStreams );
					}
					else
					{
						return new BranchOptionalAssemblyNode( streamNum, numStreams );
					}
				}
			}

			// Determine if all substream are outer (optional) joins
			bool allSubStreamsOptional = true;
			for ( int i = 0 ; i < joinedStreams.Length ; i++ )
			{
				int stream = joinedStreams[i];
				if ( isRequiredPerStream[stream] )
				{
					allSubStreamsOptional = false;
				}
			}

			// Make node for building a cartesian product 
			if ( isRoot )
			{
				return new RootCartProdAssemblyNode( streamNum, numStreams, allSubStreamsOptional );
			}
			else
			{
				return new CartesianProdAssemblyNode( streamNum, numStreams, allSubStreamsOptional );
			}
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
