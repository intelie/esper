package net.esper.regression.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.SendableEvent;
import net.esper.adapter.Player.State;
import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVPlayer;
import net.esper.client.Configuration;
import net.esper.client.EPAdministrator;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
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
	private CSVAdapter adapter;
	private String eventTypeAlias;
	private EPServiceProvider epService;
	private long currentTime;
	private CSVPlayer player;
	private EPRuntime runtime;
	
	protected void setUp()
	{
		Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
		
		eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypes);
		configuration.addEventTypeAlias("myNonMapEvent", Class.class.getName());
		
		epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		epService.initialize();
		runtime = epService.getEPRuntime();
		EPAdministrator administrator = epService.getEPAdministrator();
		String statementText = "select * from mapEvent.win:length(5)";
		EPStatement statement = administrator.createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
		
		adapter = epService.getEPAdapters().getCSVAdapter();
		
    	// Turn off external clocking
    	epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    	
    	// Set the clock to 0
    	currentTime = 0;
    	sendTimeEvent(0);
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
		startPlayer(filename, -1, true);
		assertFalse(listener.getAndClearIsInvoked());
		assertEquals(State.STOPPED, player.getState());
	}
	
	public void testRunTitleRowOnly()
	{
		String filename = "regression/titleRowOnly.csv";
		startPlayer(filename, -1, true);
		assertFalse(listener.getAndClearIsInvoked());
		assertEquals(State.STOPPED, player.getState());
	}
	
	public void testRunDecreasingTimestamps()
	{
		String filename = "regression/decreasingTimestamps.csv";
		try
		{
			startPlayer(filename, -1, false);
		
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
			startPlayer(filename, -1, false);

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
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 200, 3, 3.3, "three"});
		events.add(new Object[] { 200, 5, 5.5, "five"});
		
		boolean isLooping = false;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}

	public void testStartOneRow()
	{
		String filename = "regression/oneRow.csv";
		startPlayer(filename, -1, false);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		assertEquals(State.STOPPED, player.getState());
	}
	
	public void testPause()
	{
		String filename = "regression/noTimestampOne.csv";
		startPlayer(filename, 10, false);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		
		player.pause();
		
		sendTimeEvent(100);
		assertEquals(State.PAUSED, player.getState());
		assertFalse(listener.getAndClearIsInvoked());
	}
	
	public void testResumeWholeInterval()
	{
		String filename = "regression/noTimestampOne.csv";
		startPlayer(filename, 10, false);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		
		player.pause();
		sendTimeEvent(100);
		assertFalse(listener.getAndClearIsInvoked());
		player.resume();
		
		
		assertEvent(2, 2.2, "two");
	}
	
	public void testResumePartialInterval()
	{
		String filename = "regression/noTimestampOne.csv";
		startPlayer(filename, 10, false);
		
		// time is 100
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		
		// time is 150
		sendTimeEvent(50);
		
		player.pause();
		// time is 200
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		player.resume();
		
		assertEvent(2, 2.2, "two");
	}
	
	public void testReadAndRun()
	{
		player = adapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource("regression/noTimestampOne.csv"));
		player.setIsLooping(true);
		player.setEventsPerSec(10);
		
		SendableEvent event = player.read();
		event.send(runtime);
		assertEvent(1, 1.1, "one");
		
		event = player.read();
		event.send(runtime);
		assertEvent(2, 2.2, "two");
		
		player.start();
		sendTimeEvent(300);
		assertEvent(3, 3.3, "three");
		
		event = player.read();
		event.send(runtime);
		assertEvent(1, 1.1, "one");
		
		sendTimeEvent(200);
		assertEvent(2, 2.2, "two");
		
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		event = player.read();
		event.send(runtime);
		assertEvent(3, 3.3, "three");
		
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
	}
	
	public void testReadAndPause()
	{
		String filename = "regression/noTimestampOne.csv";
		startPlayer(filename, 10, true);
		
		sendTimeEvent(150);
		assertEvent(1, 1.1, "one");
		
		player.pause();	
		
		SendableEvent event = player.read();
		event.send(runtime);
		assertEvent(2, 2.2, "two");
		
		player.resume();
		
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		sendTimeEvent(100);
		assertEvent(3, 3.3, "three");
	}
	
	public void testReadAfterStopped()
	{
		String filename = "regression/noTimestampOne.csv";
		startPlayer(filename, 10, true);
		player.stop();
		
		assertNull(player.read());
	}
	
	public void testReadTillStop()
	{
		String filename = "regression/noTimestampOne.csv";
		startPlayer(filename, 10, false);
		
		player.read();
		player.read();
		player.read();
		assertNull(player.read());
		assertEquals(State.STOPPED, player.getState());
	}
	
	public void testEventsPerSecInvalid()
	{
		String filename = "regression/timestampOne.csv";
		startPlayer(filename, -1, true);

		try
		{
			player.setEventsPerSec(0);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
		
		try
		{
			player.setEventsPerSec(-1);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
		
		try
		{
			player.setEventsPerSec(1001);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}
	}
	
	public void testEventsPerSecValid()
	{
		String filename = "regression/timestampOne.csv";
		startPlayer(filename, -1, true);
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		
		player.setEventsPerSec(10);
		
		// This was already scheduled using timestamps
		sendTimeEvent(200);
		assertEvent(3, 3.3, "three");
		
		player.setEventsPerSec(20);

		// This reacts to the first setting of eventsPerSec
		sendTimeEvent(100);
		assertEvent(5, 5.5, "five");
		
		// This reacts to the second setting of eventsPerSec
		sendTimeEvent(50);
		assertEvent(1, 1.1, "one");
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
		startPlayer(filename, eventsPerSec, isLooping);
		assertLoopingEvents(events);
	}
	
	public void testIsLoopingNoTitleRow()
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 200, 3, 3.3, "three"});
		events.add(new Object[] { 200, 5, 5.5, "five"});
		
		boolean isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);
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
		startPlayer(filename, eventsPerSec, isLooping);
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
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}
	
	public void testStop()
	{
		String filename = "regression/timestampOne.csv";
		startPlayer(filename, -1, false);
		player.stop();
		assertEquals(State.STOPPED, player.getState());
		assertNull(player.read());
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
		player.setIsLooping(false);
		assertFlatEvents(events);
		assertNull(player.read());
		assertEquals(State.STOPPED, player.getState());
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
	
	
	private void startPlayer(String filename, int eventsPerSec, boolean isLooping)
	{
		player = adapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource(filename));
		if(eventsPerSec != -1)
		{
			player.setEventsPerSec(eventsPerSec);
		}
		player.setIsLooping(isLooping);
		player.start();
	}

	private void assertFailedConstruction(String filename)
	{
		try
		{
			player = adapter.createCSVPlayer("myNonMapEvent", new AdapterInputSource(filename));
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
}
