package com.espertech.esper.view.window;

import junit.framework.TestCase;
import com.espertech.esper.type.TimePeriodParameter;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewAttachException;
import com.espertech.esper.view.std.SizeView;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.event.EventType;

import java.util.Arrays;

public class TestExternallyTimedWindowViewFactory extends TestCase
{
    private ExternallyTimedWindowViewFactory factory;

    public void setUp()
    {
        factory = new ExternallyTimedWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"a", new TimePeriodParameter(2d)}, "a", 2000);
        tryParameter(new Object[] {"a", 10L}, "a", 10000);
        tryParameter(new Object[] {"a", 11}, "a", 11000);
        tryParameter(new Object[] {"a", 2.2}, "a", 2200);

        tryInvalidParameter(new Object[] {new TimePeriodParameter(2d)});
        tryInvalidParameter(new Object[] {"a"});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, Arrays.asList(new Object[] {"price", 1000}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new ExternallyTimedWindowView(factory, "volume", 1000, null, false)));
        assertFalse(factory.canReuse(new ExternallyTimedWindowView(factory, "price", 999, null, false)));
        assertTrue(factory.canReuse(new ExternallyTimedWindowView(factory, "price", 1000000, null, false)));
    }

    public void testAttach() throws Exception
    {
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        factory.setViewParameters(null, Arrays.asList(new Object[] {"dummy", 20}));
        try
        {
            factory.attach(parentType, null, null, null);
            fail();
        }
        catch (ViewAttachException ex)
        {
            // expected
        }

        factory.setViewParameters(null, Arrays.asList(new Object[] {"string", 20}));
        try
        {
            factory.attach(parentType, null, null, null);
            fail();
        }
        catch (ViewAttachException ex)
        {
            // expected
        }

        factory.setViewParameters(null, Arrays.asList(new Object[] {"longPrimitive", 20}));
        factory.attach(parentType, null, null, null);

        assertSame(parentType, factory.getEventType());
    }
    
    private void tryInvalidParameter(Object[] param) throws Exception
    {
        try
        {
            ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
            factory.setViewParameters(null, Arrays.asList(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldName, long msec) throws Exception
    {
        ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
        factory.setViewParameters(null, Arrays.asList(params));
        ExternallyTimedWindowView view = (ExternallyTimedWindowView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldName, view.getTimestampFieldName());
        assertEquals(msec, view.getMillisecondsBeforeExpiry());
    }
}
