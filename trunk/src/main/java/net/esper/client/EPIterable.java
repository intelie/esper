package net.esper.client;

import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.Iterator;

/**
 * Interface to iterate over events.
 */
public interface EPIterable
{
    /**
     * Returns an iterator over events.
     * @return event iterator
     */
    public Iterator<EventBean> iterator();

    /**
     * Returns the type of events the iterable returns.
     * @return event type of events the iterator returns
     */
    public EventType getEventType();
}

