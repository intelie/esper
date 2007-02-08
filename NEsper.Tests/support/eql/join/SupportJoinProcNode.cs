using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.assemble;
using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.support.eql.join
{
    public class SupportJoinProcNode : BaseAssemblyNode
    {
        private IList<EventBean[]> rowsList = new List<EventBean[]>();
        private IList<int> streamNumList = new List<int>();
        private IList<EventBean> myEventList = new List<EventBean>();
        private IList<Node> myNodeList = new List<Node>();

        public SupportJoinProcNode(int streamNum, int numStreams)
            : base(streamNum, numStreams)
        {
        }

        public override void Init(IList<Node>[] result)
        {
        }

        public override void Process(IList<Node>[] result)
        {
        }

        public override void Result(EventBean[] row, int streamNum, EventBean myEvent, Node myNode)
        {
            rowsList.Add(row);
            streamNumList.Add(streamNum);
            myEventList.Add(myEvent);
            myNodeList.Add(myNode);
        }

        public override void Print(IndentWriter indentWriter)
        {
            throw new System.NotSupportedException("unsupported");
        }

        public IList<EventBean[]> getRowsList()
        {
            return rowsList;
        }

        public IList<int> getStreamNumList()
        {
            return streamNumList;
        }

        public IList<EventBean> getMyEventList()
        {
            return myEventList;
        }

        public IList<Node> getMyNodeList()
        {
            return myNodeList;
        }
    }
}
