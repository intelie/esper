package com.espertech.esper.regression.pattern;

import junit.framework.*;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.SupportBeanConstants;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestNotOperator extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and not d=" + EVENT_D_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        String text = "select * from pattern [(every (b=" + EVENT_B_CLASS + ")) and (not g=" + EVENT_G_CLASS + ")]";
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        PatternExpr pattern = Patterns.and()
                .add(Patterns.everyFilter(EVENT_B_CLASS, "b"))
                .add(Patterns.notFilter(EVENT_G_CLASS, "g"));
        model.setFromClause(FromClause.create(PatternStream.create(pattern)));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(text, model.toEPL());
        testCase = new EventExpressionCase(model);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " and not g=" + EVENT_G_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " and not d=" + EVENT_D_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and not a=" + EVENT_A_CLASS + "(id='A1')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and not a2=" + EVENT_A_CLASS + "(id='A2')");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " and not b3=" + EVENT_B_CLASS + "(id='B3'))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " or not " + EVENT_D_CLASS + "())");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (every b=" + EVENT_B_CLASS + " and not " + EVENT_B_CLASS + "(id='B2'))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " and not " + EVENT_B_CLASS + "(id='B2'))");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                " not " + EVENT_A_CLASS);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                " not " + EVENT_G_CLASS);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                " not " + EVENT_G_CLASS);
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                " not " + EVENT_G_CLASS + "(id='x')");
        testCase.add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
        testCase.add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();

        /**
         * As of release 1.6 this no longer updates listeners when the statement is started.
         * The reason is that the dispatch view only gets attached after a pattern started, therefore
         * ZeroDepthEventStream looses the event.
         * There should be no use case requiring this
         *
        testCase = new EventExpressionCase("not (b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ")");
        testCase.add(EventCollection.ON_START_EVENT_ID);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("not a=" + EVENT_A_CLASS);
        testCase.add(EventCollection.ON_START_EVENT_ID);
        testCaseList.addTest(testCase);
         */
    }

    public void testUniformEvents() throws Exception
    {
        EventCollection events = EventCollectionFactory.getSetTwoExternalClock(0, 1000);
        CaseList results = new CaseList();
        EventExpressionCase desc = null;

        desc = new EventExpressionCase("every a=" + EVENT_A_CLASS + "() and not a1=" + EVENT_A_CLASS + "(id=\"A4\")");
        desc.add("B1", "a", events.getEvent("B1"));
        desc.add("B2", "a", events.getEvent("B2"));
        desc.add("B3", "a", events.getEvent("B3"));
        results.addTest(desc);

        PatternTestHarness util = new PatternTestHarness(events, results);
        util.runTest();
    }

    public void testNotTimeInterval()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("BBB", SupportBean.class);
        config.addEventType("AAA", SupportMarketDataBean.class);
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String text = "select A.string as string from pattern " +
                    "[every A=BBB(intPrimitive=123) -> (timer:interval(30 seconds) and not AAA(volume=123, symbol=A.string))]";
        EPStatement statement = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendTimer(0, epService);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 123));

        sendTimer(10000, epService);
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 123));

        sendTimer(20000, epService);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0, 123L, ""));

        sendTimer(30000, epService);
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 123));
        assertFalse(listener.isInvoked());

        sendTimer(40000, epService);
        String fields[] = new String[] {"string"};
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2"});

        statement.stop();
    }

    private void sendTimer(long timeInMSec, EPServiceProvider epService)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}