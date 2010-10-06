package com.espertech.esper.collection;

import junit.framework.TestCase;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.client.EventBean;

public class TestOneEventCollection extends TestCase
{
    private OneEventCollection list;
    private EventBean[] events;

    public void setUp()
    {
        list = new OneEventCollection();
        events = SupportEventBeanFactory.makeEvents(new String[] {"1", "2", "3", "4"});
    }

    public void testFlow()
    {
        assertTrue(list.isEmpty());
        ArrayAssertionUtil.assertEqualsExactOrder(new EventBean[0], list.toArray());

        list.add(events[0]);
        assertFalse(list.isEmpty());
        ArrayAssertionUtil.assertEqualsExactOrder(new EventBean[] {events[0]}, list.toArray());

        list.add(events[1]);
        assertFalse(list.isEmpty());
        ArrayAssertionUtil.assertEqualsExactOrder(new EventBean[] {events[0], events[1]}, list.toArray());

        list.add(events[2]);
        assertFalse(list.isEmpty());
        ArrayAssertionUtil.assertEqualsExactOrder(new EventBean[] {events[0], events[1], events[2]}, list.toArray());
    }
}
