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

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestViewGroupWithinGroup extends TestCase
{
    private String SYMBOL_MSFT = "MSFT";
    private String SYMBOL_GE = "GE";

    private String FEED_INFO = "INFO";
    private String FEED_REU = "REU";

    private EPServiceProvider epService;
    private EPStatement viewGrouped;
    private SupportUpdateListener listener = new SupportUpdateListener();

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        // Listen to all ticks
        viewGrouped = epService.getEPAdministrator().createEPL(
                "select irstream datapoints as size, symbol, feed, volume from " + SupportMarketDataBean.class.getName() +
                ".std:groupwin(symbol).std:groupwin(feed).std:groupwin(volume).stat:uni(price)");

        // Counts per symbol, feed and volume the events
        viewGrouped.addListener(listener);
    }

    public void testPullDateAndPushData()
    {
        ArrayList<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        // Set up a map of expected values

        Map<String, Object> expectedValues[] = new HashMap[10];
        for (int i = 0; i < expectedValues.length; i++)
        {
            expectedValues[i] = new HashMap<String, Object>();
        }

        // Send one event, check results
        sendEvent(SYMBOL_GE, FEED_INFO, 1);

        populateMap(expectedValues[0], SYMBOL_GE, FEED_INFO, 1L, 0);
        mapList.add(expectedValues[0]);
        ArrayAssertionUtil.compare(listener.getLastOldData(), mapList);
        populateMap(expectedValues[0], SYMBOL_GE, FEED_INFO, 1L, 1);
        ArrayAssertionUtil.compare(listener.getLastNewData(), mapList);
        ArrayAssertionUtil.compare(viewGrouped.iterator(), mapList);

        // Send a couple of events
        sendEvent(SYMBOL_GE, FEED_INFO, 1);
        sendEvent(SYMBOL_GE, FEED_INFO, 2);
        sendEvent(SYMBOL_GE, FEED_INFO, 1);
        sendEvent(SYMBOL_GE, FEED_REU, 99);
        sendEvent(SYMBOL_MSFT, FEED_INFO, 100);

        populateMap(expectedValues[1], SYMBOL_MSFT, FEED_INFO, 100, 0);
        mapList.clear();
        mapList.add(expectedValues[1]);
        ArrayAssertionUtil.compare(listener.getLastOldData(), mapList );
        populateMap(expectedValues[1], SYMBOL_MSFT, FEED_INFO, 100, 1);
        ArrayAssertionUtil.compare(listener.getLastNewData(), mapList );

        populateMap(expectedValues[0], SYMBOL_GE, FEED_INFO, 1, 3);
        populateMap(expectedValues[2], SYMBOL_GE, FEED_INFO, 2, 1);
        populateMap(expectedValues[3], SYMBOL_GE, FEED_REU, 99, 1);
        mapList.clear();
        mapList.add(expectedValues[0]);
        mapList.add(expectedValues[2]);
        mapList.add(expectedValues[3]);
        mapList.add(expectedValues[1]);
        ArrayAssertionUtil.compare(viewGrouped.iterator(), mapList);
    }

    private void populateMap(Map<String, Object> map, String symbol, String feed, long volume, long size)
    {
        map.put("symbol", symbol);
        map.put("feed", feed);
        map.put("volume", volume);
        map.put("size", size);
    }

    private void sendEvent(String symbol, String feed, long volume)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, volume, feed);
        epService.getEPRuntime().sendEvent(event);
    }
}
