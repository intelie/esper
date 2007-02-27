using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
	
	[TestFixture]
	public class TestCountAll 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener listener;
		private EPStatement selectTestView;
		
		[SetUp]
		public virtual void  setUp()
		{
			listener = new SupportUpdateListener();
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testCount()
		{
			String statementText = "select count(*) as cnt from " + typeof(SupportMarketDataBean).FullName + ".win:time(1)";
			selectTestView = epService.EPAdministrator.CreateEQL(statementText);
            selectTestView.AddListener(listener.Update);
			
			SendEvent("DELL", 1L);
			Assert.IsTrue(listener.getAndClearIsInvoked());
			Assert.AreEqual(1, listener.LastNewData.Length);
			Assert.AreEqual(1L, listener.LastNewData[0]["cnt"]);
			
			SendEvent("DELL", 1L);
			Assert.IsTrue(listener.getAndClearIsInvoked());
			Assert.AreEqual(1, listener.LastNewData.Length);
			Assert.AreEqual(2L, listener.LastNewData[0]["cnt"]);
			
			SendEvent("DELL", 1L);
			Assert.IsTrue(listener.getAndClearIsInvoked());
			Assert.AreEqual(1, listener.LastNewData.Length);
			Assert.AreEqual(3L, listener.LastNewData[0]["cnt"]);
		}
	
		private void  SendEvent(String symbol, Int64 volume)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
			epService.EPRuntime.SendEvent(bean);
		}
	}
}
