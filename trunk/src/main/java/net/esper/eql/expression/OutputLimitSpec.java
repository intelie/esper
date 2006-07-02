package net.esper.eql.expression;

/**
 * Spec for building an EventBatch.
 *
 */
public class OutputLimitSpec {

	boolean isEventLimit;
	boolean displayLastOnly;
	
	int eventRate;
	double timeRate;

	/**
	 * Ctor.
	 * 	 For batching events by event count.
	 * @param eventRate - the number of events to batch.
	 * @param displayLastOnly indicates whether the output should be all or only the last arriving event.
	 */
	public OutputLimitSpec(int eventRate, boolean displayLastOnly)
	{
		this.isEventLimit = true;
		this.eventRate = eventRate;
		this.displayLastOnly = displayLastOnly;
	}
	
	/**
	 * Ctor.
	 * Used for creating batching events by time.
	 * @param timeRate - the number of seconds to batch for.
	 * @param displayLastOnly indicates whether the output should be all or only the last arriving event.
	 */
	public OutputLimitSpec(double timeRate, boolean displayLastOnly)
	{
		this.isEventLimit = false;
		this.timeRate = timeRate;
		this.displayLastOnly = displayLastOnly;
	}

    /**
     * Returns the event rate.
     * @return event rate
     */
	public int getEventRate() {
		return eventRate;
	}

    /**
     * Returns the number of events, or zero if no number of events was supplied.
     * @return event limit
     */
	public boolean isEventLimit() {
		return isEventLimit;
	}

    /**
     * Returns the rate in seconds, if supplied, or zero if not supplied.
     * @return rate
     */
	public double getTimeRate() {
		return timeRate;
	}

    /**
     * Returns true to output the last event only, or false to output all events.
     * @return true if last only, false for all events
     */
	public boolean isDisplayLastOnly() {
		return displayLastOnly;
	}

}
