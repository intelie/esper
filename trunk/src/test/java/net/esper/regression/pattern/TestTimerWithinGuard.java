package net.esper.regression.pattern;

import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import junit.framework.*;

public class TestTimerWithinGuard extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id=\"B1\") where timer:within(2001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id=\"B1\") where timer:within(2000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id=\"B1\") where timer:within(1999)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id=\"B3\") where timer:within(10001)");
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id=\"B3\") where timer:within(10000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id=\"B3\") where timer:within(9999)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + ") where timer:within(2001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(every b=" + EVENT_B_CLASS + ") where timer:within(2001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(every b=" + EVENT_B_CLASS + ") where timer:within(4001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + ") where timer:within(4001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " where timer:within(2001))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every ((every b=" + EVENT_B_CLASS + ") where timer:within(2001))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + ") where timer:within(6001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every ((every b=" + EVENT_B_CLASS + ") where timer:within(6001))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every ((every b=" + EVENT_B_CLASS + ") where timer:within(4001))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + " where timer:within(4001)");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() -> d=" + EVENT_D_CLASS + "() where timer:within(4000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() where timer:within (4001) and d=" + EVENT_D_CLASS + "() where timer:within(6001))");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() where timer:within (2001) and d=" + EVENT_D_CLASS + "() where timer:within(6001)");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() where timer:within (2001) and d=" + EVENT_D_CLASS + "() where timer:within(6000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() where timer:within (2000) and d=" + EVENT_D_CLASS + "() where timer:within(6001)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + "() -> d=" + EVENT_D_CLASS + "() where timer:within(4000)");
        testCase.add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + "() -> every d=" + EVENT_D_CLASS + "() where timer:within(4000)");
        testCase.add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
        testCase.add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"));
        testCase.add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() -> d=" + EVENT_D_CLASS + "() where timer:within(3999)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + "() -> every d=" + EVENT_D_CLASS + "() where timer:within(2001)");
        testCase.add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() -> d=" + EVENT_D_CLASS + "()) where timer:within(6001)");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() where timer:within (2000) or d=" + EVENT_D_CLASS + "() where timer:within(6000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + "() where timer:within (2000) or d=" + EVENT_D_CLASS + "() where timer:within(6000)) where timer:within (1999)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() where timer:within (2001) and d=" + EVENT_D_CLASS + "() where timer:within(6001))");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() where timer:within (2001) or d=" + EVENT_D_CLASS + "() where timer:within(6001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() where timer:within (2000) or d=" + EVENT_D_CLASS + "() where timer:within(6001)");
        testCase.add("D1", "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + "() where timer:within (2001) and every d=" + EVENT_D_CLASS + "() where timer:within(6001)");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
        testCase.add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
        testCase.add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"));
        testCase.add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
        testCase.add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + "() where timer:within (2000) and every d=" + EVENT_D_CLASS + "() where timer:within(6001)");
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testInterval()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        // External clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0, epService);

        // Set up a timer:within
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select * from pattern [(every " + SupportBean.class.getName() +
                ") where timer:within(10 min)]");

        SupportUpdateListener testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        sendEvent(epService);
        testListener.assertOneGetNewAndReset();

        sendTimer(10*60*1000 - 1, epService);
        sendEvent(epService);
        testListener.assertOneGetNewAndReset();

        sendTimer(10*60*1000, epService);
        sendEvent(epService);
        assertFalse(testListener.isInvoked());
    }

    private void sendTimer(long timeInMSec, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent(EPServiceProvider epService)
    {
        SupportBean event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
    }
}