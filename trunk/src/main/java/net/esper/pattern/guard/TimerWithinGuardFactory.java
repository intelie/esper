package net.esper.pattern.guard;

import net.esper.pattern.PatternContext;

/**
 * Factory for {@link TimerWithinGuard} instances.
 */
public class TimerWithinGuardFactory implements GuardFactory
{
    private final int msec;

    /**
     * Number of milliseconds before guard expiration.
     * @param msec number of millis
     */
    public TimerWithinGuardFactory(int msec)
    {
        this.msec = msec;
    }

    public Guard makeGuard(PatternContext context, Quitable quitable)
    {
        return new TimerWithinGuard(msec, context, quitable);
    }
}
