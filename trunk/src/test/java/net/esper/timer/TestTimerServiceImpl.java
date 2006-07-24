package net.esper.timer;

import junit.framework.*;
import net.esper.support.timer.SupportTimerCallback;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestTimerServiceImpl extends TestCase
{
    private SupportTimerCallback callback;
    private TimerServiceImpl service;

    public void setUp()
    {
        callback = new SupportTimerCallback();
        service = new TimerServiceImpl();
        service.setCallback(callback);
    }

    public void testClocking()
    {
        final int RESOLUTION = TimerServiceImpl.INTERNAL_CLOCK_RESOLUTION_MSEC;

        // Wait .55 sec
        assertTrue(callback.getAndResetCount() == 0);
        service.startInternalClock();
        sleep(RESOLUTION * 5 + RESOLUTION / 2);
        service.stopInternalClock(true);
        assertTrue(callback.getAndResetCount() == 6);

        // Check if truely stopped
        sleep(RESOLUTION);
        assertTrue(callback.getAndResetCount() == 0);

        // Loop for some clock cycles
        service.startInternalClock();
        sleep(RESOLUTION / 10);
        assertTrue(callback.getAndResetCount() == 1);
        for (int i = 0; i < 20; i++)
        {
            log.debug(".testClocking i=" + i + " ...sleeping " + TimerServiceImpl.INTERNAL_CLOCK_RESOLUTION_MSEC);
            sleep(TimerServiceImpl.INTERNAL_CLOCK_RESOLUTION_MSEC);
            assertTrue(callback.getAndResetCount() >= 1);
        }

        // Stop and check again
        service.stopInternalClock(true);
        sleep(RESOLUTION);
        assertTrue(callback.getCount() == 0);

        // Try some starts and stops to see
        service.startInternalClock();
        sleep(RESOLUTION / 5);
        service.startInternalClock();
        sleep(RESOLUTION / 5);
        service.startInternalClock();
        assertTrue(callback.getAndResetCount() == 1);

        sleep(RESOLUTION / 5);
        assertTrue(callback.getCount() == 0);
        sleep(RESOLUTION);
        assertTrue(callback.getCount() == 1);
        sleep(RESOLUTION);
        assertTrue(callback.getCount() == 2);

        sleep(RESOLUTION * 10);
        assertTrue(callback.getAndResetCount() == 12);

        service.stopInternalClock(true);
        service.stopInternalClock(true);
        assertTrue(callback.getCount() == 0);
    }

    private void sleep(long msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
            log.fatal(e);
        }
    }

    private static final Log log = LogFactory.getLog(TestTimerServiceImpl.class);
}