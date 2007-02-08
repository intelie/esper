using System;
using System.Collections.Generic;

using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.assemble
{
    /// <summary>
    /// Assembly node for an event stream that is a root with a one required child node below it.
    /// </summary>

    public class RootRequiredAssemblyNode : BaseAssemblyNode
    {
        /// <summary> Ctor.</summary>
        /// <param name="streamNum">- is the stream number
        /// </param>
        /// <param name="numStreams">- is the number of streams
        /// </param>

        public RootRequiredAssemblyNode(int streamNum, int numStreams)
            : base(streamNum, numStreams)
        {
        }

        public override void Init(IList<Node>[] result)
        {
            // need not be concerned with results, all is passed from the child node
        }

        public override void Process(IList<Node>[] result)
        {
            // no action here, since we have a required child row
            // The single required child generates all events that may exist
        }

        public override void Result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode)
        {
            parentNode.Result(row, streamNum, null, null);
        }

        public override void Print(IndentWriter indentWriter)
        {
            indentWriter.WriteLine("RootRequiredAssemblyNode streamNum=" + streamNum);
        }
    }
}