package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.bookexample.OrderBean;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestFilterPropertyNested extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testMultiplicitySimple()
    {
        String[] fields = "reviewId".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        String stmtText = "select reviewId from OrderEvent[books][reviews] bookReviews order by reviewId asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}, {2}, {10}});
        listener.reset();

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{201}});
        listener.reset();
    }

    public void testMultiplicityColumnSelect()
    {
        String[] fields = "reviewId".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        // Resolve each property against each fragment+source: multiple matches are an error
        String stmtText = "select * from OrderEvent[books as book][reviews:orderDetail.orderId as orderId, book.bookId as bookId, * ] bookReviews order by reviewId asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}, {2}, {10}});
        listener.reset();

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{201}});
        listener.reset();
    }
}