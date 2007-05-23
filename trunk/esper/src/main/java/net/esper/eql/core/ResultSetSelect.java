package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.collection.MultiKeyUntyped;

public class ResultSetSelect
{
    private EventBean[] events;
    private MultiKeyUntyped[] orderKeys;

    public EventBean[] getEvents()
    {
        return events;
    }

    public void setEvents(EventBean[] events)
    {
        this.events = events;
    }

    public MultiKeyUntyped[] getOrderKeys()
    {
        return orderKeys;
    }

    public void setOrderKeys(MultiKeyUntyped[] orderKeys)
    {
        this.orderKeys = orderKeys;
    }
}
