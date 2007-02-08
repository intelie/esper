using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestIndexedTableLookupPlan 
	{
		private PropertyIndexedEventTable propertyMapEventIndex;
		private EventType[] types;
		
		[SetUp]
		public virtual void  setUp()
		{
			types = new EventType[]{SupportEventTypeFactory.createBeanType(typeof(SupportBean))};
			
			propertyMapEventIndex = new PropertyIndexedEventTable(1, types[0], new String[]{"IntBoxed"});
		}
		
		[Test]
		public virtual void  testLookup()
		{
			IndexedTableLookupPlan spec = new IndexedTableLookupPlan(0, 1, 0, new String[]{"IntBoxed"});
			
			EventTable[][] indexes = new EventTable[2][];
			indexes[1] = new EventTable[]{propertyMapEventIndex};
			
			TableLookupStrategy lookupStrategy = spec.MakeStrategy(indexes, types);

            IList<string> testList = new String[] { "IntBoxed" };

			IndexedTableLookupStrategy strategy = (IndexedTableLookupStrategy) lookupStrategy;
			Assert.AreEqual(types[0], strategy.EventType);
			Assert.AreEqual(propertyMapEventIndex, strategy.Index);
			Assert.IsTrue( CollectionHelper.AreEqual( testList, strategy.Properties ) ) ;
		}
	}
}
