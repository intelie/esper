using System;

using net.esper.eql.join.exec;
using net.esper.eql.join.table;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestFullTableScanLookupPlan 
	{
		private UnindexedEventTable unindexedEventIndex;
		
		[SetUp]
		public virtual void  setUp()
		{
			unindexedEventIndex = new UnindexedEventTable(0);
		}
		
		[Test]
		public virtual void  testLookup()
		{
			FullTableScanLookupPlan spec = new FullTableScanLookupPlan(0, 1, 2);
			
			EventTable[][] indexes = new EventTable[2][];
			indexes[1] = new EventTable[]{null, null, unindexedEventIndex};
			
			TableLookupStrategy lookupStrategy = spec.MakeStrategy(indexes, null);
			
			FullTableScanLookupStrategy strategy = (FullTableScanLookupStrategy) lookupStrategy;
			Assert.AreEqual(unindexedEventIndex, strategy.EventIndex);
		}
	}
}
