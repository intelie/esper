package com.espertech.esper.view;

import java.util.Iterator;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;

/**
 * Interface that marks an event collection.
 * Every event in the event collection must be of the same event type, as defined by the getEventType() call.
 */
public interface EventCollection extends Iterable<EventBean>
{
    /**
     * Provides metadata information about the type of object the event collection contains.
     * @return metadata for the objects in the collection
     */
    public EventType getEventType();

    /**
     * Allows iteration through all elements in this event collection.
     * The iterator will return the elements in the collection in their natural order, or,
     * if there is no natural ordering, in some unpredictable order.
     * @return an iterator which will go through all current elements in the collection.
     */
    public Iterator<EventBean> iterator();
}
