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

package com.espertech.esper.timer;

import junit.framework.*;
import com.espertech.esper.support.timer.SupportTimerCallback;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestTimerServiceImpl extends TestCase
{
    private SupportTimerCallback callback;
    private TimerServiceImpl service;

    public void setUp()
    {
        callback = new SupportTimerCallback();
        service = new TimerServiceImpl(null, 100);
        service.setCallback(callback);
    }

    public void testClocking()
    {
        final int RESOLUTION = (int) service.getMsecTimerResolution();

        // Wait .55 sec
        assertTrue(callback.getAndResetCount() == 0);
        service.startInternalClock();
        sleep(RESOLUTION * 5 + RESOLUTION / 2);
        service.stopInternalClock(true);
        assertEquals(6, callback.getAndResetCount());

        // Check if truely stopped
        sleep(RESOLUTION);
        assertTrue(callback.getAndResetCount() == 0);

        // Loop for some clock cycles
        service.startInternalClock();
        sleep(RESOLUTION / 10);
        assertTrue(callback.getAndResetCount() == 1);
        sleep(service.getMsecTimerResolution() * 20);
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