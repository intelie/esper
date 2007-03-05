using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.support;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.example.marketdatafeed
{
	[TestFixture]
	public class TestTicksPerSecondStatement
	{
		private EPServiceProvider epService;
	    private SupportUpdateListener listener;
	
	    [SetUp]
	    public void setUp() {
	        Configuration configuration = new Configuration();
	        configuration.AddEventTypeAlias("MarketDataEvent", typeof(MarketDataEvent).FullName);
	
	        epService = EPServiceProviderManager.GetProvider("TestTicksPerSecondStatement", configuration);
	        epService.Initialize();
	
	        listener = new SupportUpdateListener();
	        TicksPerSecondStatement stmt = new TicksPerSecondStatement(epService.EPAdministrator);
	        stmt.AddListener(listener.Update);
	
	        // Use external clocking for the test
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }
	
	    [Test]
	    public void testFlow() {
	        sendEvent(new CurrentTimeEvent(1000)); // Set the start time to 1 second
	
	        sendEvent(new MarketDataEvent("CSC", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("IBM", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("GE", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("MS", FeedEnum.FEED_B));
	        Assert.IsFalse(listener.Invoked);
	
	        sendEvent(new CurrentTimeEvent(1500)); // Now events arriving around 1.5 sec
	        sendEvent(new MarketDataEvent("TEL", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("CSC", FeedEnum.FEED_B));
	        Assert.IsFalse(listener.Invoked);
	
	        sendEvent(new CurrentTimeEvent(2000)); // Now events arriving around 2 sec
	        sendEvent(new MarketDataEvent("TEL", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("IBM", FeedEnum.FEED_B));
	        sendEvent(new MarketDataEvent("GE", FeedEnum.FEED_B));
	        sendEvent(new MarketDataEvent("IOU", FeedEnum.FEED_B));
	        assertCounts(4, 2);
	
	        sendEvent(new CurrentTimeEvent(2500)); // Now events arriving around 2.5 sec
	        sendEvent(new MarketDataEvent("TEL", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("GE", FeedEnum.FEED_B));
	        sendEvent(new MarketDataEvent("MS", FeedEnum.FEED_B));
	        Assert.IsFalse(listener.Invoked);
	
	        sendEvent(new CurrentTimeEvent(3000));
	        assertCounts(2, 5);
	
	        sendEvent(new CurrentTimeEvent(3500));
	        sendEvent(new MarketDataEvent("TEL", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("IBM", FeedEnum.FEED_A));
	        sendEvent(new MarketDataEvent("UUS", FeedEnum.FEED_A));
	        Assert.IsFalse(listener.Invoked);
	
	        sendEvent(new CurrentTimeEvent(4000));
	        sendEvent(new MarketDataEvent("NBOT", FeedEnum.FEED_B));
	        sendEvent(new MarketDataEvent("YAH", FeedEnum.FEED_B));
	        assertCounts(3, 0);
	
	        sendEvent(new CurrentTimeEvent(4500));
	        Assert.IsFalse(listener.Invoked);
	
	        sendEvent(new CurrentTimeEvent(5000));
	        assertCounts(0, 2);
	    }
	
	    private void assertCounts(long countFeedA, long countFeedB)
	    {
	    	EDictionary<FeedEnum, long> countPerFeed = new EHashDictionary<FeedEnum, long>();
	        countPerFeed.Put((FeedEnum)listener.LastNewData[0]["feed"], (long)listener.LastNewData[0]["cnt"]);
	        countPerFeed.Put((FeedEnum)listener.LastNewData[1]["feed"], (long)listener.LastNewData[1]["cnt"]);
	        Assert.AreEqual(2, listener.LastNewData.Length);
	        listener.reset();
	
	        Assert.AreEqual(countFeedA, (long) countPerFeed.Fetch(FeedEnum.FEED_A)); // casting to long to avoid JUnit ambiguous assert
	        Assert.AreEqual(countFeedB, (long) countPerFeed.Fetch(FeedEnum.FEED_B));
	    }
	
	    private void sendEvent(Object eventBean) {
	        epService.EPRuntime.SendEvent(eventBean);
	    }
	}
}
