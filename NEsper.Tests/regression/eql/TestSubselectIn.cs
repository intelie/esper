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
	public class TestSubselectIn
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
	        epService = EPServiceProviderManager.GetDefaultProvider(config);
	        epService.Initialize();
	        listener = new SupportUpdateListener();

	        // Use external clocking for the test, reduces logging
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestInSelect()
	    {
	        String stmtText = "select id in (select id from S1.win:length(1000)) as value from S0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(-1));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(5));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(4));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(5));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void TestInSelectWhere()
	    {
	        String stmtText = "select id in (select id from S1.win:length(1000) where id > 0) as value from S0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(-1));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(5));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(4));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(5));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void TestInSelectWhereExpressions()
	    {
	        String stmtText = "select 3*id in (select 2*id from S1.win:length(1000)) as value from S0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(-1));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(6));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(4));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void TestInNullable()
	    {
	        String stmtText = "select id from S0 as s0 where p00 in (select p10 from S1.win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "a"));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2, null));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1, "A"));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3, null));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(4, "A"));
	        Assert.AreEqual(4, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-2, null));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(5, null));
	        Assert.AreEqual(5, listener.AssertOneGetNewAndReset()["id"]);
	    }

	    [Test]
	    public void TestInNullableCoercion()
	    {
	        String stmtText = "select longBoxed from " + typeof(SupportBean).FullName + "(string='A') as s0 " +
	                          "where longBoxed in " +
	                          "(select intBoxed from " + typeof(SupportBean).FullName + "(string='B').win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        SendBean("A", 0, 0L);
	        SendBean("A", null, null);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("B", null, null);

	        SendBean("A", 0, 0L);
	        Assert.IsFalse(listener.IsInvoked);
	        SendBean("A", null, null);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["longBoxed"]);

	        SendBean("B", 99, null);

	        SendBean("A", null, null);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["longBoxed"]);
	        SendBean("A", null, 99l);
	        Assert.AreEqual(99L, listener.AssertOneGetNewAndReset()["longBoxed"]);

	        SendBean("B", 98, null);

	        SendBean("A", null, 98l);
	        Assert.AreEqual(98L, listener.AssertOneGetNewAndReset()["longBoxed"]);
	    }

	    [Test]
	    public void TestInNullRow()
	    {
	        String stmtText = "select intBoxed from " + typeof(SupportBean).FullName + "(string='A') as s0 " +
	                          "where intBoxed in " +
	                          "(select longBoxed from " + typeof(SupportBean).FullName + "(string='B').win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        SendBean("B", 1, 1l);

	        SendBean("A", null, null);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("A", 1, 1l);
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["intBoxed"]);

	        SendBean("B", null, null);

	        SendBean("A", null, null);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["intBoxed"]);

	        SendBean("A", 1, 1l);
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["intBoxed"]);
	    }

	    [Test]
	    public void TestNotInNullRow()
	    {
	        String stmtText = "select intBoxed from " + typeof(SupportBean).FullName + "(string='A') as s0 " +
	                          "where intBoxed not in " +
	                          "(select longBoxed from " + typeof(SupportBean).FullName + "(string='B').win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        SendBean("B", 1, 1l);

	        SendBean("A", null, null);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["intBoxed"]);

	        SendBean("A", 1, 1l);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("B", null, null);

	        SendBean("A", null, null);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("A", 1, 1l);
	        Assert.IsFalse(listener.IsInvoked);
	    }

	    [Test]
	    public void TestNotInSelect()
	    {
	        String stmtText = "select not id in (select id from S1.win:length(1000)) as value from S0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(-1));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(5));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(4));
	        Assert.AreEqual(true, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(5));
	        Assert.AreEqual(false, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void TestNotInNullableCoercion()
	    {
	        String stmtText = "select longBoxed from " + typeof(SupportBean).FullName + "(string='A') as s0 " +
	                          "where longBoxed not in " +
	                          "(select intBoxed from " + typeof(SupportBean).FullName + "(string='B').win:length(1000))";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        SendBean("A", 0, 0L);
	        Assert.AreEqual(0L, listener.AssertOneGetNewAndReset()["longBoxed"]);

	        SendBean("A", null, null);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["longBoxed"]);

	        SendBean("B", null, null);

	        SendBean("A", 1, 1L);
	        Assert.AreEqual(1L, listener.AssertOneGetNewAndReset()["longBoxed"]);
	        SendBean("A", null, null);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("B", 99, null);

	        SendBean("A", null, null);
	        Assert.IsFalse(listener.IsInvoked);
	        SendBean("A", null, 99l);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("B", 98, null);

	        SendBean("A", null, 98l);
	        Assert.IsFalse(listener.IsInvoked);

	        SendBean("A", null, 97l);
	        Assert.AreEqual(97L, listener.AssertOneGetNewAndReset()["longBoxed"]);
	    }

	    private void SendBean(String _string, int? intBoxed, long? longBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(_string);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetLongBoxed(longBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }
	}
} // End of namespace
