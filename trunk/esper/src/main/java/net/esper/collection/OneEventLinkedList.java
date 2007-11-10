package net.esper.collection;

import net.esper.event.EventBean;

import java.util.LinkedList;

/**
 * Simple collection that exposes a limited add-and-get interface and that is optimized towards holding
 * a single event, but can hold multiple events. If more then one event is added, the
 * class allocates a linked list for additional events.
 */
public class OneEventLinkedList
{
    private EventBean firstEvent;
    private LinkedList<EventBean> additionalEvents;

    /**
     * Add an event to the collection.
     * @param event is the event to add
     */
    public void add(EventBean event)
    {
        if (event == null)
        {
            throw new IllegalArgumentException("Null event not allowed");
        }
        
        if (firstEvent == null)
        {
            firstEvent = event;
            return;
        }
        
        if (additionalEvents == null)
        {
            additionalEvents = new LinkedList<EventBean>();
        }
        additionalEvents.add(event);
    }

    /**
     * Returns true if the collection is empty.
     * @return true if empty, false if not
     */
    public boolean isEmpty()
    {
        return firstEvent == null;
    }

    /**
     * Returns an array holding the collected events.
     * @return event array
     */
    public EventBean[] toArray()
    {
        if (firstEvent == null)
        {
            return new EventBean[0];
        }

        if (additionalEvents == null)
        {
            return new EventBean[] {firstEvent};
        }

        EventBean[] events = new EventBean[1 + additionalEvents.size()];
        events[0] = firstEvent;

        int count = 1;
        for (EventBean event : additionalEvents)
        {
            events[count] = event;
            count++;
        }

        return events;
    }
}
