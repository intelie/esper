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
	public class TestGroupByEventPerRowHaving 
	{
		private static String SYMBOL_DELL = "DELL";
		private static String SYMBOL_IBM = "IBM";
		
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
			// Every event generates a new row, this time we sum the price by symbol and output volume
			String viewExpr = "select symbol, volume, sum(price) as mySum " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol " + "having sum(price) >= 100";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			runAssertion();
		}
		
		[Test]
		public virtual void  testSumJoin()
		{
			// Every event generates a new row, this time we sum the price by symbol and output volume
			String viewExpr = "select symbol, volume, sum(price) as mySum " + "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " + "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + "  and one.str = two.symbol " + "group by symbol " + "having sum(price) >= 100";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
			epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));
			
			runAssertion();
		}
		
		private void  runAssertion()
		{
			// assert select result type
			Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
			Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("volume"));
			Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("mySum"));
			
			SendEvent(SYMBOL_DELL, 10000, 51);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent(SYMBOL_DELL, 20000, 52);
			assertNewEvent(SYMBOL_DELL, 20000, 103);
			
			SendEvent(SYMBOL_IBM, 1000, 10);
			Assert.IsFalse(testListener.Invoked);
			
			SendEvent(SYMBOL_IBM, 5000, 60);
			assertOldEvent(SYMBOL_DELL, 10000, 103);
			
			SendEvent(SYMBOL_IBM, 6000, 5);
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  assertNewEvent(String symbol, long volume, double sum)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.IsNull(oldData);
			Assert.AreEqual(1, newData.Length);
			
			Assert.AreEqual(symbol, newData[0]["symbol"]);
			Assert.AreEqual(volume, newData[0]["volume"]);
			Assert.AreEqual(sum, newData[0]["mySum"]);
			
			testListener.reset();
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  assertOldEvent(String symbol, long volume, double sum)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.IsNull(newData);
			Assert.AreEqual(1, oldData.Length);
			
			Assert.AreEqual(symbol, oldData[0]["symbol"]);
			Assert.AreEqual(volume, oldData[0]["volume"]);
			Assert.AreEqual(sum, oldData[0]["mySum"]);
			
			testListener.reset();
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  assertEvents(String symbolOld, long volumeOld, double sumOld, String symbolNew, long volumeNew, double sumNew)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.AreEqual(1, oldData.Length);
			Assert.AreEqual(1, newData.Length);
			
			Assert.AreEqual(symbolOld, oldData[0]["symbol"]);
			Assert.AreEqual(volumeOld, oldData[0]["volume"]);
			Assert.AreEqual(sumOld, oldData[0]["mySum"]);
			
			Assert.AreEqual(symbolNew, newData[0]["symbol"]);
			Assert.AreEqual(volumeNew, newData[0]["volume"]);
			Assert.AreEqual(sumNew, newData[0]["mySum"]);
			
			testListener.reset();
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  SendEvent(String symbol, long volume, double price)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
			epService.EPRuntime.SendEvent(bean);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
