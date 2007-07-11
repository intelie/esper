///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewGroupBy
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

	    [SetUp]
	    public void SetUp()
	    {
	        priceLast3StatsListener = new SupportUpdateListener();
	        priceAllStatsListener = new SupportUpdateListener();
	        volumeLast3StatsListener = new SupportUpdateListener();
	        volumeAllStatsListener = new SupportUpdateListener();

	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void testStats()
	    {
	        EPAdministrator epAdmin = epService.EPAdministrator;
	        String filter = "select * from " + typeof(SupportMarketDataBean).FullName;

	        priceLast3Stats = epAdmin.CreateEQL(filter + ".std:groupby('symbol').win:length(3).stat:uni('price')");
            priceLast3Stats.AddListener(priceLast3StatsListener);

	        volumeLast3Stats = epAdmin.CreateEQL(filter + ".std:groupby('symbol').win:length(3).stat:uni('volume')");
            volumeLast3Stats.AddListener(volumeLast3StatsListener);

	        priceAllStats = epAdmin.CreateEQL(filter + ".std:groupby('symbol').stat:uni('price')");
            priceAllStats.AddListener(priceAllStatsListener);

	        volumeAllStats = epAdmin.CreateEQL(filter + ".std:groupby('symbol').stat:uni('volume')");
            volumeAllStats.AddListener(volumeAllStatsListener);

	        List<EDictionary<String, Object>> expectedList = new List<EDictionary<String, Object>>();
	        for (int i = 0; i < 3; i++)
	        {
	            expectedList.Add(new HashDictionary<String, Object>());
	        }

	        SendEvent(SYMBOL_CISCO, 25, 50000);
	        SendEvent(SYMBOL_CISCO, 26, 60000);
	        SendEvent(SYMBOL_IBM, 10, 8000);
	        SendEvent(SYMBOL_IBM, 10.5, 8200);
	        SendEvent(SYMBOL_GE, 88, 1000);

	        EventPropertyAssertionUtil.Compare(priceLast3StatsListener.LastNewData, MakeMap(SYMBOL_GE, 88));
	        EventPropertyAssertionUtil.Compare(priceAllStatsListener.LastNewData, MakeMap(SYMBOL_GE, 88));
	        EventPropertyAssertionUtil.Compare(volumeLast3StatsListener.LastNewData, MakeMap(SYMBOL_GE, 1000) );
	        EventPropertyAssertionUtil.Compare(volumeAllStatsListener.LastNewData, MakeMap(SYMBOL_GE, 1000) );

	        SendEvent(SYMBOL_CISCO, 27, 70000);
	        SendEvent(SYMBOL_CISCO, 28, 80000);

	        EventPropertyAssertionUtil.Compare(priceAllStatsListener.LastNewData, MakeMap(SYMBOL_CISCO, 26.5d) );
	        EventPropertyAssertionUtil.Compare(volumeAllStatsListener.LastNewData, MakeMap(SYMBOL_CISCO, 65000d) );
	        EventPropertyAssertionUtil.Compare(priceLast3StatsListener.LastNewData, MakeMap(SYMBOL_CISCO, 27d) );
	        EventPropertyAssertionUtil.Compare(volumeLast3StatsListener.LastNewData, MakeMap(SYMBOL_CISCO, 70000d) );

	        SendEvent(SYMBOL_IBM, 11, 8700);
	        SendEvent(SYMBOL_IBM, 12, 8900);

	        EventPropertyAssertionUtil.Compare(priceAllStatsListener.LastNewData, MakeMap(SYMBOL_IBM, 10.875d) );
	        EventPropertyAssertionUtil.Compare(volumeAllStatsListener.LastNewData, MakeMap(SYMBOL_IBM, 8450d) );
	        EventPropertyAssertionUtil.Compare(priceLast3StatsListener.LastNewData, MakeMap(SYMBOL_IBM, 11d + 1/6d) );
	        EventPropertyAssertionUtil.Compare(volumeLast3StatsListener.LastNewData, MakeMap(SYMBOL_IBM, 8600d) );

	        SendEvent(SYMBOL_GE, 85.5, 950);
	        SendEvent(SYMBOL_GE, 85.75, 900);
	        SendEvent(SYMBOL_GE, 89, 1250);
	        SendEvent(SYMBOL_GE, 86, 1200);
	        SendEvent(SYMBOL_GE, 85, 1150);

	        double averageGE = (88d + 85.5d + 85.75d + 89d + 86d + 85d) / 6d;
	        EventPropertyAssertionUtil.Compare(priceAllStatsListener.LastNewData, MakeMap(SYMBOL_GE, averageGE) );
	        EventPropertyAssertionUtil.Compare(volumeAllStatsListener.LastNewData, MakeMap(SYMBOL_GE, 1075d) );
	        EventPropertyAssertionUtil.Compare(priceLast3StatsListener.LastNewData, MakeMap(SYMBOL_GE, 86d + 2d/3d) );
	        EventPropertyAssertionUtil.Compare(volumeLast3StatsListener.LastNewData, MakeMap(SYMBOL_GE, 1200d) );

	        // Check iterator results
	        expectedList[0].Put("symbol", SYMBOL_CISCO);
	        expectedList[0].Put("average", 26.5d);
	        expectedList[1].Put("symbol", SYMBOL_IBM);
	        expectedList[1].Put("average", 10.875d);
	        expectedList[2].Put("symbol", SYMBOL_GE);
	        expectedList[2].Put("average", averageGE);
            EventPropertyAssertionUtil.Compare(priceAllStats.GetEnumerator(), expectedList);

	        expectedList[0].Put("symbol", SYMBOL_CISCO);
	        expectedList[0].Put("average", 27d);
	        expectedList[1].Put("symbol", SYMBOL_IBM);
	        expectedList[1].Put("average", 11d + 1/6d);
	        expectedList[2].Put("symbol", SYMBOL_GE);
	        expectedList[2].Put("average", 86d + 2d/3d);
            EventPropertyAssertionUtil.Compare(priceLast3Stats.GetEnumerator(), expectedList);
	    }

	    [Test]
	    public void testLengthWindowGrouped()
	    {
	        String stmtText = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').win:length(2)";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        SendEvent("IBM", 100);
	    }

	    private void SendEvent(String symbol, double price)
	    {
	        SendEvent(symbol, price, -1);
	    }

	    private void SendEvent(String symbol, double price, long volume)
	    {
	        SupportMarketDataBean _event = new SupportMarketDataBean(symbol, price, volume, "");
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private List<EDictionary<String, Object>> MakeMap(String symbol, double average)
	    {
	        EDictionary<String, Object> result = new HashDictionary<String, Object>();

	        result.Put("symbol", symbol);
	        result.Put("average", average);

	        List<EDictionary<String, Object>> vec = new List<EDictionary<String, Object>>();
	        vec.Add(result);

	        return vec;
	    }
	}
} // End of namespace
