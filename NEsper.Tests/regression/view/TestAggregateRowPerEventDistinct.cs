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
    public class TestAggregateRowPerEventDistinct
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
        public virtual void testSumOneView()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String viewExpr = "select symbol, sum(distinct volume) as volSum " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) ";

            selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
            selectTestView.AddListener(testListener.Update);

            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("volSum"));

            SendEvent(SYMBOL_DELL, 10000);
            assertEvents(SYMBOL_DELL, 10000);

            SendEvent(SYMBOL_DELL, 10000);
            assertEvents(SYMBOL_DELL, 10000); // still 10k since summing distinct volumes

            SendEvent(SYMBOL_DELL, 20000);
            assertEvents(SYMBOL_DELL, 30000);

            SendEvent(SYMBOL_IBM, 1000);
            assertEvents(SYMBOL_DELL, 30000, SYMBOL_IBM, 31000);

            SendEvent(SYMBOL_IBM, 1000);
            assertEvents(SYMBOL_DELL, 31000, SYMBOL_IBM, 21000);

            SendEvent(SYMBOL_IBM, 1000);
            assertEvents(SYMBOL_DELL, 21000, SYMBOL_IBM, 1000);
        }

        private void assertEvents(String symbol, long volSum)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.IsNull(oldData);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbol, newData[0]["symbol"]);
            Assert.AreEqual(volSum, newData[0]["volSum"]);

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void assertEvents(String symbolOld, long volSumOld, String symbolNew, long volSumNew)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(1, oldData.Length);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbolOld, oldData[0]["symbol"]);
            Assert.AreEqual(volSumOld, oldData[0]["volSum"]);

            Assert.AreEqual(symbolNew, newData[0]["symbol"]);
            Assert.AreEqual(volSumNew, newData[0]["volSum"]);

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void SendEvent(String symbol, long volume)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}