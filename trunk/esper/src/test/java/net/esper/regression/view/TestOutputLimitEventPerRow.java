package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;
import net.esper.collection.UniformPair;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestOutputLimitEventPerRow extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select symbol, volume, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort('volume', true, 1) as s0," +
                          SupportBean.class.getName() + " as s1 where s1.string = s0.symbol " +
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
        assertEquals(2, result.getFirst().length);
        assertEquals(2.0, result.getFirst()[0].get("maxVol"));
        assertEquals(2.0, result.getFirst()[1].get("maxVol"));
        assertEquals(1, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
    }

    public void testMaxTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select symbol, " +
                                  "volume, max(price) as maxVol" +
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
        assertEquals(2, result.getFirst().length);
        assertEquals(null, result.getFirst()[0].get("maxVol"));
        assertEquals(null, result.getFirst()[1].get("maxVol"));
        assertEquals(2, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
        assertEquals(null, result.getSecond()[1].get("maxVol"));
    }

    public void testNoJoinLast()
	{
	    // Every event generates a new row, this time we sum the price by symbol and output volume
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
	                      "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(listener);

	    runAssertionLast();
	}

    private void assertEvent(String symbol, Double mySum, Long volume)
	{
	    EventBean[] newData = listener.getLastNewData();

	    assertEquals(1, newData.length);

	    assertEquals(symbol, newData[0].get("symbol"));
	    assertEquals(mySum, newData[0].get("mySum"));
	    assertEquals(volume, newData[0].get("volume"));

	    listener.reset();
	    assertFalse(listener.isInvoked());
	}

	private void runAssertionSingle()
	{
	    // assert select result type
	    assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
	    assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));

	    sendEvent(SYMBOL_DELL, 10, 100);
	    assertTrue(listener.isInvoked());
	    assertEvent(SYMBOL_DELL, 100d, 10L);

	    sendEvent(SYMBOL_IBM, 15, 50);
	    assertEvent(SYMBOL_IBM, 50d, 15L);
	}

	public void testNoOutputClauseView()
	{
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
	                      "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                      "group by symbol ";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(listener);

	    runAssertionSingle();
	}

	public void testNoJoinAll()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol " +
                          "output all every 2 events";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        runAssertionAll();
    }

    public void testJoinAll()
	{
	    // Every event generates a new row, this time we sum the price by symbol and output volume
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
	                                SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
	                      "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                      "  and one.string = two.symbol " +
	                      "group by symbol " +
	                      "output all every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(listener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

	    runAssertionAll();
	}

	public void testJoinLast()
	{
	    // Every event generates a new row, this time we sum the price by symbol and output volume
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
	                                SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
	                      "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                      "  and one.string = two.symbol " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(listener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

	    runAssertionLast();
	}

	public void testNoOutputClauseJoin()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "  and one.string = two.symbol " +
                          "group by symbol " +
                          "output last every 2 events";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

        runAssertionLast();
    }

    private void runAssertionAll()
    {
    	// assert select result type
    	assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
    	assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
    	assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));

    	sendEvent(SYMBOL_IBM, 10000, 20);
    	assertFalse(listener.getAndClearIsInvoked());

    	sendEvent(SYMBOL_DELL, 10000, 51);
    	assertTwoEvents(SYMBOL_IBM, 10000, 20,
    			SYMBOL_DELL, 10000, 51);

    	sendEvent(SYMBOL_DELL, 20000, 52);
    	assertFalse(listener.getAndClearIsInvoked());

    	sendEvent(SYMBOL_DELL, 40000, 45);
    	assertThreeEvents(SYMBOL_IBM, 10000, 20,
    					  SYMBOL_DELL, 20000, 51+52+45,
    					  SYMBOL_DELL, 40000, 51+52+45);
    }

	private void runAssertionLast()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendEvent(SYMBOL_DELL, 10000, 51);
        assertFalse(listener.getAndClearIsInvoked());

        sendEvent(SYMBOL_DELL, 20000, 52);
        assertTwoEvents(SYMBOL_DELL, 10000, 103,
        				SYMBOL_DELL, 20000, 103);

        sendEvent(SYMBOL_DELL, 30000, 70);
        assertFalse(listener.getAndClearIsInvoked());

        sendEvent(SYMBOL_IBM, 10000, 20);
        assertTwoEvents(SYMBOL_DELL, 30000, 173,
        				SYMBOL_IBM, 10000, 20);
  }

    private void assertTwoEvents(String symbol1, long volume1, double sum1,
    							 String symbol2, long volume2, double sum2)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertNull(oldData);
        assertEquals(2, newData.length);

        if(matchesEvent(newData[0], symbol1, volume1, sum1))
        {
        	assertTrue(matchesEvent(newData[1], symbol2, volume2, sum2));
        }
        else
        {
        	assertTrue(matchesEvent(newData[0], symbol2, volume2, sum2));
        	assertTrue(matchesEvent(newData[1], symbol1, volume1, sum1));
        }

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void assertThreeEvents(String symbol1, long volume1, double sum1,
    		String symbol2, long volume2, double sum2,
    		String symbol3, long volume3, double sum3)
    {
    	EventBean[] oldData = listener.getLastOldData();
    	EventBean[] newData = listener.getLastNewData();

    	assertNull(oldData);
    	assertEquals(3, newData.length);

    	if(matchesEvent(newData[0], symbol1, volume1, sum1))
    	{
    		if(matchesEvent(newData[1], symbol2, volume2, sum2))
    		{
    			assertTrue(matchesEvent(newData[2], symbol3, volume3, sum3));
    		}
    		else
    		{
    			assertTrue(matchesEvent(newData[1], symbol3, volume3, sum3));
    			assertTrue(matchesEvent(newData[2], symbol2, volume2, sum2));
    		}
    	}
    	else if(matchesEvent(newData[0], symbol2, volume2, sum2))
    	{
    		if(matchesEvent(newData[1], symbol1, volume1, sum1))
    		{
    			assertTrue(matchesEvent(newData[2], symbol3, volume3, sum3));
    		}
    		else
    		{
    			assertTrue(matchesEvent(newData[1], symbol3, volume3, sum3));
    			assertTrue(matchesEvent(newData[2], symbol1, volume1, sum1));
    		}
    	}
    	else
    	{
    		if(matchesEvent(newData[1], symbol1, volume1, sum1))
    		{
    			assertTrue(matchesEvent(newData[2], symbol2, volume2, sum2));
    		}
    		else
    		{
    			assertTrue(matchesEvent(newData[1], symbol2, volume2, sum2));
    			assertTrue(matchesEvent(newData[2], symbol1, volume1, sum1));
    		}
    	}

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private boolean matchesEvent(EventBean event, String symbol, long volume, double sum)
    {
    	return
        symbol.equals(event.get("symbol")) &&
        new Long(volume).equals(event.get("volume")) &&
        new Double(sum).equals(event.get("mySum"));
    }

    private void sendEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
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

    private static final Log log = LogFactory.getLog(TestOutputLimitEventPerRow.class);
}
