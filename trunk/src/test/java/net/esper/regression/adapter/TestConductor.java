package net.esper.regression.adapter;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Conductor;
import net.esper.adapter.SendableEvent;
import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVPlayer;
import net.esper.adapter.csv.SendableMapEvent;
import net.esper.client.Configuration;
import net.esper.client.EPAdministrator;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.schedule.ScheduleSlot;
import net.esper.support.adapter.SupportPlayer;
import net.esper.support.util.SupportUpdateListener;

public class TestConductor extends TestCase
{
	private static final Log log = LogFactory.getLog(TestConductor.class);
	
	private SupportUpdateListener listener;
	private CSVAdapter csvAdapter;
	private String eventTypeAlias;
	private EPServiceProvider epService;
	private long currentTime;
	private Conductor conductor;
	private SupportPlayer supportPlayer = new SupportPlayer();
	private CSVPlayer timestampsLooping;
	private CSVPlayer noTimestampsLooping;
	private EPRuntime runtime;
	private CSVPlayer noTimestampsNotLooping;
	private CSVPlayer timestampsNotLooping;
	
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
		runtime = epService.getEPRuntime();
		EPAdministrator administrator = epService.getEPAdministrator();
		String statementText = "select * from mapEvent.win:length(5)";
		EPStatement statement = administrator.createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
		
		csvAdapter = epService.getEPAdapters().getCSVAdapter();
		
		// Turn off external clocking
		epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
		
		// Set the clock to 0
		currentTime = 0;
		sendTimeEvent(0);
		
		conductor = epService.getEPAdapters().createConductor();

		// A CSVPlayer for a file with timestamps, not looping
		timestampsNotLooping = csvAdapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource("/regression/timestampOne.csv"));
		
		// A CSVPlauer for a file with timestamps, looping
		timestampsLooping = csvAdapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource("/regression/timestampTwo.csv"));
		timestampsLooping.setIsLooping(true);
		
		// A CSVPlayer that sends 10 events per sec, not looping
		noTimestampsNotLooping = csvAdapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource("/regression/noTimestampOne.csv"));
		noTimestampsNotLooping.setEventsPerSec(10);
		
		// A CSVPlayer that sends 5 events per sec, looping
		noTimestampsLooping = csvAdapter.createCSVPlayer(eventTypeAlias, new AdapterInputSource("/regression/noTimestampTwo.csv"));
		noTimestampsLooping.setEventsPerSec(5);
		noTimestampsLooping.setIsLooping(true);
	}
	
	public void testRun()
	{		
		conductor.add(timestampsNotLooping);
		conductor.add(timestampsLooping);
		conductor.add(noTimestampsNotLooping);
		conductor.add(noTimestampsLooping);
		
		// Add a SupportPlayer
		setSupportEvent(50);
		conductor.add(supportPlayer);
		
		// Time is 0
		assertFalse(listener.getAndClearIsInvoked());
		conductor.start();
		
		// Time is 50
		sendTimeEvent(50);
		assertEvent(0, 0, 0.0, "supportPlayer.zero");
		setSupportEvent(250);
		assertSizeAndReset(1);
		
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
		assertEvent(0, 0, 0.0, "supportPlayer.zero");
		setSupportEvent(400);
		assertSizeAndReset(1);
		
		// Time is 300	
		sendTimeEvent(50);
		assertEvent(0, 3, 3.3, "timestampOne.three");
		assertEvent(1, 3, 3.3, "noTimestampOne.three");
		assertSizeAndReset(2);
		
		// Time is 350
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		conductor.pause();
		
		// Time is 400
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		// Time is 450
		sendTimeEvent(50);
		assertFalse(listener.getAndClearIsInvoked());
		
		conductor.resume();
		
		assertEvent(0, 4, 4.4, "timestampTwo.four");
		assertEvent(1, 4, 4.4, "noTimestampTwo.four");
		assertEvent(2, 0, 0.0, "supportPlayer.zero");
		assertSizeAndReset(3);
		
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
		
		conductor.stop();
		sendTimeEvent(1000);
		assertFalse(listener.getAndClearIsInvoked());
		assertNull(conductor.read());
	}
	
	public void testRead()
	{
		conductor.add(timestampsNotLooping);
		conductor.add(timestampsLooping);
		conductor.add(noTimestampsNotLooping);
		conductor.add(noTimestampsLooping);
		
		// Add a SupportPlayer
		setSupportEvent(50);
		conductor.add(supportPlayer);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 0, 0.0, "supportPlayer.zero");
		setSupportEvent(250);
		assertSizeAndReset(1);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 1, 1.1, "timestampOne.one");
		(conductor.read()).send(runtime);
		assertEvent(1, 1, 1.1, "noTimestampOne.one");
		assertSizeAndReset(2);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 2, 2.2, "timestampTwo.two");
		(conductor.read()).send(runtime);
		assertEvent(1, 2, 2.2, "noTimestampOne.two");
		(conductor.read()).send(runtime);
		assertEvent(2, 2, 2.2, "noTimestampTwo.two");
		assertSizeAndReset(3);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 0, 0.0, "supportPlayer.zero");
		setSupportEvent(400);
		assertSizeAndReset(1);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 3, 3.3, "timestampOne.three");
		(conductor.read()).send(runtime);
		assertEvent(1, 3, 3.3, "noTimestampOne.three");
		assertSizeAndReset(2);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 4, 4.4, "timestampTwo.four");
		(conductor.read()).send(runtime);
		assertEvent(1, 4, 4.4, "noTimestampTwo.four");
		(conductor.read()).send(runtime);
		assertEvent(2, 0, 0.0, "supportPlayer.zero");
		assertSizeAndReset(3);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 5, 5.5, "timestampOne.five");
		assertSizeAndReset(1);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 6, 6.6, "timestampTwo.six");
		(conductor.read()).send(runtime);
		assertEvent(1, 2, 2.2, "noTimestampTwo.two");
		assertSizeAndReset(2);
		
		(conductor.read()).send(runtime);
		assertEvent(0, 2, 2.2, "timestampTwo.two");
		(conductor.read()).send(runtime);
		assertEvent(1, 4, 4.4, "noTimestampTwo.four");
		assertSizeAndReset(2);	
		
		conductor.start();
		
		sendTimeEvent(1000);
		assertEvent(0, 4, 4.4, "timestampTwo.four");
		assertEvent(1, 2, 2.2, "noTimestampTwo.two");
		assertSizeAndReset(2);	
		
		sendTimeEvent(100);
		(conductor.read()).send(runtime);
		assertEvent(0, 6, 6.6, "timestampTwo.six");
		(conductor.read()).send(runtime);
		assertEvent(1, 4, 4.4, "noTimestampTwo.four");
		assertSizeAndReset(2);
		
		sendTimeEvent(100);
		assertFalse(listener.getAndClearIsInvoked());
		
		sendTimeEvent(200);
		assertEvent(0, 2, 2.2, "timestampTwo.two");
		assertEvent(1, 2, 2.2, "noTimestampTwo.two");
		assertSizeAndReset(2);	
	}
	
	public void testRunTillNull()
	{
		conductor.add(timestampsNotLooping);
		conductor.add(supportPlayer);
		conductor.start();
		
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
		assertNull(conductor.read());
		
		setSupportEvent(800);
		
		// Time is 700
		sendTimeEvent(100);
		log.debug(".testRunTillNull time==700");
		assertFalse(listener.getAndClearIsInvoked());
		
		// Time is 800
		sendTimeEvent(100);
		log.debug(".testRunTillNull time==800");
		assertEvent(0, 0, 0.0, "supportPlayer.zero");
		assertSizeAndReset(1);
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
