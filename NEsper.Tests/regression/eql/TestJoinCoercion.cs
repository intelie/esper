// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestJoinCoercion
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        listener = new SupportUpdateListener();
	    }

	    [Test]
	    public void _TestJoinCoercion()
	    {
	        String joinStatement = "select volume from " +
	                typeof(SupportMarketDataBean).FullName + ".win:length(3) as s0," +
	                typeof(SupportBean).FullName + "().win:length(3) as s1 " +
	                " where s0.volume = s1.intPrimitive";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(joinStatement);
	        stmt.AddListener(listener);

	        SendBeanEvent(100);
	        SendMarketEvent(100);
	        Assert.AreEqual(100L, listener.AssertOneGetNewAndReset()["volume"]);
	    }

	    private void SendBeanEvent(int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendMarketEvent(long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean("", 0, volume, null);
	        epService.EPRuntime.SendEvent(bean);
	    }
	}
} // End of namespace
