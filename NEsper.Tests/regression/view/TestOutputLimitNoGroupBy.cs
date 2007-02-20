using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestOutputLimitNoGroupBy
    {
        private const String JOIN_KEY = "KEY";

        private EPServiceProvider epService;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testSimpleNoJoinAll()
        {
            String viewExpr = "select longBoxed " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output all every 2 events";

            runAssertAll(createStmtAndListenerNoJoin(viewExpr));

            viewExpr = "select longBoxed " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output every 2 events";

            runAssertAll(createStmtAndListenerNoJoin(viewExpr));

            viewExpr = "select * " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output every 2 events";

            runAssertAll(createStmtAndListenerNoJoin(viewExpr));
        }
        [Test]
        public virtual void testAggregateAllNoJoinAll()
        {
            String viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "having sum(longBoxed) > 0 " + "output all every 2 events";

            runAssertAllSum(createStmtAndListenerNoJoin(viewExpr));

            viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output every 2 events";

            runAssertAllSum(createStmtAndListenerNoJoin(viewExpr));
        }

        [Test]
        public virtual void testAggregateAllNoJoinLast()
        {
            String viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "having sum(longBoxed) > 0 " + "output last every 2 events";

            runAssertLastSum(createStmtAndListenerNoJoin(viewExpr));

            viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output last every 2 events";

            runAssertLastSum(createStmtAndListenerNoJoin(viewExpr));
        }

        [Test]
        public virtual void testSimpleNoJoinLast()
        {
            String viewExpr = "select longBoxed " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output last every 2 events";

            runAssertLast(createStmtAndListenerNoJoin(viewExpr));

            viewExpr = "select * " + "from " + typeof(SupportBean).FullName + ".win:length(3) " + "output last every 2 events";

            runAssertLast(createStmtAndListenerNoJoin(viewExpr));
        }

        [Test]
        public virtual void testSimpleJoinAll()
        {
            String viewExpr = "select longBoxed  " + "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " + typeof(SupportBean).FullName + ".win:length(3) as two " + "output all every 2 events";

            runAssertAll(createStmtAndListenerJoin(viewExpr));
        }

        private SupportUpdateListener createStmtAndListenerNoJoin(String viewExpr)
        {
            epService.Initialize();
            SupportUpdateListener updateListener = new SupportUpdateListener();
            EPStatement view = epService.EPAdministrator.createEQL(viewExpr);
            view.AddListener(updateListener);

            return updateListener;
        }

        [Test]
        public virtual void testAggregateAllJoinAll()
        {
            String viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " + typeof(SupportBean).FullName + ".win:length(3) as two " + "having sum(longBoxed) > 0 " + "output all every 2 events";

            runAssertAllSum(createStmtAndListenerJoin(viewExpr));

            viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " + typeof(SupportBean).FullName + ".win:length(3) as two " + "output every 2 events";

            runAssertAllSum(createStmtAndListenerJoin(viewExpr));
        }

        [Test]
        public virtual void testAggregateAllJoinLast()
        {
            String viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " + typeof(SupportBean).FullName + ".win:length(3) as two " + "having sum(longBoxed) > 0 " + "output last every 2 events";

            runAssertLastSum(createStmtAndListenerJoin(viewExpr));

            viewExpr = "select longBoxed, sum(longBoxed) as result " + "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " + typeof(SupportBean).FullName + ".win:length(3) as two " + "output last every 2 events";

            runAssertLastSum(createStmtAndListenerJoin(viewExpr));
        }

        private void runAssertAll(SupportUpdateListener updateListener)
        {
            // send an event
            SendEvent(1);

            // check no update
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // send another event
            SendEvent(2);

            // check update, all events present
            Assert.IsTrue(updateListener.getAndClearIsInvoked());
            Assert.AreEqual(2, updateListener.LastNewData.Length);
            Assert.AreEqual(1L, updateListener.LastNewData[0]["longBoxed"]);
            Assert.AreEqual(2L, updateListener.LastNewData[1]["longBoxed"]);
            Assert.IsNull(updateListener.LastOldData);
        }

        private void runAssertAllSum(SupportUpdateListener updateListener)
        {
            // send an event
            SendEvent(1);

            // check no update
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // send another event
            SendEvent(2);

            // check update, all events present
            Assert.IsTrue(updateListener.getAndClearIsInvoked());
            Assert.AreEqual(2, updateListener.LastNewData.Length);
            Assert.AreEqual(1L, updateListener.LastNewData[0]["longBoxed"]);
            Assert.AreEqual(3L, updateListener.LastNewData[0]["result"]);
            Assert.AreEqual(2L, updateListener.LastNewData[1]["longBoxed"]);
            Assert.AreEqual(3L, updateListener.LastNewData[1]["result"]);
            Assert.IsNull(updateListener.LastOldData);
        }

        private void runAssertLastSum(SupportUpdateListener updateListener)
        {
            // send an event
            SendEvent(1);

            // check no update
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // send another event
            SendEvent(2);

            // check update, all events present
            Assert.IsTrue(updateListener.getAndClearIsInvoked());
            Assert.AreEqual(1, updateListener.LastNewData.Length);
            Assert.AreEqual(2L, updateListener.LastNewData[0]["longBoxed"]);
            Assert.AreEqual(3L, updateListener.LastNewData[0]["result"]);
            Assert.IsNull(updateListener.LastOldData);
        }

        private void SendEvent(long longBoxed, int intBoxed, short shortBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.str = JOIN_KEY;
            bean.longBoxed = longBoxed;
            bean.intBoxed = intBoxed;
            bean.shortBoxed = shortBoxed;
            epService.EPRuntime.SendEvent(bean);
        }

        private void SendEvent(long longBoxed)
        {
            SendEvent(longBoxed, 0, (short)0);
        }

        [Test]
        public virtual void testSimpleJoinLast()
        {
            String viewExpr = "select longBoxed " + "from " + typeof(SupportBeanString).FullName + ".win:length(3) as one, " + typeof(SupportBean).FullName + ".win:length(3) as two " + "output last every 2 events";

            runAssertLast(createStmtAndListenerJoin(viewExpr));
        }

        private SupportUpdateListener createStmtAndListenerJoin(String viewExpr)
        {
            epService.Initialize();

            SupportUpdateListener updateListener = new SupportUpdateListener();
            EPStatement view = epService.EPAdministrator.createEQL(viewExpr);
            view.AddListener(updateListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(JOIN_KEY));

            return updateListener;
        }

        private void runAssertLast(SupportUpdateListener updateListener)
        {
            // send an event
            SendEvent(1);

            // check no update
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // send another event
            SendEvent(2);

            // check update, only the last event present
            Assert.IsTrue(updateListener.getAndClearIsInvoked());
            Assert.AreEqual(1, updateListener.LastNewData.Length);
            Assert.AreEqual(2L, updateListener.LastNewData[0]["longBoxed"]);
            Assert.IsNull(updateListener.LastOldData);
        }
    }
}