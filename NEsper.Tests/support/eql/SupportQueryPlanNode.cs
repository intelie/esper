using System;

using net.esper.eql.join.exec;
using net.esper.eql.join.plan;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.util;

namespace net.esper.support.eql
{
	
	public class SupportQueryPlanNode:QueryPlanNode
	{
		private String id;
		
		public SupportQueryPlanNode(String id)
		{
			this.id = id;
		}

        public override ExecNode MakeExec(EventTable[][] indexPerStream, EventType[] streamTypes)
		{
			return new SupportQueryExecNode(id);
		}

        public override void Print(IndentWriter writer)
		{
            writer.WriteLine(this.GetType().Name);
		}
	}
}
