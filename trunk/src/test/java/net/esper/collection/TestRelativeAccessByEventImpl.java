package net.esper.collection;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.bean.SupportBean;

public class TestRelativeAccessByEventImpl extends TestCase
{
    private RelativeAccessByEventImpl access;
    private EventBean[] events;

    public void setUp()
    {
        access = new RelativeAccessByEventImpl();

        events = new EventBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = SupportEventBeanFactory.createObject(new SupportBean());
        }
    }

    public void testGet()
    {
        access.update(new EventBean[] {events[0]}, null);
        assertEquals(events[0], access.getRelativeToEvent(events[0], 0));
        assertNull(access.getRelativeToEvent(events[0], 1));

        // sends the newest event last (i.e. 1 older 2 as 1 is sent first)
        access.update(new EventBean[] {events[1], events[2]}, null);
        assertEquals(events[1], access.getRelativeToEvent(events[1], 0));
        assertNull(access.getRelativeToEvent(events[1], 1));
        assertEquals(events[2], access.getRelativeToEvent(events[2], 0));
        assertEquals(events[1], access.getRelativeToEvent(events[2], 1));
        assertNull(access.getRelativeToEvent(events[2], 2));

        // sends the newest event last (i.e. 1 older 2 as 1 is sent first)
        access.update(new EventBean[] {events[3], events[4], events[5]}, null);
        assertEquals(events[3], access.getRelativeToEvent(events[3], 0));
        assertNull(access.getRelativeToEvent(events[3], 1));
        assertEquals(events[4], access.getRelativeToEvent(events[4], 0));
        assertEquals(events[3], access.getRelativeToEvent(events[4], 1));
        assertNull(access.getRelativeToEvent(events[4], 2));
        assertEquals(events[5], access.getRelativeToEvent(events[5], 0));
        assertEquals(events[4], access.getRelativeToEvent(events[5], 1));
        assertEquals(events[3], access.getRelativeToEvent(events[5], 2));
        assertNull(access.getRelativeToEvent(events[5], 3));
    }
}
