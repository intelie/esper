using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{

    [TestFixture]
    public class TestPatternQueries
    {
        private EPServiceProvider epService;
        private SupportUpdateListener updateListener;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            updateListener = new SupportUpdateListener();
        }

        [Test]
        public void testWhere()
        {
            String stmtText = "select s0.id as idS0, s1.id as idS1 " + "from pattern [every s0=" + typeof(SupportBean_S0).FullName + " or every s1=" + typeof(SupportBean_S1).FullName + "] " + "where (s0.id is not null and s0.id < 100) or (s1.id is not null and s1.id >= 100)";
            EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
            statement.AddListener(updateListener);

            sendEventS0(1);
            assertEventIds(1, null);

            sendEventS0(101);
            Assert.IsFalse(updateListener.IsInvoked);

            sendEventS1(1);
            Assert.IsFalse(updateListener.IsInvoked);

            sendEventS1(100);
            assertEventIds(null, 100);
        }

        [Test]
        public void testAggregation()
        {
            String stmtText = "select sum(s0.id) as sumS0, sum(s1.id) as sumS1, sum(s0.id + s1.id) as sumS0S1 " + "from pattern [every s0=" + typeof(SupportBean_S0).FullName + " or every s1=" + typeof(SupportBean_S1).FullName + "]";
            EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
            statement.AddListener(updateListener);

            sendEventS0(1);
            assertEventSums(1, null, null);

            sendEventS1(2);
            assertEventSums(1, 2, null);

            sendEventS1(10);
            assertEventSums(1, 12, null);

            sendEventS0(20);
            assertEventSums(21, 12, null);
        }

        [Test]
        public void testFollowedByAndWindow()
        {
            String stmtText = "select a.id as idA, b.id as idB, " + "a.p00 as p00A, b.p00 as p00B from pattern [every a=" + typeof(SupportBean_S0).FullName + " -> every b=" + typeof(SupportBean_S0).FullName + "(p00=a.p00)].win:time(1)";
            EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);

            statement.AddListener(updateListener);
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
            epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));

            SendEvent(1, "e1a");
            Assert.IsFalse(updateListener.IsInvoked);
            SendEvent(2, "e1a");
            assertNewEvent(1, 2, "e1a");

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(500));
            SendEvent(10, "e2a");
            SendEvent(11, "e2b");
            SendEvent(12, "e2c");
            Assert.IsFalse(updateListener.IsInvoked);
            SendEvent(13, "e2b");
            assertNewEvent(11, 13, "e2b");

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(1000));
            assertOldEvent(1, 2, "e1a");

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(1500));
            assertOldEvent(11, 13, "e2b");
        }

        private void assertNewEvent(int idA, int idB, String p00)
        {
            EventBean _eventBean = updateListener.AssertOneGetNewAndReset();
            compareEvent(_eventBean, idA, idB, p00);
        }

        private void assertOldEvent(int idA, int idB, String p00)
        {
            EventBean _eventBean = updateListener.AssertOneGetOldAndReset();
            compareEvent(_eventBean, idA, idB, p00);
        }

        private void compareEvent(EventBean _eventBean, int idA, int idB, String p00)
        {
            Assert.AreEqual(idA, _eventBean["idA"]);
            Assert.AreEqual(idB, _eventBean["idB"]);
            Assert.AreEqual(p00, _eventBean["p00A"]);
            Assert.AreEqual(p00, _eventBean["p00B"]);
        }

        private void SendEvent(int id, String p00)
        {
            SupportBean_S0 _event = new SupportBean_S0(id, p00);
            epService.EPRuntime.SendEvent(_event);
        }

        private SupportBean_S0 sendEventS0(int id)
        {
            SupportBean_S0 _event = new SupportBean_S0(id);
            epService.EPRuntime.SendEvent(_event);
            return _event;
        }

        private SupportBean_S1 sendEventS1(int id)
        {
            SupportBean_S1 _event = new SupportBean_S1(id);
            epService.EPRuntime.SendEvent(_event);
            return _event;
        }

        private void assertEventIds(
            int? idS0,
            int? idS1)
        {
            EventBean _eventBean = updateListener.GetAndResetLastNewData()[0];
            Assert.AreEqual(idS0, _eventBean["idS0"]);
            Assert.AreEqual(idS1, _eventBean["idS1"]);
            updateListener.Reset();
        }

        private void assertEventSums(
            int? sumS0,
            int? sumS1,
            int? sumS0S1)
        {
            EventBean _eventBean = updateListener.GetAndResetLastNewData()[0];
            Assert.AreEqual(sumS0, _eventBean["sumS0"]);
            Assert.AreEqual(sumS1, _eventBean["sumS1"]);
            Assert.AreEqual(sumS0S1, _eventBean["sumS0S1"]);
            updateListener.Reset();
        }
    }
}
