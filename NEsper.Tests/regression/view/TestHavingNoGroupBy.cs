using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	
	[TestFixture]
	public class TestHavingNoGroupBy 
	{
		private static String SYMBOL_DELL = "DELL";
		
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;
		private EPStatement selectTestView;
		
		[SetUp]
		public virtual void  setUp()
		{
			testListener = new SupportUpdateListener();
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testSumOneView()
		{
			String viewExpr = "select symbol, price, avg(price) as avgPrice " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "having price < avg(price)";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			runAssertion();
		}
		
		[Test]
		public virtual void  testSumJoin()
		{
			String viewExpr = "select symbol, price, avg(price) as avgPrice " + "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " + "where one.string = two.symbol " + "having price < avg(price)";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
			
			runAssertion();
		}
		
		[Test]
		public virtual void  testNoAggregationJoinHaving()
		{
			runNoAggregationJoin("having");
		}
		
		[Test]
		public virtual void  testNoAggregationJoinWhere()
		{
			runNoAggregationJoin("where");
		}
		
		private void  runNoAggregationJoin(String filterClause)
		{
			String viewExpr = "select a.price as aPrice, b.price as bPrice, Math.max(a.price, b.price) - Math.min(a.price, b.price) as spread " + "from " + typeof(SupportMarketDataBean).FullName + "(symbol='SYM1').win:length(1) as a, " + typeof(SupportMarketDataBean).FullName + "(symbol='SYM2').win:length(1) as b " + filterClause + " Math.max(a.price, b.price) - Math.min(a.price, b.price) >= 1.4";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			sendPriceEvent("SYM1", 20);
			Assert.IsFalse(testListener.Invoked);
			
			sendPriceEvent("SYM2", 10);
			assertNewSpreadEvent(20, 10, 10);
			
			sendPriceEvent("SYM2", 20);
			assertOldSpreadEvent(20, 10, 10);
			
			sendPriceEvent("SYM2", 20);
			sendPriceEvent("SYM2", 20);
			sendPriceEvent("SYM1", 20);
			Assert.IsFalse(testListener.Invoked);
			
			sendPriceEvent("SYM1", 18.7);
			Assert.IsFalse(testListener.Invoked);
			
			sendPriceEvent("SYM2", 20);
			Assert.IsFalse(testListener.Invoked);
			
			sendPriceEvent("SYM1", 18.5);
			assertNewSpreadEvent(18.5, 20, 1.5d);
			
			sendPriceEvent("SYM2", 16);
			assertOldNewSpreadEvent(18.5, 20, 1.5d, 18.5, 16, 2.5d);
			
			sendPriceEvent("SYM1", 12);
			assertOldNewSpreadEvent(18.5, 16, 2.5d, 12, 16, 4);
		}
		
		private void  assertOldNewSpreadEvent(double oldaprice, double oldbprice, double oldspread, double newaprice, double newbprice, double newspread)
		{
			Assert.AreEqual(1, testListener.OldDataList.Count);
			Assert.AreEqual(1, testListener.LastOldData.Length);
			Assert.AreEqual(1, testListener.NewDataList.Count); // since event null is put into the list
			Assert.AreEqual(1, testListener.LastNewData.Length);
			
			EventBean oldEvent = testListener.LastOldData[0];
			EventBean newEvent = testListener.LastNewData[0];
			
			compareSpreadEvent(oldEvent, oldaprice, oldbprice, oldspread);
			compareSpreadEvent(newEvent, newaprice, newbprice, newspread);
			
			testListener.reset();
		}
		
		private void  assertOldSpreadEvent(double aprice, double bprice, double spread)
		{
			Assert.AreEqual(1, testListener.OldDataList.Count);
			Assert.AreEqual(1, testListener.LastOldData.Length);
			Assert.AreEqual(1, testListener.NewDataList.Count); // since event null is put into the list
			Assert.IsNull(testListener.LastNewData);
			
			EventBean _event = testListener.LastOldData[0];
			
			compareSpreadEvent(_event, aprice, bprice, spread);
			testListener.reset();
		}
		
		private void  assertNewSpreadEvent(double aprice, double bprice, double spread)
		{
			Assert.AreEqual(1, testListener.NewDataList.Count);
			Assert.AreEqual(1, testListener.LastNewData.Length);
			Assert.AreEqual(1, testListener.OldDataList.Count);
			Assert.IsNull(testListener.LastOldData);
			
			EventBean _event = testListener.LastNewData[0];
			compareSpreadEvent(_event, aprice, bprice, spread);
			testListener.reset();
		}
		
		private void  compareSpreadEvent(EventBean _event, double aprice, double bprice, double spread)
		{
			Assert.AreEqual(aprice, _event["aPrice"]);
			Assert.AreEqual(bprice, _event["bPrice"]);
			Assert.AreEqual(spread, _event["spread"]);
		}
		
		private void  sendPriceEvent(String symbol, double price)
		{
			epService.EPRuntime.SendEvent(new SupportMarketDataBean(symbol, price, - 1L, null));
		}
		
		private void  runAssertion()
		{
			// assert select result type
			Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
			Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("price"));
			Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("avgPrice"));
			
			SendEvent(SYMBOL_DELL, 10);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent(SYMBOL_DELL, 5);
			assertNewEvents(SYMBOL_DELL, 5d, 7.5d);
			
			SendEvent(SYMBOL_DELL, 15);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent(SYMBOL_DELL, 8); // avg = (10 + 5 + 15 + 8) / 4 = 38/4=9.5
			assertNewEvents(SYMBOL_DELL, 8d, 9.5d);
			
			SendEvent(SYMBOL_DELL, 10); // avg = (10 + 5 + 15 + 8 + 10) / 5 = 48/5=9.5
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent(SYMBOL_DELL, 6); // avg = (5 + 15 + 8 + 10 + 6) / 5 = 44/5=8.8
			// no old event posted, old event falls above current avg price
			assertNewEvents(SYMBOL_DELL, 6d, 8.8d);
			
			SendEvent(SYMBOL_DELL, 12); // avg = (15 + 8 + 10 + 6 + 12) / 5 = 51/5=10.2
			assertOldEvents(SYMBOL_DELL, 5d, 8.8d);
		}
		
		private void  assertNewEvents(
			String symbol,
			Double newPrice,
			Double newAvgPrice)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.IsNull(oldData);
			Assert.AreEqual(1, newData.Length);
			
			Assert.AreEqual(symbol, newData[0]["symbol"]);
			Assert.AreEqual(newPrice, newData[0]["price"]);
			Assert.AreEqual(newAvgPrice, newData[0]["avgPrice"]);
			
			testListener.reset();
		}
		
		private void  assertOldEvents(String symbol, double oldPrice, Double oldAvgPrice)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.IsNull(newData);
			Assert.AreEqual(1, oldData.Length);
			
			Assert.AreEqual(symbol, oldData[0]["symbol"]);
			Assert.AreEqual(oldPrice, oldData[0]["price"]);
			Assert.AreEqual(oldAvgPrice, oldData[0]["avgPrice"]);
			
			testListener.reset();
		}
		
		private void  assertEvents(String symbol, Double oldPrice, Double oldAvgPrice, Double newPrice, Double newAvgPrice)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.AreEqual(1, oldData.Length);
			Assert.AreEqual(1, newData.Length);
			
			Assert.AreEqual(symbol, oldData[0]["symbol"]);
			Assert.AreEqual(oldPrice, oldData[0]["price"]);
			Assert.AreEqual(oldAvgPrice, oldData[0]["avgPrice"]);
			
			Assert.AreEqual(symbol, newData[0]["symbol"]);
			Assert.AreEqual(newPrice, newData[0]["price"]);
			Assert.AreEqual(newAvgPrice, newData[0]["avgPrice"]);
			
			testListener.reset();
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  SendEvent(String symbol, double price)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
			epService.EPRuntime.SendEvent(bean);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
