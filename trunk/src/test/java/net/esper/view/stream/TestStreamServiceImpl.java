package net.esper.view.stream;

import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.filter.SupportFilterServiceImpl;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;
import net.esper.filter.FilterOperator;
import net.esper.filter.FilterSpec;
import net.esper.view.EventStream;
import junit.framework.TestCase;

public class TestStreamServiceImpl extends TestCase
{
    private StreamReuseService streamReuseService;
    private SupportFilterServiceImpl supportFilterService;

    private FilterSpec[] filterSpecs;
    private EventStream[] streams;

    public void setUp()
    {
        streamReuseService = new StreamReuseServiceImpl();
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        supportFilterService = new SupportFilterServiceImpl();

        filterSpecs = new FilterSpec[3];
        filterSpecs[0] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[1] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[2] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "b" });

        streams = new EventStream[4];
        streams[0] = streamReuseService.createStream(filterSpecs[0], supportFilterService);
        streams[1] = streamReuseService.createStream(filterSpecs[0], supportFilterService);
        streams[2] = streamReuseService.createStream(filterSpecs[1], supportFilterService);
        streams[3] = streamReuseService.createStream(filterSpecs[2], supportFilterService);
    }

    public void testCreate()
    {
        // Streams are reused
        assertSame(streams[0], streams[1]);
        assertSame(streams[0], streams[2]);
        assertNotSame(streams[0], streams[3]);

        // Type is ok
        assertEquals(SupportBean.class, streams[0].getEventType().getUnderlyingType());

        // 2 filters are active now
        assertEquals(2, supportFilterService.getAdded().size());
    }

    public void testDrop()
    {
        streamReuseService.dropStream(filterSpecs[0], supportFilterService);
        streamReuseService.dropStream(filterSpecs[1], supportFilterService);
        assertEquals(0, supportFilterService.getRemoved().size());

        // Filter removed
        streamReuseService.dropStream(filterSpecs[0], supportFilterService);
        assertEquals(1, supportFilterService.getRemoved().size());

        streamReuseService.dropStream(filterSpecs[2], supportFilterService);
        assertEquals(2, supportFilterService.getRemoved().size());

        // Something already removed
        try
        {
            streamReuseService.dropStream(filterSpecs[2], supportFilterService);
            TestCase.fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected
        }
    }
}
