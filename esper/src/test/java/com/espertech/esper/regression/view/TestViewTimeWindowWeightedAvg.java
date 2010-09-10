package com.espertech.esper.regression.view;

import java.util.Iterator;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.client.EventBean;

public class TestViewTimeWindowWeightedAvg extends TestCase
{
    private static String SYMBOL = "CSCO.O";
    private static String FEED = "feed1";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement weightedAvgView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        // Set up a 1 second time window
        weightedAvgView = epService.getEPAdministrator().createEPL(
                "select * from " + SupportMarketDataBean.class.getName() +
                "(symbol='" + SYMBOL + "').win:time(3.0).stat:weighted_avg(price, volume, symbol, feed)");
        weightedAvgView.addListener(testListener);
        
        assertEquals(Double.class, weightedAvgView.getEventType().getPropertyType("average"));
    }
    
    public void testWindowStats()
    {
        testListener.reset();

        // Send 2 events, E1 and E2 at +0sec
        epService.getEPRuntime().sendEvent(makeBean(SYMBOL, 10, 500));
        checkValue(10);

        epService.getEPRuntime().sendEvent(makeBean(SYMBOL, 11, 500));
        checkValue(10.5);

        // Sleep for 1.5 seconds
        sleep(1500);

        // Send 2 more events, E3 and E4 at +1.5sec
        epService.getEPRuntime().sendEvent(makeBean(SYMBOL, 10, 1000));
        checkValue(10.25);
        epService.getEPRuntime().sendEvent(makeBean(SYMBOL, 10.5, 2000));
        checkValue(10.375);

        // Sleep for 2 seconds, E1 and E2 should have left the window
        sleep(2000);
        checkValue(10.333333333);

        // Send another event, E5 at +3.5sec
        epService.getEPRuntime().sendEvent(makeBean(SYMBOL, 10.2, 1000));
        checkValue(10.3);

        // Sleep for 2.5 seconds, E3 and E4 should expire
        sleep(2500);
        checkValue(10.2);

        // Sleep for 1 seconds, E5 should have expired
        sleep(1000);
        checkValue(Double.NaN);
    }

    private SupportMarketDataBean makeBean(String symbol, double price, long volume)
    {
        return new SupportMarketDataBean(symbol, price, volume, FEED);
    }

    private void checkValue(double avgE)
    {
        Iterator<EventBean> iterator = weightedAvgView.iterator();
        checkValue(iterator.next(), avgE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(testListener.getLastNewData().length == 1);
        EventBean listenerValues = testListener.getLastNewData()[0];
        checkValue(listenerValues, avgE);

        testListener.reset();
    }

    private void checkValue(EventBean values, double avgE)
    {
        double avg = getDoubleValue(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE, values);
        assertTrue(DoubleValueAssertionUtil.equals(avg,  avgE, 6));
        assertEquals(FEED, values.get("feed"));
        assertEquals(SYMBOL, values.get("symbol"));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean event)
    {
        return  (Double) event.get(field.getName());
    }

    private void sleep(int msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
        }
    }
}
