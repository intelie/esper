package net.esper.view.internal;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.view.internal.PriorEventBufferUnbound;

public class TestPriorEventBufferUnbound extends TestCase
{
    private PriorEventBufferUnbound buffer;
    private EventBean[] events;

    public void setUp()
    {
        buffer = new PriorEventBufferUnbound(3);

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
        assertEquals(events[1], buffer.getNewData(0));
        assertEquals(events[0], buffer.getNewData(1));
        assertNull(buffer.getNewData(2));
    }

    public void testInvalid()
    {
        try
        {
            buffer.getNewData(6);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}
