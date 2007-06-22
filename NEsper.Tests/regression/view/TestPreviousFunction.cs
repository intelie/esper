// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client.time;
using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestPreviousFunction
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestPrevCountStarWithStaticMethod()
	    {
	        String text = "select Count(*) as total, " +
	                      "prev(" + typeof(TestPreviousFunction).FullName + ".IntToLong(count(*)) - 1, price) as firstPrice from " + typeof(SupportMarketDataBean).FullName + ".win:time(60)";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        stmt.AddListener(testListener);

	        AssertPrevCount();
	    }

	    [Test]
	    public void TestPrevCountStar()
	    {
	        String text = "select Count(*) as total, " +
	                      "prev(count(*) - 1, price) as firstPrice from " + typeof(SupportMarketDataBean).FullName + ".win:time(60)";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        stmt.AddListener(testListener);

	        AssertPrevCount();
	    }

	    private void AssertPrevCount()
	    {
	        SendTimer(0);
	        SendMarketEvent("IBM", 75);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 1L, 75D);

	        SendMarketEvent("IBM", 76);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 2L, 75D);

	        SendTimer(10000);
	        SendMarketEvent("IBM", 77);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 3L, 75D);

	        SendTimer(20000);
	        SendMarketEvent("IBM", 78);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 4L, 75D);

	        SendTimer(50000);
	        SendMarketEvent("IBM", 79);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 5L, 75D);

	        SendTimer(60000);
	        Assert.AreEqual(1, testListener.OldDataList.Count);
	        EventBean[] oldData = testListener.LastOldData;
	        Assert.AreEqual(2, oldData.Length);
	        AssertCountAndPrice(oldData[0], 5L, null);
	        testListener.Reset();

	        SendMarketEvent("IBM", 80);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 4L, 77D);

	        SendTimer(65000);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendTimer(70000);
	        Assert.AreEqual(1, testListener.OldDataList.Count);
	        oldData = testListener.LastOldData;
	        Assert.AreEqual(1, oldData.Length);
	        AssertCountAndPrice(oldData[0], 4L, null);
	        testListener.Reset();

	        SendTimer(80000);
	        testListener.Reset();

	        SendMarketEvent("IBM", 81);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 3L, 79D);

	        SendTimer(120000);
	        testListener.Reset();

	        SendMarketEvent("IBM", 82);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 2L, 81D);

	        SendTimer(300000);
	        testListener.Reset();

	        SendMarketEvent("IBM", 83);
	        AssertCountAndPrice(testListener.AssertOneGetNewAndReset(), 1L, 83D);
	    }

	    [Test]
	    public void TestSortWindowPerGroup()
	    {
	        // descending sort
	        String viewExpr = "select symbol, Prev(1, price) as prevPrice, Prev(2, price) as prevPrevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').ext:sort('price', false, 10) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrevPrice"));

	        SendMarketEvent("IBM", 75);
	        AssertReceived("IBM", null, null);
	        SendMarketEvent("IBM", 80);
	        AssertReceived("IBM", 80d, null);
	        SendMarketEvent("IBM", 79);
	        AssertReceived("IBM", 79d, 80d);
	        SendMarketEvent("IBM", 81);
	        AssertReceived("IBM", 79d, 80d);
	        SendMarketEvent("IBM", 79.5);
	        AssertReceived("IBM", 79d, 79.5d);

	        SendMarketEvent("MSFT", 10);
	        AssertReceived("MSFT", null, null);
	        SendMarketEvent("MSFT", 20);
	        AssertReceived("MSFT", 20d, null);
	        SendMarketEvent("MSFT", 21);
	        AssertReceived("MSFT", 20d, 21d);

	        SendMarketEvent("IBM", 74d);
	        AssertReceived("IBM", 75d, 79d);

	        SendMarketEvent("MSFT", 19);
	        AssertReceived("MSFT", 19d, 20d);
	    }

	    [Test]
	    public void TestTimeBatchPerGroup()
	    {
	        String viewExpr = "select symbol, Prev(1, price) as prevPrice, Prev(2, price) as prevPrevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').win:time_batch(1 sec) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrevPrice"));

	        SendTimer(0);
	        SendMarketEvent("IBM", 75);
	        SendMarketEvent("MSFT", 40);
	        SendMarketEvent("IBM", 76);
	        SendMarketEvent("CIC", 1);
	        SendTimer(1000);

	        EventBean[] events = testListener.LastNewData;
	        // order not guaranteed as timed batch, however for testing the order is reliable as schedule buckets are created
	        // in a predictable order
	        // Previous is looking at the same batch, doesn't consider outside of window
	        AssertReceived(events[0], "IBM", null, null);
	        AssertReceived(events[1], "IBM", 75d, null);
	        AssertReceived(events[2], "MSFT", null, null);
	        AssertReceived(events[3], "CIC", null, null);

	        // Next batch, previous is looking only within the same batch
	        SendMarketEvent("MSFT", 41);
	        SendMarketEvent("IBM", 77);
	        SendMarketEvent("IBM", 78);
	        SendMarketEvent("CIC", 2);
	        SendMarketEvent("MSFT", 42);
	        SendMarketEvent("CIC", 3);
	        SendMarketEvent("CIC", 4);
	        SendTimer(2000);

	        events = testListener.LastNewData;
	        AssertReceived(events[0], "IBM", null, null);
	        AssertReceived(events[1], "IBM", 77d, null);
	        AssertReceived(events[2], "MSFT", null, null);
	        AssertReceived(events[3], "MSFT", 41d, null);
	        AssertReceived(events[4], "CIC", null, null);
	        AssertReceived(events[5], "CIC", 2d, null);
	        AssertReceived(events[6], "CIC", 3d, 2d);

	        // test for memory leak - comment in and run with large number
	        /*
	        for (int i = 0; i < 10000; i++)
	        {
	            SendMarketEvent("MSFT", 41);
	            SendTimer(1000 * i);
	            testListener.Reset();
	        }
	        */
	    }

	    [Test]
	    public void TestLengthBatchPerGroup()
	    {
	        String viewExpr = "select symbol, Prev(1, price) as prevPrice, Prev(2, price) as prevPrevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').win:length_batch(3) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrevPrice"));

	        SendMarketEvent("IBM", 75);
	        SendMarketEvent("MSFT", 50);
	        SendMarketEvent("IBM", 76);
	        SendMarketEvent("CIC", 1);
	        Assert.IsFalse(testListener.IsInvoked);
	        SendMarketEvent("IBM", 77);

	        EventBean[] eventsNew = testListener.LastNewData;
	        Assert.AreEqual(3, eventsNew.Length);
	        AssertReceived(eventsNew[0], "IBM", null, null);
	        AssertReceived(eventsNew[1], "IBM", 75d, null);
	        AssertReceived(eventsNew[2], "IBM", 76d, 75d);
	        testListener.Reset();

	        // Next batch, previous is looking only within the same batch
	        SendMarketEvent("MSFT", 51);
	        SendMarketEvent("IBM", 78);
	        SendMarketEvent("IBM", 79);
	        SendMarketEvent("CIC", 2);
	        SendMarketEvent("CIC", 3);

	        eventsNew = testListener.LastNewData;
	        Assert.AreEqual(3, eventsNew.Length);
	        AssertReceived(eventsNew[0], "CIC", null, null);
	        AssertReceived(eventsNew[1], "CIC", 1d, null);
	        AssertReceived(eventsNew[2], "CIC", 2d, 1d);
	        testListener.Reset();

	        SendMarketEvent("MSFT", 52);

	        eventsNew = testListener.LastNewData;
	        Assert.AreEqual(3, eventsNew.Length);
	        AssertReceived(eventsNew[0], "MSFT", null, null);
	        AssertReceived(eventsNew[1], "MSFT", 50d, null);
	        AssertReceived(eventsNew[2], "MSFT", 51d, 50d);
	        testListener.Reset();

	        SendMarketEvent("IBM", 80);

	        eventsNew = testListener.LastNewData;
	        EventBean[] eventsOld = testListener.LastOldData;
	        Assert.AreEqual(3, eventsNew.Length);
	        Assert.AreEqual(3, eventsOld.Length);
	        AssertReceived(eventsNew[0], "IBM", null, null);
	        AssertReceived(eventsNew[1], "IBM", 78d, null);
	        AssertReceived(eventsNew[2], "IBM", 79d, 78d);
	        AssertReceived(eventsOld[0], "IBM", null, null);
	        AssertReceived(eventsOld[1], "IBM", null, null);
	        AssertReceived(eventsOld[2], "IBM", null, null);
	    }

	    [Test]
	    public void TestTimeWindowPerGroup()
	    {
	        String viewExpr = "select symbol, Prev(1, price) as prevPrice, Prev(2, price) as prevPrevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').win:time(20 sec) ";
	        AssertPerGroup(viewExpr);
	    }

	    [Test]
	    public void TestExtTimeWindowPerGroup()
	    {
	        String viewExpr = "select symbol, Prev(1, price) as prevPrice, Prev(2, price) as prevPrevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').win:ext_timed('volume', 20 sec) ";
	        AssertPerGroup(viewExpr);
	    }

	    [Test]
	    public void TestLengthWindowPerGroup()
	    {
	        String viewExpr = "select symbol, Prev(1, price) as prevPrice, Prev(2, price) as prevPrevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').win:length(10) ";
	        AssertPerGroup(viewExpr);
	    }

	    [Test]
	    public void TestPreviousTimeWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prev(2, symbol) as prevSymbol, " +
	                          " Prev(2, price) as prevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:time(1 min) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prevSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));

	        SendTimer(0);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("D1", 1);
	        AssertNewEvents("D1", null, null);

	        SendTimer(1000);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("D2", 2);
	        AssertNewEvents("D2", null, null);

	        SendTimer(2000);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("D3", 3);
	        AssertNewEvents("D3", "D1", 1d);

	        SendTimer(3000);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("D4", 4);
	        AssertNewEvents("D4", "D2", 2d);

	        SendTimer(4000);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("D5", 5);
	        AssertNewEvents("D5", "D3", 3d);

	        SendTimer(30000);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("D6", 6);
	        AssertNewEvents("D6", "D4", 4d);

	        // Test remove stream, always returns null as previous function
	        // returns null for remove stream for time windows
	        SendTimer(60000);
	        AssertOldEvents("D1", null, null);
	        SendTimer(61000);
	        AssertOldEvents("D2", null, null);
	        SendTimer(62000);
	        AssertOldEvents("D3", null, null);
	        SendTimer(63000);
	        AssertOldEvents("D4", null, null);
	        SendTimer(64000);
	        AssertOldEvents("D5", null, null);
	        SendTimer(90000);
	        AssertOldEvents("D6", null, null);
	    }

	    [Test]
	    public void TestPreviousExtTimedWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prev(2, symbol) as prevSymbol, " +
	                          " Prev(2, price) as prevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:ext_timed('volume', 1 min) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prevSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));

	        SendMarketEvent("D1", 1, 0);
	        AssertNewEvents("D1", null, null);

	        SendMarketEvent("D2", 2, 1000);
	        AssertNewEvents("D2", null, null);

	        SendMarketEvent("D3", 3, 3000);
	        AssertNewEvents("D3", "D1", 1d);

	        SendMarketEvent("D4", 4, 4000);
	        AssertNewEvents("D4", "D2", 2d);

	        SendMarketEvent("D5", 5, 5000);
	        AssertNewEvents("D5", "D3", 3d);

	        SendMarketEvent("D6", 6, 30000);
	        AssertNewEvents("D6", "D4", 4d);

	        SendMarketEvent("D7", 7, 60000);
	        AssertEvent(testListener.LastNewData[0], "D7", "D5", 5d);
	        AssertEvent(testListener.LastOldData[0], "D1", null, null);
	        testListener.Reset();

	        SendMarketEvent("D8", 8, 61000);
	        AssertEvent(testListener.LastNewData[0], "D8", "D6", 6d);
	        AssertEvent(testListener.LastOldData[0], "D2", null, null);
	        testListener.Reset();
	    }

	    [Test]
	    public void TestPreviousTimeBatchWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prev(2, symbol) as prevSymbol, " +
	                          " Prev(2, price) as prevPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:time_batch(1 min) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prevSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));

	        SendTimer(0);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("A", 1);
	        SendMarketEvent("B", 2);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendTimer(60000);
	        Assert.AreEqual(2, testListener.LastNewData.Length);
	        AssertEvent(testListener.LastNewData[0], "A", null, null);
	        AssertEvent(testListener.LastNewData[1], "B", null, null);
	        Assert.IsNull(testListener.LastOldData);
	        testListener.Reset();

	        SendTimer(80000);
	        SendMarketEvent("C", 3);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendTimer(120000);
	        Assert.AreEqual(1, testListener.LastNewData.Length);
	        AssertEvent(testListener.LastNewData[0], "C", null, null);
	        Assert.AreEqual(2, testListener.LastOldData.Length);
	        AssertEvent(testListener.LastOldData[0], "A", null, null);
	        testListener.Reset();

	        SendTimer(300000);
	        SendMarketEvent("D", 4);
	        SendMarketEvent("E", 5);
	        SendMarketEvent("F", 6);
	        SendMarketEvent("G", 7);
	        SendTimer(360000);
	        Assert.AreEqual(4, testListener.LastNewData.Length);
	        AssertEvent(testListener.LastNewData[0], "D", null, null);
	        AssertEvent(testListener.LastNewData[1], "E", null, null);
	        AssertEvent(testListener.LastNewData[2], "F", "D", 4d);
	        AssertEvent(testListener.LastNewData[3], "G", "E", 5d);
	    }

	    [Test]
	    public void TestPreviousTimeBatchWindowJoin()
	    {
	        String viewExpr = "select string as currSymbol, " +
	                          " Prev(2, symbol) as prevSymbol, " +
	                          " Prev(1, price) as prevPrice " +
	                          "from " + typeof(SupportBean).FullName + ", " +
	                          typeof(SupportMarketDataBean).FullName + ".win:time_batch(1 min)";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prevSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));

	        SendTimer(0);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("A", 1);
	        SendMarketEvent("B", 2);
	        SendBeanEvent("X1");
	        Assert.IsFalse(testListener.IsInvoked);

	        SendTimer(60000);
	        Assert.AreEqual(2, testListener.LastNewData.Length);
	        AssertEvent(testListener.LastNewData[0], "X1", null, null);
	        AssertEvent(testListener.LastNewData[1], "X1", null, 1d);
	        Assert.IsNull(testListener.LastOldData);
	        testListener.Reset();

	        SendMarketEvent("C1", 11);
	        SendMarketEvent("C2", 12);
	        SendMarketEvent("C3", 13);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendTimer(120000);
	        Assert.AreEqual(3, testListener.LastNewData.Length);
	        AssertEvent(testListener.LastNewData[0], "X1", null, null);
	        AssertEvent(testListener.LastNewData[1], "X1", null, 11d);
	        AssertEvent(testListener.LastNewData[2], "X1", "C1", 12d);
	    }

	    [Test]
	    public void TestPreviousLengthWindow()
	    {
	        String viewExpr =   "select symbol as currSymbol, " +
	                            "prev(0, symbol) as prev0Symbol, " +
	                            "prev(1, symbol) as prev1Symbol, " +
	                            "prev(2, symbol) as prev2Symbol, " +
	                            "prev(0, price) as prev0Price, " +
	                            "prev(1, price) as prev1Price, " +
	                            "prev(2, price) as prev2Price " +
	                            "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prev0Symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prev0Price"));

	        SendMarketEvent("A", 1);
	        AssertNewEvents("A", "A", 1d, null, null, null, null);
	        SendMarketEvent("B", 2);
	        AssertNewEvents("B", "B", 2d, "A", 1d, null, null);
	        SendMarketEvent("C", 3);
	        AssertNewEvents("C", "C", 3d, "B", 2d, "A", 1d);
	        SendMarketEvent("D", 4);
	        EventBean newEvent = testListener.LastNewData[0];
	        EventBean oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "D", "D", 4d, "C", 3d, "B", 2d);
	        AssertEventProps(oldEvent, "A", null, null, null, null, null, null);
	    }

	    [Test]
	    public void TestPreviousLengthBatch()
	    {
	        String viewExpr =   "select symbol as currSymbol, " +
	                            "prev(0, symbol) as prev0Symbol, " +
	                            "prev(1, symbol) as prev1Symbol, " +
	                            "prev(2, symbol) as prev2Symbol, " +
	                            "prev(0, price) as prev0Price, " +
	                            "prev(1, price) as prev1Price, " +
	                            "prev(2, price) as prev2Price " +
	                            "from " + typeof(SupportMarketDataBean).FullName + ".win:length_batch(3) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prev0Symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prev0Price"));

	        SendMarketEvent("A", 1);
	        SendMarketEvent("B", 2);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("C", 3);
	        EventBean[] newEvents = testListener.LastNewData;
	        Assert.AreEqual(3, newEvents.Length);
	        AssertEventProps(newEvents[0], "A", "A", 1d, null, null, null, null);
	        AssertEventProps(newEvents[1], "B", "B", 2d, "A", 1d, null, null);
	        AssertEventProps(newEvents[2], "C", "C", 3d, "B", 2d, "A", 1d);
	        testListener.Reset();

	        SendMarketEvent("D", 4);
	        SendMarketEvent("E", 5);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendMarketEvent("F", 6);
	        newEvents = testListener.LastNewData;
	        EventBean[] oldEvents = testListener.LastOldData;
	        Assert.AreEqual(3, newEvents.Length);
	        Assert.AreEqual(3, oldEvents.Length);
	        AssertEventProps(newEvents[0], "D", "D", 4d, null, null, null, null);
	        AssertEventProps(newEvents[1], "E", "E", 5d, "D", 4d, null, null);
	        AssertEventProps(newEvents[2], "F", "F", 6d, "E", 5d, "D", 4d);
	        AssertEventProps(oldEvents[0], "A", null, null, null, null, null, null);
	        AssertEventProps(oldEvents[1], "B", null, null, null, null, null, null);
	        AssertEventProps(oldEvents[2], "C", null, null, null, null, null, null);
	    }

	    [Test]
	    public void TestPreviousLengthWindowWhere()
	    {
	        String viewExpr =   "select Prev(2, symbol) as currSymbol " +
	                            "from " + typeof(SupportMarketDataBean).FullName + ".win:length(100) " +
	                            "where Prev(2, price) > 100";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        SendMarketEvent("A", 1);
	        SendMarketEvent("B", 130);
	        SendMarketEvent("C", 10);
	        Assert.IsFalse(testListener.IsInvoked);
	        SendMarketEvent("D", 5);
	        Assert.AreEqual("B", testListener.AssertOneGetNewAndReset()["currSymbol"]);
	    }

	    [Test]
	    public void TestPreviousLengthWindowDynamic()
	    {
	        String viewExpr =   "select Prev(intPrimitive, string) as sPrev " +
	                            "from " + typeof(SupportBean).FullName + ".win:length(100)";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        SendBeanEvent("A", 1);
	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual(null, _event["sPrev"]);

	        SendBeanEvent("B", 0);
	        _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("B", _event["sPrev"]);

	        SendBeanEvent("C", 2);
	        _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("A", _event["sPrev"]);

	        SendBeanEvent("D", 1);
	        _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("C", _event["sPrev"]);

	        SendBeanEvent("E", 4);
	        _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("A", _event["sPrev"]);
	    }

	    [Test]
	    public void TestPreviousSortWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prev(0, symbol) as prev0Symbol, " +
	                          " Prev(1, symbol) as prev1Symbol, " +
	                          " Prev(2, symbol) as prev2Symbol, " +
	                          " Prev(0, price) as prev0Price, " +
	                          " Prev(1, price) as prev1Price, " +
	                          " Prev(2, price) as prev2Price " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".ext:sort('symbol', false, 100)";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prev0Symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prev0Price"));

	        SendMarketEvent("COX", 30);
	        AssertNewEvents("COX", "COX", 30d, null, null, null, null);

	        SendMarketEvent("IBM", 45);
	        AssertNewEvents("IBM", "COX", 30d, "IBM", 45d, null, null);

	        SendMarketEvent("MSFT", 33);
	        AssertNewEvents("MSFT", "COX", 30d, "IBM", 45d, "MSFT", 33d);

	        SendMarketEvent("XXX", 55);
	        AssertNewEvents("XXX", "COX", 30d, "IBM", 45d, "MSFT", 33d);

	        SendMarketEvent("CXX", 56);
	        AssertNewEvents("CXX", "COX", 30d, "CXX", 56d, "IBM", 45d);

	        SendMarketEvent("GE", 1);
	        AssertNewEvents("GE", "COX", 30d, "CXX", 56d, "GE", 1d);

	        SendMarketEvent("AAA", 1);
	        AssertNewEvents("AAA", "AAA", 1d, "COX", 30d, "CXX", 56d);
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        TryInvalid("select Prev(0, average) " +
	                "from " + typeof(SupportMarketDataBean).FullName + ".win:length(100).stat:uni('price')",
	                "Error starting view: Previous function requires a single data window view onto the stream [select Prev(0, average) from net.esper.support.bean.SupportMarketDataBean.win:length(100).stat:uni('price')]");
	    }

	    private void TryInvalid(String statement, String expectedError)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(statement);
	            Assert.Fail();
	        }
	        catch (EPException ex)
	        {
	            // expected
	            Assert.AreEqual(expectedError, ex.Message);
	        }
	    }

	    private void AssertNewEvents(String currSymbol,
	                                 String prevSymbol,
	                                 double? prevPrice)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(oldData);
	        Assert.AreEqual(1, newData.Length);

	        AssertEvent(newData[0], currSymbol, prevSymbol, prevPrice);

	        testListener.Reset();
	    }

	    private void AssertEvent(EventBean _eventBean,
	                             String currSymbol,
	                             String prevSymbol,
	                             double? prevPrice)
	    {
	        Assert.AreEqual(currSymbol, _eventBean["currSymbol"]);
	        Assert.AreEqual(prevSymbol, _eventBean["prevSymbol"]);
	        Assert.AreEqual(prevPrice, _eventBean["prevPrice"]);
	    }

	    private void AssertNewEvents(String currSymbol,
	                                 String prev0Symbol,
	                                 double? prev0Price,
	                                 String prev1Symbol,
	                                 double? prev1Price,
	                                 String prev2Symbol,
	                                 double? prev2Price)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(oldData);
	        Assert.AreEqual(1, newData.Length);
	        AssertEventProps(newData[0], currSymbol, prev0Symbol, prev0Price, prev1Symbol, prev1Price, prev2Symbol, prev2Price);

	        testListener.Reset();
	    }

	    private void AssertEventProps(EventBean _eventBean,
	                                  String currSymbol,
	                                  String prev0Symbol,
	                                  double? prev0Price,
	                                  String prev1Symbol,
	                                  double? prev1Price,
	                                  String prev2Symbol,
	                                  double? prev2Price)
	    {
	        Assert.AreEqual(currSymbol, _eventBean["currSymbol"]);
	        Assert.AreEqual(prev0Symbol, _eventBean["prev0Symbol"]);
	        Assert.AreEqual(prev0Price, _eventBean["prev0Price"]);
	        Assert.AreEqual(prev1Symbol, _eventBean["prev1Symbol"]);
	        Assert.AreEqual(prev1Price, _eventBean["prev1Price"]);
	        Assert.AreEqual(prev2Symbol, _eventBean["prev2Symbol"]);
	        Assert.AreEqual(prev2Price, _eventBean["prev2Price"]);

	        testListener.Reset();
	    }

	    private void SendTimer(long timeInMSec)
	    {
	        CurrentTimeEvent _event = new CurrentTimeEvent(timeInMSec);
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void SendMarketEvent(String symbol, double price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendMarketEvent(String symbol, double price, long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendBeanEvent(String _string)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(_string);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendBeanEvent(String _string, int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(_string);
	        bean.SetIntPrimitive(intPrimitive);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void AssertOldEvents(String currSymbol,
	                                 String prevSymbol,
	                                 double? prevPrice)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(newData);
	        Assert.AreEqual(1, oldData.Length);

	        Assert.AreEqual(currSymbol, oldData[0]["currSymbol"]);
	        Assert.AreEqual(prevSymbol, oldData[0]["prevSymbol"]);
	        Assert.AreEqual(prevPrice, oldData[0]["prevPrice"]);

	        testListener.Reset();
	    }

	    private void AssertPerGroup(String statement)
	    {
	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(statement);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrice"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prevPrevPrice"));

	        SendMarketEvent("IBM", 75);
	        AssertReceived("IBM", null, null);

	        SendMarketEvent("MSFT", 40);
	        AssertReceived("MSFT", null, null);

	        SendMarketEvent("IBM", 76);
	        AssertReceived("IBM", 75d, null);

	        SendMarketEvent("CIC", 1);
	        AssertReceived("CIC", null, null);

	        SendMarketEvent("MSFT", 41);
	        AssertReceived("MSFT", 40d, null);

	        SendMarketEvent("IBM", 77);
	        AssertReceived("IBM", 76d, 75d);

	        SendMarketEvent("IBM", 78);
	        AssertReceived("IBM", 77d, 76d);

	        SendMarketEvent("CIC", 2);
	        AssertReceived("CIC", 1d, null);

	        SendMarketEvent("MSFT", 42);
	        AssertReceived("MSFT", 41d, 40d);

	        SendMarketEvent("CIC", 3);
	        AssertReceived("CIC", 2d, 1d);
	    }

	    private void AssertReceived(String symbol, double? prevPrice, double? prevPrevPrice)
	    {
	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        AssertReceived(_event, symbol, prevPrice, prevPrevPrice);
	    }

	    private void AssertReceived(EventBean _event, String symbol, double? prevPrice, double? prevPrevPrice)
	    {
	        Assert.AreEqual(symbol, _event["symbol"]);
	        Assert.AreEqual(prevPrice, _event["prevPrice"]);
	        Assert.AreEqual(prevPrevPrice, _event["prevPrevPrice"]);
	    }

	    private void AssertCountAndPrice(EventBean _event, long? total, double? price)
	    {
	        Assert.AreEqual(total, _event["total"]);
	        Assert.AreEqual(price, _event["firstPrice"]);
	    }

        public static int? LongToInt(long? longValue)
        {
            int? returnValue = null;
            if ( longValue.HasValue )
            {
                returnValue = (int) longValue.Value;
            }

            return returnValue;
        }
	}
} // End of namespace
