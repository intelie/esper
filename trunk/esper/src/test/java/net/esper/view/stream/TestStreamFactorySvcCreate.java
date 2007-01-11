package net.esper.view.stream;

import junit.framework.TestCase;
import net.esper.support.filter.SupportFilterServiceImpl;
import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportBean;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterOperator;
import net.esper.view.EventStream;
import net.esper.event.EventType;

public class TestStreamFactorySvcCreate extends TestCase
{
    private StreamFactorySvcCreate streamFactoryService;
    private SupportFilterServiceImpl supportFilterService;

    private FilterSpec[] filterSpecs;
    private EventStream[] streams;

    public void setUp()
    {
        streamFactoryService = new StreamFactorySvcCreate();
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        supportFilterService = new SupportFilterServiceImpl();

        filterSpecs = new FilterSpec[3];
        filterSpecs[0] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[1] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[2] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "b" });

        streams = new EventStream[3];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, null);
        streams[1] = streamFactoryService.createStream(filterSpecs[1], supportFilterService, null);
        streams[2] = streamFactoryService.createStream(filterSpecs[2], supportFilterService, null);
    }

    public void testInvalid()
    {
        try
        {
            // try to reuse the same filter spec object, should fail
            streamFactoryService.createStream(filterSpecs[0], supportFilterService, null);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testCreate()
    {
        // Streams are reused
        assertNotSame(streams[0], streams[1]);
        assertNotSame(streams[0], streams[2]);
        assertNotSame(streams[1], streams[2]);

        // Type is ok
        assertEquals(SupportBean.class, streams[0].getEventType().getUnderlyingType());

        // 2 filters are active now
        assertEquals(3, supportFilterService.getAdded().size());
    }

    public void testDrop()
    {
        streamFactoryService.dropStream(filterSpecs[0], supportFilterService);
        streamFactoryService.dropStream(filterSpecs[1], supportFilterService);
        assertEquals(2, supportFilterService.getRemoved().size());

        // Filter removed
        streamFactoryService.dropStream(filterSpecs[2], supportFilterService);
        assertEquals(3, supportFilterService.getRemoved().size());

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
