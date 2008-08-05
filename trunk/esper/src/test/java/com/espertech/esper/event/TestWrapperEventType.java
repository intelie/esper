package com.espertech.esper.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.TestCase;
import com.espertech.esper.client.EPException;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestWrapperEventType extends TestCase 
{
	private EventType underlyingEventTypeOne;
	private EventType underlyingEventTypeTwo;
	private EventType eventType;
	private Map<String, Object> properties;
	private EventAdapterService eventAdapterService;
	
	protected void setUp()
	{
        underlyingEventTypeOne = new BeanEventType(SupportBeanSimple.class, new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>()), null, "abc");
        underlyingEventTypeTwo = new BeanEventType(SupportBean_A.class, new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>()), null, "abc");
        properties = new HashMap<String, Object>();
        properties.put("additionalString", String.class);
        properties.put("additionalInt", Integer.class);
        eventAdapterService = SupportEventAdapterService.getService();
        eventType = new WrapperEventType("mytype", underlyingEventTypeOne, properties, eventAdapterService);
	}
	
	public void testInvalidRepeatedNames()
	{
		properties.clear();
		properties.put("myString", String.class);
		
		try
		{	
			// The myString property occurs in both the event and the map
			eventType = new WrapperEventType("mytype", underlyingEventTypeOne, properties, eventAdapterService);
			fail();
		}
		catch(EPException ex)
		{
			// Expected
		}
	}
	
	public void testGetPropertyNames()
	{
		String[] expected = new String[] { "myInt", "myString", "additionalInt", "additionalString" };
		ArrayAssertionUtil.assertEqualsAnyOrder(expected, eventType.getPropertyNames());
	}
	
	public void testGetPropertyType()
	{
		assertEquals(int.class, eventType.getPropertyType("myInt"));
		assertEquals(Integer.class, eventType.getPropertyType("additionalInt"));
		assertEquals(String.class, eventType.getPropertyType("additionalString"));
		assertEquals(String.class, eventType.getPropertyType("myString"));
		assertNull(eventType.getPropertyType("unknownProperty"));
	}
	
	public void testIsProperty()
	{
		assertTrue(eventType.isProperty("myInt"));
		assertTrue(eventType.isProperty("additionalInt"));
		assertTrue(eventType.isProperty("additionalString"));
		assertTrue(eventType.isProperty("myString"));
		assertFalse(eventType.isProperty("unknownProperty"));
	}
	
	public void testEquals()
	{
		Map<String, Object> otherProperties = new HashMap<String, Object>(properties);
		EventType otherType = new WrapperEventType("mytype", underlyingEventTypeOne, otherProperties, eventAdapterService);
		assertTrue(eventType.equals(otherType));
		assertTrue(otherType.equals(eventType));
		
		otherType = new WrapperEventType("mytype", underlyingEventTypeTwo, otherProperties, eventAdapterService);
		assertFalse(eventType.equals(otherType));
		assertFalse(otherType.equals(eventType));
		
		otherProperties.put("anotherProperty", Integer.class);
		otherType = new WrapperEventType("mytype", underlyingEventTypeOne, otherProperties, eventAdapterService);
		assertFalse(eventType.equals(otherType));
		assertFalse(otherType.equals(eventType));
		
		otherType = underlyingEventTypeOne;
		assertFalse(eventType.equals(otherType));
		assertFalse(otherType.equals(eventType));
	}
}