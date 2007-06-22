// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client.time;
using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestPriorFunction
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
	    public void TestPriorTimeWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(2, symbol) as priorSymbol, " +
	                          " Prior(2, price) as priorPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:time(1 min) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("priorSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("priorPrice"));

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

	        SendTimer(60000);
	        AssertOldEvents("D1", null, null);
	        SendTimer(61000);
	        AssertOldEvents("D2", null, null);
	        SendTimer(62000);
	        AssertOldEvents("D3", "D1", 1d);
	        SendTimer(63000);
	        AssertOldEvents("D4", "D2", 2d);
	        SendTimer(64000);
	        AssertOldEvents("D5", "D3", 3d);
	        SendTimer(90000);
	        AssertOldEvents("D6", "D4", 4d);

	        SendMarketEvent("D7", 7);
	        AssertNewEvents("D7", "D5", 5d);
	        SendMarketEvent("D8", 8);
	        SendMarketEvent("D9", 9);
	        SendMarketEvent("D10", 10);
	        SendMarketEvent("D11", 11);
	        testListener.Reset();

	        // release batch
	        SendTimer(150000);
	        EventBean[] oldData = testListener.LastOldData;
	        Assert.IsNull(testListener.LastNewData);
	        Assert.AreEqual(5, oldData.Length);
	        AssertEvent(oldData[0], "D7", "D5", 5d);
	        AssertEvent(oldData[1], "D8", "D6", 6d);
	        AssertEvent(oldData[2], "D9", "D7", 7d);
	        AssertEvent(oldData[3], "D10", "D8", 8d);
	        AssertEvent(oldData[4], "D11", "D9", 9d);
	    }

	    [Test]
	    public void TestPriorExtTimedWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(2, symbol) as priorSymbol, " +
	                          " Prior(3, price) as priorPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:ext_timed('volume', 1 min) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("priorSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("priorPrice"));

	        SendMarketEvent("D1", 1, 0);
	        AssertNewEvents("D1", null, null);

	        SendMarketEvent("D2", 2, 1000);
	        AssertNewEvents("D2", null, null);

	        SendMarketEvent("D3", 3, 3000);
	        AssertNewEvents("D3", "D1", null);

	        SendMarketEvent("D4", 4, 4000);
	        AssertNewEvents("D4", "D2", 1d);

	        SendMarketEvent("D5", 5, 5000);
	        AssertNewEvents("D5", "D3", 2d);

	        SendMarketEvent("D6", 6, 30000);
	        AssertNewEvents("D6", "D4", 3d);

	        SendMarketEvent("D7", 7, 60000);
	        AssertEvent(testListener.LastNewData[0], "D7", "D5", 4d);
	        AssertEvent(testListener.LastOldData[0], "D1", null, null);
	        testListener.Reset();

	        SendMarketEvent("D8", 8, 61000);
	        AssertEvent(testListener.LastNewData[0], "D8", "D6", 5d);
	        AssertEvent(testListener.LastOldData[0], "D2", null, null);
	        testListener.Reset();

	        SendMarketEvent("D9", 9, 63000);
	        AssertEvent(testListener.LastNewData[0], "D9", "D7", 6d);
	        AssertEvent(testListener.LastOldData[0], "D3", "D1", null);
	        testListener.Reset();

	        SendMarketEvent("D10", 10, 64000);
	        AssertEvent(testListener.LastNewData[0], "D10", "D8", 7d);
	        AssertEvent(testListener.LastOldData[0], "D4", "D2", 1d);
	        testListener.Reset();

	        SendMarketEvent("D10", 10, 150000);
	        EventBean[] oldData = testListener.LastOldData;
	        Assert.AreEqual(6, oldData.Length);
	        AssertEvent(oldData[0], "D5", "D3", 2d);
	    }

	    [Test]
	    public void TestPriorTimeBatchWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(3, symbol) as priorSymbol, " +
	                          " Prior(2, price) as priorPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:time_batch(1 min) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("priorSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("priorPrice"));

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
	        AssertEvent(testListener.LastNewData[0], "C", null, 1d);
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
	        AssertEvent(testListener.LastNewData[0], "D", "A", 2d);
	        AssertEvent(testListener.LastNewData[1], "E", "B", 3d);
	        AssertEvent(testListener.LastNewData[2], "F", "C", 4d);
	        AssertEvent(testListener.LastNewData[3], "G", "D", 5d);
	    }

	    [Test]
	    public void TestPriorUnbound()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(3, symbol) as priorSymbol, " +
	                          " Prior(2, price) as priorPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName;

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("priorSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("priorPrice"));

	        SendMarketEvent("A", 1);
	        AssertNewEvents("A", null, null);

	        SendMarketEvent("B", 2);
	        AssertNewEvents("B", null, null);

	        SendMarketEvent("C", 3);
	        AssertNewEvents("C", null, 1d);

	        SendMarketEvent("D", 4);
	        AssertNewEvents("D", "A", 2d);

	        SendMarketEvent("E", 5);
	        AssertNewEvents("E", "B", 3d);
	    }

	    [Test]
	    public void TestLongRunningSingle()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(3, symbol) as prior0Symbol " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".ext:sort('symbol', false, 3)";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        Random random = new Random();
	        // 200000 is a better number for a memory test, however for short unit tests this is 2000
	        for (int i = 0; i < 2000; i++)
	        {
	            if (i % 10000 == 0)
	            {
	                //Console.WriteLine(i);
	            }

	            SendMarketEvent(Convert.ToString(random.Next()), 4);

	            if (i % 1000 == 0)
	            {
	                testListener.Reset();
	            }
	        }
	    }

	    [Test]
	    public void TestLongRunningUnbound()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(3, symbol) as prior0Symbol " +
	                          "from " + typeof(SupportMarketDataBean).FullName;

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        Random random = new Random();
	        // 200000 is a better number for a memory test, however for short unit tests this is 2000
	        for (int i = 0; i < 2000; i++)
	        {
	            if (i % 10000 == 0)
	            {
	                //Console.WriteLine(i);
	            }

	            SendMarketEvent(Convert.ToString(random.Next()), 4);

	            if (i % 1000 == 0)
	            {
	                testListener.Reset();
	            }
	        }
	    }

	    [Test]
	    public void TestLongRunningMultiple()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(3, symbol) as prior0Symbol, " +
	                          " Prior(2, symbol) as prior1Symbol, " +
	                          " Prior(1, symbol) as prior2Symbol, " +
	                          " Prior(0, symbol) as prior3Symbol, " +
	                          " Prior(0, price) as prior0Price, " +
	                          " Prior(1, price) as prior1Price, " +
	                          " Prior(2, price) as prior2Price, " +
	                          " Prior(3, price) as prior3Price " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".ext:sort('symbol', false, 3)";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        Random random = new Random();
	        // 200000 is a better number for a memory test, however for short unit tests this is 2000
	        for (int i = 0; i < 2000; i++)
	        {
	            if (i % 10000 == 0)
	            {
	                //Console.WriteLine(i);
	            }

	            SendMarketEvent(Convert.ToString(random.Next()), 4);

	            if (i % 1000 == 0)
	            {
	                testListener.Reset();
	            }
	        }
	    }

	    [Test]
	    public void TestPriorLengthWindow()
	    {
	        String viewExpr =   "select symbol as currSymbol, " +
	                            "prior(0, symbol) as prior0Symbol, " +
	                            "prior(1, symbol) as prior1Symbol, " +
	                            "prior(2, symbol) as prior2Symbol, " +
	                            "prior(3, symbol) as prior3Symbol, " +
	                            "prior(0, price) as prior0Price, " +
	                            "prior(1, price) as prior1Price, " +
	                            "prior(2, price) as prior2Price, " +
	                            "prior(3, price) as prior3Price " +
	                            "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) ";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("prior0Symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("prior0Price"));

	        SendMarketEvent("A", 1);
	        AssertNewEvents("A", "A", 1d, null, null, null, null, null, null);
	        SendMarketEvent("B", 2);
	        AssertNewEvents("B", "B", 2d, "A", 1d, null, null, null, null);
	        SendMarketEvent("C", 3);
	        AssertNewEvents("C", "C", 3d, "B", 2d, "A", 1d, null, null);

	        SendMarketEvent("D", 4);
	        EventBean newEvent = testListener.LastNewData[0];
	        EventBean oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "D", "D", 4d, "C", 3d, "B", 2d, "A", 1d);
	        AssertEventProps(oldEvent, "A", "A", 1d, null, null, null, null, null, null);

	        SendMarketEvent("E", 5);
	        newEvent = testListener.LastNewData[0];
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "E", "E", 5d, "D", 4d, "C", 3d, "B", 2d);
	        AssertEventProps(oldEvent, "B", "B", 2d, "A", 1d, null, null, null, null);

	        SendMarketEvent("F", 6);
	        newEvent = testListener.LastNewData[0];
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "F", "F", 6d, "E", 5d, "D", 4d, "C", 3d);
	        AssertEventProps(oldEvent, "C", "C", 3d, "B", 2d, "A", 1d, null, null);

	        SendMarketEvent("G", 7);
	        newEvent = testListener.LastNewData[0];
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "G", "G", 7d, "F", 6d, "E", 5d, "D", 4d);
	        AssertEventProps(oldEvent, "D", "D", 4d, "C", 3d, "B", 2d, "A", 1d);

	        SendMarketEvent("G", 8);
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(oldEvent, "E", "E", 5d, "D", 4d, "C", 3d, "B", 2d);
	    }

	    [Test]
	    public void TestPriorLengthWindowWhere()
	    {
	        String viewExpr =   "select Prior(2, symbol) as currSymbol " +
	                            "from " + typeof(SupportMarketDataBean).FullName + ".win:length(1) " +
	                            "where Prior(2, price) > 100";

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
	    public void TestPriorSortWindow()
	    {
	        String viewExpr = "select symbol as currSymbol, " +
	                          " Prior(0, symbol) as prior0Symbol, " +
	                          " Prior(1, symbol) as prior1Symbol, " +
	                          " Prior(2, symbol) as prior2Symbol, " +
	                          " Prior(3, symbol) as prior3Symbol, " +
	                          " Prior(0, price) as prior0Price, " +
	                          " Prior(1, price) as prior1Price, " +
	                          " Prior(2, price) as prior2Price, " +
	                          " Prior(3, price) as prior3Price " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".ext:sort('symbol', false, 3)";
	        TryPriorSortWindow(viewExpr);

	        viewExpr = "select symbol as currSymbol, " +
	                          " Prior(3, symbol) as prior3Symbol, " +
	                          " Prior(1, symbol) as prior1Symbol, " +
	                          " Prior(2, symbol) as prior2Symbol, " +
	                          " Prior(0, symbol) as prior0Symbol, " +
	                          " Prior(2, price) as prior2Price, " +
	                          " Prior(1, price) as prior1Price, " +
	                          " Prior(0, price) as prior0Price, " +
	                          " Prior(3, price) as prior3Price " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".ext:sort('symbol', false, 3)";
	        TryPriorSortWindow(viewExpr);
	    }

	    [Test]
	    public void TestPreviousTimeBatchWindowJoin()
	    {
	        String viewExpr = "select string as currSymbol, " +
	                          "prior(2, symbol) as priorSymbol, " +
	                          "prior(1, price) as priorPrice " +
	                          "from " + typeof(SupportBean).FullName + ", " +
	                          typeof(SupportMarketDataBean).FullName + ".win:time_batch(1 min)";

	        EPStatement selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        // assert select result type
	        Assert.AreEqual(typeof(string), selectTestView.EventType.GetPropertyType("priorSymbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("priorPrice"));

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
	        AssertEvent(testListener.LastNewData[0], "X1", "A", 2d);
	        AssertEvent(testListener.LastNewData[1], "X1", "B", 11d);
	        AssertEvent(testListener.LastNewData[2], "X1", "C1", 12d);
	    }

	    private void TryPriorSortWindow(String viewExpr)
	    {
	        EPStatement statement = epService.EPAdministrator.CreateEQL(viewExpr);
	        statement.AddListener(testListener);

	        SendMarketEvent("COX", 30);
	        AssertNewEvents("COX", "COX", 30d, null, null, null, null, null, null);

	        SendMarketEvent("IBM", 45);
	        AssertNewEvents("IBM", "IBM", 45d, "COX", 30d, null, null, null, null);

	        SendMarketEvent("MSFT", 33);
	        AssertNewEvents("MSFT", "MSFT", 33d, "IBM", 45d, "COX", 30d, null, null);

	        SendMarketEvent("XXX", 55);
	        EventBean newEvent = testListener.LastNewData[0];
	        EventBean oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "XXX", "XXX", 55d, "MSFT", 33d, "IBM", 45d, "COX", 30d);
	        AssertEventProps(oldEvent, "XXX", "XXX", 55d, "MSFT", 33d, "IBM", 45d, "COX", 30d);

	        SendMarketEvent("BOO", 20);
	        newEvent = testListener.LastNewData[0];
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "BOO", "BOO", 20d, "XXX", 55d, "MSFT", 33d, "IBM", 45d);
	        AssertEventProps(oldEvent, "MSFT", "MSFT", 33d, "IBM", 45d, "COX", 30d, null, null);

	        SendMarketEvent("DOR", 1);
	        newEvent = testListener.LastNewData[0];
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "DOR", "DOR", 1d, "BOO", 20d, "XXX", 55d, "MSFT", 33d);
	        AssertEventProps(oldEvent, "IBM", "IBM", 45d, "COX", 30d, null, null, null, null);

	        SendMarketEvent("AAA", 2);
	        newEvent = testListener.LastNewData[0];
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(newEvent, "AAA", "AAA", 2d, "DOR", 1d, "BOO", 20d, "XXX", 55d);
	        AssertEventProps(oldEvent, "DOR", "DOR", 1d, "BOO", 20d, "XXX", 55d, "MSFT", 33d);

	        SendMarketEvent("AAB", 2);
	        oldEvent = testListener.LastOldData[0];
	        AssertEventProps(oldEvent, "COX", "COX", 30d, null, null, null, null, null, null);
	        testListener.Reset();

	        statement.Stop();
	    }

	    private void AssertNewEvents(String currSymbol,
	                                 String priorSymbol,
                                     double? priorPrice)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(oldData);
	        Assert.AreEqual(1, newData.Length);

	        AssertEvent(newData[0], currSymbol, priorSymbol, priorPrice);

	        testListener.Reset();
	    }

	    private void AssertEvent(EventBean _eventBean,
	                             String currSymbol,
	                             String priorSymbol,
	                             double? priorPrice)
	    {
	        Assert.AreEqual(currSymbol, _eventBean["currSymbol"]);
	        Assert.AreEqual(priorSymbol, _eventBean["priorSymbol"]);
	        Assert.AreEqual(priorPrice, _eventBean["priorPrice"]);
	    }

	    private void AssertNewEvents(String currSymbol,
	                                 String prior0Symbol,
                                     double? prior0Price,
	                                 String prior1Symbol,
                                     double? prior1Price,
	                                 String prior2Symbol,
                                     double? prior2Price,
	                                 String prior3Symbol,
                                     double? prior3Price)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(oldData);
	        Assert.AreEqual(1, newData.Length);
	        AssertEventProps(newData[0], currSymbol, prior0Symbol, prior0Price, prior1Symbol, prior1Price, prior2Symbol, prior2Price, prior3Symbol, prior3Price);

	        testListener.Reset();
	    }

	    private void AssertEventProps(EventBean _eventBean,
	                                  String currSymbol,
	                                  String prior0Symbol,
                                      double? prior0Price,
	                                  String prior1Symbol,
                                      double? prior1Price,
	                                  String prior2Symbol,
                                      double? prior2Price,
	                                  String prior3Symbol,
                                      double? prior3Price)
	    {
	        Assert.AreEqual(currSymbol, _eventBean["currSymbol"]);
	        Assert.AreEqual(prior0Symbol, _eventBean["prior0Symbol"]);
	        Assert.AreEqual(prior0Price, _eventBean["prior0Price"]);
	        Assert.AreEqual(prior1Symbol, _eventBean["prior1Symbol"]);
	        Assert.AreEqual(prior1Price, _eventBean["prior1Price"]);
	        Assert.AreEqual(prior2Symbol, _eventBean["prior2Symbol"]);
	        Assert.AreEqual(prior2Price, _eventBean["prior2Price"]);
	        Assert.AreEqual(prior3Symbol, _eventBean["prior3Symbol"]);
	        Assert.AreEqual(prior3Price, _eventBean["prior3Price"]);

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

	    private void AssertOldEvents(String currSymbol,
	                                 String priorSymbol,
                                     double? priorPrice)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(newData);
	        Assert.AreEqual(1, oldData.Length);

	        Assert.AreEqual(currSymbol, oldData[0]["currSymbol"]);
	        Assert.AreEqual(priorSymbol, oldData[0]["priorSymbol"]);
	        Assert.AreEqual(priorPrice, oldData[0]["priorPrice"]);

	        testListener.Reset();
	    }
	}
} // End of namespace
