package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;

public class TestEPAdministrator extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
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

    public void testCreateEQLByName()
    {
        String stmt = "select * from " + SupportBean.class.getName();
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmt, "s1");
        stmtOne.addListener(testListener);
        assertEquals("s1", stmtOne.getName());
        assertEquals(stmt, stmtOne.getText());

        // check working
        sendEvent();
        testListener.assertOneGetNewAndReset();

        // create a second with the same name
        stmt = "select intPrimitive from " + SupportBean.class.getName();
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmt, "s1");
        assertEquals("s1--0", stmtTwo.getName());
        assertEquals(stmt, stmtTwo.getText());

        // create a third invalid statement with the same name
        stmt = "select xxx from " + SupportBean.class.getName();
        try
        {
            epService.getEPAdministrator().createEQL(stmt, "s1");
            fail();
        }
        catch (RuntimeException ex)
        {
            // expected
        }

        // create a forth statement with the same name
        stmt = "select string from " + SupportBean.class.getName();
        EPStatement stmtFour = epService.getEPAdministrator().createEQL(stmt, "s1");
        assertEquals("s1--1", stmtFour.getName());
        assertEquals(stmt, stmtFour.getText());

        // create a fifth pattern statement with the same name
        stmt = SupportBean.class.getName();
        EPStatement stmtFive = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--2", stmtFive.getName());
        assertEquals(stmt, stmtFive.getText());

        try
        {
            epService.getEPAdministrator().createPattern(stmt, null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
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
        EPStatement stmtFive = epService.getEPAdministrator().createEQL(stmt, "s1");
        assertEquals("s1--2", stmtFive.getName());
        assertEquals(stmt, stmtFive.getText());

        try
        {
            epService.getEPAdministrator().createPattern(stmt, null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    private EPStatement[] createStmts(String[] statementNames)
    {
        EPStatement statements[] = new EPStatement[statementNames.length];
        for (int i = 0; i < statementNames.length; i++)
        {
            statements[i] = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), statementNames[i]);
        }
        return statements;
    }

    private EPStatement createStmt(String statementName)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), statementName);
        return stmt;
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
    }
}
