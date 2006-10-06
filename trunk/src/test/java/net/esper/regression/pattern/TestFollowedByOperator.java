package net.esper.regression.pattern;

import junit.framework.*;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.bean.SupportBean_C;
import net.esper.support.util.SupportUpdateListener;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.event.EventBean;

public class TestFollowedByOperator extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

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
        Configuration config = new Configuration();
        config.addEventTypeAlias("A", SupportBean_A.class.getName());
        config.addEventTypeAlias("B", SupportBean_B.class.getName());
        config.addEventTypeAlias("C", SupportBean_C.class.getName());

        EPServiceProvider epService = EPServiceProviderManager.getProvider("TestCheckinStmt", config);
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmt =
          "select * from pattern [" +
            " every a=A -> (timer:interval(10000) and not (B(id=a.id) or C(id=a.id)))" +
          "] ";

        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(listener);

        // test case where no Completed or Cancel event arrives
        sendTimer(0, epService);
        SupportBean_A eventA = sendA("A1", epService);
        sendTimer(9999, epService);
        assertFalse(listener.isInvoked());
        sendTimer(10000, epService);
        EventBean received = listener.assertOneGetNewAndReset();
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
        sendA("A5", epService);
        sendB("B4", epService);
        sendC("A5", epService);
        sendTimer(50000, epService);
        received = listener.assertOneGetNewAndReset();
        assertEquals(eventA, received.get("a"));
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
}