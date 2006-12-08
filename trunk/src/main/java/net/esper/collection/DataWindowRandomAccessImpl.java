package net.esper.collection;

import net.esper.event.EventBean;

import java.util.ArrayList;
import java.util.List;

public class DataWindowRandomAccessImpl implements DataWindowRandomAccess
{
    private ArrayList<EventBean> arrayList;
    private List<EventBean> oldEvents;

    public void enter(EventBean event)
    {
        arrayList.add(0, event);
        oldEvents = null;
    }

    public void remove(List<EventBean> events)
    {
        for (int i = 0; i < events.size(); i++)
        {
            arrayList.remove(arrayList.size() - 1);
        }
        this.oldEvents = events;
    }

    public DataWindowRandomAccessImpl()
    {
        this.arrayList = new ArrayList<EventBean>();
    }

    public EventBean getNewData(int index)
    {
        return arrayList.get(index);
    }

    public int getNewDataSize()
    {
        return arrayList.size();
    }

    public EventBean getOldData(int index)
    {
        if (index < arrayList.size() )
        {
            return arrayList.get(index);
        }
        return oldEvents.get(index - arrayList.size());
    }

    public int getOldDataSize()
    {
        return arrayList.size() + oldEvents.size();
    }
}
