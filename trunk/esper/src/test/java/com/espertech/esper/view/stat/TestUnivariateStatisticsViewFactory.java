package com.espertech.esper.view.stat;

import junit.framework.TestCase;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.view.ViewAttachException;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.std.SizeView;

import java.util.Arrays;

public class TestUnivariateStatisticsViewFactory extends TestCase
{
    private UnivariateStatisticsViewFactory factory;

    public void setUp()
    {
        factory = new UnivariateStatisticsViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price"}, "price");

        tryInvalidParameter(new Object[] {});
        tryInvalidParameter(new Object[] {1.1d, "a"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"a", "b", "c"});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"a"}));
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new UnivariateStatisticsView(SupportStatementContextFactory.makeContext(), "x")));
        assertTrue(factory.canReuse(new UnivariateStatisticsView(SupportStatementContextFactory.makeContext(), "a")));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"price"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(double.class, factory.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName()));

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprList(new Object[] {"xxx"}));
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
            factory.setViewParameters(null, TestViewSupport.toExprList(params));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldName) throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprList(params));
        UnivariateStatisticsView view = (UnivariateStatisticsView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldName, view.getFieldName());
    }
}
