package net.esper.event;

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.util.ArrayAssertionUtil;

public class TestMapEventType extends TestCase
{
    private EventType eventType;
    private EventAdapterService eventAdapterService;

    public void setUp()
    {
        eventAdapterService = SupportEventAdapterService.getService();

        Map<String, Class> testTypesMap = new HashMap<String, Class>();
        testTypesMap.put("myInt", int.class);
        testTypesMap.put("myString", String.class);
        testTypesMap.put("myNullableString", String.class);
        testTypesMap.put("mySupportBean", SupportBean.class);
        testTypesMap.put("myComplexBean", SupportBeanComplexProps.class);
        testTypesMap.put("myNullableSupportBean", SupportBean.class);
        eventType = new MapEventType(testTypesMap, eventAdapterService);
    }

    public void testGetPropertyNames()
    {
        String[] properties = eventType.getPropertyNames();
        ArrayAssertionUtil.assertEqualsAnyOrder(properties, new String[] {"myInt", "myString", "myNullableString", "mySupportBean", "myComplexBean", "myNullableSupportBean"});
    }

    public void testGetPropertyType()
    {
        assertEquals(int.class, eventType.getPropertyType("myInt"));
        assertEquals(String.class, eventType.getPropertyType("myString"));
        assertEquals(SupportBean.class, eventType.getPropertyType("mySupportBean"));
        assertEquals(SupportBeanComplexProps.class, eventType.getPropertyType("myComplexBean"));
        assertEquals(int.class, eventType.getPropertyType("mySupportBean.intPrimitive"));
        assertEquals(String.class, eventType.getPropertyType("myComplexBean.nested.nestedValue"));
        assertEquals(int.class, eventType.getPropertyType("myComplexBean.indexed[1]"));
        assertEquals(String.class, eventType.getPropertyType("myComplexBean.mapped('a')"));

        assertNull(eventType.getPropertyType("dummy"));
        assertNull(eventType.getPropertyType("mySupportBean.dfgdg"));
        assertNull(eventType.getPropertyType("xxx.intPrimitive"));
        assertNull(eventType.getPropertyType("myComplexBean.nested.nestedValueXXX"));
    }

    public void testGetUnderlyingType()
    {
        assertEquals(Map.class, eventType.getUnderlyingType());
    }

    public void testIsValidProperty()
    {
        assertTrue(eventType.isProperty("myInt"));
        assertTrue(eventType.isProperty("myString"));
        assertTrue(eventType.isProperty("mySupportBean.intPrimitive"));
        assertTrue(eventType.isProperty("myComplexBean.nested.nestedValue"));
        assertTrue(eventType.isProperty("myComplexBean.indexed[1]"));
        assertTrue(eventType.isProperty("myComplexBean.mapped('a')"));

        assertFalse(eventType.isProperty("dummy"));
        assertFalse(eventType.isProperty("mySupportBean.dfgdg"));
        assertFalse(eventType.isProperty("xxx.intPrimitive"));
        assertFalse(eventType.isProperty("myComplexBean.nested.nestedValueXXX"));
    }

    public void testGetGetter()
    {
        SupportBean nestedSupportBean = new SupportBean();
        nestedSupportBean.setIntPrimitive(100);
        SupportBeanComplexProps complexPropBean = SupportBeanComplexProps.makeDefaultBean();

        assertEquals(null, eventType.getGetter("dummy"));

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("myInt", 20);
        valuesMap.put("myString", "a");
        valuesMap.put("mySupportBean", nestedSupportBean);
        valuesMap.put("myComplexBean", complexPropBean);
        valuesMap.put("myNullableSupportBean", null);
        valuesMap.put("myNullableString", null);
        EventBean eventBean = new MapEventBean(valuesMap, eventType);

        EventPropertyGetter getter = eventType.getGetter("myInt");
        assertEquals(20, getter.get(eventBean));

        getter = eventType.getGetter("myString");
        assertEquals("a", getter.get(eventBean));

        getter = eventType.getGetter("myNullableString");
        assertNull(getter.get(eventBean));

        getter = eventType.getGetter("mySupportBean");
        assertEquals(nestedSupportBean, getter.get(eventBean));

        getter = eventType.getGetter("mySupportBean.intPrimitive");
        assertEquals(100, getter.get(eventBean));

        getter = eventType.getGetter("myNullableSupportBean.intPrimitive");
        assertNull(getter.get(eventBean));

        getter = eventType.getGetter("myComplexBean.nested.nestedValue");
        assertEquals("nestedValue", getter.get(eventBean));

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
        mapTwo.put("mySupportBean", SupportBean.class);
        mapTwo.put("myNullableSupportBean", SupportBean.class);
        mapTwo.put("myComplexBean", SupportBeanComplexProps.class);
        assertFalse((new MapEventType(mapTwo, eventAdapterService)).equals(eventType));
        mapTwo.put("myString", String.class);
        mapTwo.put("myNullableString", String.class);

        // compare, should equal
        assertEquals(new MapEventType(mapTwo, eventAdapterService), eventType);

        mapTwo.put("xx", int.class);
        assertFalse(eventType.equals(new MapEventType(mapTwo, eventAdapterService)));
        mapTwo.remove("xx");
        assertTrue(eventType.equals(new MapEventType(mapTwo, eventAdapterService)));

        mapTwo.put("myInt", Integer.class);
        assertFalse(eventType.equals(new MapEventType(mapTwo, eventAdapterService)));
        mapTwo.remove("myInt");
        assertFalse(eventType.equals(new MapEventType(mapTwo, eventAdapterService)));
        mapTwo.put("myInt", int.class);
        assertTrue(eventType.equals(new MapEventType(mapTwo, eventAdapterService)));
    }

    private static final Log log = LogFactory.getLog(TestMapEventType.class);
}