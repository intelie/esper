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
			eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
			
			propertyMapEventIndex = new PropertyIndexedEventTable(0, eventType, new String[]{"string", "IntPrimitive"});
			lookupStrategy = new IndexedTableLookupStrategy(eventType, new String[]{"string", "IntPrimitive"}, propertyMapEventIndex);
			
			propertyMapEventIndex.Add(new EventBean[]{SupportEventBeanFactory.createObject(new SupportBean("a", 1))});
		}
		
		[Test]
		public virtual void  testLookup()
		{
      ISet<EventBean> events = lookupStrategy.lookup(SupportEventBeanFactory.createObject(new SupportBean("a", 1)));
			
			Assert.AreEqual(1, events.Count);
		}
		
		[Test]
		public virtual void  testInvalid()
		{
			try
			{
				new IndexedTableLookupStrategy(eventType, new String[]{"string", "xxx"}, propertyMapEventIndex);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// expected
			}
		}
	}
}
