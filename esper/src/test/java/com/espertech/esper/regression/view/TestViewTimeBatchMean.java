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

import junit.framework.TestCase;

import java.util.Iterator;

import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.client.EventBean;

public class TestViewTimeBatchMean extends TestCase
{
    private static String SYMBOL = "CSCO.O";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement timeBatchMean;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        // Set up a 2 second time window
        timeBatchMean = epService.getEPAdministrator().createEPL(
                "select * from " + SupportMarketDataBean.class.getName() +
                "(symbol='" + SYMBOL + "').win:time_batch(2).stat:uni(volume)");
        timeBatchMean.addListener(testListener);
    }

    public void testTimeBatchMean()
    {
        testListener.reset();
        checkMeanIterator(Double.NaN);
        assertFalse(testListener.isInvoked());

        // Send a couple of events, check mean
        sendEvent(SYMBOL, 500);
        sendEvent(SYMBOL, 1000);
        checkMeanIterator(Double.NaN);              // The iterator is still showing no result yet as no batch was released
        assertFalse(testListener.isInvoked());      // No new data posted to the iterator, yet

        // Sleep for 1 seconds
        sleep(1000);

        // Send more events
        sendEvent(SYMBOL, 1000);
        sendEvent(SYMBOL, 1200);
        checkMeanIterator(Double.NaN);              // The iterator is still showing no result yet as no batch was released
        assertFalse(testListener.isInvoked());

        // Sleep for 1.5 seconds, thus triggering a new batch
        sleep(1500);
        checkMeanIterator(925);                 // Now the statistics view received the first batch
        assertTrue(testListener.isInvoked());   // Listener has been invoked
        checkMeanListener(925);

        // Send more events
        sendEvent(SYMBOL, 500);
        sendEvent(SYMBOL, 600);
        sendEvent(SYMBOL, 1000);
        checkMeanIterator(925);              // The iterator is still showing the old result as next batch not released
        assertFalse(testListener.isInvoked());

        // Sleep for 1 seconds
        sleep(1000);

        // Send more events
        sendEvent(SYMBOL, 200);
        checkMeanIterator(925);
        assertFalse(testListener.isInvoked());

        // Sleep for 1.5 seconds, thus triggering a new batch
        sleep(1500);
        checkMeanIterator(2300d / 4d); // Now the statistics view received the second batch, the mean now is over all events
        assertTrue(testListener.isInvoked());   // Listener has been invoked
        checkMeanListener(2300d / 4d);

        // Send more events
        sendEvent(SYMBOL, 1200);
        checkMeanIterator(2300d / 4d);
        assertFalse(testListener.isInvoked());

        // Sleep for 2 seconds, no events received anymore
        sleep(2000);
        checkMeanIterator(1200); // statistics view received the third batch
        assertTrue(testListener.isInvoked());   // Listener has been invoked
        checkMeanListener(1200);

        // try to compile with flow control, these are tested elsewhere
        epService.getEPAdministrator().createEPL("select * from SupportBean.win:time_batch(10 sec, 'FORCE_UPDATE, START_EAGER')");
    }

    private void sendEvent(String symbol, long volume)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private void checkMeanListener(double meanExpected)
    {
        assertTrue(testListener.getLastNewData().length == 1);
        EventBean listenerValues = testListener.getLastNewData()[0];
        checkValue(listenerValues, meanExpected);
        testListener.reset();
    }

    private void checkMeanIterator(double meanExpected)
    {
        Iterator<EventBean> iterator = timeBatchMean.iterator();
        checkValue(iterator.next(), meanExpected);
        assertTrue(iterator.hasNext() == false);
    }

    private void checkValue(EventBean values, double avgE)
    {
        double avg = getDoubleValue(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE, values);
        assertTrue(DoubleValueAssertionUtil.equals(avg,  avgE, 6));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean event)
    {
        return (Double) event.get(field.getName());
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
