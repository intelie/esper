using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.rep;
using net.esper.util;
using net.esper.events;

namespace net.esper.eql.join.assemble
{
	/// <summary> Represents a node in a tree responsible for assembling outer join query results.
	/// <p>
	/// The tree is double-linked, child nodes know each parent and parent know all child nodes.
	/// <p>
	/// Each specific subclass of this abstract assembly node is dedicated to assembling results for
	/// a certain event stream.
	/// </summary>
	public abstract class BaseAssemblyNode : ResultAssembler
	{
		/// <summary> Returns the stream number.</summary>
		/// <returns> stream number
		/// </returns>

		virtual public int StreamNum
		{
			get { return streamNum; }
		}

		/// <summary> Returns an array of stream numbers that lists all child node's stream numbers.</summary>
		/// <returns> child node stream numbers
		/// </returns>
		virtual public int[] Substreams
		{
			get
			{
				IList<Int32> substreams = new ELinkedList<Int32>();
				recusiveAddSubstreams( substreams );

				// copy to array
				int[] substreamArr = new int[substreams.Count];
				int count = 0;

                foreach (Int32 stream in substreams)
				{
					substreamArr[count++] = stream;
				}

				return substreamArr;
			}

		}
		/// <summary> Parent node.</summary>
		protected ResultAssembler parentNode;

		/// <summary> Child nodes.</summary>
		protected readonly IList<BaseAssemblyNode> childNodes;

		/// <summary> Stream number.</summary>
		protected readonly int streamNum;

		/// <summary> Number of streams in statement.</summary>
		internal readonly int numStreams;

		/// <summary> Ctor.</summary>
		/// <param name="streamNum">- stream number of the event stream that this node assembles results for.
		/// </param>
		/// <param name="numStreams">- number of streams
		/// </param>

		protected BaseAssemblyNode( int streamNum, int numStreams )
		{
			this.streamNum = streamNum;
			this.numStreams = numStreams;
			childNodes = new List<BaseAssemblyNode>();
		}

		/// <summary> Provides results to assembly nodes for initialization.</summary>
		/// <param name="result">is a list of result nodes per stream
		/// </param>
		public abstract void Init( IList<Node>[] result );

		/// <summary> Process results.</summary>
		/// <param name="result">is a list of result nodes per stream
		/// </param>
		public abstract void Process( IList<Node>[] result );

		/// <summary> Output this node using writer, not outputting child nodes.</summary>
		/// <param name="indentWriter">to use for output
		/// </param>
		public abstract void Print( IndentWriter indentWriter );

		/// <summary> Add a child node.</summary>
		/// <param name="childNode">to add
		/// </param>
		public virtual void AddChild( BaseAssemblyNode childNode )
		{
            childNode.ParentAssembler = this;
			childNodes.Add( childNode );
		}

		/// <summary> Returns child nodes.</summary>
		/// <returns> child nodes
		/// </returns>
		public IList<BaseAssemblyNode> ChildNodes
		{
			get
			{
				return childNodes;
			}
		}

		/// <summary> Gets or sets the parent node.</summary>
		/// <returns> parent node
		/// </returns>
		public virtual ResultAssembler ParentAssembler
		{
			get { return parentNode; }
            set { this.parentNode = value ; }
		}

		private void recusiveAddSubstreams( IList<Int32> substreams )
		{
			substreams.Add( streamNum );

			foreach ( BaseAssemblyNode child in childNodes )
			{
				child.recusiveAddSubstreams( substreams );
			}
		}

		/// <summary> Output this node and all descendent nodes using writer, outputting child nodes.</summary>
		/// <param name="indentWriter">to output to
		/// </param>
		public virtual void PrintDescendends( IndentWriter indentWriter )
		{
			this.Print( indentWriter );
			foreach ( BaseAssemblyNode child in childNodes )
			{
				indentWriter.incrIndent();
				child.Print( indentWriter );
				indentWriter.decrIndent();
			}
		}

		/// <summary> Returns all descendent nodes to the top node in a list in which the utmost descendants are
		/// listed first and the top node itself is listed last.
		/// </summary>
		/// <param name="topNode">is the root node of a tree structure
		/// </param>
		/// <returns> list of nodes with utmost descendants first ordered by level of depth in tree with top node last
		/// </returns>
		public static IList<BaseAssemblyNode> GetDescendentNodesBottomUp( BaseAssemblyNode topNode )
		{
			IList<BaseAssemblyNode> result = new ELinkedList<BaseAssemblyNode>();

			// Map to hold per level of the node (1 to N depth) of node a list of nodes, if any
			// exist at that level
            ETreeDictionary<Int32, IList<BaseAssemblyNode>> nodesPerLevel = new ETreeDictionary<Int32, IList<BaseAssemblyNode>>();

			// Recursively enter all aggregate functions and their level into map
			recursiveAggregateEnter( topNode, nodesPerLevel, 1 );

			// Done if none found
			if ( nodesPerLevel.Count == 0 )
			{
				throw new SystemException( "Empty collection for nodes per level" );
			}

			// From the deepest (highest) level to the lowest, add aggregates to list
			int deepLevel = nodesPerLevel.LastKey;
			for ( int i = deepLevel ; i >= 1 ; i-- )
			{
                IList<BaseAssemblyNode> list = null;
                if (!nodesPerLevel.TryGetValue(i, out list))
                {
                    continue;
                }
                
                foreach( BaseAssemblyNode _next in list )
                {
                	result.Add( _next ) ;
                }
			}

			return result;
		}

		private static void recursiveAggregateEnter( BaseAssemblyNode currentNode, EDictionary<Int32, IList<BaseAssemblyNode>> nodesPerLevel, int currentLevel )
		{
			// ask all child nodes to enter themselves
			foreach ( BaseAssemblyNode node in currentNode.ChildNodes )
			{
				recursiveAggregateEnter( node, nodesPerLevel, currentLevel + 1 );
			}

			// Add myself to list
			IList<BaseAssemblyNode> aggregates = nodesPerLevel.Fetch( currentLevel, null ) ;
			if ( aggregates == null )
			{
				aggregates = new ELinkedList<BaseAssemblyNode>();
				nodesPerLevel[currentLevel] = aggregates ;
			}
			aggregates.Add( currentNode );
		}

		public abstract void Result( EventBean[] param1, int param2, EventBean param3, Node param4 );
	}
}
