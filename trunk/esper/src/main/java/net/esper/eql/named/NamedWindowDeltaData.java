package net.esper.eql.named;

import net.esper.event.EventBean;

public class NamedWindowDeltaData
{
    private final EventBean[] newData;
    private final EventBean[] oldData;

    public NamedWindowDeltaData(EventBean[] newData, EventBean[] oldData)
    {
        this.newData = newData;
        this.oldData = oldData;
    }

    public EventBean[] getNewData()
    {
        return newData;
    }

    public EventBean[] getOldData()
    {
        return oldData;
    }
}
