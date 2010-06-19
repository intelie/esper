package com.espertech.esper.example.stockticker;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.UniformPair;

import java.util.List;
import java.util.LinkedList;

import junit.framework.Assert;

public class SupportUpdateListener implements UpdateListener
{
    private final List<EventBean[]> newDataList;
    private final List<EventBean[]> oldDataList;
    private EventBean[] lastNewData;
    private EventBean[] lastOldData;
    private boolean isInvoked;

    public SupportUpdateListener()
    {
        newDataList = new LinkedList<EventBean[]>();
        oldDataList = new LinkedList<EventBean[]>();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
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

    public EventBean[] getLastNewDataAndReset()
    {
        EventBean[] hold = lastNewData;
        reset();
        return hold;
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

    public EventBean assertOneGetNew()
    {
        Assert.assertTrue(isInvoked);

        Assert.assertEquals(1, newDataList.size());
        Assert.assertEquals(1, oldDataList.size());

        Assert.assertEquals(1, lastNewData.length);
        return lastNewData[0];
    }

    public EventBean assertOneGetOld()
    {
        Assert.assertTrue(isInvoked);

        Assert.assertEquals(1, newDataList.size());
        Assert.assertEquals(1, oldDataList.size());

        Assert.assertEquals(1, lastOldData.length);
        return lastOldData[0];
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

    public EventBean[] getNewDataListFlattened()
    {
        return flatten(newDataList);
    }

    public EventBean[] getOldDataListFlattened()
    {
        return flatten(oldDataList);
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

    public void assertUnderlyingAndReset(Object[] expectedUnderlyingNew, Object[] expectedUnderlyingOld)
    {
        Assert.assertEquals(1, getNewDataList().size());
        Assert.assertEquals(1, getOldDataList().size());

        EventBean[] newEvents = getLastNewData();
        EventBean[] oldEvents = getLastOldData();

        if (expectedUnderlyingNew != null)
        {
            Assert.assertEquals(expectedUnderlyingNew.length, newEvents.length);
            for (int i = 0; i < expectedUnderlyingNew.length; i++)
            {
                Assert.assertSame(expectedUnderlyingNew[i], newEvents[i].getUnderlying());
            }
        }
        else
        {
            Assert.assertNull(newEvents);
        }

        if (expectedUnderlyingOld != null)
        {
            Assert.assertEquals(expectedUnderlyingOld.length, oldEvents.length);
            for (int i = 0; i < expectedUnderlyingOld.length; i++)
            {
                Assert.assertSame(expectedUnderlyingOld[i], oldEvents[i].getUnderlying());
            }
        }
        else
        {
            Assert.assertNull(oldEvents);
        }

        reset();
    }

    public void assertFieldEqualsAndReset(String fieldName, Object[] expectedNew, Object[] expectedOld)
    {
        Assert.assertEquals(1, getNewDataList().size());
        Assert.assertEquals(1, getOldDataList().size());

        EventBean[] newEvents = getLastNewData();
        EventBean[] oldEvents = getLastOldData();

        if (expectedNew != null)
        {
            Assert.assertEquals(expectedNew.length, newEvents.length);
            for (int i = 0; i < expectedNew.length; i++)
            {
                Object result = newEvents[i].get(fieldName);
                Assert.assertEquals(expectedNew[i], result);
            }
        }
        else
        {
            Assert.assertNull(newEvents);
        }

        if (expectedOld != null)
        {
            Assert.assertEquals(expectedOld.length, oldEvents.length);
            for (int i = 0; i < expectedOld.length; i++)
            {
                Assert.assertEquals(expectedOld[i], oldEvents[i].get(fieldName));
            }
        }
        else
        {
            Assert.assertNull(oldEvents);
        }

        reset();
    }

    public UniformPair<EventBean[]> getDataListsFlattened()
    {
        return new UniformPair<EventBean[]>(flatten(newDataList), flatten(oldDataList));
    }
}
