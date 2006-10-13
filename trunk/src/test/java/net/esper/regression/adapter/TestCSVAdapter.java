package net.esper.regression.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.EPAdapterManager;
import net.esper.adapter.Feed;
import net.esper.adapter.FeedState;
import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVFeedSpec;
import net.esper.client.Configuration;
import net.esper.client.EPAdministrator;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.util.SupportUpdateListener;

public class TestCSVAdapter extends TestCase
{
	private SupportUpdateListener listener;
	private EPAdapterManager adapter;
	private CSVAdapter csvAdapter;
	private String eventTypeAlias;
	private EPServiceProvider epService;
	private long currentTime;
	private Feed feed;
	private String[] propertyOrder;
	
	protected void setUp()
	{
		Map<String, Class> propertyTypes = new HashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
		
		eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypes);
		configuration.addEventTypeAlias("myNonMapEvent", Class.class.getName());
		
		epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		epService.initialize();
		EPAdministrator administrator = epService.getEPAdministrator();
		String statementText = "select * from mapEvent.win:length(5)";
		EPStatement statement = administrator.createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
		
		adapter = epService.getEPAdapters();
		csvAdapter = adapter.getCSVAdapter();
		
    	// Turn off external clocking
    	epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    	
    	// Set the clock to 0
    	currentTime = 0;
    	sendTimeEvent(0);
    	
    	propertyOrder = new String[] { "myInt", "myDouble", "myString" };
	}
	
	public void testRunWrongAlias()
	{
		String filename = "regression/noTimestampOne.csv";
		assertFailedConstruction(filename);
	}

	public void testRunWrongMapType()
	{
		String filename = "regression/differentMap.csv";
		assertFailedConstruction(filename);
	}

	public void testRunNonexistentFile()
	{
		String filename = "someNonexistentFile";	
		assertFailedConstruction(filename);
	}

	public void testRunEmptyFile()
	{
		String filename = "regression/emptyFile.csv";
		startFeed(filename, -1, true);
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testRunTitleRowOnly()
	{
		String filename = "regression/titleRowOnly.csv";
		propertyOrder = null;
		startFeed(filename, -1, true);
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testRunDecreasingTimestamps()
	{
		String filename = "regression/decreasingTimestamps.csv";
		try
		{
			startFeed(filename, -1, false);
		
			sendTimeEvent(100);
			assertEvent(1, 1.1, "one");
		
			sendTimeEvent(200);
			fail();
		}
		catch(EPException e)
		{
			// Expected  
		}
	}
	
	public void testRunNegativeTimestamps()
	{
		String filename = "regression/negativeTimestamps.csv";
		try
		{
			startFeed(filename, -1, false);

			sendTimeEvent(100);
			assertEvent(1, 1.1, "one");

			sendTimeEvent(200);
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
	public void testRunNoTimestampColumn()
	{
		String filename = "regression/noTimestampOne.csv";
		assertFailedConstruction(filename);
	}
	
	public void testRunTimestamps() 
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "timestampOne.one"});
		events.add(new Object[] { 200, 3, 3.3, "timestampOne.three"});
		events.add(new Object[] { 200, 5, 5.5, "timestampOne.five"});
		
		boolean isLooping = false;
		startFeed(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startFeed(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}

	public void testStartOneRow()
	{
		String filename = "regression/oneRow.csv";
		startFeed(filename, -1, false);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
	}
	
	public void testPause()
	{
		String filename = "regression/noTimestampOne.csv";
		startFeed(filename, 10, false);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");
		
		feed.pause();
		
		sendTimeEvent(100);
		assertEquals(FeedState.PAUSED, feed.getState());
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testResumeWholeInterval()
	{
		String filename = "regression/noTimestampOne.csv";
		startFeed(filename, 10, false);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");
		
		feed.pause();
		sendTimeEvent(100);
		assertFalse(listener.getAndClearIsInvoked());
		feed.resume();
		
		
		assertEvent(2, 2.2, "noTimestampOne.two");
	}
	
	public void testResumePartialInterval()
	{
		String filename = "regression/noTimestampOne.csv";
		startFeed(filename, 10, false);
		
		// time is 100
		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");
		
		// time is 150
		sendTimeEvent(50);
		
		feed.pause();
		// time is 200
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		feed.resume();
		
		assertEvent(2, 2.2, "noTimestampOne.two");
	}
	
	public void testEventsPerSecInvalid()
	{
		String filename = "regression/timestampOne.csv";
		
		try
		{
			startFeed(filename, 0, true);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
		
		try
		{
			startFeed(filename, 1001, true);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
	}
	
	public void testIsLoopingTitleRow()
	{
		String filename =  "regression/titleRow.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 200, 3, 3.3, "three"});
		events.add(new Object[] { 200, 5, 5.5, "five"});
		
		boolean isLooping = true;
		propertyOrder = null;
		startFeed(filename, eventsPerSec, isLooping);
		assertLoopingEvents(events);
	}
	
	public void testIsLoopingNoTitleRow()
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "timestampOne.one"});
		events.add(new Object[] { 200, 3, 3.3, "timestampOne.three"});
		events.add(new Object[] { 200, 5, 5.5, "timestampOne.five"});
		
		boolean isLooping = true;
		startFeed(filename, eventsPerSec, isLooping);
		assertLoopingEvents(events);
	}
	
	public void testTitleRowNoTimestamp()
	{
		String filename =  "regression/titleRowNoTimestamp.csv";
		int eventsPerSec = 10;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 100, 3, 3.3, "three"});
		events.add(new Object[] { 100, 5, 5.5, "five"});
		
		boolean isLooping = true;
		propertyOrder = null;
		startFeed(filename, eventsPerSec, isLooping);
		assertLoopingEvents(events);
	}
	
	public void testComments()
	{
		String filename =  "regression/comments.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 200, 3, 3.3, "three"});
		events.add(new Object[] { 200, 5, 5.5, "five"});
		
		boolean isLooping = false;
		startFeed(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startFeed(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}
	
	public void testDestroy()
	{
		String filename = "regression/timestampOne.csv";
		startFeed(filename, -1, false);
		feed.destroy();
		assertEquals(FeedState.DESTROYED, feed.getState());
	}
	
	public void testStop()
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "timestampOne.one"});
		events.add(new Object[] { 200, 3, 3.3, "timestampOne.three"});
		
		boolean isLooping = false;
		startFeed(filename, eventsPerSec, isLooping);
		
		assertFlatEvents(events);
		
		feed.stop();
		
		sendTimeEvent(1000);
		assertFalse(listener.getAndClearIsInvoked());
		
		feed.start();
		assertFlatEvents(events);
	}

	private void sendTimeEvent(int timeIncrement){
		currentTime += timeIncrement;
	    CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
	    epService.getEPRuntime().sendEvent(event);                
	}



	private void assertEvents(boolean isLooping, List<Object[]> events)
	{
		if(isLooping)
		{
			assertLoopingEvents(events);
		}
		else
		{
			assertNonLoopingEvents(events);
		}
	}



	private void assertEvent(Object[] properties)
	{
		if(properties.length == 1)
		{
			assertFalse(listener.getAndClearIsInvoked());
		}
		else if(properties.length == 4)
		{
			// properties = [callbackDelay, myInt, myDouble, myString]
			assertEvent((Integer)properties[1], (Double)properties[2], (String)properties[3]);
		}
		else
		{
			// properties = [callbackDelay, intOne, doubleOne, StringOne, intTwo, doubleTwo, stringTwo]
			assertTwoEvents((Integer)properties[1], (Double)properties[2], (String)properties[3], (Integer)properties[4], (Double)properties[5], (String)properties[6]);
		}
	}
	
	private void assertEvent(Integer myInt, Double myDouble, String myString)
	{
		assertTrue(listener.getAndClearIsInvoked());
		assertEquals(1, listener.getLastNewData().length);
		EventBean event = listener.getLastNewData()[0];
		assertEquals(myInt, event.get("myInt"));
		assertEquals(myDouble, event.get("myDouble"));
		assertEquals(myString, event.get("myString"));
	}
	
	private void assertTwoEvents(Integer intOne, Double doubleOne, String stringOne,
								 Integer intTwo, Double doubleTwo, String stringTwo)
	{
		assertTrue(listener.isInvoked());
		assertEquals(2, listener.getNewDataList().size());
		
		assertEquals(1, listener.getNewDataList().get(0).length);
		EventBean event = listener.getNewDataList().get(0)[0];
		assertEquals(intOne, event.get("myInt"));
		assertEquals(doubleOne, event.get("myDouble"));
		assertEquals(stringOne, event.get("myString"));
		
		assertEquals(1, listener.getNewDataList().get(1).length);
		event = listener.getNewDataList().get(1)[0];
		assertEquals(intTwo, event.get("myInt"));
		assertEquals(doubleTwo, event.get("myDouble"));
		assertEquals(stringTwo, event.get("myString"));
	}



	private void assertNonLoopingEvents(List<Object[]> events)
	{
		assertFlatEvents(events);
		
		sendTimeEvent(1000);
		assertEvent(new Object[] { 1000 });
	}



	private void assertLoopingEvents(List<Object[]> events)
	{
		assertFlatEvents(events);
		assertFlatEvents(events);
		assertFlatEvents(events);
	}



	private void assertFlatEvents(List<Object[]> events)
	{
		for(Object[] event : events)
		{
			sendTimeEvent((Integer)event[0]);
			assertEvent(event);
			listener.reset();
		}
	}
	
	
	private void startFeed(String filename, int eventsPerSec, boolean isLooping)
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(new AdapterInputSource(filename), eventTypeAlias);
		if(eventsPerSec != -1)
		{
			feedSpec.setEventsPerSec(eventsPerSec);
		}
		feedSpec.setIsLooping(isLooping);
		feedSpec.setPropertyOrder(propertyOrder);
		
		feed = adapter.createFeed(feedSpec);
		feed.start();
	}

	private void assertFailedConstruction(String filename)
	{
		try
		{
			csvAdapter.startFeed(new AdapterInputSource(filename), "myNonMapEvent");
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
}
