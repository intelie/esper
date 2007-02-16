package net.esper.view.stat;

import junit.framework.TestCase;
import net.esper.view.ViewAttachException;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewFieldEnum;
import net.esper.view.std.SizeView;
import net.esper.event.EventType;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportViewContextFactory;

import java.util.Arrays;

public class TestWeightedAverageViewFactory extends TestCase
{
    private WeightedAverageViewFactory factory;

    public void setUp()
    {
        factory = new WeightedAverageViewFactory();
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

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(Arrays.asList(new Object[] {"price", "volume"}));
        factory.attach(parentType, SupportViewContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName()));

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

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView(SupportViewContextFactory.makeContext())));
        assertFalse(factory.canReuse(new WeightedAverageView(SupportViewContextFactory.makeContext(), "a", "c")));
        assertFalse(factory.canReuse(new WeightedAverageView(SupportViewContextFactory.makeContext(), "x", "b")));
        assertTrue(factory.canReuse(new WeightedAverageView(SupportViewContextFactory.makeContext(), "a", "b")));
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

    private void tryParameter(Object[] params, String fieldNameX, String fieldNameW) throws Exception
    {
        factory.setViewParameters(Arrays.asList(params));
        WeightedAverageView view = (WeightedAverageView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(fieldNameX, view.getFieldNameX());
        assertEquals(fieldNameW, view.getFieldNameWeight());
    }
}
