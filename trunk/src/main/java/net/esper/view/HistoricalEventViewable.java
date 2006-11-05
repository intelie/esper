package net.esper.view;

import net.esper.event.EventBean;

public interface HistoricalEventViewable extends Viewable
{
    public EventBean[] poll(EventBean[][] eventsPerStream);
}
