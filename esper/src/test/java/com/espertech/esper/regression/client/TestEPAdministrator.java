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

package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.core.EPAdministratorSPI;
import com.espertech.esper.epl.expression.ExprDotNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.StatementSpecRaw;
import com.espertech.esper.pattern.EvalFollowedByNode;
import com.espertech.esper.pattern.EvalNode;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.io.StringWriter;

public class TestEPAdministrator extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableTimerDebug(true);
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void test1kValidStmtsPerformance()
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            String text = "select * from " + SupportBean.class.getName();
            EPStatement stmt = epService.getEPAdministrator().createEPL(text, "s1");
            assertEquals("s1", stmt.getName());
            stmt.stop();
            stmt.start();
            stmt.stop();
            stmt.destroy();
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue(".test10kValid delta=" + delta, delta < 5000);
    }

    public void test1kInvalidStmts()
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            try
            {
                String text = "select xxx from " + SupportBean.class.getName();
                epService.getEPAdministrator().createEPL(text, "s1");
            }
            catch (Exception ex)
            {
                // expected
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue(".test1kInvalid delta=" + delta, delta < 2500);
    }

    public void testGetStmtByName()
    {
        String[] names = new String[] {"s1", "s2", "s3--0", "s3", "s3"};
        String[] expected = new String[] {"s1", "s2", "s3--0", "s3", "s3--1"};
        EPStatement[] stmts = createStmts(names);
        for (int i = 0; i < stmts.length; i++)
        {
            assertSame("failed for " + names[i], stmts[i], epService.getEPAdministrator().getStatement(expected[i]));
            assertEquals("failed for " + names[i], expected[i], epService.getEPAdministrator().getStatement(expected[i]).getName());
        }
    }

    public void testStatementArray()
    {
        assertEquals(0, epService.getEPAdministrator().getStatementNames().length);

        String[] names = new String[] {"s1"};
        EPStatement[] stmtsSetOne = createStmts(names);
        ArrayAssertionUtil.assertEqualsAnyOrder(names, epService.getEPAdministrator().getStatementNames());

        names = new String[] {"s1", "s2"};
        EPStatement[] stmtsSetTwo = createStmts(names);
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"s1", "s1--0", "s2"} , epService.getEPAdministrator().getStatementNames());
    }

    public void testCreateEPLByName()
    {
        String stmt = "select * from " + SupportBean.class.getName();
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmt, "s1");
        stmtOne.addListener(testListener);
        assertEquals("s1", stmtOne.getName());
        assertEquals(stmt, stmtOne.getText());

        // check working
        sendEvent();
        testListener.assertOneGetNewAndReset();

        // create a second with the same name
        stmt = "select intPrimitive from " + SupportBean.class.getName();
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmt, "s1");
        assertEquals("s1--0", stmtTwo.getName());
        assertEquals(stmt, stmtTwo.getText());

        // create a third invalid statement with the same name
        stmt = "select xxx from " + SupportBean.class.getName();
        try
        {
            epService.getEPAdministrator().createEPL(stmt, "s1");
            fail();
        }
        catch (RuntimeException ex)
        {
            // expected
        }

        // create a forth statement with the same name
        stmt = "select string from " + SupportBean.class.getName();
        EPStatement stmtFour = epService.getEPAdministrator().createEPL(stmt, "s1");
        assertEquals("s1--1", stmtFour.getName());
        assertEquals(stmt, stmtFour.getText());

        // create a fifth pattern statement with the same name
        stmt = SupportBean.class.getName();
        EPStatement stmtFive = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--2", stmtFive.getName());
        assertEquals(stmt, stmtFive.getText());

        // should allow null statement name
        epService.getEPAdministrator().createPattern(stmt, null);
    }

    public void testCreatePatternByName()
    {
        String stmt = SupportBean.class.getName();
        EPStatement stmtOne = epService.getEPAdministrator().createPattern(stmt, "s1");
        stmtOne.addListener(testListener);
        assertEquals("s1", stmtOne.getName());
        assertEquals(stmt, stmtOne.getText());

        // check working
        sendEvent();
        testListener.assertOneGetNewAndReset();

        // create a second with the same name
        stmt = SupportMarketDataBean.class.getName();
        EPStatement stmtTwo = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--0", stmtTwo.getName());
        assertEquals(stmt, stmtTwo.getText());

        // create a third invalid statement with the same name
        stmt = "xxx" + SupportBean.class.getName();
        try
        {
            epService.getEPAdministrator().createPattern(stmt, "s1");
            fail();
        }
        catch (RuntimeException ex)
        {
            // expected
        }

        // create a forth statement with the same name
        stmt = SupportBean.class.getName();
        EPStatement stmtFour = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--1", stmtFour.getName());
        assertEquals(stmt, stmtFour.getText());

        // create a fifth pattern statement with the same name
        stmt = "select * from " + SupportBean.class.getName();
        EPStatement stmtFive = epService.getEPAdministrator().createEPL(stmt, "s1");
        assertEquals("s1--2", stmtFive.getName());
        assertEquals(stmt, stmtFive.getText());

        // Null statement names should be allowed
        epService.getEPAdministrator().createPattern("every " + SupportBean.class.getName(), null);
        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testDestroyAll()
    {
        EPStatement[] stmts = createStmts(new String[] {"s1", "s2", "s3"});
        stmts[0].addListener(testListener);
        stmts[1].addListener(testListener);
        stmts[2].addListener(testListener);
        sendEvent();
        assertEquals(3, testListener.getNewDataList().size());
        testListener.reset();

        epService.getEPAdministrator().destroyAllStatements();
        assertDestroyed(stmts);
    }

    public void testStopStartAll()
    {
        EPStatement[] stmts = createStmts(new String[] {"s1", "s2", "s3"});
        stmts[0].addListener(testListener);
        stmts[1].addListener(testListener);
        stmts[2].addListener(testListener);

        assertStarted(stmts);

        epService.getEPAdministrator().stopAllStatements();
        assertStopped(stmts);

        epService.getEPAdministrator().startAllStatements();
        assertStarted(stmts);

        epService.getEPAdministrator().destroyAllStatements();
        assertDestroyed(stmts);
    }

    public void testStopStartSome()
    {
        EPStatement[] stmts = createStmts(new String[] {"s1", "s2", "s3"});
        stmts[0].addListener(testListener);
        stmts[1].addListener(testListener);
        stmts[2].addListener(testListener);
        assertStarted(stmts);

        stmts[0].stop();
        sendEvent();
        assertEquals(2, testListener.getNewDataList().size());
        testListener.reset();

        epService.getEPAdministrator().stopAllStatements();
        assertStopped(stmts);

        stmts[1].start();
        sendEvent();
        assertEquals(1, testListener.getNewDataList().size());
        testListener.reset();

        epService.getEPAdministrator().startAllStatements();
        assertStarted(stmts);

        epService.getEPAdministrator().destroyAllStatements();
        assertDestroyed(stmts);
    }

    public void testSPI() {
        EPAdministratorSPI spi = (EPAdministratorSPI) epService.getEPAdministrator();

        ExprDotNode funcnode = (ExprDotNode) spi.compileExpression("func()");
        assertFalse(funcnode.getChainSpec().get(0).isProperty());
        
        ExprNode node = spi.compileExpression("value=5 and /* comment */ true");
        assertEquals("(value = 5 AND true)", node.toExpressionString());

        Expression expr = spi.compileExpressionToSODA("value=5 and true");
        StringWriter buf = new StringWriter();
        expr.toEPL(buf, ExpressionPrecedenceEnum.MINIMUM);
        assertEquals("value = 5 and true", buf.toString());

        expr = spi.compileExpressionToSODA("5 sec");
        buf = new StringWriter();
        expr.toEPL(buf, ExpressionPrecedenceEnum.MINIMUM);
        assertEquals("5 seconds", buf.toString());

        EvalNode pattern = spi.compilePatternToNode("every A -> B");
        assertEquals(EvalFollowedByNode.class, pattern.getClass());

        PatternExpr patternExpr = spi.compilePatternToSODA("every A -> B");
        assertEquals(PatternFollowedByExpr.class, patternExpr.getClass());

        AnnotationPart part = spi.compileAnnotationToSODA("@somevalue(a='test', b=5)");
        assertEquals("somevalue", part.getName());
        assertEquals(2, part.getAttributes().size());
        assertEquals("a", part.getAttributes().get(0).getName());
        assertEquals("test", part.getAttributes().get(0).getValue());
        assertEquals("b", part.getAttributes().get(1).getName());
        assertEquals(5, part.getAttributes().get(1).getValue());

        MatchRecognizeRegEx regex = spi.compileMatchRecognizePatternToSODA("a b* c+ d? e?");
        assertEquals(5, regex.getChildren().size());

        // test fail cases
        String expected = "Incorrect syntax near 'goofy' at line 1 column 40 near reserved keyword 'in' [goofy in in]";
        String compiled = "goofy in in";
        try {
            spi.compileExpression(compiled);
            fail();
        }
        catch (EPException ex) {
            assertEquals(expected, ex.getMessage());
        }

        try {
            spi.compileExpressionToSODA(compiled);
            fail();
        }
        catch (EPException ex) {
            assertEquals(expected, ex.getMessage());
        }

        expected = "Incorrect syntax near 'goofy' ('in' is a reserved keyword) near reserved keyword 'in' [goofy in in]";
        try {
            spi.compilePatternToNode(compiled);
            fail();
        }
        catch (EPException ex) {
            assertEquals(expected, ex.getMessage());
        }

        try {
            spi.compilePatternToSODA(compiled);
            fail();
        }
        catch (EPException ex) {
            assertEquals(expected, ex.getMessage());
        }

        try {
            spi.compileAnnotationToSODA("not an annotation");
            fail();
        }
        catch (EPException ex) {
            assertEquals("Incorrect syntax near 'not' (a reserved keyword) [not an annotation]", ex.getMessage());
        }

        try {
            spi.compileMatchRecognizePatternToSODA("a b???");
            fail();
        }
        catch (EPException ex) {
            assertEquals("Incorrect syntax near '?' expecting a closing parenthesis ')' but found a questionmark '?' at line 1 column 79 [a b???]", ex.getMessage());
        }

        StatementSpecRaw raw = spi.compileEPLToRaw("select * from java.lang.Object");
        assertNotNull(raw);
        EPStatementObjectModel model = spi.mapRawToSODA(raw);
        assertNotNull(model);
    }

    private void assertStopped(EPStatement[] stmts)
    {
        for (int i = 0; i < stmts.length; i++)
        {
            assertEquals(EPStatementState.STOPPED, stmts[i].getState());
        }
        sendEvent();
        assertEquals(0, testListener.getNewDataList().size());
        testListener.reset();
    }

    private void assertStarted(EPStatement[] stmts)
    {
        for (int i = 0; i < stmts.length; i++)
        {
            assertEquals(EPStatementState.STARTED, stmts[i].getState());
        }
        sendEvent();
        assertEquals(stmts.length, testListener.getNewDataList().size());
        testListener.reset();
    }

    private void assertDestroyed(EPStatement[] stmts)
    {
        for (int i = 0; i < stmts.length; i++)
        {
            assertEquals(EPStatementState.DESTROYED, stmts[i].getState());
        }
        sendEvent();
        assertEquals(0, testListener.getNewDataList().size());
        testListener.reset();
    }

    private EPStatement[] createStmts(String[] statementNames)
    {
        EPStatement statements[] = new EPStatement[statementNames.length];
        for (int i = 0; i < statementNames.length; i++)
        {
            statements[i] = epService.getEPAdministrator().createEPL("select * from " + SupportBean.class.getName(), statementNames[i]);
        }
        return statements;
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
    }
}
