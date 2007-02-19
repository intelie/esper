package net.esper.view.stat;

import junit.framework.TestCase;
import net.esper.event.EventType;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.view.ViewFieldEnum;
import net.esper.view.ViewAttachException;
import net.esper.view.stat.olap.Cube;
import net.esper.view.ViewParameterException;
import net.esper.view.std.SizeView;

import java.util.Arrays;

public class TestMultiDimStatsViewFactory extends TestCase
{
    private MultiDimStatsViewFactory factory;

    public void setUp()
    {
        factory = new MultiDimStatsViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {new String[] {"stddev"}, "price", "volume"},
                     new String[] {"stddev"}, "price", "volume", null, null);

        tryParameter(new Object[] {new String[] {"stddev"}, "price", "volume", "a"},
                     new String[] {"stddev"}, "price", "volume", "a", null);

        tryParameter(new Object[] {new String[] {"stddev"}, "price", "volume", "a", "b"},
                     new String[] {"stddev"}, "price", "volume", "a", "b");

        tryInvalidParameter(new Object[] {new String[] {"stdev"}, "a"});
        tryInvalidParameter(new Object[] {1.1d, "a"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"a", "b", "c"});
        tryInvalidParameter(new Object[] {new String[] {"a", "b"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {new String[] {"stddev"}, "price", "volume"}));
        assertFalse(factory.canReuse(new SizeView(SupportViewContextFactory.makeContext())));
        assertFalse(factory.canReuse(new MultiDimStatsView(SupportViewContextFactory.makeContext(),
                new String[] {"stddev", "average"}, "price", "volume", null, null)));
        assertTrue(factory.canReuse(new MultiDimStatsView(SupportViewContextFactory.makeContext(),
                new String[] {"stddev"}, "price", "volume", null, null)));

        factory.setViewParameters(Arrays.asList(new Object[] {new String[] {"stddev"}, "price", "volume", "a", "b"}));
        assertFalse(factory.canReuse(new SizeView(SupportViewContextFactory.makeContext())));
        assertFalse(factory.canReuse(new MultiDimStatsView(SupportViewContextFactory.makeContext(),
                new String[] {"stddev"}, "price", "volume", "x", "b")));
        assertTrue(factory.canReuse(new MultiDimStatsView(SupportViewContextFactory.makeContext(),
                new String[] {"stddev"}, "price", "volume", "a", "b")));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(Arrays.asList(new Object[] {new String[] {"stddev"}, "price", "volume"}));
        factory.attach(parentType, SupportViewContextFactory.makeContext(), null, null);
        assertEquals(Cube.class, factory.getEventType().getPropertyType(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName()));

        try
        {
            factory.setViewParameters(Arrays.asList(new Object[] {new String[] {"stddev"}, "xxx", "y"}));
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

    private void tryParameter(Object[] params, String[] derived, String measureField, String columnField, String rowField, String pageField) throws Exception
    {
        factory.setViewParameters(Arrays.asList(params));
        MultiDimStatsView view = (MultiDimStatsView) factory.makeView(SupportViewContextFactory.makeContext());
        assertEquals(measureField, view.getMeasureField());
        assertEquals(pageField, view.getPageField());
        assertEquals(rowField, view.getRowField());
        assertEquals(columnField, view.getColumnField());
        ArrayAssertionUtil.assertEqualsExactOrder(view.getDerivedMeasures(), derived);
    }
}
