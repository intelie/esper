package net.esper.eql.join.exec;

import net.esper.event.EventBean;

import java.util.Set;

/**
 * Strategy for looking up, in some sort of table or index, an event, potentially based on the
 * events properties, and returning a set of matched events.
 */
public interface TableLookupStrategy
{
    /**
     * Returns matched events for a event to look up for. Never returns an empty result set,
     * always returns null to indicate no results.
     * @param event to look up
     * @return set of matching events, or null if none matching
     */
    public Set<EventBean> lookup(EventBean event);
}
