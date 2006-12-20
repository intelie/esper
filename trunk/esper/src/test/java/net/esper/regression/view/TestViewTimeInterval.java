package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestViewTimeInterval extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        // External clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testTimeWindow()
    {
        tryTimeWindow("30000");
        tryTimeWindow("30E6 milliseconds");
        tryTimeWindow("30000 seconds");
        tryTimeWindow("500 minutes");
        tryTimeWindow("8.33333333333333333333 hours");
        tryTimeWindow("0.34722222222222222222222222222222 days");
        tryTimeWindow("0.1 hour 490 min 240 sec");
    }

    public void testTimeBatchNoRefPoint()
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() +
                ".win:time_batch(10 minutes)");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendTimer(0);

        sendEvent();
        testListener.reset();

        sendTimerAssertNotInvoked(10*60*1000 - 1);
        sendTimerAssertInvoked(10*60*1000);
    }

    public void testTimeBatchRefPoint()
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() +
                ".win:time_batch(10 minutes, 10L)");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendTimer(10);

        sendEvent();
        testListener.reset();

        sendTimerAssertNotInvoked(10*60*1000 - 1 + 10);
        sendTimerAssertInvoked(10*60*1000 + 10);
    }

    public void testExternallyTimed()
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() +
                ".win:ext_timed('longPrimitive', 10 minutes)");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendExtTimeEvent(0);

        testListener.reset();
        sendExtTimeEvent(10*60*1000-1);
        assertNull(testListener.getOldDataList().get(0));

        testListener.reset();
        sendExtTimeEvent(10*60*1000+1);
        assertEquals(1, testListener.getOldDataList().get(0).length);
    }

    private void tryTimeWindow(String intervalSpec)
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEQL(
                "select * from " + SupportBean.class.getName() +
                ".win:time(" + intervalSpec + ")");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendTimer(0);

        sendEvent();
        testListener.reset();

        sendTimerAssertNotInvoked(29999*1000);
        sendTimerAssertInvoked(30000*1000);
    }

    private void sendTimerAssertNotInvoked(long timeInMSec)
    {
        sendTimer(timeInMSec);
        assertFalse(testListener.isInvoked());
        testListener.reset();
    }

    private void sendTimerAssertInvoked(long timeInMSec)
    {
        sendTimer(timeInMSec);
        assertTrue(testListener.isInvoked());
        testListener.reset();
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent()
    {
        SupportBean event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendExtTimeEvent(long longPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }
}
