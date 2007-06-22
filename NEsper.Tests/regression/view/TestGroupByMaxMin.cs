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
	public class TestGroupByMaxMin
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
	    public void TestMinMaxView()
	    {
	        String viewExpr = "select symbol, " +
	                                  "min(all volume) as minVol," +
	                                  "max(all volume) as maxVol," +
	                                  "min(distinct volume) as minDistVol," +
	                                  "max(distinct volume) as maxDistVol" +
	                          " from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " +
	                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                          "group by symbol";

	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        RunAssertion();
	    }

	    [Test]
	    public void TestMinMaxJoin()
	    {
	        String viewExpr = "select symbol, " +
	                                  "min(volume) as minVol," +
	                                  "max(volume) as maxVol," +
	                                  "min(distinct volume) as minDistVol," +
	                                  "max(distinct volume) as maxDistVol" +
	                          " from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " +
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
	    public void TestMinNoGroupHaving()
	    {
	        String stmtText = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:time(5 sec) " +
	                          "having volume > Min(volume) * 1.3";

	        selectTestView = epService.EPAdministrator.CreateEQL(stmtText);
	        selectTestView.AddListener(testListener);

	        SendEvent("DELL", 100L);
	        SendEvent("DELL", 105L);
	        SendEvent("DELL", 100L);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent("DELL", 131L);
	        Assert.AreEqual("DELL", testListener.AssertOneGetNewAndReset()["symbol"]);

	        SendEvent("DELL", 132L);
	        Assert.AreEqual("DELL", testListener.AssertOneGetNewAndReset()["symbol"]);

	        SendEvent("DELL", 129L);
	        Assert.IsFalse(testListener.IsInvoked);
	    }

	    [Test]
	    public void TestMinNoGroupSelectHaving()
	    {
	        String stmtText = "select symbol, Min(volume) as mymin from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
	                          "having volume > Min(volume) * 1.3";

	        selectTestView = epService.EPAdministrator.CreateEQL(stmtText);
	        selectTestView.AddListener(testListener);

	        SendEvent("DELL", 100L);
	        SendEvent("DELL", 105L);
	        SendEvent("DELL", 100L);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent("DELL", 131L);
	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("DELL", _event["symbol"]);
	        Assert.AreEqual(100L, _event["mymin"]);

	        SendEvent("DELL", 132L);
	        _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("DELL", _event["symbol"]);
	        Assert.AreEqual(100L, _event["mymin"]);

	        SendEvent("DELL", 129L);
	        SendEvent("DELL", 125L);
	        SendEvent("DELL", 125L);
	        Assert.IsFalse(testListener.IsInvoked);

	        SendEvent("DELL", 170L);
	        _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("DELL", _event["symbol"]);
	        Assert.AreEqual(125L, _event["mymin"]);
	    }

	    private void RunAssertion()
	    {
	        // assert select result type
	        Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("minVol"));
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("maxVol"));
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("minDistVol"));
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("maxDistVol"));

	        SendEvent(SYMBOL_DELL, 50L);
	        AssertEvents(SYMBOL_DELL, null, null, null, null,
	                SYMBOL_DELL, 50L, 50L, 50L, 50L
	                );

	        SendEvent(SYMBOL_DELL, 30L);
	        AssertEvents(SYMBOL_DELL, 50L, 50L, 50L, 50L,
	                SYMBOL_DELL, 30L, 50L, 30L, 50L
	                );

	        SendEvent(SYMBOL_DELL, 30L);
	        AssertEvents(SYMBOL_DELL, 30L, 50L, 30L, 50L,
	                SYMBOL_DELL, 30L, 50L, 30L, 50L
	                );

	        SendEvent(SYMBOL_DELL, 90L);
	        AssertEvents(SYMBOL_DELL, 30L, 50L, 30L, 50L,
	                SYMBOL_DELL, 30L, 90L, 30L, 90L
	                );

	        SendEvent(SYMBOL_DELL, 100L);
	        AssertEvents(SYMBOL_DELL, 30L, 90L, 30L, 90L,
	                SYMBOL_DELL, 30L, 100L, 30L, 100L
	                );

	        SendEvent(SYMBOL_IBM, 20L);
	        SendEvent(SYMBOL_IBM, 5L);
	        SendEvent(SYMBOL_IBM, 15L);
	        SendEvent(SYMBOL_IBM, 18L);
	        AssertEvents(SYMBOL_IBM, 5L, 20L, 5L, 20L,
	                SYMBOL_IBM, 5L, 18L, 5L, 18L
	                );

	        SendEvent(SYMBOL_IBM, null);
	        AssertEvents(SYMBOL_IBM, 5L, 18L, 5L, 18L,
	                SYMBOL_IBM, 15L, 18L, 15L, 18L
	                );

	        SendEvent(SYMBOL_IBM, null);
	        AssertEvents(SYMBOL_IBM, 15L, 18L, 15L, 18L,
	                SYMBOL_IBM, 18L, 18L, 18L, 18L
	                );

	        SendEvent(SYMBOL_IBM, null);
	        AssertEvents(SYMBOL_IBM, 18L, 18L, 18L, 18L,
	                SYMBOL_IBM, null, null, null, null
	                );
	    }

	    private void AssertEvents(String symbolOld, long? minVolOld, long? maxVolOld, long? minDistVolOld, long? maxDistVolOld,
	                              String symbolNew, long? minVolNew, long? maxVolNew, long? minDistVolNew, long? maxDistVolNew)
	    {
	        EventBean[] oldData = testListener.LastOldData;
	        EventBean[] newData = testListener.LastNewData;

	        Assert.AreEqual(1, oldData.Length);
	        Assert.AreEqual(1, newData.Length);

	        Assert.AreEqual(symbolOld, oldData[0]["symbol"]);
	        Assert.AreEqual(minVolOld, oldData[0]["minVol"]);
	        Assert.AreEqual(maxVolOld, oldData[0]["maxVol"]);
	        Assert.AreEqual(minDistVolOld, oldData[0]["minDistVol"]);
	        Assert.AreEqual(maxDistVolOld, oldData[0]["maxDistVol"]);

	        Assert.AreEqual(symbolNew, newData[0]["symbol"]);
	        Assert.AreEqual(minVolNew, newData[0]["minVol"]);
	        Assert.AreEqual(maxVolNew, newData[0]["maxVol"]);
	        Assert.AreEqual(minDistVolNew, newData[0]["minDistVol"]);
	        Assert.AreEqual(maxDistVolNew, newData[0]["maxDistVol"]);

	        testListener.Reset();
	        Assert.IsFalse(testListener.IsInvoked);
	    }

	    private void SendEvent(String symbol, long? volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
