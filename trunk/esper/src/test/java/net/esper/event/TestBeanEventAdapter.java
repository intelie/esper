package net.esper.event;

import junit.framework.TestCase;
import net.esper.support.bean.*;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.*;

public class TestBeanEventAdapter extends TestCase
{
    private BeanEventAdapter beanEventAdapter;

    public void setUp()
    {
        beanEventAdapter = new BeanEventAdapter();
    }

    public void testCreateBeanType()
    {
        EventTypeSPI eventType = beanEventAdapter.createOrGetBeanType(SupportBeanSimple.class);

        assertEquals(SupportBeanSimple.class, eventType.getUnderlyingType());
        assertEquals(2, eventType.getPropertyNames().length);
        assertEquals("CLASS_net.esper.support.bean.SupportBeanSimple", eventType.getEventTypeId());

        // Second call to create the event type, should be the same instance as the first
        EventType eventTypeTwo = beanEventAdapter.createOrGetBeanType(SupportBeanSimple.class);
        assertTrue(eventTypeTwo == eventType);
        assertEquals("CLASS_net.esper.support.bean.SupportBeanSimple", eventType.getEventTypeId());

        // Third call to create the event type, getting a given event type id
        EventType eventTypeThree = beanEventAdapter.createOrGetBeanType(SupportBeanSimple.class);
        assertTrue(eventTypeThree == eventType);
        assertEquals("CLASS_net.esper.support.bean.SupportBeanSimple", eventType.getEventTypeId());
    }

    public void testInterfaceProperty()
    {
        // Assert implementations have full set of properties
        ISupportDImpl event = new ISupportDImpl("D", "BaseD", "BaseDBase");
        EventBean bean = beanEventAdapter.adapterForBean(event, null);
        assertEquals("D", bean.get("d"));
        assertEquals("BaseD", bean.get("baseD"));
        assertEquals("BaseDBase", bean.get("baseDBase"));
        assertEquals(3, bean.getEventType().getPropertyNames().length);
        ArrayAssertionUtil.assertEqualsAnyOrder(bean.getEventType().getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});

        // Assert intermediate interfaces have full set of fields
        EventType interfaceType = beanEventAdapter.createOrGetBeanType(ISupportD.class);
        ArrayAssertionUtil.assertEqualsAnyOrder(interfaceType.getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});
    }

    public void testMappedIndexedNestedProperty() throws Exception
    {
    	EventType eventType = beanEventAdapter.createOrGetBeanType(SupportBeanComplexProps.class);

        assertEquals(Map.class, eventType.getPropertyType("mapProperty"));
        assertEquals(String.class, eventType.getPropertyType("mapped('x')"));
        assertEquals(int.class, eventType.getPropertyType("indexed[1]"));
        assertEquals(SupportBeanComplexProps.SupportBeanSpecialGetterNested.class, eventType.getPropertyType("nested"));
        assertEquals(int[].class, eventType.getPropertyType("arrayProperty"));
    }
}
