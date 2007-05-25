package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;

public class TestOutputLimitlEventPerGroup extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testWithGroupBy()
    {
    	String eventName = SupportMarketDataBean.class.getName();
    	String statementString = "select symbol, sum(price) from " + eventName + ".win:length(5) " +
                                 "group by symbol output every 5 events";
    	EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
    	SupportUpdateListener updateListener = new SupportUpdateListener();
    	statement.addListener(updateListener);

    	// send some events and check that only the most recent
    	// ones are kept
    	sendMarketEvent("IBM", 1D);
    	sendMarketEvent("IBM", 2D);
    	sendMarketEvent("HP", 1D);
    	sendMarketEvent("IBM", 3D);
    	sendMarketEvent("MAC", 1D);

    	assertTrue(updateListener.getAndClearIsInvoked());
    	EventBean[] newData = updateListener.getLastNewData();
    	assertEquals(3, newData.length);
    	assertSingleInstance(newData, "IBM");
    	assertSingleInstance(newData, "HP");
    	assertSingleInstance(newData, "MAC");
    	EventBean[] oldData = updateListener.getLastOldData();
        assertNull(oldData);
    }


    public void testNoJoinLast()
	{
	    String viewExpr = "select symbol," +
	                             "sum(price) as mySum," +
	                             "avg(price) as myAvg " +
	                      "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
	                      "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(testListener);

	    runAssertionLast();
	}

    public void testNoOutputClauseView()
    {
    	String viewExpr = "select symbol," +
                        "sum(price) as mySum," +
                        "avg(price) as myAvg " +
                        "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                        "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                        "group by symbol";

    	selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
    	selectTestView.addListener(testListener);

    	runAssertionSingle();
    }

    public void testNoOutputClauseJoin()
    {
    	String viewExpr = "select symbol," +
                        "sum(price) as mySum," +
                        "avg(price) as myAvg " +
                        "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                        SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
                        "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                        "       and one.string = two.symbol " +
                        "group by symbol";

    	selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
    	selectTestView.addListener(testListener);

    	epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
    	epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
    	epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

    	runAssertionSingle();
    }

	public void testNoJoinAll()
    {
        String viewExpr = "select symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol " +
                          "output all every 2 events";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertionAll();
    }

    public void testJoinLast()
	{
	    String viewExpr = "select symbol," +
	                             "sum(price) as mySum," +
	                             "avg(price) as myAvg " +
	                      "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
	                                SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
	                      "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                      "       and one.string = two.symbol " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(testListener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
	    epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

	    runAssertionLast();
	}

	public void testJoinAll()
    {
        String viewExpr = "select symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "       and one.string = two.symbol " +
                          "group by symbol " +
                          "output all every 2 events";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
        epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

        runAssertionAll();
    }

    private void runAssertionLast()
	{
	    // assert select result type
	    assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

	    sendEvent(SYMBOL_DELL, 10);
	    assertFalse(testListener.isInvoked());

	    sendEvent(SYMBOL_DELL, 20);
	    assertEventNew(SYMBOL_DELL,
	            30d, 15d);
	    testListener.reset();

	    sendEvent(SYMBOL_DELL, 100);
	    assertFalse(testListener.isInvoked());

	    sendEvent(SYMBOL_DELL, 50);
	    assertEvent(SYMBOL_DELL,
	    		30d, 15d,
	            170d, 170/3d);
	}

    private void runAssertionSingle()
	{
	    // assert select result type
	    assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

	    sendEvent(SYMBOL_DELL, 10);
	    assertTrue(testListener.isInvoked());
	    assertEvent(SYMBOL_DELL,
            	null, null,
            	10d, 10d);

	    sendEvent(SYMBOL_IBM, 20);
	    assertTrue(testListener.isInvoked());
	    assertEvent(SYMBOL_IBM,
	            	null, null,
	            	20d, 20d);
	}

	private void runAssertionAll()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

        sendEvent(SYMBOL_IBM, 70);
        assertFalse(testListener.isInvoked());

        sendEvent(SYMBOL_DELL, 10);
        assertEventsNew(70d, 70d,
        		SYMBOL_DELL,
                10d, 10d);
	    testListener.reset();

        sendEvent(SYMBOL_DELL, 20);
        assertFalse(testListener.isInvoked());

        sendEvent(SYMBOL_DELL, 100);
        assertEvents(70d, 70d,
        		70d, 70d,
        		SYMBOL_DELL,
                10d, 10d,
                130d, 130d/3d);
    }

    private void assertEvent(String symbol,
                             Double oldSum, Double oldAvg,
                             Double newSum, Double newAvg)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldSum, oldData[0].get("mySum"));
        assertEquals(oldAvg, oldData[0].get("myAvg"));

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newSum, newData[0].get("mySum"));
        assertEquals("newData myAvg wrong", newAvg, newData[0].get("myAvg"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertEventNew(String symbol,
                             Double newSum, Double newAvg)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newSum, newData[0].get("mySum"));
        assertEquals("newData myAvg wrong", newAvg, newData[0].get("myAvg"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertEventsNew(double newSumOne, double newAvgOne,
                              String symbolTwo,
                              double newSumTwo, double newAvgTwo)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(2, newData.length);

        int indexOne = 0;
        int indexTwo = 1;
        if (newData[0].get("symbol").equals(symbolTwo))
        {
            indexTwo = 0;
            indexOne = 1;
        }
        assertEquals(newSumOne, newData[indexOne].get("mySum"));
        assertEquals(newSumTwo, newData[indexTwo].get("mySum"));

        assertEquals(newAvgOne, newData[indexOne].get("myAvg"));
        assertEquals(newAvgTwo, newData[indexTwo].get("myAvg"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertEvents(Double oldSumOne, Double oldAvgOne,
                              double newSumOne, double newAvgOne,
                              String symbolTwo,
                              Double oldSumTwo, Double oldAvgTwo,
                              double newSumTwo, double newAvgTwo)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(2, oldData.length);
        assertEquals(2, newData.length);

        int indexOne = 0;
        int indexTwo = 1;
        if (oldData[0].get("symbol").equals(symbolTwo))
        {
            indexTwo = 0;
            indexOne = 1;
        }
        assertEquals(newSumOne, newData[indexOne].get("mySum"));
        assertEquals(newSumTwo, newData[indexTwo].get("mySum"));
        assertEquals(oldSumOne, oldData[indexOne].get("mySum"));
        assertEquals(oldSumTwo, oldData[indexTwo].get("mySum"));

        assertEquals(newAvgOne, newData[indexOne].get("myAvg"));
        assertEquals(newAvgTwo, newData[indexTwo].get("myAvg"));
        assertEquals(oldAvgOne, oldData[indexOne].get("myAvg"));
        assertEquals(oldAvgTwo, oldData[indexTwo].get("myAvg"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendMarketEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void assertSingleInstance(EventBean[] data, String symbol)
    {
        int instanceCount = 0;
        for(EventBean event : data)
        {
            if(event.get("symbol").equals(symbol))
            {
                instanceCount++;
            }
        }
        assertEquals(1, instanceCount);
    }    
}
