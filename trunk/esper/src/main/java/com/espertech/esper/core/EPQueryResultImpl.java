package com.espertech.esper.core;

import com.espertech.esper.client.EPQueryResult;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.Viewable;

import java.util.Iterator;

public class EPQueryResultImpl implements EPQueryResult
{
    private Viewable viewable;

    public EPQueryResultImpl(Viewable viewable)
    {
        this.viewable = viewable;
    }

    public int getRowCount()
    {
        // TODO
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<EventBean> iterator()
    {
        return viewable.iterator();
    }

    public SafeIterator<EventBean> safeIterator()
    {
        // TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType getEventType()
    {
        return viewable.getEventType();
    }
}
