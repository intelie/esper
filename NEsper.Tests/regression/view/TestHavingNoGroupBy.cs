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
	public class TestHavingNoGroupBy
	{
	    private static String SYMBOL_DELL = "DELL";

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
	    public void testSumOneView()
	    {
	        String viewExpr = "select symbol, price, Avg(price) as avgPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
	                          "having price < Avg(price)";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        RunAssertion();
	    }

	    [Test]
	    public void testSumJoin()
	    {
	        String viewExpr = "select symbol, price, Avg(price) as avgPrice " +
	                          "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " +
	                                    typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " +
	                          "where one.string = two.symbol " +
	                          "having price < Avg(price)";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));

	        RunAssertion();
	    }

	    [Test]
	    public void testSumHavingNoAggregatedProp()
	    {
	        String viewExpr = "select symbol, price, Avg(price) as avgPrice " +
	                          "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " +
	                          "having volume < Avg(price)";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);
	    }

	    [Test]
	    public void testNoAggregationJoinHaving()
	    {
	        RunNoAggregationJoin("having");
	    }

	    [Test]
	    public void testNoAggregationJoinWhere()
	    {
	        RunNoAggregationJoin("where");
	    }

	    private void RunNoAggregationJoin(String filterClause)
	    {
	        String viewExpr = "select a.price as aPrice, b.price as bPrice, Math.Max(a.price, b.price) - Math.Min(a.price, b.price) as spread " +
	                          "from " + typeof(SupportMarketDataBean).FullName + "(symbol='SYM1').win:length(1) as a, " +
	                                    typeof(SupportMarketDataBean).FullName + "(symbol='SYM2').win:length(1) as b " +
	                          filterClause + " Math.Max(a.price, b.price) - Math.Min(a.price, b.price) >= 1.4";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        SendPriceEvent("SYM1", 20);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendPriceEvent("SYM2", 10);
	        AssertNewSpreadEvent(20, 10, 10);

	        SendPriceEvent("SYM2", 20);
	        AssertOldSpreadEvent(20, 10, 10);

	        SendPriceEvent("SYM2", 20);
	        SendPriceEvent("SYM2", 20);
	        SendPriceEvent("SYM1", 20);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendPriceEvent("SYM1", 18.7);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendPriceEvent("SYM2", 20);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendPriceEvent("SYM1", 18.5);
	        AssertNewSpreadEvent(18.5, 20, 1.5d);

	        SendPriceEvent("SYM2", 16);
	        AssertOldNewSpreadEvent(18.5, 20, 1.5d, 18.5, 16, 2.5d);

	        SendPriceEvent("SYM1", 12);
	        AssertOldNewSpreadEvent(18.5, 16, 2.5d, 12, 16, 4);
	    }

	    private void AssertOldNewSpreadEvent(double oldaprice, double oldbprice, double oldspread,
	                                         double newaprice, double newbprice, double newspread)
	    {
	        Assert.AreEqual(1, testListener.OldDataList.Count);
            Assert.AreEqual(1, testListener.LastOldData.Length);
	        Assert.AreEqual(1, testListener.NewDataList.Count);   // since event null is put into the list
            Assert.AreEqual(1, testListener.LastNewData.Length);

	        EventBean oldEvent = testListener.LastOldData[0];
	        EventBean newEvent = testListener.LastNewData[0];

	        CompareSpreadEvent(oldEvent, oldaprice, oldbprice, oldspread);
	        CompareSpreadEvent(newEvent, newaprice, newbprice, newspread);

	        testListener.Reset();
	    }

	    private void AssertOldSpreadEvent(double aprice, double bprice, double spread)
	    {
	        Assert.AreEqual(1, testListener.OldDataList.Count);
            Assert.AreEqual(1, testListener.LastOldData.Length);
	        Assert.AreEqual(1, testListener.NewDataList.Count);   // since event null is put into the list
	        Assert.IsNull(testListener.LastNewData);

	        EventBean _event = testListener.LastOldData[0];

	        CompareSpreadEvent(_event, aprice, bprice, spread);
	        testListener.Reset();
	    }

	    private void AssertNewSpreadEvent(double aprice, double bprice, double spread)
	    {
	        Assert.AreEqual(1, testListener.NewDataList.Count);
            Assert.AreEqual(1, testListener.LastNewData.Length);
	        Assert.AreEqual(1, testListener.OldDataList.Count);
	        Assert.IsNull(testListener.LastOldData);

	        EventBean _event = testListener.LastNewData[0];
	        CompareSpreadEvent(_event, aprice, bprice, spread);
	        testListener.Reset();
	    }

	    private void CompareSpreadEvent(EventBean _event, double aprice, double bprice, double spread)
	    {
	        Assert.AreEqual(aprice, _event["aPrice"]);
	        Assert.AreEqual(bprice, _event["bPrice"]);
	        Assert.AreEqual(spread, _event["spread"]);
	    }

	    private void SendPriceEvent(String symbol, double price)
	    {
	        epService.EPRuntime.SendEvent(new SupportMarketDataBean(symbol, price, -1L, null));
	    }

	    private void RunAssertion()
	    {
	        // assert select result type
	        Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(double), selectTestView.EventType.GetPropertyType("price"));
	        Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("avgPrice"));

	        SendEvent(SYMBOL_DELL, 10);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent(SYMBOL_DELL, 5);
	        AssertNewEvents(SYMBOL_DELL, 5d, 7.5d);

	        SendEvent(SYMBOL_DELL, 15);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent(SYMBOL_DELL, 8);  // avg = (10 + 5 + 15 + 8) / 4 = 38/4=9.5
	        AssertNewEvents(SYMBOL_DELL, 8d, 9.5d);

	        SendEvent(SYMBOL_DELL, 10);  // avg = (10 + 5 + 15 + 8 + 10) / 5 = 48/5=9.5
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent(SYMBOL_DELL, 6);  // avg = (5 + 15 + 8 + 10 + 6) / 5 = 44/5=8.8
	        // no old event posted, old event falls above current avg price
	        AssertNewEvents(SYMBOL_DELL, 6d, 8.8d);

	        SendEvent(SYMBOL_DELL, 12);  // avg = (15 + 8 + 10 + 6 + 12) / 5 = 51/5=10.2
	        AssertOldEvents(SYMBOL_DELL, 5d, 8.8d);
	    }

	    [Test]
	    public void testHavingSum()
	    {
	        String stmt = "select Sum(myEvent.intPrimitive) as mysum from pattern [every myEvent=" + typeof(SupportBean).FullName +
	                "] having Sum(myEvent.intPrimitive) = 2";
	        selectTestView = epService.EPAdministrator.CreateEQL(stmt);
	        selectTestView.AddListener(testListener);

	        SendEvent(1);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent(1);
	        Assert.AreEqual(2, testListener.AssertOneGetNewAndReset()["mysum"]);

	        SendEvent(1);
	        Assert.AreEqual(2, testListener.AssertOneGetOldAndReset()["mysum"]);
	    }

	    [Test]
	    public void testHavingSumIStream()
	    {
	        String stmt = "select istream Sum(myEvent.intPrimitive) as mysum from pattern [every myEvent=" + typeof(SupportBean).FullName +
	                "] having Sum(myEvent.intPrimitive) = 2";
	        selectTestView = epService.EPAdministrator.CreateEQL(stmt);
	        selectTestView.AddListener(testListener);

	        SendEvent(1);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent(1);
	        Assert.AreEqual(2, testListener.AssertOneGetNewAndReset()["mysum"]);

	        SendEvent(1);
	        Assert.IsFalse(testListener.IsInvoked);
	    }

	    private void AssertNewEvents(String symbol,
	                                 double? newPrice, double? newAvgPrice
	                              )
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(oldData);
            Assert.AreEqual(1, newData.Length);

	        Assert.AreEqual(symbol, newData[0]["symbol"]);
	        Assert.AreEqual(newPrice, newData[0]["price"]);
	        Assert.AreEqual(newAvgPrice, newData[0]["avgPrice"]);

	        testListener.Reset();
	    }

	    private void AssertOldEvents(String symbol,
	                                 double? oldPrice, double? oldAvgPrice
	                              )
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.IsNull(newData);
            Assert.AreEqual(1, oldData.Length);

	        Assert.AreEqual(symbol, oldData[0]["symbol"]);
	        Assert.AreEqual(oldPrice, oldData[0]["price"]);
	        Assert.AreEqual(oldAvgPrice, oldData[0]["avgPrice"]);

	        testListener.Reset();
	    }

	    private void SendEvent(int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendEvent(String symbol, double price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
