using System;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
    [TestFixture]
    public class TestPerf2StreamSimpleJoin
    {
        private EPServiceProvider epService;
        private EPStatement joinView;
        private SupportUpdateListener updateListener;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
            updateListener = new SupportUpdateListener();

            String joinStatement = "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length(1000000)," + typeof(SupportBean).FullName + ".win:length(1000000)" + " where symbol=string";

            joinView = epService.EPAdministrator.createEQL(joinStatement);
            joinView.AddListener(updateListener);
        }

        [Test]
        public virtual void testPerformanceJoinNoResults()
        {
            String methodName = ".testPerformanceJoinNoResults";

            // Send events for each stream
            log.Info(methodName + " Preloading events");
            long startTime = DateTimeHelper.CurrentTimeMillis;
            for (int i = 0; i < 1000; i++)
            {
                SendEvent(makeMarketEvent("IBM_" + i));
                SendEvent(makeSupportEvent("CSCO_" + i));
            }
            log.Info(methodName + " Done preloading");

            long endTime = DateTimeHelper.CurrentTimeMillis;
            log.Info(methodName + " delta=" + (endTime - startTime));

            // Stay below 500 ms
            Assert.IsTrue((endTime - startTime) < 500);
        }

        [Test]
        public virtual void testJoinPerformanceStreamA()
        {
            String methodName = ".testJoinPerformanceStreamA";

            // Send 100k events
            log.Info(methodName + " Preloading events");
            for (int i = 0; i < 50000; i++)
            {
                SendEvent(makeMarketEvent("IBM_" + i));
            }
            log.Info(methodName + " Done preloading");

            long startTime = DateTimeHelper.CurrentTimeMillis;
            SendEvent(makeSupportEvent("IBM_10"));
            long endTime = DateTimeHelper.CurrentTimeMillis;
            log.Info(methodName + " delta=" + (endTime - startTime));

            Assert.AreEqual(1, updateListener.LastNewData.Length);
            // Stay below 50 ms
            Assert.IsTrue((endTime - startTime) < 500);
        }

        [Test]
        public virtual void testJoinPerformanceStreamB()
        {
            String methodName = ".testJoinPerformanceStreamB";

            // Send 100k events
            log.Info(methodName + " Preloading events");
            for (int i = 0; i < 50000; i++)
            {
                SendEvent(makeSupportEvent("IBM_" + i));
            }
            log.Info(methodName + " Done preloading");

            long startTime = DateTimeHelper.CurrentTimeMillis;

            updateListener.reset();
            SendEvent(makeMarketEvent("IBM_" + 10));

            long endTime = DateTimeHelper.CurrentTimeMillis;
            log.Info(methodName + " delta=" + (endTime - startTime));

            Assert.AreEqual(1, updateListener.LastNewData.Length);
            // Stay below 50 ms
            Assert.IsTrue((endTime - startTime) < 250);
        }

        private void SendEvent(Object _event)
        {
            epService.EPRuntime.SendEvent(_event);
        }

        private Object makeSupportEvent(String id)
        {
            SupportBean bean = new SupportBean();
            bean.str = id;
            return bean;
        }

        private Object makeMarketEvent(String id)
        {
            return new SupportMarketDataBean(id, 0, 0L, "");
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
