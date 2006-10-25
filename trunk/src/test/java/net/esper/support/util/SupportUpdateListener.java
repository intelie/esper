package net.esper.support.util;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

import java.util.List;
import java.util.LinkedList;
import java.io.Serializable;

import junit.framework.Assert;

public class SupportUpdateListener implements UpdateListener, Serializable
{
    private transient List<EventBean[]> newDataList;
    private transient List<EventBean[]> oldDataList;
    private transient EventBean[] lastNewData;
    private transient EventBean[] lastOldData;
    private transient boolean isInvoked;

    public SupportUpdateListener()
    {
        newDataList = new LinkedList<EventBean[]>();
        oldDataList = new LinkedList<EventBean[]>();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newDataList == null)
        {
            newDataList = new LinkedList<EventBean[]>();
            oldDataList = new LinkedList<EventBean[]>();
        }

        this.oldDataList.add(oldData);
        this.newDataList.add(newData);

        this.lastNewData = newData;
        this.lastOldData = oldData;

        isInvoked = true;
    }

    public void reset()
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

    public EventBean[] getAndResetLastNewData()
    {
        EventBean[] lastNew = lastNewData;
        reset();
        return lastNew;
    }

    public EventBean assertOneGetNewAndReset()
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

    public EventBean assertOneGetOldAndReset()
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

    public boolean getAndClearIsInvoked()
    {
        boolean invoked = isInvoked;
        isInvoked = false;
        return invoked;
    }

    public void setLastNewData(EventBean[] lastNewData)
    {
        this.lastNewData = lastNewData;
    }

    public void setLastOldData(EventBean[] lastOldData)
    {
        this.lastOldData = lastOldData;
    }
}
