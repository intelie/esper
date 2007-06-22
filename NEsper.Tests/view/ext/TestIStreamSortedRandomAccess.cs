// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.util;

namespace net.esper.view.ext
{
	[TestFixture]
	public class TestIStreamSortedRandomAccess
	{
	    private IStreamSortedRandomAccess access;
	    private TreeDictionary<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents;
	    private EventBean[] events;

	    internal class VoidObserver : IStreamSortedRandomAccess.IStreamRandomAccessUpdateObserver
        {
            public void Updated(IStreamSortedRandomAccess iStreamSortedRandomAccess)
            {
            }
        }

	    [SetUp]
	    public void SetUp()
	    {
	    	access = new IStreamSortedRandomAccess(new VoidObserver());
	        sortedEvents = new TreeDictionary<MultiKeyUntyped, LinkedList<EventBean>>(new MultiKeyComparator(new Boolean[] {false}));

	        events = new EventBean[100];
	        for (int i = 0; i < events.Length; i++)
	        {
	            events[i] = SupportEventBeanFactory.CreateObject(new SupportBean());
	        }
	    }

	    [Test]
	    public void TestGet()
	    {
	        access.Refresh(sortedEvents, 0, 10);
	        Assert.IsNull(access.GetNewData(0));
	        Assert.IsNull(access.GetNewData(1));

	        Add("C", events[0]);
	        access.Refresh(sortedEvents, 1, 10);
	        AssertData(new EventBean[] {events[0]});

	        Add("E", events[1]);
	        access.Refresh(sortedEvents, 2, 10);
	        AssertData(new EventBean[] {events[0], events[1]});

	        Add("A", events[2]);
	        access.Refresh(sortedEvents, 3, 10);
	        AssertData(new EventBean[] {events[2], events[0], events[1]});

	        Add("C", events[4]);
	        access.Refresh(sortedEvents, 4, 10);
	        AssertData(new EventBean[] {events[2], events[4], events[0], events[1]});

	        Add("E", events[5]);
	        access.Refresh(sortedEvents, 5, 10);
	        AssertData(new EventBean[] {events[2], events[4], events[0], events[5], events[1]});

	        Add("A", events[6]);
	        access.Refresh(sortedEvents, 6, 10);
	        AssertData(new EventBean[] {events[6], events[2], events[4], events[0], events[5], events[1]});

	        Add("B", events[7]);
	        access.Refresh(sortedEvents, 7, 10);
	        AssertData(new EventBean[] {events[6], events[2], events[7], events[4], events[0], events[5], events[1]});

	        Add("F", events[8]);
	        access.Refresh(sortedEvents, 8, 10);
	        AssertData(new EventBean[] {events[6], events[2], events[7], events[4], events[0], events[5], events[1], events[8]});
	        //                          A           A           B       C           C           E           E           F

	        Add("D", events[9]);
	        access.Refresh(sortedEvents, 9, 10);
	        EventBean _event = access.GetNewData(5);
	        Assert.AreSame(events[9], access.GetNewData(5));
	    }

	    private void AssertData(EventBean[] events)
	    {
	        for (int i = 0; i < events.Length; i++)
	        {
                Assert.AreSame(events[i], access.GetNewData(i), "Failed for index " + i);
	        }
	        Assert.IsNull(access.GetNewData(events.Length));
	    }

	    private void Add(String key, EventBean _event)
	    {
	        ((SupportBean)_event.Underlying).SetString(key);
	        MultiKeyUntyped mkey = new MultiKeyUntyped(new Object[] {key});
	        LinkedList<EventBean> eventList = sortedEvents.Fetch(mkey);
	        if (eventList == null)
	        {
	            eventList = new LinkedList<EventBean>();
	        }
	        eventList.AddFirst(_event);
	        sortedEvents.Put(mkey, eventList);
	    }
	}
} // End of namespace
