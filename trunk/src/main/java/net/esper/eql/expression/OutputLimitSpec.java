package net.esper.eql.expression;

/**
 * Spec for building an EventBatch.
 *
 */
public class OutputLimitSpec 
{
	public enum DisplayLimit { FIRST, LAST, ALL };
	
	private final boolean isEventLimit;
	private final DisplayLimit displayLimit;
	
	private final int eventRate;
	private final double timeRate;

	/**
	 * Ctor.
	 * 	 For batching events by event count.
	 * @param eventRate - the number of events to batch.
	 * @param displayLimit - indicates whether to output only the first, only the last, or all events
	 */
	public OutputLimitSpec(int eventRate, DisplayLimit displayLimit)
	{
		this.isEventLimit = true;
		this.eventRate = eventRate;
		this.timeRate = -1.0;
		this.displayLimit = displayLimit;
	}
	
	/**
	 * Ctor.
	 * Used for creating batching events by time.
	 * @param timeRate - the number of seconds to batch for.
	 * @param displayLimit - indicates whether to output only the first, only the last, or all events
	 */
	public OutputLimitSpec(double timeRate, DisplayLimit displayLimit)
	{
		this.isEventLimit = false;
		this.timeRate = timeRate;
		this.eventRate = -1;
		this.displayLimit = displayLimit;
	}

    /**
     * Returns the event rate.
     * @return event rate
     */
	public int getEventRate() 
	{
		return eventRate;
	}

    /**
     * Returns the number of events, or zero if no number of events was supplied.
     * @return event limit
     */
	public boolean isEventLimit() 
	{
		return isEventLimit;
	}

    /**
     * Returns the rate in seconds, if supplied, or zero if not supplied.
     * @return rate
     */
	public double getTimeRate() 
	{
		return timeRate;
	}

    /**
     * Returns true to output the last event only.
     * @return true if last only, false otherwise
     */
	public boolean isDisplayLastOnly() 
	{
		return displayLimit == DisplayLimit.LAST;
	}
	
    /**
     * Returns true to output the first event only.
     * @return true if first only, false otherwise
     */
	public boolean isDisplayFirstOnly() 
	{
		return displayLimit == DisplayLimit.FIRST;
	}

}
