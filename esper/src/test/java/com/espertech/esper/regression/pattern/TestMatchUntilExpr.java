package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMatchUntilExpr extends TestCase implements SupportBeanConstants
{
    private Configuration config;
    
    public void setUp()
    {
        config = SupportConfigFactory.getConfiguration();
        config.addEventType("A", SupportBean_A.class.getName());
        config.addEventType("B", SupportBean_B.class.getName());
        config.addEventType("C", SupportBean_C.class.getName());
        config.addEventType("SupportBean", SupportBean.class.getName());
    }

    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase;

        testCase = new EventExpressionCase("a=A(id='A2') until D");
        testCase.add("D1", "a[0]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=A until D");
        testCase.add("D1", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=B until a=A");
        testCase.add("A1", "b[0]", null, "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=B until D(id='D3')");
        testCase.add("D3", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(a=A or b=B) until d=D(id='D3')");
        testCase.add("D3", new Object[][] {
                {"a[0]", events.getEvent("A1")},
                {"a[1]", events.getEvent("A2")},
                {"b[0]", events.getEvent("B1")},
                {"b[1]", events.getEvent("B2")},
                {"b[2]", events.getEvent("B3")},
                {"d", events.getEvent("D3")}});
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(a=A or b=B) until (g=G or d=D)");
        testCase.add("D1", new Object[][] {
                {"a[0]", events.getEvent("A1")},
                {"a[1]", events.getEvent("A2")},
                {"b[0]", events.getEvent("B1")},
                {"b[1]", events.getEvent("B2")},
                {"d", events.getEvent("D1")}});
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(d=D) until a=A(id='A1')");
        testCase.add("A1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=A until G(id='GX')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2] a=A");
        testCase.add("A2", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2..2] a=A");
        testCase.add("A2", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1] a=A");
        testCase.add("A1", "a[0]", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1:1] a=A");
        testCase.add("A1", "a[0]", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[3] a=A");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[3] b=B");
        testCase.add("B3", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[4] (a=A or b=B)");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        // the until ends the matching returning permanently false
        testCase = new EventExpressionCase("[2] b=B until a=A(id='A1')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2] b=B until c=C");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2:2] b=B until g=G(id='G1')");
        testCase.add("B2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[..4] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[..3] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[..2] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[..1] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[..1] b=B until a=A(id='A1')");
        testCase.add("A1", "b[0]", null, "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..] b=B until a=A");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2..] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2..] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2..] b=B until c=C");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2..] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        // same event triggering both clauses, until always wins, match does not count
        testCase = new EventExpressionCase("[2..] b=B until e=B(id='B2')");
        testCaseList.addTest(testCase);

        // same event triggering both clauses, until always wins, match does not count
        testCase = new EventExpressionCase("[1..] b=B until e=B(id='B1')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..2] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", null, "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..3] b=B until G");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "b[3]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..2] b=B until G");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..10] b=B until F");
        testCase.add("F1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1..10] b=B until C");
        testCase.add("C1", "b[0]", events.getEvent("B1"), "b[1]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[1:10] b=B until C");
        testCase.add("C1", "b[0]", events.getEvent("B1"), "b[1]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[0:1] b=B until C");
        testCase.add("C1", "b[0]", events.getEvent("B1"), "b[1]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("c=C -> [2] b=B -> d=D");
        testCase.add("D3", "c", events.getEvent("C1"), "b[0]", events.getEvent("B2"), "b[1]", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[3] d=D or [3] b=B");
        testCase.add("B3", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[3] d=D or [4] b=B");
        testCase.add("D3", "d[0]", events.getEvent("D1"), "d[1]", events.getEvent("D2"), "d[2]", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2] d=D and [2] b=B");
        testCase.add("D2", "d[0]", events.getEvent("D1"), "d[1]", events.getEvent("D2"), "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("d=D until timer:interval(7 sec)");
        testCase.add("E1", "d[0]", events.getEvent("D1"), "d[1]", null, "d[2]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (d=D until b=B)");
        testCase.add("B1", "d[0]", null, "b", events.getEvent("B1"));
        testCase.add("B2", "d[0]", null, "b", events.getEvent("B2"));
        testCase.add("B3", "d[0]", events.getEvent("D1"), "d[1]", events.getEvent("D2"), "d[2]", null, "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        // note precendence: every is higher then until
        testCase = new EventExpressionCase("every d=D until b=B");
        testCase.add("B1", "d[0]", null, "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(every d=D) until b=B");
        testCase.add("B1", "d[0]", null, "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=A until (every (timer:interval(6 sec) and not A))");
        testCase.add("G1", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("A until (every (timer:interval(7 sec) and not A))");
        testCase.add("D3");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[2] (a=A or b=B)");
        testCase.add("B1", "a[0]", events.getEvent("A1"), "b[0]", events.getEvent("B1"), "b[1]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every [2] a=A");
        testCase.add("A2", new Object[][] {
                {"a[0]", events.getEvent("A1")},
                {"a[1]", events.getEvent("A2")},
                });
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every [2] a=A until d=D");  // every has precedence; ESPER-339
        testCase.add("D1", new Object[][] {
                {"a[0]", events.getEvent("A1")},
                {"a[1]", events.getEvent("A2")},
                {"d", events.getEvent("D1")},
                });
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[3] (a=A or b=B)");
        testCase.add("B2", "a[0]", events.getEvent("A1"), "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("[4] (a=A or b=B)");
        testCase.add("A2", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"), "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(a=A until b=B) until c=C");
        testCase.add("C1", "a[0]", events.getEvent("A1"), "b[0]", events.getEvent("B1"), "c", events.getEvent("C1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(a=A until b=B) until g=G");
        testCase.add("G1", new Object[][] { {"a[0]", events.getEvent("A1")}, {"b[0]", events.getEvent("B1")},
                                            {"a[1]", events.getEvent("A2")}, {"b[1]", events.getEvent("B2")},
                                            {"b[2]", events.getEvent("B3")},
                                            {"g", events.getEvent("G1")}
            });
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testSelectArray()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmt ="select a, b, a[0] as a0, a[0].id as a0Id, a[1] as a1, a[1].id as a1Id, a[2] as a2, a[2].id as a2Id from pattern [a=A until b=B]";
        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        Object eventA1 = new SupportBean_A("A1");
        epService.getEPRuntime().sendEvent(eventA1);

        Object eventA2 = new SupportBean_A("A2");
        epService.getEPRuntime().sendEvent(eventA2);
        assertFalse(listener.isInvoked());

        Object eventB1 = new SupportBean_B("B1");
        epService.getEPRuntime().sendEvent(eventB1);

        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {eventA1, eventA2}, (Object[]) event.get("a"));
        assertSame(eventA1, event.get("a0"));
        assertSame(eventA2, event.get("a1"));
        assertNull(event.get("a2"));
        assertEquals("A1", event.get("a0Id"));
        assertEquals("A2", event.get("a1Id"));
        assertNull(null, event.get("a2Id"));
        assertSame(eventB1, event.get("b"));

        // try wildcard
        stmt ="select * from pattern [a=A until b=B]";
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(eventA1);
        epService.getEPRuntime().sendEvent(eventA2);
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(eventB1);

        event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {eventA1, eventA2}, (Object[]) event.get("a"));
        assertSame(eventA1, event.get("a[0]"));
        assertSame(eventA2, event.get("a[1]"));
        assertNull(event.get("a[2]"));
        assertEquals("A1", event.get("a[0].id"));
        assertEquals("A2", event.get("a[1].id"));
        assertNull(null, event.get("a[2].id"));
        assertSame(eventB1, event.get("b"));
    }

    public void testUseFilter()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmt ="select * from pattern [a=A until b=B -> c=C(id = ('C' || a[0].id || a[1].id || b.id))]";
        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        Object eventA1 = new SupportBean_A("A1");
        epService.getEPRuntime().sendEvent(eventA1);

        Object eventA2 = new SupportBean_A("A2");
        epService.getEPRuntime().sendEvent(eventA2);

        Object eventB1 = new SupportBean_B("B1");
        epService.getEPRuntime().sendEvent(eventB1);
        
        epService.getEPRuntime().sendEvent(new SupportBean_C("C1"));
        assertFalse(listener.isInvoked());

        Object eventC1 = new SupportBean_C("CA1A2B1");
        epService.getEPRuntime().sendEvent(eventC1);
        EventBean event = listener.assertOneGetNewAndReset();
        assertSame(eventA1, event.get("a[0]"));
        assertSame(eventA2, event.get("a[1]"));
        assertNull(event.get("a[2]"));
        assertSame(eventB1, event.get("b"));
        assertSame(eventC1, event.get("c"));
        statement.destroy();

        // Test equals-optimization with array event
        stmt ="select * from pattern [a=A until b=B -> c=SupportBean(string = a[1].id)]";
        listener = new SupportUpdateListener();
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));

        epService.getEPRuntime().sendEvent(new SupportBean("A3", 20));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("A2", 10));
        event = listener.assertOneGetNewAndReset();
        assertEquals(10, event.get("c.intPrimitive"));
        statement.destroy();

        // Test in-optimization
        stmt ="select * from pattern [a=A until b=B -> c=SupportBean(string in(a[2].id))]";
        listener = new SupportUpdateListener();
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));

        epService.getEPRuntime().sendEvent(new SupportBean("A2", 20));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("A3", 5));
        event = listener.assertOneGetNewAndReset();
        assertEquals(5, event.get("c.intPrimitive"));
        statement.destroy();

        // Test not-in-optimization
        stmt ="select * from pattern [a=A until b=B -> c=SupportBean(string!=a[0].id and string!=a[1].id and string!=a[2].id)]";
        listener = new SupportUpdateListener();
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));

        epService.getEPRuntime().sendEvent(new SupportBean("A2", 20));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("A1", 20));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("A6", 5));
        event = listener.assertOneGetNewAndReset();
        assertEquals(5, event.get("c.intPrimitive"));
        statement.destroy();

        // Test range-optimization
        stmt ="select * from pattern [a=SupportBean(string like 'A%') until b=SupportBean(string like 'B%') -> c=SupportBean(intPrimitive between a[0].intPrimitive and a[1].intPrimitive)]";
        listener = new SupportUpdateListener();
        statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("A1", 5));
        epService.getEPRuntime().sendEvent(new SupportBean("A2", 8));
        epService.getEPRuntime().sendEvent(new SupportBean("B1", -1));

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 20));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 5));
        event = listener.assertOneGetNewAndReset();
        assertEquals(5, event.get("c.intPrimitive"));
    }

    public void testRepeatUseTags()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmt ="select * from pattern [every [2] (a=A() -> b=B(id=a.id))]";
        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);
    }

    public void testInvalid()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        tryInvalid(epService, "every [2] (a=A() -> b=B(id=a[0].id))", "Property named 'a[0].id' is not valid in any stream [every [2] (a=A() -> b=B(id=a[0].id))]");
        tryInvalid(epService, "[-1] A", "Incorrect syntax near '-' at line 1 column 1, please check the pattern expression [[-1] A]");
        tryInvalid(epService, "[10..4] A", "Incorrect range specification, lower bounds value '10' is higher then higher bounds '4' [[10..4] A]");
        tryInvalid(epService, "[4..6] A", "Variable bounds repeat operator requires an until-expression [[4..6] A]");
        tryInvalid(epService, "[0..0] A", "Incorrect range specification, lower bounds and higher bounds values are zero [[0..0] A]");
        tryInvalid(epService, "[0] A", "Incorrect range specification, a bounds of zero is not allowed [[0] A]");
        tryInvalid(epService, "[..0] A until B", "Incorrect range specification, a high endpoint of zero is not allowed [[..0] A until B]");
        tryInvalid(epService, "[1] a=A(a[0].id='a')", "Property named 'a[0].id' is not valid in any stream [[1] a=A(a[0].id='a')]");
        tryInvalid(epService, "a=A -> B(a[0].id='a')", "Property named 'a[0].id' is not valid in any stream [a=A -> B(a[0].id='a')]");
        tryInvalid(epService, "(a=A until c=B) -> c=C", "Tag 'c' for event 'C' has already been declared for events of type com.espertech.esper.support.bean.SupportBean_B [(a=A until c=B) -> c=C]");
        tryInvalid(epService, "((a=A until b=B) until a=A)", "Tag 'a' for event 'A' used in the repeat-until operator cannot also appear in other filter expressions [((a=A until b=B) until a=A)]");
    }

    private void tryInvalid(EPServiceProvider epService, String pattern, String message)
    {
        try
        {
            epService.getEPAdministrator().createPattern(pattern);
            fail();
        }
        catch (EPException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private static Log log = LogFactory.getLog(TestMatchUntilExpr.class);
}
