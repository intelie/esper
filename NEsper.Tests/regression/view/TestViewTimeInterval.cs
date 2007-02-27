using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestViewTimeInterval
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            // External clocking
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
        }

        [Test]
        public virtual void testTimeWindow()
        {
            tryTimeWindow("30000");
            tryTimeWindow("30E6 milliseconds");
            tryTimeWindow("30000 seconds");
            tryTimeWindow("500 minutes");
            tryTimeWindow("8.33333333333333333333 hours");
            tryTimeWindow("0.34722222222222222222222222222222 days");
            tryTimeWindow("0.1 hour 490 min 240 sec");
        }

        [Test]
        public virtual void testTimeBatchNoRefPoint()
        {
            // Set up a time window with a unique view attached
            EPStatement view = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName + ".win:time_batch(10 minutes)");
            testListener = new SupportUpdateListener();
            view.AddListener(testListener.Update);

            sendTimer(0);

            SendEvent();
            testListener.reset();

            sendTimerAssertNotInvoked(10 * 60 * 1000 - 1);
            sendTimerAssertInvoked(10 * 60 * 1000);
        }

        [Test]
        public virtual void testTimeBatchRefPoint()
        {
            // Set up a time window with a unique view attached
            EPStatement view = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName + ".win:time_batch(10 minutes, 10L)");
            testListener = new SupportUpdateListener();
            view.AddListener(testListener.Update);

            sendTimer(10);

            SendEvent();
            testListener.reset();

            sendTimerAssertNotInvoked(10 * 60 * 1000 - 1 + 10);
            sendTimerAssertInvoked(10 * 60 * 1000 + 10);
        }

        [Test]
        public virtual void testExternallyTimed()
        {
            // Set up a time window with a unique view attached
            EPStatement view = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName + ".win:ext_timed('longPrimitive', 10 minutes)");
            testListener = new SupportUpdateListener();
            view.AddListener(testListener.Update);

            sendExtTimeEvent(0);

            testListener.reset();
            sendExtTimeEvent(10 * 60 * 1000 - 1);
            Assert.IsNull(testListener.OldDataList[0]);

            testListener.reset();
            sendExtTimeEvent(10 * 60 * 1000 + 1);
            Assert.AreEqual(1, testListener.OldDataList[0].Length);
        }

        private void tryTimeWindow(String intervalSpec)
        {
            // Set up a time window with a unique view attached
            EPStatement view = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName + ".win:time(" + intervalSpec + ")");
            testListener = new SupportUpdateListener();
            view.AddListener(testListener.Update);

            sendTimer(0);

            SendEvent();
            testListener.reset();

            sendTimerAssertNotInvoked(29999 * 1000);
            sendTimerAssertInvoked(30000 * 1000);
        }

        private void sendTimerAssertNotInvoked(long timeInMSec)
        {
            sendTimer(timeInMSec);
            Assert.IsFalse(testListener.Invoked);
            testListener.reset();
        }

        private void sendTimerAssertInvoked(long timeInMSec)
        {
            sendTimer(timeInMSec);
            Assert.IsTrue(testListener.Invoked);
            testListener.reset();
        }

        private void sendTimer(long timeInMSec)
        {
            CurrentTimeEvent _event = new CurrentTimeEvent(timeInMSec);
            EPRuntime runtime = epService.EPRuntime;
            runtime.SendEvent(_event);
        }

        private void SendEvent()
        {
            SupportBean _event = new SupportBean();
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendExtTimeEvent(long longPrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.longPrimitive = longPrimitive;
            epService.EPRuntime.SendEvent(_event);
        }
    }
}