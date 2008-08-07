package com.espertech.esper.core;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.Iterator;

/**
 * Results of a fire-and-forget, non-continuous query.
 */
public interface EPQueryResult
{
    /**
     * Returns an array representing query result rows.
     * @return result array
     */
    public EventBean[] getArray();

    /**
     * Returns the event type of the result.
     * @return type
     */
    public EventType getEventType();

    /**
     * Returns an iterator representing query result rows.
     * @return result row iterator
     */
    public Iterator<EventBean> iterator();
}
