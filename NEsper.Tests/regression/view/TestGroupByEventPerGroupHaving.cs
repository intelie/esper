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
    public class TestGroupByEventPerGroupHaving
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
        public virtual void testSumJoin()
        {
            String viewExpr = "select symbol, sum(price) as mySum " + "from " + typeof(SupportBeanString).FullName + ".win:length(100) as one, " + " " + typeof(SupportMarketDataBean).FullName + ".win:length(3) as two " + "where (symbol='DELL' or symbol='IBM' or symbol='GE')" + "       and one.str = two.symbol " + "group by symbol " + "having sum(price) >= 100";

            selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_DELL));
            epService.EPRuntime.SendEvent(new SupportBeanString(SYMBOL_IBM));
            epService.EPRuntime.SendEvent(new SupportBeanString("AAA"));

            runAssertion();
        }

        [Test]
        public virtual void testSumOneView()
        {
            String viewExpr = "select symbol, sum(price) as mySum " + "from " + typeof(SupportMarketDataBean).FullName + ".win:length(3) " + "where symbol='DELL' or symbol='IBM' or symbol='GE' " + "group by symbol " + "having sum(price) >= 100";

            selectTestView = epService.EPAdministrator.CreateEQL(viewExpr);
            selectTestView.AddListener(testListener);

            runAssertion();
        }

        private void runAssertion()
        {
            SendEvent(SYMBOL_DELL, 10);
            Assert.IsFalse(testListener.IsInvoked);

            SendEvent(SYMBOL_DELL, 60);
            Assert.IsFalse(testListener.IsInvoked);

            SendEvent(SYMBOL_DELL, 30);
            assertNewEvent(SYMBOL_DELL, 100);

            SendEvent(SYMBOL_IBM, 30);
            assertOldEvent(SYMBOL_DELL, 100);

            SendEvent(SYMBOL_IBM, 80);
            assertNewEvent(SYMBOL_IBM, 110);
        }

        private void assertNewEvent(String symbol, double newSum)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.IsNull(oldData);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(newSum, newData[0]["mySum"]);
            Assert.AreEqual(symbol, newData[0]["symbol"]);

            testListener.Reset();
            Assert.IsFalse(testListener.IsInvoked);
        }

        private void assertOldEvent(String symbol, double newSum)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.IsNull(newData);
            Assert.AreEqual(1, oldData.Length);

            Assert.AreEqual(newSum, oldData[0]["mySum"]);
            Assert.AreEqual(symbol, oldData[0]["symbol"]);

            testListener.Reset();
            Assert.IsFalse(testListener.IsInvoked);
        }

        private void assertEvents(String symbol, double oldSum, double newSum)
        {
            EventBean[] oldData = testListener.LastOldData;
            EventBean[] newData = testListener.LastNewData;

            Assert.AreEqual(1, oldData.Length);
            Assert.AreEqual(1, newData.Length);

            Assert.AreEqual(oldSum, oldData[0]["mySum"]);
            Assert.AreEqual(symbol, oldData[0]["symbol"]);

            Assert.AreEqual(newSum, newData[0]["mySum"]);
            Assert.AreEqual(symbol, newData[0]["symbol"]);

            testListener.Reset();
            Assert.IsFalse(testListener.IsInvoked);
        }

        private void assertEvents(String symbolOne, double oldSumOne, double newSumOne, String symbolTwo, double oldSumTwo, double newSumTwo)
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

            testListener.Reset();
            Assert.IsFalse(testListener.IsInvoked);
        }

        private void SendEvent(String symbol, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}