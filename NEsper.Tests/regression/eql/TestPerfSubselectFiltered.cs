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
using net.esper.client.time;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPerfSubselectFiltered
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        Configuration config = new Configuration();
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
	    public void testPerformanceOneCriteria()
	    {
	        String stmtText = "select (select p10 from S1.win:length(100000) where id = s0.id) as value from S0 as s0";

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
	            Assert.AreEqual(Convert.ToString(index), listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerformanceTwoCriteria()
	    {
	        String stmtText = "select (select p10 from S1.win:length(100000) where s0.id = id and p10 = s0.p00) as value from S0 as s0";

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
	            Assert.AreEqual(Convert.ToString(index), listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }

	    [Test]
	    public void testPerformanceJoin3CriteriaSceneOne()
	    {
	        String stmtText = "select (select p00 from S0.win:length(100000) where p00 = s1.p10 and p01 = s2.p20 and p02 = s3.p30) as value " +
	                "from S1.win:length(100000) as s1, S2.win:length(100000) as s2, S3.win:length(100000) as s3 where s1.id = s2.id and s2.id = s3.id";
	        TryPerfJoin3Criteria(stmtText);
	    }

	    [Test]
	    public void testPerformanceJoin3CriteriaSceneTwo()
	    {
	        String stmtText = "select (select p00 from S0.win:length(100000) where p01 = s2.p20 and p00 = s1.p10 and p02 = s3.p30 and id >= 0) as value " +
	                "from S3.win:length(100000) as s3, S1.win:length(100000) as s1, S2.win:length(100000) as s2 where s2.id = s3.id and s1.id = s2.id";
	        TryPerfJoin3Criteria(stmtText);
	    }

	    private void TryPerfJoin3Criteria(String stmtText)
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // preload with 10k events
	        for (int i = 0; i < 10000; i++)
	        {
	            epService.EPRuntime.SendEvent(new SupportBean_S0(i, Convert.ToString(i), Convert.ToString(i + 1), Convert.ToString(i + 2)));
	        }

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 5000; i++)
	        {
	            int index = i;
	            epService.EPRuntime.SendEvent(new SupportBean_S1(i, Convert.ToString(index)));
                epService.EPRuntime.SendEvent(new SupportBean_S2(i, Convert.ToString(index + 1)));
                epService.EPRuntime.SendEvent(new SupportBean_S3(i, Convert.ToString(index + 2)));
                Assert.AreEqual(Convert.ToString(index), listener.AssertOneGetNewAndReset()["value"]);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;

	        Assert.IsTrue(delta < 1000, "Failed perf test, delta=" + delta);
	    }
	}
} // End of namespace
