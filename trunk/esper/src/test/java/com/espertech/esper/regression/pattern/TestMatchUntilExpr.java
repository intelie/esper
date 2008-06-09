package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMatchUntilExpr extends TestCase implements SupportBeanConstants
{
    // TODO: test no until-clause for unbounded cases
    // TODO: test with until-clause
    // TODO: test "match [2] a=A until b=B -> C(a[0].id = c1, a[1].id = c2)
    // todo: test invalid use of array tag without array
    // todo: test nested match-until
    // todo: test exist() on composite events
    // todo: test statement object model
    // todo: test 2..1

    // todo: invalid cases: match[0], match [-1..-2], [..0]

    private Configuration config;
    
    public void setUp()
    {
        config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("A", SupportBean_A.class.getName());
        config.addEventTypeAlias("B", SupportBean_B.class.getName());
        config.addEventTypeAlias("C", SupportBean_C.class.getName());
        config.addEventTypeAlias("SupportBean", SupportBean.class.getName());
    }

    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        /*
        testCase = new EventExpressionCase("match a=A until D");
        testCase.add("D1", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match a=A(id='A2') until D");
        testCase.add("D1", "a[0]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match b=B until a=A");
        testCase.add("A1", "b[0]", null, "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match b=B until D(id='D3')");
        testCase.add("D3", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match (a=A or b=B) until d=D(id='D3')");
        testCase.add("D3", new Object[][] {
                {"a[0]", events.getEvent("A1")},
                {"a[1]", events.getEvent("A2")},
                {"b[0]", events.getEvent("B1")},
                {"b[1]", events.getEvent("B2")},
                {"b[2]", events.getEvent("B3")},
                {"d", events.getEvent("D3")}});
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match (a=A or b=B) until (g=G or d=D)");
        testCase.add("D1", new Object[][] {
                {"a[0]", events.getEvent("A1")},
                {"a[1]", events.getEvent("A2")},
                {"b[0]", events.getEvent("B1")},
                {"b[1]", events.getEvent("B2")},
                {"d", events.getEvent("D1")}});
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match (d=D) until a=A(id='A1')");
        testCase.add("A1");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match a=A until G(id='GX')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2] a=A");
        testCase.add("A2", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2..2] a=A");
        testCase.add("A2", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1] a=A");
        testCase.add("A1", "a[0]", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1:1] a=A");
        testCase.add("A1", "a[0]", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [3] a=A");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [3] b=B");
        testCase.add("B3", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [4] (a=A or b=B)");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        // the until ends the matching returning permanently false
        testCase = new EventExpressionCase("match [2] b=B until a=A(id='A1')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2] b=B until c=C");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2:2] b=B until g=G(id='G1')");
        testCase.add("B2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [..4] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [..3] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [..2] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [..1] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [..1] b=B until a=A(id='A1')");
        testCase.add("A1", "b[0]", null, "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..] b=B until g=G(id='G1')");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "g", events.getEvent("G1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..] b=B until a=A");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2..] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2..] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2..] b=B until c=C");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2..] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        // same event triggering both clauses, until always wins, match does not count
        testCase = new EventExpressionCase("match [2..] b=B until b=B(id='B2')");
        testCaseList.addTest(testCase);

        // same event triggering both clauses, until always wins, match does not count
        testCase = new EventExpressionCase("match [1..] b=B until b=B(id='B1')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..2] b=B until a=A(id='A2')");
        testCase.add("A2", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", null, "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..3] b=B until G");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"), "b[3]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..2] b=B until G");
        testCase.add("G1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..10] b=B until F");
        testCase.add("F1", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1..10] b=B until C");
        testCase.add("C1", "b[0]", events.getEvent("B1"), "b[1]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [1:10] b=B until C");
        testCase.add("C1", "b[0]", events.getEvent("B1"), "b[1]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("c=C -> match [2] b=B -> d=D");
        testCase.add("D3", "c", events.getEvent("C1"), "b[0]", events.getEvent("B2"), "b[1]", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [3] d=D or match [3] b=B");
        testCase.add("B3", "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"), "b[2]", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [3] d=D or match [4] b=B");
        testCase.add("D3", "d[0]", events.getEvent("D1"), "d[1]", events.getEvent("D2"), "d[2]", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match [2] d=D and match [2] b=B");
        testCase.add("D2", "d[0]", events.getEvent("D1"), "d[1]", events.getEvent("D2"), "b[0]", events.getEvent("B1"), "b[1]", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match d=D until timer:interval(7 sec)");
        testCase.add("E1", "d[0]", events.getEvent("D1"), "d[1]", null, "d[2]", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every match d=D until b=B");
        testCase.add("B1", "d[0]", null, "b", events.getEvent("B1"));
        testCase.add("B2", "d[0]", null, "b", events.getEvent("B2"));
        testCase.add("B3", "d[0]", events.getEvent("D1"), "d[1]", events.getEvent("D2"), "d[2]", null, "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);
        */

        testCase = new EventExpressionCase("match a=A until (every (timer:interval(6 sec) and not A))");
        testCase.add("G1", "a[0]", events.getEvent("A1"), "a[1]", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("match A until (every (timer:interval(7 sec) and not A))");
        testCase.add("D3");
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    /*
    testData.put("A1", new SupportBean_A("A1"));
    testData.put("B1", new SupportBean_B("B1"));
    testData.put("C1", new SupportBean_C("C1"));
    testData.put("B2", new SupportBean_B("B2"));
    testData.put("A2", new SupportBean_A("A2"));
    testData.put("D1", new SupportBean_D("D1"));
    testData.put("E1", new SupportBean_E("E1"));
    testData.put("F1", new SupportBean_F("F1"));
    testData.put("D2", new SupportBean_D("D2"));
    testData.put("B3", new SupportBean_B("B3"));
    testData.put("G1", new SupportBean_G("G1"));
    testData.put("D3", new SupportBean_D("D3"));
    */

    public void testMultiEventMatch()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmt ="select * from pattern [match a=A until b=B -> SupportBean(a[0].id = id, a[1].id = id)]";
        //String stmt ="select * from pattern [match a=A until b=B -> SupportBean(a.a0.id = id, a.a1.id = id)]";
        //String stmt ="select * from pattern [match a=A until b=B -> SupportBean(a.a[0].id = id, a.a[1].id = id)]";
        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);
    }

    public void testSelectArray()
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmt ="select a[0].id from pattern [match a=A until b=B]";
        SupportUpdateListener listener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(stmt);
        statement.addListener(listener);
    }

    private static Log log = LogFactory.getLog(TestMatchUntilExpr.class);
}
