package net.esper.collection;

import net.esper.event.EventBean;

public interface RelativeAccessByEvent
{
    public EventBean getRelativeToEvent(EventBean event, int prevIndex);
}
