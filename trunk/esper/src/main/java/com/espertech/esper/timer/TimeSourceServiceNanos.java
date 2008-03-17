package com.espertech.esper.timer;


/**
 * Gets system (wall clock) time using System.nanoTime.
 * @see System#nanoTime()
 * @author Jerry Shea
 */
public class TimeSourceServiceNanos extends TimeSourceService
{

	private long wallClockOffset = 0;

	public TimeSourceServiceNanos() {
		super();
		// calculate approximate offset by referring to TimeSourceMillis which
		// is already normalised to wall clock time
		this.wallClockOffset = new TimeSourceServiceMillis().getTimeMicros() - this.getTimeMicros();
	}

	public long getTimeMicros() {
		return (System.nanoTime() / NANOS_TO_MICROS) + wallClockOffset;
	}
}
