///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;

namespace net.esper.support.util
{
	public class SupportUpdateListener : UpdateListener
	{
	    private readonly List<EventBean[]> newDataList;
	    private readonly List<EventBean[]> oldDataList;
	    private EventBean[] lastNewData;
	    private EventBean[] lastOldData;
	    private bool isInvoked;

	    public SupportUpdateListener()
	    {
	        newDataList = new List<EventBean[]>();
	        oldDataList = new List<EventBean[]>();
	    }

	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        this.oldDataList.Add(oldData);
	        this.newDataList.Add(newData);

	        this.lastNewData = newData;
	        this.lastOldData = oldData;

	        isInvoked = true;
	    }

	    public void Reset()
	    {
	        this.oldDataList.Clear();
	        this.newDataList.Clear();
	        this.lastNewData = null;
	        this.lastOldData = null;
	        isInvoked = false;
	    }

	    public EventBean[] GetAndResetLastNewData()
	    {
	        EventBean[] lastNew = lastNewData;
	        Reset();
	        return lastNew;
	    }

	    public EventBean AssertOneGetNewAndReset()
	    {
	        Assert.IsTrue(isInvoked);

	        Assert.AreEqual(1, newDataList.Count);
	        Assert.AreEqual(1, oldDataList.Count);

	        Assert.AreEqual(1, lastNewData.Length);
	        Assert.IsNull(lastOldData);

	        EventBean lastNew = lastNewData[0];
	        Reset();
	        return lastNew;
	    }

	    public EventBean AssertOneGetOldAndReset()
	    {
	        Assert.IsTrue(isInvoked);

	        Assert.AreEqual(1, newDataList.Count);
	        Assert.AreEqual(1, oldDataList.Count);

	        Assert.AreEqual(1, lastOldData.Length);
	        Assert.IsNull(lastNewData);

	        EventBean lastNew = lastOldData[0];
	        Reset();
	        return lastNew;
	    }

        public EventBean[] LastNewData
        {
            get { return lastNewData; }
            set { lastNewData = value;  }
        }

	    public EventBean[] LastOldData
	    {
	        get { return lastOldData; }
            set { lastOldData = value;  }
	    }

	    public IList<EventBean[]> NewDataList
	    {
            get { return newDataList; }
	    }

	    public IList<EventBean[]> OldDataList
	    {
            get { return oldDataList; }
	    }

	    public bool IsInvoked
	    {
            get { return isInvoked; }
	    }

	    public bool GetAndClearIsInvoked()
	    {
	        bool invoked = isInvoked;
	        isInvoked = false;
	        return invoked;
	    }

	    public EventBean[] GetNewDataListFlattened()
	    {
	        return Flatten(newDataList);
	    }

	    private static EventBean[] Flatten(List<EventBean[]> list)
	    {
	        int count = 0;
	        foreach (EventBean[] events in list)
	        {
	            if (events != null)
	            {
	                count += events.Length;
	            }
	        }

	        EventBean[] array = new EventBean[count];
	        count = 0;
	        foreach (EventBean[] events in list)
	        {
	            if (events != null)
	            {
	                for (int i = 0; i < events.Length; i++)
	                {
	                    array[count++] = events[i];
	                }
	            }
	        }
	        return array;
	    }

	    public void AssertUnderlyingAndReset(Object[] expectedUnderlyingNew, Object[] expectedUnderlyingOld)
	    {
	        Assert.AreEqual(1, NewDataList.Count);
	        Assert.AreEqual(1, OldDataList.Count);

	        EventBean[] newEvents = LastNewData;
	        EventBean[] oldEvents = LastOldData;

	        if (expectedUnderlyingNew != null)
	        {
	            Assert.AreEqual(expectedUnderlyingNew.Length, newEvents.Length);
	            for (int i = 0; i < expectedUnderlyingNew.Length; i++)
	            {
	                Assert.AreSame(expectedUnderlyingNew[i], newEvents[i].Underlying);
	            }
	        }
	        else
	        {
	            Assert.IsNull(newEvents);
	        }

	        if (expectedUnderlyingOld != null)
	        {
	            Assert.AreEqual(expectedUnderlyingOld.Length, oldEvents.Length);
	            for (int i = 0; i < expectedUnderlyingOld.Length; i++)
	            {
	                Assert.AreSame(expectedUnderlyingOld[i], oldEvents[i].Underlying);
	            }
	        }
	        else
	        {
	            Assert.IsNull(oldEvents);
	        }

	        Reset();
	    }

	    public void AssertFieldEqualsAndReset(String fieldName, Object[] expectedNew, Object[] expectedOld)
	    {
	        Assert.AreEqual(1, NewDataList.Count);
	        Assert.AreEqual(1, OldDataList.Count);

	        EventBean[] newEvents = LastNewData;
	        EventBean[] oldEvents = LastOldData;

	        if (expectedNew != null)
	        {
	            Assert.AreEqual(expectedNew.Length, newEvents.Length);
	            for (int i = 0; i < expectedNew.Length; i++)
	            {
	                Object result = newEvents[i][fieldName];
	                Assert.AreEqual(expectedNew[i], result);
	            }
	        }
	        else
	        {
	            Assert.IsNull(newEvents);
	        }

	        if (expectedOld != null)
	        {
	            Assert.AreEqual(expectedOld.Length, oldEvents.Length);
	            for (int i = 0; i < expectedOld.Length; i++)
	            {
	                Assert.AreEqual(expectedOld[i], oldEvents[i][fieldName]);
	            }
	        }
	        else
	        {
	            Assert.IsNull(oldEvents);
	        }

	        Reset();
	    }
	}
} // End of namespace
