using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestEventTypeIndexBuilder 
	{
		private EventTypeIndex eventTypeIndex;
		private EventTypeIndexBuilder indexBuilder;
		
		private EventType typeOne;
		private EventType typeTwo;
		
		private FilterValueSet valueSetOne;
		private FilterValueSet valueSetTwo;
		
		private FilterCallback callbackOne;
		private FilterCallback callbackTwo;
		
		[SetUp]
		public virtual void  setUp()
		{
			eventTypeIndex = new EventTypeIndex();
			indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);
			
			typeOne = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
			typeTwo = SupportEventTypeFactory.createBeanType(typeof(SupportBeanSimple));
			
			valueSetOne = SupportFilterSpecBuilder.build(typeOne, new Object[0]).getValueSet(null);
			valueSetTwo = SupportFilterSpecBuilder.build(typeTwo, new Object[0]).getValueSet(null);
			
			callbackOne = new SupportFilterCallback();
			callbackTwo = new SupportFilterCallback();
		}
		
		[Test]
		public virtual void  testAddRemove()
		{
			Assert.IsNull(eventTypeIndex[typeOne]);
			Assert.IsNull(eventTypeIndex[typeTwo]);
			
			indexBuilder.Add(valueSetOne, callbackOne);
			indexBuilder.Add(valueSetTwo, callbackTwo);
			
			Assert.IsTrue(eventTypeIndex[typeOne] != null);
			Assert.IsTrue(eventTypeIndex[typeTwo] != null);
			
			try
			{
				indexBuilder.Add(valueSetOne, callbackOne);
				Assert.IsTrue(false);
			}
			catch (System.SystemException ex)
			{
				// Expected exception
			}
			
			indexBuilder.Remove(callbackOne);
			indexBuilder.Add(valueSetOne, callbackOne);
			indexBuilder.Remove(callbackOne);
			
			// Try invalid remove
			try
			{
				indexBuilder.Remove(callbackOne);
				Assert.IsTrue(false);
			}
			catch (ArgumentException ex)
			{
				// Expected Exception
			}
		}
	}
}
