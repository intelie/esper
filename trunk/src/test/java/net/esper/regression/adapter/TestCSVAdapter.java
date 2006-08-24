package net.esper.regression.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Conductor;
import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVPlayer;
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
	private CSVAdapter adapter;
	private String eventTypeAlias;
	private EPServiceProvider epService;
	private long currentTime;
	private CSVPlayer player;
	private Conductor conductor;
	
	protected void setUp()
	{
		Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
		
		eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypes);
		
		epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		epService.initialize();
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
	

	
	public void testSingleFileNoTimestamp() throws InterruptedException 
	{
		String filename = "regression/noTimestampOne.csv";
		int eventsPerSec = 10;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 100, 2, 2.2, "two"});
		events.add(new Object[] { 100, 3, 3.3, "three"});
		
		boolean isLooping = false;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}
	
	public void testSingleFileTimestamp() throws InterruptedException
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 300, 3, 3.3, "three"});
		events.add(new Object[] { 500, 5, 5.5, "five"});
		
		boolean isLooping = false;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}



	public void testTitleRow() throws InterruptedException
	{
		String filename = "regression/titleRow.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 300, 3, 3.3, "three"});
		events.add(new Object[] { 500, 5, 5.5, "five"});
	
		boolean isLooping = false;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
		
		isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);
		assertEvents(isLooping, events);
	}
	
	public void testMultipleFilesTimestamp() throws InterruptedException
	{
		String filenameOne = "regression/timestampOne.csv";
		String filenameTwo = "regression/timestampTwo.csv";
		int eventsPerSec = -1;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 100, 2, 2.2, "two"});
		events.add(new Object[] { 100, 3, 3.3, "three"});
		events.add(new Object[] { 100, 4, 4.4, "four"});
		events.add(new Object[] { 100, 5, 5.5, "five"});
		events.add(new Object[] { 100, 6, 6.6, "six"});
		
		boolean isLooping = false;
		startGroup(new String[] { filenameOne, filenameTwo }, new int[] { eventsPerSec, eventsPerSec}, new boolean[] { isLooping, isLooping });
		assertEvents(isLooping, events);
		
		events.remove(5);
		events.add(new Object[] { 100, 1, 1.1, "one", 6, 6.6, "six" });
		events.add(new Object[] { 200, 3, 3.3, "three", 2, 2.2, "two" });
		
		isLooping = true;
		startGroup(new String[] { filenameOne, filenameTwo }, new int[] { eventsPerSec, eventsPerSec}, new boolean[] { isLooping, isLooping });
		assertFlatEvents(events);
	}
	
	public void testMultipleFilesMixed() throws InterruptedException
	{	
		String filenameOne = "regression/timestampOne.csv";
		String filenameTwo = "regression/noTimestampTwo.csv";
		int eventsPerSecOne = -1;
		int eventsPerSecTwo = 5;
		
		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "one"});
		events.add(new Object[] { 100, 2, 2.2, "two"});
		events.add(new Object[] { 100, 3, 3.3, "three"});
		events.add(new Object[] { 100, 4, 4.4, "four"});
		events.add(new Object[] { 100, 5, 5.5, "five"});
		events.add(new Object[] { 100, 2, 2.2, "two"});
		events.add(new Object[] { 200, 4, 4.4, "four"});
		
		boolean isLoopingOne = false;
		boolean isLoopingTwo = true;
		startGroup(new String[] { filenameOne, filenameTwo }, new int[] { eventsPerSecOne, eventsPerSecTwo}, new boolean[] { isLoopingOne, isLoopingTwo });
		assertFlatEvents(events);
	}
	
	public void testStates()
	{
		String filename = "regression/noTimestampOne.csv";
		int eventsPerSec = 10;
		boolean isLooping = true;
		startPlayer(filename, eventsPerSec, isLooping);

		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		
		sendTimeEvent(100);
		assertEvent(2, 2.2, "two");
		
		player.pause();
		
		sendTimeEvent(100);
		assertFalse(listener.getAndClearIsInvoked());
		
		player.resume();
		
		sendTimeEvent(100);
		assertEvent(3, 3.3, "three");
		
		player.pause();
		
		sendTimeEvent(100);
		assertFalse(listener.getAndClearIsInvoked());
		
		player.resume();
		
		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
		
		player.stop();
		
		sendTimeEvent(1000);
		assertFalse(listener.getAndClearIsInvoked());
		
		try
		{
			player.start();
			fail();
		}
		catch(EPException e)
		{
			// Expected
		}
		
		try
		{
			player.pause();
			fail();
		}
		catch(EPException e)
		{
			// Expected
		}
		
		try
		{
			player.resume();
			fail();
		}
		catch(EPException e)
		{
			// Expected
		}
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
	
	private void startGroup(String[] filenames, int eventsPerSecArray[], boolean[] isLoopingArray)
	{
		conductor = epService.getEPAdapters().createConductor();
		int count = 0;
		for(String filename : filenames)
		{
			CSVPlayer player = adapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource(filename));
			if(eventsPerSecArray[count] != -1)
			{
				player.setEventsPerSec(eventsPerSecArray[count]);
			}
			player.setIsLooping(isLoopingArray[count]);
			conductor.add(player);
			count++;
		}
		conductor.start();
	}
	
}
