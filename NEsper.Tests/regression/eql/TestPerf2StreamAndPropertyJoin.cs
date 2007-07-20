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
    public class TestPerf2StreamAndPropertyJoin
    {
        private EPServiceProvider epService;
        private EPStatement joinView;
        private SupportUpdateListener updateListener;

        [SetUp]
        public virtual void setUp()
        {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
            updateListener = new SupportUpdateListener();
        }

        [Test]
        public void testPerf2Properties()
        {
            String methodName = ".testPerformanceJoinNoResults";

            String joinStatement = "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length(1000000)," + typeof(SupportBean).FullName + ".win:length(1000000)" + " where symbol=string and volume=longBoxed";

            joinView = epService.EPAdministrator.CreateEQL(joinStatement);
            joinView.AddListener(updateListener);

            // Send events for each stream
            log.Info(methodName + " Preloading events");
            long startTime = DateTimeHelper.CurrentTimeMillis;
            for (int i = 0; i < 1000; i++)
            {
                SendEvent(MakeMarketEvent("IBM_" + i, 1));
                SendEvent(MakeSupportEvent("CSCO_" + i, 2));
            }
            log.Info(methodName + " Done preloading");

            long endTime = DateTimeHelper.CurrentTimeMillis;
            log.Info(methodName + " delta=" + (endTime - startTime));

            // Stay at 250, belwo 500ms
            Assert.IsTrue((endTime - startTime) < 500);
        }

        [Test]
        public void testPerf3Properties()
        {
            String methodName = ".testPerformanceJoinNoResults";

            String joinStatement = "select * from " + typeof(SupportMarketDataBean).FullName + "().win:length(1000000)," + typeof(SupportBean).FullName + ".win:length(1000000)" + " where symbol=string and volume=longBoxed and doublePrimitive=price";

            joinView = epService.EPAdministrator.CreateEQL(joinStatement);
            joinView.AddListener(updateListener);

            // Send events for each stream
            log.Info(methodName + " Preloading events");
            long startTime = DateTimeHelper.CurrentTimeMillis;
            for (int i = 0; i < 1000; i++)
            {
                SendEvent(MakeMarketEvent("IBM_" + i, 1));
                SendEvent(MakeSupportEvent("CSCO_" + i, 2));
            }
            log.Info(methodName + " Done preloading");

            long endTime = DateTimeHelper.CurrentTimeMillis;
            log.Info(methodName + " delta=" + (endTime - startTime));

            // Stay at 250, belwo 500ms
            Assert.IsTrue((endTime - startTime) < 500);
        }

        private void SendEvent(Object _event)
        {
            epService.EPRuntime.SendEvent(_event);
        }

        private Object MakeSupportEvent(String id, long longBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.SetString(id);
            bean.SetLongBoxed(longBoxed);
            return bean;
        }

        private Object MakeMarketEvent(String id, long volume)
        {
            return new SupportMarketDataBean(id, 0, (long)volume, "");
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
