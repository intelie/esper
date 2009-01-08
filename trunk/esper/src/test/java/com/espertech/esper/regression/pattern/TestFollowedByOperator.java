package com.espertech.esper.regression.pattern;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.core.EPStatementSPI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFollowedByOperator extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> (d=" + EVENT_D_CLASS + " or not d=" + EVENT_D_CLASS + ")");
        testCase.add("B1", "b", events.getEvent("B1"), "d", null);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> every d=" + EVENT_D_CLASS);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
        testCase.add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> not d=" + EVENT_D_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " -> every d=" + EVENT_D_CLASS);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
        testCase.add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
        testCase.add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"));
        testCase.add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
        testCase.add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " -> every d=" + EVENT_D_CLASS + ")");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
        testCase.add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (a_1=" + EVENT_A_CLASS + "() -> b=" + EVENT_B_CLASS + " -> a_2=" + EVENT_A_CLASS + ")");
        testCase.add("A2", "a_1", events.getEvent("A1"), "b", events.getEvent("B1"), "a_2", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("c=" + EVENT_C_CLASS + "() -> d=" + EVENT_D_CLASS + " -> a=" + EVENT_A_CLASS);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (a_1=" + EVENT_A_CLASS + "() -> b=" + EVENT_B_CLASS + "() -> a_2=" + EVENT_A_CLASS + "())");
        testCase.add("A2", "a_1", events.getEvent("A1"), "b", events.getEvent("B1"), "a_2", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every ( every a=" + EVENT_A_CLASS + " -> every b=" + EVENT_B_CLASS + ")");
        testCase.add("B1", "a", events.getEvent("A1"), "b", events.getEvent("B1"));
        testCase.add("B2", "a", events.getEvent("A1"), "b", events.getEvent("B2"));
        testCase.add("B3", "a", events.getEvent("A1"), "b", events.getEvent("B3"));
        testCase.add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
        testCase.add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
        testCase.add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (a=" + EVENT_A_CLASS + "() -> every b=" + EVENT_B_CLASS + "())");
        testCase.add("B1", "a", events.getEvent("A1"), "b", events.getEvent("B1"));
        testCase.add("B2", "a", events.getEvent("A1"), "b", events.getEvent("B2"));
        testCase.add("B3", "a", events.getEvent("A1"), "b", events.getEvent("B3"));
        testCase.add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
        testCase.add("B3", "a", events.getEvent("A2"), "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testFollowedByWithNot()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("A", SupportBean_A.class.getName());
        config.addEventType("B", SupportBean_B.class.getName());
        config.addEventType("C", SupportBean_C.class.getName());

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmt =
          "select * from pattern [" +
            " every a=A -> (timer:interval(10 seconds) and not (B(id=a.id) or C(id=a.id)))" +
          "] ";

        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(stmt);
        assertEquals(StatementType.SELECT, ((EPStatementSPI) statement).getStatementMetadata().getStatementType());
        statement.addListener(listener);

        SupportBean_A eventA = null;
        EventBean received = null;
        sendTimer(0, epService);

        // test case where no Completed or Cancel event arrives
        eventA = sendA("A1", epService);
        sendTimer(9999, epService);
        assertFalse(listener.isInvoked());
        sendTimer(10000, epService);
        received = listener.assertOneGetNewAndReset();
        assertEquals(eventA, received.get("a"));

        // test case where Completed event arrives within the time set
        sendTimer(20000, epService);
        eventA = sendA("A2", epService);
        sendTimer(29999, epService);
        sendB("A2", epService);
        sendTimer(30000, epService);
        assertFalse(listener.isInvoked());

        // test case where Cancelled event arrives within the time set
        sendTimer(30000, epService);
        eventA = sendA("A3", epService);
        sendTimer(30000, epService);
        sendC("A3", epService);
        sendTimer(40000, epService);
        assertFalse(listener.isInvoked());

        // test case where no matching Completed or Cancel event arrives
        eventA = sendA("A4", epService);
        sendB("B4", epService);
        sendC("A5", epService);
        sendTimer(50000, epService);
        received = listener.assertOneGetNewAndReset();
        assertEquals(eventA, received.get("a"));
    }

    public void testFollowedByTimer() throws ParseException
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("CallEvent", SupportCallEvent.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String expression = "select * from pattern " +
          "[every A=CallEvent -> every B=CallEvent(dest=A.dest, startTime in [A.startTime:A.endTime]) where timer:within (7200000)]" +
          "where B.source != A.source";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        SupportCallEvent eventOne = sendEvent(epService.getEPRuntime(), 2000002601, "18", "123456789014795", dateToLong("2005-09-26 13:02:53.200"), dateToLong("2005-09-26 13:03:34.400"));
        SupportCallEvent eventTwo = sendEvent(epService.getEPRuntime(), 2000002607, "20", "123456789014795", dateToLong("2005-09-26 13:03:17.300"), dateToLong("2005-09-26 13:03:58.600"));

        EventBean event = listener.assertOneGetNewAndReset();
        assertSame(eventOne, event.get("A"));
        assertSame(eventTwo, event.get("B"));

        SupportCallEvent eventThree = sendEvent(epService.getEPRuntime(), 2000002610, "22", "123456789014795", dateToLong("2005-09-26 13:03:31.300"), dateToLong("2005-09-26 13:04:12.100"));
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(2, listener.getLastNewData().length);
        event = listener.getLastNewData()[0];
        assertSame(eventOne, event.get("A"));
        assertSame(eventThree, event.get("B"));
        event = listener.getLastNewData()[1];
        assertSame(eventTwo, event.get("A"));
        assertSame(eventThree, event.get("B"));
    }

    public void testMemoryRFIDEvent()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("LR", SupportRFIDEvent.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String expression =
            "select 'Tag May Be Broken' as alert, " +
                "tagMayBeBroken.mac, " +
                "tagMayBeBroken.zoneID " +
            "from pattern [" +
                "every tagMayBeBroken=LR -> (timer:interval(10 sec) and not LR(mac=tagMayBeBroken.mac))" +
            "]";

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        for (int i = 0; i < 10000; i++)
        {
            /*
            if (i % 1000 == 0)
            {
                log.info(".testMemoryRFIDEvent now at " + i);
            }
            */
            SupportRFIDEvent event = new SupportRFIDEvent("a", "111");
            epService.getEPRuntime().sendEvent(event);

            event = new SupportRFIDEvent("a", "111");
            epService.getEPRuntime().sendEvent(event);
        }
    }

    public void testRFIDZoneExit()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("LR", SupportRFIDEvent.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

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

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        SupportRFIDEvent event = new SupportRFIDEvent("a", "1");
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        event = new SupportRFIDEvent("a", "2");
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().get("b"));

        event = new SupportRFIDEvent("b", "1");
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        event = new SupportRFIDEvent("b", "1");
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        event = new SupportRFIDEvent("b", "2");
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().get("b"));
    }

    public void testRFIDZoneEnter()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("LR", SupportRFIDEvent.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

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

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        SupportRFIDEvent event = new SupportRFIDEvent("a", "2");
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        event = new SupportRFIDEvent("a", "1");
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().get("b"));

        event = new SupportRFIDEvent("b", "2");
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        event = new SupportRFIDEvent("b", "2");
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        event = new SupportRFIDEvent("b", "1");
        epService.getEPRuntime().sendEvent(event);
        assertEquals(event, listener.assertOneGetNewAndReset().get("b"));
    }

    public void testFollowedNotEvery()
    {
        String expression = "select * from pattern [every A=" + SupportBean.class.getName() +
                " -> (timer:interval(1 seconds) and not " + SupportBean_A.class.getName() + ")]";

        Configuration config = SupportConfigFactory.getConfiguration();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Object eventOne = new SupportBean();
        epService.getEPRuntime().sendEvent(eventOne);

        Object eventTwo = new SupportBean();
        epService.getEPRuntime().sendEvent(eventTwo);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(2, listener.getNewDataList().get(0).length);
    }

    public void testFollowedEveryMultiple()
    {
        String expression = "select * from pattern [every a=" + SupportBean_A.class.getName() +
                " -> b=" + SupportBean_B.class.getName() +
                " -> c=" + SupportBean_C.class.getName() +
                " -> d=" + SupportBean_D.class.getName() +
                "]";

        Configuration config = SupportConfigFactory.getConfiguration();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Object[] events = new Object[10];
        events[0] = new SupportBean_A("A1");
        epService.getEPRuntime().sendEvent(events[0]);

        events[1] = new SupportBean_A("A2");
        epService.getEPRuntime().sendEvent(events[1]);

        events[2] = new SupportBean_B("B1");
        epService.getEPRuntime().sendEvent(events[2]);

        events[3] = new SupportBean_C("C1");
        epService.getEPRuntime().sendEvent(events[3]);
        assertFalse(listener.isInvoked());

        events[4] = new SupportBean_D("D1");
        epService.getEPRuntime().sendEvent(events[4]);
        assertEquals(2, listener.getLastNewData().length);
        String fields[] = new String[] {"a", "b", "c", "d"};
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {events[0], events[2], events[3], events[4]});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[1], fields, new Object[] {events[1], events[2], events[3], events[4]});
    }

    private long dateToLong(String dateText) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = format.parse(dateText);
        log.debug(".dateToLong out=" + date.toString());
        return date.getTime();
    }

    private SupportCallEvent sendEvent(EPRuntime runtime, long callId, String source, String destination, long startTime, long endTime)
    {
        SupportCallEvent event = new SupportCallEvent(callId, source, destination, startTime, endTime);
        runtime.sendEvent(event);
        return event;
    }

    private SupportBean_A sendA(String id, EPServiceProvider epService)
    {
        SupportBean_A a = new SupportBean_A(id);
        epService.getEPRuntime().sendEvent(a);
        return a;
    }

    private void sendB(String id, EPServiceProvider epService)
    {
        SupportBean_B b = new SupportBean_B(id);
        epService.getEPRuntime().sendEvent(b);
    }

    private void sendC(String id, EPServiceProvider epService)
    {
        SupportBean_C c = new SupportBean_C(id);
        epService.getEPRuntime().sendEvent(c);
    }

    private void sendTimer(long time, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        epService.getEPRuntime().sendEvent(event);
    }

    private static Log log = LogFactory.getLog(TestFollowedByOperator.class);
}