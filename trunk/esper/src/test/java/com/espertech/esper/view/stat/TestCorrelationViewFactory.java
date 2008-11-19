package com.espertech.esper.view.stat;

import junit.framework.TestCase;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.std.SizeView;

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

        tryInvalidParameter(new Object[] {"a", 1.1d});
        tryInvalidParameter(new Object[] {1.1d, "a"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"a", "b", "c"});
        tryInvalidParameter(new Object[] {new String[] {"a", "b"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"a", "b"}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNode("a"), SupportExprNodeFactory.makeIdentNode("c"))));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNode("x"), SupportExprNodeFactory.makeIdentNode("b"))));
        assertTrue(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNode("a"), SupportExprNodeFactory.makeIdentNode("b"))));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"price", "volume"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.getName()));

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"xxx", "y"}));
            factory.attach(parentType, null, null, null);
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
            factory.setViewParameters(null, TestViewSupport.toExprList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldNameX, String fieldNameY) throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprList(params));
        CorrelationView view = (CorrelationView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNameX, view.getExpressionX());
        assertEquals(fieldNameY, view.getExpressionY());
    }
}
