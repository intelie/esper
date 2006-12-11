package net.esper.collection;

import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestDataWindowRandomAccessImpl extends TestCase
{
    private RandomAccessIRStreamImpl access;
    private EventBean[] events;

    public void setUp()
    {
        access = new RandomAccessIRStreamImpl();
        events = new EventBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = SupportEventBeanFactory.createObject(new SupportBean());
        }
    }

    public void testFlow()
    {
        assertNull(access.getNewData(0));
        assertNull(access.getOldData(0));

        access.update(new EventBean[] {events[0]}, null);
        assertEquals(events[0], access.getNewData(0));
        assertNull(access.getNewData(1));
        assertNull(access.getOldData(0));

        access.update(new EventBean[] {events[1], events[2]}, null);
        assertEquals(events[2], access.getNewData(0));
        assertEquals(events[1], access.getNewData(1));
        assertEquals(events[0], access.getNewData(2));
        assertNull(access.getNewData(3));
        assertEquals(events[0], access.getOldData(0));
        assertNull(access.getOldData(1));

        access.update(new EventBean[] {events[3]}, new EventBean[] {events[0]});
        assertEquals(events[3], access.getNewData(0));
        assertEquals(events[2], access.getNewData(1));
        assertEquals(events[1], access.getNewData(2));
        assertNull(access.getNewData(3));
        assertEquals(events[2], access.getOldData(0));
        assertEquals(events[1], access.getOldData(1));
        assertEquals(events[0], access.getOldData(2));
        assertNull(access.getOldData(3));

        access.update(null, new EventBean[] {events[1], events[2]});
        assertEquals(events[3], access.getNewData(0));
        assertNull(access.getNewData(1));
        assertEquals(events[3], access.getOldData(0));
        assertEquals(events[2], access.getOldData(1));
        assertEquals(events[1], access.getOldData(2));
        assertNull(access.getOldData(3));

        access.update(null, new EventBean[] {events[3]});
        assertNull(access.getNewData(0));
        assertEquals(events[3], access.getOldData(0));
        assertNull(access.getOldData(1));
    }
}
