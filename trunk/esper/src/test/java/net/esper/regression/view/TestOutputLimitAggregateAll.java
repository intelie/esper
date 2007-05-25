package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.util.SupportUpdateListener;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;

public class TestOutputLimitAggregateAll extends TestCase
{
    private static final String JOIN_KEY = "KEY";
    private static final String EVENT_NAME = SupportMarketDataBean.class.getName();
	private EPServiceProvider epService;
	private long currentTime;
	private SupportUpdateListener updateListener;

	protected void setUp()
	{
		epService = EPServiceProviderManager.getProvider("TestOutputLimitFirst");
        epService.initialize();
    }

    public void testAggregateAllNoJoinAll()
    {
        String viewExpr = "select longBoxed, sum(longBoxed) as result " +
                        "from " + SupportBean.class.getName() + ".win:length(3) " +
                        "having sum(longBoxed) > 0 " +
                        "output all every 2 events";

        runAssertAllSum(createStmtAndListenerNoJoin(viewExpr));

        viewExpr = "select longBoxed, sum(longBoxed) as result " +
                    "from " + SupportBean.class.getName() + ".win:length(3) " +
                    "output every 2 events";

        runAssertAllSum(createStmtAndListenerNoJoin(viewExpr));
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
        updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);
        updateListener.reset();

        // Send the first event of the batch; should be output
        sendEventMktData(10L);
        assertEvent(10L);

        // Send another event, not the first, for aggregation
        // update only, no output
        sendEventMktData(20L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Update time
        sendTimeEvent(3000);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Send first event of the next batch, should be output.
        // The aggregate value is computed over all events
        // received: 10 + 20 + 30 = 60
        sendEventMktData(30L);
        assertEvent(60L);

        // Send the next event of the batch, no output
        sendEventMktData(40L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Update time
        sendTimeEvent(3000);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Send first event of third batch
        sendEventMktData(1L);
        assertEvent(101L);

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
        updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);
        updateListener.reset();

        // Send the first event of the batch, should be output
        sendEventMktData(10L);
        assertEvent(10L);

        // Send the second event of the batch, not output, used
        // for updating the aggregate value only
        sendEventMktData(20L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // Send the third event of the batch, still not output,
        // but should reset the batch
        sendEventMktData(30L);
        assertFalse(updateListener.getAndClearIsInvoked());

        // First event, next batch, aggregate value should be
        // 10 + 20 + 30 + 40 = 100
        sendEventMktData(40L);
        assertEvent(100L);

        // Next event again not output
        sendEventMktData(50L);
        assertFalse(updateListener.getAndClearIsInvoked());
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
        assertEquals(1L, updateListener.getLastNewData()[0].get("result"));
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

    public void testSimpleJoinLast()
    {
        String viewExpr = "select longBoxed " +
        "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
        SupportBean.class.getName() + ".win:length(3) as two " +
        "output last every 2 events";

        runAssertLast(createStmtAndListenerJoin(viewExpr));
    }

    private SupportUpdateListener createStmtAndListenerJoin(String viewExpr) {
        epService.initialize();

        SupportUpdateListener updateListener = new SupportUpdateListener();
        EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
        view.addListener(updateListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));

        return updateListener;
    }

    private SupportUpdateListener createStmtAndListenerNoJoin(String viewExpr) {
        epService.initialize();
        SupportUpdateListener updateListener = new SupportUpdateListener();
        EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
        view.addListener(updateListener);

        return updateListener;
    }

    private void runAssertLast(SupportUpdateListener updateListener)
    {
        // send an event
        sendEvent(1);

        // check no update
        assertFalse(updateListener.getAndClearIsInvoked());

        // send another event
        sendEvent(2);

        // check update, only the last event present
        assertTrue(updateListener.getAndClearIsInvoked());
        assertEquals(1, updateListener.getLastNewData().length);
        assertEquals(2L, updateListener.getLastNewData()[0].get("longBoxed"));
        assertNull(updateListener.getLastOldData());
    }

	private void sendTimeEvent(int timeIncrement){
		currentTime += timeIncrement;
	    CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
	    epService.getEPRuntime().sendEvent(event);
	}

	private void assertEvent(long volume)
	{
		assertTrue(updateListener.getAndClearIsInvoked());
    	assertTrue(updateListener.getLastNewData() != null);
    	assertEquals(1, updateListener.getLastNewData().length);
    	assertEquals(volume, updateListener.getLastNewData()[0].get("sum(volume)"));
	}

    private void sendEvent(long longBoxed)
    {
        sendEvent(longBoxed, 0, (short)0);
    }

	private void sendEventMktData(long volume)
	{
		epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DELL", 0.0, volume, null));
	}
}
