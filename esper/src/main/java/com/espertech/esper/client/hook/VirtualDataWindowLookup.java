package com.espertech.esper.client.hook;

import com.espertech.esper.client.EventBean;

import java.util.Set;

/**
 * Represents a lookup strategy object that an EPL statement that queries a virtual data window obtains
 * to perform read operations into the virtual data window.
 * <p>
 * An instance is associated to each EPL statement querying (join, subquery, on-action etc.) the virtual data window.
 */
public interface VirtualDataWindowLookup {

    /**
     * Invoked by an EPL statement that queries a virtual data window to perform a lookup.
     * <p>
     * Keys passed are the actual query lookup values.
     * @param keys lookup values
     * @return set of events
     */
    public Set<EventBean> lookup(Object[] keys, EventBean[] eventsPerStream);
}
