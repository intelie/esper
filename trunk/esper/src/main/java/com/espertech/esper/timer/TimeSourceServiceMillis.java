package com.espertech.esper.timer;

/**
 * Gets system (wall clock) time using System.currentTimeMillis.
 * @see System#currentTimeMillis()
 * @author Jerry Shea
 */
public class TimeSourceServiceMillis extends TimeSourceService
{

	public long getTimeMicros() {
		return System.currentTimeMillis() * MICROS_TO_MILLIS;
	}
}
