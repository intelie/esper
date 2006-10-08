package net.esper.pattern.guard;

import net.esper.pattern.PatternContext;
import net.esper.eql.parse.IntervalParameter;

/**
 * Factory for {@link TimerWithinGuard} instances.
 */
public class TimerWithinGuardFactory implements GuardFactory
{
    private final int msec;

    /**
     * Creates a timer guard.
     * @param msec number of milliseconds before guard expiration
     */
    public TimerWithinGuardFactory(int msec)
    {
        this.msec = msec;
    }

    /**
     * Creates a timer guard.
     * @param intervalParameter number of milliseconds before guard expiration
     */
    public TimerWithinGuardFactory(IntervalParameter intervalParameter)
    {
        this.msec = (int) intervalParameter.getNumSeconds() * 1000;
    }

    public Guard makeGuard(PatternContext context, Quitable quitable)
    {
        return new TimerWithinGuard(msec, context, quitable);
    }
}
