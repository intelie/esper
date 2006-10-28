package net.esper.regression.adapter;

import java.io.InputStream;
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
	private String[] propertyOrderTimestamps;
	private String[] propertyOrderNoTimestamps;
	private Map<String, Class> propertyTypes;
	
	protected void setUp()
	{
		propertyTypes = new HashMap<String, Class>();
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
    	
    	propertyOrderNoTimestamps = new String[] { "myInt", "myDouble", "myString" };
    	propertyOrderTimestamps = new String[] { "timestamp", "myInt", "myDouble", "myString" };
	}
	
	public void testConvenience()
	{
		String filename = "regression/titleRow.csv";
		csvAdapter.startFeed(new AdapterInputSource(filename), eventTypeAlias);
		
		assertEquals(3, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "one");
		assertEvent(1, 3, 3.3, "three");
		assertEvent(2, 5, 5.5, "five");
	}
	
	public void testInputStream()
	{
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("regression/noTimestampOne.csv");
		CSVFeedSpec feedSpec  = new CSVFeedSpec(new AdapterInputSource(stream), eventTypeAlias);
		feedSpec.setPropertyOrder(propertyOrderNoTimestamps);

		adapter.createFeed(feedSpec);
		
		feedSpec.setLooping(true);
		try
		{
			adapter.createFeed(feedSpec);
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
	public void testFewerPropertiesToSend()
	{
		String filename =  "regression/moreProperties.csv";
		int eventsPerSec = 10;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "moreProperties.one" });
		events.add(new Object[] { 100, 2, 2.2, "moreProperties.two" });
		events.add(new Object[] { 100, 3, 3.3, "moreProperties.three" });
		String[] propertyOrder = new String[] { "someString", "myInt", "someInt", "myDouble", "myString" };
		
		boolean isLooping = false;
		startFeed(filename, eventsPerSec, isLooping, true, null, propertyOrder);
		assertEvents(isLooping, events);
	}
	
	public void testConflictingPropertyOrder()
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(new AdapterInputSource("regression/intsTitleRow.csv"), "intsTitleRowEvent");
		feedSpec.setEventsPerSec(10);
		feedSpec.setPropertyOrder(new String[] { "intTwo", "intOne" });
		feedSpec.setUsingEngineThread(true);
		feed = adapter.createFeed(feedSpec);
		
		String statementText = "select * from intsTitleRowEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		statement.addListener(listener);
		
		feed.start();
		
		sendTimeEvent(100);
		
		assertTrue(listener.getAndClearIsInvoked());
		assertEquals(1, listener.getLastNewData().length);
		assertEquals("1", listener.getLastNewData()[0].get("intTwo"));
		assertEquals("0", listener.getLastNewData()[0].get("intOne"));
	}
	
	public void testEventsPerSecAndTimestamp()
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = 5;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 200, 1, 1.1, "timestampOne.one"});
		events.add(new Object[] { 200, 3, 3.3, "timestampOne.three"});
		events.add(new Object[] { 200, 5, 5.5, "timestampOne.five"});
		
		boolean isLooping = false;
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
	}
	
	public void testNoTimestampNoEventsPerSec()
	{
		String filename = "regression/timestampOne.csv";
		
		startFeed(filename, -1, false, true, null, propertyOrderTimestamps);
	
		assertEquals(3, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "timestampOne.one");
		assertEvent(1, 3, 3.3, "timestampOne.three");
		assertEvent(2, 5, 5.5, "timestampOne.five");
	}
	
	public void testNoPropertyTypes()
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "allStringEvent");
		feedSpec.setEventsPerSec(10);
		feedSpec.setPropertyOrder(new String[] { "myInt", "myDouble", "myString" });
		feedSpec.setUsingEngineThread(true);
		feed = adapter.createFeed(feedSpec);
		
		String statementText = "select * from allStringEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		statement.addListener(listener);
		
		feed.start();
		
		sendTimeEvent(100);
		assertEvent("1", "1.1", "noTimestampOne.one");
		
		sendTimeEvent(100);
		assertEvent("2", "2.2", "noTimestampOne.two");
		
		sendTimeEvent(100);
		assertEvent("3", "3.3", "noTimestampOne.three");
	}
	
	public void testRuntimePropertyTypes()
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "propertyTypeEvent");
		feedSpec.setEventsPerSec(10);
		feedSpec.setPropertyOrder(new String[] { "myInt", "myDouble", "myString" });
		feedSpec.setPropertyTypes(propertyTypes);
		feedSpec.setUsingEngineThread(true);
		feed = adapter.createFeed(feedSpec);	
		
		String statementText = "select * from propertyTypeEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		statement.addListener(listener);
		
		feed.start();
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");
		
		sendTimeEvent(100);
		assertEvent(2, 2.2, "noTimestampOne.two");
		
		sendTimeEvent(100);
		assertEvent(3, 3.3, "noTimestampOne.three");
	}
	
	public void testRuntimePropertyTypesInvalid()
	{
		Map<String, Class> propertyTypesInvalid = new HashMap<String, Class>(propertyTypes);
		propertyTypesInvalid.put("anotherProperty", String.class);
		try
		{
			csvAdapter.startFeed(new AdapterInputSource("regression/noTimestampOne.csv"), "mapEvent", propertyTypesInvalid);
			fail();
		}
		catch(EPException er)
		{
			// Expected
		}
		
		propertyTypesInvalid = new HashMap<String, Class>(propertyTypes);
		propertyTypesInvalid.put("myInt", String.class);
		try
		{
			csvAdapter.startFeed(new AdapterInputSource("regression/noTimestampOne.csv"), "mapEvent", propertyTypesInvalid);
			fail();
		}
		catch(EPException er)
		{
			// Expected
		}
		
		propertyTypesInvalid = new HashMap<String, Class>(propertyTypes);
		propertyTypesInvalid.remove("myInt");
		propertyTypesInvalid.put("anotherInt", Integer.class);
		try
		{
			csvAdapter.startFeed(new AdapterInputSource("regression/noTimestampOne.csv"), "mapEvent", propertyTypesInvalid);
			fail();
		}
		catch(EPException er)
		{
			// Expected
		}
	}
	
	public void testRunWrongAlias()
	{
		String filename = "regression/noTimestampOne.csv";
		assertFailedConstruction(filename, "myNonMapEvent");
	}

	public void testRunWrongMapType()
	{
		String filename = "regression/differentMap.csv";
		assertFailedConstruction(filename, eventTypeAlias);
	}

	public void testRunNonexistentFile()
	{
		String filename = "someNonexistentFile";	
		assertFailedConstruction(filename, eventTypeAlias);
	}

	public void testRunEmptyFile()
	{
		String filename = "regression/emptyFile.csv";
		startFeed(filename, -1, true, true, null, propertyOrderTimestamps);
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testRunTitleRowOnly()
	{
		String filename = "regression/titleRowOnly.csv";
		propertyOrderNoTimestamps = null;
		startFeed(filename, -1, true, true, "timestamp", null);
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testRunDecreasingTimestamps()
	{
		String filename = "regression/decreasingTimestamps.csv";
		try
		{
			startFeed(filename, -1, false, true, null, null);
		
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
			startFeed(filename, -1, false, true, null, null);

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
	
	public void testRunTimestamps() 
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "timestampOne.one"});
		events.add(new Object[] { 200, 3, 3.3, "timestampOne.three"});
		events.add(new Object[] { 200, 5, 5.5, "timestampOne.five"});
		
		boolean isLooping = false;
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
	}

	public void testStartOneRow()
	{
		String filename = "regression/oneRow.csv";
		startFeed(filename, -1, false, true, "timestamp", propertyOrderTimestamps);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
	}
	
	public void testPause()
	{
		String filename = "regression/noTimestampOne.csv";
		startFeed(filename, 10, false, true, "timestamp", propertyOrderNoTimestamps);
		
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
		startFeed(filename, 10, false, true, null, propertyOrderNoTimestamps);
		
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
		startFeed(filename, 10, false, true, null, propertyOrderNoTimestamps);
		
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
			startFeed(filename, 0, true, true, null, null);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
		
		try
		{
			startFeed(filename, 1001, true, true, null, null);
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
		propertyOrderNoTimestamps = null;
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", null);
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
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
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
		propertyOrderNoTimestamps = null;
		startFeed(filename, eventsPerSec, isLooping, true, null, null);
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
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
	}
	
	public void testDestroy()
	{
		String filename = "regression/timestampOne.csv";
		startFeed(filename, -1, false, true, "timestamp", propertyOrderTimestamps);
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
		startFeed(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		
		assertFlatEvents(events);
		
		feed.stop();
		
		sendTimeEvent(1000);
		assertFalse(listener.getAndClearIsInvoked());
		
		feed.start();
		assertFlatEvents(events);
	}
	
	public void testStopAfterEOF()
	{
		String filename =  "regression/timestampOne.csv";
		startFeed(filename, -1, false, false, "timestamp", propertyOrderTimestamps);
		assertEquals(FeedState.OPENED, feed.getState());
	}
	
	public void testNotUsingEngineThreadTimestamp()
	{
		String filename = "regression/timestampOne.csv";
		
		long startTime = System.currentTimeMillis();
		startFeed(filename, -1, false, false, "timestamp", propertyOrderTimestamps);
		long endTime = System.currentTimeMillis();
	
		// The last event should be sent after 500 ms
		assertTrue(endTime - startTime > 500);
		
		assertEquals(3, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "timestampOne.one");
		assertEvent(1, 3, 3.3, "timestampOne.three");
		assertEvent(2, 5, 5.5, "timestampOne.five");
	}
	
	public void testNotUsingEngineThreaNoTimestamp()
	{
		String filename = "regression/noTimestampOne.csv";
		
		long startTime = System.currentTimeMillis();
		startFeed(filename, 5, false, false, null, propertyOrderNoTimestamps);
		long endTime = System.currentTimeMillis();
	
		// The last event should be sent after 600 ms
		assertTrue(endTime - startTime > 600);
		
		assertEquals(3, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "noTimestampOne.one");
		assertEvent(1, 2, 2.2, "noTimestampOne.two");
		assertEvent(2, 3, 3.3, "noTimestampOne.three");
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
	
	private void assertEvent(Object myInt, Object myDouble, Object myString)
	{
		assertTrue(listener.getAndClearIsInvoked());
		assertEquals(1, listener.getLastNewData().length);
		EventBean event = listener.getLastNewData()[0];
		assertEquals(myInt, event.get("myInt"));
		assertEquals(myDouble, event.get("myDouble"));
		assertEquals(myString, event.get("myString"));
		listener.reset();
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
	
	private void startFeed(String filename, int eventsPerSec, boolean isLooping, boolean usingEngineThread, String timestampColumn, String[] propertyOrder)
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(new AdapterInputSource(filename), eventTypeAlias);
		if(eventsPerSec != -1)
		{
			feedSpec.setEventsPerSec(eventsPerSec);
		}
		feedSpec.setLooping(isLooping);
		feedSpec.setPropertyOrder(propertyOrder);	
		feedSpec.setUsingEngineThread(usingEngineThread);
		feedSpec.setTimestampColumn(timestampColumn);
		
		feed = adapter.createFeed(feedSpec);
		feed.start();
	}

	private void assertFailedConstruction(String filename, String eventTypeAlias)
	{
		try
		{
			csvAdapter.startFeed(new AdapterInputSource(filename), eventTypeAlias);
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
}
