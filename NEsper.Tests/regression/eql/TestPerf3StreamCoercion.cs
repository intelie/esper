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
	public class TestPerf3StreamCoercion
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        listener = new SupportUpdateListener();
	    }

	    [Test]
	    public void testPerfCoercion3waySceneOne()
	    {
	        String stmtText = "select s1.intBoxed as value from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) s1," +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) s2," +
	                typeof(SupportBean).FullName + "(string='C').win:length(1000000) s3" +
	            " where s1.intBoxed=s2.longBoxed and s1.intBoxed=s3.doubleBoxed";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            SendEvent("B", 0, i, 0);
	            SendEvent("C", 0, 0, i);
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            SendEvent("A", index, 0, 0);
	            Assert.AreEqual(index, listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerfCoercion3waySceneTwo()
	    {
	        String stmtText = "select s1.intBoxed as value from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) s1," +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) s2," +
	                typeof(SupportBean).FullName + "(string='C').win:length(1000000) s3" +
	            " where s1.intBoxed=s2.longBoxed and s1.intBoxed=s3.doubleBoxed";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            SendEvent("A", i, 0, 0);
	            SendEvent("B", 0, i, 0);
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            SendEvent("C", 0, 0, index);
	            Assert.AreEqual(index, listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerfCoercion3waySceneThree()
	    {
	        String stmtText = "select s1.intBoxed as value from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) s1," +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) s2," +
	                typeof(SupportBean).FullName + "(string='C').win:length(1000000) s3" +
	            " where s1.intBoxed=s2.longBoxed and s1.intBoxed=s3.doubleBoxed";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            SendEvent("A", i, 0, 0);
	            SendEvent("C", 0, 0, i);
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            SendEvent("B", 0, index, 0);
	            Assert.AreEqual(index, listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }

	    private void SendEvent(String _string, int intBoxed, long longBoxed, double doubleBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(_string);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetLongBoxed(longBoxed);
	        bean.SetDoubleBoxed(doubleBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
