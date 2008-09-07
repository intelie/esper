package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

import java.util.Map;

public class TestMappedProperty extends TestCase
{
    private MappedProperty[] mapped;
    private EventBean event;
    private BeanEventType eventType;

    public void setUp()
    {
        mapped = new MappedProperty[2];
        mapped[0] = new MappedProperty("mapped", "keyOne");
        mapped[1] = new MappedProperty("mapped", "keyTwo");

        event = SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean());
        eventType = (BeanEventType)event.getEventType();
    }

    public void testGetGetter()
    {
        Object[] expected = new String[] {"valueOne", "valueTwo"};
        for (int i = 0; i < mapped.length; i++)
        {
            EventPropertyGetter getter = mapped[i].getGetter(eventType);
            assertEquals(expected[i], getter.get(event));
        }

        // try invalid case
        MappedProperty mpd = new MappedProperty("dummy", "dummy");
        assertNull(mpd.getGetter(eventType));
    }

    public void testGetPropertyType()
    {
        Class[] expected = new Class[] {String.class, String.class};
        for (int i = 0; i < mapped.length; i++)
        {
            assertEquals(expected[i], mapped[i].getPropertyType(eventType));
        }

        // try invalid case
        MappedProperty mpd = new MappedProperty("dummy", "dummy");
        assertNull(mpd.getPropertyType(eventType));
        mpd = new MappedProperty("mapProperty", "dummy");
        assertNull(mpd.getPropertyType(eventType));
    }
}
