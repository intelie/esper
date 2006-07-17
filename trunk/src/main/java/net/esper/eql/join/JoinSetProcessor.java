package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import java.util.Set;

/**
 * Processes a join result set constisting of sets of tuples of events.
 */
public interface JoinSetProcessor
{
    /**
     * Process join result set.
     * @param newEvents - set of event tuples representing new data
     * @param oldEvents - set of event tuples representing old data
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents);
}
