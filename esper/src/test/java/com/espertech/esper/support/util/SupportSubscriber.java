/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.util;

import com.espertech.esper.collection.UniformPair;
import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;

public class SupportSubscriber
{
    private final List<Object[]> newDataList;
    private final List<Object[]> oldDataList;
    private Object[] lastNewData;
    private Object[] lastOldData;
    private boolean isInvoked;

    public SupportSubscriber()
    {
        newDataList = new LinkedList<Object[]>();
        oldDataList = new LinkedList<Object[]>();
    }

    public void update(Object[] newData, Object[] oldData)
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

    public Object[] getLastNewData()
    {
        return lastNewData;
    }

    public Object[] getLastNewDataAndReset()
    {
        Object[] hold = lastNewData;
        reset();
        return hold;
    }

    public Object[] getAndResetLastNewData()
    {
        Object[] lastNew = lastNewData;
        reset();
        return lastNew;
    }

    public Object assertOneGetNewAndReset()
    {
        Assert.assertTrue(isInvoked);

        Assert.assertEquals(1, newDataList.size());
        Assert.assertEquals(1, oldDataList.size());

        Assert.assertEquals(1, lastNewData.length);
        Assert.assertNull(lastOldData);

        Object lastNew = lastNewData[0];
        reset();
        return lastNew;
    }

    public Object assertOneGetOldAndReset()
    {
        Assert.assertTrue(isInvoked);

        Assert.assertEquals(1, newDataList.size());
        Assert.assertEquals(1, oldDataList.size());

        Assert.assertEquals(1, lastOldData.length);
        Assert.assertNull(lastNewData);

        Object lastNew = lastOldData[0];
        reset();
        return lastNew;
    }

    public Object[] getLastOldData()
    {
        return lastOldData;
    }

    public List<Object[]> getNewDataList()
    {
        return newDataList;
    }

    public List<Object[]> getOldDataList()
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

    public void setLastNewData(Object[] lastNewData)
    {
        this.lastNewData = lastNewData;
    }

    public void setLastOldData(Object[] lastOldData)
    {
        this.lastOldData = lastOldData;
    }

    public Object[] getNewDataListFlattened()
    {
        return flatten(newDataList);
    }

    public Object[] getOldDataListFlattened()
    {
        return flatten(oldDataList);
    }

    private Object[] flatten(List<Object[]> list)
    {
        int count = 0;
        for (Object[] events : list)
        {
            if (events != null)
            {
                count += events.length;
            }
        }

        Object[] array = new Object[count];
        count = 0;
        for (Object[] events : list)
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

    public UniformPair<Object[]> getDataListsFlattened()
    {
        return new UniformPair<Object[]>(flatten(newDataList), flatten(oldDataList));
    }
}
