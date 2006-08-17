package net.esper.adapter.csv;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVAdapterGroup;
import net.esper.adapter.csv.CSVAdapterSpec;
import net.esper.adapter.csv.MapEventSpec;
import net.esper.client.Configuration;
import net.esper.client.EPAdministrator;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.support.util.SupportUpdateListener;

public class TestCSVEvents extends TestCase
{
	Map<String, String> propertyTypeNames = new LinkedHashMap<String, String>();
	Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
	String[] titleRow;
	MapEventSpec mapEventSpec;
	SupportUpdateListener listener;
	protected void setUp() throws ClassNotFoundException
	{
		propertyTypeNames.put("myInt", Integer.class.getName());
		propertyTypeNames.put("myDouble", Double.class.getName());
		propertyTypeNames.put("myString", String.class.getName());
		
		String eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypeNames);
		
		EPServiceProvider epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		EPRuntime epRuntime = epService.getEPRuntime();
		mapEventSpec = new MapEventSpec(eventTypeAlias, propertyTypeNames, epRuntime);
		propertyTypes = mapEventSpec.getPropertyTypes();
		
		EPAdministrator administrator = epService.getEPAdministrator();
		String statementText = "select * from mapEvent.win:length(5)";
		EPStatement statement = administrator.createEQL(statementText);
		listener = new SupportUpdateListener();
		statement.addListener(listener);
	}
	

	
	public void testSingleFileNoTimeStamp() throws InterruptedException 
	{
		CSVAdapterSpec adapterSpec = new CSVAdapterSpec("regression/noTimestampOne.csv", false, 10);
		CSVAdapter adapter = new CSVAdapter(adapterSpec, mapEventSpec);
		
		adapter.start();
		
		Thread.sleep(150);
		assertEvent(1, 1.1, "one");
		listener.reset();

		Thread.sleep(100);
		assertEvent(2, 2.2, "two");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(3, 3.3, "three");
		listener.reset();
		
		Thread.sleep(100);		
		assertNull(listener.getLastNewData());
	}
	
	public void testSingleFileTimestamp() throws InterruptedException
	{
		CSVAdapterSpec adapterSpec = new CSVAdapterSpec("regression/timestampOne.csv", false, -1);
		CSVAdapter adapter = new CSVAdapter(adapterSpec, mapEventSpec);
		
		adapter.start();
		
		Thread.sleep(150);
		assertEvent(1, 1.1, "one");
		listener.reset();
		
		Thread.sleep(200);
		assertEvent(3, 3.3, "three");
		listener.reset();
		
		Thread.sleep(200);
		assertEvent(5, 5.5, "five");
		listener.reset();
		
		Thread.sleep(100);
		assertNull(listener.getLastNewData());
	}
	
	public void testTitleRow() throws InterruptedException
	{
		CSVAdapterSpec adapterSpec = new CSVAdapterSpec("regression/titleRow.csv", false, -1);
		CSVAdapter adapter = new CSVAdapter(adapterSpec, mapEventSpec);
		
		adapter.start();
		
		Thread.sleep(150);
		assertEvent(1, 1.1, "one");
		listener.reset();
		
		Thread.sleep(200);
		assertEvent(3, 3.3, "three");
		listener.reset();
		
		Thread.sleep(200);
		assertEvent(5, 5.5, "five");
		listener.reset();
		
		Thread.sleep(100);
		assertNull(listener.getLastNewData());
	}
	
	public void testMultipleFilesTimestamp() throws InterruptedException
	{
		CSVAdapterGroup group = new CSVAdapterGroup();
		CSVAdapterSpec adapterSpecOne = new CSVAdapterSpec("regression/timestampOne.csv", false, -1);
		CSVAdapterSpec adapterSpecTwo = new CSVAdapterSpec("regression/timestampTwo.csv", false, -1);
		group.addNewAdapter(adapterSpecOne, mapEventSpec);
		group.addNewAdapter(adapterSpecTwo, mapEventSpec);
		
		group.start();		
		
		Thread.sleep(130);
		assertEvent(1, 1.1, "one");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(2, 2.2, "two");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(3, 3.3, "three");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(4, 4.4, "four");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(5, 5.5, "five");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(6, 6.6, "six");
		listener.reset();
		
		Thread.sleep(100);
		assertNull(listener.getLastNewData());
	}
	
	public void testMultipleFilesMixed() throws InterruptedException
	{
		CSVAdapterGroup group = new CSVAdapterGroup();
		// First file has timestamps and isn't looped
		CSVAdapterSpec adapterSpecOne = new CSVAdapterSpec("regression/timestampOne.csv", false, -1);
		// Second file is 5 events per sec and is looped
		CSVAdapterSpec adapterSpecTwo = new CSVAdapterSpec("regression/noTimestampTwo.csv", true, 5);
		group.addNewAdapter(adapterSpecOne, mapEventSpec);
		group.addNewAdapter(adapterSpecTwo, mapEventSpec);
		
		group.start();		
		
		Thread.sleep(150);
		assertEvent(1, 1.1, "one");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(2, 2.2, "two");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(3, 3.3, "three");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(4, 4.4, "four");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(5, 5.5, "five");
		listener.reset();
		
		Thread.sleep(100);
		assertEvent(2, 2.2, "two");
		listener.reset();
		
		Thread.sleep(200);
		assertEvent(4, 4.4, "four");
		listener.reset();
		
		group.cancel();
		
		Thread.sleep(100);
		assertNull(listener.getLastNewData());
	}
	
	private void assertEvent(int myInt, double myDouble, String myString)
	{
		assertTrue(listener.isInvoked());
		assertEquals(1, listener.getLastNewData().length);
		EventBean event = listener.getLastNewData()[0];
		assertEquals(myInt, event.get("myInt"));
		assertEquals(myDouble, event.get("myDouble"));
		assertEquals(myString, event.get("myString"));
	}
}
