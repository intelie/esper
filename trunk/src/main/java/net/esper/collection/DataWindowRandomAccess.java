package net.esper.collection;

import net.esper.event.EventBean;

public interface DataWindowRandomAccess
{
    public EventBean getNewData(int index);
    public EventBean getOldData(int index);
    public int getNewDataSize();
    public int getOldDataSize();
}
