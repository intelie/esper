package com.espertech.esper.view.stat;

import junit.framework.TestCase;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.std.FirstElementView;

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

        tryInvalidParameter(new Object[] {"symbol", 1.1d});
        tryInvalidParameter(new Object[] {1.1d, "symbol"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"symbol", "symbol", "symbol"});
        tryInvalidParameter(new Object[] {new String[] {"symbol", "feed"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {"price", "volume"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new FirstElementView()));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("price"))));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("feed"), SupportExprNodeFactory.makeIdentNodeMD("volume"))));
        assertTrue(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"))));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {"price", "volume"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.getName()));

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListMD(new Object[] {"symbol", "volume"}));
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
        CorrelationView view = (CorrelationView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNameX, view.getExpressionX().toExpressionString());
        assertEquals(fieldNameY, view.getExpressionY().toExpressionString());
    }
}
