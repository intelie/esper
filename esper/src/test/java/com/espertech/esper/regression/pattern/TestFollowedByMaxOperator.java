/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.client.hook.ConditionHandlerContext;
import com.espertech.esper.client.hook.ConditionHandlerFactoryContext;
import com.espertech.esper.client.hook.ConditionPatternSubexpressionMax;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConditionHandlerFactory;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TestFollowedByMaxOperator extends TestCase implements SupportBeanConstants
{
    private EPServiceProvider epService;

    public void setUp() {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBean_A", SupportBean_A.class);
        config.addEventType("SupportBean_B", SupportBean_B.class);
        config.addEventType("SupportBean_C", SupportBean_C.class);
        config.getEngineDefaults().getConditionHandling().addClass(SupportConditionHandlerFactory.class);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testInvalid() {
        tryInvalid("select * from pattern[a=SupportBean_A -[a.intPrimitive]> SupportBean_B]",
                "Invalid maximum expression in followed-by, event properties are not allowed within the expression [select * from pattern[a=SupportBean_A -[a.intPrimitive]> SupportBean_B]]");
        tryInvalid("select * from pattern[a=SupportBean_A -[false]> SupportBean_B]",
                "Invalid maximum expression in followed-by, the expression must return an integer value [select * from pattern[a=SupportBean_A -[false]> SupportBean_B]]");
    }

    public void tryInvalid(String text, String message) {
        try {
            epService.getEPAdministrator().createEPL(text);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    public void testMultiple() {
        SupportConditionHandlerFactory.SupportConditionHandler handler = SupportConditionHandlerFactory.getLastHandler();

        String expression = "select a.id as a, b.id as b, c.id as c from pattern [" +
                "every a=SupportBean_A -[2]> b=SupportBean_B -[3]> c=SupportBean_C]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);

        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);
        String fields[] = new String[] {"a", "b", "c"};

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        assertTrue(handler.getContexts().isEmpty());
        
        epService.getEPRuntime().sendEvent(new SupportBean_B("B2"));
        assertContext(epService, stmt, handler.getContexts(), 3);

        epService.getEPRuntime().sendEvent(new SupportBean_C("C1"));
        assertTrue(handler.getContexts().isEmpty());
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A1","B1","C1"}, {"A2","B1","C1"}, {"A3","B2","C1"}});
    }

    public void testMixed() {
        SupportConditionHandlerFactory.SupportConditionHandler handler = SupportConditionHandlerFactory.getLastHandler();

        String expression = "select a.id as a, b.id as b, c.id as c from pattern [" +
                "every a=SupportBean_A -> b=SupportBean_B -[2]> c=SupportBean_C]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);

        runAssertionMixed(epService, stmt, handler);

        // test SODA
        stmt.destroy();
        EPStatementObjectModel model =  epService.getEPAdministrator().compileEPL(expression);
        assertEquals(expression, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        assertEquals(stmt.getText(), model.toEPL());
        runAssertionMixed(epService, stmt, handler);
    }

    private static void runAssertionMixed(EPServiceProvider epService, EPStatement stmt, SupportConditionHandlerFactory.SupportConditionHandler handler) {
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);
        String fields[] = new String[] {"a", "b", "c"};

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));

        handler.getContexts().clear();
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        assertContext(epService, stmt, handler.getContexts(), 2);

        epService.getEPRuntime().sendEvent(new SupportBean_C("C1"));
        assertTrue(handler.getContexts().isEmpty());
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A1","B1","C1"}, {"A2","B1","C1"}});
    }

    public void testSinglePermFalseAndQuit() {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        ConditionHandlerFactoryContext context = SupportConditionHandlerFactory.getFactoryContexts().get(0);
        assertEquals(epService.getURI(), context.getEngineURI());
        SupportConditionHandlerFactory.SupportConditionHandler handler = SupportConditionHandlerFactory.getLastHandler();
        SupportUpdateListener listener = new SupportUpdateListener();

        // not-operator
        String expression = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[2]> (b=SupportBean_B and not SupportBean_C)]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        stmt.addListener(listener);
        String fields[] = new String[] {"a", "b"};

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_C("C1"));

        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        assertTrue(handler.getContexts().isEmpty());
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A3","B1"}, {"A4","B1"}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("A5"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A6"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A7"));
        assertContext(epService, stmt, handler.getContexts(), 2);
        stmt.destroy();

        // guard
        String expressionTwo = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[2]> (b=SupportBean_B where timer:within(1))]";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(expressionTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2000)); // expires sub-expressions
        assertTrue(handler.getContexts().isEmpty());

        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        assertTrue(handler.getContexts().isEmpty());
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A3","B1"}, {"A4","B1"}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("A5"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A6"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A7"));
        assertContext(epService, stmtTwo, handler.getContexts(), 2);

        // every-operator
        stmtTwo.destroy();
        String expressionThree = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[2]> (every b=SupportBean_B(id=a.id) and not SupportBean_C(id=a.id))]";
        EPStatement stmtThree = epService.getEPAdministrator().createEPL(expressionThree);
        stmtThree.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("2"));

        epService.getEPRuntime().sendEvent(new SupportBean_B("1"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"1","1"}});

        epService.getEPRuntime().sendEvent(new SupportBean_B("2"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"2","2"}});

        epService.getEPRuntime().sendEvent(new SupportBean_C("1"));

        epService.getEPRuntime().sendEvent(new SupportBean_A("3"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("3"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"3","3"}});
    }

    public void testSingleMaxSimple()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean_A", SupportBean_A.class);
        config.addEventType("SupportBean_B", SupportBean_B.class);
        config.getEngineDefaults().getConditionHandling().addClass(SupportConditionHandlerFactory.class);
        
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        ConditionHandlerFactoryContext context = SupportConditionHandlerFactory.getFactoryContexts().get(0);
        assertEquals(epService.getURI(), context.getEngineURI());
        SupportConditionHandlerFactory.SupportConditionHandler handler = SupportConditionHandlerFactory.getLastHandler();

        String expression = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[2]> b=SupportBean_B]";
        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        runAssertionSingleMaxSimple(epService, statement, handler);
        statement.destroy();

        // test SODA
        EPStatementObjectModel model =  epService.getEPAdministrator().compileEPL(expression);
        assertEquals(expression, model.toEPL());
        statement = epService.getEPAdministrator().create(model);
        assertEquals(statement.getText(), model.toEPL());
        runAssertionSingleMaxSimple(epService, statement, handler);
        statement.destroy();
        
        // test variable
        epService.getEPAdministrator().createEPL("create variable int myvar=3");
        expression = "select a.id as a, b.id as b from pattern [every a=SupportBean_A -[myvar-1]> b=SupportBean_B]";
        statement = epService.getEPAdministrator().createEPL(expression);
        runAssertionSingleMaxSimple(epService, statement, handler);
    }

    private static void runAssertionSingleMaxSimple(EPServiceProvider epService, EPStatement stmt, SupportConditionHandlerFactory.SupportConditionHandler handler) {

        String fields[] = new String[] {"a", "b"};
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));

        handler.getContexts().clear();
        epService.getEPRuntime().sendEvent(new SupportBean_A("A3"));
        assertContext(epService, stmt, handler.getContexts(), 2);

        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A1","B1"}, {"A2","B1"}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("A4"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B2"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A4","B2"}});
        assertTrue(handler.getContexts().isEmpty());

        for (int i = 5; i < 9; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean_A("A" + i));
            if (i >= 7) {
                assertContext(epService, stmt, handler.getContexts(), 2);
            }
        }

        epService.getEPRuntime().sendEvent(new SupportBean_B("B3"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A5","B3"}, {"A6","B3"}});

        epService.getEPRuntime().sendEvent(new SupportBean_B("B4"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_A("A20"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A21"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B5"));
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"A20","B5"}, {"A21","B5"}});
        assertTrue(handler.getContexts().isEmpty());
    }

    private static void assertContext(EPServiceProvider epService, EPStatement stmt, List<ConditionHandlerContext> contexts, int max) {
        assertEquals(1, contexts.size());
        ConditionHandlerContext context = contexts.get(0);
        assertEquals(epService.getURI(), context.getEngineURI());
        assertEquals(stmt.getText(), context.getEpl());
        assertEquals(stmt.getName(), context.getStatementName());
        ConditionPatternSubexpressionMax condition = (ConditionPatternSubexpressionMax) context.getEngineCondition();
        assertEquals(max, condition.getMax());
        contexts.clear();
    }

    private static Log log = LogFactory.getLog(TestFollowedByMaxOperator.class);
}
