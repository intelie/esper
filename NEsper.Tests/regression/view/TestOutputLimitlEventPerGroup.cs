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
    public class TestOutputLimitlEventPerGroup
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
            String viewExpr = "select symbol," + "sum(price) as mySum," + "avg(price) as myAvg " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol " + "output last every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertionLast();
        }

        [Test]
        public virtual void testNoOutputClauseView()
        {
            String viewExpr = "select symbol," + "sum(price) as mySum," + "avg(price) as myAvg " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertionSingle();
        }

        [Test]
        public virtual void testNoOutputClauseJoin()
        {
            String viewExpr = "select symbol," + "sum(price) as mySum," + "avg(price) as myAvg " + "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " + "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + "       and one.str = two.symbol " + "group by symbol";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));
            epService.EPRuntime.SendEvent(new SupportBeanString("AAA"));

            runAssertionSingle();
        }

        [Test]
        public virtual void testNoJoinAll()
        {
            String viewExpr =
                "select symbol," + "sum(price) as mySum," + "avg(price) as myAvg " +
                "from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + 
                "where symbol='DELL' or symbol='IBM' or symbol='GE' " + 
                "group by symbol " +
                "output all every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertionAll();
        }

        [Test]
        public virtual void testJoinLast()
        {
            String viewExpr =
                "select symbol," + "sum(price) as mySum," + "avg(price) as myAvg " + 
                "from " +
                typeof(SupportBeanString).FullName + ".win:length(100) as one, " + 
                typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " + 
                "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + 
                "       and one.str = two.symbol " +
                "group by symbol " +
                "output last every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));
            epService.EPRuntime.SendEvent(new SupportBeanString("AAA"));

            runAssertionLast();
        }

        [Test]
        public virtual void testJoinAll()
        {
            String viewExpr =
                "select symbol," + "sum(price) as mySum," + "avg(price) as myAvg " + 
                "from " + 
                typeof(SupportBeanString).FullName + ".win:length(100) as one, " + 
                typeof(SupportMarketDataBean).FullName + ".win:length(5) as two " + 
                "where (symbol='DELL' or symbol='IBM' or symbol='GE') " + 
                "       and one.str = two.symbol " +
                "group by symbol " +
                "output all every 2 events";

            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));
            epService.EPRuntime.SendEvent(new SupportBeanString("AAA"));

            runAssertionAll();
        }

        private void runAssertionLast()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("myAvg"));

            SendEvent(SYMBOL_DELL, 10);
            Assert.IsFalse(testListener.Invoked);

            SendEvent(SYMBOL_DELL, 20);
            assertEvent(SYMBOL_DELL, null, null, 30d, 15d);
            testListener.reset();

            SendEvent(SYMBOL_DELL, 100);
            Assert.IsFalse(testListener.Invoked);

            SendEvent(SYMBOL_DELL, 50);
            assertEvent(SYMBOL_DELL, 30d, 15d, 170d, 170 / 3d);
        }

        private void runAssertionSingle()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("myAvg"));

            SendEvent(SYMBOL_DELL, 10);
            Assert.IsTrue(testListener.Invoked);
            assertEvent(SYMBOL_DELL, null, null, 10d, 10d);

            SendEvent(SYMBOL_IBM, 20);
            Assert.IsTrue(testListener.Invoked);
            assertEvent(SYMBOL_IBM, null, null, 20d, 20d);
        }

        private void runAssertionAll()
        {
            // assert select result type
            Assert.AreEqual(typeof(String), selectTestView.EventType.GetPropertyType("symbol"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("mySum"));
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("myAvg"));

            SendEvent(SYMBOL_IBM, 70);
            Assert.IsFalse(testListener.Invoked);

            SendEvent(SYMBOL_DELL, 10);
            assertEvents(
                SYMBOL_IBM, null, null, 70d, 70d,
                SYMBOL_DELL, null, null, 10d, 10d);
            testListener.reset();

            SendEvent(SYMBOL_DELL, 20);
            Assert.IsFalse(testListener.Invoked);

            SendEvent(SYMBOL_DELL, 100);
            assertEvents(
                SYMBOL_IBM, null, null, 70d, 70d,
                SYMBOL_DELL, 10d, 10d, 130d, 130d / 3d);
        }

        private void assertEvent(
            String symbol,
            double? oldSum,
            double? oldAvg,
            double? newSum,
            double? newAvg)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(1, oldData.Length);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(symbol, oldData[0]["symbol"]);
            Assert.AreEqual(oldSum, oldData[0]["mySum"]);
            Assert.AreEqual(oldAvg, oldData[0]["myAvg"]);

            Assert.AreEqual(symbol, newData[0]["symbol"]);
            Assert.AreEqual(newSum, newData[0]["mySum"]);
            Assert.AreEqual(newAvg, newData[0]["myAvg"], "newData myAvg wrong");

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void assertEvents(
            String symbolOne,
            double? oldSumOne,
            double? oldAvgOne,
            double newSumOne,
            double newAvgOne,
            String symbolTwo,
            double? oldSumTwo,
            double? oldAvgTwo,
            double newSumTwo,
            double newAvgTwo)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(2, oldData.Length);
            Assert.AreEqual(2, newData.Length);

            int indexOne = 0;
            int indexTwo = 1;
            if (oldData[0]["symbol"].Equals(symbolTwo))
            {
                indexTwo = 0;
                indexOne = 1;
            }
            Assert.AreEqual(newSumOne, newData[indexOne]["mySum"]);
            Assert.AreEqual(newSumTwo, newData[indexTwo]["mySum"]);
            Assert.AreEqual(oldSumOne, oldData[indexOne]["mySum"]);
            Assert.AreEqual(oldSumTwo, oldData[indexTwo]["mySum"]);

            Assert.AreEqual(newAvgOne, newData[indexOne]["myAvg"]);
            Assert.AreEqual(newAvgTwo, newData[indexTwo]["myAvg"]);
            Assert.AreEqual(oldAvgOne, oldData[indexOne]["myAvg"]);
            Assert.AreEqual(oldAvgTwo, oldData[indexTwo]["myAvg"]);

            testListener.reset();
            Assert.IsFalse(testListener.Invoked);
        }

        private void SendEvent(String symbol, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
            epService.EPRuntime.SendEvent(bean);
        }
    }
}
