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
    public class TestGroupByMedianAndDeviation
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
            String viewExpr = "select symbol," + "median(all price) as myMedian," + "median(distinct price) as myDistMedian," + "stddev(all price) as myStdev," + "avedev(all price) as myAvedev " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertion();
        }

        [Test]
        public virtual void testSumJoin()
        {
            String viewExpr = "select symbol," + "median(price) as myMedian," + "median(distinct price) as myDistMedian," + "stddev(price) as myStdev," + "avedev(price) as myAvedev " + "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " + "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + "       and one.str = two.symbol " + "group by symbol";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));
            epService.EPRuntime.SendEvent(new SupportBeanString("AAA"));

            runAssertion();
        }

        private void runAssertion()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("myMedian"));
            Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("myDistMedian"));
            Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("myStdev"));
            Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("myAvedev"));

            SendEvent(SYMBOL_DELL, 10);
            assertEvents(SYMBOL_DELL, null, null, null, null, 10d, 10d, null, 0d);

            SendEvent(SYMBOL_DELL, 20);
            assertEvents(SYMBOL_DELL, 10d, 10d, null, 0d, 15d, 15d, 7.071067812d, 5d);

            SendEvent(SYMBOL_DELL, 20);
            assertEvents(SYMBOL_DELL, 15d, 15d, 7.071067812d, 5d, 20d, 15d, 5.773502692, 4.444444444444444);

            SendEvent(SYMBOL_DELL, 90);
            assertEvents(SYMBOL_DELL, 20d, 15d, 5.773502692, 4.444444444444444, 20d, 20d, 36.96845502d, 27.5d);

            SendEvent(SYMBOL_DELL, 5);
            assertEvents(SYMBOL_DELL, 20d, 20d, 36.96845502d, 27.5d, 20d, 15d, 34.71310992d, 24.4d);

            SendEvent(SYMBOL_DELL, 90);
            assertEvents(SYMBOL_DELL, 20d, 15d, 34.71310992d, 24.4d, 20d, 20d, 41.53311931d, 36d);

            SendEvent(SYMBOL_DELL, 30);
            assertEvents(SYMBOL_DELL, 20d, 20d, 41.53311931d, 36d, 30d, 25d, 40.24922359d, 34.4d);
        }

        private void assertEvents(
            String symbol,
            Double? oldMedian,
            Double? oldDistMedian,
            Double? oldStdev,
            Double? oldAvedev,
            Double? newMedian,
            Double? newDistMedian,
            Double? newStdev,
            Double? newAvedev)
        {

            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(1, oldData.Length);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbol, oldData[0]["symbol"]);
            Assert.AreEqual(oldMedian, oldData[0]["myMedian"], "oldData.myMedian wrong");
            Assert.AreEqual(oldDistMedian, oldData[0]["myDistMedian"], "oldData.myDistMedian wrong");
            Assert.AreEqual(oldAvedev, oldData[0]["myAvedev"], "oldData.myAvedev wrong");

            Double? oldStdevResult = (Double?)oldData[0]["myStdev"];
            if (! oldStdevResult.HasValue)
            {
                Assert.IsNull(oldStdev);
            }
            else
            {
                Assert.AreEqual(
                    Math.Round(oldStdev.Value * 1000),
                    Math.Round(oldStdevResult.Value * 1000),
                    "oldData.myStdev wrong"
                    );
            }

            Assert.AreEqual(symbol, newData[0]["symbol"]);
            Assert.AreEqual(newMedian, newData[0]["myMedian"], "newData.myMedian wrong");
            Assert.AreEqual(newDistMedian, newData[0]["myDistMedian"], "newData.myDistMedian wrong");
            Assert.AreEqual(newAvedev, newData[0]["myAvedev"], "newData.myAvedev wrong");

            Double? newStdevResult = (Double?)newData[0]["myStdev"];
            if (!newStdevResult.HasValue)
            {
                Assert.IsNull(newStdev);
            }
            else
            {
                Assert.AreEqual(
                    Math.Round(newStdev.Value * 1000),
                    Math.Round(newStdevResult.Value * 1000),
                    "newData.myStdev wrong");
            }

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void SendEvent(String symbol, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
            epService.EPRuntime.SendEvent(bean);
        }


        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
