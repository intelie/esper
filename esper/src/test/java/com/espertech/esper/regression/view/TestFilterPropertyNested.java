package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.bookexample.OrderBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

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

    public void testSimple()
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

    public void testWhere()
    {
        String[] fields = "reviewId".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        // try where in root
        String stmtText = "select reviewId from OrderEvent[books where title = 'Enders Game'][reviews] bookReviews order by reviewId asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}, {2}});
        listener.reset();

        // try where in different levels
        stmt.destroy();
        stmtText = "select reviewId from OrderEvent[books where title = 'Enders Game'][reviews where reviewId in (1, 10)] bookReviews order by reviewId asc";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}});
        listener.reset();

        // try where in combination
        stmt.destroy();
        stmtText = "select reviewId from OrderEvent[books as bc][reviews as rw where rw.reviewId in (1, 10) and bc.title = 'Enders Game'] bookReviews order by reviewId asc";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{1}});
        listener.reset();
        assertFalse(listener.isInvoked());
    }

    public void testColumnSelect()
    {
        //Object eventObject = TestFilterPropertySimple.makeEventOne();
        //EventBean event = SupportEventAdapterService.getService().adapterForBean(eventObject);
        //String xml = EventRendererProvider.renderXML("abc", event);
        //StringBuilder builder = new StringBuilder();
        //OutputValueRendererXMLString.xmlEncode(xml, builder, false);
        //System.out.println(xml);

        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        // columns supplied
        String stmtText = "select * from OrderEvent[select bookId, orderdetail.orderId as orderId from books][select reviewId from reviews] bookReviews order by reviewId asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        runAssertion();
        stmt.destroy();

        // stream wildcards identify fragments
        stmtText = "select orderFrag.orderdetail.orderId as orderId, bookFrag.bookId as bookId, reviewFrag.reviewId as reviewId " +
          "from OrderEvent[books as book][select myorder.* as orderFrag, book.* as bookFrag, review.* as reviewFrag from reviews as review] as myorder";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        runAssertion();
        stmt.destroy();

        // one event type dedicated as underlying
        stmtText = "select orderdetail.orderId as orderId, bookFrag.bookId as bookId, reviewFrag.reviewId as reviewId " +
          "from OrderEvent[books as book][select myorder.*, book.* as bookFrag, review.* as reviewFrag from reviews as review] as myorder";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        runAssertion();
        stmt.destroy();

        // wildcard unnamed as underlying
        stmtText = "select orderFrag.orderdetail.orderId as orderId, bookId, reviewId " +
          "from OrderEvent[select * from books][select myorder.* as orderFrag, reviewId from reviews as review] as myorder";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        runAssertion();
        stmt.destroy();

        // wildcard named as underlying
        stmtText = "select orderFrag.orderdetail.orderId as orderId, bookFrag.bookId as bookId, reviewFrag.reviewId as reviewId " +
          "from OrderEvent[select * from books as bookFrag][select myorder.* as orderFrag, review.* as reviewFrag from reviews as review] as myorder";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        runAssertion();
        stmt.destroy();

        // object model
        stmtText = "select orderFrag.orderdetail.orderId as orderId, bookId, reviewId " +
          "from OrderEvent[select * from books][select myorder.* as orderFrag, reviewId from reviews as review] as myorder";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());
        stmt = epService.getEPAdministrator().create(model, stmtText);
        stmt.addListener(listener);
        runAssertion();
        stmt.destroy();
    }

    public void testPatternSelect()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from pattern [" +
                "every r=OrderEvent[books][reviews] -> SupportBean(intPrimitive = r[0].reviewId)]");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertTrue(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", -1));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 201));
        assertTrue(listener.getAndClearIsInvoked());
    }

    public void testSubSelect()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select string from SupportBean s0 where " +
                "exists (select * from OrderEvent[books][reviews].std:unique(reviewId) where reviewId = s0.intPrimitive)");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertTrue(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", -1));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 201));
        assertTrue(listener.getAndClearIsInvoked());
    }

    public void testUnderlyingSelect()
    {
        String[] fields = "orderId,bookId,reviewId".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        String stmtText = "select orderdetail.orderId as orderId, bookFrag.bookId as bookId, reviewFrag.reviewId as reviewId " +
        //String stmtText = "select * " +
          "from OrderEvent[books as book][select myorder.*, book.* as bookFrag, review.* as reviewFrag from reviews as review] as myorder";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {
                {"PO200901", "10020", 1}, {"PO200901", "10020", 2}, {"PO200901", "10021", 10}});
        listener.reset();

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200904", "10031", 201}});
        listener.reset();
    }

    public void testInvalid()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        tryInvalid("select bookId from OrderEvent[select count(*) from books]",
                   "Expression in a property-selection may not utilize an aggregation function [select bookId from OrderEvent[select count(*) from books]]");

        tryInvalid("select bookId from OrderEvent[select bookId, (select abc from review.std:lastevent()) from books]",
                   "Expression in a property-selection may not utilize a subselect [select bookId from OrderEvent[select bookId, (select abc from review.std:lastevent()) from books]]");

        tryInvalid("select bookId from OrderEvent[select prev(1, bookId) from books]",
                   "Previous function cannot be used in this context [select bookId from OrderEvent[select prev(1, bookId) from books]]");

        tryInvalid("select bookId from OrderEvent[select * from books][select * from reviews]",
                   "A column name must be supplied for all but one stream if multiple streams are selected via the stream.* notation [select bookId from OrderEvent[select * from books][select * from reviews]]");

        tryInvalid("select bookId from OrderEvent[select abc from books][reviews]",
                   "Property named 'abc' is not valid in any stream [select bookId from OrderEvent[select abc from books][reviews]]");

        tryInvalid("select bookId from OrderEvent[books][reviews]",
                   "Error starting statement: Property named 'bookId' is not valid in any stream [select bookId from OrderEvent[books][reviews]]");

        tryInvalid("select orderId from OrderEvent[books]",
                   "Error starting statement: Property named 'orderId' is not valid in any stream [select orderId from OrderEvent[books]]");

        tryInvalid("select * from OrderEvent[books where abc=1]",
                   "Property named 'abc' is not valid in any stream [select * from OrderEvent[books where abc=1]]");

        tryInvalid("select * from OrderEvent[abc]",
                   "Property expression 'abc' against type 'OrderEvent' does not return a fragmentable property value [select * from OrderEvent[abc]]");
    }

    private void runAssertion()
    {
        String[] fields = "orderId,bookId,reviewId".split(",");

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {
                {"PO200901", "10020", 1}, {"PO200901", "10020", 2}, {"PO200901", "10021", 10}});
        listener.reset();

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200904", "10031", 201}});
        listener.reset();
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