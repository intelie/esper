package com.espertech.esper.regression.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import com.espertech.esper.adapter.AdapterInputSource;
import com.espertech.esper.adapter.AdapterState;
import com.espertech.esper.adapter.InputAdapter;
import com.espertech.esper.adapter.csv.CSVInputAdapter;
import com.espertech.esper.adapter.csv.CSVInputAdapterSpec;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestCSVAdapter extends TestCase
{
	private SupportUpdateListener listener;
	private String eventTypeAlias;
	private EPServiceProvider epService;
	private long currentTime;
	private InputAdapter adapter;
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

    	// Turn off external clocking
    	epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

    	// Set the clock to 0
    	currentTime = 0;
    	sendTimeEvent(0);

    	propertyOrderNoTimestamps = new String[] { "myInt", "myDouble", "myString" };
    	propertyOrderTimestamps = new String[] { "timestamp", "myInt", "myDouble", "myString" };
	}

	public void testNullEPService()
	{
		CSVInputAdapter adapter = new CSVInputAdapter(null, new AdapterInputSource("regression/titleRow.csv"), eventTypeAlias);
		runNullEPService(adapter);

		listener.reset();

		adapter = new CSVInputAdapter(new AdapterInputSource("regression/titleRow.csv"), eventTypeAlias);
		runNullEPService(adapter);
	}

	public void testInputStream()
	{
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("regression/noTimestampOne.csv");
		CSVInputAdapterSpec adapterSpec  = new CSVInputAdapterSpec(new AdapterInputSource(stream), eventTypeAlias);
		adapterSpec.setPropertyOrder(propertyOrderNoTimestamps);

		new CSVInputAdapter(epService, adapterSpec);

		adapterSpec.setLooping(true);
		try
		{
			new CSVInputAdapter(epService, adapterSpec);
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
		startAdapter(filename, eventsPerSec, isLooping, true, null, propertyOrder);
		assertEvents(isLooping, events);
	}

	public void testConflictingPropertyOrder()
	{
		CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource("regression/intsTitleRow.csv"), "intsTitleRowEvent");
		adapterSpec.setEventsPerSec(10);
		adapterSpec.setPropertyOrder(new String[] { "intTwo", "intOne" });
		adapterSpec.setUsingEngineThread(true);
		adapter = new CSVInputAdapter(epService, adapterSpec);

		String statementText = "select * from intsTitleRowEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		statement.addListener(listener);

		adapter.start();

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
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
	}

	public void testNoTimestampNoEventsPerSec()
	{
		String filename = "regression/timestampOne.csv";

		startAdapter(filename, -1, false, true, null, propertyOrderTimestamps);

		assertEquals(3, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "timestampOne.one");
		assertEvent(1, 3, 3.3, "timestampOne.three");
		assertEvent(2, 5, 5.5, "timestampOne.five");
	}

	public void testNoPropertyTypes()
	{
		CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "allStringEvent");
		adapterSpec.setEventsPerSec(10);
		adapterSpec.setPropertyOrder(new String[] { "myInt", "myDouble", "myString" });
		adapterSpec.setUsingEngineThread(true);
		adapter = new CSVInputAdapter(epService, adapterSpec);

		String statementText = "select * from allStringEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		statement.addListener(listener);

		adapter.start();

		sendTimeEvent(100);
		assertEvent("1", "1.1", "noTimestampOne.one");

		sendTimeEvent(100);
		assertEvent("2", "2.2", "noTimestampOne.two");

		sendTimeEvent(100);
		assertEvent("3", "3.3", "noTimestampOne.three");
	}

	public void testRuntimePropertyTypes()
	{
		CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "propertyTypeEvent");
		adapterSpec.setEventsPerSec(10);
		adapterSpec.setPropertyOrder(new String[] { "myInt", "myDouble", "myString" });
		adapterSpec.setPropertyTypes(propertyTypes);
		adapterSpec.setUsingEngineThread(true);
		adapter = new CSVInputAdapter(epService, adapterSpec);

		String statementText = "select * from propertyTypeEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		statement.addListener(listener);

		adapter.start();

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
			CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "mapEvent");
			adapterSpec.setPropertyTypes(propertyTypesInvalid);
			(new CSVInputAdapter(epService, adapterSpec)).start();
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
			CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "mapEvent");
			adapterSpec.setPropertyTypes(propertyTypesInvalid);
			(new CSVInputAdapter(epService, adapterSpec)).start();
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
			CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource("regression/noTimestampOne.csv"), "mapEvent");
			adapterSpec.setPropertyTypes(propertyTypesInvalid);
			(new CSVInputAdapter(epService, adapterSpec)).start();
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
		startAdapter(filename, -1, true, true, null, propertyOrderTimestamps);
		assertFalse(listener.getAndClearIsInvoked());
	}

	public void testRunTitleRowOnly()
	{
		String filename = "regression/titleRowOnly.csv";
		propertyOrderNoTimestamps = null;
		startAdapter(filename, -1, true, true, "timestamp", null);
		assertFalse(listener.getAndClearIsInvoked());
	}

	public void testRunDecreasingTimestamps()
	{
		String filename = "regression/decreasingTimestamps.csv";
		try
		{
			startAdapter(filename, -1, false, true, null, null);

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
			startAdapter(filename, -1, false, true, null, null);

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
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);

		isLooping = true;
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
	}

	public void testStartOneRow()
	{
		String filename = "regression/oneRow.csv";
		startAdapter(filename, -1, false, true, "timestamp", propertyOrderTimestamps);

		sendTimeEvent(100);
		assertEvent(1, 1.1, "one");
	}

	public void testPause()
	{
		String filename = "regression/noTimestampOne.csv";
		startAdapter(filename, 10, false, true, "timestamp", propertyOrderNoTimestamps);

		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");

		adapter.pause();

		sendTimeEvent(100);
		assertEquals(AdapterState.PAUSED, adapter.getState());
		assertFalse(listener.getAndClearIsInvoked());
	}

	public void testResumeWholeInterval()
	{
		String filename = "regression/noTimestampOne.csv";
		startAdapter(filename, 10, false, true, null, propertyOrderNoTimestamps);

		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");

		adapter.pause();
		sendTimeEvent(100);
		assertFalse(listener.getAndClearIsInvoked());
		adapter.resume();


		assertEvent(2, 2.2, "noTimestampOne.two");
	}

	public void testResumePartialInterval()
	{
		String filename = "regression/noTimestampOne.csv";
		startAdapter(filename, 10, false, true, null, propertyOrderNoTimestamps);

		// time is 100
		sendTimeEvent(100);
		assertEvent(1, 1.1, "noTimestampOne.one");

		// time is 150
		sendTimeEvent(50);

		adapter.pause();
		// time is 200
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		adapter.resume();

		assertEvent(2, 2.2, "noTimestampOne.two");
	}

	public void testEventsPerSecInvalid()
	{
		String filename = "regression/timestampOne.csv";

		try
		{
			startAdapter(filename, 0, true, true, null, null);
			fail();
		}
		catch(IllegalArgumentException e)
		{
			// Expected
		}

		try
		{
			startAdapter(filename, 1001, true, true, null, null);
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
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", null);
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
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
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
		startAdapter(filename, eventsPerSec, isLooping, true, null, null);
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
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);

		isLooping = true;
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);
		assertEvents(isLooping, events);
	}

	public void testDestroy()
	{
		String filename = "regression/timestampOne.csv";
		startAdapter(filename, -1, false, true, "timestamp", propertyOrderTimestamps);
		adapter.destroy();
		assertEquals(AdapterState.DESTROYED, adapter.getState());
	}

	public void testStop()
	{
		String filename =  "regression/timestampOne.csv";
		int eventsPerSec = -1;

		List<Object[]> events = new ArrayList<Object[]>();
		events.add(new Object[] { 100, 1, 1.1, "timestampOne.one"});
		events.add(new Object[] { 200, 3, 3.3, "timestampOne.three"});

		boolean isLooping = false;
		startAdapter(filename, eventsPerSec, isLooping, true, "timestamp", propertyOrderTimestamps);

		assertFlatEvents(events);

		adapter.stop();

		sendTimeEvent(1000);
		assertFalse(listener.getAndClearIsInvoked());

		adapter.start();
		assertFlatEvents(events);
	}

	public void testStopAfterEOF()
	{
		String filename =  "regression/timestampOne.csv";
		startAdapter(filename, -1, false, false, "timestamp", propertyOrderTimestamps);
		assertEquals(AdapterState.OPENED, adapter.getState());
	}

	public void testNotUsingEngineThreadTimestamp()
	{
		String filename = "regression/timestampOne.csv";

		long startTime = System.currentTimeMillis();
		startAdapter(filename, -1, false, false, "timestamp", propertyOrderTimestamps);
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
		startAdapter(filename, 5, false, false, null, propertyOrderNoTimestamps);
		long endTime = System.currentTimeMillis();

		// The last event should be sent after 600 ms
		assertTrue(endTime - startTime > 600);

		assertEquals(3, listener.getNewDataList().size());
		assertEvent(0, 1, 1.1, "noTimestampOne.one");
		assertEvent(1, 2, 2.2, "noTimestampOne.two");
		assertEvent(2, 3, 3.3, "noTimestampOne.three");
	}

	private void runNullEPService(CSVInputAdapter adapter)
	{
		try
		{
			adapter.start();
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}

		try
		{
			adapter.setEPService(null);
			fail();
		}
		catch(NullPointerException ex)
		{
			// Expected
		}

		adapter.setEPService(epService);
		adapter.start();
		assertEquals(3, listener.getNewDataList().size());
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

	private void startAdapter(String filename, int eventsPerSec, boolean isLooping, boolean usingEngineThread, String timestampColumn, String[] propertyOrder)
	{
		CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(new AdapterInputSource(filename), eventTypeAlias);
		if(eventsPerSec != -1)
		{
			adapterSpec.setEventsPerSec(eventsPerSec);
		}
		adapterSpec.setLooping(isLooping);
		adapterSpec.setPropertyOrder(propertyOrder);
		adapterSpec.setUsingEngineThread(usingEngineThread);
		adapterSpec.setTimestampColumn(timestampColumn);

		adapter = new CSVInputAdapter(epService, adapterSpec);
		adapter.start();
	}

	private void assertFailedConstruction(String filename, String eventTypeAlias)
	{
		try
		{
			(new CSVInputAdapter(epService, new AdapterInputSource(filename), eventTypeAlias)).start();
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}

}
