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
	public class TestGroupByMaxMin 
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
		public virtual void  testMinMaxView()
		{
			String viewExpr = "select symbol, " + "min(all volume) as minVol," + "max(all volume) as maxVol," + "min(distinct volume) as minDistVol," + "max(distinct volume) as maxDistVol" + " from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			runAssertion();
		}
		
		[Test]
		public virtual void  testMinMaxJoin()
		{
			String viewExpr = "select symbol, " + "min(volume) as minVol," + "max(volume) as maxVol," + "min(distinct volume) as minDistVol," + "max(distinct volume) as maxDistVol" + " from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " + "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + "  and one.str = two.symbol " + "group by symbol";
			
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
			Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("minVol"));
			Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("maxVol"));
			Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("minDistVol"));
			Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("maxDistVol"));
			
			SendEvent(SYMBOL_DELL, 50L);
			assertEvents(SYMBOL_DELL, null, null, null, null, SYMBOL_DELL, 50L, 50L, 50L, 50L);
			
			SendEvent(SYMBOL_DELL, 30L);
			assertEvents(SYMBOL_DELL, 50L, 50L, 50L, 50L, SYMBOL_DELL, 30L, 50L, 30L, 50L);
			
			SendEvent(SYMBOL_DELL, 30L);
			assertEvents(SYMBOL_DELL, 30L, 50L, 30L, 50L, SYMBOL_DELL, 30L, 50L, 30L, 50L);
			
			SendEvent(SYMBOL_DELL, 90L);
			assertEvents(SYMBOL_DELL, 30L, 50L, 30L, 50L, SYMBOL_DELL, 30L, 90L, 30L, 90L);
			
			SendEvent(SYMBOL_DELL, 100L);
			assertEvents(SYMBOL_DELL, 30L, 90L, 30L, 90L, SYMBOL_DELL, 30L, 100L, 30L, 100L);
			
			SendEvent(SYMBOL_IBM, 20L);
			SendEvent(SYMBOL_IBM, 5L);
			SendEvent(SYMBOL_IBM, 15L);
			SendEvent(SYMBOL_IBM, 18L);
			assertEvents(SYMBOL_IBM, 5L, 20L, 5L, 20L, SYMBOL_IBM, 5L, 18L, 5L, 18L);
			
			SendEvent(SYMBOL_IBM, null);
			assertEvents(SYMBOL_IBM, 5L, 18L, 5L, 18L, SYMBOL_IBM, 15L, 18L, 15L, 18L);
			
			SendEvent(SYMBOL_IBM, null);
			assertEvents(SYMBOL_IBM, 15L, 18L, 15L, 18L, SYMBOL_IBM, 18L, 18L, 18L, 18L);
			
			SendEvent(SYMBOL_IBM, null);
			assertEvents(SYMBOL_IBM, 18L, 18L, 18L, 18L, SYMBOL_IBM, null, null, null, null);
		}
		
		private void  assertEvents(
			String symbolOld,
            long? minVolOld,
			long? maxVolOld,
			long? minDistVolOld,
			long? maxDistVolOld,
			string symbolNew,
            long? minVolNew,
            long? maxVolNew,
            long? minDistVolNew,
            long? maxDistVolNew)
		{
			EventBean[] oldData = testListener.LastOldData;
			EventBean[] newData = testListener.LastNewData;
			
			Assert.AreEqual(1, oldData.Length);
			Assert.AreEqual(1, newData.Length);
			
			Assert.AreEqual(symbolOld, oldData[0]["symbol"]);
			Assert.AreEqual(minVolOld, oldData[0]["minVol"]);
			Assert.AreEqual(maxVolOld, oldData[0]["maxVol"]);
			Assert.AreEqual(minDistVolOld, oldData[0]["minDistVol"]);
			Assert.AreEqual(maxDistVolOld, oldData[0]["maxDistVol"]);
			
			Assert.AreEqual(symbolNew, newData[0]["symbol"]);
			Assert.AreEqual(minVolNew, newData[0]["minVol"]);
			Assert.AreEqual(maxVolNew, newData[0]["maxVol"]);
			Assert.AreEqual(minDistVolNew, newData[0]["minDistVol"]);
			Assert.AreEqual(maxDistVolNew, newData[0]["maxDistVol"]);
			
			testListener.reset();
			Assert.IsFalse(testListener.Invoked);
		}
		
		private void  SendEvent(String symbol, long? volume)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
			epService.EPRuntime.SendEvent(bean);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
