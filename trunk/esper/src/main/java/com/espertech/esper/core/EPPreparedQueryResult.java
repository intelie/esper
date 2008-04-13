package com.espertech.esper.core;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;

/**
 * The result of executing a prepared query.
 */
public class EPPreparedQueryResult
{
    private final EventType eventType;
    private final EventBean[] result;

    /**
     * Ctor.
     * @param eventType is the type of event produced by the query
     * @param result the result rows
     */
    public EPPreparedQueryResult(EventType eventType, EventBean[] result)
    {
        this.eventType = eventType;
        this.result = result;
    }

    /**
     * Returs the event type representing the selected columns.
     * @return metadata
     */
    public EventType getEventType()
    {
        return eventType;
    }

    /**
     * Returns the query result.
     * @return result rows
     */
    public EventBean[] getResult()
    {
        return result;
    }
}
