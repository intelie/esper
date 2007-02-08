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
        /**
 * Ctor.
 * @param streamNum - is the stream number
 * @param numStreams - is the number of streams
 */
        public LeafAssemblyNode(int streamNum, int numStreams)
            : base(streamNum, numStreams)
        {
        }

        public override void Init(IList<Node>[] result)
        {
            return;
        }

        public override void Process(IList<Node>[] result)
        {
            IList<Node> nodes = result[streamNum];
            if (nodes == null)
            {
                return;
            }

            foreach (Node node in nodes)
            {
                ISet<EventBean> events = node.Events;
                foreach (EventBean ev in events)
                {
                    processEvent(ev, node);
                }
            }
        }

        private void processEvent(EventBean ev, Node currentNode)
        {
            EventBean[] row = new EventBean[numStreams];
            row[streamNum] = ev;
            parentNode.Result(row, streamNum, currentNode.ParentEvent, currentNode.Parent);
        }

        public override void Result(EventBean[] row, int streamNum, EventBean myEvent, Node myNode)
        {
            throw new NotSupportedException("Leaf node cannot process child results");
        }

        public override void Print(IndentWriter indentWriter)
        {
            indentWriter.WriteLine("LeafAssemblyNode streamNum=" + streamNum);
        }
    }
}