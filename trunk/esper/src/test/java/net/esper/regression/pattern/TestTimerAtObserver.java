package net.esper.regression.pattern;

import junit.framework.*;
import java.util.Calendar;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;

public class TestTimerAtObserver extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005, 3, 9, 8, 00, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();

        /**
         // Start a 2004-12-9 8:00:00am and send events every 10 minutes
        "A1"    8:10
        "B1"    8:20
        "C1"    8:30
        "B2"    8:40
        "A2"    8:50
        "D1"    9:00
        "E1"    9:10
        "F1"    9:20
        "D2"    9:30
        "B3"    9:40
        "G1"    9:50
        "D3"   10:00
         */

        EventCollection testData = EventCollectionFactory.getEventSetOne(startTime, 1000 * 60 * 10);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("timer:at(10, 8, *, *, *)");
        testCase.add("A1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(10, 8, *, *, *, 1)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(5, 8, *, *, *)");
        testCase.add("A1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(10, 8, *, *, *, *)");
        testCase.add("A1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(25, 9, *, *, *)");
        testCase.add("D2");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(11, 8, *, *, *)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(19, 8, *, *, *, 59)");
        testCase.add("B1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(* / 5, *, *, *, *, *)");
        addAll(testCase);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(*, *, *, *, *, * / 10)");
        addAll(testCase);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(20, 8, *, *, *, 20)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(*, *, *, *, *)");
        addAll(testCase);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(*, *, *, *, *, *)");
        addAll(testCase);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(* / 9, *, *, *, *, *)");
        addAll(testCase);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(* / 10, *, *, *, *, *)");
        addAll(testCase);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every timer:at(* / 30, *, *, *, *)");
        testCase.add("C1");
        testCase.add("D1");
        testCase.add("D2");
        testCase.add("D3");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(10, 9, *, *, *, 10) or timer:at(30, 9, *, *, *, *)");
        testCase.add("F1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id='B3') -> timer:at(20, 9, *, *, *, *)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id='B3') -> timer:at(45, 9, *, *, *, *)");
        testCase.add("G1", "b", testData.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(59, 8, *, *, *, 59) -> d=" + EVENT_D_CLASS);
        testCase.add("D1", "d", testData.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(*, 9, *, *, *, 59) -> d=" + EVENT_D_CLASS);
        testCase.add("D2", "d", testData.getEvent("D2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *) -> b=" + EVENT_B_CLASS + "(id='B3') -> timer:at(55, *, *, *, *)");
        testCase.add("D3", "b", testData.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(40, *, *, *, *, 1) and b=" + EVENT_B_CLASS);
        testCase.add("A2", "b", testData.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(40, 9, *, *, *, 1) or d=" + EVENT_D_CLASS + "(id=\"D3\")");
        testCase.add("G1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *) -> b=" + EVENT_B_CLASS + "() -> timer:at(55, 8, *, *, *)");
        testCase.add("D1", "b", testData.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *, 1) where timer:within(1 second)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *, 1) where timer:within(30 minutes)");
        testCase.add("C1");
        testCaseList.addTest(testCase);

        /**
         * As of release 1.6 this no longer updates listeners when the statement is started.
         * The reason is that the dispatch view only gets attached after a pattern started, therefore
         * ZeroDepthEventStream looses the event.
         * There should be no use case requiring this
         *
        testCase = new EventExpressionCase("not timer:at(22, 8, *, *, *, 1)");
        testCase.add(EventCollection.ON_START_EVENT_ID);
        testCaseList.addTest(testCase);
         */

        testCase = new EventExpressionCase("timer:at(*, 9, *, *, *) and timer:at(55, *, *, *, *)");
        testCase.add("D1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("timer:at(40, 8, *, *, *, 1) and b=" + EVENT_B_CLASS);
        testCase.add("A2", "b", testData.getEvent("B1"));
        testCaseList.addTest(testCase);

        // Run all tests
        PatternTestHarness util = new PatternTestHarness(testData, testCaseList);
        util.runTest();
    }

    private void addAll (EventExpressionCase desc)
    {
        desc.add("A1");
        desc.add("B1");
        desc.add("C1");
        desc.add("B2");
        desc.add("A2");
        desc.add("D1");
        desc.add("E1");
        desc.add("F1");
        desc.add("D2");
        desc.add("B3");
        desc.add("G1");
        desc.add("D3");
    }
}