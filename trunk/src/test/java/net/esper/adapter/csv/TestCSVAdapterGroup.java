package net.esper.adapter.csv;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

public class TestCSVAdapterGroup extends TestCase
{
	Conductor group;
	
	protected void setUp() throws ClassNotFoundException
	{
		group = new Conductor();
		CSVAdapterSpec adapterSpecOne = new CSVAdapterSpec("regression/timestampOne.csv", false, -1);
		CSVAdapterSpec adapterSpecTwo = new CSVAdapterSpec("regression/timestampTwo.csv", false, -1);
		
		Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);	
		
		String eventTypeAlias = "mapEvent";
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias(eventTypeAlias, propertyTypes);
		
		EPServiceProvider epService = EPServiceProviderManager.getProvider("CSVProvider", configuration);
		EPRuntime epRuntime = epService.getEPRuntime();
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypes, epRuntime);

		group.add(new CSVPlayer(null, null, adapterSpecOne, mapSpec));
		group.add(new CSVPlayer(null, null, adapterSpecTwo, mapSpec));
	}
	
	public void testCancel()
	{
		group.start();
		// Check events arriving
		
		group.cancel();
		// Check events not arriving
		
		try
		{
			group.start();
			fail();
		}
		catch (EPException e)
		{
			// Expected
		}
		try
		{
			group.cancel();
			fail();
		}
		catch (EPException e)
		{
			// Expected
		}
	}
	
	public void testPauseAndResume()
	{
		group.start();
		// Check events arriving

		group.pause();
		// Check events not arriving
		
		group.resume();
		// Check arriving again
		
		try
		{
			group.resume();
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
		
		group.cancel();
		// Check events not arriving

		try 
		{
			group.pause();
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
		
		
		try
		{
			group.resume();
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
}
