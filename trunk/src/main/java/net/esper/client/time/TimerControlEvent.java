package net.esper.client.time;

/**
 * Event for controlling clocking, i.e. to enable and disable external clocking.
 */
public final class TimerControlEvent extends TimerEvent
{
    /**
     * Constants controlling the clocking.
     */
    public enum ClockType
    {
        /**
         * For external clocking.
         */
        CLOCK_EXTERNAL,
        /**
         * For internal clocking.
         */
        CLOCK_INTERNAL;
    }

    private final ClockType clockType;

    /**
     * Constructor takes a clocking type as parameter.
     * @param clockType for internal or external clocking
     */
    public TimerControlEvent(final ClockType clockType)
    {
        this.clockType = clockType;
    }

    /**
     * Returns clocking type.
     * @return clocking type
     */
    public ClockType getClockType()
    {
        return clockType;
    }
}
