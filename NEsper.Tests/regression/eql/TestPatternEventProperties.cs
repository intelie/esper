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
			EventBean _eventBean = updateListener.AssertOneGetNewAndReset();
			Assert.AreSame(_event, _eventBean["a"]);
		}

		[Test]
		public virtual void  testWildcardOrPattern()
		{
			setupOrPattern("*");

			Object _event = new SupportBean();
			epService.EPRuntime.SendEvent(_event);
			EventBean _eventBean = updateListener.AssertOneGetNewAndReset();
			Assert.AreSame(_event, _eventBean["a"]);
			Assert.IsNull(_eventBean["b"]);

			_event = SupportBeanComplexProps.MakeDefaultBean();
			epService.EPRuntime.SendEvent(_event);
			_eventBean = updateListener.AssertOneGetNewAndReset();
			Assert.AreSame(_event, _eventBean["b"]);
			Assert.IsNull(_eventBean["a"]);
		}

		[Test]
		public virtual void  testPropertiesSimplePattern()
		{
			setupSimplePattern("a, a as myEvent, a.intPrimitive as myInt, a.str");

			SupportBean _event = new SupportBean();
			_event.SetIntPrimitive(1);
            _event.SetString("test");
			epService.EPRuntime.SendEvent(_event);

			EventBean _eventBean = updateListener.AssertOneGetNewAndReset();
			Assert.AreSame(_event, _eventBean["a"]);
			Assert.AreSame(_event, _eventBean["myEvent"]);
			Assert.AreEqual(1, _eventBean["myInt"]);
			Assert.AreEqual("test", _eventBean["a.str"]);
		}

		[Test]
		public virtual void  testPropertiesOrPattern()
		{
			setupOrPattern("a, a as myAEvent, b, b as myBEvent, a.intPrimitive as myInt, " + "a.str, b.simpleProperty as simple, b.indexed[0] as indexed, b.nested.nestedValue as nestedVal");

			Object _event = SupportBeanComplexProps.MakeDefaultBean();
			epService.EPRuntime.SendEvent(_event);
			EventBean _eventBean = updateListener.AssertOneGetNewAndReset();
			Assert.AreSame(_event, _eventBean["b"]);
			Assert.AreEqual("simple", _eventBean["simple"]);
			Assert.AreEqual(1, _eventBean["indexed"]);
			Assert.AreEqual("nestedValue", _eventBean["nestedVal"]);
			Assert.IsNull(_eventBean["a"]);
			Assert.IsNull(_eventBean["myAEvent"]);
			Assert.IsNull(_eventBean["myInt"]);
			Assert.IsNull(_eventBean["a.str"]);

			SupportBean eventTwo = new SupportBean();
			eventTwo.SetIntPrimitive(2);
            eventTwo.SetString("test2");
			epService.EPRuntime.SendEvent(eventTwo);
			_eventBean = updateListener.AssertOneGetNewAndReset();
			Assert.AreEqual(2, _eventBean["myInt"]);
			Assert.AreEqual("test2", _eventBean["a.str"]);
			Assert.IsNull(_eventBean["b"]);
			Assert.IsNull(_eventBean["myBEvent"]);
			Assert.IsNull(_eventBean["simple"]);
			Assert.IsNull(_eventBean["indexed"]);
			Assert.IsNull(_eventBean["nestedVal"]);
		}

		private void  setupSimplePattern(String selectCriteria)
		{
			String stmtText =
				"select " + selectCriteria +
				" from pattern [a=" + typeof(SupportBean).FullName + "]";
			EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(updateListener);
		}

		private void  setupOrPattern(String selectCriteria)
		{
			String stmtText =
				"select " + selectCriteria +
				" from pattern [every(a=" + typeof(SupportBean).FullName +
				" or b=" + typeof(SupportBeanComplexProps).FullName + ")]";
			EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(updateListener);
		}
	}
}
