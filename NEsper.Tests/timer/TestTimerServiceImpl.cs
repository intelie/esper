using System;
using System.Threading;

using net.esper.support.timer;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.timer
{
    [TestFixture]
    public class TestTimerServiceImpl
    {
        private SupportTimerCallback callback;
        private TimerServiceImpl service;

        [SetUp]
        public virtual void setUp()
        {
            callback = new SupportTimerCallback();
            service = new MyTimerServiceImpl();
            service.Callback = callback.TimerCallback;
        }

        [Test]
        public virtual void testClocking()
        {
            int RESOLUTION = TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC;
            int tempCount;

            // Wait .55 sec
            Assert.IsTrue(callback.GetAndResetCount() == 0);
            service.StartInternalClock();
            sleep(RESOLUTION * 5 + RESOLUTION / 2);
            service.StopInternalClock(true);
            Assert.AreEqual(6, callback.GetAndResetCount());

            // Check if truely Stopped
            sleep(RESOLUTION);
            Assert.IsTrue(callback.GetAndResetCount() == 0);

            // Loop for some clock cycles
            service.StartInternalClock();
            sleep(RESOLUTION / 10);
            tempCount = callback.GetAndResetCount();
            Assert.IsTrue(tempCount == 1);
            sleep(TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC * 20);
            tempCount = callback.GetAndResetCount();
            log.Debug(".testClocking count=" + tempCount);
            Assert.IsTrue(tempCount >= 19);

            // Stop and check again
            service.StopInternalClock(true);
            sleep(RESOLUTION);
            Assert.IsTrue(callback.Count <= 1);

            // Try some Starts and Stops to see
            service.StartInternalClock();
            sleep(RESOLUTION / 5);
            service.StartInternalClock();
            sleep(RESOLUTION / 5);
            service.StartInternalClock();
            Assert.IsTrue(callback.GetAndResetCount() >= 1);

            sleep(RESOLUTION / 5);
            Assert.AreEqual(0, callback.Count);
            sleep(RESOLUTION);
            Assert.IsTrue(callback.Count >= 1);
            sleep(RESOLUTION);
            Assert.IsTrue(callback.Count >= 1);

            sleep(RESOLUTION * 5);
            Assert.IsTrue(callback.GetAndResetCount() >= 7);

            service.StopInternalClock(true);
            int generatedAux = callback.GetAndResetCount();
            service.StopInternalClock(true);
            sleep(RESOLUTION * 2);
            Assert.IsTrue(callback.Count == 0);
        }

        private void sleep(int msec)
        {
            try
            {
                Thread.Sleep(msec);
            }
            catch (ThreadInterruptedException e)
            {
                log.Fatal(e);
            }
        }

        class MyTimerServiceImpl : TimerServiceImpl
        {
            public MyTimerServiceImpl()
                : base()
            {
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
