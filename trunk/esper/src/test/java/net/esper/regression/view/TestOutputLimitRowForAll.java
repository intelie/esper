package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.collection.UniformPair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOutputLimitRowForAll extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testJoinSortWindow()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        sendTimer(0);

        String viewExpr = "select max(price) as maxVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".ext:sort('volume', true, 1) as s0," +
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

    private void sendEvent(String s)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setDoubleBoxed(0.0);
	    bean.setIntPrimitive(0);
	    bean.setIntBoxed(0);
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


