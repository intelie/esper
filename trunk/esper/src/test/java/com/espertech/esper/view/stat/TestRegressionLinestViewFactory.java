package com.espertech.esper.view.stat;

import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.view.std.FirstElementView;
import com.espertech.esper.epl.expression.ExprNode;
import junit.framework.TestCase;

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

        tryInvalidParameter(new Object[] {"symbol", 1.1d});
        tryInvalidParameter(new Object[] {1.1d, "symbol"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"price", "volume", "symbol"});
        tryInvalidParameter(new Object[] {new String[] {"a", "b"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {"price", "volume"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new FirstElementView()));
        assertFalse(factory.canReuse(new RegressionLinestView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("price"))));
        assertFalse(factory.canReuse(new RegressionLinestView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("price"))));
        assertTrue(factory.canReuse(new RegressionLinestView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"))));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null,
                Arrays.asList(new ExprNode[] {SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("price")}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.REGRESSION__SLOPE.getName()));

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {"symbol", "symbol"}));
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

    private void tryParameter(Object[] params, String fieldNameX, String fieldNameY) throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListMD(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        RegressionLinestView view = (RegressionLinestView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNameX, view.getExpressionX().toExpressionString());
        assertEquals(fieldNameY, view.getExpressionY().toExpressionString());
    }
}
