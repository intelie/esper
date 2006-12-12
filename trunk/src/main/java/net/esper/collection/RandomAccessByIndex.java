package net.esper.collection;

import net.esper.event.EventBean;

public interface RandomAccessByIndex
{
    public EventBean getNewData(int index);
    public EventBean getOldData(int index);
}
