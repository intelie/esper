package net.esper.collection;

import net.esper.event.EventBean;

import java.util.ArrayList;

public class RandomAccessIStreamImpl implements RandomAccess
{
    private ArrayList<EventBean> arrayList;

    public RandomAccessIStreamImpl()
    {
        this.arrayList = new ArrayList<EventBean>();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                arrayList.add(0, newData[i]);
            }
        }

        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                arrayList.remove(arrayList.size() - 1);
            }
        }
    }

    public EventBean getNewData(int index)
    {
        // New events are added to the start of the list
        if (index < arrayList.size() )
        {
            return arrayList.get(index);
        }
        return null;
    }

    public EventBean getOldData(int index)
    {
        return null;
    }
}
