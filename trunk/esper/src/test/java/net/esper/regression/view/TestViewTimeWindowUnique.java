package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestViewTimeWindowUnique extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement windowUniqueView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        // Set up a time window with a unique view attached
        windowUniqueView = epService.getEPAdministrator().createEQL(
                "select * from " + SupportMarketDataBean.class.getName() +
                ".win:time(3.0).std:unique('symbol')");
        windowUniqueView.addListener(testListener);

        // External clocking
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    // Make sure the timer and dispatch works for externally timed events and views
    public void testWindowUnique()
    {
        sendTimer(0);

        sendEvent("IBM");

        assertNull(testListener.getLastOldData());
        sendTimer(4000);
        assertEquals(1, testListener.getLastOldData().length);
    }

    private void sendEvent(String symbol)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, 0L, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
