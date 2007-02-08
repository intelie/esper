using System;

using net.esper.eql.join.exec;
using net.esper.eql.join.table;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestTableLookupPlan 
	{
		[Test]
		public virtual void  testMakeExec()
		{
			EventTable[][] tmpArray = new EventTable[2][];
			for (int i = 0; i < 2; i++)
			{
				tmpArray[i] = new EventTable[0];
			}
			EventTable[][] indexesPerStream = tmpArray;
			indexesPerStream[1] = new EventTable[1];
			indexesPerStream[1][0] = new UnindexedEventTable(0);
			
			TableLookupNode spec = new TableLookupNode(new FullTableScanLookupPlan(0, 1, 0));
			ExecNode execNode = spec.MakeExec(indexesPerStream, null);
			TableLookupExecNode exec = (TableLookupExecNode) execNode;
			
			Assert.AreSame(indexesPerStream[1][0], ((FullTableScanLookupStrategy) exec.LookupStrategy).getEventIndex());
			Assert.AreEqual(1, exec.IndexedStream);
		}
	}
}
