package net.esper.regression.pattern;

import junit.framework.*;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;
import net.esper.support.util.SupportUpdateListener;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;

public class TestTimerIntervalObserver extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        // The wait is done when 2 seconds passed
        testCase = new EventExpressionCase("timer:interval(1999 msec)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(2 sec)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        // 3 seconds (>2001 microseconds) passed
        testCase = new EventExpressionCase("timer:interval(2.001)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(2999 milliseconds)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3 seconds)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3.001 seconds)");
        testCase.add("B2");
        testCaseList.addTest(testCase);

        // Try with an all ... repeated timer every 3 seconds
        testCase = new EventExpressionCase("every timer:interval(3.001 sec)");
        testCase.add("B2");
        testCase.add("F1");
        testCase.add("D3");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:interval(5000 msec)");
        testCase.add("A2");
        testCase.add("B3");
        testCaseList.addTest(testCase);


        testCase = new EventExpressionCase("timer:interval(3.999 second) -> b=" + EVENT_B_CLASS);
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(4 sec) -> b=" + EVENT_B_CLASS);
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(4.001 sec) -> b=" + EVENT_B_CLASS);
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(0) -> b=" + EVENT_B_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        // Try with an followed-by as a second argument
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(0.001)");
        testCase.add("C1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(0)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(1 sec)");
        testCase.add("C1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(1.001)");
        testCase.add("B2", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        // Try in a 3-way followed by
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() -> timer:interval(6.000) -> d=" + EVENT_D_CLASS);
        testCase.add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() -> timer:interval(2.001) -> d=" + EVENT_D_CLASS + "())");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() -> timer:interval(2.000) -> d=" + EVENT_D_CLASS + "())");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        // Try with an "or"
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() or timer:interval(1.001)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() or timer:interval(2.001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id='B3') or timer:interval(8.500)");
        testCase.add("D2");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(8.500) or timer:interval(7.500)");
        testCase.add("F1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(999999 msec) or g=" + EVENT_G_CLASS);
        testCase.add("G1", "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        // Try with an "and"
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() and timer:interval(4000 msec)");
        testCase.add("B2", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() and timer:interval(4001 msec)");
        testCase.add("A2", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(9999999 msec) and b=" + EVENT_B_CLASS);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(1 msec) and b=" + EVENT_B_CLASS + "(id=\"B2\")");
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        // Try with an "within"
        testCase = new EventExpressionCase("timer:interval(3.000) where timer:within(2.000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3.000) where timer:within (3.000)");
        testCaseList.addTest(testCase);

        // Run all tests
        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    /**
     * As of release 1.6 this no longer updates listeners when the statement is started.
     * The reason is that the dispatch view only gets attached after a pattern started, therefore
     * ZeroDepthEventStream looses the event.
     * There should be no use case requiring this
     *
    testCase = new EventExpressionCase("not timer:interval(5000 millisecond)");
    testCase.add(EventCollection.ON_START_EVENT_ID);
    testCaseList.addTest(testCase);
     */

    public void testIntervalSpec()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        // External clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0, epService);

        // Set up a timer:within
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select * from pattern [timer:interval(1 minute 2 seconds)]");

        SupportUpdateListener testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        sendTimer(62*1000 - 1, epService);
        assertFalse(testListener.isInvoked());

        sendTimer(62*1000, epService);
        assertTrue(testListener.isInvoked());
    }

    private void sendTimer(long timeInMSec, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}