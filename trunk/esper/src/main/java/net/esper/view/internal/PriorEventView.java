package net.esper.view.internal;

import net.esper.view.ViewSupport;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.ViewUpdatedCollection;

import java.util.Iterator;

public class PriorEventView extends ViewSupport
{
    private ViewUpdatedCollection buffer;

    public PriorEventView(ViewUpdatedCollection buffer)
    {
        this.buffer = buffer;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        buffer.update(newData, oldData);
        this.updateChildren(newData, oldData);
    }

    protected ViewUpdatedCollection getBuffer()
    {
        return buffer;
    }

    public EventType getEventType()
    {
        return parent.getEventType();
    }

    public Iterator<EventBean> iterator()
    {
        return parent.iterator();
    }
}
