package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.SupportStmtAwareUpdateListener;
import com.espertech.esper.support.util.SupportSubscriber;

public class TestEPStatement extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testListenerWithReplay()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:length(2)");

        // test empty statement
        stmt.addListenerWithReplay(listener);
        assertTrue(listener.isInvoked());
        assertEquals(1, listener.getNewDataList().size());
        assertNull(listener.getNewDataList().get(0));
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals("E1", listener.assertOneGetNewAndReset().get("string"));
        stmt.destroy();
        listener.reset();

        // test 1 event
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:length(2)");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        stmt.addListenerWithReplay(listener);
        assertEquals("E1", listener.assertOneGetNewAndReset().get("string"));
        stmt.destroy();
        listener.reset();

        // test 2 events
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:length(2)");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        stmt.addListenerWithReplay(listener);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"string"}, new Object[][] {{"E1"}, {"E2"}});

        // test stopped statement and destroyed statement
        listener.reset();
        stmt.stop();
        stmt.removeAllListeners();
        
        stmt.addListenerWithReplay(listener);
        assertTrue(listener.isInvoked());
        assertEquals(1, listener.getNewDataList().size());
        assertNull(listener.getNewDataList().get(0));
        listener.reset();

        // test destroyed
        listener.reset();
        stmt.destroy();
        try
        {
            stmt.addListenerWithReplay(listener);
            fail();
        }
        catch (IllegalStateException ex)
        {
            //
        }

        stmt.removeAllListeners();
        stmt.removeListener(listener);
        stmt.removeListener(new SupportStmtAwareUpdateListener());
        stmt.setSubscriber(new SupportSubscriber());

        stmt.getAnnotations();
        stmt.getState();
        stmt.getSubscriber();

        try
        {
            stmt.addListener(listener);
            fail();
        }
        catch (IllegalStateException ex)
        {
            //
        }
        try
        {
            stmt.addListener(new SupportStmtAwareUpdateListener());
            fail();
        }
        catch (IllegalStateException ex)
        {
            //
        }
    }

    public void testStartedDestroy()
    {
        sendTimer(1000);

        String text = "select * from " + SupportBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(text, "s1");
        assertEquals(1000l, stmt.getTimeLastStateChange());
        assertEquals(false, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(true, stmt.isStarted());

        stmt.addListener(listener);
        sendEvent();
        listener.assertOneGetNewAndReset();

        sendTimer(2000);
        stmt.destroy();
        assertEquals(2000l, stmt.getTimeLastStateChange());
        assertEquals(true, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(false, stmt.isStarted());

        sendEvent();
        assertFalse(listener.isInvoked());

        assertStmtDestroyed(stmt, text);
    }

    public void testStopDestroy()
    {
        sendTimer(5000);
        String text = "select * from " + SupportBean.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(text, "s1");
        assertEquals(false, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(true, stmt.isStarted());
        assertEquals(5000l, stmt.getTimeLastStateChange());
        stmt.addListener(listener);
        sendEvent();
        listener.assertOneGetNewAndReset();

        sendTimer(6000);
        stmt.stop();
        assertEquals(6000l, stmt.getTimeLastStateChange());
        assertEquals(false, stmt.isDestroyed());
        assertEquals(true, stmt.isStopped());
        assertEquals(false, stmt.isStarted());

        sendEvent();
        assertFalse(listener.isInvoked());

        sendTimer(7000);
        stmt.destroy();
        assertEquals(true, stmt.isDestroyed());
        assertEquals(false, stmt.isStopped());
        assertEquals(false, stmt.isStarted());
        assertEquals(7000l, stmt.getTimeLastStateChange());
        sendEvent();
        assertFalse(listener.isInvoked());

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
