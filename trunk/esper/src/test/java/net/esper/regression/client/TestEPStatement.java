package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementState;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;

public class TestEPStatement extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testStartedDestroy()
    {
        String text = "select * from " + SupportBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEQL(text, "s1");
        stmt.addListener(testListener);
        sendEvent();
        testListener.assertOneGetNewAndReset();

        stmt.destroy();
        sendEvent();
        assertFalse(testListener.isInvoked());

        assertStmtDestroyed(stmt, text);
    }

    public void testStopDestroy()
    {
        String text = "select * from " + SupportBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEQL(text, "s1");
        stmt.addListener(testListener);
        sendEvent();
        testListener.assertOneGetNewAndReset();

        stmt.stop();
        sendEvent();
        assertFalse(testListener.isInvoked());

        stmt.destroy();
        sendEvent();
        assertFalse(testListener.isInvoked());

        assertStmtDestroyed(stmt, text);
    }

    private void assertStmtDestroyed(EPStatement stmt, String text)
    {
        assertEquals(EPStatementState.DESTROYED, stmt.getState());
        assertEquals(text, stmt.getText());
        assertEquals("s1", stmt.getName());
        assertNull(epService.getEPAdministrator().getStatement("s1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[0], epService.getEPAdministrator().getStatementNames());

        try
        {
            stmt.destroy();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
            assertEquals("Statement already destroyed", ex.getMessage());
        }

        try
        {
            stmt.start();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
            assertEquals("Cannot start statement, statement is in destroyed state", ex.getMessage());
        }

        try
        {
            stmt.stop();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
            assertEquals("Cannot stop statement, statement is in destroyed state", ex.getMessage());
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
