package net.esper.regression.view;

import java.util.Iterator;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.view.ViewFieldEnum;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.DoubleValueAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

public class TestViewLengthWindowStats extends TestCase
{
    private static String SYMBOL = "CSCO.O";
            
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement priceStatsView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        String viewExpr = "select * from " + SupportMarketDataBean.class.getName() +
                "(symbol='" + SYMBOL + "').win:length(3).stat:uni('price')";
        priceStatsView = epService.getEPAdministrator().createEQL(viewExpr);
        priceStatsView.addListener(testListener);
    }
    
    public void testWindowStats()
    {
        testListener.reset();

        sendEvent(SYMBOL, 100);
        checkOld(0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        checkNew(1, 100, 100, 0, Double.NaN, Double.NaN);

        sendEvent(SYMBOL, 100.5);
        checkOld(1, 100, 100, 0, Double.NaN, Double.NaN);
        checkNew(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);

        sendEvent("DUMMY", 100.5);
        assertTrue(testListener.getLastNewData() == null);
        assertTrue(testListener.getLastOldData() == null);

        sendEvent(SYMBOL, 100.7);
        checkOld(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);
        checkNew(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);

        sendEvent(SYMBOL, 100.6);
        checkOld(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);
        checkNew(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);

        sendEvent(SYMBOL, 100.9);
        checkOld(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);
        checkNew(3, 302.2, 100.733333333, 0.124721913, 0.152752523, 0.023333333);
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, price, 0L, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void checkNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        Iterator<EventBean> iterator = priceStatsView.iterator();
        checkValues(iterator.next(), countE, sumE, avgE, stdevpaE, stdevE, varianceE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(testListener.getLastNewData().length == 1);
        EventBean childViewValues = testListener.getLastNewData()[0];
        checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);

        testListener.reset();
    }

    private void checkOld(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        assertTrue(testListener.getLastOldData().length == 1);
        EventBean childViewValues = testListener.getLastOldData()[0];
        checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
    }

    private void checkValues(EventBean values, long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        long count = getLongValue(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT, values);
        double sum = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM, values);
        double avg = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE, values);
        double stdevpa = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA, values);
        double stdev = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV, values);
        double variance = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE, values);

        assertEquals(count, countE);
        assertTrue(DoubleValueAssertionUtil.equals(sum,  sumE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(avg,  avgE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(stdevpa,  stdevpaE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(stdev,  stdevE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(variance,  varianceE, 6));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean values)
    {
        return (Double) values.get(field.getName());
    }

    private long getLongValue(ViewFieldEnum field, EventBean values)
    {
        return (Long) values.get(field.getName());
    }
}
