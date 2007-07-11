///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.events;
using net.esper.regression.support;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.pattern
{
	[TestFixture]
	public class TestFollowedByOperator : SupportBeanConstants
	{
	    [Test]
	    public void testOp()
	    {
	        EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
	        CaseList testCaseList = new CaseList();
	        EventExpressionCase testCase = null;

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> every d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> not d=" + EVENT_D_CLASS);
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " -> every d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " -> every d=" + EVENT_D_CLASS + ")");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (a_1=" + EVENT_A_CLASS + "() -> b=" + EVENT_B_CLASS + " -> a_2=" + EVENT_A_CLASS + ")");
	        testCase.Add("A2", "a_1", events.GetEvent("A1"), "b", events.GetEvent("B1"), "a_2", events.GetEvent("A2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("c=" + EVENT_C_CLASS + "() -> d=" + EVENT_D_CLASS + " -> a=" + EVENT_A_CLASS);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (a_1=" + EVENT_A_CLASS + "() -> b=" + EVENT_B_CLASS + "() -> a_2=" + EVENT_A_CLASS + "())");
	        testCase.Add("A2", "a_1", events.GetEvent("A1"), "b", events.GetEvent("B1"), "a_2", events.GetEvent("A2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every ( every a=" + EVENT_A_CLASS + " -> every b=" + EVENT_B_CLASS + ")");
	        testCase.Add("B1", "a", events.GetEvent("A1"), "b", events.GetEvent("B1"));
	        testCase.Add("B2", "a", events.GetEvent("A1"), "b", events.GetEvent("B2"));
	        testCase.Add("B3", "a", events.GetEvent("A1"), "b", events.GetEvent("B3"));
	        testCase.Add("B3", "a", events.GetEvent("A2"), "b", events.GetEvent("B3"));
	        testCase.Add("B3", "a", events.GetEvent("A2"), "b", events.GetEvent("B3"));
	        testCase.Add("B3", "a", events.GetEvent("A2"), "b", events.GetEvent("B3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (a=" + EVENT_A_CLASS + "() -> every b=" + EVENT_B_CLASS + "())");
	        testCase.Add("B1", "a", events.GetEvent("A1"), "b", events.GetEvent("B1"));
	        testCase.Add("B2", "a", events.GetEvent("A1"), "b", events.GetEvent("B2"));
	        testCase.Add("B3", "a", events.GetEvent("A1"), "b", events.GetEvent("B3"));
	        testCase.Add("B3", "a", events.GetEvent("A2"), "b", events.GetEvent("B3"));
	        testCase.Add("B3", "a", events.GetEvent("A2"), "b", events.GetEvent("B3"));
	        testCaseList.AddTest(testCase);

	        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
	        util.RunTest();
	    }

	    [Test]
	    public void testFollowedByWithNot()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("A", typeof(SupportBean_A).FullName);
	        config.AddEventTypeAlias("B", typeof(SupportBean_B).FullName);
	        config.AddEventTypeAlias("C", typeof(SupportBean_C).FullName);

	        EPServiceProvider epService = EPServiceProviderManager.GetProvider("TestCheckinStmt", config);
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

	        String stmt =
	          "select * from pattern [" +
	            " every a=A -> (timer:interval(10 seconds) and not (B(id=a.id) or C(id=a.id)))" +
	          "] ";

	        SupportUpdateListener listener = new SupportUpdateListener();
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmt);
	        statement.AddListener(listener);

	        SupportBean_A eventA = null;
	        EventBean received = null;
	        SendTimer(0, epService);

	        // test case where no Completed or Cancel event arrives
	        eventA = SendA("A1", epService);
	        SendTimer(9999, epService);
	        Assert.IsFalse(listener.IsInvoked);
	        SendTimer(10000, epService);
	        received = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual(eventA, received["a"]);

	        // test case where Completed event arrives within the time set
	        SendTimer(20000, epService);
	        eventA = SendA("A2", epService);
	        SendTimer(29999, epService);
	        SendB("A2", epService);
	        SendTimer(30000, epService);
	        Assert.IsFalse(listener.IsInvoked);

	        // test case where Cancelled event arrives within the time set
	        SendTimer(30000, epService);
	        eventA = SendA("A3", epService);
	        SendTimer(30000, epService);
	        SendC("A3", epService);
	        SendTimer(40000, epService);
	        Assert.IsFalse(listener.IsInvoked);

	        // test case where no matching Completed or Cancel event arrives
	        eventA = SendA("A4", epService);
	        SendB("B4", epService);
	        SendC("A5", epService);
	        SendTimer(50000, epService);
	        received = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual(eventA, received["a"]);
	    }

	    [Test]
	    public void testFollowedByTimer()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("CallEvent", typeof(SupportCallEvent).FullName);
	        EPServiceProvider epService = EPServiceProviderManager.GetProvider("testFollowedByTimer", config);
	        epService.Initialize();

	        String expression = "select * from pattern " +
	          "[every A=CallEvent -> every B=CallEvent(dest=A.dest, startTime in [A.startTime:A.endTime]) where timer:within (7200000)]" +
	          "where B.source != A.source";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(expression);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        SupportCallEvent eventOne = SendEvent(epService.EPRuntime, 2000002601, "18", "123456789014795", DateToLong("2005-09-26 13:02:53.200"), DateToLong("2005-09-26 13:03:34.400"));
	        SupportCallEvent eventTwo = SendEvent(epService.EPRuntime, 2000002607, "20", "123456789014795", DateToLong("2005-09-26 13:03:17.300"), DateToLong("2005-09-26 13:03:58.600"));

	        EventBean _event = listener.AssertOneGetNewAndReset();
            Assert.AreSame(eventOne, _event["A"]);
            Assert.AreSame(eventTwo, _event["B"]);

	        SupportCallEvent eventThree = SendEvent(epService.EPRuntime, 2000002610, "22", "123456789014795", DateToLong("2005-09-26 13:03:31.300"), DateToLong("2005-09-26 13:04:12.100"));
	        Assert.AreEqual(1, listener.NewDataList.Count);
	        Assert.AreEqual(2, listener.LastNewData.Length);
	        _event = listener.LastNewData[0];
            Assert.AreSame(eventOne, _event["A"]);
            Assert.AreSame(eventThree, _event["B"]);
	        _event = listener.LastNewData[1];
            Assert.AreSame(eventTwo, _event["A"]);
	        Assert.AreSame(eventThree, _event["B"]);
	    }

	    [Test]
	    public void testMemoryRFIDEvent()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("LR", typeof(SupportRFIDEvent).FullName);
	        EPServiceProvider epService = EPServiceProviderManager.GetProvider("testMemoryRFIDEvent", config);
	        epService.Initialize();

	        String expression =
	            "select 'Tag May Be Broken' as alert, " +
	                "tagMayBeBroken.mac, " +
	                "tagMayBeBroken.zoneID " +
	            "from pattern [" +
	                "every tagMayBeBroken=LR -> (timer:interval(10 sec) and not LR(mac=tagMayBeBroken.mac))" +
	            "]";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(expression);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        for (int i = 0; i < 10000; i++)
	        {
	            /*
	            if (i % 1000 == 0)
	            {
	                log.Info(".testMemoryRFIDEvent now at " + i);
	            }
	            */
	            SupportRFIDEvent _event = new SupportRFIDEvent("a", "111");
	            epService.EPRuntime.SendEvent(_event);

	            _event = new SupportRFIDEvent("a", "111");
	            epService.EPRuntime.SendEvent(_event);
	        }
	    }

	    [Test]
	    public void testRFIDZoneExit()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("LR", typeof(SupportRFIDEvent).FullName);
	        EPServiceProvider epService = EPServiceProviderManager.GetProvider("testRFIDZoneExit", config);
	        epService.Initialize();

	        /**
	         * Every LR event with a zone of '1' activates a new sub-expression after
	         * the followed-by operator. The sub-expression instance can end two different ways:
	         * It ends when a LR for the same mac and a different exit-zone comes in, or
	         * it ends when a LR for the same max and the same zone come in. The latter also starts the
	         * sub-expression again.
	         */
	        String expression =
	            "select * " +
	            "from pattern [" +
	                "every a=LR(zoneID='1') -> (b=LR(mac=a.mac,zoneID!='1') and not LR(mac=a.mac,zoneID='1'))" +
	            "]";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(expression);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        SupportRFIDEvent _event = new SupportRFIDEvent("a", "1");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(listener.IsInvoked);

	        _event = new SupportRFIDEvent("a", "2");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.AreEqual(_event, listener.AssertOneGetNewAndReset()["b"]);

	        _event = new SupportRFIDEvent("b", "1");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(listener.IsInvoked);

	        _event = new SupportRFIDEvent("b", "1");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(listener.IsInvoked);

	        _event = new SupportRFIDEvent("b", "2");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.AreEqual(_event, listener.AssertOneGetNewAndReset()["b"]);
	    }

	    [Test]
	    public void testRFIDZoneEnter()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("LR", typeof(SupportRFIDEvent).FullName);
	        EPServiceProvider epService = EPServiceProviderManager.GetProvider("testRFIDZoneEnter", config);
	        epService.Initialize();

	        /**
	         * Every LR event with a zone other then '1' activates a new sub-expression after
	         * the followed-by operator. The sub-expression instance can end two different ways:
	         * It ends when a LR for the same mac and the enter-zone comes in, or
	         * it ends when a LR for the same max and the same zone come in. The latter also starts the
	         * sub-expression again.
	         */
	        String expression =
	            "select * " +
	            "from pattern [" +
	                "every a=LR(zoneID!='1') -> (b=LR(mac=a.mac,zoneID='1') and not LR(mac=a.mac,zoneID=a.zoneID))" +
	            "]";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(expression);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        SupportRFIDEvent _event = new SupportRFIDEvent("a", "2");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(listener.IsInvoked);

	        _event = new SupportRFIDEvent("a", "1");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.AreEqual(_event, listener.AssertOneGetNewAndReset()["b"]);

	        _event = new SupportRFIDEvent("b", "2");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(listener.IsInvoked);

	        _event = new SupportRFIDEvent("b", "2");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(listener.IsInvoked);

	        _event = new SupportRFIDEvent("b", "1");
	        epService.EPRuntime.SendEvent(_event);
	        Assert.AreEqual(_event, listener.AssertOneGetNewAndReset()["b"]);
	    }

	    [Test]
	    public void testFollowedNotEvery()
	    {
	        String expression = "select * from pattern [every A=" + typeof(SupportBean).FullName +
	                " -> (timer:interval(1 seconds) and not " + typeof(SupportBean_A).FullName + ")]";

	        EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));

	        EPStatement statement = epService.EPAdministrator.CreateEQL(expression);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        Object eventOne = new SupportBean();
	        epService.EPRuntime.SendEvent(eventOne);

	        Object eventTwo = new SupportBean();
	        epService.EPRuntime.SendEvent(eventTwo);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(1000));
	        Assert.AreEqual(1, listener.NewDataList.Count);
	        Assert.AreEqual(2, listener.NewDataList[0].Length);
	    }

        private static long DateToLong(String dateText)
	    {
            DateTime dateTime = DateTime.ParseExact(dateText, "yyyy-MM-dd HH:mm:ss.SSS", null);
	        log.Debug(".dateToLong out=" + dateTime);
	        return DateTimeHelper.TimeInMillis(dateTime);
	    }

        private static SupportCallEvent SendEvent(EPRuntime runtime, long callId, String source, String destination, long startTime, long endTime)
	    {
	        SupportCallEvent _event = new SupportCallEvent(callId, source, destination, startTime, endTime);
	        runtime.SendEvent(_event);
	        return _event;
	    }

	    private static SupportBean_A SendA(String id, EPServiceProvider epService)
	    {
	        SupportBean_A a = new SupportBean_A(id);
	        epService.EPRuntime.SendEvent(a);
	        return a;
	    }

        private static void SendB(String id, EPServiceProvider epService)
	    {
	        SupportBean_B b = new SupportBean_B(id);
	        epService.EPRuntime.SendEvent(b);
	    }

        private static void SendC(String id, EPServiceProvider epService)
	    {
	        SupportBean_C c = new SupportBean_C(id);
	        epService.EPRuntime.SendEvent(c);
	    }

        private static void SendTimer(long time, EPServiceProvider epService)
	    {
	        CurrentTimeEvent _event = new CurrentTimeEvent(time);
	        epService.EPRuntime.SendEvent(_event);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
