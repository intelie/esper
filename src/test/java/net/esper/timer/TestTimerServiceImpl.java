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

        sleep(TimerServiceImpl.INTERNAL_CLOCK_RESOLUTION_MSEC * 20);

        int count = callback.getAndResetCount();

        log.debug(".testClocking count=" + count);

        assertTrue(count >= 19);

        // Stop and check again

        service.stopInternalClock(true);

        sleep(RESOLUTION);

        assertTrue(callback.getCount() <= 1);

        // Try some starts and stops to see

        service.startInternalClock();

        sleep(RESOLUTION / 5);

        service.startInternalClock();

        sleep(RESOLUTION / 5);

        service.startInternalClock();

        assertTrue(callback.getAndResetCount() >= 1);


        sleep(RESOLUTION / 5);

        assertEquals(0, callback.getCount());

        sleep(RESOLUTION);

        assertTrue(callback.getCount() >= 1);

        sleep(RESOLUTION);

        assertTrue(callback.getCount() >= 1);


        sleep(RESOLUTION * 5);

        assertTrue(callback.getAndResetCount() >= 7);


        service.stopInternalClock(true);

        callback.getAndResetCount();

        service.stopInternalClock(true);

        sleep(RESOLUTION * 2);

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