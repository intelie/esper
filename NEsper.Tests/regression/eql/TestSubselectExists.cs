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
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestSubselectExists
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

            Configuration config = new Configuration();
	        config.AddEventTypeAlias("S0", typeof(SupportBean_S0));
	        config.AddEventTypeAlias("S1", typeof(SupportBean_S1));
	        config.AddEventTypeAlias("S2", typeof(SupportBean_S2));
	        epService = EPServiceProviderManager.GetDefaultProvider(config);
	        epService.Initialize();
	        listener = new SupportUpdateListener();

	        // Use external clocking for the test, reduces logging
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void testExistsInSelect()
	    {
	        String stmtText = "select exists (select * from S1.win:length(1000)) as value from S0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void testExists()
	    {
	        String stmtText = "select id from S0 where exists (select * from S1.win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-2));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.AreEqual(3, listener.AssertOneGetNewAndReset()["id"]);
	    }

	    [Test]
	    public void testExistsFiltered()
	    {
	        String stmtText = "select id from S0 as s0 where exists (select * from S1.win:length(1000) as s1 where s1.id=s0.id)";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-2));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(-2));
	        Assert.AreEqual(-2, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(1));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(2));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(3));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.AreEqual(3, listener.AssertOneGetNewAndReset()["id"]);
	    }

	    [Test]
	    public void testTwoExistsFiltered()
	    {
	        String stmtText = "select id from S0 as s0 where " +
	                "exists (select * from S1.win:length(1000) as s1 where s1.id=s0.id) " +
	                "and " +
	                "exists (select * from S2.win:length(1000) as s2 where s2.id=s0.id) "
	                ;

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S2(3));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(3));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.AreEqual(3, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(1));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(2));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(1));
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.IsFalse(listener.IsInvoked);
	    }

	    [Test]
	    public void testNotExists()
	    {
	        String stmtText = "select id from S0 where not exists (select * from S1.win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(1));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-2));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.IsFalse(listener.IsInvoked);
	    }
	}
} // End of namespace
