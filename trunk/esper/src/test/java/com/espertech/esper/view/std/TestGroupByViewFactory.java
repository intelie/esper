package com.espertech.esper.view.std;

import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import junit.framework.TestCase;

public class TestGroupByViewFactory extends TestCase
{
    private GroupByViewFactory factory;

    public void setUp()
    {
        factory = new GroupByViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"doublePrimitive"}, new String[] {"doublePrimitive"});
        tryParameter(new Object[] {"doublePrimitive", "longPrimitive"}, new String[] {"doublePrimitive", "longPrimitive"});

        tryInvalidParameter(new Object[] {"string", 1.1d});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {new String[] {}});
        tryInvalidParameter(new Object[] {new String[] {}, new String[] {}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {"string", "longPrimitive"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new SizeView(SupportStatementContextFactory.makeContext())));
        assertFalse(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodesBean("string"))));
        assertTrue(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodesBean("string", "longPrimitive"))));

        factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {SupportExprNodeFactory.makeIdentNodesBean("string", "longPrimitive")}));
        assertFalse(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodesBean("string"))));
        assertTrue(factory.canReuse(new GroupByView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodesBean("string", "longPrimitive"))));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {"intBoxed"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            GroupByViewFactory factory = new GroupByViewFactory();
            factory.setViewParameters(null, TestViewSupport.toExprListBean(params));
            factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
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
        factory.setViewParameters(null, TestViewSupport.toExprListBean(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
        GroupByView view = (GroupByView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNames[0], view.getCriteriaExpressions()[0].toExpressionString());
    }
}
