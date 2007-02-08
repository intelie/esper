using System;
using System.Globalization;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.regression.support;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.pattern
{
	
	[TestFixture]
	public class TestFollowedByOperator : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> not d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ")");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (a_1=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "() -> b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> a_2=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + ")");
			testCase.Add("A2", "a_1", events.getEvent("A1"), "b", events.getEvent("B1"), "a_2", events.getEvent("A2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("c=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_C_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + " -> a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS);
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (a_1=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "() -> b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() -> a_2=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "())");
			testCase.Add("A2", "a_1", events.getEvent("A1"), "b", events.getEvent("B1"), "a_2", events.getEvent("A2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every ( every a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " -> every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + ")");
			testCase.Add("B1", "a", events.getEvent("A1"), "b", events.getEvent("B1"));
			testCase.Add("B2", "a", events.getEvent("A1"), "b", events.getEvent("B2"));
			testCase.Add("B3", "a", events.getEvent("A1"), "b", events.getEvent("B3"));
			testCase.Add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
			testCase.Add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
			testCase.Add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "() -> every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "())");
			testCase.Add("B1", "a", events.getEvent("A1"), "b", events.getEvent("B1"));
			testCase.Add("B2", "a", events.getEvent("A1"), "b", events.getEvent("B2"));
			testCase.Add("B3", "a", events.getEvent("A1"), "b", events.getEvent("B3"));
			testCase.Add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
			testCase.Add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
			testCaseList.AddTest(testCase);
			
			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.runTest();
		}
		
		[Test]
		public virtual void  testFollowedByWithNot()
		{
			Configuration config = new Configuration();
			config.addEventTypeAlias("A", typeof(SupportBean_A).FullName);
			config.addEventTypeAlias("B", typeof(SupportBean_B).FullName);
			config.addEventTypeAlias("C", typeof(SupportBean_C).FullName);
			
			EPServiceProvider epService = EPServiceProviderManager.GetProvider("TestCheckinStmt", config);
			epService.Initialize();
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
			
			String stmt = "select * from pattern [" + " every a=A -> (timer:interval(10 seconds) and not (B(id=a.id) or C(id=a.id)))" + "] ";
			
			SupportUpdateListener listener = new SupportUpdateListener();
			EPStatement statement = epService.EPAdministrator.createEQL(stmt);
			statement.AddListener(listener);
			
			// test case where no Completed or Cancel event arrives
			sendTimer(0, epService);
			SupportBean_A eventA = sendA("A1", epService);
			sendTimer(9999, epService);
			Assert.IsFalse(listener.Invoked);
			sendTimer(10000, epService);
			EventBean received = listener.assertOneGetNewAndReset();
			Assert.AreEqual(eventA, received["a"]);
			
			// test case where Completed event arrives within the time set
			sendTimer(20000, epService);
			eventA = sendA("A2", epService);
			sendTimer(29999, epService);
			sendB("A2", epService);
			sendTimer(30000, epService);
			Assert.IsFalse(listener.Invoked);
			
			// test case where Cancelled event arrives within the time set
			sendTimer(30000, epService);
			eventA = sendA("A3", epService);
			sendTimer(30000, epService);
			sendC("A3", epService);
			sendTimer(40000, epService);
			Assert.IsFalse(listener.Invoked);
			
			// test case where no matching Completed or Cancel event arrives
			eventA = sendA("A4", epService);
			sendA("A5", epService);
			sendB("B4", epService);
			sendC("A5", epService);
			sendTimer(50000, epService);
			received = listener.assertOneGetNewAndReset();
			Assert.AreEqual(eventA, received["a"]);
		}
		
		[Test]
		public virtual void  testFollowedByTimer()
		{
			Configuration config = new Configuration();
			config.addEventTypeAlias("CallEvent", typeof(SupportCallEvent).FullName);
			EPServiceProvider epService = EPServiceProviderManager.GetProvider("testFollowedByTimer", config);
			epService.Initialize();
			
			String expression = "select * from pattern " + "[every A=CallEvent -> every B=CallEvent(dest=A.dest, startTime in [A.startTime:A.endTime]) where timer:within (7200000)]" + "where B.source != A.source";
			EPStatement statement = epService.EPAdministrator.createEQL(expression);
			SupportUpdateListener listener = new SupportUpdateListener();
			statement.AddListener(listener);
			
			SupportCallEvent eventOne = SendEvent(epService.EPRuntime, 2000002601, "18", "123456789014795", dateToLong("2005-09-26 13:02:53.200"), dateToLong("2005-09-26 13:03:34.400"));
			SupportCallEvent eventTwo = SendEvent(epService.EPRuntime, 2000002607, "20", "123456789014795", dateToLong("2005-09-26 13:03:17.300"), dateToLong("2005-09-26 13:03:58.600"));
			
			EventBean _event = listener.assertOneGetNewAndReset();
			Assert.AreSame(eventOne, _event["A"]);
			Assert.AreSame(eventTwo, _event["B"]);
			
			SupportCallEvent eventThree = SendEvent(epService.EPRuntime, 2000002610, "22", "123456789014795", dateToLong("2005-09-26 13:03:31.300"), dateToLong("2005-09-26 13:04:12.100"));
			Assert.AreEqual(1, listener.NewDataList.Count);
			Assert.AreEqual(2, listener.LastNewData.Length);
			_event = listener.LastNewData[0];
			Assert.AreSame(eventOne, _event["A"]);
			Assert.AreSame(eventThree, _event["B"]);
			_event = listener.LastNewData[1];
			Assert.AreSame(eventTwo, _event["A"]);
			Assert.AreSame(eventThree, _event["B"]);
		}
		
		private long dateToLong(String dateText)
		{
            String format = "yyyy-MM-dd HH:mm:ss.SSS";
            DateTime date = DateTime.ParseExact(dateText, format, CultureInfo.CurrentCulture);
			log.Debug(".dateToLong out=" + date.ToString(format));
			return date.Ticks;
		}
		
		private SupportCallEvent SendEvent(EPRuntime runtime, long callId, String source, String destination, long StartTime, long endTime)
		{
			SupportCallEvent _event = new SupportCallEvent(callId, source, destination, StartTime, endTime);
			runtime.SendEvent(_event);
			return _event;
		}
		
		private SupportBean_A sendA(String id, EPServiceProvider epService)
		{
			SupportBean_A a = new SupportBean_A(id);
			epService.EPRuntime.SendEvent(a);
			return a;
		}
		
		private void  sendB(String id, EPServiceProvider epService)
		{
			SupportBean_B b = new SupportBean_B(id);
			epService.EPRuntime.SendEvent(b);
		}
		
		private void  sendC(String id, EPServiceProvider epService)
		{
			SupportBean_C c = new SupportBean_C(id);
			epService.EPRuntime.SendEvent(c);
		}
		
		private void  sendTimer(long time, EPServiceProvider epService)
		{
			CurrentTimeEvent _event = new CurrentTimeEvent(time);
			epService.EPRuntime.SendEvent(_event);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
