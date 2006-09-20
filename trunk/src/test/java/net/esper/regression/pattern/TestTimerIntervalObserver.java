package net.esper.regression.pattern;

import junit.framework.*;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;

public class TestTimerIntervalObserver extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        // The wait is done when 2 seconds passed
        testCase = new EventExpressionCase("timer:interval(1999)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(2000)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        // 3 seconds (>2001 microseconds) passed
        testCase = new EventExpressionCase("timer:interval(2001)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(2999)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3000)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3001)");
        testCase.add("B2");
        testCaseList.addTest(testCase);

        // Try with an all ... repeated timer every 3 seconds
        testCase = new EventExpressionCase("every timer:interval(3001)");
        testCase.add("B2");
        testCase.add("F1");
        testCase.add("D3");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:interval(5000)");
        testCase.add("A2");
        testCase.add("B3");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("not timer:interval(5000)");
        testCase.add(EventCollection.ON_START_EVENT_ID);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3999) -> b=" + EVENT_B_CLASS);
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(4000) -> b=" + EVENT_B_CLASS);
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(4001) -> b=" + EVENT_B_CLASS);
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(0) -> b=" + EVENT_B_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        // Try with an followed-by as a second argument
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(1)");
        testCase.add("C1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(0)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(1000)");
        testCase.add("C1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> timer:interval(1001)");
        testCase.add("B2", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        // Try in a 3-way followed by
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() -> timer:interval(6000) -> d=" + EVENT_D_CLASS);
        testCase.add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() -> timer:interval(2001) -> d=" + EVENT_D_CLASS + "())");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() -> timer:interval(2000) -> d=" + EVENT_D_CLASS + "())");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        // Try with an "or"
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() or timer:interval(1001)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() or timer:interval(2001)");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id='B3') or timer:interval(8500)");
        testCase.add("D2");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(8500) or timer:interval(7500)");
        testCase.add("F1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(999999) or g=" + EVENT_G_CLASS);
        testCase.add("G1", "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        // Try with an "and"
        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() and timer:interval(4000)");
        testCase.add("B2", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() and timer:interval(4001)");
        testCase.add("A2", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(9999999) and b=" + EVENT_B_CLASS);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(1) and b=" + EVENT_B_CLASS + "(id=\"B2\")");
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        // Try with an "within"
        testCase = new EventExpressionCase("timer:interval(3000) where timer:within(2000)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:interval(3000) where timer:within (3000)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        // Run all tests
        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }
}