package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.client.soda.*;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.eql.SupportStaticMethodLib;
import net.esper.util.SerializableObjectCopier;
import net.esper.core.InsertIntoLatchSpin;

import java.util.Map;

public class TestInsertInto extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener feedListener;
    private SupportUpdateListener resultListenerDelta;
    private SupportUpdateListener resultListenerProduct;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        feedListener = new SupportUpdateListener();
        resultListenerDelta = new SupportUpdateListener();
        resultListenerProduct = new SupportUpdateListener();

        // Use external clocking for the test
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testVariantRStreamOMToStmt() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setInsertInto(InsertIntoClause.create("Event_1", new String[0], StreamSelector.RSTREAM_ONLY));
        model.setSelectClause(SelectClause.create().add("intPrimitive", "intBoxed"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName())));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        EPStatement stmt = epService.getEPAdministrator().create(model, "s1");

        String eql = "insert rstream into Event_1 " +
                      "select intPrimitive, intBoxed " +
                      "from " + SupportBean.class.getName();
        assertEquals(eql, model.toEQL());
        assertEquals(eql, stmt.getText());

        EPStatementObjectModel modelTwo = epService.getEPAdministrator().compileEQL(model.toEQL());
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(eql, modelTwo.toEQL());
    }

    public void testVariantOneOMToStmt() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setInsertInto(InsertIntoClause.create("Event_1", "delta", "product"));
        model.setSelectClause(SelectClause.create().add(Expressions.minus("intPrimitive", "intBoxed"), "deltaTag")
                .add(Expressions.multiply("intPrimitive", "intBoxed"), "productTag"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName()).addView(View.create("win", "length", 100))));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        EPStatement stmt = runAsserts(null, model);

        String eql = "insert into Event_1(delta, product) " +
                      "select (intPrimitive - intBoxed) as deltaTag, (intPrimitive * intBoxed) as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";
        assertEquals(eql, model.toEQL());
        assertEquals(eql, stmt.getText());
    }

    public void testVariantOneEQLToOMStmt() throws Exception
    {
        String eql = "insert into Event_1(delta, product) " +
                      "select (intPrimitive - intBoxed) as deltaTag, (intPrimitive * intBoxed) as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(eql);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(eql, model.toEQL());

        EPStatement stmt = runAsserts(null, model);
        assertEquals(eql, stmt.getText());
    }

    public void testVariantOne()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";

        runAsserts(stmtText, null);
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

        runAsserts(stmtText, null);
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

        runAsserts(stmtText, null);
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
        
        SupportBean event = sendEvent(10, 11);
        assertTrue(listenerOne.getAndClearIsInvoked());
        assertEquals(1, listenerOne.getLastNewData().length);
        assertEquals(10, listenerOne.getLastNewData()[0].get("intPrimitive"));
        assertEquals(11, listenerOne.getLastNewData()[0].get("intBoxed"));
        assertEquals(18, listenerOne.getLastNewData()[0].getEventType().getPropertyNames().length);
        assertSame(event, listenerOne.getLastNewData()[0].getUnderlying());
 
        assertTrue(listenerTwo.getAndClearIsInvoked());
        assertEquals(1, listenerTwo.getLastNewData().length);
        assertEquals(10, listenerTwo.getLastNewData()[0].get("intPrimitive"));
        assertEquals(11, listenerTwo.getLastNewData()[0].get("intBoxed"));
        assertEquals(18, listenerTwo.getLastNewData()[0].getEventType().getPropertyNames().length);
        assertSame(event, listenerTwo.getLastNewData()[0].getUnderlying());
    }

    public void testVariantTwoJoin()
    {
        String stmtText = "insert into Event_1 " +
                      "select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
                        "from " + SupportBean.class.getName() + ".win:length(100) as s0," +
                                  SupportBean_A.class.getName() + ".win:length(100) as s1 " +
                        " where s0.string = s1.id";

        runAsserts(stmtText, null);
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
        SupportBean_A eventA = new SupportBean_A("myId");
        epService.getEPRuntime().sendEvent(eventA);
        
        SupportBean eventOne = sendEvent(10, 11);
        assertTrue(listenerOne.getAndClearIsInvoked());
        assertEquals(1, listenerOne.getLastNewData().length);
        assertEquals(2, listenerOne.getLastNewData()[0].getEventType().getPropertyNames().length);
        assertTrue(listenerOne.getLastNewData()[0].getEventType().isProperty("s0"));
        assertTrue(listenerOne.getLastNewData()[0].getEventType().isProperty("s1"));
        assertSame(eventOne, listenerOne.getLastNewData()[0].get("s0"));
        assertSame(eventA, listenerOne.getLastNewData()[0].get("s1"));

        assertTrue(listenerTwo.getAndClearIsInvoked());
        assertEquals(1, listenerTwo.getLastNewData().length);
        assertEquals(2, listenerTwo.getLastNewData()[0].getEventType().getPropertyNames().length);
        assertTrue(listenerTwo.getLastNewData()[0].getEventType().isProperty("s0"));
        assertTrue(listenerTwo.getLastNewData()[0].getEventType().isProperty("s1"));
        assertSame(eventOne, listenerOne.getLastNewData()[0].get("s0"));
        assertSame(eventA, listenerOne.getLastNewData()[0].get("s1"));
    }

    public void testInvalidStreamUsed()
    {
        String stmtText = "insert into Event_1 (delta, product) " +
                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";
        epService.getEPAdministrator().createEQL(stmtText);

        try
        {
            stmtText = "insert into Event_1(delta) " +
                      "select (intPrimitive - intBoxed) as deltaTag " +
                      "from " + SupportBean.class.getName() + ".win:length(100)";
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            // expected
            assertEquals("Error starting view: Event type named 'Event_1' has already been declared with differing column name or type information [insert into Event_1(delta) select (intPrimitive - intBoxed) as deltaTag from net.esper.support.bean.SupportBean.win:length(100)]", ex.getMessage());
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
    
    public void testStaggeredWithWildcard()
    {
    	String statementOne = "insert into streamA select * from " + SupportBeanSimple.class.getName() + ".win:length(5)";
    	String statementTwo = "insert into streamB select *, myInt+myInt as summed, myString||myString as concat from streamA.win:length(5)";
    	String statementThree = "insert into streamC select * from streamB.win:length(5)";
    	
    	SupportUpdateListener listenerOne = new SupportUpdateListener();
    	SupportUpdateListener listenerTwo = new SupportUpdateListener();
    	SupportUpdateListener listenerThree = new SupportUpdateListener();
    	
    	epService.getEPAdministrator().createEQL(statementOne).addListener(listenerOne);
    	epService.getEPAdministrator().createEQL(statementTwo).addListener(listenerTwo);
    	epService.getEPAdministrator().createEQL(statementThree).addListener(listenerThree);
    	
    	sendSimpleEvent("one", 1);
    	assertSimple(listenerOne, "one", 1, null, 0);
    	assertSimple(listenerTwo, "one", 1, "oneone", 2);
    	assertSimple(listenerThree, "one", 1, "oneone", 2);
    	
    	sendSimpleEvent("two", 2);
    	assertSimple(listenerOne, "two", 2, null, 0);
    	assertSimple(listenerTwo, "two", 2, "twotwo", 4);
    	assertSimple(listenerThree, "two", 2, "twotwo", 4);
    }

    public void testInsertFromPattern()
    {
    	String stmtOneText = "insert into streamA select * from pattern [every " + SupportBean.class.getName() + "]";
    	SupportUpdateListener listenerOne = new SupportUpdateListener();
    	EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmtOneText);
        stmtOne.addListener(listenerOne);

        String stmtTwoText = "insert into streamA select * from pattern [every " + SupportBean.class.getName() + "]";
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmtTwoText);
        stmtTwo.addListener(listenerTwo);

        EventType eventType = stmtOne.getEventType();
        assertEquals(Map.class, eventType.getUnderlyingType());
    }

    public void testInsertIntoPlusPattern()
    {
        String stmtOneTxt = "insert into InZone " +
                      "select 111 as statementId, mac, locationReportId " +
                      "from " + SupportRFIDEvent.class.getName() + " " +
                      "where mac in ('1','2','3') " +
                      "and zoneID = '10'";
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmtOneTxt);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        String stmtTwoTxt = "insert into OutOfZone " +
                      "select 111 as statementId, mac, locationReportId " +
                      "from " + SupportRFIDEvent.class.getName() + " " +
                      "where mac in ('1','2','3') " +
                      "and zoneID != '10'";
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmtTwoTxt);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        String stmtThreeTxt = "select 111 as eventSpecId, A.locationReportId as locationReportId " +
                      " from pattern [every A=InZone -> (timer:interval(1 sec) and not OutOfZone(mac=A.mac))]";
        EPStatement stmtThree = epService.getEPAdministrator().createEQL(stmtThreeTxt);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmtThree.addListener(listener);

        // try the alert case with 1 event for the mac in question
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        epService.getEPRuntime().sendEvent(new SupportRFIDEvent("LR1", "1", "10"));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));

        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("LR1", event.get("locationReportId"));

        listenerOne.reset();
        listenerTwo.reset();

        // try the alert case with 2 events for zone 10 within 1 second for the mac in question
        epService.getEPRuntime().sendEvent(new SupportRFIDEvent("LR2", "2", "10"));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1500));
        epService.getEPRuntime().sendEvent(new SupportRFIDEvent("LR3", "2", "10"));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2000));

        event = listener.assertOneGetNewAndReset();
        assertEquals("LR2", event.get("locationReportId"));
    }
    
    public void testNullType()
    {
        String stmtOneTxt = "insert into InZone select null as dummy from java.lang.String";
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmtOneTxt);
        assertTrue(stmtOne.getEventType().isProperty("dummy"));

        String stmtTwoTxt = "select dummy from InZone";
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmtTwoTxt);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmtTwo.addListener(listener);
        
        epService.getEPRuntime().sendEvent("a");
        assertNull(listener.assertOneGetNewAndReset().get("dummy"));
    }
    
    private void assertSimple(SupportUpdateListener listener, String myString, int myInt, String additionalString, int additionalInt)
    {
    	assertTrue(listener.getAndClearIsInvoked());
    	EventBean eventBean = listener.getLastNewData()[0];
    	assertEquals(myString, eventBean.get("myString"));
    	assertEquals(myInt, eventBean.get("myInt"));
    	if(additionalString != null)
    	{
    		assertEquals(additionalString, eventBean.get("concat"));
    		assertEquals(additionalInt, eventBean.get("summed"));
    	}
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, null, null);
        epService.getEPRuntime().sendEvent(bean);
    }
    
    private void sendSimpleEvent(String string, int val)
    {
    	epService.getEPRuntime().sendEvent(new SupportBeanSimple(string, val));
    }

    private EPStatement runAsserts(String stmtText, EPStatementObjectModel model)
    {
        // Attach listener to feed
        EPStatement stmt = null;
        if (model != null)
        {
            stmt = epService.getEPAdministrator().create(model, "s1");
        }
        else
        {
            stmt = epService.getEPAdministrator().createEQL(stmtText);
        }
        stmt.addListener(feedListener);

        // send event for joins to match on
        epService.getEPRuntime().sendEvent(new SupportBean_A("myId"));

        // Attach delta statement to statement and add listener
        stmtText = "select min(delta) as minD, max(delta) as maxD " +
                   "from Event_1.win:time(60)";
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmtText);
        stmtTwo.addListener(resultListenerDelta);

        // Attach prodict statement to statement and add listener
        stmtText = "select min(product) as minP, max(product) as maxP " +
                   "from Event_1.win:time(60)";
        EPStatement stmtThree = epService.getEPAdministrator().createEQL(stmtText);
        stmtThree.addListener(resultListenerProduct);

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

        return stmt;
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

    private SupportBean sendEvent(int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString("myId");
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
