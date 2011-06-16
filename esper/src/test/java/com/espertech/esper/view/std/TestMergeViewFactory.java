package com.espertech.esper.view.std;

import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewFactoryContext;
import com.espertech.esper.view.ViewParameterException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestMergeViewFactory extends TestCase
{
    private MergeViewFactory factory;
    private List<ViewFactory> parents;
    private ViewFactoryContext viewFactoryContext = new ViewFactoryContext(SupportStatementContextFactory.makeContext(), 1, 0, null, null);

    public void setUp() throws Exception
    {
        factory = new MergeViewFactory();

        parents = new ArrayList<ViewFactory>();
        GroupByViewFactory groupByView = new GroupByViewFactory();
        groupByView.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(new Object[] {"symbol", "feed"}));
        groupByView.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        parents.add(groupByView);
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"symbol", "feed"}, new String[] {"symbol", "feed"});

        tryInvalidParameter(new Object[] {"symbol", 1.1d});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {new String[] {}});
        tryInvalidParameter(new Object[] {new String[] {}, new String[] {}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(new Object[] {"symbol", "feed"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, parents);
        assertFalse(factory.canReuse(new FirstElementView()));
        assertFalse(factory.canReuse(new MergeView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodesMD("symbol"), null)));
        assertTrue(factory.canReuse(new MergeView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodesMD("symbol", "feed"), null)));
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            MergeViewFactory factory = new MergeViewFactory();
            factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(params));
            factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, parents);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String[] fieldNames) throws Exception
    {
        MergeViewFactory factory = new MergeViewFactory();
        factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, parents);
        MergeView view = (MergeView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNames[0], view.getGroupFieldNames()[0].toExpressionString());
        if (fieldNames.length > 0)
        {
            assertEquals(fieldNames[1], view.getGroupFieldNames()[1].toExpressionString());
        }
    }
}
