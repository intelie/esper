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
			EPStatement statement = epService.EPAdministrator.createEQL("select * from " + typeof(SupportBean).FullName + "(string != 'a')");
			statement.AddListener(testListener);
			
			SendEvent("a");
			Assert.IsFalse(testListener.Invoked);
			
			Object _event = SendEvent("b");
			Assert.AreSame(_event, testListener.getAndResetLastNewData()[0].Underlying);
			
			SendEvent("a");
			Assert.IsFalse(testListener.Invoked);
			
			_event = SendEvent(null);
			Assert.IsFalse(testListener.Invoked);
		}
		
		[Test]
		public virtual void  testCombinationEqualsOp()
		{
			EPStatement statement = epService.EPAdministrator.createEQL("select * from " + typeof(SupportBean).FullName + "(string != 'a', intPrimitive=0)");
			statement.AddListener(testListener);
			
			SendEvent("b", 1);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent("a", 0);
			Assert.IsFalse(testListener.Invoked);
			
			Object _event = SendEvent("x", 0);
			Assert.AreSame(_event, testListener.getAndResetLastNewData()[0].Underlying);
			
			_event = SendEvent(null, 0);
			Assert.IsFalse(testListener.Invoked);
		}
		
		private Object SendEvent(String stringValue)
		{
			return SendEvent(stringValue, - 1);
		}
		
		private Object SendEvent(String stringValue, int intPrimitive)
		{
			SupportBean _event = new SupportBean();
			_event.StringValue = stringValue;
			_event.intPrimitive = intPrimitive;
			epService.EPRuntime.SendEvent(_event);
			return _event;
		}
	}
}
