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
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.client.EventBean;

public class TestViewTimeLengthBatch extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportMarketDataBean[] events;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        events = new SupportMarketDataBean[100];
        for (int i = 0; i < events.length; i++)
        {
            events[i] = new SupportMarketDataBean("S" + Integer.toString(i), "id_" + Integer.toString(i), i);
        }
    }

    public void testTimeLengthBatch()
    {
        final long startTime = 1000;
        sendTimer(startTime);
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(10 sec, 3)");
        stmt.addListener(listener);
        EPRuntime engine = epService.getEPRuntime();

        // Send 3 events in batch
        engine.sendEvent(events[0]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[1]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[2]);
        assertEquals(1, listener.getNewDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[0], events[1], events[2]});
        listener.reset();

        // Send another 3 events in batch
        engine.sendEvent(events[3]);
        engine.sendEvent(events[4]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[5]);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[0], events[1], events[2]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[3], events[4], events[5]});
        listener.reset();

        // Expire the last 3 events by moving time
        sendTimer(startTime + 9999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 10000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[3], events[4], events[5]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();

        sendTimer(startTime + 10001);
        assertFalse(listener.isInvoked());

        // Send an event, let the timer send the batch
        sendTimer(startTime + 10100);
        engine.sendEvent(events[6]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 19999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 20000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[6]});
        listener.reset();

        sendTimer(startTime + 20001);
        assertFalse(listener.isInvoked());

        // Send two events, let the timer send the batch
        sendTimer(startTime + 29998);
        engine.sendEvent(events[7]);
        engine.sendEvent(events[8]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 29999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 30000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[6]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[7], events[8]});
        listener.reset();

        // Send three events, the the 3 events batch
        sendTimer(startTime + 30001);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[9]);
        engine.sendEvent(events[10]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[11]);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[7], events[8]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[9], events[10], events[11]});
        listener.reset();

        // Send 1 event, let the timer to do the batch
        sendTimer(startTime + 39000 + 9999);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[12]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 10000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[9], events[10], events[11]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[12]});
        listener.reset();

        sendTimer(startTime + 39000 + 10001);
        assertFalse(listener.isInvoked());

        // Send no events, let the timer to do the batch
        sendTimer(startTime + 39000 + 19999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 20000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[12]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();

        sendTimer(startTime + 39000 + 20001);
        assertFalse(listener.isInvoked());

        // Send no events, let the timer to do NO batch
        sendTimer(startTime + 39000 + 29999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 30000);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 30001);
        assertFalse(listener.isInvoked());

        // Send 1 more event
        sendTimer(startTime + 90000);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[13]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 99999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 100000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[13]});
        listener.reset();
    }

    public void testTimeLengthBatchForceOutput()
    {
        final long startTime = 1000;
        sendTimer(startTime);

        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(10 sec, 3, 'FORCE_UPDATE')");
        stmt.addListener(listener);
        EPRuntime engine = epService.getEPRuntime();

        // Send 3 events in batch
        engine.sendEvent(events[0]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[1]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[2]);
        assertEquals(1, listener.getNewDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[0], events[1], events[2]});
        listener.reset();

        // Send another 3 events in batch
        engine.sendEvent(events[3]);
        engine.sendEvent(events[4]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[5]);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[0], events[1], events[2]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[3], events[4], events[5]});
        listener.reset();

        // Expire the last 3 events by moving time
        sendTimer(startTime + 9999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 10000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[3], events[4], events[5]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();

        sendTimer(startTime + 10001);
        assertFalse(listener.isInvoked());

        // Send an event, let the timer send the batch
        sendTimer(startTime + 10100);
        engine.sendEvent(events[6]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 19999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 20000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[6]});
        listener.reset();

        sendTimer(startTime + 20001);
        assertFalse(listener.isInvoked());

        // Send two events, let the timer send the batch
        sendTimer(startTime + 29998);
        engine.sendEvent(events[7]);
        engine.sendEvent(events[8]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 29999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 30000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[6]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[7], events[8]});
        listener.reset();

        // Send three events, the the 3 events batch
        sendTimer(startTime + 30001);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[9]);
        engine.sendEvent(events[10]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[11]);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[7], events[8]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[9], events[10], events[11]});
        listener.reset();

        // Send 1 event, let the timer to do the batch
        sendTimer(startTime + 39000 + 9999);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[12]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 10000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[9], events[10], events[11]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[12]});
        listener.reset();

        sendTimer(startTime + 39000 + 10001);
        assertFalse(listener.isInvoked());

        // Send no events, let the timer to do the batch
        sendTimer(startTime + 39000 + 19999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 20000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[12]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();

        sendTimer(startTime + 39000 + 20001);
        assertFalse(listener.isInvoked());

        // Send no events, let the timer do a batch
        sendTimer(startTime + 39000 + 29999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 30000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getOldDataList().size());
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();

        sendTimer(startTime + 39000 + 30001);
        assertFalse(listener.isInvoked());

        // Send no events, let the timer do a batch
        sendTimer(startTime + 39000 + 39999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 39000 + 40000);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();

        sendTimer(startTime + 39000 + 40001);
        assertFalse(listener.isInvoked());

        // Send 1 more event
        sendTimer(startTime + 80000);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[13]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 88999);   // 10 sec from last batch
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 89000);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[13]});
        listener.reset();

        // Send 3 more events
        sendTimer(startTime + 90000);
        engine.sendEvent(events[14]);
        engine.sendEvent(events[15]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 92000);
        engine.sendEvent(events[16]);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[13]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {events[14], events[15], events[16]});
        listener.reset();

        // Send no events, let the timer do a batch
        sendTimer(startTime + 101999);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 102000);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getOldDataListFlattened(), new Object[] {events[14], events[15], events[16]});
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(listener.getNewDataListFlattened(), new Object[] {});
        listener.reset();
    }

    public void testTimeLengthBatchForceOutputSum()
    {
        final long startTime = 1000;
        sendTimer(startTime);

        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select sum(price) from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(10 sec, 3, 'FORCE_UPDATE')");
        stmt.addListener(listener);
        EPRuntime engine = epService.getEPRuntime();

        // Send 1 events in batch
        engine.sendEvent(events[10]);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 10000);
        assertEquals(10.0, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();

        sendTimer(startTime + 20000);
        assertEquals(null, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();

        sendTimer(startTime + 30000);
        assertEquals(null, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();

        sendTimer(startTime + 40000);
        assertEquals(null, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();
    }

    public void testForceOutputStartEagerSum()
    {
        final long startTime = 1000;
        sendTimer(startTime);

        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select sum(price) from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(10 sec, 3, 'force_update, start_eager')");
        stmt.addListener(listener);
        EPRuntime engine = epService.getEPRuntime();
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 9999);
        assertFalse(listener.isInvoked());

        // Send batch off
        sendTimer(startTime + 10000);
        assertEquals(null, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();

        // Send batch off
        sendTimer(startTime + 20000);
        assertEquals(null, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();

        engine.sendEvent(events[11]);
        engine.sendEvent(events[12]);
        sendTimer(startTime + 30000);
        assertEquals(23.0, listener.getLastNewData()[0].get("sum(price)"));
        listener.reset();
    }

    public void testForceOutputStartNoEagerSum()
    {
        final long startTime = 1000;
        sendTimer(startTime);

        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select sum(price) from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(10 sec, 3, 'force_update')");
        stmt.addListener(listener);

        // No batch as we are not start eager
        sendTimer(startTime + 10000);
        assertFalse(listener.isInvoked());

        // No batch as we are not start eager
        sendTimer(startTime + 20000);
        assertFalse(listener.isInvoked());
    }

    public void testPreviousAndPrior()
    {
        final long startTime = 1000;
        sendTimer(startTime);
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select price, prev(1, price) as prevPrice, prior(1, price) as priorPrice from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(10 sec, 3)");
        stmt.addListener(listener);
        EPRuntime engine = epService.getEPRuntime();

        // Send 3 events in batch
        engine.sendEvent(events[0]);
        engine.sendEvent(events[1]);
        assertFalse(listener.isInvoked());

        engine.sendEvent(events[2]);
        assertEquals(1, listener.getNewDataList().size());
        EventBean[] events = listener.getLastNewData();
        assertData(events[0], 0, null, null);
        assertData(events[1], 1.0, 0.0, 0.0);
        assertData(events[2], 2.0, 1.0, 1.0);
        listener.reset();
    }

    public void testGroupBySumStartEager()
    {
        final long startTime = 1000;
        sendTimer(startTime);

        EPRuntime engine = epService.getEPRuntime();
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select symbol, sum(price) as s from " + SupportMarketDataBean.class.getName() +
                ".win:time_length_batch(5, 10, \"START_EAGER\") group by symbol order by symbol asc");
        stmt.addListener(listener);

        sendTimer(startTime + 4000);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 6000);
        assertEquals(1, listener.getNewDataList().size());
        EventBean[] events = listener.getLastNewData();
        assertNull(events);
        listener.reset();

        sendTimer(startTime + 7000);
        engine.sendEvent(new SupportMarketDataBean("S1", "e1", 10d));

        sendTimer(startTime + 8000);
        engine.sendEvent(new SupportMarketDataBean("S2", "e2", 77d));

        sendTimer(startTime + 9000);
        engine.sendEvent(new SupportMarketDataBean("S1", "e3", 1d));

        sendTimer(startTime + 10000);
        assertFalse(listener.isInvoked());

        sendTimer(startTime + 11000);
        assertEquals(1, listener.getNewDataList().size());
        events = listener.getLastNewData();
        assertEquals(2, events.length);
        assertEquals("S1", events[0].get("symbol"));
        assertEquals(11d, events[0].get("s"));
        assertEquals("S2", events[1].get("symbol"));
        assertEquals(77d, events[1].get("s"));
        listener.reset();
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void assertData(EventBean event, double price, Double prevPrice, Double priorPrice)
    {
        assertEquals(price, event.get("price"));
        assertEquals(prevPrice, event.get("prevPrice"));
        assertEquals(priorPrice, event.get("priorPrice"));
    }
}
