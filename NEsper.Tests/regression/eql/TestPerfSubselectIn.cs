// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client.time;
using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerfSubselectIn
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("MyEvent", typeof(SupportBean));
	        config.AddEventTypeAlias("S0", typeof(SupportBean_S0));
	        config.AddEventTypeAlias("S1", typeof(SupportBean_S1));
	        config.AddEventTypeAlias("S2", typeof(SupportBean_S2));
	        config.AddEventTypeAlias("S3", typeof(SupportBean_S3));
	        epService = EPServiceProviderManager.GetDefaultProvider(config);
	        epService.Initialize();
	        listener = new SupportUpdateListener();

	        // Use external clocking for the test, reduces logging
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestPerformanceWhereClauseCoercion()
	    {
	        String stmtText = "select intPrimitive from MyEvent(string='A') as s0 where intPrimitive in (" +
	                            "select longBoxed from MyEvent(string='B').win:length(10000) where s0.intPrimitive = longBoxed)";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload with 10k events
	        for (int i = 0; i < 10000; i++)
	        {
	            SupportBean bean = new SupportBean();
	            bean.SetString("B");
	            bean.SetLongBoxed((long)i);
	            epService.EPRuntime.SendEvent(bean);
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 10000; i++)
	        {
	            int index = 5000 + i % 1000;
	            SupportBean bean = new SupportBean();
	            bean.SetString("A");
	            bean.SetIntPrimitive(index);
	            epService.EPRuntime.SendEvent(bean);
	            Assert.AreEqual(index, listener.AssertOneGetNewAndReset()["intPrimitive"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void TestPerformanceWhereClause()
	    {
	        String stmtText = "select id from S0 as s0 where p00 in (" +
	                            "select p10 from S1.win:length(10000) where s0.p00 = p10)";
	        TryPerformanceOneCriteria(stmtText);
	    }

	    private void TryPerformanceOneCriteria(String stmtText)
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload with 10k events
	        for (int i = 0; i < 10000; i++)
	        {
	            epService.EPRuntime.SendEvent(new SupportBean_S1(i, Convert.ToString(i)));
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 10000; i++)
	        {
	            int index = 5000 + i % 1000;
	            epService.EPRuntime.SendEvent(new SupportBean_S0(index, Convert.ToString(index)));
	            Assert.AreEqual(index, listener.AssertOneGetNewAndReset()["id"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000,"Failed perf test, delta=" + delta);
	    }
	}
} // End of namespace
