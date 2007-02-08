using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewWhereClause 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;
		private EPStatement testView;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			
			String viewExpr = 
                "select * from " + typeof(SupportMarketDataBean).FullName + 
                ".win:length(3) where Symbol='CSCO'";
			testView = epService.EPAdministrator.createEQL(viewExpr);
			testListener = new SupportUpdateListener();
			testView.AddListener(testListener);
		}
		
		[Test]
		public virtual void  testWhere()
		{
			sendMarketDataEvent("IBM");
			Assert.IsFalse(testListener.getAndClearIsInvoked());
			
			sendMarketDataEvent("CSCO");
			Assert.IsTrue(testListener.getAndClearIsInvoked());
			
			sendMarketDataEvent("IBM");
			Assert.IsFalse(testListener.getAndClearIsInvoked());
			
			sendMarketDataEvent("CSCO");
			Assert.IsTrue(testListener.getAndClearIsInvoked());
		}
		
		[Test]
		public virtual void  testWhereNumericType()
		{
			String viewExpr =
                "select " +
                " IntPrimitive + LongPrimitive as p1," +
                " IntPrimitive * DoublePrimitive as p2," +
                " FloatPrimitive / DoublePrimitive as p3" + 
                " from " + typeof(SupportBean).FullName +
                ".win:length(3) where " + 
                "IntPrimitive=LongPrimitive and IntPrimitive=DoublePrimitive and FloatPrimitive=DoublePrimitive";
			
			testView = epService.EPAdministrator.createEQL(viewExpr);
			testListener = new SupportUpdateListener();
			testView.AddListener(testListener);
			
			sendSupportBeanEvent(1, 2, 3, 4);
			Assert.IsFalse(testListener.Invoked);
			
			sendSupportBeanEvent(2, 2, 2, 2);
			EventBean _event = testListener.getAndResetLastNewData()[0];
			Assert.AreEqual(typeof(Int64), _event.EventType.GetPropertyType("p1"));
			Assert.AreEqual(4L, _event["p1"]);
			Assert.AreEqual(typeof(Double), _event.EventType.GetPropertyType("p2"));
			Assert.AreEqual(4d, _event["p2"]);
			Assert.AreEqual(typeof(Double), _event.EventType.GetPropertyType("p3"));
			Assert.AreEqual(1d, _event["p3"]);
		}
		
		private void  sendMarketDataEvent(String symbol)
		{
			SupportMarketDataBean _event = new SupportMarketDataBean(symbol, 0, 0L, "");
			epService.EPRuntime.SendEvent(_event);
		}
		
		private void  sendSupportBeanEvent(int intPrimitive, long longPrimitive, float floatPrimitive, double doublePrimitive)
		{
			SupportBean _event = new SupportBean();
			_event.IntPrimitive = intPrimitive;
			_event.LongPrimitive = longPrimitive;
			_event.FloatPrimitive = floatPrimitive;
			_event.DoublePrimitive = doublePrimitive;
			epService.EPRuntime.SendEvent(_event);
		}
	}
}
