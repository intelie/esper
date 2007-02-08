using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	
	[TestFixture]
	public class TestPatternEventProperties 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener updateListener;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();
		}
		
		[Test]
		public virtual void  testWildcardSimplePattern()
		{
			setupSimplePattern("*");
			
			Object _event = new SupportBean();
			epService.EPRuntime.SendEvent(_event);
			EventBean eventBean = updateListener.assertOneGetNewAndReset();
			Assert.AreSame(_event, eventBean["a"]);
		}
		
		[Test]
		public virtual void  testWildcardOrPattern()
		{
			setupOrPattern("*");
			
			Object _event = new SupportBean();
			epService.EPRuntime.SendEvent(_event);
			EventBean eventBean = updateListener.assertOneGetNewAndReset();
			Assert.AreSame(_event, eventBean["a"]);
			Assert.IsNull(eventBean["b"]);
			
			_event = SupportBeanComplexProps.makeDefaultBean();
			epService.EPRuntime.SendEvent(_event);
			eventBean = updateListener.assertOneGetNewAndReset();
			Assert.AreSame(_event, eventBean["b"]);
			Assert.IsNull(eventBean["a"]);
		}
		
		[Test]
		public virtual void  testPropertiesSimplePattern()
		{
			setupSimplePattern("a, a as myEvent, a.IntPrimitive as myInt, a.string");
			
			SupportBean _event = new SupportBean();
			_event.IntPrimitive = 1;
			_event.StringValue = "test";
			epService.EPRuntime.SendEvent(_event);
			
			EventBean eventBean = updateListener.assertOneGetNewAndReset();
			Assert.AreSame(_event, eventBean["a"]);
			Assert.AreSame(_event, eventBean["myEvent"]);
			Assert.AreEqual(1, eventBean["myInt"]);
			Assert.AreEqual("test", eventBean["a.string"]);
		}
		
		[Test]
		public virtual void  testPropertiesOrPattern()
		{
			setupOrPattern("a, a as myAEvent, b, b as myBEvent, a.IntPrimitive as myInt, " + "a.string, b.simpleProperty as simple, b.indexed[0] as indexed, b.nested.nestedValue as nestedVal");
			
			Object _event = SupportBeanComplexProps.makeDefaultBean();
			epService.EPRuntime.SendEvent(_event);
			EventBean eventBean = updateListener.assertOneGetNewAndReset();
			Assert.AreSame(_event, eventBean["b"]);
			Assert.AreEqual("simple", eventBean["simple"]);
			Assert.AreEqual(1, eventBean["indexed"]);
			Assert.AreEqual("nestedValue", eventBean["nestedVal"]);
			Assert.IsNull(eventBean["a"]);
			Assert.IsNull(eventBean["myAEvent"]);
			Assert.IsNull(eventBean["myInt"]);
			Assert.IsNull(eventBean["a.string"]);
			
			SupportBean eventTwo = new SupportBean();
			eventTwo.IntPrimitive = 2;
			eventTwo.StringValue = "test2";
			epService.EPRuntime.SendEvent(eventTwo);
			eventBean = updateListener.assertOneGetNewAndReset();
			Assert.AreEqual(2, eventBean["myInt"]);
			Assert.AreEqual("test2", eventBean["a.string"]);
			Assert.IsNull(eventBean["b"]);
			Assert.IsNull(eventBean["myBEvent"]);
			Assert.IsNull(eventBean["simple"]);
			Assert.IsNull(eventBean["indexed"]);
			Assert.IsNull(eventBean["nestedVal"]);
		}
		
		private void  setupSimplePattern(String selectCriteria)
		{
			String stmtText =
				"select " + selectCriteria +
				" from pattern [a=" + typeof(SupportBean).FullName + "]";
			EPStatement stmt = epService.EPAdministrator.createEQL(stmtText);
			stmt.AddListener(updateListener);
		}
		
		private void  setupOrPattern(String selectCriteria)
		{
			String stmtText =
				"select " + selectCriteria +
				" from pattern [every(a=" + typeof(SupportBean).FullName +
				" or b=" + typeof(SupportBeanComplexProps).FullName + ")]";
			EPStatement stmt = epService.EPAdministrator.createEQL(stmtText);
			stmt.AddListener(updateListener);
		}
	}
}
