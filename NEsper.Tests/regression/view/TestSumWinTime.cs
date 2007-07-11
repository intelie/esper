using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestSumWinTime
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
        public void testWinTimeSum()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String sumTimeExpr = "select symbol, volume, sum(price) as mySum " + "from " + typeof(SupportMarketDataBean).FullName + ".win:time(30)";

            selectTestView = epService.EPAdministrator.CreateEQL(sumTimeExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            runAssertion();
        }

        [Test]
        public void testWinTimeSumGroupBy()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String sumTimeUniExpr = "select symbol, volume, sum(price) as mySum " + "from " + typeof(SupportMarketDataBean).FullName + ".win:time(30) group by symbol";

            selectTestView = epService.EPAdministrator.CreateEQL(sumTimeUniExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            runGroupByAssertions();
        }

        [Test]
        public void testWinTimeSumSingle()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String sumTimeUniExpr = "select symbol, volume, sum(price) as mySum " + "from " + typeof(SupportMarketDataBean).FullName + "(symbol = 'IBM').win:time(30)";

            selectTestView = epService.EPAdministrator.CreateEQL(sumTimeUniExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            runSingleAssertion();
        }

        private void runAssertion()
        {
            assertSelectResultType();

            CurrentTimeEvent currentTime = new CurrentTimeEvent(0);
            epService.EPRuntime.SendEvent(currentTime);

            SendEvent(SYMBOL_DELL, 10000, 51);
            assertEvents(SYMBOL_DELL, 10000, 51, false);

            SendEvent(SYMBOL_IBM, 20000, 52);
            assertEvents(SYMBOL_IBM, 20000, 103, false);

            SendEvent(SYMBOL_DELL, 40000, 45);
            assertEvents(SYMBOL_DELL, 40000, 148, false);

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(35000));

            //These events are out of the window and new sums are generated

            SendEvent(SYMBOL_IBM, 30000, 70);
            assertEvents(SYMBOL_IBM, 30000, 70, false);

            SendEvent(SYMBOL_DELL, 10000, 20);
            assertEvents(SYMBOL_DELL, 10000, 90, false);
        }

        private void runGroupByAssertions()
        {
            assertSelectResultType();

            CurrentTimeEvent currentTime = new CurrentTimeEvent(0);
            epService.EPRuntime.SendEvent(currentTime);

            SendEvent(SYMBOL_DELL, 10000, 51);
            assertEvents(SYMBOL_DELL, 10000, 51, false);

            SendEvent(SYMBOL_IBM, 30000, 70);
            assertEvents(SYMBOL_IBM, 30000, 70, false);

            SendEvent(SYMBOL_DELL, 20000, 52);
            assertEvents(SYMBOL_DELL, 20000, 103, false);

            SendEvent(SYMBOL_IBM, 30000, 70);
            assertEvents(SYMBOL_IBM, 30000, 140, false);

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(35000));

            //These events are out of the window and new sums are generated
            SendEvent(SYMBOL_DELL, 10000, 90);
            assertEvents(SYMBOL_DELL, 10000, 90, false);

            SendEvent(SYMBOL_IBM, 30000, 120);
            assertEvents(SYMBOL_IBM, 30000, 120, false);

            SendEvent(SYMBOL_DELL, 20000, 90);
            assertEvents(SYMBOL_DELL, 20000, 180, false);

            SendEvent(SYMBOL_IBM, 30000, 120);
            assertEvents(SYMBOL_IBM, 30000, 240, false);
        }

        private void runSingleAssertion()
        {
            assertSelectResultType();

            CurrentTimeEvent currentTime = new CurrentTimeEvent(0);
            epService.EPRuntime.SendEvent(currentTime);

            SendEvent(SYMBOL_IBM, 20000, 52);
            assertEvents(SYMBOL_IBM, 20000, 52, false);

            SendEvent(SYMBOL_IBM, 20000, 100);
            assertEvents(SYMBOL_IBM, 20000, 152, false);

            epService.EPRuntime.SendEvent(new CurrentTimeEvent(35000));

            //These events are out of the window and new sums are generated
            SendEvent(SYMBOL_IBM, 20000, 252);
            assertEvents(SYMBOL_IBM, 20000, 252, false);

            SendEvent(SYMBOL_IBM, 20000, 100);
            assertEvents(SYMBOL_IBM, 20000, 352, false);
        }

        private void assertEvents(String symbol, long volume, double sum, bool unique)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            if (!unique)
                Assert.IsNull(oldData);

            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbol, newData[0]["symbol"]);
            Assert.AreEqual(volume, newData[0]["volume"]);
            Assert.AreEqual(sum, newData[0]["mySum"]);

            testListener.Reset();
            Assert.IsFalse(testListener.IsInvoked);
        }

        private void assertSelectResultType()
        {
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("volume"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));
        }

        private void SendEvent(String symbol, long volume, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}