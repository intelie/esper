package net.esper.adapter.csv;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

public class TestCSVAdapter extends TestCase
{
	private Map<String, Class> propertyTypes;
	private MapEventSpec mapEventSpec;
	
	protected void setUp() throws ClassNotFoundException
	{
		propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
		
		String eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypes);
		
		EPServiceProvider epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		EPRuntime epRuntime = epService.getEPRuntime();
		mapEventSpec = new MapEventSpec(eventTypeAlias, propertyTypes, epRuntime);
	}

	public void testResolveTitleRow()
	{
		// Use first row
		String[] firstRow = new String[] { "myDouble", "myInt", "myString" };
		assertEquals(firstRow, CSVPlayer.resolvePropertyOrder(firstRow, propertyTypes));
		
		firstRow = new String[] { "myDouble", "myInt", "myString", "timestamp" };
		assertEquals(firstRow, CSVPlayer.resolvePropertyOrder(firstRow, propertyTypes));
		
		// Use propertyTypes
		firstRow = new String[] { "1", "2.0", "text" };
		String[] expected = propertyTypes.keySet().toArray(new String[0]);
		assertEquals(Arrays.asList(expected), Arrays.asList(CSVPlayer.resolvePropertyOrder(firstRow, propertyTypes)));

		propertyTypes.put("timestamp", long.class);
		expected = propertyTypes.keySet().toArray(new String[0]);
		assertEquals(Arrays.asList(expected), Arrays.asList(CSVPlayer.resolvePropertyOrder(firstRow, propertyTypes)));
	}
	
	public void testCancel()
	{
		CSVAdapterSpec adapterSpec = new CSVAdapterSpec("regression/noTimestampOne.csv", false, 10);
		CSVPlayer adapter = new CSVPlayer(adapterSpec, mapEventSpec);

		adapter.start();
		adapter.cancel();
		
		try
		{
			adapter.start();
			fail();
		}
		catch(EPException e)
		{
			// Expected
		}
		try
		{
			adapter.cancel();
			fail();
		}
		catch(EPException e)
		{
			// Expected
		}
	}
}
