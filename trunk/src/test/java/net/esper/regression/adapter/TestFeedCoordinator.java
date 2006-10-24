package net.esper.regression.adapter;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.FeedCoordinator;
import net.esper.adapter.SendableEvent;
import net.esper.adapter.csv.CSVFeedSpec;
import net.esper.adapter.csv.SendableMapEvent;
import net.esper.client.Configuration;
import net.esper.client.EPAdministrator;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.schedule.ScheduleSlot;
import net.esper.support.adapter.SupportReadableFeed;
import net.esper.support.util.SupportUpdateListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFeedCoordinator extends TestCase
{
	private static final Log log = LogFactory.getLog(TestFeedCoordinator.class);
	
	private SupportUpdateListener listener;
	private String eventTypeAlias;
	private EPServiceProvider epService;
	private long currentTime;
	private FeedCoordinator coordinator;
	private SupportReadableFeed supportPlayer = new SupportReadableFeed();
	private CSVFeedSpec timestampsLooping;
	private CSVFeedSpec noTimestampsLooping;
	private CSVFeedSpec noTimestampsNotLooping;
	private CSVFeedSpec timestampsNotLooping;
	private String[] propertyOrderNoTimestamp;
	
	protected void setUp()
	{
		Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
		
		eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypes);
		
		epService = EPServiceProviderManager.getProvider("Adapter", configuration);
		epService.initialize();
		EPAdministrator administrator = epService.getEPAdministrator();
		String statementText = "select * from mapEvent.win:length(5)";
		EPStatement statement = administrator.createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
		
		// Turn off external clocking
		epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
		
		// Set the clock to 0
		currentTime = 0;
		sendTimeEvent(0);
		
		coordinator = epService.getEPAdapters().createFeedCoordinator(true);

    	propertyOrderNoTimestamp = new String[] { "myInt", "myDouble", "myString" };
    	String[] propertyOrderTimestamp = new String[] { "timestamp", "myInt", "myDouble", "myString" };
		
		// A CSVPlayer for a file with timestamps, not looping
		timestampsNotLooping = new CSVFeedSpec(new AdapterInputSource("/regression/timestampOne.csv"), eventTypeAlias);
		timestampsNotLooping.setUsingEngineThread(true);
		timestampsNotLooping.setPropertyOrder(propertyOrderTimestamp);
		timestampsNotLooping.setTimestampColumn("timestamp");
		
		// A CSVFeed for a file with timestamps, looping
		timestampsLooping = new CSVFeedSpec(new AdapterInputSource("/regression/timestampTwo.csv"), eventTypeAlias);
		timestampsLooping.setLooping(true);
		timestampsLooping.setUsingEngineThread(true);
		timestampsLooping.setPropertyOrder(propertyOrderTimestamp);
		timestampsLooping.setTimestampColumn("timestamp");
		
		// A CSVFeed that sends 10 events per sec, not looping
		noTimestampsNotLooping = new CSVFeedSpec(new AdapterInputSource("/regression/noTimestampOne.csv"), eventTypeAlias);
		noTimestampsNotLooping.setEventsPerSec(10);
		noTimestampsNotLooping.setPropertyOrder(propertyOrderNoTimestamp);
		noTimestampsNotLooping.setUsingEngineThread(true);
		
		// A CSVFeed that sends 5 events per sec, looping
		noTimestampsLooping = new CSVFeedSpec(new AdapterInputSource("/regression/noTimestampTwo.csv"), eventTypeAlias);
		noTimestampsLooping.setEventsPerSec(5);
		noTimestampsLooping.setLooping(true);
		noTimestampsLooping.setPropertyOrder(propertyOrderNoTimestamp);
		noTimestampsLooping.setUsingEngineThread(true);
	}
	
	public void testRun()
	{		
		coordinator.coordinate(timestampsNotLooping);
		coordinator.coordinate(timestampsLooping);
		coordinator.coordinate(noTimestampsNotLooping);
		coordinator.coordinate(noTimestampsLooping);
		
		// Time is 0
		assertFalse(listener.getAndClearIsInvoked());
		coordinator.start();
		
		// Time is 50
		sendTimeEvent(50);
		
		// Time is 100
		sendTimeEvent(50);
		assertEvent(0, 1, 1.1, "timestampOne.one");
		assertEvent(1, 1, 1.1, "noTimestampOne.one");
		assertSizeAndReset(2);
		
		// Time is 150
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		// Time is 200
		sendTimeEvent(50);
		assertEvent(0, 2, 2.2, "timestampTwo.two");
		assertEvent(1, 2, 2.2, "noTimestampOne.two");
		assertEvent(2, 2, 2.2, "noTimestampTwo.two");
		assertSizeAndReset(3);
		
		// Time is 250
		sendTimeEvent(50);
		
		// Time is 300	
		sendTimeEvent(50);
		assertEvent(0, 3, 3.3, "timestampOne.three");
		assertEvent(1, 3, 3.3, "noTimestampOne.three");
		assertSizeAndReset(2);
		
		// Time is 350
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		coordinator.pause();
		
		// Time is 400
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		// Time is 450
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		coordinator.resume();
		
		assertEvent(0, 4, 4.4, "timestampTwo.four");
		assertEvent(1, 4, 4.4, "noTimestampTwo.four");
		assertSizeAndReset(2);
		
		// Time is 500
		sendTimeEvent(50);
		assertEvent(0, 5, 5.5, "timestampOne.five");
		assertSizeAndReset(1);
		
		// Time is 600
		sendTimeEvent(100);
		assertEvent(0, 6, 6.6, "timestampTwo.six");
		assertEvent(1, 2, 2.2, "noTimestampTwo.two");
		assertSizeAndReset(2);
		
		// Time is 800
		sendTimeEvent(200);
		assertEvent(0, 2, 2.2, "timestampTwo.two");
		assertEvent(1, 4, 4.4, "noTimestampTwo.four");
		assertSizeAndReset(2);				
		
		coordinator.stop();
		sendTimeEvent(1000);
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testRunTillNull()
	{
		coordinator.coordinate(timestampsNotLooping);
		coordinator.start();
		
		// Time is 100
		sendTimeEvent(100);
		log.debug(".testRunTillNull time==100");
		assertEvent(0, 1, 1.1, "timestampOne.one");
		assertSizeAndReset(1);
		
		// Time is 300
		sendTimeEvent(200);
		log.debug(".testRunTillNull time==300");
		assertEvent(0, 3, 3.3, "timestampOne.three");
		assertSizeAndReset(1);
		
		// Time is 500
		sendTimeEvent(200);
		log.debug(".testRunTillNull time==500");
		assertEvent(0, 5, 5.5, "timestampOne.five");
		assertSizeAndReset(1);
		
		// Time is 600
		sendTimeEvent(100);
		log.debug(".testRunTillNull time==600");
		assertFalse(listener.getAndClearIsInvoked());
		
		setSupportEvent(800);
		
		// Time is 700
		sendTimeEvent(100);
		log.debug(".testRunTillNull time==700");
		assertFalse(listener.getAndClearIsInvoked());
		
		// Time is 800
		sendTimeEvent(100);
		log.debug(".testRunTillNull time==800");
	}
	
	public void testNotUsingEngineThread()
	{
		coordinator = epService.getEPAdapters().createFeedCoordinator(false);
		coordinator.coordinate(noTimestampsNotLooping);
		coordinator.coordinate(timestampsNotLooping);
		
		long startTime = System.currentTimeMillis();
		coordinator.start();
		long endTime = System.currentTimeMillis();

		// The last event should be sent after 500 ms
		assertTrue(endTime - startTime > 500);
		
		assertEquals(6, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "noTimestampOne.one");
		assertEvent(1, 1, 1.1, "timestampOne.one");
		assertEvent(2, 2, 2.2, "noTimestampOne.two");
		assertEvent(3, 3, 3.3, "noTimestampOne.three");
		assertEvent(4, 3, 3.3, "timestampOne.three");
		assertEvent(5, 5, 5.5, "timestampOne.five");
	}
	
	private void assertEvent(int howManyBack, Integer myInt, Double myDouble, String myString)
	{
		assertTrue(listener.isInvoked());
		assertTrue(howManyBack < listener.getNewDataList().size());
		EventBean[] data = listener.getNewDataList().get(howManyBack);
		assertEquals(1, data.length);
		EventBean event = data[0];
		assertEquals(myInt, event.get("myInt"));
		assertEquals(myDouble, event.get("myDouble"));
		assertEquals(myString, event.get("myString"));
	}
	

	private void sendTimeEvent(int timeIncrement){
		currentTime += timeIncrement;
	    CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
	    epService.getEPRuntime().sendEvent(event);                
	}
	
	private void setSupportEvent(long timestamp)
	{
		Map<String, Object> mapToSend = new HashMap<String, Object>();
		mapToSend.put("myInt", 0);
		mapToSend.put("myDouble", 0D);
		mapToSend.put("myString", "supportPlayer.zero");
		ScheduleSlot slot = new ScheduleSlot(10, 10);
		SendableEvent event = new SendableMapEvent(mapToSend, eventTypeAlias, timestamp, slot);
		supportPlayer.setEvent(event);
	}
	
	private void assertSizeAndReset(int size)
	{
		List<EventBean[]> list = listener.getNewDataList();
		assertEquals(size, list.size());
		list.clear();
		listener.getAndClearIsInvoked();
	}

}
