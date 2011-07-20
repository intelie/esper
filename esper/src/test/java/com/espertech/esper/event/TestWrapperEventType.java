/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestWrapperEventType extends TestCase 
{
	private EventType underlyingEventTypeOne;
	private EventType underlyingEventTypeTwo;
	private EventType eventType;
	private Map<String, Object> properties;
	private EventAdapterService eventAdapterService;
	
	protected void setUp()
	{
        underlyingEventTypeOne = new BeanEventType(null, 1, SupportBeanSimple.class, SupportEventAdapterService.getService(), null);
        underlyingEventTypeTwo = new BeanEventType(null, 1, SupportBean_A.class, SupportEventAdapterService.getService(), null);
        properties = new HashMap<String, Object>();
        properties.put("additionalString", String.class);
        properties.put("additionalInt", Integer.class);
        eventAdapterService = SupportEventAdapterService.getService();
        EventTypeMetadata meta = EventTypeMetadata.createWrapper("test", true, false, false);
        eventType = new WrapperEventType(meta, "mytype", 1, underlyingEventTypeOne, properties, eventAdapterService);
	}
	
	public void testInvalidRepeatedNames()
	{
		properties.clear();
		properties.put("myString", String.class);
		
		try
		{	
			// The myString property occurs in both the event and the map
			eventType = new WrapperEventType(null,"mytype", 1, underlyingEventTypeOne, properties, eventAdapterService);
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
        EventTypeMetadata meta = EventTypeMetadata.createWrapper("test", true, false, false);
		EventType otherType = new WrapperEventType(meta, "mytype", 1, underlyingEventTypeOne, otherProperties, eventAdapterService);
		assertTrue(eventType.equals(otherType));
		assertTrue(otherType.equals(eventType));
		
		otherType = new WrapperEventType(meta, "mytype", 1, underlyingEventTypeTwo, otherProperties, eventAdapterService);
		assertFalse(eventType.equals(otherType));
		assertFalse(otherType.equals(eventType));
		
		otherProperties.put("anotherProperty", Integer.class);
		otherType = new WrapperEventType(meta, "mytype", 1, underlyingEventTypeOne, otherProperties, eventAdapterService);
		assertFalse(eventType.equals(otherType));
		assertFalse(otherType.equals(eventType));
		
		otherType = underlyingEventTypeOne;
		assertFalse(eventType.equals(otherType));
		assertFalse(otherType.equals(eventType));
	}
}
