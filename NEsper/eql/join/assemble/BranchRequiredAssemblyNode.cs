using System;
using System.Collections.Generic;

using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.assemble
{
	
	/// <summary>
	/// Assembly node for an event stream that is a branch with a single required child node below it.
	/// </summary>
	
	public class BranchRequiredAssemblyNode : BaseAssemblyNode
	{
		/// <summary>
		/// Initializes a new instance of the <see cref="T:BranchRequiredAssemblyNode"/> class.
		/// </summary>
		/// <param name="streamNum">stream number of the event stream that this node assembles results for.</param>
		/// <param name="numStreams">number of streams</param>
		
		public BranchRequiredAssemblyNode( int streamNum, int numStreams )
			: base( streamNum, numStreams )
		{
		}

        /// <summary>
        /// Provides results to assembly nodes for initialization.
        /// </summary>
        /// <param name="result">is a list of result nodes per stream</param>
		public override void Init( IList<Node>[] result )
		{
			// need not be concerned with results, all is passed from the child node
		}

        /// <summary>
        /// Process results.
        /// </summary>
        /// <param name="result">is a list of result nodes per stream</param>
        public override void Process(IList<Node>[] result)
		{
			// no action here, since we have a required child row
			// The single required child generates all events that may exist
		}

        /// <summary>
        /// Publish a result row.
        /// </summary>
        /// <param name="row">is the result to publish</param>
        /// <param name="fromStreamNum">is the originitor that publishes the row</param>
        /// <param name="myEvent">is optional and is the event that led to the row result</param>
        /// <param name="myNode">is optional and is the result node of the event that led to the row result</param>
        public override void Result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode)
		{
			row[streamNum] = myEvent;
			Node parentResultNode = myNode.Parent;
            parentNode.Result(row, streamNum, myNode.ParentEvent, parentResultNode);
		}

        /// <summary>
        /// Output this node using writer, not outputting child nodes.
        /// </summary>
        /// <param name="indentWriter">to use for output</param>
        public override void Print(IndentWriter indentWriter)
		{
			indentWriter.WriteLine( "BranchRequiredAssemblyNode streamNum=" + streamNum );
		}
	}
}