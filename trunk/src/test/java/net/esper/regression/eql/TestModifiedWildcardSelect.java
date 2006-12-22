package net.esper.regression.eql;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_B;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;


public class TestModifiedWildcardSelect extends TestCase
{
	private EPServiceProvider epService;
	private SupportUpdateListener listener;
	private SupportUpdateListener insertListener;
	private Map<String, Object> properties;

	protected void setUp()
	{
		epService = EPServiceProviderManager.getDefaultProvider();
		epService.initialize();
		listener = new SupportUpdateListener();
		insertListener = new SupportUpdateListener();
		properties = new HashMap<String, Object>();
	}
	
	public void testSingle() throws Exception

	{
		String eventName = SupportBeanSimple.class.getName();  
		String text = "select *, myString||myString as concat from " + eventName + ".win:length(5)";
		
		EPStatement statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);
		assertSimple();
	}
	
	public void testSingleInsertInto() throws InterruptedException
	{
		String eventName = SupportBeanSimple.class.getName();  
		String text = "insert into someEvent select *, myString||myString as concat from " + eventName + ".win:length(5)";
		String textTwo = "select * from someEvent.win:length(5)";
		
		EPStatement statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);
		
		statement = epService.getEPAdministrator().createEQL(textTwo);
		statement.addListener(insertListener);
		assertSimple();
		assertProperties(insertListener);
	}
	
	public void testJoinInsertInto() throws InterruptedException
	{
		String eventNameOne = SupportBeanSimple.class.getName();
		String eventNameTwo = SupportMarketDataBean.class.getName();
		String text = "insert into someJoinEvent select *, myString||myString as concat " +
				"from " + eventNameOne + ".win:length(5) as eventOne, " 
				+ eventNameTwo + ".win:length(5) as eventTwo";
		String textTwo = "select * from someJoinEvent.win:length(5)";
		
		EPStatement statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);

		statement = epService.getEPAdministrator().createEQL(textTwo);
		statement.addListener(insertListener);
		
		assertNoCommonProperties();
		assertProperties(insertListener);
	}

	public void testJoinNoCommonProperties() throws Exception
	{
		String eventNameOne = SupportBeanSimple.class.getName();
		String eventNameTwo = SupportMarketDataBean.class.getName();
		String text = "select *, myString||myString as concat " +
				"from " + eventNameOne + ".win:length(5) as eventOne, " 
				+ eventNameTwo + ".win:length(5) as eventTwo";
		
		EPStatement statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);

		assertNoCommonProperties();
		
		listener.reset();
		epService.initialize();
		
		text = "select *, myString||myString as concat " +
		"from " + eventNameOne + ".win:length(5) as eventOne, " + 
				eventNameTwo + ".win:length(5) as eventTwo " + 
				"where eventOne.myString = eventTwo.symbol";

		statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);

		assertNoCommonProperties();
	}

	public void testJoinCommonProperties() throws Exception
	{
		String eventNameOne = SupportBean_A.class.getName();
		String eventNameTwo = SupportBean_B.class.getName();
		String text = "select *, eventOne.id||eventTwo.id as concat " +
				"from " + eventNameOne + ".win:length(5) as eventOne, " + 
						eventNameTwo + ".win:length(5) as eventTwo ";
		
		EPStatement statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);

		assertCommonProperties();
		
		listener.reset();
		epService.initialize();
		
		text = "select *, eventOne.id||eventTwo.id as concat " +
			"from " + eventNameOne + ".win:length(5) as eventOne, " + 
				eventNameTwo + ".win:length(5) as eventTwo " + 
				"where eventOne.id = eventTwo.id";

		statement = epService.getEPAdministrator().createEQL(text);
		statement.addListener(listener);

		assertCommonProperties();
	}
	
	
	private void assertNoCommonProperties() throws InterruptedException
	{
		sendSimpleEvent("string");
		sendMarketEvent("string");
		Thread.sleep(50);
		EventBean event = listener.getLastNewData()[0];
		properties.put("concat", "stringstring");
		assertProperties(listener);
		assertNotNull(event.get("eventOne"));
		assertNotNull(event.get("eventTwo"));
	}
	
	private void assertSimple() throws InterruptedException
	{
		sendSimpleEvent("string");
		Thread.sleep(50);
		assertEquals("stringstring", listener.getLastNewData()[0].get("concat"));
		properties.put("concat", "stringstring");
		properties.put("myString", "string");
		properties.put("myInt", 0);
		assertProperties(listener);
	}

	private void assertCommonProperties() throws InterruptedException
	{
		sendABEvents("string");
		Thread.sleep(50);
		EventBean event = listener.getLastNewData()[0];
		properties.put("concat", "stringstring");
		assertProperties(listener);
		assertNotNull(event.get("eventOne"));
		assertNotNull(event.get("eventTwo"));
	}

	private void assertProperties(SupportUpdateListener listener)
	{
		EventBean event = listener.getLastNewData()[0];
		for(String property : properties.keySet())
		{
			assertEquals(properties.get(property), event.get(property));
		}
	}
	
	private void sendSimpleEvent(String s)
	{
	    SupportBeanSimple bean = new SupportBeanSimple(s, 0);
	    epService.getEPRuntime().sendEvent(bean);
	}
	
	private void sendMarketEvent(String symbol)
	{
		SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0.0, 0L, null);
		epService.getEPRuntime().sendEvent(bean);
	}
	
	private void sendABEvents(String id)
	{
		SupportBean_A beanOne = new SupportBean_A(id);
		SupportBean_B beanTwo = new SupportBean_B(id);
		epService.getEPRuntime().sendEvent(beanOne);
		epService.getEPRuntime().sendEvent(beanTwo);
	}
}
