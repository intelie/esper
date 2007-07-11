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
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerf2StreamSimpleJoinCoercion
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

	    public void TearDown()
	    {
	        epService.Initialize();
	    }

	    [Test]
	    public void testPerformanceCoercionForward()
	    {
	        String stmt = "select A.longBoxed as value from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) as A," +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) as B" +
	            " where A.longBoxed=B.intPrimitive";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmt);
	        statement.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            epService.EPRuntime.SendEvent(MakeSupportEvent("A", 0, i));
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            epService.EPRuntime.SendEvent(MakeSupportEvent("B", index, 0));
	            Assert.AreEqual((long)index, listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerformanceCoercionBack()
	    {
	        String stmt = "select A.intPrimitive as value from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) as A," +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) as B" +
	            " where A.intPrimitive=B.longBoxed";

	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmt);
	        statement.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            epService.EPRuntime.SendEvent(MakeSupportEvent("A", i, 0));
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            epService.EPRuntime.SendEvent(MakeSupportEvent("B", 0, index));
	            Assert.AreEqual(index, listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }

	    private Object MakeSupportEvent(String _string, int intPrimitive, long longBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(_string);
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetLongBoxed(longBoxed);
	        return bean;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
