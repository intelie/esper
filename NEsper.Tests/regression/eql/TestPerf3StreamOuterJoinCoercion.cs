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
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerf3StreamOuterJoinCoercion
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
	        String stmtText = "select s1.intBoxed as v1, s2.longBoxed as v2, s3.doubleBoxed as v3 from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) s1 " +
	                " left outer join " +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) s2 on s1.intBoxed=s2.longBoxed " +
	                " left outer join " +
	                typeof(SupportBean).FullName + "(string='C').win:length(1000000) s3 on s1.intBoxed=s3.doubleBoxed";

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
	            EventBean _event = listener.AssertOneGetNewAndReset();
	            Assert.AreEqual(index, _event["v1"]);
	            Assert.AreEqual((long)index, _event["v2"]);
	            Assert.AreEqual((double)index, _event["v3"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerfCoercion3waySceneTwo()
	    {
	        String stmtText = "select s1.intBoxed as v1, s2.longBoxed as v2, s3.doubleBoxed as v3 from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) s1 " +
	                " left outer join " +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) s2 on s1.intBoxed=s2.longBoxed " +
	                " left outer join " +
	                typeof(SupportBean).FullName + "(string='C').win:length(1000000) s3 on s1.intBoxed=s3.doubleBoxed";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            SendEvent("B", 0, i, 0);
	            SendEvent("A", i, 0, 0);
	        }

	        listener.Reset();
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            SendEvent("C", 0, 0, index);
	            EventBean _event = listener.AssertOneGetNewAndReset();
	            Assert.AreEqual(index, _event["v1"]);
	            Assert.AreEqual((long)index, _event["v2"]);
	            Assert.AreEqual((double)index, _event["v3"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerfCoercion3waySceneThree()
	    {
	        String stmtText = "select s1.intBoxed as v1, s2.longBoxed as v2, s3.doubleBoxed as v3 from " +
	                typeof(SupportBean).FullName + "(string='A').win:length(1000000) s1 " +
	                " left outer join " +
	                typeof(SupportBean).FullName + "(string='B').win:length(1000000) s2 on s1.intBoxed=s2.longBoxed " +
	                " left outer join " +
	                typeof(SupportBean).FullName + "(string='C').win:length(1000000) s3 on s1.intBoxed=s3.doubleBoxed";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload
	        for (int i = 0; i < 10000; i++)
	        {
	            SendEvent("A", i, 0, 0);
	            SendEvent("C", 0, 0, i);
	        }

	        listener.Reset();
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = 5000 + i % 1000;
	            SendEvent("B", 0, index, 0);
	            EventBean _event = listener.AssertOneGetNewAndReset();
	            Assert.AreEqual(index, _event["v1"]);
	            Assert.AreEqual((long)index, _event["v2"]);
	            Assert.AreEqual((double)index, _event["v3"]);
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
