package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.collection.UniformPair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOutputLimitRowForAll extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort(volume, true, 1) as s0," +
                          SupportBean.class.getName() + " as s1 where s1.string = s0.symbol " +
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
        assertEquals(1, result.getFirst().length);
        assertEquals(2.0, result.getFirst()[0].get("maxVol"));
        assertEquals(1, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
    }

    public void testMaxTimeWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select max(price) as maxVol" +
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
        assertEquals(1, result.getFirst().length);
        assertEquals(null, result.getFirst()[0].get("maxVol"));
        assertEquals(1, result.getSecond().length);
        assertEquals(null, result.getSecond()[0].get("maxVol"));
    }

    public void testTimeWindowOutputCount()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmtText = "select count(*) as cnt from " + SupportBean.class.getName() + ".win:time(10 seconds) output every 10 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendTimer(0);
        sendTimer(10000);
        assertFalse(listener.isInvoked());
        sendTimer(20000);
        assertFalse(listener.isInvoked());

        sendEvent("e1");
        sendTimer(30000);
        EventBean[] newEvents = listener.getAndResetLastNewData();
        assertEquals(1, newEvents.length);
        assertEquals(0L, newEvents[0].get("cnt"));

        sendTimer(31000);

        sendEvent("e2");
        sendEvent("e3");
        sendTimer(40000);
        newEvents = listener.getAndResetLastNewData();
        assertEquals(1, newEvents.length);
        assertEquals(2L, newEvents[0].get("cnt"));
    }

    public void testTimeBatchOutputCount()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmtText = "select count(*) as cnt from " + SupportBean.class.getName() + ".win:time_batch(10 seconds) output every 10 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendTimer(0);
        sendTimer(10000);
        assertFalse(listener.isInvoked());
        sendTimer(20000);
        assertFalse(listener.isInvoked());

        sendEvent("e1");
        sendTimer(30000);
        assertFalse(listener.isInvoked());
        sendTimer(40000);
        EventBean[] newEvents = listener.getAndResetLastNewData();
        assertEquals(1, newEvents.length);
        // output limiting starts 10 seconds after, therefore the old batch was posted already and the cnt is zero
        assertEquals(0L, newEvents[0].get("cnt"));

        sendTimer(50000);
        EventBean[] newData = listener.getLastNewData();
        assertEquals(0L, newData[0].get("cnt"));
        listener.reset();

        sendEvent("e2");
        sendEvent("e3");
        sendTimer(60000);
        newEvents = listener.getAndResetLastNewData();
        assertEquals(1, newEvents.length);
        assertEquals(2L, newEvents[0].get("cnt"));
    }

    public void testLimitSnapshot()
    {
        SupportUpdateListener listener = new SupportUpdateListener();

        sendTimer(0);
        String selectStmt = "select count(*) as cnt from " + SupportBean.class.getName() + ".win:time(10 seconds) where intPrimitive > 0 output snapshot every 1 seconds";

        EPStatement stmt = epService.getEPAdministrator().createEQL(selectStmt);
        stmt.addListener(listener);
        sendEvent("s0", 1);

        sendTimer(500);
        sendEvent("s1", 1);
        sendEvent("s2", -1);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{2L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("s4", 2);
        sendEvent("s5", 3);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(2000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{4L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendEvent("s5", 4);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(9000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{5L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{4L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10999);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{3L}});
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    public void testLimitSnapshotJoin()
    {
        SupportUpdateListener listener = new SupportUpdateListener();

        sendTimer(0);
        String selectStmt = "select count(*) as cnt from " +
                SupportBean.class.getName() + ".win:time(10 seconds) as s, " +
                SupportMarketDataBean.class.getName() + " as m where m.symbol = s.string and intPrimitive > 0 output snapshot every 1 seconds";

        EPStatement stmt = epService.getEPAdministrator().createEQL(selectStmt);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s0", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s1", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s2", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s4", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s5", 0, 0L, ""));

        sendEvent("s0", 1);

        sendTimer(500);
        sendEvent("s1", 1);
        sendEvent("s2", -1);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(1000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{2L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(1500);
        sendEvent("s4", 2);
        sendEvent("s5", 3);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(2000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{4L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendEvent("s5", 4);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(9000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{5L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        // The execution of the join is after the snapshot, as joins are internal dispatch
        sendTimer(10000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{5L}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendTimer(10999);
        assertFalse(listener.getAndClearIsInvoked());

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), new String[] {"cnt"}, new Object[][] {{3L}});
        assertNull(listener.getLastOldData());
        listener.reset();
    }

    private void sendEvent(String s)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setDoubleBoxed(0.0);
	    bean.setIntPrimitive(0);
	    bean.setIntBoxed(0);
	    epService.getEPRuntime().sendEvent(bean);
	}
    
    private void sendEvent(String s, int intPrimitive)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setIntPrimitive(intPrimitive);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private static Log log = LogFactory.getLog(TestOutputLimitRowForAll.class);
}


