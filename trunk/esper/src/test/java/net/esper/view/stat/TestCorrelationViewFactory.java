package net.esper.view.stat;

import junit.framework.TestCase;
import net.esper.event.EventType;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.view.ViewFieldEnum;
import net.esper.view.ViewAttachException;
import net.esper.view.ViewParameterException;
import net.esper.view.std.SizeView;

import java.util.Arrays;

public class TestCorrelationViewFactory extends TestCase
{
    private CorrelationViewFactory factory;

    public void setUp()
    {
        factory = new CorrelationViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price", "volume"}, "price", "volume");

        tryInvalidParameter(new Object[] {"a", 1.1d});
        tryInvalidParameter(new Object[] {1.1d, "a"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"a", "b", "c"});
        tryInvalidParameter(new Object[] {new String[] {"a", "b"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, Arrays.asList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), "a", "c")));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), "x", "b")));
        assertTrue(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), "a", "b")));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, Arrays.asList(new Object[] {"price", "volume"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.getName()));

        try
        {
            factory.setViewParameters(null, Arrays.asList(new Object[] {"xxx", "y"}));
            factory.attach(parentType, null, null, null);
            fail();
        }
        catch (ViewAttachException ex)
        {
            // expected;
        }
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            factory.setViewParameters(null, Arrays.asList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldNameX, String fieldNameY) throws Exception
    {
        factory.setViewParameters(null, Arrays.asList(params));
        CorrelationView view = (CorrelationView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNameX, view.getFieldNameX());
        assertEquals(fieldNameY, view.getFieldNameY());
    }
}