package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;

import java.util.Set;

/**
 * Interface for populating a join tuple result set from new data and old data for each stream.
 */
public interface JoinSetComposer
{
    /**
     * Return join tuple result set from new data and old data for each stream.
     * @param newDataPerStream - for each stream the event array (can be null).
     * @param oldDataPerStream - for each stream the event array (can be null).
     * @return join tuples
     */
    public UniformPair<Set<MultiKey<EventBean>>> join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream);
}
