package net.esper.regression.pattern;

import junit.framework.*;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;

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
}