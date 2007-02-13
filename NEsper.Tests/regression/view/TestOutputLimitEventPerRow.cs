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
    public class TestOutputLimitEventPerRow
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
        public virtual void testNoJoinLast()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String viewExpr =
                "select symbol, volume, sum(price) as mySum " +
                "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
                "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                "group by symbol " +
                "output last every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertionLast();
        }

        private void assertEvent(String symbol, Double mySum, Int64 volume)
        {
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbol, newData[0]["symbol"]);
            Assert.AreEqual(mySum, newData[0]["mySum"]);
            Assert.AreEqual(volume, newData[0]["volume"]);

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void runAssertionSingle()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(Double), selectTestView.EventType.GetPropertyType("mySum"));
            Assert.AreEqual(typeof(Int64), selectTestView.EventType.GetPropertyType("volume"));

            SendEvent(SYMBOL_DELL, 10, 100);
            Assert.IsTrue(testListener.Invoked);
            assertEvent(SYMBOL_DELL, 100d, 10L);

            SendEvent(SYMBOL_IBM, 15, 50);
            assertEvent(SYMBOL_IBM, 50d, 15L);
        }

        [Test]
        public virtual void testNoOutputClauseView()
        {
            String viewExpr =
                "select symbol, volume, sum(price) as mySum " +
                "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
                "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                "group by symbol ";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertionSingle();
        }

        [Test]
        public virtual void testNoJoinAll()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String viewExpr =
                "select symbol, volume, sum(price) as mySum " +
                "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
                "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                "group by symbol " +
                "output all every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertionAll();
        }

        [Test]
        public virtual void testJoinAll()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String viewExpr =
                "select symbol, volume, sum(price) as mySum " +
                "from " +
                typeof(SupportBeanString).FullName + ".win:length(100) as one, " +
                typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " +
                "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                "  and one.str = two.symbol " +
                "group by symbol " +
                "output all every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));

            runAssertionAll();
        }

        [Test]
        public virtual void testJoinLast()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String viewExpr =
                "select symbol, volume, sum(price) as mySum " +
                "from " +
                typeof(SupportBeanString).FullName + ".win:length(100) as one, " +
                typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " +
                "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                "  and one.str = two.symbol " +
                "group by symbol " +
                "output last every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));

            runAssertionLast();
        }

        [Test]
        public virtual void testNoOutputClauseJoin()
        {
            // Every event generates a new row, this time we sum the price by symbol and output volume
            String viewExpr =
                "select symbol, volume, sum(price) as mySum " +
                "from " +
                typeof(SupportBeanString).FullName + ".win:length(100) as one, " +
                typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " +
                "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                "  and one.str = two.symbol " +
                "group by symbol " +
                "output last every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));

            runAssertionLast();
        }

        private void runAssertionAll()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("volume"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));

            SendEvent(SYMBOL_IBM, 10000, 20);
            Assert.IsFalse(testListener.getAndClearIsInvoked());

            SendEvent(SYMBOL_DELL, 10000, 51);
            assertTwoEvents(SYMBOL_IBM, 10000, 20, SYMBOL_DELL, 10000, 51);

            SendEvent(SYMBOL_DELL, 20000, 52);
            Assert.IsFalse(testListener.getAndClearIsInvoked());

            SendEvent(SYMBOL_DELL, 40000, 45);
            assertThreeEvents(SYMBOL_IBM, 10000, 20, SYMBOL_DELL, 20000, 51 + 52 + 45, SYMBOL_DELL, 40000, 51 + 52 + 45);
        }

        private void runAssertionLast()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("volume"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));

            SendEvent(SYMBOL_DELL, 10000, 51);
            Assert.IsFalse(testListener.getAndClearIsInvoked());

            SendEvent(SYMBOL_DELL, 20000, 52);
            assertTwoEvents(SYMBOL_DELL, 10000, 103, SYMBOL_DELL, 20000, 103);

            SendEvent(SYMBOL_DELL, 30000, 70);
            Assert.IsFalse(testListener.getAndClearIsInvoked());

            SendEvent(SYMBOL_IBM, 10000, 20);
            assertTwoEvents(SYMBOL_DELL, 30000, 173, SYMBOL_IBM, 10000, 20);
        }

        private void assertTwoEvents(String symbol1, long volume1, double sum1, String symbol2, long volume2, double sum2)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.IsNull(oldData);
            Assert.AreEqual(2, newData.Length);

            if (matchesEvent(newData[0], symbol1, volume1, sum1))
            {
                Assert.IsTrue(matchesEvent(newData[1], symbol2, volume2, sum2));
            }
            else
            {
                Assert.IsTrue(matchesEvent(newData[0], symbol2, volume2, sum2));
                Assert.IsTrue(matchesEvent(newData[1], symbol1, volume1, sum1));
            }

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void assertThreeEvents(String symbol1, long volume1, double sum1, String symbol2, long volume2, double sum2, String symbol3, long volume3, double sum3)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.IsNull(oldData);
            Assert.AreEqual(3, newData.Length);

            if (matchesEvent(newData[0], symbol1, volume1, sum1))
            {
                if (matchesEvent(newData[1], symbol2, volume2, sum2))
                {
                    Assert.IsTrue(matchesEvent(newData[2], symbol3, volume3, sum3));
                }
                else
                {
                    Assert.IsTrue(matchesEvent(newData[1], symbol3, volume3, sum3));
                    Assert.IsTrue(matchesEvent(newData[2], symbol2, volume2, sum2));
                }
            }
            else if (matchesEvent(newData[0], symbol2, volume2, sum2))
            {
                if (matchesEvent(newData[1], symbol1, volume1, sum1))
                {
                    Assert.IsTrue(matchesEvent(newData[2], symbol3, volume3, sum3));
                }
                else
                {
                    Assert.IsTrue(matchesEvent(newData[1], symbol3, volume3, sum3));
                    Assert.IsTrue(matchesEvent(newData[2], symbol1, volume1, sum1));
                }
            }
            else
            {
                if (matchesEvent(newData[1], symbol1, volume1, sum1))
                {
                    Assert.IsTrue(matchesEvent(newData[2], symbol2, volume2, sum2));
                }
                else
                {
                    Assert.IsTrue(matchesEvent(newData[1], symbol2, volume2, sum2));
                    Assert.IsTrue(matchesEvent(newData[2], symbol1, volume1, sum1));
                }
            }


            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private bool matchesEvent(EventBean _event, String symbol, long volume, double sum)
        {
            return
                symbol.Equals(_event["symbol"]) &&
                Object.Equals(_event["volume"], volume) &&
                Object.Equals(_event["mySum"], sum);
        }

        private void SendEvent(String symbol, long volume, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}