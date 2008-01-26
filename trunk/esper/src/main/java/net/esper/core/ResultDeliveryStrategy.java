package net.esper.core;

import net.esper.event.EventBean;
import net.esper.collection.Pair;

/**
 * Strategy for use with {@link StatementResultService} to dispatch to a statement's subscriber
 * via method invocations.
 */
public interface ResultDeliveryStrategy
{
    /**
     * Execute the dispatch.
     * @param result is the insert and remove stream to indicate
     */
    public void execute(Pair<EventBean[], EventBean[]> result);
}
