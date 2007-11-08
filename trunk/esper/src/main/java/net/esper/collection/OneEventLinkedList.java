package net.esper.collection;

import net.esper.event.EventBean;

import java.util.LinkedList;

public class OneEventLinkedList
{
    private EventBean firstEvent;
    private LinkedList<EventBean> additionalEvents;

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

    public boolean isEmpty()
    {
        return firstEvent == null;
    }

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
