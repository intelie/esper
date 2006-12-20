package net.esper.example.marketdatafeed;

import net.esper.example.support.SupportUpdateListener;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import junit.framework.TestCase;

import java.util.HashMap;

public class TestTicksPerSecondStatement extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("MarketDataEvent", MarketDataEvent.class.getName());

        epService = EPServiceProviderManager.getProvider("TestTicksPerSecondStatement", configuration);
        epService.initialize();

        listener = new SupportUpdateListener();
        TicksPerSecondStatement stmt = new TicksPerSecondStatement(epService.getEPAdministrator());
        stmt.addListener(listener);

        // Use external clocking for the test
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testFlow() {
        sendEvent(new CurrentTimeEvent(1000)); // Set the start time to 1 second

        sendEvent(new MarketDataEvent("CSC", FeedEnum.FEED_A));
        sendEvent(new MarketDataEvent("IBM", FeedEnum.FEED_A));
        sendEvent(new MarketDataEvent("GE", FeedEnum.FEED_A));
        sendEvent(new MarketDataEvent("MS", FeedEnum.FEED_B));
        assertFalse(listener.isInvoked());

        sendEvent(new CurrentTimeEvent(1500)); // Now events arriving around 1.5 sec
        sendEvent(new MarketDataEvent("TEL", FeedEnum.FEED_A));
        sendEvent(new MarketDataEvent("CSC", FeedEnum.FEED_B));
        assertFalse(listener.isInvoked());

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
        assertFalse(listener.isInvoked());

        sendEvent(new CurrentTimeEvent(3000));
        assertCounts(2, 5);

        sendEvent(new CurrentTimeEvent(3500));
        sendEvent(new MarketDataEvent("TEL", FeedEnum.FEED_A));
        sendEvent(new MarketDataEvent("IBM", FeedEnum.FEED_A));
        sendEvent(new MarketDataEvent("UUS", FeedEnum.FEED_A));
        assertFalse(listener.isInvoked());

        sendEvent(new CurrentTimeEvent(4000));
        sendEvent(new MarketDataEvent("NBOT", FeedEnum.FEED_B));
        sendEvent(new MarketDataEvent("YAH", FeedEnum.FEED_B));
        assertCounts(3, 0);

        sendEvent(new CurrentTimeEvent(4500));
        assertFalse(listener.isInvoked());

        sendEvent(new CurrentTimeEvent(5000));
        assertCounts(0, 2);
    }

    private void assertCounts(long countFeedA, long countFeedB)
    {
        HashMap<FeedEnum, Long> countPerFeed = new HashMap<FeedEnum, Long>();
        countPerFeed.put((FeedEnum)listener.getLastNewData()[0].get("feed"), (Long)listener.getLastNewData()[0].get("cnt"));
        countPerFeed.put((FeedEnum)listener.getLastNewData()[1].get("feed"), (Long)listener.getLastNewData()[1].get("cnt"));
        assertEquals(2, listener.getLastNewData().length);
        listener.reset();

        assertEquals(countFeedA, (long) countPerFeed.get(FeedEnum.FEED_A)); // casting to long to avoid JUnit ambiguous assert
        assertEquals(countFeedB, (long) countPerFeed.get(FeedEnum.FEED_B));
    }

    private void sendEvent(Object event) {
        epService.getEPRuntime().sendEvent(event);
    }
}

