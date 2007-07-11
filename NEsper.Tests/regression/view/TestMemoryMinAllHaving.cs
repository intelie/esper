using System;
using System.Diagnostics;
using System.Threading;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestMemoryMinAllHaving
    {
        private EPServiceProvider epService;
        private SupportUpdateListener listener;
        private Random random = new Random();

        [SetUp]
        public virtual void setUp()
        {
            listener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public void testMemory()
        {
            String statementText = "select price, min(price) as minPrice " + "from " + typeof(SupportMarketDataBean).FullName + ".win:time(30)" + "having price >= min(price) * (1.02)";

            EPStatement testView = epService.EPAdministrator.CreateEQL(statementText);
            testView.AddListener(listener);

            sendClockingInternal();

            //sendClockingExternal();
        }

        private void sendClockingInternal()
        {
            // Change to perform a long-running tests, each loop is 1 second
            int LOOP_COUNT = 2;
            int loopCount = 0;

            while (true)
            {
                log.Info("Sending batch " + loopCount);

                // send events
                long startTime = DateTimeHelper.CurrentTimeMillis;
                for (int i = 0; i < 5000; i++)
                {
                    double price = 50 + 49 * random.Next(100) / 100.0;
                    SendEvent(price);
                }
                long endTime = DateTimeHelper.CurrentTimeMillis;

                // sleep remainder of 1 second
                long delta = endTime - startTime;
                if (delta < 950)
                {
                    Thread.Sleep((int) (950 - delta));
                }

                listener.Reset();
                loopCount++;
                if (loopCount > LOOP_COUNT)
                {
                    break;
                }
            }
        }

        private void sendClockingExternal()
        {
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            Process thisProcess = Process.GetCurrentProcess();

            long memoryBefore = GC.GetTotalMemory(false);
            for (int i = 0; i < 1000; i++)
            {
                sendEvents(i);

                GC.Collect();

                long memoryAfter = GC.GetTotalMemory(false);

                log.Info("Memory before=" + memoryBefore + " after=" + memoryAfter + " delta=" + (memoryAfter - memoryBefore));
            }
        }

        private void sendEvents(int loop)
        {
            long startTime = loop * 31000;
            long endTime = loop * 31000 + 30500;
            log.Info("Sending batch " + loop + " startTime=" + startTime + " endTime=" + endTime);
            sendTimer(startTime);

            for (int i = 0; i < 100000; i++)
            {
                double price = 50 + 49 * random.Next(20) / 100.0;
                SendEvent(price);
            }

            sendTimer(endTime);
            listener.Reset();
        }

        private void SendEvent(double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean("DELL", price, -1L, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private void sendTimer(long time)
        {
            CurrentTimeEvent _event = new CurrentTimeEvent(time);
            EPRuntime runtime = epService.EPRuntime;
            runtime.SendEvent(_event);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
