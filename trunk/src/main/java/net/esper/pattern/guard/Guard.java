package net.esper.pattern.guard;

import net.esper.pattern.MatchedEventMap;

/**
 * Guard instances inspect a matched events and makes a determination on whether to let it pass or not.
 */
public interface Guard
{
    /**
     * Start the guard operation.
     */
    public void startGuard();

    /**
     * Called when sub-expression quits, or when the pattern stopped.
     */
    public void stopGuard();

    /**
     * Returns true if inspection shows that the match events can pass, or false to not pass.
     * @param matchEvent is the map of matching events
     * @return true to pass, false to not pass
     */
    public boolean inspect(MatchedEventMap matchEvent);
}
