package net.esper.view.stream;

import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.filter.SupportFilterServiceImpl;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.event.EventType;
import net.esper.filter.FilterOperator;
import net.esper.filter.FilterSpec;
import net.esper.view.EventStream;
import junit.framework.TestCase;

public class TestStreamFactorySvcReuse extends TestCase
{
    private StreamFactorySvcReuse streamFactoryService;
    private SupportFilterServiceImpl supportFilterService;

    private FilterSpec[] filterSpecs;
    private EventStream[] streams;

    public void setUp()
    {
        streamFactoryService = new StreamFactorySvcReuse();
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        supportFilterService = new SupportFilterServiceImpl();

        filterSpecs = new FilterSpec[3];
        filterSpecs[0] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[1] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[2] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "b" });

        streams = new EventStream[4];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, null);
        streams[1] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, null);
        streams[2] = streamFactoryService.createStream(filterSpecs[1], supportFilterService, null);
        streams[3] = streamFactoryService.createStream(filterSpecs[2], supportFilterService, null);
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
        streamFactoryService.dropStream(filterSpecs[0], supportFilterService);
        streamFactoryService.dropStream(filterSpecs[1], supportFilterService);
        assertEquals(0, supportFilterService.getRemoved().size());

        // Filter removed
        streamFactoryService.dropStream(filterSpecs[0], supportFilterService);
        assertEquals(1, supportFilterService.getRemoved().size());

        streamFactoryService.dropStream(filterSpecs[2], supportFilterService);
        assertEquals(2, supportFilterService.getRemoved().size());

        // Something already removed
        try
        {
            streamFactoryService.dropStream(filterSpecs[2], supportFilterService);
            TestCase.fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected
        }
    }
}
