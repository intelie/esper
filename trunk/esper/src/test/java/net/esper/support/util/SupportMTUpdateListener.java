package net.esper.support.util;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.util.ThreadLogUtil;

import java.util.List;
import java.util.LinkedList;

import junit.framework.Assert;

public class SupportMTUpdateListener implements UpdateListener
{
    private final List<EventBean[]> newDataList;
    private final List<EventBean[]> oldDataList;
    private EventBean[] lastNewData;
    private EventBean[] lastOldData;
    private boolean isInvoked;

    public SupportMTUpdateListener()
    {
        newDataList = new LinkedList<EventBean[]>();
        oldDataList = new LinkedList<EventBean[]>();
    }

    public synchronized void update(EventBean[] newData, EventBean[] oldData)
    {
        this.oldDataList.add(oldData);
        this.newDataList.add(newData);

        this.lastNewData = newData;
        this.lastOldData = oldData;

        isInvoked = true;
    }

    public synchronized void reset()
    {
        this.oldDataList.clear();
        this.newDataList.clear();
        this.lastNewData = null;
        this.lastOldData = null;
        isInvoked = false;
    }

    public EventBean[] getLastNewData()
    {
        return lastNewData;
    }

    public synchronized EventBean[] getAndResetLastNewData()
    {
        EventBean[] lastNew = lastNewData;
        reset();
        return lastNew;
    }

    public synchronized EventBean assertOneGetNewAndReset()
    {
        Assert.assertTrue(isInvoked);

        Assert.assertEquals(1, newDataList.size());
        Assert.assertEquals(1, oldDataList.size());

        Assert.assertEquals(1, lastNewData.length);
        Assert.assertNull(lastOldData);

        EventBean lastNew = lastNewData[0];
        reset();
        return lastNew;
    }

    public synchronized EventBean assertOneGetOldAndReset()
    {
        Assert.assertTrue(isInvoked);

        Assert.assertEquals(1, newDataList.size());
        Assert.assertEquals(1, oldDataList.size());

        Assert.assertEquals(1, lastOldData.length);
        Assert.assertNull(lastNewData);

        EventBean lastNew = lastOldData[0];
        reset();
        return lastNew;
    }

    public EventBean[] getLastOldData()
    {
        return lastOldData;
    }

    public List<EventBean[]> getNewDataList()
    {
        return newDataList;
    }

    public List<EventBean[]> getOldDataList()
    {
        return oldDataList;
    }

    public boolean isInvoked()
    {
        return isInvoked;
    }

    public synchronized boolean getAndClearIsInvoked()
    {
        boolean invoked = isInvoked;
        isInvoked = false;
        return invoked;
    }

    public synchronized EventBean[] getNewDataListFlattened()
    {
        return flatten(newDataList);
    }

    private EventBean[] flatten(List<EventBean[]> list)
    {
        int count = 0;
        for (EventBean[] events : list)
        {
            if (events != null)
            {
                count += events.length;
            }
        }

        EventBean[] array = new EventBean[count];
        count = 0;
        for (EventBean[] events : list)
        {
            if (events != null)
            {
                for (int i = 0; i < events.length; i++)
                {
                    array[count++] = events[i];
                }
            }
        }
        return array;
    }
}
