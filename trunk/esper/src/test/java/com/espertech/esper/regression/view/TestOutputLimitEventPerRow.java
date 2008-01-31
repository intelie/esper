package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.UniformPair;

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
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testHavingOutputAll()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select symbol, volume, sum(price) as sumprice" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:keepall() " +
                          "group by symbol " +
                          "having sum(price) >= 10 " +
                          "output every 3 events";
        EPStatement stmt = epService.getEPAdministrator().createEQL(viewExpr);
        stmt.addListener(listener);

        sendEvent("S1", 1, 5);
        sendEvent("S1", 2, 6);
        assertFalse(listener.isInvoked());

        sendEvent("S1", 3, -3);
        String fields[] = "symbol,volume,sumprice".split(",");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"S1", 2, 11});
    }

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select symbol, volume, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort(volume, true, 1) as s0," +
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

    public void testLimitSnapshot()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);
        String selectStmt = "select symbol, volume, sum(price) as sumprice from " + SupportMarketDataBean.class.getName() +
                ".win:time(10 seconds) group by symbol output snapshot every 1 seconds";

        EPStatement stmt = epService.getEPAdministrator().createEQL(selectStmt);
        stmt.addListener(listener);
        sendEvent("s0", 1, 20);

        sendTimer(500);
        sendEvent("s1", 2, 16);
        sendEvent("s0", 3, 14);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        String fields[] = new String[] {"symbol", "volume", "sumprice"};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"s0", 1L, 34d}, {"s1", 2L, 16d}, {"s0", 3L, 34d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("s2", 4, 18);
        sendEvent("s1", 5, 30);

        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {{"s0", 1L, 34d}, {"s1", 2L, 46d}, {"s0", 3L, 34d}, {"s2", 4L, 18d}, {"s1", 5L, 46d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"s2", 4L, 18d}, {"s1", 5L, 30d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(12000);
        assertTrue(listener.isInvoked());
        assertNull(listener.getLastNewData());
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(13000);
        assertTrue(listener.isInvoked());
        assertNull(listener.getLastNewData());
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    public void testLimitSnapshotJoin()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);
        String selectStmt = "select symbol, volume, sum(price) as sumprice from " + SupportMarketDataBean.class.getName() +
                ".win:time(10 seconds) as m, " + SupportBean.class.getName() +
                ".win:keepall() as s where s.string = m.symbol group by symbol output snapshot every 1 seconds order by symbol, volume asc";

        EPStatement stmt = epService.getEPAdministrator().createEQL(selectStmt);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("s0", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("s1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("s2", 3));

        sendEvent("s0", 1, 20);

        sendTimer(500);
        sendEvent("s1", 2, 16);
        sendEvent("s0", 3, 14);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        String fields[] = new String[] {"symbol", "volume", "sumprice"};
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"s0", 1L, 34d}, {"s0", 3L, 34d}, {"s1", 2L, 16d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("s2", 4, 18);
        sendEvent("s1", 5, 30);

        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {{"s0", 1L, 34d}, {"s0", 3L, 34d}, {"s1", 2L, 46d}, {"s1", 5L, 46d}, {"s2", 4L, 18d}, });
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10500);
        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"s1", 5L, 30d}, {"s2", 4L, 18d}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(11500);
        sendTimer(12000);
        assertTrue(listener.isInvoked());
        assertNull(listener.getLastNewData());
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(13000);
        assertTrue(listener.isInvoked());
        assertNull(listener.getLastNewData());
        assertNull(listener.getLastOldData());
        listener.reset();
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
        	assertEvent(newData[1], symbol2, volume2, sum2);
        }
        else
        {
        	assertEvent(newData[0], symbol2, volume2, sum2);
        	assertEvent(newData[1], symbol1, volume1, sum1);
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
    			assertEvent(newData[2], symbol3, volume3, sum3);
    		}
    		else
    		{
    			assertEvent(newData[1], symbol3, volume3, sum3);
    			assertEvent(newData[2], symbol1, volume1, sum1);
    		}
    	}
    	else
    	{
    		if(matchesEvent(newData[1], symbol1, volume1, sum1))
    		{
    			assertEvent(newData[2], symbol2, volume2, sum2);
    		}
    		else
    		{
    			assertEvent(newData[1], symbol2, volume2, sum2);
    			assertEvent(newData[2], symbol1, volume1, sum1);
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

    private void assertEvent(EventBean event, String symbol, long volume, double sum)
    {
        assertEquals(symbol, event.get("symbol"));
        assertEquals(volume, event.get("volume"));
        assertEquals(sum, event.get("mySum"));
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
