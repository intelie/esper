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

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestCountAll
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;
	    private EPStatement selectTestView;

	    [SetUp]
	    public void SetUp()
	    {
	        listener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void testSize()
	    {
	        String statementText = "select size from " + typeof(SupportMarketDataBean).FullName + ".std:size()";
	        selectTestView = epService.EPAdministrator.CreateEQL(statementText);
	        selectTestView.AddListener(listener);

	        SendEvent("DELL", 1L);
	        AssertSize(1, 0);

	        SendEvent("DELL", 1L);
	        AssertSize(2, 1);
	    }

	    [Test]
	    public void testCount()
	    {
	    	String statementText = "select Count(*) as cnt from " + typeof(SupportMarketDataBean).FullName + ".win:time(1)";
	        selectTestView = epService.EPAdministrator.CreateEQL(statementText);
	        selectTestView.AddListener(listener);

	        SendEvent("DELL", 1L);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(1L, listener.LastNewData[0]["cnt"]);

	        SendEvent("DELL", 1L);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(2L, listener.LastNewData[0]["cnt"]);

	        SendEvent("DELL", 1L);
	        Assert.IsTrue(listener.GetAndClearIsInvoked());
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        Assert.AreEqual(3L, listener.LastNewData[0]["cnt"]);
	    }

	    [Test]
	    public void testCountHaving()
	    {
	        String _event = typeof(SupportBean).FullName;
	        String statementText = "select Sum(intPrimitive) as mysum from " + _event + " having Sum(intPrimitive) = 2";
	        selectTestView = epService.EPAdministrator.CreateEQL(statementText);
	        selectTestView.AddListener(listener);

	        SendEvent();
	        Assert.IsFalse(listener.GetAndClearIsInvoked());
	        SendEvent();
	        Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["mysum"]);
	        SendEvent();
	        Assert.AreEqual(2, listener.AssertOneGetOldAndReset()["mysum"]);
	    }

	    [Test]
	    public void testSumHaving()
	    {
	        String _event = typeof(SupportBean).FullName;
	        String statementText = "select Count(*) as mysum from " + _event + " having Count(*) = 2";
	        selectTestView = epService.EPAdministrator.CreateEQL(statementText);
	        selectTestView.AddListener(listener);

	        SendEvent();
	        Assert.IsFalse(listener.GetAndClearIsInvoked());
	        SendEvent();
	        Assert.AreEqual(2L, listener.AssertOneGetNewAndReset()["mysum"]);
	        SendEvent();
	        Assert.AreEqual(2L, listener.AssertOneGetOldAndReset()["mysum"]);
	    }

	    private void SendEvent(String symbol, long? volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendEvent()
	    {
	        SupportBean bean = new SupportBean("", 1);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void AssertSize(long newSize, long oldSize)
	    {
	        listener.AssertFieldEqualsAndReset("size", new Object[] {newSize}, new Object[] {oldSize});
	    }
	}
} // End of namespace
