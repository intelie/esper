using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join;
using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.assemble
{
    /// <summary>
    /// Assembly node for an event stream that is a leaf with a no child nodes below it.
    /// </summary>

    public class LeafAssemblyNode : BaseAssemblyNode
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="LeafAssemblyNode"/> class.
        /// </summary>
        /// <param name="streamNum">stream number of the event stream that this node assembles results for.</param>
        /// <param name="numStreams">number of streams</param>
        
        public LeafAssemblyNode(int streamNum, int numStreams)
            : base(streamNum, numStreams)
        {
        }

        /// <summary>
        /// Provides results to assembly nodes for initialization.
        /// </summary>
        /// <param name="result">is a list of result nodes per stream</param>
        public override void Init(IList<Node>[] result)
        {
            return;
        }

        /// <summary>
        /// Process results.
        /// </summary>
        /// <param name="result">is a list of result nodes per stream</param>
        public override void Process(IList<Node>[] result)
        {
            IList<Node> nodes = result[streamNum];
            if (nodes == null)
            {
                return;
            }

            foreach (Node node in nodes)
            {
                Set<EventBean> events = node.Events;
                foreach (EventBean ev in events)
                {
                    processEvent(ev, node);
                }
            }
        }

        /// <summary>
        /// Processes the event.
        /// </summary>
        /// <param name="ev">The ev.</param>
        /// <param name="currentNode">The current node.</param>
        private void processEvent(EventBean ev, Node currentNode)
        {
            EventBean[] row = new EventBean[numStreams];
            row[streamNum] = ev;
            parentNode.Result(row, streamNum, currentNode.ParentEvent, currentNode.Parent);
        }

        /// <summary>
        /// Results the specified row.
        /// </summary>
        /// <param name="row">The row.</param>
        /// <param name="streamNum">The stream num.</param>
        /// <param name="myEvent">My event.</param>
        /// <param name="myNode">My node.</param>
        public override void Result(EventBean[] row, int streamNum, EventBean myEvent, Node myNode)
        {
            throw new NotSupportedException("Leaf node cannot process child results");
        }

        /// <summary>
        /// Output this node using writer, not outputting child nodes.
        /// </summary>
        /// <param name="indentWriter">to use for output</param>
        public override void Print(IndentWriter indentWriter)
        {
            indentWriter.WriteLine("LeafAssemblyNode streamNum=" + streamNum);
        }
    }
}
