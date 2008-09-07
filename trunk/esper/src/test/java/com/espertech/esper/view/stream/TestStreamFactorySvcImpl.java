package com.espertech.esper.view.stream;

import junit.framework.TestCase;
import com.espertech.esper.event.EventType;
import com.espertech.esper.filter.FilterOperator;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.filter.SupportFilterServiceImpl;
import com.espertech.esper.support.filter.SupportFilterSpecBuilder;
import com.espertech.esper.view.EventStream;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.util.ManagedLockImpl;

public class TestStreamFactorySvcImpl extends TestCase
{
    private StreamFactoryService streamFactoryService;
    private SupportFilterServiceImpl supportFilterService;

    private FilterSpecCompiled[] filterSpecs;
    private EventStream[] streams;

    public void setUp()
    {
        supportFilterService = new SupportFilterServiceImpl();
        streamFactoryService = new StreamFactorySvcImpl(true);
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        filterSpecs = new FilterSpecCompiled[3];
        filterSpecs[0] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[1] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
        filterSpecs[2] = SupportFilterSpecBuilder.build(eventType, new Object[] { "string", FilterOperator.EQUAL, "b" });
    }

    public void testInvalidJoin()
    {
        streams = new EventStream[3];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, null, true).getFirst();

        try
        {
            // try to reuse the same filter spec object, should fail
            streamFactoryService.createStream(filterSpecs[0], supportFilterService, null, true);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testCreateJoin()
    {
        streams = new EventStream[3];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, null, true).getFirst();
        streams[1] = streamFactoryService.createStream(filterSpecs[1], supportFilterService, null, true).getFirst();
        streams[2] = streamFactoryService.createStream(filterSpecs[2], supportFilterService, null, true).getFirst();

        // Streams are reused
        assertNotSame(streams[0], streams[1]);
        assertNotSame(streams[0], streams[2]);
        assertNotSame(streams[1], streams[2]);

        // Type is ok
        assertEquals(SupportBean.class, streams[0].getEventType().getUnderlyingType());

        // 2 filters are active now
        assertEquals(3, supportFilterService.getAdded().size());
    }

    public void testDropJoin()
    {
        streams = new EventStream[3];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, null, true).getFirst();
        streams[1] = streamFactoryService.createStream(filterSpecs[1], supportFilterService, null, true).getFirst();
        streams[2] = streamFactoryService.createStream(filterSpecs[2], supportFilterService, null, true).getFirst();

        streamFactoryService.dropStream(filterSpecs[0], supportFilterService, true);
        streamFactoryService.dropStream(filterSpecs[1], supportFilterService, true);
        assertEquals(2, supportFilterService.getRemoved().size());

        // Filter removed
        streamFactoryService.dropStream(filterSpecs[2], supportFilterService, true);
        assertEquals(3, supportFilterService.getRemoved().size());

        // Something already removed
        try
        {
            streamFactoryService.dropStream(filterSpecs[2], supportFilterService, true);
            TestCase.fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected
        }
    }

    public void testCreateNoJoin()
    {
        EPStatementHandle stmtHande = new EPStatementHandle("id", new ManagedLockImpl("id"), "text", false, null);
        streams = new EventStream[4];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, stmtHande, false).getFirst();
        streams[1] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, stmtHande, false).getFirst();
        streams[2] = streamFactoryService.createStream(filterSpecs[1], supportFilterService, stmtHande, false).getFirst();
        streams[3] = streamFactoryService.createStream(filterSpecs[2], supportFilterService, stmtHande, false).getFirst();

        // Streams are reused
        assertSame(streams[0], streams[1]);
        assertSame(streams[0], streams[2]);
        assertNotSame(streams[0], streams[3]);

        // Type is ok
        assertEquals(SupportBean.class, streams[0].getEventType().getUnderlyingType());

        // 2 filters are active now
        assertEquals(2, supportFilterService.getAdded().size());
    }

    public void testDropNoJoin()
    {
        EPStatementHandle stmtHande = new EPStatementHandle("id", new ManagedLockImpl("id"), "text", false, null);
        streams = new EventStream[4];
        streams[0] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, stmtHande, false).getFirst();
        streams[1] = streamFactoryService.createStream(filterSpecs[0], supportFilterService, stmtHande, false).getFirst();
        streams[2] = streamFactoryService.createStream(filterSpecs[1], supportFilterService, stmtHande, false).getFirst();
        streams[3] = streamFactoryService.createStream(filterSpecs[2], supportFilterService, stmtHande, false).getFirst();

        streamFactoryService.dropStream(filterSpecs[0], supportFilterService, false);
        streamFactoryService.dropStream(filterSpecs[1], supportFilterService, false);
        assertEquals(0, supportFilterService.getRemoved().size());

        // Filter removed
        streamFactoryService.dropStream(filterSpecs[0], supportFilterService, false);
        assertEquals(1, supportFilterService.getRemoved().size());

        streamFactoryService.dropStream(filterSpecs[2], supportFilterService, false);
        assertEquals(2, supportFilterService.getRemoved().size());

        // Something already removed
        try
        {
            streamFactoryService.dropStream(filterSpecs[2], supportFilterService, false);
            TestCase.fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected
        }
    }
}
