package com.espertech.esper.view.std;

import junit.framework.TestCase;

import java.util.Arrays;

import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewAttachException;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.event.EventType;

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
        factory.setViewParameters(null, Arrays.asList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), new String[] {"a"})));
        assertTrue(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), new String[] {"a", "b"})));

        factory.setViewParameters(null, Arrays.asList(new Object[] {new String[] {"a", "b"}}));
        assertFalse(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), new String[] {"a"})));
        assertTrue(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), new String[] {"a", "b"})));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, Arrays.asList(new Object[] {"price"}));
        factory.attach(parentType, null, null, null);

        try
        {
            factory.setViewParameters(null, Arrays.asList(new Object[] {"xxx"}));
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
            factory.setViewParameters(null, Arrays.asList(params));
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
        factory.setViewParameters(null, Arrays.asList(params));
        GroupByView view = (GroupByView) factory.makeView(SupportStatementContextFactory.makeContext());
        ArrayAssertionUtil.assertEqualsExactOrder(fieldNames, view.getGroupFieldNames());
    }
}