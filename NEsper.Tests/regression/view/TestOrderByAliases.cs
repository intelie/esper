using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestOrderByAliases
    {
        private EPServiceProvider epService;
        private IList<Double> prices;
        private IList<String> symbols;
        private SupportUpdateListener testListener;
        private IList<long> volumes;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
            symbols = new List<String>();
            prices = new List<Double>();
            volumes = new List<Int64>();
        }

        [Test]
        public virtual void testAliasesSimple()
        {
            String statementString = "select symbol as mySymbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by mySymbol";
            createAndSend(statementString);
            orderValuesBySymbol();
            assertValues(symbols, "mySymbol");
            assertOnlyProperties(new String[] { "mySymbol" });
            clearValues();

            statementString = "select symbol as mySymbol, price as myPrice from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by myPrice";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "mySymbol");
            assertValues(prices, "myPrice");
            assertOnlyProperties(new String[] { "mySymbol", "myPrice" });
            clearValues();

            statementString = "select symbol, price as myPrice from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by (myPrice * 6) + 5, price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "myPrice" });
            clearValues();

            statementString = "select symbol, 1+volume*23 as myVol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by (price * 6) + 5, price, myVol";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "myVol" });
            clearValues();
        }

        [Test]
        public virtual void testAliasesAggregation()
        {
            String statementString = "select symbol, volume, sum(price) from " + typeof(SupportMarketDataBean).FullName + ".win:length(20) " + "group by symbol " + "output every 6 events " + "order by sum(price)";
            createAndSendAggregate(statementString);
            orderValuesBySumPriceEvent();
            assertValues(prices, "sum(price)");
            assertValues(symbols, "symbol");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] { "symbol", "sum(price)", "volume" });
            clearValues();
        }

        private
        void assertOnlyProperties(IList<String> requiredProperties)
        {
            EventBean[] events = testListener.LastNewData;
            if (events == null || events.Length == 0)
            {
                return;
            }
            EventType type = events[0].EventType;
            IList<String> actualProperties = new List<String>(type.PropertyNames);
            log.Debug(".assertOnlyProperties actualProperties==" + actualProperties);
            Assert.IsTrue(CollectionHelper.ContainsAll(actualProperties, requiredProperties));
            CollectionHelper.RemoveAll(actualProperties, requiredProperties);
            Assert.IsTrue(actualProperties.Count == 0);
        }

        private void assertValues<T>(IList<T> values, String valueName)
        {
            EventBean[] events = testListener.LastNewData;
            Assert.AreEqual(values.Count, events.Length);
            log.Debug(".assertValues values: " + values);
            for (int i = 0; i < events.Length; i++)
            {
                log.Debug(".assertValues events[" + i + "]==" + events[i][valueName]);
                Assert.AreEqual(values[i], events[i][valueName]);
            }
        }

        private void clearValues()
        {
            prices.Clear();
            volumes.Clear();
            symbols.Clear();
        }

        private void createAndSend(String statementString)
        {
            testListener = new SupportUpdateListener();
            EPStatement statement = epService.EPAdministrator.CreateEQL(statementString);
            statement.AddListener(testListener.Update);
            SendEvent("IBM", 2);
            SendEvent("KGB", 1);
            SendEvent("CMU", 3);
            SendEvent("IBM", 6);
            SendEvent("CAT", 6);
            SendEvent("CAT", 5);
        }


        private void orderValuesByPrice()
        {
            symbols.Add("KGB");
            symbols.Add("IBM");
            symbols.Add("CMU");
            symbols.Add("CAT");
            symbols.Add("IBM");
            symbols.Add("CAT");
            prices.Add(1d);
            prices.Add(2d);
            prices.Add(3d);
            prices.Add(5d);
            prices.Add(6d);
            prices.Add(6d);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
        }

        private void SendEvent(String symbol, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private void orderValuesBySumPriceEvent()
        {
            symbols.Add("CMU");
            symbols.Add("CMU");
            symbols.Add("IBM");
            symbols.Add("IBM");
            symbols.Add("CAT");
            symbols.Add("CAT");
            prices.Add(3d);
            prices.Add(3d);
            prices.Add(7d);
            prices.Add(7d);
            prices.Add(11d);
            prices.Add(11d);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
        }

        private void orderValuesBySymbol()
        {
            symbols.Add("CAT");
            symbols.Add("CAT");
            symbols.Add("CMU");
            symbols.Add("IBM");
            symbols.Add("IBM");
            symbols.Add("KGB");
            prices.Add(6d);
            prices.Add(5d);
            prices.Add(3d);
            prices.Add(2d);
            prices.Add(6d);
            prices.Add(1d);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
        }

        private void createAndSendAggregate(String statementString)
        {
            testListener = new SupportUpdateListener();
            EPStatement statement = epService.EPAdministrator.CreateEQL(statementString);
            statement.AddListener(testListener.Update);
            SendEvent("IBM", 3);
            SendEvent("IBM", 4);
            SendEvent("CMU", 1);
            SendEvent("CMU", 2);
            SendEvent("CAT", 5);
            SendEvent("CAT", 6);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TestOrderByAliases));
    }
}
