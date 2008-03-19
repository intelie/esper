package com.espertech.esper.core;

import com.espertech.esper.client.EPQueryResult;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.view.View;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.Iterator;
import java.util.List;

// TODO: remove
public class EPQueryResultViewable implements Viewable
{
    private Iterator<EventBean> it;
    private EventType eventType;

    public EPQueryResultViewable(Iterator<EventBean> it, EventType eventType)
    {
        this.it = it;
        this.eventType = eventType;
    }

    public View addView(View view)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<View> getViews()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean removeView(View view)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasViews()
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return it;
    }
}
