/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.view;

import java.util.Iterator;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestViewLengthWindowStats extends TestCase
{
    private static String SYMBOL = "CSCO.O";
    private static String FEED = "feed1";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement statement;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }
    
    public void testIterator()
    {
        String viewExpr = "select symbol, price from " + SupportMarketDataBean.class.getName() + ".win:length(2)";
        statement = epService.getEPAdministrator().createEPL(viewExpr);
        statement.addListener(testListener);

        sendEvent("ABC", 20);
        sendEvent("DEF", 100);

        // check iterator results
        Iterator<EventBean> events = statement.iterator();
        EventBean event = events.next();
        assertEquals("ABC", event.get("symbol"));
        assertEquals(20d, event.get("price"));

        event = events.next();
        assertEquals("DEF", event.get("symbol"));
        assertEquals(100d, event.get("price"));
        assertFalse(events.hasNext());

        sendEvent("EFG", 50);

        // check iterator results
        events = statement.iterator();
        event = events.next();
        assertEquals("DEF", event.get("symbol"));
        assertEquals(100d, event.get("price"));

        event = events.next();
        assertEquals("EFG", event.get("symbol"));
        assertEquals(50d, event.get("price"));
    }

    public void testWindowStats()
    {
        String viewExpr = "select irstream * from " + SupportMarketDataBean.class.getName() +
                "(symbol='" + SYMBOL + "').win:length(3).stat:uni(price, symbol, feed)";
        statement = epService.getEPAdministrator().createEPL(viewExpr);
        statement.addListener(testListener);
        testListener.reset();

        assertEquals(Double.class, statement.getEventType().getPropertyType("average"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("variance"));
        assertEquals(Long.class, statement.getEventType().getPropertyType("datapoints"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("total"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("stddev"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("stddevpa"));

        sendEvent(SYMBOL, 100);
        checkOld(true, 0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        checkNew(1, 100, 100, 0, Double.NaN, Double.NaN);

        sendEvent(SYMBOL, 100.5);
        checkOld(false, 1, 100, 100, 0, Double.NaN, Double.NaN);
        checkNew(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);

        sendEvent("DUMMY", 100.5);
        assertTrue(testListener.getLastNewData() == null);
        assertTrue(testListener.getLastOldData() == null);

        sendEvent(SYMBOL, 100.7);
        checkOld(false, 2, 200.5, 100.25, 0.25, 0.353553391, 0.125);
        checkNew(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);

        sendEvent(SYMBOL, 100.6);
        checkOld(false, 3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);
        checkNew(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);

        sendEvent(SYMBOL, 100.9);
        checkOld(false, 3, 301.8, 100.6, 0.081649658, 0.1, 0.01);
        checkNew(3, 302.2, 100.733333333, 0.124721913, 0.152752523, 0.023333333);
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, price, 0L, FEED);
        epService.getEPRuntime().sendEvent(event);
    }

    private void checkNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        Iterator<EventBean> iterator = statement.iterator();
        checkValues(iterator.next(), false, false, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(testListener.getLastNewData().length == 1);
        EventBean childViewValues = testListener.getLastNewData()[0];
        checkValues(childViewValues, false, false, countE, sumE, avgE, stdevpaE, stdevE, varianceE);

        testListener.reset();
    }

    private void checkOld(boolean isFirst, long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        assertTrue(testListener.getLastOldData().length == 1);
        EventBean childViewValues = testListener.getLastOldData()[0];
        checkValues(childViewValues, isFirst, false, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
    }

    private void checkValues(EventBean values, boolean isFirst, boolean isNewData, long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        long count = getLongValue(ViewFieldEnum.UNIVARIATE_STATISTICS__DATAPOINTS, values);
        double sum = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__TOTAL, values);
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
        if (isFirst && !isNewData) {
            assertEquals(null, values.get("symbol"));
            assertEquals(null, values.get("feed"));
        }
        else {
            assertEquals(SYMBOL, values.get("symbol"));
            assertEquals(FEED, values.get("feed"));
        }
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
