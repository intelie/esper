package net.esper.event;

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

import net.esper.support.event.SupportEventBeanFactory;

public class TestMapEventType extends TestCase
{
    EventType eventType;

    public void setUp()
    {
        Map<String, Class> testTypesMap = new HashMap<String, Class>();
        testTypesMap.put("myInt", int.class);
        testTypesMap.put("myString", String.class);
        eventType = new MapEventType(testTypesMap);
    }

    public void testGetPropertyNames()
    {
        String[] properties = eventType.getPropertyNames();
        assertTrue(properties.length == 2);
        assertTrue(properties[0].equals("myInt"));
        assertTrue(properties[1].equals("myString"));
    }

    public void testGetPropertyType()
    {
        assertEquals(int.class, eventType.getPropertyType("myInt"));
        assertEquals(String.class, eventType.getPropertyType("myString"));

        assertEquals(null, eventType.getPropertyType("dummy"));
    }

    public void testGetUnderlyingType()
    {
        assertEquals(Map.class, eventType.getUnderlyingType());
    }

    public void testIsValidProperty()
    {
        assertTrue(eventType.isProperty("myInt"));
        assertTrue(eventType.isProperty("myString"));

        assertFalse(eventType.isProperty("dummy"));
    }

    public void testGetGetter()
    {
        assertEquals(null, eventType.getGetter("dummy"));

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("myInt", 20);
        valuesMap.put("myString", "a");
        EventBean eventBean = new MapEventBean(valuesMap, eventType);

        EventPropertyGetter getter = eventType.getGetter("myInt");
        assertEquals(20, getter.get(eventBean));

        getter = eventType.getGetter("myString");
        assertEquals("a", getter.get(eventBean));

        try
        {
            eventBean = SupportEventBeanFactory.createObject(new Object());
            getter.get(eventBean);
            assertTrue(false);
        }
        catch (PropertyAccessException ex)
        {
            // Expected
            log.debug(".testGetGetter Expected exception, msg=" + ex.getMessage());
        }
    }

    public void testGetSuperTypes()
    {
        assertNull(eventType.getSuperTypes());
    }

    public void testEquals()
    {
        Map<String, Class> mapTwo = new LinkedHashMap<String, Class>();
        mapTwo.put("myInt", int.class);
        mapTwo.put("myString", String.class);

        // compare, should equal
        assertEquals(new MapEventType(mapTwo), eventType);

        mapTwo.put("xx", int.class);
        assertFalse(eventType.equals(new MapEventType(mapTwo)));
        mapTwo.remove("xx");
        assertTrue(eventType.equals(new MapEventType(mapTwo)));

        mapTwo.put("myInt", Integer.class);
        assertFalse(eventType.equals(new MapEventType(mapTwo)));
        mapTwo.remove("myInt");
        assertFalse(eventType.equals(new MapEventType(mapTwo)));
        mapTwo.put("myInt", int.class);
        assertTrue(eventType.equals(new MapEventType(mapTwo)));
    }

    private static final Log log = LogFactory.getLog(TestMapEventType.class);
}