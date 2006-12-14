package net.esper.view.internal;

import net.esper.view.ViewSupport;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.RollingEventBuffer;

import java.util.Iterator;

public class PriorEventView extends ViewSupport
{
    private int size;
    private RollingEventBuffer newDataBuf;
    private RollingEventBuffer oldDataBuf;

    public PriorEventView(int size)
    {
        this.size = size;
        newDataBuf = new RollingEventBuffer(size);
        oldDataBuf = new RollingEventBuffer(size);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        newDataBuf.add(newData);
        oldDataBuf.add(oldData);
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
