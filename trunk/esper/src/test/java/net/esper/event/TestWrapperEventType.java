package net.esper.event;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.util.ArrayAssertionUtil;

public class TestWrapperEventType extends TestCase 
{
	private EventType underlyingEventTypeOne;
	private EventType underlyingEventTypeTwo;
	private EventType eventType;
	private Map<String, Class> properties;
	private EventAdapterService eventAdapterService;
	
	protected void setUp()
	{
        underlyingEventTypeOne = new BeanEventType(SupportBeanSimple.class, new BeanEventAdapter(), null, "abc");
        underlyingEventTypeTwo = new BeanEventType(SupportBean_A.class, new BeanEventAdapter(), null, "abc");
        properties = new HashMap<String, Class>();
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
		Map<String, Class> otherProperties = new HashMap<String, Class>(properties);
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
