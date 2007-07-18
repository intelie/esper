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
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestAggregateRowForAll
	{
	    private const String JOIN_KEY = "KEY";

	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;
	    private EPStatement selectTestView;

	    [SetUp]
	    public void SetUp()
	    {
	        PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;
	        listener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void testSumOneView()
	    {
	        String viewExpr = "select sum(longBoxed) as mySum " +
	                          "from " + typeof(SupportBean).FullName + ".win:time(10 sec)";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(listener);

	        RunAssert();
	    }

	    [Test]
	    public void testSumJoin()
	    {
	        String viewExpr = "select sum(longBoxed) as mySum " +
	                          "from " + typeof(SupportBeanString).FullName + ".win:time(10) as one, " +
	                                    typeof(SupportBean).FullName + ".win:time(10 sec) as two " +
	                          "where one.string = two.string";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBeanString(JOIN_KEY));

	        RunAssert();
	    }

	    private void RunAssert()
	    {
	        // assert select result type
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("mySum"));

	        SendTimerEvent(0);
	        SendEvent(10);
	        Assert.AreEqual(10L, listener.GetAndResetLastNewData()[0]["mySum"]);

	        SendTimerEvent(5000);
	        SendEvent(15);
	        Assert.AreEqual(25L, listener.GetAndResetLastNewData()[0]["mySum"]);

	        SendTimerEvent(8000);
	        SendEvent(-5);
	        Assert.AreEqual(20L, listener.GetAndResetLastNewData()[0]["mySum"]);
	        Assert.IsNull(listener.LastOldData);

	        SendTimerEvent(10000);
	        Assert.AreEqual(20L, listener.LastOldData[0]["mySum"]);
	        Assert.AreEqual(10L, listener.GetAndResetLastNewData()[0]["mySum"]);

	        SendTimerEvent(15000);
	        Assert.AreEqual(10L, listener.LastOldData[0]["mySum"]);
	        Assert.AreEqual(-5L, listener.GetAndResetLastNewData()[0]["mySum"]);

	        SendTimerEvent(18000);
	        Assert.AreEqual(-5L, listener.LastOldData[0]["mySum"]);
	        Assert.IsNull(listener.GetAndResetLastNewData()[0]["mySum"]);
	    }

	    [Test]
	    public void testSumDivideZero()
	    {
	        String eventName = typeof(SupportBean).FullName;
	        String stmt;

	        stmt = "select ((sum(floatBoxed) - floatBoxed) / (count(*) - 1)) as laggingAvg  from " + eventName + " .win:time(60) as a";
	        selectTestView = epService.EPAdministrator.CreateEQL(stmt);
	        selectTestView.AddListener(listener);
	        SendEventFloat(1.1f);

	        stmt = "select ((sum(intBoxed) - intBoxed) / (count(*) - 1)) as laggingAvg  from " + eventName + " .win:time(60) as a";
	        selectTestView = epService.EPAdministrator.CreateEQL(stmt);
	        selectTestView.AddListener(listener);

	        try
	        {
	            SendEventInt(10);
	            Assert.Fail();
	        }
	        catch (EPException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAvgPerSym()
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(
	                "select Avg(price) as avgp, sym from " + typeof(SupportPriceEvent).FullName + ".std:groupby('sym').win:length(2)"
	        );
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportPriceEvent(1, "A"));
	        EventBean _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual("A", _event["sym"]);
	        Assert.AreEqual(1.0, _event["avgp"]);

	        epService.EPRuntime.SendEvent(new SupportPriceEvent(2, "B"));
	        _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual("B", _event["sym"]);
	        Assert.AreEqual(1.5, _event["avgp"]);

	        epService.EPRuntime.SendEvent(new SupportPriceEvent(9, "A"));
	        _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual("A", _event["sym"]);
	        Assert.AreEqual((1 + 2 + 9) / 3.0, _event["avgp"]);

	        epService.EPRuntime.SendEvent(new SupportPriceEvent(18, "B"));
	        _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual("B", _event["sym"]);
	        Assert.AreEqual((1 + 2 + 9 + 18) / 4.0, _event["avgp"]);

	        epService.EPRuntime.SendEvent(new SupportPriceEvent(5, "A"));
	        _event = listener.LastNewData[0];
	        Assert.AreEqual("A", _event["sym"]);
	        Assert.AreEqual((2 + 9 + 18 + 5) / 4.0, _event["avgp"]);
	        _event = listener.LastOldData[0];
	        Assert.AreEqual("A", _event["sym"]);
	        Assert.AreEqual((1 + 2 + 9 + 18) / 4.0, _event["avgp"]);
	    }

	    [Test]
	    public void testSelectStarStdGroupBy() {
	        String stmtText = "select istream * from "+ typeof(SupportMarketDataBean).FullName
	                +".std:groupby('symbol').win:length(2)";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        statement.AddListener(listener);

	        SendEvent("A", 1);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1.0, listener.LastNewData[0]["price"]);
	        Assert.IsTrue(listener.LastNewData[0].Underlying is SupportMarketDataBean);
	    }

	    [Test]
	    public void testSelectExprStdGroupBy() {
	        String stmtText = "select istream price from "+ typeof(SupportMarketDataBean).FullName
	                +".std:groupby('symbol').win:length(2)";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        statement.AddListener(listener);

	        SendEvent("A", 1);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1.0, listener.LastNewData[0]["price"]);
	    }

	    [Test]
	    public void testSelectAvgExprStdGroupBy() {
	        String stmtText = "select istream avg(price) as aprice from "+ typeof(SupportMarketDataBean).FullName
	                +".std:groupby('symbol').win:length(2)";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        statement.AddListener(listener);

	        SendEvent("A", 1);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1.0, listener.LastNewData[0]["aprice"]);
	        SendEvent("B", 3);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(2.0, listener.LastNewData[0]["aprice"]);
	    }

	    [Test]
	    public void testSelectAvgStdGroupByUni() {
	        String stmtText = "select istream average as aprice from "+ typeof(SupportMarketDataBean).FullName
	                +".std:groupby('symbol').win:length(2).stat:uni('price')";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        statement.AddListener(listener);

	        SendEvent("A", 1);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(1.0, listener.LastNewData[0]["aprice"]);
	        SendEvent("B", 3);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(3.0, listener.LastNewData[0]["aprice"]);
	        SendEvent("A", 3);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(2.0, listener.LastNewData[0]["aprice"]);
	        SendEvent("A", 10);
	        SendEvent("A", 20);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(15.0, listener.LastNewData[0]["aprice"]);
	    }

	    [Test]
	    public void testSelectAvgExprGroupBy() {
	        String stmtText = "select istream Avg(price) as aprice, symbol from "+ typeof(SupportMarketDataBean).FullName
	                +".win:length(2) group by symbol";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        statement.AddListener(listener);

	        SendEvent("A", 1);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1.0, listener.LastNewData[0]["aprice"]);
	        Assert.AreEqual("A", listener.LastNewData[0]["symbol"]);
	        SendEvent("B", 3);
	        //there is no A->1 as we already got it out
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(3.0, listener.LastNewData[0]["aprice"]);
	        Assert.AreEqual("B", listener.LastNewData[0]["symbol"]);
	        SendEvent("B", 5);
	        // there is NOW a A->null entry
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(2, listener.LastNewData.Length);
	        Assert.AreEqual(null, listener.LastNewData[0]["aprice"]);
	        Assert.AreEqual(4.0, listener.LastNewData[1]["aprice"]);
	        Assert.AreEqual("B", listener.LastNewData[1]["symbol"]);
	        SendEvent("A", 10);
	        SendEvent("A", 20);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(2, listener.LastNewData.Length);
	        Assert.AreEqual(15.0, listener.LastNewData[0]["aprice"]);//A
	        Assert.AreEqual(null, listener.LastNewData[1]["aprice"]);//B
	    }

	    private void SendEvent(String symbol, double price) {
	        epService.EPRuntime.SendEvent(new SupportMarketDataBean(symbol, price, null, null));
	    }


	    private void SendEvent(long longBoxed, int intBoxed, short shortBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(JOIN_KEY);
	        bean.SetLongBoxed(longBoxed);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetShortBoxed(shortBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendEvent(long longBoxed)
	    {
	        SendEvent(longBoxed, 0, (short)0);
	    }

	    private void SendEventInt(int intBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntBoxed(intBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendEventFloat(float floatBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetFloatBoxed(floatBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendTimerEvent(long msec)
	    {
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(msec));
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
