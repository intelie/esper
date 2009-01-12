package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.util.XMLEventRenderer;
import com.espertech.esper.client.util.EventRendererProvider;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.bookexample.OrderBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.event.util.XMLRendererImpl;
import com.espertech.esper.event.util.OutputValueRendererXMLString;

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

    public void testIt()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String stmt = "select * from SupportBean(string='MSFT').win:length(2) having prev(0,intPrimitive) > 1.03*prev(count(*) - 1,intPrimitive)";
        epService.getEPAdministrator().createEPL(stmt);
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

    public void testMultiplicityWhere()
    {
        String[] fields = "reviewId".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        String stmtText = "select reviewId from OrderEvent[books where title = 'Ender\\'s Game'][reviews] bookReviews order by reviewId asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}, {2}});
        listener.reset();

        assertFalse(listener.isInvoked());
    }

    // select * from OrderEvent[books as book][reviews as review]
    // ==> Underlying OrderEvent + book property + review property
    //     bad: resulting event as the underlying is inconsistent

    // select * from OrderEvent[books][reviews]
    // ==> Underlying is review
    //     good: consistent         bad: where to get book and order properties

    // select orderId, bookId from OrderEvent[books][reviews]
    // (1) Copy selected properties to the result type or all properties if so desired "reviews:OrderEvent.*,books.*"
    // (2) Make the higher fragments a property of the result type: "reviews: OrderEvent as order, books as book"
    // (4) Inspect all expressions in the EPL and dynamically decide which properties to copy and replace the expression
    // (5) New event type: hierarchical event type, just has an int-pointer to the inner property index of the individual types making up the hierarchy.
    // ==> copy properties or make event a property of another event and/or make one of them an underlying event

    // Syntax:
    //      [property : property as name]
    //     select * from OrderEvent[select orderId, bookId from book[select * from review]]
    //      or
    //     select * from OrderEvent(select * from (OrderEvent.book (se[select * from review]]
    // ==> OrderEvent[book: orderId, bookId][review]

    public void testMultiplicityColumnSelect()
    {
        //Object eventObject = TestFilterPropertySimple.makeEventOne();
        //EventBean event = SupportEventAdapterService.getService().adapterForBean(eventObject);
        //String xml = EventRendererProvider.renderXML("abc", event);
        //StringBuilder builder = new StringBuilder();
        //OutputValueRendererXMLString.xmlEncode(xml, builder, false);
        //System.out.println(xml);

        String[] fields = "orderId, bookId, reviewId".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        // Resolve each property against each source and then against each fragment: multiple matches are an error.
        // Create wrapper type: Implicitly make the last fragment the underlying type, add the properties to the wrapper as listed.
        String stmtText = "select * from OrderEvent[books: bookId, orderDetail.orderId as orderId][reviews:*] bookReviews order by reviewId asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}, {2}, {10}});
        listener.reset();

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{201}});
        listener.reset();
    }

    public void testInvalid()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        tryInvalid("select * from OrderEvent[books where abc=1]",
                   "");

        tryInvalid("select * from OrderEvent[abc]",
                   "Property expression 'abc' against type 'OrderEvent' does not return a fragmentable property value [select * from OrderEvent[abc]]");
    }

    private void tryInvalid(String text, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(text);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }
}