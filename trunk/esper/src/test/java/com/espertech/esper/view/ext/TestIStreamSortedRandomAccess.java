package com.espertech.esper.view.ext;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.util.MultiKeyComparator;
import com.espertech.esper.view.window.RandomAccessByIndexObserver;
import com.espertech.esper.view.window.RandomAccessByIndex;

import java.util.LinkedList;
import java.util.TreeMap;

import junit.framework.TestCase;

public class TestIStreamSortedRandomAccess extends TestCase
{
    private IStreamSortedRandomAccess access;
    private TreeMap<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents;
    private EventBean[] events;

    public void setUp()
    {
        RandomAccessByIndexObserver updateObserver = new RandomAccessByIndexObserver()
        {
            public void updated(RandomAccessByIndex randomAccessByIndex)
            {
            }
        };
        access = new IStreamSortedRandomAccess(updateObserver);
        sortedEvents = new TreeMap<MultiKeyUntyped, LinkedList<EventBean>>(new MultiKeyComparator(new Boolean[] {false}));

        events = new EventBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = SupportEventBeanFactory.createObject(new SupportBean());
        }
    }

    public void testGet()
    {
        access.refresh(sortedEvents, 0, 10);
        assertNull(access.getNewData(0));
        assertNull(access.getNewData(1));

        add("C", events[0]);
        access.refresh(sortedEvents, 1, 10);
        assertData(new EventBean[] {events[0]});

        add("E", events[1]);
        access.refresh(sortedEvents, 2, 10);
        assertData(new EventBean[] {events[0], events[1]});

        add("A", events[2]);
        access.refresh(sortedEvents, 3, 10);
        assertData(new EventBean[] {events[2], events[0], events[1]});

        add("C", events[4]);
        access.refresh(sortedEvents, 4, 10);
        assertData(new EventBean[] {events[2], events[4], events[0], events[1]});

        add("E", events[5]);
        access.refresh(sortedEvents, 5, 10);
        assertData(new EventBean[] {events[2], events[4], events[0], events[5], events[1]});

        add("A", events[6]);
        access.refresh(sortedEvents, 6, 10);
        assertData(new EventBean[] {events[6], events[2], events[4], events[0], events[5], events[1]});

        add("B", events[7]);
        access.refresh(sortedEvents, 7, 10);
        assertData(new EventBean[] {events[6], events[2], events[7], events[4], events[0], events[5], events[1]});

        add("F", events[8]);
        access.refresh(sortedEvents, 8, 10);
        assertData(new EventBean[] {events[6], events[2], events[7], events[4], events[0], events[5], events[1], events[8]});
        //                          A           A           B       C           C           E           E           F

        add("D", events[9]);
        access.refresh(sortedEvents, 9, 10);
        EventBean event = access.getNewData(5);
        assertSame(events[9], access.getNewData(5));
    }

    private void assertData(EventBean[] events)
    {
        for (int i = 0; i < events.length; i++)
        {
            assertSame("Failed for index " + i, events[i], access.getNewData(i));
        }
        assertNull(access.getNewData(events.length));
    }

    private void add(String key, EventBean event)
    {
        ((SupportBean)event.getUnderlying()).setString(key);
        MultiKeyUntyped mkey = new MultiKeyUntyped(new Object[] {key});
        LinkedList<EventBean> eventList = sortedEvents.get(mkey);
        if (eventList == null)
        {
            eventList = new LinkedList<EventBean>();
        }
        eventList.addFirst(event);
        sortedEvents.put(mkey, eventList);
    }
}
