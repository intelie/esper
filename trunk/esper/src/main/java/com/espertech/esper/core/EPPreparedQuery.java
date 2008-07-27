package com.espertech.esper.core;

import com.espertech.esper.event.EventType;

/**
 * Interface for a prepared query that can be executed multiple times.
 */
public interface EPPreparedQuery
{
    /**
     * Execute the prepared query returning query results.
     * @return query result
     */
    public EPQueryResult execute();

    /**
     * Returns the event type, representing the columns of the select-clause
     * @return event type
     */
    public EventType getEventType();
}
