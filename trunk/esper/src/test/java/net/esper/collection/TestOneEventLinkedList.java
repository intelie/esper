package net.esper.collection;

import junit.framework.TestCase;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.event.EventBean;

public class TestOneEventLinkedList extends TestCase
{
    private OneEventLinkedList list;
    private EventBean[] events;

    public void setUp()
    {
        list = new OneEventLinkedList();
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
