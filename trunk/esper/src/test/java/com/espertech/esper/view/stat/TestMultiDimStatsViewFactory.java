package com.espertech.esper.view.stat;

import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.stat.olap.Cube;
import com.espertech.esper.view.std.SizeView;
import junit.framework.TestCase;

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

        tryParameter(new Object[] {new String[] {"stddev"}, "price", "volume", "price"},
                     new String[] {"stddev"}, "price", "volume", "price", null);

        tryParameter(new Object[] {new String[] {"stddev"}, "price", "volume", "price", "volume"},
                     new String[] {"stddev"}, "price", "volume", "price", "volume");

        tryInvalidParameter(new Object[] {new String[] {"stdev"}, "symbol"});
        tryInvalidParameter(new Object[] {1.1d, "symbol"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"symbol", "symbol", "symbol"});
        tryInvalidParameter(new Object[] {new String[] {"symbol", "symbol"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {new String[] {"stddev"}, "price", "volume"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new MultiDimStatsView(SupportStatementContextFactory.makeContext(),
                new String[] {"stddev", "average"}, SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"), null, null)));
        assertTrue(factory.canReuse(new MultiDimStatsView(SupportStatementContextFactory.makeContext(),
                new String[] {"stddev"}, SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"), null, null)));

        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {new String[] {"stddev"}, "price", "volume", "price", "volume"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new MultiDimStatsView(SupportStatementContextFactory.makeContext(),
                new String[] {"stddev"}, SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("price"))));
        assertTrue(factory.canReuse(new MultiDimStatsView(SupportStatementContextFactory.makeContext(),
                new String[] {"stddev"}, SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"))));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {new String[] {"stddev"}, "price", "volume"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(Cube.class, factory.getEventType().getPropertyType(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName()));

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {new String[] {"stddev"}, "symbol", "symbol"}));
            factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected;
        }
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

    private void tryParameter(Object[] params, String[] derived, String measureField, String columnField, String rowField, String pageField) throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListMD(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        MultiDimStatsView view = (MultiDimStatsView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(measureField, view.getMeasureField().toExpressionString());
        if (pageField != null)
        {
            assertEquals(pageField, view.getPageField().toExpressionString());
        }
        else
        {
            assertNull(view.getPageField());
        }
        if (rowField != null)
        {
            assertEquals(rowField, view.getRowField().toExpressionString());
        }
        else
        {
            assertNull(view.getRowField());
        }
        assertEquals(columnField, view.getColumnField().toExpressionString());
        ArrayAssertionUtil.assertEqualsExactOrder(view.getDerivedMeasures(), derived);
    }
}
