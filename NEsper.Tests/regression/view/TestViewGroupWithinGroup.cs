using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestViewGroupWithinGroup
    {
        private String SYMBOL_MSFT = "MSFT";
        private String SYMBOL_GE = "GE";

        private String FEED_INFO = "INFO";
        private String FEED_REU = "REU";

        private EPServiceProvider epService;
        private EPStatement viewGrouped;
        private SupportUpdateListener listener = new SupportUpdateListener();

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            // Listen to all ticks
            viewGrouped = epService.EPAdministrator.CreateEQL(
                "select * from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').std:groupby('feed').std:groupby('volume').std:size()");

            // Counts per symbol, feed and volume the events
            viewGrouped.AddListener(listener.Update);
        }

        [Test]
        public virtual void testPullDateAndPushData()
        {
            List<EDictionary<String, Object>> mapList = new List<EDictionary<String, Object>>();

            // Set up a map of expected values

            EDictionary<String, Object>[] expectedValues = new EHashDictionary<String, Object>[10];
            for (int i = 0; i < expectedValues.Length; i++)
            {
                expectedValues[i] = new EHashDictionary<String, Object>();
            }

            // Send one event, check results
            SendEvent(SYMBOL_GE, FEED_INFO, 1);

            populateMap(expectedValues[0], SYMBOL_GE, FEED_INFO, 1L, 0);
            mapList.Add(expectedValues[0]);
            EventPropertyAssertionUtil.compare(listener.LastOldData, mapList);
            populateMap(expectedValues[0], SYMBOL_GE, FEED_INFO, 1L, 1);
            EventPropertyAssertionUtil.compare(listener.LastNewData, mapList);
            EventPropertyAssertionUtil.compare(viewGrouped.GetEnumerator(), mapList);

            // Send a couple of events
            SendEvent(SYMBOL_GE, FEED_INFO, 1);
            SendEvent(SYMBOL_GE, FEED_INFO, 2);
            SendEvent(SYMBOL_GE, FEED_INFO, 1);
            SendEvent(SYMBOL_GE, FEED_REU, 99);
            SendEvent(SYMBOL_MSFT, FEED_INFO, 100);

            populateMap(expectedValues[1], SYMBOL_MSFT, FEED_INFO, 100, 0);
            mapList.Clear();
            mapList.Add(expectedValues[1]);
            EventPropertyAssertionUtil.compare(listener.LastOldData, mapList);
            populateMap(expectedValues[1], SYMBOL_MSFT, FEED_INFO, 100, 1);
            EventPropertyAssertionUtil.compare(listener.LastNewData, mapList);

            populateMap(expectedValues[0], SYMBOL_GE, FEED_INFO, 1, 3);
            populateMap(expectedValues[2], SYMBOL_GE, FEED_INFO, 2, 1);
            populateMap(expectedValues[3], SYMBOL_GE, FEED_REU, 99, 1);
            mapList.Clear();
            mapList.Add(expectedValues[0]);
            mapList.Add(expectedValues[2]);
            mapList.Add(expectedValues[3]);
            mapList.Add(expectedValues[1]);
            EventPropertyAssertionUtil.compare(viewGrouped.GetEnumerator(), mapList);
        }

        private void populateMap(EDictionary<String, Object> map, String symbol, String feed, long volume, long size)
        {
            map.Put("symbol", symbol);
            map.Put("feed", feed);
            map.Put("volume", volume);
            map.Put("size", size);
        }

        private void SendEvent(String symbol, String feed, long volume)
        {
            SupportMarketDataBean _event = new SupportMarketDataBean(symbol, 0, volume, feed);
            epService.EPRuntime.SendEvent(_event);
        }
    }
}