package net.esper.collection;

import net.esper.event.EventBean;

import java.util.ArrayList;

public class RandomAccessIRStreamImpl implements RandomAccessByIndex
{
    private ArrayList<EventBean> arrayList;
    private EventBean[] lastOldEvents;
    private EventBean[] lastNewEvents;

    public RandomAccessIRStreamImpl()
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

        lastOldEvents = oldData;
        lastNewEvents = newData;
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
        // Taking a view as if
        // - the oldEvents are still part of the window
        // - the newEvents have not been added yet

        // Add to index if we got new events
        int oldIndex = index;
        if (lastNewEvents != null)
        {
            oldIndex = index + lastNewEvents.length;
        }

        // If in the data window, serve up
        if (oldIndex < arrayList.size() )
        {
            return arrayList.get(oldIndex);
        }

        // Overflow to the last old events
        int overflow = oldIndex - arrayList.size();
        if (lastOldEvents != null)
        {
            if (overflow < lastOldEvents.length)
            {
                // The oldest event in the lastOldEvents is first, therefore reverse the
                // index access to start at the end
                int reversedIndex = lastOldEvents.length - overflow - 1;
                return lastOldEvents[reversedIndex];
            }
        }
        return null;
    }
}
