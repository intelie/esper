using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.events;
using net.esper.support;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.example.marketdatafeed
{
	[TestFixture]
	public class TestTicksFalloffStatement
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;
	
	    [SetUp]
	    public void setUp() {
	        Configuration configuration = new Configuration();
	        configuration.AddEventTypeAlias("MarketDataEvent", typeof(MarketDataEvent).FullName);
	
	        epService = EPServiceProviderManager.GetProvider("TestTicksPerSecondStatement", configuration);
	        epService.Initialize();
	
	        new TicksPerSecondStatement(epService.EPAdministrator);
	        TicksFalloffStatement stmt = new TicksFalloffStatement(epService.EPAdministrator);
	        listener = new SupportUpdateListener();
	        stmt.AddListener(listener.Update);
	
	        // Use external clocking for the test
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }
	
	    [Test]
	    public void testFlow() {
	
	        sendEvents(1000, 50, 150); // Set time to 1 second, send 100 feed A and 150 feed B
	        sendEvents(1500, 50, 50);
	        sendEvents(2000, 60, 130);
	        sendEvents(2500, 40, 70);
	        sendEvents(3000, 50, 150);
	        sendEvents(3500, 50, 50);
	        sendEvents(4000, 50, 150);
	        sendEvents(4500, 50, 50);
	        sendEvents(5000, 50, 150);
	        sendEvents(5500, 50, 50);
	        sendEvents(6000, 50, 24);
	        Assert.IsFalse(listener.Invoked);
	        sendEvents(6500, 50, 50);
	        sendEvents(7000, 50, 150);
	        assertReceived(FeedEnum.FEED_B, (200 * 5 + 74) / 6, 74);
	        sendEvents(7500, 50, 50);
	        sendEvents(8000, 50, 150);
	        sendEvents(8500, 50, 50);
	        sendEvents(9000, 60, 150);
	        sendEvents(9500, 40, 50);
	        sendEvents(10000, 50, 150);
	        sendEvents(10500, 70, 50);
	        sendEvents(11000, 30, 150);
	        sendEvents(11500, 50, 50);
	        sendEvents(12000, 40, 150);
	        Assert.IsFalse(listener.Invoked);
	        sendEvents(12500, 30, 150);
	        sendEvents(13000, 50, 150);
	        assertReceived(FeedEnum.FEED_A, (100 * 9 + 70) / 10, 70);
	    }
	
	    private void assertReceived(FeedEnum feedEnum, double average, long count)
	    {
	        Assert.IsTrue(listener.Invoked);
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        EventBean eventBean = listener.LastNewData[0];
	        Assert.AreEqual(feedEnum, eventBean["Feed"]);
	        Assert.AreEqual(average, eventBean["AvgCnt"]);
	        Assert.AreEqual(count, eventBean["FeedCnt"]);
	        listener.reset();
	    }
	
	    private void sendEvents(long timestamp, int numFeedA, int numFeedB) {
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(timestamp));
	        send(FeedEnum.FEED_A, numFeedA);
	        send(FeedEnum.FEED_B, numFeedB);
	    }
	
	    private void send(FeedEnum feedEnum, int numEvents)
	    {
	        for (int i = 0; i < numEvents; i++)
	        {
	            epService.EPRuntime.SendEvent(new MarketDataEvent("CSC", feedEnum));
	        }
	    }
	}
}
