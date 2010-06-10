package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanConstants;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportRFIDEvent;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;
import junit.framework.TestCase;

public class TestExpressionGuard extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("a=A -> (every b=B) where(b.id != 'B2')");
        testCase.add("B1", "a", events.getEvent("A1"), "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=A -> (every b=B) where(b.id != 'B3')");
        testCase.add("B1", "a", events.getEvent("A1"), "b", events.getEvent("B1"));
        testCase.add("B2", "a", events.getEvent("A1"), "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(every b=B) where(b.id != 'B3')");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(every b=B) where(b.id != 'B1')");
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }
    
    public void testVariable() {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addVariable("myVariable", "boolean", true);

        String expression =
            "select * from pattern [every a=SupportBean(string like 'A%') -> (every b=SupportBean(string like 'B%')) where (myVariable)]";

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("A2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("B1", 100));
        assertEquals(2, listener.getAndResetLastNewData().length);

        epService.getEPRuntime().setVariableValue("myVariable", false);
        
        epService.getEPRuntime().sendEvent(new SupportBean("A3", 3));
        epService.getEPRuntime().sendEvent(new SupportBean("A4", 4));
        epService.getEPRuntime().sendEvent(new SupportBean("B2", 200));
        assertFalse(listener.isInvoked());
    }

    public void testInvalid() {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        tryInvalid(epService, "select * from pattern [every SupportBean where ('abc')]",
                "Invalid parameter for pattern guard: Expression pattern guard requires a single expression as a parameter returning a true or false (boolean) value [select * from pattern [every SupportBean where ('abc')]]");
        tryInvalid(epService, "select * from pattern [every SupportBean where (abc)]",
                "Property named 'abc' is not valid in any stream [select * from pattern [every SupportBean where (abc)]]");
    }

    private void tryInvalid(EPServiceProvider epService, String epl, String message) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }
}