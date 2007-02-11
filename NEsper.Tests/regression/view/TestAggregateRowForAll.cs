using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	
	[TestFixture]
	public class TestAggregateRowForAll 
	{
		private const String JOIN_KEY = "KEY";
		
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;
		private EPStatement selectTestView;
		
		[SetUp]
		public virtual void  setUp()
		{
			testListener = new SupportUpdateListener();
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
		}
		
		[Test]
		public virtual void  testSumOneView()
		{
			String viewExpr = "select sum(longBoxed) as mySum " + "from " + typeof(SupportBean).FullName + ".win:time(10 sec)";
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			runAssert();
		}
		
		[Test]
		public virtual void  testSumJoin()
		{
			String viewExpr = "select sum(longBoxed) as mySum " + "from " + typeof(SupportBeanString).FullName + ".win:time(10) as one, " + typeof(SupportBean).FullName + ".win:time(10 sec) as two " + "where one.string = two.string";
			
			selectTestView = epService.EPAdministrator.createEQL(viewExpr);
			selectTestView.AddListener(testListener);
			
			epService.EPRuntime.SendEvent(new SupportBeanString(JOIN_KEY));
			
			runAssert();
		}
		
		private void  runAssert()
		{
			// assert select result type
			Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("mySum"));
			
			sendTimerEvent(0);
			SendEvent(10);
			Assert.AreEqual(10L, testListener.getAndResetLastNewData()[0]["mySum"]);
			
			sendTimerEvent(5000);
			SendEvent(15);
			Assert.AreEqual(25L, testListener.getAndResetLastNewData()[0]["mySum"]);
			
			sendTimerEvent(8000);
			SendEvent(- 5);
			Assert.AreEqual(20L, testListener.getAndResetLastNewData()[0]["mySum"]);
			Assert.IsNull(testListener.LastOldData);
			
			sendTimerEvent(10000);
			Assert.AreEqual(20L, testListener.LastOldData[0]["mySum"]);
			Assert.AreEqual(10L, testListener.getAndResetLastNewData()[0]["mySum"]);
			
			sendTimerEvent(15000);
			Assert.AreEqual(10L, testListener.LastOldData[0]["mySum"]);
			Assert.AreEqual(- 5L, testListener.getAndResetLastNewData()[0]["mySum"]);
			
			sendTimerEvent(18000);
			Assert.AreEqual(- 5L, testListener.LastOldData[0]["mySum"]);
			Assert.IsNull(testListener.getAndResetLastNewData()[0]["mySum"]);
		}
		
		private void  SendEvent(long longBoxed, int intBoxed, short shortBoxed)
		{
			SupportBean bean = new SupportBean();
			bean.StringValue = JOIN_KEY;
			bean.longBoxed = longBoxed;
			bean.intBoxed = intBoxed;
			bean.shortBoxed = shortBoxed;
			epService.EPRuntime.SendEvent(bean);
		}
		
		private void  SendEvent(long longBoxed)
		{
			SendEvent(longBoxed, 0, (short) 0);
		}
		
		private void  sendTimerEvent(long msec)
		{
			epService.EPRuntime.SendEvent(new CurrentTimeEvent(msec));
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
