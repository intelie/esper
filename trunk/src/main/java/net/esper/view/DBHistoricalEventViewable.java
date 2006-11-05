package net.esper.view;

import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.List;
import java.util.Iterator;

public class DBHistoricalEventViewable implements HistoricalEventViewable
{
    private EventType eventType;

    public DBHistoricalEventViewable(EventType eventType)
    {
        this.eventType = eventType;
    }

    public View addView(View view)
    {
        return null;
    }

    public List<View> getViews()
    {
        return null;
    }

    public boolean removeView(View view)
    {
        return false;
    }

    public boolean hasViews()
    {
        return false;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return null;
    }

    public EventBean[] poll(EventBean[][] eventsPerStream)
    {
        return new EventBean[0];
    }
}
