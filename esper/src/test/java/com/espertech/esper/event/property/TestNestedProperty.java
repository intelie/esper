package com.espertech.esper.event.property;

import com.espertech.esper.event.bean.BeanEventAdapter;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.event.bean.BeanEventTypeFactory;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.TestCase;

public class TestNestedProperty extends TestCase
{
    private NestedProperty[] nested;
    private EventBean event;
    private BeanEventTypeFactory beanEventTypeFactory;

    public void setUp()
    {
        beanEventTypeFactory = new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>(), SupportEventAdapterService.getService());

        nested = new NestedProperty[2];
        nested[0] = makeProperty(new String[] {"nested", "nestedValue"});
        nested[1] = makeProperty(new String[] {"nested", "nestedNested", "nestedNestedValue"});

        event = SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean());
    }

    public void testGetGetter()
    {
        EventPropertyGetter getter = nested[0].getGetter((BeanEventType)event.getEventType(), SupportEventAdapterService.getService());
        assertEquals("nestedValue", getter.get(event));

        getter = nested[1].getGetter((BeanEventType)event.getEventType(), SupportEventAdapterService.getService());
        assertEquals("nestedNestedValue", getter.get(event));
    }

    public void testGetPropertyType()
    {
        assertEquals(String.class, nested[0].getPropertyType((BeanEventType)event.getEventType(), SupportEventAdapterService.getService()));
        assertEquals(String.class, nested[1].getPropertyType((BeanEventType)event.getEventType(), SupportEventAdapterService.getService()));
    }

    private NestedProperty makeProperty(String[] propertyNames)
    {
        List<Property> properties = new LinkedList<Property>();
        for (String prop : propertyNames)
        {
            properties.add(new SimpleProperty(prop));
        }
        return new NestedProperty(properties);
    }
}
