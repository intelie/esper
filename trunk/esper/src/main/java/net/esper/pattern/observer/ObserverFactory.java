package net.esper.pattern.observer;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;

/**
 * Interface for factories for making observer instances.
 */
public interface ObserverFactory
{
    /**
     * Make an observer instance.
     * @param context - services that may be required by observer implementation
     * @param beginState - start state for observer
     * @param observerEventEvaluator - receiver for events observed
     * @return observer instance
     */
    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator);
}
