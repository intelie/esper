package com.espertech.esper.event;

import junit.framework.TestCase;
import com.espertech.esper.support.bean.ISupportD;
import com.espertech.esper.support.bean.ISupportDImpl;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestBeanEventAdapter extends TestCase
{
    private BeanEventTypeFactory beanEventTypeFactory;

    public void setUp()
    {
        beanEventTypeFactory = new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>());
    }

    public void testCreateBeanType()
    {
        BeanEventType eventType = beanEventTypeFactory.createBeanType("a", SupportBeanSimple.class);

        assertEquals(SupportBeanSimple.class, eventType.getUnderlyingType());
        assertEquals(2, eventType.getPropertyNames().length);

        // Second call to create the event type, should be the same instance as the first
        EventType eventTypeTwo = beanEventTypeFactory.createBeanType("b", SupportBeanSimple.class);
        assertTrue(eventTypeTwo == eventType);

        // Third call to create the event type, getting a given event type id
        EventType eventTypeThree = beanEventTypeFactory.createBeanType("c", SupportBeanSimple.class);
        assertTrue(eventTypeThree == eventType);
    }

    public void testInterfaceProperty()
    {
        // Assert implementations have full set of properties
        ISupportDImpl event = new ISupportDImpl("D", "BaseD", "BaseDBase");
        EventType typeBean = beanEventTypeFactory.createBeanType(event.getClass().getName(), event.getClass());
        EventBean bean = new BeanEventBean(event, typeBean);
        assertEquals("D", bean.get("d"));
        assertEquals("BaseD", bean.get("baseD"));
        assertEquals("BaseDBase", bean.get("baseDBase"));
        assertEquals(3, bean.getEventType().getPropertyNames().length);
        ArrayAssertionUtil.assertEqualsAnyOrder(bean.getEventType().getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});

        // Assert intermediate interfaces have full set of fields
        EventType interfaceType = beanEventTypeFactory.createBeanType("d", ISupportD.class);
        ArrayAssertionUtil.assertEqualsAnyOrder(interfaceType.getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});
    }

    public void testMappedIndexedNestedProperty() throws Exception
    {
    	EventType eventType = beanEventTypeFactory.createBeanType("e", SupportBeanComplexProps.class);

        assertEquals(Map.class, eventType.getPropertyType("mapProperty"));
        assertEquals(String.class, eventType.getPropertyType("mapped('x')"));
        assertEquals(int.class, eventType.getPropertyType("indexed[1]"));
        assertEquals(SupportBeanComplexProps.SupportBeanSpecialGetterNested.class, eventType.getPropertyType("nested"));
        assertEquals(int[].class, eventType.getPropertyType("arrayProperty"));
    }
}
