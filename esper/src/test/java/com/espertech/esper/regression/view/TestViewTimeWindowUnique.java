package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestViewTimeWindowUnique extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getViewResources().setAllowMultipleExpiryPolicies(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    // Make sure the timer and dispatch works for externally timed events and views
    public void testWindowUnique()
    {
        // Set up a time window with a unique view attached
        EPStatement windowUniqueView = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportMarketDataBean.class.getName() +
                ".win:time(3.0).std:unique(symbol)");
        windowUniqueView.addListener(listener);

        sendTimer(0);

        sendEvent("IBM");

        assertNull(listener.getLastOldData());
        sendTimer(4000);
        assertEquals(1, listener.getLastOldData().length);
    }

    // Make sure the timer and dispatch works for externally timed events and views
    public void testWindowUniqueMultiKey()
    {
        sendTimer(0);

        // Set up a time window with a unique view attached
        EPStatement windowUniqueView = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportMarketDataBean.class.getName() +
                ".win:time(3.0).std:unique(symbol, price)");
        windowUniqueView.addListener(listener);
        String[] fields = new String[] {"symbol", "price", "volume"};

        sendEvent("IBM", 10, 1L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"IBM", 10.0, 1L});

        sendEvent("IBM", 11, 2L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"IBM", 11.0, 2L});

        sendEvent("IBM", 10, 3L);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"IBM", 10.0, 3L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"IBM", 10.0, 1L});
        listener.reset();

        sendEvent("IBM", 11, 4L);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"IBM", 11.0, 4L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"IBM", 11.0, 2L});
        listener.reset();

        sendTimer(2000);
        sendEvent(null, 11, 5L);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, 11.0, 5L});

        sendTimer(3000);
        assertEquals(2, listener.getLastOldData().length);
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"IBM", 10.0, 3L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[1], fields, new Object[] {"IBM", 11.0, 4L});
        listener.reset();

        sendEvent(null, 11, 6L);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {null, 11.0, 6L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {null, 11.0, 5L});
        listener.reset();

        sendTimer(6000);
        assertEquals(1, listener.getLastOldData().length);
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {null, 11.0, 6L});
        listener.reset();
    }

    private void sendEvent(String symbol)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, 0L, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendEvent(String symbol, double price, Long volume)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, price, volume, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
