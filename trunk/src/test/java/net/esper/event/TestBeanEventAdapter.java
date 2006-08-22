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
        EventType eventType = beanEventAdapter.createBeanType(SupportBeanSimple.class);

        assertEquals(SupportBeanSimple.class, eventType.getUnderlyingType());
        assertEquals(2, eventType.getPropertyNames().length);

        // Second call to create the event type, should be the same instance as the first
        EventType eventTypeTwo = beanEventAdapter.createBeanType(SupportBeanSimple.class);
        assertTrue(eventTypeTwo == eventType);
    }

    public void testInterfaceProperty()
    {
        // Assert implementations have full set of properties
        ISupportDImpl event = new ISupportDImpl("D", "BaseD", "BaseDBase");
        EventBean bean = beanEventAdapter.adapterForBean(event);
        assertEquals("D", bean.get("d"));
        assertEquals("BaseD", bean.get("baseD"));
        assertEquals("BaseDBase", bean.get("baseDBase"));
        assertEquals(3, bean.getEventType().getPropertyNames().length);
        ArrayAssertionUtil.assertEqualsAnyOrder(bean.getEventType().getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});

        // Assert intermediate interfaces have full set of fields
        EventType interfaceType = beanEventAdapter.createBeanType(ISupportD.class);
        ArrayAssertionUtil.assertEqualsAnyOrder(interfaceType.getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});
    }

    public void testMappedIndexedNestedProperty() throws Exception
    {
    	EventType eventType = beanEventAdapter.createBeanType(SupportBeanComplexProps.class);

        assertEquals(Map.class, eventType.getPropertyType("mapProperty"));
        assertEquals(String.class, eventType.getPropertyType("mapped('x')"));
        assertEquals(int.class, eventType.getPropertyType("indexed[1]"));
        assertEquals(SupportBeanComplexProps.SupportBeanSpecialGetterNested.class, eventType.getPropertyType("nested"));
        assertEquals(int[].class, eventType.getPropertyType("arrayProperty"));
    }
}
