package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

public class TestIndexedProperty extends TestCase
{
    private IndexedProperty[] indexed;
    private EventBean event;
    private BeanEventType eventType;

    public void setUp()
    {
        indexed = new IndexedProperty[4];
        indexed[0] = new IndexedProperty("indexed", 0);
        indexed[1] = new IndexedProperty("indexed", 1);
        indexed[2] = new IndexedProperty("arrayProperty", 0);
        indexed[3] = new IndexedProperty("arrayProperty", 1);

        event = SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean());
        eventType = (BeanEventType)event.getEventType();
    }

    public void testGetGetter()
    {
        int[] expected = new int[] {1, 2, 10, 20};
        for (int i = 0; i < indexed.length; i++)
        {
            EventPropertyGetter getter = indexed[i].getGetter(eventType);
            assertEquals(expected[i], getter.get(event));
        }

        // try invalid case
        IndexedProperty ind = new IndexedProperty("dummy", 0);
        assertNull(ind.getGetter(eventType));
    }

    public void testGetPropertyType()
    {
        Class[] expected = new Class[] {int.class, int.class, int.class, int.class};
        for (int i = 0; i < indexed.length; i++)
        {
            assertEquals(expected[i], indexed[i].getPropertyType(eventType));
        }

        // try invalid case
        IndexedProperty ind = new IndexedProperty("dummy", 0);
        assertNull(ind.getPropertyType(eventType));
    }
}
