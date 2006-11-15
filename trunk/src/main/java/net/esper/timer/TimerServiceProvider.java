package net.esper.timer;

/**
 * Static factory for implementations of the TimerService interface.
 */
public final class TimerServiceProvider
{
    /**
     * Creates an implementation of the TimerService interface.
     * @return implementation
     */
    public static TimerService newService()
    {
        return new TimerServiceImpl();
    }
}
