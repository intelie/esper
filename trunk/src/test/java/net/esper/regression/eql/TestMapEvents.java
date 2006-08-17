package net.esper.regression.eql;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;

public class TestMapEvents extends TestCase
{
	Map<String, String> properties;
	Map<String, Object> map;
	EPServiceProvider epService;
	
	protected void setUp()
	{
		properties = new HashMap<String, String>();
		properties.put("myInt", Integer.class.getName());
		properties.put("myString", String.class.getName());
		
		map = new HashMap<String, Object>();
		map.put("myInt", 3);
		map.put("myString", "some string");
		
		Configuration configuration = new Configuration();
		configuration.addEventTypeAlias("myMapEvent", properties);
		
		epService = EPServiceProviderManager.getProvider("myProvider", configuration);
	}	
	
	public void testSendMap()
	{
		String statementText = "select * from myMapEvent.win:length(5)";
		EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
		SupportUpdateListener listener = new SupportUpdateListener();
		statement.addListener(listener);		
		
		epService.getEPRuntime().sendEvent(map, "myMapEvent");
		
		assertTrue(listener.getAndClearIsInvoked());
		assertEquals(1, listener.getLastNewData().length);
		assertEquals(map, listener.getLastNewData()[0].getUnderlying());
		assertEquals(3, listener.getLastNewData()[0].get("myInt"));
		assertEquals("some string", listener.getLastNewData()[0].get("myString"));
	}
}
