package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;
import net.esper.collection.UniformPair;

public class TestOutputLimitAggregateAll extends TestCase
{
    private static final String EVENT_NAME = SupportMarketDataBean.class.getName();
    private final static String JOIN_KEY = "KEY";

    private SupportUpdateListener listener;
	private EPServiceProvider epService;
    private long currentTime;
    
    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testMaxTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select volume, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:time(1 sec) " +
                          "output every 1 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(viewExpr);
        stmt.addListener(listener);

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

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select volume, max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort('volume', true, 1) as s0," +
                          SupportBean.class.getName() + " as s1 " +
                          "output every 1 seconds";
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

	public void testAggregateAllNoJoinLast()
	{
	    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "having sum(longBoxed) > 0 " +
	    "output last every 2 events";

	    runAssertLastSum(createStmtAndListenerNoJoin(viewExpr));

	    viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output last every 2 events";

	    runAssertLastSum(createStmtAndListenerNoJoin(viewExpr));
	}

	public void testAggregateAllJoinAll()
	{
	    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
                        "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
                        SupportBean.class.getName() + ".win:length(3) as two " +
                        "having sum(longBoxed) > 0 " +
                        "output all every 2 events";

	    runAssertAllSum(createStmtAndListenerJoin(viewExpr));

	    viewExpr = "select longBoxed, sum(longBoxed) as result " +
                    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
                    SupportBean.class.getName() + ".win:length(3) as two " +
                    "output every 2 events";

	    runAssertAllSum(createStmtAndListenerJoin(viewExpr));
	}

	public void testAggregateAllJoinLast()
    {
        String viewExpr = "select longBoxed, sum(longBoxed) as result " +
        "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
        SupportBean.class.getName() + ".win:length(3) as two " +
        "having sum(longBoxed) > 0 " +
        "output last every 2 events";

        runAssertLastSum(createStmtAndListenerJoin(viewExpr));

        viewExpr = "select longBoxed, sum(longBoxed) as result " +
        "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
        SupportBean.class.getName() + ".win:length(3) as two " +
        "output last every 2 events";

        runAssertLastSum(createStmtAndListenerJoin(viewExpr));
    }

    public void testTime()
    {
        // Clear any old events
        epService.initialize();

        // Turn off external clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        // Set the clock to 0
        currentTime = 0;
        sendTimeEvent(0);

        // Create the eql statement and add a listener
        String statementText = "select symbol, sum(volume) from " + EVENT_NAME + ".win:length(5) output first every 3 seconds";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
        SupportUpdateListener updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);
        updateListener.reset();

        // Send the first event of the batch; should be output
        sendMarketDataEvent(10L);
        assertEvent(updateListener, 10L);

        // Send another event, not the first, for aggregation
        // update only, no output
        sendMarketDataEvent(20L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Update time
        sendTimeEvent(3000);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Send first event of the next batch, should be output.
        // The aggregate value is computed over all events
        // received: 10 + 20 + 30 = 60
        sendMarketDataEvent(30L);
        assertEvent(updateListener, 60L);

        // Send the next event of the batch, no output
        sendMarketDataEvent(40L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Update time
        sendTimeEvent(3000);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Send first event of third batch
        sendMarketDataEvent(1L);
        assertEvent(updateListener, 101L);

        // Update time
        sendTimeEvent(3000);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Update time: no first event this batch, so a callback
        // is made at the end of the interval
        sendTimeEvent(3000);
        assertTrue(updateListener.getAndClearIsInvoked());
        assertNull(updateListener.getLastNewData());
        assertNull(updateListener.getLastOldData());
    }

    public void testCount()
    {
        // Create the eql statement and add a listener
        String statementText = "select symbol, sum(volume) from " + EVENT_NAME + ".win:length(5) output first every 3 events";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
        SupportUpdateListener updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);
        updateListener.reset();

        // Send the first event of the batch, should be output
        sendEventLong(10L);
        assertEvent(updateListener, 10L);

        // Send the second event of the batch, not output, used
        // for updating the aggregate value only
        sendEventLong(20L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Send the third event of the batch, still not output,
        // but should reset the batch
        sendEventLong(30L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // First event, next batch, aggregate value should be
        // 10 + 20 + 30 + 40 = 100
        sendEventLong(40L);
        assertEvent(updateListener, 100L);

        // Next event again not output
        sendEventLong(50L);
        assertFalse(updateListener.getAndClearIsInvoked());
    }

    private void sendEventLong(long volume)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DELL", 0.0, volume, null));
    }

    private SupportUpdateListener createStmtAndListenerNoJoin(String viewExpr) {
		epService.initialize();
		SupportUpdateListener updateListener = new SupportUpdateListener();
		EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
	    view.addListener(updateListener);

	    return updateListener;
	}

	private void runAssertAllSum(SupportUpdateListener updateListener)
	{
		// send an event
	    sendEvent(1);

	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());

	    // send another event
	    sendEvent(2);

	    // check update, all events present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(2, updateListener.getLastNewData().length);
	    assertEquals(1L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertEquals(3L, updateListener.getLastNewData()[0].get("result"));
	    assertEquals(2L, updateListener.getLastNewData()[1].get("longBoxed"));
	    assertEquals(3L, updateListener.getLastNewData()[1].get("result"));
	    assertNull(updateListener.getLastOldData());
	}

	private void runAssertLastSum(SupportUpdateListener updateListener)
	{
		// send an event
	    sendEvent(1);

	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());

	    // send another event
	    sendEvent(2);

	    // check update, all events present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(1, updateListener.getLastNewData().length);
	    assertEquals(2L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertEquals(3L, updateListener.getLastNewData()[0].get("result"));
	    assertNull(updateListener.getLastOldData());
	}

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(JOIN_KEY);
	    bean.setLongBoxed(longBoxed);
	    bean.setIntBoxed(intBoxed);
	    bean.setShortBoxed(shortBoxed);
	    epService.getEPRuntime().sendEvent(bean);
	}

	private void sendEvent(long longBoxed)
	{
	    sendEvent(longBoxed, 0, (short)0);
	}

    private void sendMarketDataEvent(long volume)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("SYM1", 0, volume, null));
    }

    private void sendTimeEvent(int timeIncrement){
        currentTime += timeIncrement;
        CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
        epService.getEPRuntime().sendEvent(event);
    }

	private SupportUpdateListener createStmtAndListenerJoin(String viewExpr) {
		epService.initialize();
		
		SupportUpdateListener updateListener = new SupportUpdateListener();
		EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
	    view.addListener(updateListener);
	    
	    epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));
	    
	    return updateListener;
	}

    private void assertEvent(SupportUpdateListener updateListener, long volume)
    {
        assertTrue(updateListener.getAndClearIsInvoked());
        assertTrue(updateListener.getLastNewData() != null);
        assertEquals(1, updateListener.getLastNewData().length);
        assertEquals(volume, updateListener.getLastNewData()[0].get("sum(volume)"));
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}    

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }    
}
