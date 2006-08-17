package net.esper.adapter.csv;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVAdapterException;
import net.esper.adapter.csv.CSVAdapterSpec;
import net.esper.adapter.csv.MapEventSpec;
import net.esper.client.Configuration;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

import junit.framework.TestCase;

public class TestCSVAdapter extends TestCase
{
	Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
	CSVAdapter adapter;
	private MapEventSpec mapEventSpec;
	
	protected void setUp() throws ClassNotFoundException
	{
		Map<String, String> propertyTypeNames = new LinkedHashMap<String, String>();
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
	}

	public void testResolveTitleRow()
	{
		// Use first row
		String[] firstRow = new String[] { "myDouble", "myInt", "myString" };
		assertEquals(firstRow, CSVAdapter.resolvePropertyOrder(firstRow, propertyTypes));
		
		firstRow = new String[] { "myDouble", "myInt", "myString", "timestamp" };
		assertEquals(firstRow, CSVAdapter.resolvePropertyOrder(firstRow, propertyTypes));
		
		// Use propertyTypes
		firstRow = new String[] { "1", "2.0", "text" };
		String[] expected = propertyTypes.keySet().toArray(new String[0]);
		assertEquals(Arrays.asList(expected), Arrays.asList(CSVAdapter.resolvePropertyOrder(firstRow, propertyTypes)));

		propertyTypes.put("timestamp", long.class);
		expected = propertyTypes.keySet().toArray(new String[0]);
		assertEquals(Arrays.asList(expected), Arrays.asList(CSVAdapter.resolvePropertyOrder(firstRow, propertyTypes)));
	}
	
	public void testCancel()
	{
		CSVAdapterSpec adapterSpec = new CSVAdapterSpec("regression/noTimestampOne.csv", false, 10);
		CSVAdapter adapter = new CSVAdapter(adapterSpec, mapEventSpec);

		adapter.start();
		adapter.cancel();
		
		try
		{
			adapter.start();
			fail();
		}
		catch(CSVAdapterException e)
		{
			// Expected
		}
		try
		{
			adapter.cancel();
			fail();
		}
		catch(CSVAdapterException e)
		{
			// Expected
		}
	}
}
