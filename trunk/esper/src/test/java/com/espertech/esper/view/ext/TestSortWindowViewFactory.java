package com.espertech.esper.view.ext;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.std.FirstElementView;
import junit.framework.TestCase;

public class TestSortWindowViewFactory extends TestCase
{
    private SortWindowViewFactory factory;

    public void setUp()
    {
        factory = new SortWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {100, "price", "volume"},
                     new String[] {"price", "volume"}, 100);

        tryInvalidParameter(new Object[] {"price", "symbol", "volume"});
        tryInvalidParameter(new Object[] {});
        tryInvalidParameter(new Object[] {100, "price", 100});
        tryInvalidParameter(new Object[] {100, 100});
        tryInvalidParameter(new Object[] {100, "price", true});
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {100, "price"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {true, "price"}));
            factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
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

        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {100, "price"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new FirstElementView()));
        assertTrue(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodesMD("price"), new boolean[] {false}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodesMD("volume"), new boolean[] {true}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodesMD("price"), new boolean[] {false}, 99, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodesMD("symbol"), new boolean[] {false}, 100, null, false)));

        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {100, "price", "volume"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertTrue(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodesMD("price", "volume"), new boolean[] {false, false}, 100, null, false)));
        assertFalse(factory.canReuse(new SortWindowView(factory, SupportExprNodeFactory.makeIdentNodesMD("price", "symbol"), new boolean[] {true, false}, 100, null, false)));
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListMD(params));
            factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String[] fieldNames, int size) throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListMD(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        SortWindowView view = (SortWindowView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(size, view.getSortWindowSize());
        assertEquals(fieldNames[0], view.getSortCriteriaExpressions()[0].toExpressionString());
        if (fieldNames.length > 0)
        {
            assertEquals(fieldNames[1], view.getSortCriteriaExpressions()[1].toExpressionString());
        }
    }
}
