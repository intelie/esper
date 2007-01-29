package net.esper.collection;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.util.ManagedReadWriteLock;

public class TestFlushedEventBuffer extends TestCase
{
    private FlushedEventBuffer buffer;
    private EventBean[] events;

    public void setUp()
    {
        buffer = new FlushedEventBuffer();
        events = new EventBean[10];

        for (int i = 0; i < events.length; i++)
        {
            events[i] = SupportEventBeanFactory.createObject(i);
        }
    }

    public void testFlow()
    {
        // test empty buffer
        buffer.add(null);
        assertNull(buffer.getAndFlush());
        buffer.flush();

        // test add single events
        buffer.add(new EventBean[] { events[0] });
        EventBean[] results = buffer.getAndFlush();
        assertTrue((results.length == 1) && (results[0] == events[0]));

        buffer.add(new EventBean[] { events[0] });
        buffer.add(new EventBean[] { events[1] });
        results = buffer.getAndFlush();
        assertTrue((results.length == 2));
        assertSame(events[0], results[0]);
        assertSame(events[1], results[1]);

        buffer.flush();
        assertNull(buffer.getAndFlush());

        // Add multiple events
        buffer.add(new EventBean[] { events[2], events[3] });
        buffer.add(new EventBean[] { events[4], events[5] });
        results = buffer.getAndFlush();
        assertTrue((results.length == 4));
        assertSame(events[2], results[0]);
        assertSame(events[3], results[1]);
        assertSame(events[4], results[2]);
        assertSame(events[5], results[3]);

        buffer.flush();
        assertNull(buffer.getAndFlush());
    }
}
