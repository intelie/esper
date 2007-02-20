using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
    [TestFixture]
    public class TestRepeatRouteEvent
    {
        private EPServiceProvider epService;
        private EPStatement patternStmt;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            String viewExpr = "every tag=" + typeof(SupportBean).FullName;

            patternStmt = epService.EPAdministrator.createPattern(viewExpr);
        }

        /// <summary> Test route of an event within a listener.
        /// The Listener when it receives an event will generate a single new event
        /// that it routes back into the runtime, up to X number of times.
        /// </summary>
        [Test]
        public virtual void testRouteSingle()
        {
            SingleRouteUpdateListener listener = new SingleRouteUpdateListener(this);
            patternStmt.AddListener(listener);

            // Send first event that triggers the loop
            SendEvent(0);

            // Should have fired X times
            Assert.AreEqual(1000, listener.Count);
        }

        /// <summary> Test route of multiple events within a listener.
        /// The Listener when it receives an event will generate multiple new events
        /// that it routes back into the runtime, up to X number of times.
        /// </summary>
        [Test]
        public virtual void testRouteCascade()
        {
            CascadeRouteUpdateListener listener = new CascadeRouteUpdateListener(this);
            patternStmt.AddListener(listener);

            // Send first event that triggers the loop
            SendEvent(2); // the 2 translates to number of new events routed

            // Should have fired X times
            Assert.AreEqual(9, listener.CountReceived);
            Assert.AreEqual(8, listener.CountRouted);

            //  Num    Received         Routes      Num
            //  2             1           2         3
            //  3             2           6         4
            //  4             6             -
        }

        [Test]
        public virtual void testRouteTimer()
        {
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
            epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));

            // define time-based pattern and listener
            String viewExpr = "timer:at(*,*,*,*,*,*)";
            EPStatement atPatternStmt = epService.EPAdministrator.createPattern(viewExpr);
            SingleRouteUpdateListener timeListener = new SingleRouteUpdateListener(this);
            atPatternStmt.AddListener(timeListener);

            // register regular listener
            SingleRouteUpdateListener eventListener = new SingleRouteUpdateListener(this);
            patternStmt.AddListener(eventListener);

            Assert.AreEqual(0, timeListener.Count);
            Assert.AreEqual(0, eventListener.Count);

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(10000));

            Assert.AreEqual(1, timeListener.Count);
            Assert.AreEqual(1000, eventListener.Count);
        }

        private SupportBean SendEvent(int intValue)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intValue;
            epService.EPRuntime.SendEvent(_event);
            return _event;
        }

        private SupportBean routeEvent(int intValue)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intValue;
            epService.EPRuntime.Route(_event);
            return _event;
        }

        internal class SingleRouteUpdateListener : UpdateListener
        {
            public SingleRouteUpdateListener(TestRepeatRouteEvent enclosingInstance)
            {
                this.enclosingInstance = enclosingInstance;
            }

            private TestRepeatRouteEvent enclosingInstance;
            virtual public int Count
            {
                get { return count; }
            }

            public TestRepeatRouteEvent Enclosing_Instance
            {
                get { return enclosingInstance; }
            }

            private int count = 0;

            public virtual void Update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                count++;
                if (count < 1000)
                {
                    Enclosing_Instance.routeEvent(0);
                }
            }
        }

        internal class CascadeRouteUpdateListener : UpdateListener
        {
            public CascadeRouteUpdateListener(TestRepeatRouteEvent enclosingInstance)
            {
                this.enclosingInstance = enclosingInstance;
            }
            private TestRepeatRouteEvent enclosingInstance;
            virtual public int CountReceived
            {
                get
                {
                    return countReceived;
                }
            }

            virtual public int CountRouted
            {
                get
                {
                    return countRouted;
                }
            }

            public TestRepeatRouteEvent Enclosing_Instance
            {
                get
                {
                    return enclosingInstance;
                }
            }

            private int countReceived = 0;
            private int countRouted = 0;

            public virtual void Update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                countReceived++;
                SupportBean _event = (SupportBean)(newEvents[0]["tag"]);
                int numNewEvents = _event.intPrimitive;

                for (int i = 0; i < numNewEvents; i++)
                {
                    if (numNewEvents < 4)
                    {
                        Enclosing_Instance.routeEvent(numNewEvents + 1);
                        countRouted++;
                    }
                }
            }
        }
    }
}