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

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Ensure that TimeSourceMills and TimeSourceMills
 * agree on wall clock time.
 * @author Jerry Shea
 */
public class TestTimeSource extends TestCase
{
    public void tearDown()
    {
        TimeSourceServiceImpl.IS_SYSTEM_CURRENT_TIME = true;
    }

    public void testWallClock() throws InterruptedException
    {
        // allow a tolerance as TimeSourceMillis resolution may be around 16ms
        final long TOLERANCE_MILLISECS = 50, DELAY_MILLISECS = 100;

        // This is a smoke test
        TimeSourceService nanos = new TimeSourceServiceImpl();
        TimeSourceService millis = new TimeSourceServiceImpl();

        assertTimeWithinTolerance(TOLERANCE_MILLISECS, nanos, millis);
        Thread.sleep(DELAY_MILLISECS);
        assertTimeWithinTolerance(TOLERANCE_MILLISECS, nanos, millis);
        Thread.sleep(DELAY_MILLISECS);
        assertTimeWithinTolerance(TOLERANCE_MILLISECS, nanos, millis);
        Thread.sleep(DELAY_MILLISECS);
        assertTimeWithinTolerance(TOLERANCE_MILLISECS, nanos, millis);
    }

	private void assertTimeWithinTolerance(final long TOLERANCE_MILLISECS,
			TimeSourceService nanos, TimeSourceService millis) {

        TimeSourceServiceImpl.IS_SYSTEM_CURRENT_TIME = true;
		long nanosWallClockTime = nanos.getTimeMillis();

        TimeSourceServiceImpl.IS_SYSTEM_CURRENT_TIME = false;
        long millisWallClockTime = millis.getTimeMillis();

        long diff = nanosWallClockTime - millisWallClockTime;
        log.info("diff="+diff+" between "+nanos+" and "+millis);
        assertTrue("Diff "+diff+" >= "+TOLERANCE_MILLISECS, Math.abs(diff) < TOLERANCE_MILLISECS);
	}

    private static final Log log = LogFactory.getLog(TestTimeSource.class);
}
