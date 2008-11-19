package com.espertech.esper.view.ext;

import junit.framework.TestCase;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.view.std.SizeView;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.epl.SupportExprNodeFactory;

public class TestSortWindowViewFactory extends TestCase
{
    private SortWindowViewFactory factory;

    public void setUp()
    {
        factory = new SortWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price", true, 100},
                     new String[] {"price"}, new boolean[] {true}, 100);

        tryParameter(new Object[] {new Object[] {"price", true, "volume", false}, 100},
                     new String[] {"price", "volume"}, new boolean[] {true, false}, 100);

        tryInvalidParameter(new Object[] {new Object[] {"price", "a", "volume", false}, 100});
        tryInvalidParameter(new Object[] {});
        tryInvalidParameter(new Object[] {"price", "x", 100});
        tryInvalidParameter(new Object[] {1.1, "x", 100});
        tryInvalidParameter(new Object[] {"price", true, "x"});
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"price", true, 100}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"xxx", true, 100}));
            factory.attach(parentType, null, null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected;
        }
    }

    public void testCanReuse() throws Exception
    {
        StatementContext context = SupportStatementContextFactory.makeContext();

        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"price", true, 100}));
        assertFalse(factory.canReuse(new SizeView(context)));
        assertTrue(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodes("price"), new boolean[] {true}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodes("volume"), new boolean[] {true}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodes("price"), new boolean[] {false}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodes("price"), new boolean[] {true}, 99, null, false)));

        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {new Object[] {"price", true, "volume", false}, 100}));
        assertTrue(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodes("price", "volume"), new boolean[] {true, false}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodes("price", "xxx"), new boolean[] {true, false}, 100, null, false)));
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String[] fieldNames, boolean[] ascInd, int size) throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprList(params));
        SortWindowView view = (SortWindowView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(size, view.getSortWindowSize());
        ArrayAssertionUtil.assertEqualsExactOrder(fieldNames, view.getSortCriteriaExpressions());
        ArrayAssertionUtil.assertEqualsExactOrder(ascInd, view.getIsDescendingValues());
    }
}
