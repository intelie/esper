package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestEPStatement extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testStartedDestroy()
    {
        sendTimer(1000);

        String text = "select * from " + SupportBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEQL(text, "s1");
        assertEquals(1000l, stmt.getTimeLastStateChange());
        assertEquals(false, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(true, stmt.isStarted());

        stmt.addListener(testListener);
        sendEvent();
        testListener.assertOneGetNewAndReset();

        sendTimer(2000);
        stmt.destroy();
        assertEquals(2000l, stmt.getTimeLastStateChange());
        assertEquals(true, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(false, stmt.isStarted());

        sendEvent();
        assertFalse(testListener.isInvoked());

        assertStmtDestroyed(stmt, text);
    }

    public void testStopDestroy()
    {
        sendTimer(5000);
        String text = "select * from " + SupportBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEQL(text, "s1");
        assertEquals(false, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(true, stmt.isStarted());
        assertEquals(5000l, stmt.getTimeLastStateChange());
        stmt.addListener(testListener);
        sendEvent();
        testListener.assertOneGetNewAndReset();

        sendTimer(6000);
        stmt.stop();
        assertEquals(6000l, stmt.getTimeLastStateChange());
        assertEquals(false, stmt.isDestroyed());
        assertEquals(true, stmt.isStopped());
        assertEquals(false, stmt.isStarted());

        sendEvent();
        assertFalse(testListener.isInvoked());

        sendTimer(7000);
        stmt.destroy();
        assertEquals(true, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(false, stmt.isStarted());
        assertEquals(7000l, stmt.getTimeLastStateChange());
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
    
    private void sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }    
}
