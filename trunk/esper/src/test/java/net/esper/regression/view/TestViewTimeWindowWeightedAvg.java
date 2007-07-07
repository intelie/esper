package net.esper.regression.view;

import java.util.Iterator;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.DoubleValueAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.view.ViewFieldEnum;
import net.esper.event.EventBean;

public class TestViewTimeWindowWeightedAvg extends TestCase
{
    private static String SYMBOL = "CSCO.O";
            
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement weightedAvgView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        // Set up a 1 second time window
        weightedAvgView = epService.getEPAdministrator().createEQL(
                "select * from " + SupportMarketDataBean.class.getName() +
                "(symbol='" + SYMBOL + "').win:time(3.0).stat:weighted_avg('price', 'volume')");
        weightedAvgView.addListener(testListener);
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
        return new SupportMarketDataBean(symbol, price, volume, "");
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
