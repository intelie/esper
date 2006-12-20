package net.esper.client.time;

import java.util.Date;

/**
 * Event for externally controlling the time within an {@link net.esper.client.EPRuntime} instance.
 * External clocking must be enabled via {@link TimerControlEvent} before this class can be used
 * to externally feed time.
 */
public final class CurrentTimeEvent extends TimerEvent
{
    private final long timeInMillis;

    /**
     * Constructor.
     * @param timeInMillis is the time in milliseconds
     */
    public CurrentTimeEvent(final long timeInMillis)
    {
        this.timeInMillis = timeInMillis;
    }

    public String toString()
    {
        return (new Date(timeInMillis)).toString();
    }

    /**
     * Returns the time in milliseconds.
     * @return time in milliseconds
     */
    public long getTimeInMillis()
    {
        return timeInMillis;
    }
}
