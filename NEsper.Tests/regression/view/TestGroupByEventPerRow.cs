///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestGroupByEventPerRow
	{
	    private static String SYMBOL_DELL = "DELL";
	    private static String SYMBOL_IBM = "IBM";

	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;
	    private EPStatement selectTestView;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void TestSumOneView()
	    {
	        // Every event generates a new row, this time we sum the price by symbol and output volume
	        String viewExpr = "select symbol, volume, Sum(price) as mySum " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " +
	                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                          "group by symbol";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        RunAssertion();
	    }

	    [Test]
	    public void TestSumJoin()
	    {
	        // Every event generates a new row, this time we sum the price by symbol and output volume
	        String viewExpr = "select symbol, volume, Sum(price) as mySum " +
	                          "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " +
	                                    typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " +
	                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                          "  and one.string = two.symbol " +
	                          "group by symbol";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
	        epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));

	        RunAssertion();
	    }

	    [Test]
	    public void TestInsertInto()
	    {
	        SupportUpdateListener listenerOne = new SupportUpdateListener();
	        String eventType = typeof(SupportMarketDataBean).FullName;
	        String stmt = " select symbol as symbol, Avg(price) as average, Sum(volume) as sumation from " + eventType + ".win:length(3000)";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmt);
            statement.AddListener(listenerOne);

	        epService.EPRuntime.SendEvent(new SupportMarketDataBean("IBM", 10D, 20000L, null));
	        EventBean _eventBean = listenerOne.LastNewData[0];
	        Assert.AreEqual("IBM", _eventBean["symbol"]);
	        Assert.AreEqual(10d, _eventBean["average"]);
	        Assert.AreEqual(20000L, _eventBean["sumation"]);

	        // create insert into statements
	        stmt =  "insert into StockAverages select symbol as symbol, Avg(price) as average, Sum(volume) as sumation " +
	                    "from " + eventType + ".win:length(3000)";
	        statement = epService.EPAdministrator.CreateEQL(stmt);
	        SupportUpdateListener listenerTwo = new SupportUpdateListener();
            statement.AddListener(listenerTwo);

	        stmt = " select * from StockAverages";
	        statement = epService.EPAdministrator.CreateEQL(stmt);
	        SupportUpdateListener listenerThree = new SupportUpdateListener();
            statement.AddListener(listenerThree);

	        // send event
	        epService.EPRuntime.SendEvent(new SupportMarketDataBean("IBM", 20D, 40000L, null));
	        _eventBean = listenerOne.LastNewData[0];
	        Assert.AreEqual("IBM", _eventBean["symbol"]);
	        Assert.AreEqual(15d, _eventBean["average"]);
	        Assert.AreEqual(60000L, _eventBean["sumation"]);

	        Assert.AreEqual(1, listenerThree.NewDataList.Count);
	        Assert.AreEqual(1, listenerThree.LastNewData.Length);
	        _eventBean = listenerThree.LastNewData[0];
	        Assert.AreEqual("IBM", _eventBean["symbol"]);
	        Assert.AreEqual(20d, _eventBean["average"]);
	        Assert.AreEqual(40000L, _eventBean["sumation"]);
	    }

	    private void RunAssertion()
	    {
	        // assert select result type
	        Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("volume"));
	        Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));

	        SendEvent(SYMBOL_DELL, 10000, 51);
	        AssertEvents(SYMBOL_DELL, 10000, 51);

	        SendEvent(SYMBOL_DELL, 20000, 52);
	        AssertEvents(SYMBOL_DELL, 20000, 103);

	        SendEvent(SYMBOL_IBM, 30000, 70);
	        AssertEvents(SYMBOL_IBM, 30000, 70);

	        SendEvent(SYMBOL_IBM, 10000, 20);
	        AssertEvents(SYMBOL_DELL, 10000, 103, SYMBOL_IBM, 10000, 90);

	        SendEvent(SYMBOL_DELL, 40000, 45);
	        AssertEvents(SYMBOL_DELL, 20000, 52, SYMBOL_DELL, 40000, 45);
	    }

	    private void AssertEvents(String symbol, long volume, double sum)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(oldData);
	        Assert.AreEqual(1, newData.Length);

	        Assert.AreEqual(symbol, newData[0]["symbol"]);
	        Assert.AreEqual(volume, newData[0]["volume"]);
	        Assert.AreEqual(sum, newData[0]["mySum"]);

	        testListener.Reset();
	        Assert.IsFalse(testListener.IsInvoked);
	    }

	    private void AssertEvents(String symbolOld, long volumeOld, double sumOld,
	                              String symbolNew, long volumeNew, double sumNew)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.AreEqual(1, oldData.Length);
	        Assert.AreEqual(1, newData.Length);

	        Assert.AreEqual(symbolOld, oldData[0]["symbol"]);
	        Assert.AreEqual(volumeOld, oldData[0]["volume"]);
	        Assert.AreEqual(sumOld, oldData[0]["mySum"]);

	        Assert.AreEqual(symbolNew, newData[0]["symbol"]);
	        Assert.AreEqual(volumeNew, newData[0]["volume"]);
	        Assert.AreEqual(sumNew, newData[0]["mySum"]);

	        testListener.Reset();
	        Assert.IsFalse(testListener.IsInvoked);
	    }

	    private void SendEvent(String symbol, long volume, double price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
