package net.esper.event.property;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.*;

public class TestSimpleProperty extends TestCase
{
    private SimpleProperty prop;
    private SimpleProperty invalidPropMap;
    private SimpleProperty invalidPropIndexed;
    private SimpleProperty invalidDummy;
    private EventBean event;
    private BeanEventType eventType;

    public void setUp()
    {
        prop = new SimpleProperty("simpleProperty");
        invalidPropMap = new SimpleProperty("mapped");
        invalidPropIndexed = new SimpleProperty("indexed");
        invalidDummy = new SimpleProperty("dummy");
        event = SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean());
        eventType = (BeanEventType)event.getEventType();
    }

    public void testGetGetter()
    {
        EventPropertyGetter getter = prop.getGetter(eventType);
        assertEquals("simple", getter.get(event));

        assertNull(invalidDummy.getGetter(eventType));
        assertNull(invalidPropMap.getGetter(eventType));
        assertNull(invalidPropIndexed.getGetter(eventType));
    }

    public void testGetPropertyType()
    {
        assertEquals(String.class, prop.getPropertyType(eventType));

        assertNull(invalidDummy.getGetter(eventType));
        assertNull(invalidPropMap.getGetter(eventType));
        assertNull(invalidPropIndexed.getGetter(eventType));
    }

    private void tryInvalidGetGetter(SimpleProperty property)
    {
        try
        {
            property.getGetter(eventType);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }

    private void tryInvalidGetPropertyType(SimpleProperty property)
    {
        try
        {
            property.getPropertyType(eventType);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }
}
