package net.esper.pattern.observer;

import net.esper.pattern.*;

/**
 * For use by {@link EventObserver} instances to place an event for processing/evaluation.
 */
public interface ObserverEventEvaluator
{
    /**
     * Indicate an event for evaluation (sub-expression the observer represents has turned true).
     * @param matchEvent is the matched events so far
     */
    public void observerEvaluateTrue(MatchedEventMap matchEvent);

    /**
     * Indicate that the observer turned permanently false.
     */
    public void observerEvaluateFalse();
}
