package net.esper.schedule;

/**
 * Static factory for implementations of the SchedulingService interface.
 */
public final class SchedulingServiceProvider
{
    /**
     * Creates an implementation of the SchedulingService interface.
     * @return implementation
     */
    public static SchedulingService newService()
    {
        return new SchedulingServiceImpl();
    }
}
