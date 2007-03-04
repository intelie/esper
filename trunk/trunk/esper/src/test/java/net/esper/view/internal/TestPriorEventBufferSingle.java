package net.esper.view.internal;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.view.internal.PriorEventBufferSingle;

public class TestPriorEventBufferSingle extends TestCase
{
    private PriorEventBufferSingle buffer;
    private EventBean[] events;

    public void setUp()
    {
        buffer = new PriorEventBufferSingle(3);

        events = new EventBean[100];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean_S0 bean = new SupportBean_S0(i);
            events[i] = SupportEventBeanFactory.createObject(bean);
        }
    }

    public void testFlow()
    {
        buffer.update(new EventBean[] {events[0], events[1]}, null);
        assertEvents0And1();

        buffer.update(new EventBean[] {events[2]}, null);
        assertEvents0And1();
        assertEvents2();

        buffer.update(new EventBean[] {events[3], events[4]}, null);
        assertEvents0And1();
        assertEvents2();
        assertEvents3And4();

        buffer.update(null, new EventBean[] {events[0]});
        assertEvents0And1();
        assertEvents2();
        assertEvents3And4();

        buffer.update(null, new EventBean[] {events[1], events[3]});
        tryInvalid(events[0], 0);
        assertNull(buffer.getRelativeToEvent(events[1], 0));
        assertEvents2();
        assertEvents3And4();

        buffer.update(new EventBean[] {events[5]}, null);
        tryInvalid(events[0], 0);
        tryInvalid(events[1], 0);
        tryInvalid(events[3], 0);
        assertEvents2();
        assertEquals(events[1], buffer.getRelativeToEvent(events[4], 0));
        assertEquals(events[2], buffer.getRelativeToEvent(events[5], 0));
    }

    private void assertEvents0And1()
    {
        assertNull(buffer.getRelativeToEvent(events[0], 0));     // getting 0 is getting prior 1 (see indexes)
        assertNull(buffer.getRelativeToEvent(events[1], 0));
    }

    private void assertEvents2()
    {
        assertNull(buffer.getRelativeToEvent(events[2], 0));
    }

    private void assertEvents3And4()
    {
        assertEquals(events[0], buffer.getRelativeToEvent(events[3], 0));
        assertEquals(events[1], buffer.getRelativeToEvent(events[4], 0));
    }

    public void tryInvalid(EventBean event, int index)
    {
        try
        {
            buffer.getRelativeToEvent(event, index);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testInvalid()
    {
        try
        {
            buffer.getRelativeToEvent(events[1], 2);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}
