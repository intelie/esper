using System;
using System.Collections.Generic;

using net.esper.eql.join.exec;
using net.esper.events;
using net.esper.util;

namespace net.esper.support.eql
{
    public class SupportQueryExecNode : ExecNode
    {
        virtual public String Id
        {
            get { return id; }
        }

        virtual public EventBean[] LastPrefillPath
        {
            get { return lastPrefillPath; }
        }

        private readonly String id;

        private EventBean[] lastPrefillPath;

        public SupportQueryExecNode(String id)
        {
            this.id = id;
        }

        public override void Process(EventBean lookupEvent, EventBean[] prefillPath, IList<EventBean[]> result)
        {
            lastPrefillPath = prefillPath;
        }

        public override void Print(IndentWriter writer)
        {
            writer.WriteLine("SupportQueryExecNode");
        }
    }
}
