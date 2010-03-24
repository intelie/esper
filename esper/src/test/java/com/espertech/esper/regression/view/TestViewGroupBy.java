package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
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

    public void testInvalidGroupByNoChild()
    {
        String stmtText = "select avg(price), symbol from " + SupportMarketDataBean.class.getName() + ".win:length(100).std:groupby(symbol)";

        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting statement: Invalid use of the 'std:groupby' view, the view requires one or more child views to group, or consider using the group-by clause [select avg(price), symbol from com.espertech.esper.support.bean.SupportMarketDataBean.win:length(100).std:groupby(symbol)]", ex.getMessage());
        }
    }

    public void testStats()
    {
        EPAdministrator epAdmin = epService.getEPAdministrator();
        String filter = "select * from " + SupportMarketDataBean.class.getName();

        priceLast3Stats = epAdmin.createEPL(filter + ".std:groupby(symbol).win:length(3).stat:uni(price)");
        priceLast3Stats.addListener(priceLast3StatsListener);

        volumeLast3Stats = epAdmin.createEPL(filter + ".std:groupby(symbol).win:length(3).stat:uni(volume)");
        volumeLast3Stats.addListener(volumeLast3StatsListener);

        priceAllStats = epAdmin.createEPL(filter + ".std:groupby(symbol).stat:uni(price)");
        priceAllStats.addListener(priceAllStatsListener);

        volumeAllStats = epAdmin.createEPL(filter + ".std:groupby(symbol).stat:uni(volume)");
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
        String stmtText = "select symbol, price from " + SupportMarketDataBean.class.getName() + ".std:groupby(symbol).win:length(2)";
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
        EPStatement statement = admin.createEPL("select * from Market.std:groupby(symbol).win:length(1000000).stat:correl(price, volume)");
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        String[] fields = new String[] {"symbol", "correlation"};

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 10.0, 1000L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", Double.NaN});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 1.0, 2L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", Double.NaN});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 2.0, 4L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", 1.0});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 20.0, 2000L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", 1.0});
    }

    public void testLinest()
    {
        // further math tests can be found in the view unit test
        EPAdministrator admin = epService.getEPAdministrator();
        admin.getConfiguration().addEventType("Market", SupportMarketDataBean.class);
        EPStatement statement = admin.createEPL("select * from Market.std:groupby(symbol).win:length(1000000).stat:linest(price, volume)");
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        String[] fields = new String[] {"symbol", "slope", "YIntercept"};

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 10.0, 50000L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", Double.NaN, Double.NaN});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 1.0, 1L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", Double.NaN, Double.NaN});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DEF", 2.0, 2L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"DEF", 1.0, 0.0});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ABC", 11.0, 50100L, null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"ABC", 100.0, 49000.0});
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
