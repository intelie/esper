package net.esper.view.stat;

import junit.framework.TestCase;
import net.esper.event.EventType;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.view.ViewFieldEnum;
import net.esper.view.ViewAttachException;
import net.esper.view.ViewParameterException;
import net.esper.view.std.SizeView;

import java.util.Arrays;

public class TestRegressionLinestViewFactory extends TestCase
{
    private RegressionLinestViewFactory factory;

    public void setUp()
    {
        factory = new RegressionLinestViewFactory();
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
        factory.setViewParameters(Arrays.asList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView()));
        assertFalse(factory.canReuse(new RegressionLinestView("a", "c")));
        assertFalse(factory.canReuse(new RegressionLinestView("x", "b")));
        assertTrue(factory.canReuse(new RegressionLinestView("a", "b")));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(Arrays.asList(new Object[] {"price", "volume"}));
        factory.attach(parentType, SupportViewContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.REGRESSION__SLOPE.getName()));

        try
        {
            factory.setViewParameters(Arrays.asList(new Object[] {"xxx", "y"}));
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
            factory.setViewParameters(Arrays.asList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldNameX, String fieldNameY) throws Exception
    {
        factory.setViewParameters(Arrays.asList(params));
        RegressionLinestView view = (RegressionLinestView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(fieldNameX, view.getFieldNameX());
        assertEquals(fieldNameY, view.getFieldNameY());
    }
}
