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

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.*;

public class TestViewGroupBy extends TestCase
{
    private static String SYMBOL_CISCO = "CSCO.O";
    private static String SYMBOL_IBM = "IBM.N";
    private static String SYMBOL_GE = "GE.N";

    private EPServiceProvider epService;

    private SupportUpdateListener priceLast3StatsListener;
    private SupportUpdateListener priceAllStatsListener;
    private SupportUpdateListener volumeLast3StatsListener;
    private SupportUpdateListener volumeAllStatsListener;

    private EPStatement priceLast3Stats;
    private EPStatement priceAllStats;
    private EPStatement volumeLast3Stats;
    private EPStatement volumeAllStats;

    public void setUp()
    {
        priceLast3StatsListener = new SupportUpdateListener();
        priceAllStatsListener = new SupportUpdateListener();
        volumeLast3StatsListener = new SupportUpdateListener();
        volumeAllStatsListener = new SupportUpdateListener();

        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testSelfJoin() {
        // ESPER-528
        epService.getEPAdministrator().createEPL("create schema Product (product string, productsize int)");

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        String query =
                " @Hint('reclaim_group_aged=1,reclaim_group_freq=1') select Product.product as product, Product.productsize as productsize from Product unidirectional" +
                " left outer join Product.win:time(3 seconds).std:groupwin(product,productsize).std:size() PrevProduct on Product.product=PrevProduct.product and Product.productsize=PrevProduct.productsize" +
                " having PrevProduct.size<2";
        epService.getEPAdministrator().createEPL(query);

        // Set to larger number of executions and monitor memory
        for (int i = 0; i < 10; i++) {
            sendProductNew("The id of this product is deliberately very very long so that we can use up more memory per instance of this event sent into Esper " + i, i);
            epService.getEPRuntime().sendEvent(new CurrentTimeEvent(i * 100));
            //if (i % 2000 == 0) {
            //    System.out.println("i=" + i + "; Allocated: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "; Free: " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
            //}
        }
    }

    private void sendProductNew(String product, int size) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("product", product);
        event.put("productsize", size);
        epService.getEPRuntime().sendEvent(event, "Product");
    }

    public void testReclaimTimeWindow()
    {
        sendTimer(0);

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("@Hint('reclaim_group_aged=30,reclaim_group_freq=5') " +
                "select longPrimitive, count(*) from SupportBean.std:groupwin(string).win:time(3000000)");

        for (int i = 0; i < 10; i++)
        {
            SupportBean event = new SupportBean(Integer.toString(i), i);
            epService.getEPRuntime().sendEvent(event);
        }

        EPServiceProviderSPI spi = (EPServiceProviderSPI) epService;
        int handleCountBefore = spi.getSchedulingService().getScheduleHandleCount();
        assertEquals(10, handleCountBefore);

        sendTimer(1000000);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        int handleCountAfter = spi.getSchedulingService().getScheduleHandleCount();
        assertEquals(1, handleCountAfter);
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    public void testReclaimAgedHint() {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String epl = "@Hint('reclaim_group_aged=5,reclaim_group_freq=1') " +
                "select * from SupportBean.std:groupwin(string).win:keepall()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);

        int maxSlots = 10;
        int maxEventsPerSlot = 1000;
        for (int timeSlot = 0; timeSlot < maxSlots; timeSlot++) {
            epService.getEPRuntime().sendEvent(new CurrentTimeEvent(timeSlot * 1000 + 1));

            for (int i = 0; i < maxEventsPerSlot; i++) {
                epService.getEPRuntime().sendEvent(new SupportBean("E" + timeSlot, 0));
            }
        }
        
        EventBean[] iterator = ArrayAssertionUtil.iteratorToArray(stmt.iterator());
        assertTrue(iterator.length <= 6 * maxEventsPerSlot);
    }

    public void testInvalidGroupByNoChild()
    {
        String stmtText = "select avg(price), symbol from " + SupportMarketDataBean.class.getName() + ".win:length(100).std:groupwin(symbol)";

        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Invalid use of the 'std:groupwin' view, the view requires one or more child views to group, or consider using the group-by clause [select avg(price), symbol from com.espertech.esper.support.bean.SupportMarketDataBean.win:length(100).std:groupwin(symbol)]", ex.getMessage());
        }
    }

    public void testStats()
    {
        EPAdministrator epAdmin = epService.getEPAdministrator();
        String filter = "select * from " + SupportMarketDataBean.class.getName();

        priceLast3Stats = epAdmin.createEPL(filter + ".std:groupwin(symbol).win:length(3).stat:uni(price)");
        priceLast3Stats.addListener(priceLast3StatsListener);

        volumeLast3Stats = epAdmin.createEPL(filter + ".std:groupwin(symbol).win:length(3).stat:uni(volume)");
        volumeLast3Stats.addListener(volumeLast3StatsListener);

        priceAllStats = epAdmin.createEPL(filter + ".std:groupwin(symbol).stat:uni(price)");
        priceAllStats.addListener(priceAllStatsListener);

        volumeAllStats = epAdmin.createEPL(filter + ".std:groupwin(symbol).stat:uni(volume)");
        volumeAllStats.addListener(volumeAllStatsListener);

        Vector<Map<String, Object>> expectedList = new Vector<Map<String, Object>>();
        for (int i = 0; i < 3; i++)
        {
            expectedList.add(new HashMap<String, Object>());
        }

        sendEvent(SYMBOL_CISCO, 25, 50000);
        sendEvent(SYMBOL_CISCO, 26, 60000);
        sendEvent(SYMBOL_IBM, 10, 8000);
        sendEvent(SYMBOL_IBM, 10.5, 8200);
        sendEvent(SYMBOL_GE, 88, 1000);

        ArrayAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 88));
        ArrayAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, 88));
        ArrayAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1000) );
        ArrayAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1000) );

        sendEvent(SYMBOL_CISCO, 27, 70000);
        sendEvent(SYMBOL_CISCO, 28, 80000);

        ArrayAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 26.5d) );
        ArrayAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 65000d) );
        ArrayAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 27d) );
        ArrayAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 70000d) );

        sendEvent(SYMBOL_IBM, 11, 8700);
        sendEvent(SYMBOL_IBM, 12, 8900);

        ArrayAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 10.875d) );
        ArrayAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 8450d) );
        ArrayAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 11d + 1/6d) );
        ArrayAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 8600d) );

        sendEvent(SYMBOL_GE, 85.5, 950);
        sendEvent(SYMBOL_GE, 85.75, 900);
        sendEvent(SYMBOL_GE, 89, 1250);
        sendEvent(SYMBOL_GE, 86, 1200);
        sendEvent(SYMBOL_GE, 85, 1150);

        double averageGE = (88d + 85.5d + 85.75d + 89d + 86d + 85d) / 6d;
        ArrayAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, averageGE) );
        ArrayAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1075d) );
        ArrayAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 86d + 2d/3d) );
        ArrayAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1200d) );

        // Check iterator results
        expectedList.get(0).put("symbol", SYMBOL_CISCO);
        expectedList.get(0).put("average", 26.5d);
        expectedList.get(1).put("symbol", SYMBOL_IBM);
        expectedList.get(1).put("average", 10.875d);
        expectedList.get(2).put("symbol", SYMBOL_GE);
        expectedList.get(2).put("average", averageGE);
        ArrayAssertionUtil.compare(priceAllStats.iterator(), expectedList);

        expectedList.get(0).put("symbol", SYMBOL_CISCO);
        expectedList.get(0).put("average", 27d);
        expectedList.get(1).put("symbol", SYMBOL_IBM);
        expectedList.get(1).put("average", 11d + 1/6d);
        expectedList.get(2).put("symbol", SYMBOL_GE);
        expectedList.get(2).put("average", 86d + 2d/3d);
        ArrayAssertionUtil.compare(priceLast3Stats.iterator(), expectedList);
    }

    public void testLengthWindowGrouped()
    {
        String stmtText = "select symbol, price from " + SupportMarketDataBean.class.getName() + ".std:groupwin(symbol).win:length(2)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendEvent("IBM", 100);
    }

    public void testCorrel()
    {
        // further math tests can be found in the view unit test
        EPAdministrator admin = epService.getEPAdministrator();
        admin.getConfiguration().addEventType("Market", SupportMarketDataBean.class);
        EPStatement statement = admin.createEPL("select * from Market.std:groupwin(symbol).win:length(1000000).stat:correl(price, volume, feed)");
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        assertEquals(Double.class, statement.getEventType().getPropertyType("correlation"));

        String[] fields = new String[] {"symbol", "correlation", "feed"};

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 10.0, 1000L, "f1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", Double.NaN, "f1"});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 1.0, 2L, "f2"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", Double.NaN, "f2"});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 2.0, 4L, "f3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", 1.0, "f3"});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 20.0, 2000L, "f4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", 1.0, "f4"});
    }

    public void testLinest()
    {
        // further math tests can be found in the view unit test
        EPAdministrator admin = epService.getEPAdministrator();
        admin.getConfiguration().addEventType("Market", SupportMarketDataBean.class);
        EPStatement statement = admin.createEPL("select * from Market.std:groupwin(symbol).win:length(1000000).stat:linest(price, volume, feed)");
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        assertEquals(Double.class, statement.getEventType().getPropertyType("slope"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("YIntercept"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("XAverage"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("XStandardDeviationPop"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("XStandardDeviationSample"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("XSum"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("XVariance"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("YAverage"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("YStandardDeviationPop"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("YStandardDeviationSample"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("YSum"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("YVariance"));
        assertEquals(Long.class, statement.getEventType().getPropertyType("dataPoints"));
        assertEquals(Long.class, statement.getEventType().getPropertyType("n"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("sumX"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("sumXSq"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("sumXY"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("sumY"));
        assertEquals(Double.class, statement.getEventType().getPropertyType("sumYSq"));

        String[] fields = new String[] {"symbol", "slope", "YIntercept", "feed"};

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 10.0, 50000L, "f1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", Double.NaN, Double.NaN, "f1"});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 1.0, 1L, "f2"));
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, fields, new Object[] {"DEF", Double.NaN, Double.NaN, "f2"});
        assertEquals(1d, event.get("XAverage"));
        assertEquals(0d, event.get("XStandardDeviationPop"));
        assertEquals(Double.NaN, event.get("XStandardDeviationSample"));
        assertEquals(1d, event.get("XSum"));
        assertEquals(Double.NaN, event.get("XVariance"));
        assertEquals(1d, event.get("YAverage"));
        assertEquals(0d, event.get("YStandardDeviationPop"));
        assertEquals(Double.NaN, event.get("YStandardDeviationSample"));
        assertEquals(1d, event.get("YSum"));
        assertEquals(Double.NaN, event.get("YVariance"));
        assertEquals(1L, event.get("dataPoints"));
        assertEquals(1L, event.get("n"));
        assertEquals(1d, event.get("sumX"));
        assertEquals(1d, event.get("sumXSq"));
        assertEquals(1d, event.get("sumXY"));
        assertEquals(1d, event.get("sumY"));
        assertEquals(1d, event.get("sumYSq"));
        // above computed values tested in more detail in RegressionBean test

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 2.0, 2L, "f3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", 1.0, 0.0, "f3"});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 11.0, 50100L, "f4"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", 100.0, 49000.0, "f4"});
    }

    private void sendEvent(String symbol, double price)
    {
        sendEvent(symbol, price, -1);
    }

    private void sendEvent(String symbol, double price, long volume)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, price, volume, "");
        epService.getEPRuntime().sendEvent(event);
    }

    private List<Map<String, Object>> makeMap(String symbol, double average)
    {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("symbol", symbol);
        result.put("average", average);

        ArrayList<Map<String, Object>> vec = new ArrayList<Map<String, Object>>();
        vec.add(result);

        return vec;
    }
}
