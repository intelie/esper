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
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestSubselectFiltered
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
	        config.AddEventTypeAlias("S4", typeof(SupportBean_S4));
	        config.AddEventTypeAlias("S5", typeof(SupportBean_S5));
	        epService = EPServiceProviderManager.GetDefaultProvider(config);
	        epService.Initialize();
	        listener = new SupportUpdateListener();

	        // Use external clocking for the test, reduces logging
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestWhereConstant()
	    {
	        String stmtText = "select (select id from S1.win:length(1000) where p10='X') as ids1 from S0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        // test no _event, should return null
	        epService.EPRuntime.SendEvent(new SupportBean_S1(-1, "Y"));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids1"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(1, "X"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(2, "Y"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(3, "Z"));

	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["ids1"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1));
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["ids1"]);

	        // second X event
	        epService.EPRuntime.SendEvent(new SupportBean_S1(2, "X"));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids1"]);
	    }

	    [Test]
	    public void TestWherePrevious()
	    {
	        String stmtText = "select (select Prev(1, id) from S1.win:length(1000) where id=s0.id) as value from S0 as s0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(2));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["value"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(3));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void TestSelectWithWhereJoined()
	    {
	        String stmtText = "select (select id from S1.win:length(1000) where p10=s0.p00) as ids1 from S0 as s0";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids1"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(1, "X"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(2, "Y"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(3, "Z"));

	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids1"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(0, "X"));
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["ids1"]);
	        epService.EPRuntime.SendEvent(new SupportBean_S0(0, "Y"));
	        Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["ids1"]);
	        epService.EPRuntime.SendEvent(new SupportBean_S0(0, "Z"));
	        Assert.AreEqual(3, listener.AssertOneGetNewAndReset()["ids1"]);
	        epService.EPRuntime.SendEvent(new SupportBean_S0(0, "A"));
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids1"]);
	    }

	    [Test]
	    public void TestSelectWhereJoined2Streams()
	    {
	        String stmtText = "select (select id from S0.win:length(1000) where p00=s1.p10 and p00=s2.p20) as ids0 from S1 as s1, S2 as s2 where s1.id = s2.id";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(10, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(10, "s0_1"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(99, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(11, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(11, "s0_1"));
	        Assert.AreEqual(99, listener.AssertOneGetNewAndReset()["ids0"]);
	    }

	    [Test]
	    public void TestSelectWhereJoined3Streams()
	    {
	        String stmtText = "select (select id from S0.win:length(1000) where p00=s1.p10 and p00=s3.p30) as ids0 " +
	                            "from S1 as s1, S2 as s2, S3 as s3 where s1.id = s2.id and s2.id = s3.id";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(10, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(10, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(10, "s0_1"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(99, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(11, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(11, "xxx"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(11, "s0_1"));
	        Assert.AreEqual(99, listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(98, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(12, "s0_x"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(12, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(12, "s0_1"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(13, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(13, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(13, "s0_x"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(14, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(14, "xx"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(14, "s0_2"));
	        Assert.AreEqual(98, listener.AssertOneGetNewAndReset()["ids0"]);
	    }

	    [Test]
	    public void TestSelectWhereJoined3SceneTwo()
	    {
	        String stmtText = "select (select id from S0.win:length(1000) where p00=s1.p10 and p00=s3.p30 and p00=s2.p20) as ids0 " +
	                            "from S1 as s1, S2 as s2, S3 as s3 where s1.id = s2.id and s2.id = s3.id";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(10, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(10, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(10, "s0_1"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(99, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(11, "s0_1"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(11, "xxx"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(11, "s0_1"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(98, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(12, "s0_x"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(12, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(12, "s0_1"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(13, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(13, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(13, "s0_x"));
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(14, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S2(14, "s0_2"));
	        epService.EPRuntime.SendEvent(new SupportBean_S3(14, "s0_2"));
	        Assert.AreEqual(98, listener.AssertOneGetNewAndReset()["ids0"]);
	    }

	    [Test]
	    public void TestSelectWhereJoined4Coercion()
	    {
	        String stmtText = "select " +
	          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
	          "  where intBoxed=s1.longBoxed and " +
	                   "intBoxed=s2.doubleBoxed and " +
	                   "doubleBoxed=s3.intBoxed" +
	          ") as ids0 from " +
	          "MyEvent(string='A') as s1, " +
	          "MyEvent(string='B') as s2, " +
	          "MyEvent(string='C') as s3 " +
	          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
	        TrySelectWhereJoined4Coercion(stmtText);

	        stmtText = "select " +
	          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
	          "  where doubleBoxed=s3.intBoxed and " +
	                   "intBoxed=s2.doubleBoxed and " +
	                   "intBoxed=s1.longBoxed" +
	          ") as ids0 from " +
	          "MyEvent(string='A') as s1, " +
	          "MyEvent(string='B') as s2, " +
	          "MyEvent(string='C') as s3 " +
	          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
	        TrySelectWhereJoined4Coercion(stmtText);

	        stmtText = "select " +
	          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
	          "  where doubleBoxed=s3.intBoxed and " +
	                   "intBoxed=s1.longBoxed and " +
	                   "intBoxed=s2.doubleBoxed" +
	          ") as ids0 from " +
	          "MyEvent(string='A') as s1, " +
	          "MyEvent(string='B') as s2, " +
	          "MyEvent(string='C') as s3 " +
	          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
	        TrySelectWhereJoined4Coercion(stmtText);
	    }

	    [Test]
	    public void TestSelectWhereJoined4BackCoercion()
	    {
	        String stmtText = "select " +
	          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
	          "  where longBoxed=s1.intBoxed and " +
	                   "longBoxed=s2.doubleBoxed and " +
	                   "intBoxed=s3.longBoxed" +
	          ") as ids0 from " +
	          "MyEvent(string='A') as s1, " +
	          "MyEvent(string='B') as s2, " +
	          "MyEvent(string='C') as s3 " +
	          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
	        TrySelectWhereJoined4CoercionBack(stmtText);

	        stmtText = "select " +
	          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
	          "  where longBoxed=s2.doubleBoxed and " +
	                   "intBoxed=s3.longBoxed and " +
	                   "longBoxed=s1.intBoxed " +
	          ") as ids0 from " +
	          "MyEvent(string='A') as s1, " +
	          "MyEvent(string='B') as s2, " +
	          "MyEvent(string='C') as s3 " +
	          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
	        TrySelectWhereJoined4CoercionBack(stmtText);
	    }

	    private void TrySelectWhereJoined4CoercionBack(String stmtText)
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        SendBean("A", 1, 10, 200, 3000);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("B", 1, 10, 200, 3000);
	        SendBean("C", 1, 10, 200, 3000);
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -1, 11, 201, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("A", 2, 201, 0, 0);
	        SendBean("B", 2, 0, 0, 201);
	        SendBean("C", 2, 0, 11, 0);
	        Assert.AreEqual(-1, listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -2, 12, 202, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("A", 3, 202, 0, 0);
	        SendBean("B", 3, 0, 0, 202);
	        SendBean("C", 3, 0, -1, 0);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -3, 13, 203, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("A", 4, 203, 0, 0);
	        SendBean("B", 4, 0, 0, 203.0001);
	        SendBean("C", 4, 0, 13, 0);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -4, 14, 204, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("A", 5, 205, 0, 0);
	        SendBean("B", 5, 0, 0, 204);
	        SendBean("C", 5, 0, 14, 0);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids0"]);

	        stmt.Stop();
	    }

	    private void TrySelectWhereJoined4Coercion(String stmtText)
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        SendBean("A", 1, 10, 200, 3000);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("B", 1, 10, 200, 3000);
	        SendBean("C", 1, 10, 200, 3000);
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -2, 11, 0, 3001);
	        SendBean("A", 2, 0, 11, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("B", 2, 0, 0, 11);
	        SendBean("C", 2, 3001, 0, 0);
	        Assert.AreEqual(-2, listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -3, 12, 0, 3002);
	        SendBean("A", 3, 0, 12, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("B", 3, 0, 0, 12);
	        SendBean("C", 3, 3003, 0, 0);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -4, 11, 0, 3003);
	        SendBean("A", 4, 0, 0, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("B", 4, 0, 0, 11);
	        SendBean("C", 4, 3003, 0, 0);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids0"]);

	        SendBean("S", -5, 14, 0, 3004);
	        SendBean("A", 5, 0, 14, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
	        SendBean("B", 5, 0, 0, 11);
	        SendBean("C", 5, 3004, 0, 0);
	        Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["ids0"]);

	        stmt.Stop();
	    }

	    [Test]
	    public void TestSelectWithWhere2Subqery()
	    {
	        String stmtText = "select id from S0 as s0 where " +
	                        " id = (select id from S1.win:length(1000) where s0.id = id) or id = (select id from S2.win:length(1000) where s0.id = id)";

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(0));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(1));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(1));
	        Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S2(2));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2));
	        Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["id"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S1(3));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(3));
	        Assert.AreEqual(3, listener.AssertOneGetNewAndReset()["id"]);
	    }

	    [Test]
	    public void TestJoinFilteredOne()
	    {
	        String stmtText = "select s0.id as s0id, s1.id as s1id, " +
	                          "(select p20 from S2.win:length(1000) where id=s0.id) as s2p20, " +
	                          "(select Prior(1, p20) from S2.win:length(1000) where id=s0.id) as s2p20Prior, " +
	                          "(select Prev(1, p20) from S2.win:length(10) where id=s0.id) as s2p20Prev " +
	                          "from S0 as s0, S1 as s1 " +
	                          "where s0.id = s1.id and p00||p10 = (select p20 from S2.win:length(1000) where id=s0.id)";
	        TryJoinFiltered(stmtText);
	    }

	    [Test]
	    public void TestJoinFilteredTwo()
	    {
	        String stmtText = "select s0.id as s0id, s1.id as s1id, " +
	                          "(select p20 from S2.win:length(1000) where id=s0.id) as s2p20, " +
	                          "(select Prior(1, p20) from S2.win:length(1000) where id=s0.id) as s2p20Prior, " +
	                          "(select Prev(1, p20) from S2.win:length(10) where id=s0.id) as s2p20Prev " +
	                          "from S0 as s0, S1 as s1 " +
	                          "where s0.id = s1.id and (select s0.p00||s1.p10 = p20 from S2.win:length(1000) where id=s0.id)";
	        TryJoinFiltered(stmtText);
	    }

	    public void TryJoinFiltered(String stmtText)
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(0, "X"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(0, "Y"));
	        Assert.IsFalse(listener.IsInvoked);

	        epService.EPRuntime.SendEvent(new SupportBean_S2(1, "ab"));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "a"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(1, "b"));
	        EventBean _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual(1, _event["s0id"]);
	        Assert.AreEqual(1, _event["s1id"]);
	        Assert.AreEqual("ab", _event["s2p20"]);
	        Assert.AreEqual(null, _event["s2p20Prior"]);
	        Assert.AreEqual(null, _event["s2p20Prev"]);

	        epService.EPRuntime.SendEvent(new SupportBean_S2(2, "qx"));
	        epService.EPRuntime.SendEvent(new SupportBean_S0(2, "q"));
	        epService.EPRuntime.SendEvent(new SupportBean_S1(2, "x"));
	        _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual(2, _event["s0id"]);
	        Assert.AreEqual(2, _event["s1id"]);
	        Assert.AreEqual("qx", _event["s2p20"]);
	        Assert.AreEqual("ab", _event["s2p20Prior"]);
	        Assert.AreEqual("ab", _event["s2p20Prev"]);
	    }

	    private void SendBean(String _string, int intPrimitive, int intBoxed, long longBoxed, double doubleBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString(_string);
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetLongBoxed(longBoxed);
	        bean.SetDoubleBoxed(doubleBoxed);
	        epService.EPRuntime.SendEvent(bean);
	    }
	}
} // End of namespace
