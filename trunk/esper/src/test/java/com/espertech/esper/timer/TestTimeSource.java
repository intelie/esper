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
    public void testWallClock() throws InterruptedException
    {
    	// allow a tolerance as TimeSourceMillis resolution may be around 16ms
        final long TOLERANCE_MILLISECS = 20, DELAY_MILLISECS = 100;

        TimeSourceService nanos = new TimeSourceServiceNanos();
        TimeSourceService millis = new TimeSourceServiceMillis();

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
		long nanosWallClockTime = nanos.getTimeMillis();
        long millisWallClockTime = millis.getTimeMillis();

        long diff = nanosWallClockTime - millisWallClockTime;
        log.info("diff="+diff+" between "+nanos+" and "+millis);
        assertTrue("Diff "+diff+" >= "+TOLERANCE_MILLISECS, Math.abs(diff) < TOLERANCE_MILLISECS);
	}

    private static final Log log = LogFactory.getLog(TestTimeSource.class);
}
