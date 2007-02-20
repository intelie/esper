using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestGroupByCount
    {
        private static String SYMBOL_DELL = "DELL";
        private static String SYMBOL_IBM = "IBM";

        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private EPStatement selectTestView;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testCountOneView()
        {
            String viewExpr = "select symbol, " + "count(*) as countAll," + "count(distinct volume) as countDistVol," + "count(all volume) as countVol" + " from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertion();
        }

        [Test]
        public virtual void testCountJoin()
        {
            String viewExpr = "select symbol, " + "count(*) as countAll," + "count(distinct volume) as countDistVol," + "count(volume) as countVol " + " from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " + "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + "  and one.str = two.symbol " + "group by symbol";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));

            runAssertion();
        }

        private void runAssertion()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("countAll"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("countDistVol"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("countVol"));

            SendEvent(SYMBOL_DELL, 50L);
            assertEvents(SYMBOL_DELL, 0L, 0L, 0L, SYMBOL_DELL, 1L, 1L, 1L);

            SendEvent(SYMBOL_DELL, null);
            assertEvents(SYMBOL_DELL, 1L, 1L, 1L, SYMBOL_DELL, 2L, 1L, 1L);

            SendEvent(SYMBOL_DELL, 25L);
            assertEvents(SYMBOL_DELL, 2L, 1L, 1L, SYMBOL_DELL, 3L, 2L, 2L);

            SendEvent(SYMBOL_DELL, 25L);
            assertEvents(SYMBOL_DELL, 3L, 2L, 2L, SYMBOL_DELL, 3L, 1L, 2L);

            SendEvent(SYMBOL_DELL, 25L);
            assertEvents(SYMBOL_DELL, 3L, 1L, 2L, SYMBOL_DELL, 3L, 1L, 3L);

            SendEvent(SYMBOL_IBM, 1L);
            SendEvent(SYMBOL_IBM, null);
            SendEvent(SYMBOL_IBM, null);
            SendEvent(SYMBOL_IBM, null);
            assertEvents(SYMBOL_IBM, 3L, 1L, 1L, SYMBOL_IBM, 3L, 0L, 0L);
        }

        private void assertEvents(
            String symbolOld,
            Int64 countAllOld,
            Int64 countDistVolOld,
            Int64 countVolOld,
            String symbolNew,
            Int64 countAllNew,
            Int64 countDistVolNew,
            Int64 countVolNew)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(1, oldData.Length);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbolOld, oldData[0]["symbol"]);
            Assert.AreEqual(countAllOld, oldData[0]["countAll"]);
            Assert.AreEqual(countDistVolOld, oldData[0]["countDistVol"]);
            Assert.AreEqual(countVolOld, oldData[0]["countVol"]);

            Assert.AreEqual(symbolNew, newData[0]["symbol"]);
            Assert.AreEqual(countAllNew, newData[0]["countAll"]);
            Assert.AreEqual(countDistVolNew, newData[0]["countDistVol"]);
            Assert.AreEqual(countVolNew, newData[0]["countVol"]);

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void SendEvent(String symbol, long? volume)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}