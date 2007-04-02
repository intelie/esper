using System;

using net.esper.compat;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.exec
{
	[TestFixture]
	public class TestIndexedTableLookupStrategy 
	{
		private EventType eventType;
		private IndexedTableLookupStrategy lookupStrategy;
		private PropertyIndexedEventTable propertyMapEventIndex;
		
		[SetUp]
		public virtual void  setUp()
		{
			eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
			
			propertyMapEventIndex = new PropertyIndexedEventTable(0, eventType, new String[]{"str", "intPrimitive"});
            lookupStrategy = new IndexedTableLookupStrategy(eventType, new String[] { "str", "intPrimitive" }, propertyMapEventIndex);
			
			propertyMapEventIndex.Add(new EventBean[]{SupportEventBeanFactory.createObject(new SupportBean("a", 1))});
		}
		
		[Test]
		public virtual void  testLookup()
		{
      ISet<EventBean> events = lookupStrategy.Lookup(SupportEventBeanFactory.createObject(new SupportBean("a", 1)));
			
			Assert.AreEqual(1, events.Count);
		}
		
		[Test]
		public virtual void  testInvalid()
		{
			try
			{
                new IndexedTableLookupStrategy(eventType, new String[] { "str", "xxx" }, propertyMapEventIndex);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
	}
}
