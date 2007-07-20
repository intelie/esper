///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerf2StreamSimpleJoin
	{
	    private EPServiceProvider epService;
	    private EPStatement joinView;
	    private SupportUpdateListener updateListener;

	    [SetUp]
	    public void SetUp()
	    {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;
            
            epService = EPServiceProviderManager.GetProvider("TestPerf2StreamSimpleJoin");
	        epService.Initialize();
	        updateListener = new SupportUpdateListener();

	        String joinStatement = "select * from " +
	                typeof(SupportMarketDataBean).FullName + ".win:length(1000000)," +
	                typeof(SupportBean).FullName + ".win:length(1000000)" +
	            " where symbol=string";

	        joinView = epService.EPAdministrator.CreateEQL(joinStatement);
            joinView.AddListener(updateListener);
	    }

	    public void TearDown()
	    {
	        epService.Initialize();
	    }

	    [Test]
	    public void testPerformanceJoinNoResults()
	    {
	        String methodName = ".testPerformanceJoinNoResults";

	        // Send events for each stream
	        log.Info(methodName + " Preloading events");
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 1000; i++)
	        {
	            SendEvent(MakeMarketEvent("IBM_" + i));
	            SendEvent(MakeSupportEvent("CSCO_" + i));
	        }
	        log.Info(methodName + " Done preloading");

	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(methodName + " delta=" + (endTime - startTime));

	        // Stay below 50 ms
	        Assert.IsTrue((endTime - startTime) < 500);
	    }

	    [Test]
	    public void testJoinPerformanceStreamA()
	    {
	        String methodName = ".testJoinPerformanceStreamA";

	        // Send 100k events
	        log.Info(methodName + " Preloading events");
	        for (int i = 0; i < 50000; i++)
	        {
	            SendEvent(MakeMarketEvent("IBM_" + i));
	        }
	        log.Info(methodName + " Done preloading");

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        SendEvent(MakeSupportEvent("IBM_10"));
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(methodName + " delta=" + (endTime - startTime));

	        Assert.AreEqual(1, updateListener.LastNewData.Length);
	        // Stay below 50 ms
	        Assert.IsTrue((endTime - startTime) < 50);
	    }

	    [Test]
	    public void testJoinPerformanceStreamB()
	    {
	        String methodName = ".testJoinPerformanceStreamB";

	        // Send 100k events
	        log.Info(methodName + " Preloading events");
	        for (int i = 0; i < 50000; i++)
	        {
	            SendEvent(MakeSupportEvent("IBM_" + i));
	        }
	        log.Info(methodName + " Done preloading");

	        long startTime = DateTimeHelper.CurrentTimeMillis;

	        updateListener.Reset();
	        SendEvent(MakeMarketEvent("IBM_" + 10));

	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(methodName + " delta=" + (endTime - startTime));

	        Assert.AreEqual(1, updateListener.LastNewData.Length);
	        // Stay below 50 ms
	        Assert.IsTrue((endTime - startTime) < 25);
	    }

	    private void SendEvent(Object _event)
	    {
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private Object MakeSupportEvent(String id)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(id);
	        return bean;
	    }

	    private Object MakeMarketEvent(String id)
	    {
	        return new SupportMarketDataBean(id, 0, (long) 0, "");
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
