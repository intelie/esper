package net.esper.view.window;

import junit.framework.TestCase;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.std.SizeView;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.event.EventType;

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
        factory.setViewParameters(Arrays.asList(new Object[] {"price", 1000}));
        assertFalse(factory.canReuse(new SizeView()));
        assertFalse(factory.canReuse(new ExternallyTimedWindowView("volume", 1000)));
        assertFalse(factory.canReuse(new ExternallyTimedWindowView("price", 999)));
        assertTrue(factory.canReuse(new ExternallyTimedWindowView("price", 1000000)));
    }

    public void testAttach() throws Exception
    {
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        factory.setViewParameters(Arrays.asList(new Object[] {"dummy", 20}));
        try
        {
            factory.attach(parentType, null, null);
            fail();
        }
        catch (ViewAttachException ex)
        {
            // expected
        }

        factory.setViewParameters(Arrays.asList(new Object[] {"string", 20}));
        try
        {
            factory.attach(parentType, null, null);
            fail();
        }
        catch (ViewAttachException ex)
        {
            // expected
        }

        factory.setViewParameters(Arrays.asList(new Object[] {"longPrimitive", 20}));
        factory.attach(parentType, null, null);

        assertSame(parentType, factory.getEventType());
    }
    
    private void tryInvalidParameter(Object[] param) throws Exception
    {
        try
        {
            ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
            factory.setViewParameters(Arrays.asList(new Object[] {param}));
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
        factory.setViewParameters(Arrays.asList(params));
        ExternallyTimedWindowView view = (ExternallyTimedWindowView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(fieldName, view.getTimestampFieldName());
        assertEquals(msec, view.getMillisecondsBeforeExpiry());
    }
}
