package net.esper.regression.view;

import junit.framework.TestCase;

import java.util.*;

import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.EventPropertyAssertionUtil;
import net.esper.support.bean.SupportMarketDataBean;

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

        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        EPAdministrator epAdmin = epService.getEPAdministrator();

        String filter = "select * from " + SupportMarketDataBean.class.getName();

        priceLast3Stats = epAdmin.createEQL(filter + ".std:groupby('symbol').win:length(3).stat:uni('price')");
        priceLast3Stats.addListener(priceLast3StatsListener);

        volumeLast3Stats = epAdmin.createEQL(filter + ".std:groupby('symbol').win:length(3).stat:uni('volume')");
        volumeLast3Stats.addListener(volumeLast3StatsListener);

        priceAllStats = epAdmin.createEQL(filter + ".std:groupby('symbol').stat:uni('price')");
        priceAllStats.addListener(priceAllStatsListener);

        volumeAllStats = epAdmin.createEQL(filter + ".std:groupby('symbol').stat:uni('volume')");
        volumeAllStats.addListener(volumeAllStatsListener);
    }

    public void testStats()
    {
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

        EventPropertyAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 88));
        EventPropertyAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, 88));
        EventPropertyAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1000) );
        EventPropertyAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1000) );

        sendEvent(SYMBOL_CISCO, 27, 70000);
        sendEvent(SYMBOL_CISCO, 28, 80000);

        EventPropertyAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 26.5d) );
        EventPropertyAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 65000d) );
        EventPropertyAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 27d) );
        EventPropertyAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_CISCO, 70000d) );

        sendEvent(SYMBOL_IBM, 11, 8700);
        sendEvent(SYMBOL_IBM, 12, 8900);

        EventPropertyAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 10.875d) );
        EventPropertyAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 8450d) );
        EventPropertyAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 11d + 1/6d) );
        EventPropertyAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_IBM, 8600d) );

        sendEvent(SYMBOL_GE, 85.5, 950);
        sendEvent(SYMBOL_GE, 85.75, 900);
        sendEvent(SYMBOL_GE, 89, 1250);
        sendEvent(SYMBOL_GE, 86, 1200);
        sendEvent(SYMBOL_GE, 85, 1150);

        double averageGE = (88d + 85.5d + 85.75d + 89d + 86d + 85d) / 6d;
        EventPropertyAssertionUtil.compare(priceAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, averageGE) );
        EventPropertyAssertionUtil.compare(volumeAllStatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1075d) );
        EventPropertyAssertionUtil.compare(priceLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 86d + 2d/3d) );
        EventPropertyAssertionUtil.compare(volumeLast3StatsListener.getLastNewData(), makeMap(SYMBOL_GE, 1200d) );

        // Check iterator results
        expectedList.get(0).put("symbol", SYMBOL_CISCO);
        expectedList.get(0).put("average", 26.5d);
        expectedList.get(1).put("symbol", SYMBOL_IBM);
        expectedList.get(1).put("average", 10.875d);
        expectedList.get(2).put("symbol", SYMBOL_GE);
        expectedList.get(2).put("average", averageGE);
        EventPropertyAssertionUtil.compare(priceAllStats.iterator(), expectedList);

        expectedList.get(0).put("symbol", SYMBOL_CISCO);
        expectedList.get(0).put("average", 27d);
        expectedList.get(1).put("symbol", SYMBOL_IBM);
        expectedList.get(1).put("average", 11d + 1/6d);
        expectedList.get(2).put("symbol", SYMBOL_GE);
        expectedList.get(2).put("average", 86d + 2d/3d);
        EventPropertyAssertionUtil.compare(priceLast3Stats.iterator(), expectedList);
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
