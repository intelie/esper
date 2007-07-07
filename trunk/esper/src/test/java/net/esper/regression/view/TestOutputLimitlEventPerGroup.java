package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.collection.UniformPair;

public class TestOutputLimitlEventPerGroup extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select symbol, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort('volume', true, 1) as s0," +
                          SupportBean.class.getName() + " as s1 " +
                          "group by symbol output every 1 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(viewExpr);
        stmt.addListener(listener);
        epService.getEPRuntime().sendEvent(new SupportBean("JOIN_KEY", -1));

        sendEvent("JOIN_KEY", 1d);
        sendEvent("JOIN_KEY", 2d);
        listener.reset();

        // moves all events out of the window,
        sendTimer(1000);        // newdata is 2 eventa, old data is the same 2 events, therefore the sum is null
        UniformPair<EventBean[]> result = listener.getDataListsFlattened();
        assertEquals(1, result.getFirst().length);
        assertEquals(2.0, result.getFirst()[0].get("maxVol"));
        assertEquals(1, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
    }

    public void testWithGroupBy()
    {
    	String eventName = SupportMarketDataBean.class.getName();
    	String statementString = "select symbol, sum(price) from " + eventName + ".win:length(5) group by symbol output every 5 events";
    	EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
    	SupportUpdateListener updateListener = new SupportUpdateListener();
    	statement.addListener(updateListener);

    	// send some events and check that only the most recent
    	// ones are kept
    	sendEvent("IBM", 1D);
    	sendEvent("IBM", 2D);
    	sendEvent("HP", 1D);
    	sendEvent("IBM", 3D);
    	sendEvent("MAC", 1D);

    	assertTrue(updateListener.getAndClearIsInvoked());
    	EventBean[] newData = updateListener.getLastNewData();
    	assertEquals(3, newData.length);
    	assertSingleInstance(newData, "IBM");
    	assertSingleInstance(newData, "HP");
    	assertSingleInstance(newData, "MAC");
    	EventBean[] oldData = updateListener.getLastOldData();
    	assertSingleInstance(oldData, "IBM");
    	assertSingleInstance(oldData, "HP");
    	assertSingleInstance(oldData, "MAC");
    }

    public void testMaxTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select symbol, " +
                                  "max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:time(1 sec) " +
                          "group by symbol output every 1 seconds";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent("SYM1", 1d);
        sendEvent("SYM1", 2d);
        listener.reset();

        // moves all events out of the window,
        sendTimer(1000);        // newdata is 2 eventa, old data is the same 2 events, therefore the sum is null
        UniformPair<EventBean[]> result = listener.getDataListsFlattened();
        assertEquals(1, result.getFirst().length);
        assertEquals(null, result.getFirst()[0].get("maxVol"));
        assertEquals(1, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
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
	    selectTestView.addListener(listener);

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
    	selectTestView.addListener(listener);

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
    	selectTestView.addListener(listener);

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
        selectTestView.addListener(listener);

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
	    selectTestView.addListener(listener);

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
        selectTestView.addListener(listener);

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
	    assertFalse(listener.isInvoked());

	    sendEvent(SYMBOL_DELL, 20);
	    assertEvent(SYMBOL_DELL,
	            null, null,
	            30d, 15d);
	    listener.reset();

	    sendEvent(SYMBOL_DELL, 100);
	    assertFalse(listener.isInvoked());

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
	    assertTrue(listener.isInvoked());
	    assertEvent(SYMBOL_DELL,
            	null, null,
            	10d, 10d);

	    sendEvent(SYMBOL_IBM, 20);
	    assertTrue(listener.isInvoked());
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
        assertFalse(listener.isInvoked());

        sendEvent(SYMBOL_DELL, 10);
        assertEvents(SYMBOL_IBM,
        		null, null,
        		70d, 70d,
        		SYMBOL_DELL,
                null, null,
                10d, 10d);
	    listener.reset();

        sendEvent(SYMBOL_DELL, 20);
        assertFalse(listener.isInvoked());


        sendEvent(SYMBOL_DELL, 100);
        assertEvents(SYMBOL_IBM,
        		null, null,
        		70d, 70d,
        		SYMBOL_DELL,
                10d, 10d,
                130d, 130d/3d);
    }

    private void assertEvent(String symbol,
                             Double oldSum, Double oldAvg,
                             Double newSum, Double newAvg)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldSum, oldData[0].get("mySum"));
        assertEquals(oldAvg, oldData[0].get("myAvg"));

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newSum, newData[0].get("mySum"));
        assertEquals("newData myAvg wrong", newAvg, newData[0].get("myAvg"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void assertEvents(String symbolOne,
                              Double oldSumOne, Double oldAvgOne,
                              double newSumOne, double newAvgOne,
                              String symbolTwo,
                              Double oldSumTwo, Double oldAvgTwo,
                              double newSumTwo, double newAvgTwo)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

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

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}
        
    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
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
