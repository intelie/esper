package com.espertech.esper.timer;

/**
 * Allow for different strategies for getting VM (wall clock) time.
 * See JIRA issue ESPER-191 Support nano/microsecond resolution for more
 * information on Java system time-call performance, accuracy and drift.
 * @author Jerry Shea
 */
public abstract class TimeSourceService
{
	public static final long MICROS_TO_MILLIS = 1000;
	public static final long NANOS_TO_MICROS = 1000;

	private String description;

	/**
	 * Get VM (wall clock) time.
	 * @see System#currentTimeMillis()
	 * @return The number of microseconds since Jan 1 1970 UTC
	 */
	public abstract long getTimeMicros();

	/**
	 * Convenience method to get time in milliseconds
	 * @return wall-clock time in milliseconds
	 */
	public long getTimeMillis() {
		return getTimeMicros() / MICROS_TO_MILLIS;
	}

	public TimeSourceService() {
		this.description = String.format("%s: resolution %d microsecs",
						   this.getClass().getSimpleName(), this.calculateResolution());
	}

	/**
	 * Calculate resolution of this timer in microseconds i.e. what is the resolution
	 * of the underlying platform's timer.
	 * @return timer resolution
	 */
	protected long calculateResolution() {
		final int LOOPS = 5;
        long totalResolution = 0;
		long time = this.getTimeMicros(), prevTime = time;
        for (int i = 0; i < LOOPS; i++) {
            // wait until time changes
            while (time == prevTime)
                time = this.getTimeMicros();
            totalResolution += (time - prevTime);
			prevTime = time;
        }
		return totalResolution / LOOPS;
	}

	@Override
	public String toString() {
		return description;
	}
}
