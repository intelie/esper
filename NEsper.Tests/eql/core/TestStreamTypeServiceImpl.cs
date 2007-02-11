using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestStreamTypeServiceImpl 
	{
		private StreamTypeServiceImpl service;
		
		[SetUp]
		public virtual void  setUp()
		{
			EventType[] eventTypes = new EventType[]{SupportEventTypeFactory.createBeanType(typeof(SupportBean)), SupportEventTypeFactory.createBeanType(typeof(SupportBean)), SupportEventTypeFactory.createBeanType(typeof(SupportBean_A)), SupportEventTypeFactory.createBeanType(typeof(SupportMarketDataBean))};
			
			String[] streamNames = new String[]{"s1", null, "s3", "s4"};
			
			service = new StreamTypeServiceImpl(eventTypes, streamNames);
		}
		
		[Test]
		public virtual void  testResolveByStreamAndPropNameBoth()
		{
			// Test lookup by stream name and prop name
			PropertyResolutionDescriptor desc = service.resolveByStreamAndPropName("s4", "volume");
			Assert.AreEqual(3, (int) desc.StreamNum);
			Assert.AreEqual(typeof(long?), desc.PropertyType);
			Assert.AreEqual("volume", desc.PropertyName);
			Assert.AreEqual("s4", desc.StreamName);
			Assert.AreEqual(typeof(SupportMarketDataBean), desc.StreamEventType.UnderlyingType);
			
			try
			{
				service.resolveByStreamAndPropName("xxx", "volume");
				Assert.Fail();
			}
			catch (StreamNotFoundException ex)
			{
				// Expected
			}
			
			try
			{
				service.resolveByStreamAndPropName("s4", "xxxx");
				Assert.Fail();
			}
			catch (PropertyNotFoundException ex)
			{
				// Expected
			}
		}
		
		[Test]
		public virtual void  testResolveByPropertyName()
		{
			// Test lookup by property name only
			PropertyResolutionDescriptor desc = service.resolveByPropertyName("volume");
			Assert.AreEqual(3, (int) (desc.StreamNum));
			Assert.AreEqual(typeof(long?), desc.PropertyType);
			Assert.AreEqual("volume", desc.PropertyName);
			Assert.AreEqual("s4", desc.StreamName);
			Assert.AreEqual(typeof(SupportMarketDataBean), desc.StreamEventType.UnderlyingType);
			
			try
			{
				service.resolveByPropertyName("boolPrimitive");
				Assert.Fail();
			}
			catch (DuplicatePropertyException ex)
			{
				// Expected
			}
			
			try
			{
				service.resolveByPropertyName("xxxx");
				Assert.Fail();
			}
			catch (PropertyNotFoundException ex)
			{
				// Expected
			}
		}
		
		[Test]
		public virtual void  testResolveByStreamAndPropNameInOne()
		{
			// Test lookup by stream name and prop name
			PropertyResolutionDescriptor desc = service.resolveByStreamAndPropName("s4.volume");
			Assert.AreEqual(3, (int) desc.StreamNum);
			Assert.AreEqual(typeof(long?), desc.PropertyType);
			Assert.AreEqual("volume", desc.PropertyName);
			Assert.AreEqual("s4", desc.StreamName);
			Assert.AreEqual(typeof(SupportMarketDataBean), desc.StreamEventType.UnderlyingType);
			
			try
			{
				service.resolveByStreamAndPropName("xxx.volume");
				Assert.Fail();
			}
			catch (PropertyNotFoundException ex)
			{
				// Expected
			}
			
			try
			{
				service.resolveByStreamAndPropName("s4.xxxx");
				Assert.Fail();
			}
			catch (PropertyNotFoundException ex)
			{
				// Expected
			}
		}
	}
}
