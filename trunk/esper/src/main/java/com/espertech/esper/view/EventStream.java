package com.espertech.esper.view;

import com.espertech.esper.event.EventBean;

/**
 * A streams is a conduct for incoming events. Incoming data is placed into streams for consumption by queries.
 */
public interface EventStream extends Viewable
{
    /**
     * Insert a new event onto the stream.
     * @param event to insert
     */
    public void insert(EventBean event);
}
