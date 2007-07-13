using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Framework;

namespace net.esper.regression.eql
{
    [TestFixture]
    public class Test2StreamOuterJoin
    {
        private EPServiceProvider epService;
        private SupportUpdateListener updateListener;

        private SupportBean_S0[] eventsS0 = new SupportBean_S0[15];
        private SupportBean_S1[] eventsS1 = new SupportBean_S1[15];

        [SetUp]
        public virtual void setUp()
        {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
            updateListener = new SupportUpdateListener();

            int count = 100;
            for (int i = 0; i < eventsS0.Length; i++)
            {
                eventsS0[i] = new SupportBean_S0(count++, Convert.ToString(i));
            }
            count = 200;
            for (int i = 0; i < eventsS1.Length; i++)
            {
                eventsS1[i] = new SupportBean_S1(count++, Convert.ToString(i));
            }
        }

        [Test]
        public virtual void testFullOuterJoin()
        {
            setupStatement("full");

            // Send S0[0]
            SendEvent(eventsS0[0]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), 100, "0", null, null);

            // Send S1[1]
            SendEvent(eventsS1[1]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), null, null, 201, "1");

            // Send S1[2] and S0[2]
            SendEvent(eventsS1[2]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), null, null, 202, "2");
            SendEvent(eventsS0[2]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), 102, "2", 202, "2");

            // Send S0[3] and S1[3]
            SendEvent(eventsS0[3]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), 103, "3", null, null);
            SendEvent(eventsS1[3]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), 103, "3", 203, "3");

            // Send S0[4], pushes S0[0] out of window
            SendEvent(eventsS0[4]);
            EventBean oldEvent = updateListener.LastOldData[0];
            EventBean newEvent = updateListener.LastNewData[0];
            compareEvent(oldEvent, 100, "0", null, null);
            compareEvent(newEvent, 104, "4", null, null);
            updateListener.Reset();

            // Send S1[4]
            SendEvent(eventsS1[4]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), 104, "4", 204, "4");

            // Send S1[5]
            SendEvent(eventsS1[5]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), null, null, 205, "5");

            // Send S1[6], pushes S1[1] out of window
            SendEvent(eventsS1[5]);
            oldEvent = updateListener.LastOldData[0];
            newEvent = updateListener.LastNewData[0];
            compareEvent(oldEvent, null, null, 201, "1");
            compareEvent(newEvent, null, null, 205, "5");
        }

        [Test]
        public virtual void testRightOuterJoin()
        {
            setupStatement("right");

            // Send S0 events, no events expected
            SendEvent(eventsS0[0]);
            SendEvent(eventsS0[1]);
            Assert.IsFalse(updateListener.IsInvoked);

            // Send S1[2]
            SendEvent(eventsS1[2]);
            EventBean _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, null, null, 202, "2");

            // Send S0[2] events, joined event expected
            SendEvent(eventsS0[2]);
            _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, 102, "2", 202, "2");

            // Send S1[3]
            SendEvent(eventsS1[3]);
            _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, null, null, 203, "3");

            // Send some more S0 events
            SendEvent(eventsS0[3]);
            _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, 103, "3", 203, "3");

            // Send some more S0 events
            SendEvent(eventsS0[4]);
            Assert.IsFalse(updateListener.IsInvoked);

            // Push S0[2] out of the window
            SendEvent(eventsS0[5]);
            _event = updateListener.AssertOneGetOldAndReset();
            compareEvent(_event, 102, "2", 202, "2");

            // Some more S1 events
            SendEvent(eventsS1[6]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), null, null, 206, "6");
            SendEvent(eventsS1[7]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), null, null, 207, "7");
            SendEvent(eventsS1[8]);
            compareEvent(updateListener.AssertOneGetNewAndReset(), null, null, 208, "8");

            // Push S1[2] out of the window
            SendEvent(eventsS1[9]);
            EventBean oldEvent = updateListener.LastOldData[0];
            EventBean newEvent = updateListener.LastNewData[0];
            compareEvent(oldEvent, null, null, 202, "2");
            compareEvent(newEvent, null, null, 209, "9");
        }

        [Test]
        public virtual void testLeftOuterJoin()
        {
            setupStatement("left");

            // Send S1 events, no events expected
            SendEvent(eventsS1[0]);
            SendEvent(eventsS1[1]);
            SendEvent(eventsS1[3]);
            Assert.IsNull(updateListener.LastNewData); // No events expected

            // Send S0 _event, expect event back from outer join
            SendEvent(eventsS0[2]);
            EventBean _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, 102, "2", null, null);

            // Send S1 event matching S0, expect event back
            SendEvent(eventsS1[2]);
            _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, 102, "2", 202, "2");

            // Send some more unmatched events
            SendEvent(eventsS1[4]);
            SendEvent(eventsS1[5]);
            SendEvent(eventsS1[6]);
            Assert.IsNull(updateListener.LastNewData); // No events expected

            // Send _event, expect a join result
            SendEvent(eventsS0[5]);
            _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, 105, "5", 205, "5");

            // Let S1[2] go out of the window (length 5), expected old join event
            SendEvent(eventsS1[7]);
            SendEvent(eventsS1[8]);
            _event = updateListener.AssertOneGetOldAndReset();
            compareEvent(_event, 102, "2", 202, "2");

            // S0[9] should generate an outer join event
            SendEvent(eventsS0[9]);
            _event = updateListener.AssertOneGetNewAndReset();
            compareEvent(_event, 109, "9", null, null);

            // S0[2] Should leave the window (length 3), should get OLD and NEW event
            SendEvent(eventsS0[10]);
            EventBean oldEvent = updateListener.LastOldData[0];
            EventBean newEvent = updateListener.LastNewData[0];
            compareEvent(oldEvent, 102, "2", null, null); // S1[2] has left the window already
            compareEvent(newEvent, 110, "10", null, null);
        }

        [Test]
        public virtual void testEventType()
        {
            EPStatement outerJoinView = setupStatement("left");

            Assert.AreEqual(typeof(String), outerJoinView.EventType.GetPropertyType("s0.p00"));
            Assert.AreEqual(typeof(int?), outerJoinView.EventType.GetPropertyType("s0.id"));
            Assert.AreEqual(typeof(String), outerJoinView.EventType.GetPropertyType("s1.p10"));
            Assert.AreEqual(typeof(int?), outerJoinView.EventType.GetPropertyType("s1.id"));
            Assert.AreEqual(4, outerJoinView.EventType.PropertyNames.Count);
        }

        private void compareEvent(
            EventBean receivedEvent,
            int? idS0, String p00,
            int? idS1, String p10)
        {
            Assert.AreEqual(idS0, receivedEvent["s0.id"]);
            Assert.AreEqual(idS1, receivedEvent["s1.id"]);
            Assert.AreEqual(p00, receivedEvent["s0.p00"]);
            Assert.AreEqual(p10, receivedEvent["s1.p10"]);
        }

        private EPStatement setupStatement(String outerJoinType)
        {
            String joinStatement =
                "select s0.id, s0.p00, s1.id, s1.p10 from " + typeof(SupportBean_S0).FullName +
                ".win:length(3) as s0 " + outerJoinType +
                " outer join " + typeof(SupportBean_S1).FullName +
                ".win:length(5) as s1" +
                " on s0.p00 = s1.p10";

            EPStatement outerJoinView = epService.EPAdministrator.CreateEQL(joinStatement);
            outerJoinView.AddListener(updateListener);

            return outerJoinView;
        }

        private void SendEvent(Object _event)
        {
            epService.EPRuntime.SendEvent(_event);
        }
    }
}