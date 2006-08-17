package net.esper.adapter.csv;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import net.esper.adapter.csv.CSVAdapterException;
import net.esper.adapter.csv.CSVAdapterGroup;
import net.esper.adapter.csv.CSVAdapterSpec;
import net.esper.adapter.csv.MapEventSpec;
import net.esper.client.Configuration;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

public class TestCSVAdapterGroup extends TestCase
{
	CSVAdapterGroup group;
	
	protected void setUp() throws ClassNotFoundException
	{
		group = new CSVAdapterGroup();
		CSVAdapterSpec adapterSpecOne = new CSVAdapterSpec("regression/timestampOne.csv", false, -1);
		CSVAdapterSpec adapterSpecTwo = new CSVAdapterSpec("regression/timestampTwo.csv", false, -1);
		
		Map<String, String> propertyTypeNames = new LinkedHashMap<String, String>();
		propertyTypeNames.put("myInt", Integer.class.getName());
		propertyTypeNames.put("myDouble", Double.class.getName());
		propertyTypeNames.put("myString", String.class.getName());	
		
		String eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypeNames);
		
		EPServiceProvider epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		EPRuntime epRuntime = epService.getEPRuntime();
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypeNames, epRuntime);

		group.addNewAdapter(adapterSpecOne, mapSpec);
		group.addNewAdapter(adapterSpecTwo, mapSpec);
	}
	
	public void testCancel()
	{
		group.start();
		group.cancel();
		try
		{
			group.start();
			fail();
		}
		catch (CSVAdapterException e)
		{
			// Expected
		}
		try
		{
			group.cancel();
			fail();
		}
		catch (CSVAdapterException e)
		{
			// Expected
		}
	}
}
