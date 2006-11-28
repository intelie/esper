package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import junit.framework.TestCase;

public class TestInsertInto extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener feedListener;
    private SupportUpdateListener resultListenerDelta;
    private SupportUpdateListener resultListenerProduct;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        feedListener = new SupportUpdateListener();
        resultListenerDelta = new SupportUpdateListener();
        resultListenerProduct = new SupportUpdateListener();

        // Use external clocking for the test
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testVariantOne()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";

        runAsserts(stmtText);
    }
    
    public void testVariantOneWildcard()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
        "select * from " + SupportBean.class.getName() + ".win:length(100)";

        try{
        	epService.getEPAdministrator().createEQL(stmtText);
        	fail();
        }
        catch(EPStatementException ex)
        {
        	// Expected
        }
    }

    public void testVariantOneJoin()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100) as s0," +
                                SupportBean_A.class.getName() + ".win:length(100) as s1 " +
                      " where s0.string = s1.id";

        runAsserts(stmtText);
    }
    
    public void testVariantOneJoinWildcard()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
        "select * " +
        "from " + SupportBean.class.getName() + ".win:length(100) as s0," +
                  SupportBean_A.class.getName() + ".win:length(100) as s1 " +
        " where s0.string = s1.id";
        
        try{
        	epService.getEPAdministrator().createEQL(stmtText);
        	fail();
        }
        catch(EPStatementException ex)
        {
        	// Expected
        }
    }

    public void testVariantTwo()
    {
        String stmtText = "insert into Event_1 " +
                      "select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";

        runAsserts(stmtText);
    }
    
    public void testVariantTwoWildcard() throws InterruptedException
    {
        String stmtText = "insert into event1 select * from " + SupportBean.class.getName() + ".win:length(100)";
        String otherText = "select * from event1.win:length(10)";
        
        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(otherText);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);
        
        sendEvent(10, 11);
        assertTrue(listenerOne.getAndClearIsInvoked());
        assertEquals(1, listenerOne.getLastNewData().length);
        assertEquals(10, listenerOne.getLastNewData()[0].get("intPrimitive"));
        assertEquals(11, listenerOne.getLastNewData()[0].get("intBoxed"));
        assertEquals(18, listenerOne.getLastNewData()[0].getEventType().getPropertyNames().length);
 
        assertTrue(listenerTwo.getAndClearIsInvoked());
        assertEquals(1, listenerTwo.getLastNewData().length);
        assertEquals(10, listenerTwo.getLastNewData()[0].get("intPrimitive"));
        assertEquals(11, listenerTwo.getLastNewData()[0].get("intBoxed"));
        assertEquals(18, listenerTwo.getLastNewData()[0].getEventType().getPropertyNames().length);
    }

    public void testVariantTwoJoin()
    {
        String stmtText = "insert into Event_1 " +
                      "select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
                        "from " + SupportBean.class.getName() + ".win:length(100) as s0," +
                                  SupportBean_A.class.getName() + ".win:length(100) as s1 " +
                        " where s0.string = s1.id";

        runAsserts(stmtText);
    }
    
    public void testVariantTwoJoinWildcard()
    {
        String textOne = "insert into event2 select * " +
        		          "from " + SupportBean.class.getName() + ".win:length(100) as s0, " +
        		          SupportBean_A.class.getName() + ".win:length(5) as s1 " + 
        		          "where s0.string = s1.id";
        String textTwo = "select * from event2.win:length(10)";
        
        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(textTwo);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);
        
        // send event for joins to match on
        epService.getEPRuntime().sendEvent(new SupportBean_A("myId"));
        
        sendEvent(10, 11);
        assertTrue(listenerOne.getAndClearIsInvoked());
        assertEquals(1, listenerOne.getLastNewData().length);
        assertEquals(2, listenerOne.getLastNewData()[0].getEventType().getPropertyNames().length);
        assertTrue(listenerOne.getLastNewData()[0].getEventType().isProperty("s0"));
        assertTrue(listenerOne.getLastNewData()[0].getEventType().isProperty("s1"));
        
        assertTrue(listenerTwo.getAndClearIsInvoked());
        assertEquals(1, listenerTwo.getLastNewData().length);
        assertEquals(2, listenerTwo.getLastNewData()[0].getEventType().getPropertyNames().length);
        assertTrue(listenerTwo.getLastNewData()[0].getEventType().isProperty("s0"));
        assertTrue(listenerTwo.getLastNewData()[0].getEventType().isProperty("s1"));
    }

    public void testInvalidStreamUsed()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";
        epService.getEPAdministrator().createEQL(stmtText);

        try
        {
            stmtText = "insert into Event_1 (delta) " +
                      "select intPrimitive - intBoxed as deltaTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            // expected
            assertEquals("Error starting view: Event type named 'Event_1' has already been declared with differing type information [insert into Event_1 (delta) select intPrimitive - intBoxed as deltaTag from net.esper.support.bean.SupportBean.win:length(100)]", ex.getMessage());
        }
    }

    public void testWithOutputLimitAndSort()
    {
        // NOTICE: we are inserting the RSTREAM (removed events)
        String stmtText = "insert rstream into StockTicks(mySymbol, myPrice) " +
                          "select symbol, price from " + SupportMarketDataBean.class.getName() + ".win:time(60) " +
                          "output every 5 seconds " +
                          "order by symbol asc";
        epService.getEPAdministrator().createEQL(stmtText);

        stmtText = "select mySymbol, sum(myPrice) as pricesum from StockTicks.win:length(100)";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(feedListener);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        sendEvent("IBM", 50);
        sendEvent("CSC", 10);
        sendEvent("GE", 20);
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10 * 1000));
        sendEvent("DEF", 100);
        sendEvent("ABC", 11);
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(20 * 1000));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(30 * 1000));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(40 * 1000));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(50 * 1000));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(55 * 1000));

        assertFalse(feedListener.isInvoked());
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(60 * 1000));

        assertTrue(feedListener.isInvoked());
        assertEquals(3, feedListener.getNewDataList().size());
        assertEquals("CSC", feedListener.getNewDataList().get(0)[0].get("mySymbol"));
        assertEquals(10.0, feedListener.getNewDataList().get(0)[0].get("pricesum"));
        assertEquals("GE", feedListener.getNewDataList().get(1)[0].get("mySymbol"));
        assertEquals(30.0, feedListener.getNewDataList().get(1)[0].get("pricesum"));
        assertEquals("IBM", feedListener.getNewDataList().get(2)[0].get("mySymbol"));
        assertEquals(80.0, feedListener.getNewDataList().get(2)[0].get("pricesum"));
        feedListener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(65 * 1000));
        assertFalse(feedListener.isInvoked());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(70 * 1000));
        assertEquals("ABC", feedListener.getNewDataList().get(0)[0].get("mySymbol"));
        assertEquals(91.0, feedListener.getNewDataList().get(0)[0].get("pricesum"));
        assertEquals("DEF", feedListener.getNewDataList().get(1)[0].get("mySymbol"));
        assertEquals(191.0, feedListener.getNewDataList().get(1)[0].get("pricesum"));
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, null, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void runAsserts(String stmtText)
    {
        // Attach listener to feed
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(feedListener);

        // send event for joins to match on
        epService.getEPRuntime().sendEvent(new SupportBean_A("myId"));

        // Attach delta statement to statement and add listener
        stmtText = "select min(delta) as minD, max(delta) as maxD " +
                   "from Event_1.win:time(60)";
        stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(resultListenerDelta);

        // Attach prodict statement to statement and add listener
        stmtText = "select min(product) as minP, max(product) as maxP " +
                   "from Event_1.win:time(60)";
        stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(resultListenerProduct);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0)); // Set the time to 0 seconds

        // send events
        sendEvent(20, 10);
        assertReceivedFeed(10, 200);
        assertReceivedMinMax(10, 10, 200, 200);

        sendEvent(50, 25);
        assertReceivedFeed(25, 25 * 50);
        assertReceivedMinMax(10, 25, 200, 1250);

        sendEvent(5, 2);
        assertReceivedFeed(3, 2 * 5);
        assertReceivedMinMax(3, 25, 10, 1250);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10 * 1000)); // Set the time to 10 seconds

        sendEvent(13, 1);
        assertReceivedFeed(12, 13);
        assertReceivedMinMax(3, 25, 10, 1250);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(61 * 1000)); // Set the time to 61 seconds
        assertReceivedMinMax(12, 12, 13, 13);
    }

    private void assertReceivedMinMax(int minDelta, int maxDelta, int minProduct, int maxProduct)
    {
        assertEquals(1, resultListenerDelta.getNewDataList().size());
        assertEquals(1, resultListenerDelta.getLastNewData().length);
        assertEquals(1, resultListenerProduct.getNewDataList().size());
        assertEquals(1, resultListenerProduct.getLastNewData().length);
        assertEquals(minDelta, resultListenerDelta.getLastNewData()[0].get("minD"));
        assertEquals(maxDelta, resultListenerDelta.getLastNewData()[0].get("maxD"));
        assertEquals(minProduct, resultListenerProduct.getLastNewData()[0].get("minP"));
        assertEquals(maxProduct, resultListenerProduct.getLastNewData()[0].get("maxP"));
        resultListenerDelta.reset();
        resultListenerProduct.reset();
    }

    private void assertReceivedFeed(int delta, int product)
    {
        assertEquals(1, feedListener.getNewDataList().size());
        assertEquals(1, feedListener.getLastNewData().length);
        assertEquals(delta, feedListener.getLastNewData()[0].get("delta"));
        assertEquals(product, feedListener.getLastNewData()[0].get("product"));
        feedListener.reset();
    }

    private void sendEvent(int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString("myId");
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
