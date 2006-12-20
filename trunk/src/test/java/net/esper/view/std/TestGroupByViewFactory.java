package net.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.event.EventType;

public class TestGroupByViewFactory extends TestCase
{
    private GroupByViewFactory factory;

    public void setUp()
    {
        factory = new GroupByViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price"}, new String[] {"price"});
        tryParameter(new Object[] {"price", "volume"}, new String[] {"price", "volume"});
        tryParameter(new Object[] {new String[] {"price", "volume"}}, new String[] {"price", "volume"});
        tryParameter(new Object[] {new String[] {"price"}}, new String[] {"price"});

        tryInvalidParameter(new Object[] {"a", 1.1d});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {new String[] {}});
        tryInvalidParameter(new Object[] {new String[] {}, new String[] {}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(Arrays.asList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView()));
        assertFalse(factory.canReuse(new GroupByView(new String[] {"a"})));
        assertTrue(factory.canReuse(new GroupByView(new String[] {"a", "b"})));

        factory.setViewParameters(Arrays.asList(new Object[] {new String[] {"a", "b"}}));
        assertFalse(factory.canReuse(new GroupByView(new String[] {"a"})));
        assertTrue(factory.canReuse(new GroupByView(new String[] {"a", "b"})));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(Arrays.asList(new Object[] {"price"}));
        factory.attach(parentType, null, null, null);

        try
        {
            factory.setViewParameters(Arrays.asList(new Object[] {"xxx"}));
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
            GroupByViewFactory factory = new GroupByViewFactory();
            factory.setViewParameters(Arrays.asList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String[] fieldNames) throws Exception
    {
        GroupByViewFactory factory = new GroupByViewFactory();
        factory.setViewParameters(Arrays.asList(params));
        GroupByView view = (GroupByView) factory.makeView(SupportViewContextFactory.makeContext());
        ArrayAssertionUtil.assertEqualsExactOrder(fieldNames, view.getGroupFieldNames());
    }
}
