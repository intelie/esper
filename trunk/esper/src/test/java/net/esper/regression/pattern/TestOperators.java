package net.esper.regression.pattern;

import junit.framework.*;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;
import net.esper.client.soda.*;

public class TestOperators extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") " +
                " and " +
                "(a=" + EVENT_A_CLASS + " -> e=" + EVENT_E_CLASS + ")"
                );
        testCase.add("E1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"), "e", events.getEvent("E1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> (d=" + EVENT_D_CLASS + "() or a=" + EVENT_A_CLASS + ")");
        testCase.add("A2", "b", events.getEvent("B1"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        PatternExpr pattern = Patterns.followedBy(
                Patterns.filter(EVENT_B_CLASS, "b"),
                Patterns.or(Patterns.filter(EVENT_D_CLASS, "d"), Patterns.filter(EVENT_A_CLASS, "a")));
        model.setFromClause(FromClause.create(PatternStream.create(pattern)));
        String text = "select * from pattern [(b=" + EVENT_B_CLASS + ") -> ((d=" + EVENT_D_CLASS + ") or (a=" + EVENT_A_CLASS + "))]";
        assertEquals(text, model.toEQL());
        testCase = new EventExpressionCase(model);
        testCase.add("A2", "b", events.getEvent("B1"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() -> (" +
                "(d=" + EVENT_D_CLASS + "() -> a=" + EVENT_A_CLASS + "())" +
                " or " +
                "(a=" + EVENT_A_CLASS + "() -> e=" + EVENT_E_CLASS + "()))"
                );
        testCase.add("E1", "b", events.getEvent("B1"), "a", events.getEvent("A2"), "e", events.getEvent("E1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() and d=" +
                EVENT_D_CLASS + "() or a=" +
                EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + "() -> d=" + EVENT_D_CLASS + "()) or a=" + EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + "() and " +
                "d=" + EVENT_D_CLASS + "()) or " +
                "a=" + EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }
}