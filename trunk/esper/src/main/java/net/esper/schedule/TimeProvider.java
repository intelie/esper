package net.esper.schedule;

/**
 * Provider of internal system time.
 * <p>
 * Internal system time is controlled either by a timer function or by external time events.
 */
public interface TimeProvider
{
    /**
     * Returns the current engine time.
     * @return time that has last been set
     */
    public long getTime();
}
