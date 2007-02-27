using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.client.time;
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
    public class TestOrderBySimple 
    {
        private EPServiceProvider epService;
        private List<Double> prices;
        private List<String> symbols;
        private SupportUpdateListener testListener;
        private List<long> volumes;

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
        public virtual void testAcrossJoin()
        {
            String statementString = "select symbol, str from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertValues(symbols, "str");
            assertOnlyProperties(new String[] { "symbol", "str" });
            clearValues();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by str, price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbolPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();
        }

        [Test]
        public virtual void testDescending()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price desc";
            createAndSend(statementString);
            orderValuesByPriceDesc();
            assertValues(symbols, "symbol");
            clearValues();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price desc, symbol asc";
            createAndSend(statementString);
            orderValuesByPrice();
            symbols.Reverse();
            assertValues(symbols, "symbol");
            clearValues();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price asc";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            clearValues();

            statementString = "select symbol, volume from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by symbol desc";
            createAndSend(statementString);
            orderValuesBySymbol();
            symbols.Reverse();
            assertValues(symbols, "symbol");
            assertValues(volumes, "volume");
            clearValues();

            statementString = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by symbol desc, price desc";
            createAndSend(statementString);
            orderValuesBySymbolPrice();
            symbols.Reverse();
            prices.Reverse();
            assertValues(symbols, "symbol");
            assertValues(prices, "price");
            clearValues();

            statementString = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by symbol, price";
            createAndSend(statementString);
            orderValuesBySymbolPrice();
            assertValues(symbols, "symbol");
            assertValues(prices, "price");
            clearValues();
        }

        [Test]
        public virtual void testExpressions()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by (price * 6) + 5";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by (price * 6) + 5, price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "price" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol, 1+volume*23 from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by (price * 6) + 5, price, volume";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "(1+(volume*23))" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by volume*price, symbol";
            createAndSend(statementString);
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol, sum(price) from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by volume*sum(price), symbol";
            createAndSend(statementString);
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "sum(price)" });
            clearValues();
        }

        [Test]
        public virtual void testExpressionsJoin()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by (price * 6) + 5";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by (price * 6) + 5, price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(prices, "price");
            assertOnlyProperties(new String[] { "symbol", "price" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol, 1+volume*23 from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by (price * 6) + 5, price, volume";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "(1+(volume*23))" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by volume*price, symbol";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            epService.Initialize();

            statementString = "select symbol, sum(price) from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by volume*sum(price), symbol";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "sum(price)" });
            clearValues();
        }

        [Test]
        public virtual void testInvalid()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by sum(price)";
            try
            {
                createAndSend(statementString);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                // expected
            }

            statementString = "select sum(price) from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by sum(price + 6)";
            try
            {
                createAndSend(statementString);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                // expected
            }

            statementString = "select sum(price + 6) from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by sum(price)";
            try
            {
                createAndSend(statementString);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testInvalidJoin()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by sum(price)";
            try
            {
                createAndSend(statementString);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                // expected
            }

            statementString = "select sum(price) from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by sum(price + 6)";
            try
            {
                createAndSend(statementString);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                // expected
            }

            statementString = "select sum(price + 6) from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by sum(price)";
            try
            {
                createAndSend(statementString);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testMultipleKeys()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by symbol, price";
            createAndSend(statementString);
            orderValuesBySymbolPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by price, symbol, volume";
            createAndSend(statementString);
            orderValuesByPriceSymbol();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            statementString = "select symbol, volume*2 from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) " + "output every 6 events " + "order by price, volume";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "(volume*2)" });
            clearValues();
        }

        [Test]
        public virtual void testAliases()
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

            statementString = "select symbol as mySymbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "order by price, mySymbol";
            createAndSend(statementString);
            symbols.Add("CAT");
            assertValues(symbols, "mySymbol");
            clearValues();
            SendEvent("FOX", 10);
            symbols.Add("FOX");
            assertValues(symbols, "mySymbol");
            clearValues();
        }

        [Test]
        public virtual void testMultipleKeysJoin()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by symbol, price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbolPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price, symbol, volume";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceSymbol();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            statementString = "select symbol, volume*2 from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price, volume";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol", "(volume*2)" });
            clearValues();
        }

        [Test]
        public virtual void testSimple()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            statementString = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertValues(prices, "price");
            assertOnlyProperties(new String[] { "symbol", "price" });
            clearValues();

            statementString = "select symbol, volume from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] { "symbol", "volume" });
            clearValues();

            statementString = "select symbol, volume*2 from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertValues(volumes, "(volume*2)");
            assertOnlyProperties(new String[] { "symbol", "(volume*2)" });
            clearValues();

            statementString = "select symbol, volume from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by symbol";
            createAndSend(statementString);
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] { "symbol", "volume" });
            clearValues();

            statementString = "select price from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by symbol";
            createAndSend(statementString);
            orderValuesBySymbol();
            assertValues(prices, "price");
            assertOnlyProperties(new String[] { "price" });
            clearValues();
        }

        [Test]
        public virtual void testSimpleJoin()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();

            statementString = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertValues(prices, "price");
            assertOnlyProperties(new String[] { "symbol", "price" });
            clearValues();

            statementString = "select symbol, volume from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] { "symbol", "volume" });
            clearValues();

            statementString = "select symbol, volume*2 from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertValues(symbols, "symbol");
            assertValues(volumes, "(volume*2)");
            assertOnlyProperties(new String[] { "symbol", "(volume*2)" });
            clearValues();

            statementString = "select symbol, volume from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by symbol";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] { "symbol", "volume" });
            clearValues();

            statementString = "select price from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by symbol, price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbolJoin();
            assertValues(prices, "price");
            assertOnlyProperties(new String[] { "price" });
            clearValues();
        }

        [Test]
        public virtual void testWildcard()
        {
            String statementString = "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            orderValuesByPrice();
            assertValues(symbols, "symbol");
            assertValues(prices, "price");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] {
                "symbol", "volume", "price", "feed",
                "Symbol", "Volume", "Price", "Feed"
            });
            clearValues();

            statementString = "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "output every 6 events " + "order by symbol";
            createAndSend(statementString);
            orderValuesBySymbol();
            assertValues(symbols, "symbol");
            assertValues(prices, "price");
            assertValues(volumes, "volume");
            assertOnlyProperties(new String[] { 
                "symbol", "volume", "price", "feed",
                "Symbol", "Volume", "Price", "Feed"
            });
            clearValues();
        }
        
        [Test]
        public virtual void testWildcardJoin()
        {
            String statementString = "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            assertSymbolsJoinWildCard();
            clearValues();

            epService.Initialize();

            statementString = "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "output every 6 events " + "order by symbol, price";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesBySymbolJoin();
            assertSymbolsJoinWildCard();
            clearValues();
        }

        [Test]
        public virtual void testNoOutputClauseView()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(5) " + "order by price";
            createAndSend(statementString);
            symbols.Add("CAT");
            assertValues(symbols, "symbol");
            clearValues();
            SendEvent("FOX", 10);
            symbols.Add("FOX");
            assertValues(symbols, "symbol");
            clearValues();

            epService.Initialize();

            // Set manual clocking
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            // Set Start time
            sendTimeEvent(0);

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:time_batch(1 sec) " + "order by price";
            createAndSend(statementString);
            orderValuesByPrice();
            sendTimeEvent(1000);
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();
        }

        [Test]
        public virtual void testNoOutputClauseJoin()
        {
            String statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "order by price";
            createAndSend(statementString);
            sendJoinEvents();
            symbols.Add("KGB");
            assertValues(symbols, "symbol");
            clearValues();
            SendEvent("DOG", 10);
            symbols.Add("DOG");
            assertValues(symbols, "symbol");
            clearValues();

            epService.Initialize();

            // Set manual clocking
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            // Set Start time
            sendTimeEvent(0);

            statementString = "select symbol from " + typeof(SupportMarketDataBean).FullName + ".win:time_batch(1) as one, " + typeof(SupportBeanString).FullName + ".win:length(100) as two " + "where one.symbol = two.str " + "order by price, symbol";
            createAndSend(statementString);
            sendJoinEvents();
            orderValuesByPriceJoin();
            sendTimeEvent(1000);
            assertValues(symbols, "symbol");
            assertOnlyProperties(new String[] { "symbol" });
            clearValues();
        }

        private void assertOnlyProperties(IList<String> requiredProperties)
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

        private void assertSymbolsJoinWildCard()
        {
            EventBean[] events = testListener.LastNewData;
            log.Debug(".assertValues event type = " + events[0].EventType);
            log.Debug(".assertValues values: " + symbols);
            log.Debug(".assertValues events.Length==" + events.Length);
            for (int i = 0; i < events.Length; i++)
            {
                SupportMarketDataBean _event = (SupportMarketDataBean)events[i]["one"];
                Assert.AreEqual(symbols[i], _event.Symbol);
            }
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

        private void orderValuesByPriceDesc()
        {
            symbols.Add("IBM");
            symbols.Add("CAT");
            symbols.Add("CAT");
            symbols.Add("CMU");
            symbols.Add("IBM");
            symbols.Add("KGB");
            prices.Add(6d);
            prices.Add(6d);
            prices.Add(5d);
            prices.Add(3d);
            prices.Add(2d);
            prices.Add(1d);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
            volumes.Add(0L);
        }

        private void orderValuesByPriceJoin()
        {
            symbols.Add("KGB");
            symbols.Add("IBM");
            symbols.Add("CMU");
            symbols.Add("CAT");
            symbols.Add("CAT");
            symbols.Add("IBM");
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

        private void orderValuesByPriceSymbol()
        {
            symbols.Add("KGB");
            symbols.Add("IBM");
            symbols.Add("CMU");
            symbols.Add("CAT");
            symbols.Add("CAT");
            symbols.Add("IBM");
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

        private void orderValuesBySymbolJoin()
        {
            symbols.Add("CAT");
            symbols.Add("CAT");
            symbols.Add("CMU");
            symbols.Add("IBM");
            symbols.Add("IBM");
            symbols.Add("KGB");
            prices.Add(5d);
            prices.Add(6d);
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

        private void orderValuesBySymbolPrice()
        {
            symbols.Add("CAT");
            symbols.Add("CAT");
            symbols.Add("CMU");
            symbols.Add("IBM");
            symbols.Add("IBM");
            symbols.Add("KGB");
            prices.Add(5d);
            prices.Add(6d);
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

        private void SendEvent(String symbol, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private void sendTimeEvent(int millis)
        {
            CurrentTimeEvent _event = new CurrentTimeEvent(millis);
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendJoinEvents()
        {
            epService.EPRuntime.SendEvent(new SupportBeanString("CAT"));
            epService.EPRuntime.SendEvent(new SupportBeanString("IBM"));
            epService.EPRuntime.SendEvent(new SupportBeanString("CMU"));
            epService.EPRuntime.SendEvent(new SupportBeanString("KGB"));
            epService.EPRuntime.SendEvent(new SupportBeanString("DOG"));
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TestOrderBySimple));
    }
}
