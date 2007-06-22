using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.regression.support;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{

	[TestFixture]
	public class TestTimerWithinGuard : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "(id=\"B1\") where timer:within(2001 msec)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "(id=\"B1\") where timer:within(2 sec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "(id=\"B1\") where timer:within(1999 msec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "(id=\"B3\") where timer:within(10001 msec)");
			testCase.Add("B3", "b", events.GetEvent("B3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "(id=\"B3\") where timer:within(10 sec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "(id=\"B3\") where timer:within(9.999)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(2.001)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("(every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(2.001)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("(every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(4001 milliseconds)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(4001 millisecond)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + " where timer:within(2001 msec))");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every ((every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(2.001))");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(6.001)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B3", "b", events.GetEvent("B3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every ((every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(6.001))");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B3", "b", events.GetEvent("B3"));
			testCase.Add("B3", "b", events.GetEvent("B3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every ((every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + ") where timer:within(4.001))");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + " where timer:within(4001 milliseconds)");
			testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(4 sec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (4.001) and d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6.001))");
			testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
			testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2001 msec) and d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec)");
			testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2001 msec) and d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6000 msec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2000 msec) and d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(4000 msec)");
			testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
			testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(4000 msec)");
			testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
			testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"));
			testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"));
			testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(3999 msec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(2001 msec)");
			testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
			testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "()) where timer:within(6001 msec)");
			testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
			testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2000 msec) or d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6000 msec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2000 msec) or d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6000 msec)) where timer:within (1999 msec)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2001 msec) and d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec))");
			testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2001 msec) or d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec)");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2000 msec) or d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec)");
			testCase.Add("D1", "d", events.GetEvent("D1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2001 msec) and every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec)");
			testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
			testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
			testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
			testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"));
			testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
			testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() where timer:within (2000 msec) and every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() where timer:within(6001 msec)");
			testCaseList.AddTest(testCase);

			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.RunTest();
		}

		[Test]
		public virtual void  testInterval()
		{
			EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();

			// External clocking
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
			sendTimer(0, epService);

			// Set up a timer:within
			EPStatement statement = epService.EPAdministrator.CreateEQL("select * from pattern [(every " + typeof(SupportBean).FullName + ") where timer:within(10 min)]");

			SupportUpdateListener testListener = new SupportUpdateListener();
			statement.AddListener(testListener);

			SendEvent(epService);
			testListener.AssertOneGetNewAndReset();

			sendTimer(10 * 60 * 1000 - 1, epService);
			SendEvent(epService);
			testListener.AssertOneGetNewAndReset();

			sendTimer(10 * 60 * 1000, epService);
			SendEvent(epService);
			Assert.IsFalse(testListener.IsInvoked);
		}

		private void  sendTimer(long timeInMSec, EPServiceProvider epService)
		{
			CurrentTimeEvent _event = new CurrentTimeEvent(timeInMSec);
			EPRuntime runtime = epService.EPRuntime;
			runtime.SendEvent(_event);
		}

		private void  SendEvent(EPServiceProvider epService)
		{
			SupportBean _event = new SupportBean();
			epService.EPRuntime.SendEvent(_event);
		}
	}
}
