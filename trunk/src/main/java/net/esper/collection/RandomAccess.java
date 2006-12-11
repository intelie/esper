package net.esper.collection;

import net.esper.event.EventBean;

public interface RandomAccess
{
    public EventBean getNewData(int index);
    public EventBean getOldData(int index);
}
