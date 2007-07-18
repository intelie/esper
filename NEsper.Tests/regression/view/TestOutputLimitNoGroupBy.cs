///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestOutputLimitNoGroupBy
	{
	    private readonly static String JOIN_KEY = "KEY";

		private EPServiceProvider epService;


	    [SetUp]
	    public void SetUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void testSimpleNoJoinAll()
		{
		    String viewExpr = "select longBoxed " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "output all every 2 events";

            RunAssertAll(CreateStmtAndListenerNoJoin(viewExpr));

		    viewExpr = "select longBoxed " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "output every 2 events";

            RunAssertAll(CreateStmtAndListenerNoJoin(viewExpr));

		    viewExpr = "select * " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "output every 2 events";

            RunAssertAll(CreateStmtAndListenerNoJoin(viewExpr));
		}
		[Test]
		public void testAggregateAllNoJoinAll()
		{
		    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "having Sum(longBoxed) > 0 " +
		    "output all every 2 events";

            RunAssertAllSum(CreateStmtAndListenerNoJoin(viewExpr));

		    viewExpr = "select longBoxed, sum(longBoxed) as result " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "output every 2 events";

		    RunAssertAllSum(CreateStmtAndListenerNoJoin(viewExpr));
		}

		[Test]
		public void testAggregateAllNoJoinLast()
		{
		    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "having Sum(longBoxed) > 0 " +
		    "output last every 2 events";

            RunAssertLastSum(CreateStmtAndListenerNoJoin(viewExpr));

		    viewExpr = "select longBoxed, sum(longBoxed) as result " +
		    "from " + typeof(SupportBean).FullName + ".win:length(3) " +
		    "output last every 2 events";

            RunAssertLastSum(CreateStmtAndListenerNoJoin(viewExpr));
		}

		[Test]
		public void testSimpleNoJoinLast()
	    {
	        String viewExpr = "select longBoxed " +
	        "from " + typeof(SupportBean).FullName + ".win:length(3) " +
	        "output last every 2 events";

            RunAssertLast(CreateStmtAndListenerNoJoin(viewExpr));

	        viewExpr = "select * " +
	        "from " + typeof(SupportBean).FullName + ".win:length(3) " +
	        "output last every 2 events";

            RunAssertLast(CreateStmtAndListenerNoJoin(viewExpr));
	    }

	    [Test]
	    public void testSimpleJoinAll()
		{
		    String viewExpr = "select longBoxed  " +
		    "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " +
		    typeof(SupportBean).FullName + ".win:length(3) as two " +
		    "output all every 2 events";

            RunAssertAll(CreateStmtAndListenerNoJoin(viewExpr));
		}

	    private SupportUpdateListener CreateStmtAndListenerNoJoin(String viewExpr) {
			epService.Initialize();
			SupportUpdateListener updateListener = new SupportUpdateListener();
			EPStatement view = epService.EPAdministrator.CreateEQL(viewExpr);
            view.AddListener(updateListener);

		    return updateListener;
		}

		[Test]
		public void testAggregateAllJoinAll()
		{
		    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
		    "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " +
		    typeof(SupportBean).FullName + ".win:length(3) as two " +
		    "having Sum(longBoxed) > 0 " +
		    "output all every 2 events";

            RunAssertAllSum(CreateStmtAndListenerNoJoin(viewExpr));

		    viewExpr = "select longBoxed, sum(longBoxed) as result " +
		    "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " +
		    typeof(SupportBean).FullName + ".win:length(3) as two " +
		    "output every 2 events";

            RunAssertAllSum(CreateStmtAndListenerNoJoin(viewExpr));
		}

		[Test]
		public void testAggregateAllJoinLast()
	    {
	        String viewExpr = "select longBoxed, sum(longBoxed) as result " +
	        "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " +
	        typeof(SupportBean).FullName + ".win:length(3) as two " +
	        "having Sum(longBoxed) > 0 " +
	        "output last every 2 events";

            RunAssertLastSum(CreateStmtAndListenerNoJoin(viewExpr));

	        viewExpr = "select longBoxed, sum(longBoxed) as result " +
	        "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " +
	        typeof(SupportBean).FullName + ".win:length(3) as two " +
	        "output last every 2 events";

            RunAssertLastSum(CreateStmtAndListenerNoJoin(viewExpr));
	    }

		private void RunAssertAll(SupportUpdateListener updateListener)
		{
			// send an event
		    SendEvent(1);

		    // check no update
		    Assert.IsFalse(updateListener.GetAndClearIsInvoked());

		    // send another event
		    SendEvent(2);

		    // check update, all events present
		    Assert.IsTrue(updateListener.GetAndClearIsInvoked());
		    Assert.AreEqual(2, updateListener.LastNewData.Length);
		    Assert.AreEqual(1L, updateListener.LastNewData[0]["longBoxed"]);
		    Assert.AreEqual(2L, updateListener.LastNewData[1]["longBoxed"]);
		    Assert.IsNull(updateListener.LastOldData);
		}

		private void RunAssertAllSum(SupportUpdateListener updateListener)
		{
			// send an event
		    SendEvent(1);

		    // check no update
		    Assert.IsFalse(updateListener.GetAndClearIsInvoked());

		    // send another event
		    SendEvent(2);

		    // check update, all events present
		    Assert.IsTrue(updateListener.GetAndClearIsInvoked());
		    Assert.AreEqual(2, updateListener.LastNewData.Length);
		    Assert.AreEqual(1L, updateListener.LastNewData[0]["longBoxed"]);
		    Assert.AreEqual(3L, updateListener.LastNewData[0]["result"]);
		    Assert.AreEqual(2L, updateListener.LastNewData[1]["longBoxed"]);
		    Assert.AreEqual(3L, updateListener.LastNewData[1]["result"]);
		    Assert.IsNull(updateListener.LastOldData);
		}

		private void RunAssertLastSum(SupportUpdateListener updateListener)
		{
			// send an event
		    SendEvent(1);

		    // check no update
		    Assert.IsFalse(updateListener.GetAndClearIsInvoked());

		    // send another event
		    SendEvent(2);

		    // check update, all events present
		    Assert.IsTrue(updateListener.GetAndClearIsInvoked());
		    Assert.AreEqual(1, updateListener.LastNewData.Length);
		    Assert.AreEqual(2L, updateListener.LastNewData[0]["longBoxed"]);
		    Assert.AreEqual(3L, updateListener.LastNewData[0]["result"]);
		    Assert.IsNull(updateListener.LastOldData);
		}

	    private void SendEvent(long longBoxed, int intBoxed, short shortBoxed)
		{
		    SupportBean bean = new SupportBean();
		    bean.SetString(JOIN_KEY);
		    bean.SetLongBoxed(longBoxed);
		    bean.SetIntBoxed(intBoxed);
		    bean.SetShortBoxed(shortBoxed);
		    epService.EPRuntime.SendEvent(bean);
		}

		private void SendEvent(long longBoxed)
		{
		    SendEvent(longBoxed, 0, (short)0);
		}

		[Test]
		public void testSimpleJoinLast()
		{
		    String viewExpr = "select longBoxed " +
		    "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " +
		    typeof(SupportBean).FullName + ".win:length(3) as two " +
		    "output last every 2 events";

			RunAssertLast(CreateStmtAndListenerJoin(viewExpr));
		}

		private SupportUpdateListener CreateStmtAndListenerJoin(String viewExpr) {
			epService.Initialize();

			SupportUpdateListener updateListener = new SupportUpdateListener();
			EPStatement view = epService.EPAdministrator.CreateEQL(viewExpr);
            view.AddListener(updateListener);

		    epService.EPRuntime.SendEvent(new SupportBeanString(JOIN_KEY));

		    return updateListener;
		}

		private void RunAssertLast(SupportUpdateListener updateListener)
		{
			// send an event
		    SendEvent(1);

		    // check no update
		    Assert.IsFalse(updateListener.GetAndClearIsInvoked());

		    // send another event
		    SendEvent(2);

		    // check update, only the last event present
		    Assert.IsTrue(updateListener.GetAndClearIsInvoked());
		    Assert.AreEqual(1, updateListener.LastNewData.Length);
		    Assert.AreEqual(2L, updateListener.LastNewData[0]["longBoxed"]);
		    Assert.IsNull(updateListener.LastOldData);
		}

	}
} // End of namespace
