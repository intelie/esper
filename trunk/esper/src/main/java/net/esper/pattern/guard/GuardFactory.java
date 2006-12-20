package net.esper.pattern.guard;

import net.esper.pattern.PatternContext;

/**
 * Interface for a factory for {@link Guard} instances.
 */
public interface GuardFactory
{
    /**
     * Constructs a guard instance.
     * @param context - services for use by guard
     * @param quitable - to use for indicating the guard has quit
     * @return guard instance
     */
    public Guard makeGuard(PatternContext context, Quitable quitable);
}
