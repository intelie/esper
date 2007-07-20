// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;
using net.esper.compat;
using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.eql;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.db
{
	[TestFixture]
	public class TestDatabaseJoinPerformance
	{
	    private EPServiceProvider epServiceRetained;
	    private EPServiceProvider epServicePooled;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        ConfigurationDBRef configDB = new ConfigurationDBRef();
	        configDB.DriverManagerConnection = (SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
	        configDB.ConnectionLifecycle = (ConnectionLifecycleEnum.RETAIN);
	        Configuration configuration = new Configuration();
	        configuration.AddDatabaseReference("MyDB", configDB);

	        epServiceRetained = EPServiceProviderManager.GetProvider("TestDatabaseJoinRetained", configuration);
	        epServiceRetained.Initialize();
	        epServiceRetained.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

	        configDB = new ConfigurationDBRef();
	        configDB.DriverManagerConnection = (SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
	        configDB.ConnectionLifecycle = (ConnectionLifecycleEnum.POOLED);
	        configuration = new Configuration();
	        configuration.AddDatabaseReference("MyDB", configDB);
	        epServicePooled = EPServiceProviderManager.GetProvider("TestDatabaseJoinPooled", configuration);
	        epServicePooled.Initialize();
	        epServicePooled.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    public void Test100EventsRetained()
	    {
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        Try100Events(epServiceRetained);
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(".test100EventsRetained delta=" + (endTime - startTime));
	        Assert.IsTrue(endTime - startTime < 5000);
	    }

	    [Test]
	    public void Test100EventsPooled()
	    {
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        Try100Events(epServicePooled);
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(".test100EventsPooled delta=" + (endTime - startTime));
	        Assert.IsTrue(endTime - startTime < 10000);
	    }

	    [Test]
	    public void TestSelectRStream()
	    {
	        String stmtText = "select rstream myvarchar from " +
	                typeof(SupportBean_S0).FullName + ".win:length(1000) as s0," +
	                " sql:MyDB ['select myvarchar from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = epServiceRetained.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        // 1000 events should enter the window fast, no joins
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 1000; i++)
	        {
	            SupportBean_S0 bean = new SupportBean_S0(10);
	            epServiceRetained.EPRuntime.SendEvent(bean);
	            Assert.IsFalse(listener.IsInvoked);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        log.Info(".testSelectRStream delta=" + (endTime - startTime));
	        Assert.IsTrue(endTime - startTime < 500);

	        // 1001st event should finally join and produce a result
	        do
	        {
	            SupportBean_S0 bean = new SupportBean_S0(10);
	            epServiceRetained.EPRuntime.SendEvent(bean);
	            Assert.AreEqual("J", listener.AssertOneGetNewAndReset()["myvarchar"]);
	        } while (false); 
	    }

        [Test]
	    public void TestSelectIStream()
	    {
	        // set time to zero
	        epServiceRetained.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	        epServiceRetained.EPRuntime.SendEvent(new CurrentTimeEvent(0));

	        String stmtText = "select istream myvarchar from " +
	                typeof(SupportBean_S0).FullName + ".win:time(1 sec) as s0," +
	                " sql:MyDB ['select myvarchar from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = epServiceRetained.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        // Send 100 events which all fireStatementStopped a join
	        for (int i = 0; i < 100; i++)
	        {
	            SupportBean_S0 bean = new SupportBean_S0(5);
	            epServiceRetained.EPRuntime.SendEvent(bean);
	            Assert.AreEqual("E", listener.AssertOneGetNewAndReset()["myvarchar"]);
	        }

	        // now advance the time, this should not produce events or join
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        epServiceRetained.EPRuntime.SendEvent(new CurrentTimeEvent(2000));
	        long endTime = DateTimeHelper.CurrentTimeMillis;

	        log.Info(".testSelectIStream delta=" + (endTime - startTime));
	        Assert.IsTrue(endTime - startTime < 200);
	        Assert.IsFalse(listener.IsInvoked);
	    }

	    private void Try100Events(EPServiceProvider engine)
	    {
	        String stmtText = "select myint from " +
	                typeof(SupportBean_S0).FullName + " as s0," +
	                " sql:MyDB ['select myint from mytesttable where ${id} = mytesttable.mybigint'] as s1";

	        EPStatement statement = engine.EPAdministrator.CreateEQL(stmtText);
	        listener = new SupportUpdateListener();
	        statement.AddListener(listener.Update);

	        for (int i = 0; i < 100; i++)
	        {
	            int id = i % 10 + 1;

	            SupportBean_S0 bean = new SupportBean_S0(id);
	            engine.EPRuntime.SendEvent(bean);

	            EventBean received = listener.AssertOneGetNewAndReset();
	            Assert.AreEqual(id * 10, received["myint"]);
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
