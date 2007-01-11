package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;

import java.util.Set;

/**
 * Encapsulates the strategy use to resolve the events for a stream into a tuples of events in a join.
 */
public interface QueryStrategy
{
    /**
     * Look up events returning tuples of joined events.
     * @param lookupEvents - events to use to perform the join
     * @param joinSet - result join tuples of events 
     */
    public void lookup(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet);
}
