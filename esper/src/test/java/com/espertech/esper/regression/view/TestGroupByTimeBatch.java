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
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;

public class TestGroupByTimeBatch extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MarketData", SupportMarketDataBean.class);
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testTimeBatchRowForAllNoJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream sum(price) as sumPrice from MarketData.win:time_batch(1 sec)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // send first batch
        sendMDEvent("DELL", 10, 0L);
        sendMDEvent("IBM", 15, 0L);
        sendMDEvent("DELL", 20, 0L);
        sendTimer(1000);

        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], 45d);

        // send second batch
        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);

        newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], 20d);

        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(1, oldEvents.length);
        assertEvent(oldEvents[0], 45d);
    }

    public void testTimeBatchRowForAllJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream sum(price) as sumPrice from MarketData.win:time_batch(1 sec) as S0, SupportBean.win:keepall() as S1 where S0.symbol = S1.string";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendSupportEvent("DELL");
        sendSupportEvent("IBM");

        // send first batch
        sendMDEvent("DELL", 10, 0L);
        sendMDEvent("IBM", 15, 0L);
        sendMDEvent("DELL", 20, 0L);
        sendTimer(1000);

        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], 45d);

        // send second batch
        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);

        newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], 20d);

        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(1, oldEvents.length);
        assertEvent(oldEvents[0], 45d);
    }

    public void testTimeBatchAggregateAllNoJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream symbol, sum(price) as sumPrice from MarketData.win:time_batch(1 sec)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // send first batch
        sendMDEvent("DELL", 10, 0L);
        sendMDEvent("IBM", 15, 0L);
        sendMDEvent("DELL", 20, 0L);
        sendTimer(1000);

        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(3, newEvents.length);
        assertEvent(newEvents[0], "DELL", 45d);
        assertEvent(newEvents[1], "IBM", 45d);
        assertEvent(newEvents[2], "DELL", 45d);

        // send second batch
        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);

        newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], "IBM", 20d);

        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(3, oldEvents.length);
        assertEvent(oldEvents[0], "DELL", 20d);
        assertEvent(oldEvents[1], "IBM", 20d);
        assertEvent(oldEvents[2], "DELL", 20d);
    }

    public void testTimeBatchAggregateAllJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream symbol, sum(price) as sumPrice from MarketData.win:time_batch(1 sec) as S0, SupportBean.win:keepall() as S1 where S0.symbol = S1.string";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendSupportEvent("DELL");
        sendSupportEvent("IBM");

        // send first batch
        sendMDEvent("DELL", 10, 0L);
        sendMDEvent("IBM", 15, 0L);
        sendMDEvent("DELL", 20, 0L);
        sendTimer(1000);

        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(3, newEvents.length);
        assertEvent(newEvents[0], "DELL", 45d);
        assertEvent(newEvents[1], "IBM", 45d);
        assertEvent(newEvents[2], "DELL", 45d);

        // send second batch
        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);

        newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], "IBM", 20d);

        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(3, oldEvents.length);
        assertEvent(oldEvents[0], "DELL", 20d);
        assertEvent(oldEvents[1], "IBM", 20d);
        assertEvent(oldEvents[2], "DELL", 20d);
    }

    public void testTimeBatchRowPerGroupNoJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream symbol, sum(price) as sumPrice from MarketData.win:time_batch(1 sec) group by symbol order by symbol asc";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // send first batch
        sendMDEvent("DELL", 10, 0L);
        sendMDEvent("IBM", 15, 0L);
        sendMDEvent("DELL", 20, 0L);
        sendTimer(1000);

        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(2, newEvents.length);
        assertEvent(newEvents[0], "DELL", 30d);
        assertEvent(newEvents[1], "IBM", 15d);

        // send second batch
        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);

        newEvents = listener.getLastNewData();
        assertEquals(2, newEvents.length);
        assertEvent(newEvents[0], "DELL", null);
        assertEvent(newEvents[1], "IBM", 20d);

        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(2, oldEvents.length);
        assertEvent(oldEvents[0], "DELL", 30d);
        assertEvent(oldEvents[1], "IBM", 15d);
    }

    public void testTimeBatchRowPerGroupJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream symbol, sum(price) as sumPrice " +
                         " from MarketData.win:time_batch(1 sec) as S0, SupportBean.win:keepall() as S1" +
                         " where S0.symbol = S1.string " +
                         " group by symbol";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendSupportEvent("DELL");
        sendSupportEvent("IBM");

        // send first batch
        sendMDEvent("DELL", 10, 0L);
        sendMDEvent("IBM", 15, 0L);
        sendMDEvent("DELL", 20, 0L);
        sendTimer(1000);

        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(2, newEvents.length);
        assertEvent(newEvents[0], "DELL", 30d);
        assertEvent(newEvents[1], "IBM", 15d);

        // send second batch
        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);

        newEvents = listener.getLastNewData();
        assertEquals(2, newEvents.length);
        assertEvent(newEvents[0], "DELL", null);
        assertEvent(newEvents[1], "IBM", 20d);

        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(2, oldEvents.length);
        assertEvent(oldEvents[0], "DELL", 30d);
        assertEvent(oldEvents[1], "IBM", 15d);
    }

    public void testTimeBatchAggrGroupedNoJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream symbol, sum(price) as sumPrice, volume from MarketData.win:time_batch(1 sec) group by symbol";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendMDEvent("DELL", 10, 200L);
        sendMDEvent("IBM", 15, 500L);
        sendMDEvent("DELL", 20, 250L);

        sendTimer(1000);
        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(3, newEvents.length);
        assertEvent(newEvents[0], "DELL", 30d, 200L);
        assertEvent(newEvents[1], "IBM", 15d, 500L);
        assertEvent(newEvents[2], "DELL", 30d, 250L);

        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);
        newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], "IBM", 20d, 600L);
        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(3, oldEvents.length);
        assertEvent(oldEvents[0], "DELL", null, 200L);
        assertEvent(oldEvents[1], "IBM", 20d, 500L);
        assertEvent(oldEvents[2], "DELL", null, 250L);
    }

    public void testTimeBatchAggrGroupedJoin()
    {
        sendTimer(0);
        String stmtText = "select irstream symbol, sum(price) as sumPrice, volume " +
                          "from MarketData.win:time_batch(1 sec) as S0, SupportBean.win:keepall() as S1" +
                          " where S0.symbol = S1.string " +
                          " group by symbol";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        sendSupportEvent("DELL");
        sendSupportEvent("IBM");

        sendMDEvent("DELL", 10, 200L);
        sendMDEvent("IBM", 15, 500L);
        sendMDEvent("DELL", 20, 250L);

        sendTimer(1000);
        EventBean[] newEvents = listener.getLastNewData();
        assertEquals(3, newEvents.length);
        assertEvent(newEvents[0], "DELL", 30d, 200L);
        assertEvent(newEvents[1], "IBM", 15d, 500L);
        assertEvent(newEvents[2], "DELL", 30d, 250L);

        sendMDEvent("IBM", 20, 600L);
        sendTimer(2000);
        newEvents = listener.getLastNewData();
        assertEquals(1, newEvents.length);
        assertEvent(newEvents[0], "IBM", 20d, 600L);
        EventBean[] oldEvents = listener.getLastOldData();
        assertEquals(3, oldEvents.length);
        assertEvent(oldEvents[0], "DELL", null, 200L);
        assertEvent(oldEvents[1], "IBM", 20d, 500L);
        assertEvent(oldEvents[2], "DELL", null, 250L);
    }

    private void sendSupportEvent(String string)
    {
        epService.getEPRuntime().sendEvent(new SupportBean(string, -1));
    }

    private void sendMDEvent(String symbol, double price, Long volume)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, price, volume, null));
    }

    private void assertEvent(EventBean event, String symbol, Double sumPrice, Long volume)
    {
        assertEquals(symbol, event.get("symbol"));
        assertEquals(sumPrice, event.get("sumPrice"));
        assertEquals(volume, event.get("volume"));
    }

    private void assertEvent(EventBean event, String symbol, Double sumPrice)
    {
        assertEquals(symbol, event.get("symbol"));
        assertEquals(sumPrice, event.get("sumPrice"));
    }

    private void assertEvent(EventBean event, Double sumPrice)
    {
        assertEquals(sumPrice, event.get("sumPrice"));
    }

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
