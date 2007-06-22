using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestJoinStartStop
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;
		
		private Object[] setOne = new Object[5];
		private Object[] setTwo = new Object[5];
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();
			
			String joinStatement = "select * from " + typeof(SupportMarketDataBean).FullName + "(symbol='IBM').win:length(3) s0, " + typeof(SupportMarketDataBean).FullName + "(symbol='CSCO').win:length(3) s1" + " where s0.volume=s1.volume";
			log.Info(".setUp statement=" + joinStatement);
			
			joinView = epService.EPAdministrator.CreateEQL(joinStatement);
			joinView.AddListener(updateListener);
			
			long[] volumesOne = new long[]{10, 20, 20, 40, 50};
			long[] volumesTwo = new long[]{10, 20, 30, 40, 50};
			
			for (int i = 0; i < setOne.Length; i++)
			{
				setOne[i] = new SupportMarketDataBean("IBM", volumesOne[i], (long) i, "");
				setTwo[i] = new SupportMarketDataBean("CSCO", volumesTwo[i], (long) i, "");
			}
		}
		
		[Test]
		public virtual void  testJoinUniquePerId()
		{
			SendEvent(setOne[0]);
			SendEvent(setTwo[0]);
			Assert.IsNotNull(updateListener.LastNewData);
			updateListener.Reset();
			
			joinView.Stop();
			SendEvent(setOne[1]);
			SendEvent(setTwo[1]);
			Assert.IsFalse(updateListener.IsInvoked);
			
			joinView.Start();
			SendEvent(setOne[2]);
			Assert.IsFalse(updateListener.IsInvoked);
			
			joinView.Stop();
			SendEvent(setOne[3]);
			SendEvent(setOne[4]);
			SendEvent(setTwo[3]);
			
			joinView.Start();
			SendEvent(setTwo[4]);
			Assert.IsFalse(updateListener.IsInvoked);
		}
		
		private void  SendEvent(Object _event)
		{
			epService.EPRuntime.SendEvent(_event);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
