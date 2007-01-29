package net.esper.view.window;

import net.esper.event.EventBean;
import net.esper.collection.ViewUpdatedCollection;

import java.util.ArrayList;

/**
 * For use with length and time window views that must provide random access into data window contents
 * provided for the "previous" expression if used.
 */
public class IStreamRandomAccess implements RandomAccessByIndex, ViewUpdatedCollection
{
    private ArrayList<EventBean> arrayList;

    /**
     * Ctor.
     */
    public IStreamRandomAccess()
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
