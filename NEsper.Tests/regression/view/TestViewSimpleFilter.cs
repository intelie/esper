using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewSimpleFilter
	{
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;

		[SetUp]
		public virtual void  setUp()
		{
			testListener = new SupportUpdateListener();
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}

		[Test]
		public virtual void  testNotEqualsOp()
		{
			EPStatement statement = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName + "(str != 'a')");
			statement.AddListener(testListener);

			SendEvent("a");
			Assert.IsFalse(testListener.IsInvoked);

			Object _event = SendEvent("b");
			Assert.AreSame(_event, testListener.GetAndResetLastNewData()[0].Underlying);

			SendEvent("a");
			Assert.IsFalse(testListener.IsInvoked);

			_event = SendEvent(null);
			Assert.IsFalse(testListener.IsInvoked);
		}

		[Test]
		public virtual void  testCombinationEqualsOp()
		{
			EPStatement statement = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName + "(str != 'a', intPrimitive=0)");
			statement.AddListener(testListener);

			SendEvent("b", 1);
			Assert.IsFalse(testListener.IsInvoked);

			SendEvent("a", 0);
			Assert.IsFalse(testListener.IsInvoked);

			Object _event = SendEvent("x", 0);
			Assert.AreSame(_event, testListener.GetAndResetLastNewData()[0].Underlying);

			_event = SendEvent(null, 0);
			Assert.IsFalse(testListener.IsInvoked);
		}

		private Object SendEvent(String stringValue)
		{
			return SendEvent(stringValue, - 1);
		}

		private Object SendEvent(String stringValue, int intPrimitive)
		{
			SupportBean _event = new SupportBean();
		    _event.SetString(stringValue);
		    _event.SetIntPrimitive(intPrimitive);
			epService.EPRuntime.SendEvent(_event);
			return _event;
		}
	}
}
