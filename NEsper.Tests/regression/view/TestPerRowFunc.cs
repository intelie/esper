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
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestPerRowFunc
	{
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
	    public void testCoalesceBeans()
	    {
	        TryCoalesceBeans("select Coalesce(a.string, b.string) as myString, Coalesce(a, b) as myBean" +
	                          " from pattern [every (a=" + typeof(SupportBean).FullName + "(string='s0') or b=" + typeof(SupportBean).FullName + "(string='s1'))]");

	        TryCoalesceBeans("SELECT COALESCE(a.string, b.string) AS myString, COALESCE(a, b) AS myBean" +
	                          " FROM PATTERN [EVERY (a=" + typeof(SupportBean).FullName + "(string='s0') OR b=" + typeof(SupportBean).FullName + "(string='s1'))]");
	    }

	    private void TryCoalesceBeans(String viewExpr)
	    {
	        epService.Initialize();
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        SupportBean _event = SendEvent("s0");
	        EventBean eventReceived = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("s0", eventReceived["myString"]);
	        Assert.AreSame(_event, eventReceived["myBean"]);

	        _event = SendEvent("s1");
	        eventReceived = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("s1", eventReceived["myString"]);
	        Assert.AreSame(_event, eventReceived["myBean"]);
	    }

	    [Test]
	    public void testCoalesceLong()
	    {
	        SetupCoalesce("coalesce(longBoxed, intBoxed, shortBoxed)");
	        Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("result"));

	        SendEvent(1L, 2, (short) 3);
	        Assert.AreEqual(1L, testListener.AssertOneGetNewAndReset()["result"]);

	        SendBoxedEvent(null, 2, null);
	        Assert.AreEqual(2L, testListener.AssertOneGetNewAndReset()["result"]);

	        SendBoxedEvent(null, null, Int16.Parse("3"));
	        Assert.AreEqual(3L, testListener.AssertOneGetNewAndReset()["result"]);

	        SendBoxedEvent(null, null, null);
	        Assert.AreEqual(null, testListener.AssertOneGetNewAndReset()["result"]);
	    }

	    [Test]
	    public void testCoalesceDouble()
	    {
	        SetupCoalesce("coalesce(null, byteBoxed, shortBoxed, intBoxed, longBoxed, floatBoxed, doubleBoxed)");
	        Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("result"));

	        SendEventWithDouble(null, null, null, null, null, null);
	        Assert.AreEqual(null, testListener.AssertOneGetNewAndReset()["result"]);

	        SendEventWithDouble(null, Int16.Parse("2"), null, null, null, 1d);
	        Assert.AreEqual(2d, testListener.AssertOneGetNewAndReset()["result"]);

	        SendEventWithDouble(null, null, null, null, null, 100d);
	        Assert.AreEqual(100d, testListener.AssertOneGetNewAndReset()["result"]);

	        SendEventWithDouble(null, null, null, null, 10f, 100d);
	        Assert.AreEqual(10d, testListener.AssertOneGetNewAndReset()["result"]);

	        SendEventWithDouble(null, null, 1, 5l, 10f, 100d);
	        Assert.AreEqual(1d, testListener.AssertOneGetNewAndReset()["result"]);

	        SendEventWithDouble(SByte.Parse("3"), null, null, null, null, null);
	        Assert.AreEqual(3d, testListener.AssertOneGetNewAndReset()["result"]);

	        SendEventWithDouble(null, null, null, 5l, 10f, 100d);
	        Assert.AreEqual(5d, testListener.AssertOneGetNewAndReset()["result"]);
	    }

	    private void SetupCoalesce(String coalesceExpr)
	    {
	        epService.Initialize();
	        String viewExpr = "select " + coalesceExpr + " as result" +
	                          " from " + typeof(SupportBean).FullName + ".win:length(1000) ";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);
	    }

	    [Test]
	    public void testCoalesceInvalid()
	    {
	        String viewExpr = "select Coalesce(null, null) as result" +
	                          " from " + typeof(SupportBean).FullName + ".win:length(3) ";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        Assert.AreEqual(null, selectTestView.EventType.GetPropertyType("result"));

	        TryCoalesceInvalid("coalesce(intPrimitive)");
	        TryCoalesceInvalid("coalesce(intPrimitive, string)");
	        TryCoalesceInvalid("coalesce(intPrimitive, xxx)");
	        TryCoalesceInvalid("coalesce(intPrimitive, boolBoxed)");
	        TryCoalesceInvalid("coalesce(charPrimitive, longBoxed)");
	        TryCoalesceInvalid("coalesce(charPrimitive, string, string)");
	        TryCoalesceInvalid("coalesce(string, longBoxed)");
	        TryCoalesceInvalid("coalesce(null, longBoxed, string)");
	        TryCoalesceInvalid("coalesce(null, null, boolBoxed, 1l)");
	    }

	    private void TryCoalesceInvalid(String coalesceExpr)
	    {
	        String viewExpr = "select " + coalesceExpr + " as result" +
	                          " from " + typeof(SupportBean).FullName + ".win:length(3) ";

	        try {
	            selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        }
	        catch (EPStatementException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testMinMaxEventType()
	    {
	        SetUpMinMax();
	        EventType type = selectTestView.EventType;
	        log.Debug(".testGetEventType properties=" + CollectionHelper.Render(type.PropertyNames));
	        Assert.AreEqual(typeof(long?), type.GetPropertyType("myMax"));
	        Assert.AreEqual(typeof(long?), type.GetPropertyType("myMin"));
	        Assert.AreEqual(typeof(long?), type.GetPropertyType("myMinEx"));
	        Assert.AreEqual(typeof(long?), type.GetPropertyType("myMaxEx"));
	    }

	    [Test]
	    public void testMinMaxWindowStats()
	    {
	        SetUpMinMax();
	        testListener.Reset();

	        SendEvent(10, 20, (short)4);
	        EventBean received = testListener.GetAndResetLastNewData()[0];
	        Assert.AreEqual(20L, received["myMax"]);
	        Assert.AreEqual(10L, received["myMin"]);
	        Assert.AreEqual(4L, received["myMinEx"]);
	        Assert.AreEqual(20L, received["myMaxEx"]);

	        SendEvent(-10, -20, (short)-30);
	        received = testListener.GetAndResetLastNewData()[0];
	        Assert.AreEqual(-10L, received["myMax"]);
	        Assert.AreEqual(-20L, received["myMin"]);
	        Assert.AreEqual(-30L, received["myMinEx"]);
	        Assert.AreEqual(-10L, received["myMaxEx"]);
	    }

	    [Test]
	    public void testOperators()
	    {
	        String viewExpr = "select longBoxed % intBoxed as myMod " +
	                          " from " + typeof(SupportBean).FullName + ".win:length(3) where Not(longBoxed > intBoxed)";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        SendEvent(1, 1, (short)0);
	        Assert.AreEqual(0l, testListener.LastNewData[0]["myMod"]);
	        testListener.Reset();

	        SendEvent(2, 1, (short)0);
	        Assert.IsFalse(testListener.GetAndClearIsInvoked());

	        SendEvent(2, 3, (short)0);
	        Assert.AreEqual(2l, testListener.LastNewData[0]["myMod"]);
	        testListener.Reset();
	    }

	    [Test]
	    public void testConcat()
	    {
	        String viewExpr = "select p00 || p01 as c1, p00 || p01 || p02 as c2, p00 || '|' || p01 as c3" +
	                          " from " + typeof(SupportBean_S0).FullName + ".win:length(10)";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "a", "b", "c"));
	        AssertConcat("ab", "abc", "a|b");

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, null, "b", "c"));
	        AssertConcat(null, null, null);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "", "b", "c"));
	        AssertConcat("b", "bc", "|b");

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "123", null, "c"));
	        AssertConcat(null, null, null);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "123", "456", "c"));
	        AssertConcat("123456", "123456c", "123|456");

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "123", "456", null));
	        AssertConcat("123456", null, "123|456");
	    }

	    private void SetUpMinMax()
	    {
	        String viewExpr = "select Max(longBoxed, intBoxed) as myMax, " +
	                                 "max(longBoxed, intBoxed, shortBoxed) as myMaxEx," +
	                                 "min(longBoxed, intBoxed) as myMin," +
	                                 "min(longBoxed, intBoxed, shortBoxed) as myMinEx" +
	                          " from " + typeof(SupportBean).FullName + ".win:length(3) ";
	        selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
	        selectTestView.AddListener(testListener);
	    }

	    private SupportBean SendEvent(String _string)
	    {
	        SupportBean bean = new SupportBean();
            bean.SetString(_string);
	        epService.EPRuntime.SendEvent(bean);
	        return bean;
	    }

	    private void SendEvent(long longBoxed, int intBoxed, short shortBoxed)
	    {
	        SendBoxedEvent(longBoxed, intBoxed, shortBoxed);
	    }

	    private void SendBoxedEvent(long? longBoxed, int? intBoxed, short? shortBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetLongBoxed(longBoxed);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetShortBoxed(shortBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

        private void SendEventWithDouble(sbyte? byteBoxed, short? shortBoxed, int? intBoxed, long? longBoxed, float? floatBoxed, double? doubleBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetByteBoxed(byteBoxed);
	        bean.SetShortBoxed(shortBoxed);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetLongBoxed(longBoxed);
	        bean.SetFloatBoxed(floatBoxed);
	        bean.SetDoubleBoxed(doubleBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void AssertConcat(String c1, String c2, String c3)
	    {
	        EventBean _event = testListener.LastNewData[0];
	        Assert.AreEqual(c1, _event["c1"]);
	        Assert.AreEqual(c2, _event["c2"]);
	        Assert.AreEqual(c3, _event["c3"]);
	        testListener.Reset();
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
