package com.espertech.esper.event;

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestMapEventType extends TestCase
{
    private MapEventType eventType;
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
        testTypesMap.put("myNullType", null);
        eventType = new MapEventType("", testTypesMap, eventAdapterService);
    }

    public void testGetPropertyNames()
    {
        String[] properties = eventType.getPropertyNames();
        ArrayAssertionUtil.assertEqualsAnyOrder(properties, new String[] {"myInt", "myString", "myNullableString", "mySupportBean", "myComplexBean", "myNullableSupportBean", "myNullType"});
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
        assertEquals(null, eventType.getPropertyType("myNullType"));

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
        assertTrue(eventType.isProperty("myNullType"));

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
        assertFalse((new MapEventType("", mapTwo, eventAdapterService)).equals(eventType));
        mapTwo.put("myString", String.class);
        mapTwo.put("myNullableString", String.class);
        mapTwo.put("myNullType", null);

        // compare, should equal
        assertEquals(new MapEventType("", mapTwo, eventAdapterService), eventType);
        assertFalse((new MapEventType("google", mapTwo, eventAdapterService)).equals(eventType));

        mapTwo.put("xx", int.class);
        assertFalse(eventType.equals(new MapEventType("", mapTwo, eventAdapterService)));
        mapTwo.remove("xx");
        assertTrue(eventType.equals(new MapEventType("", mapTwo, eventAdapterService)));

        mapTwo.put("myInt", Integer.class);
        assertTrue(eventType.equals(new MapEventType("", mapTwo, eventAdapterService)));
        mapTwo.remove("myInt");
        assertFalse(eventType.equals(new MapEventType("", mapTwo, eventAdapterService)));
        mapTwo.put("myInt", int.class);
        assertTrue(eventType.equals(new MapEventType("", mapTwo, eventAdapterService)));

        // Test boxed and primitive compatible
        Map<String, Class> mapOne = new LinkedHashMap<String, Class>();
        mapOne.put("myInt", int.class);
        mapTwo = new LinkedHashMap<String, Class>();
        mapTwo.put("myInt", Integer.class);
        assertEquals(new MapEventType("T1", mapOne, eventAdapterService), new MapEventType("T1", mapTwo, eventAdapterService));
    }

    public void testGetFromMap()
    {
        SupportBean nestedSupportBean = new SupportBean();
        nestedSupportBean.setIntPrimitive(100);
        SupportBeanComplexProps complexPropBean = SupportBeanComplexProps.makeDefaultBean();

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put("myInt", 20);
        valuesMap.put("myString", "a");
        valuesMap.put("mySupportBean", nestedSupportBean);
        valuesMap.put("myComplexBean", complexPropBean);
        valuesMap.put("myNullableSupportBean", null);
        valuesMap.put("myNullableString", null);

        assertEquals(20, eventType.getValue("myInt", valuesMap));
        assertEquals(100, eventType.getValue("mySupportBean.intPrimitive", valuesMap));
        assertEquals("nestedValue", eventType.getValue("myComplexBean.nested.nestedValue", valuesMap));
    }

    public void testNestedMap()
    {
        Map<String, Object> levelThree = new HashMap<String, Object>();
        levelThree.put("simpleThree", long.class);
        levelThree.put("objThree", SupportBean_D.class);
        levelThree.put("nodefmapThree", Map.class);

        Map<String, Object> levelTwo = new HashMap<String, Object>();
        levelTwo.put("simpleTwo", float.class);
        levelTwo.put("objTwo", SupportBean_C.class);
        levelTwo.put("nodefmapTwo", Map.class);
        levelTwo.put("mapTwo", levelThree);

        Map<String, Object> levelOne = new HashMap<String, Object>();
        levelOne.put("simpleOne", Integer.class);
        levelOne.put("objOne", SupportBean_B.class);
        levelOne.put("nodefmapOne", Map.class);
        levelOne.put("mapOne", levelTwo);

        Map<String, Object> levelZero = new HashMap<String, Object>();
        levelZero.put("simple", double.class);
        levelZero.put("obj", SupportBean_A.class);
        levelZero.put("nodefmap", Map.class);
        levelZero.put("map", levelOne);

        MapEventType mapType = new MapEventType("M1", eventAdapterService, levelZero);
        Map<String, Object> testData = getTestData();
        MapEventBean event = new MapEventBean(testData, mapType);

        Object[][] expected = new Object[][] {
                {"map.mapOne.simpleTwo", float.class,       300f},
                {"nodefmap.item?",      Object.class,       "|nodefmap.item|"},
                {"map.objOne",      SupportBean_B.class,    new SupportBean_B("B1")},
                {"map.simpleOne",   Integer.class,          20},
                {"map.mapOne",      Map.class,              ((Map)testData.get("map")).get("mapOne")},
                {"map.mapOne.objTwo", SupportBean_C.class,  new SupportBean_C("C1")},
                {"map.mapOne.mapTwo", Map.class,            ((Map) ((Map)testData.get("map")).get("mapOne")).get("mapTwo")},
                {"map.mapOne.mapTwo.simpleThree", long.class,  4000L},
                {"map.mapOne.mapTwo.objThree", SupportBean_D.class, new SupportBean_D("D1")},
                {"simple",      double.class,               1d},
                {"obj",         SupportBean_A.class,        new SupportBean_A("A1")},
                {"nodefmap",    Map.class,                  testData.get("nodefmap")},
                {"map",         Map.class,                  testData.get("map")},
                    };

        // assert getter available for all properties
        for (int i = 0; i < expected.length; i++)
        {
            String propName = (String) expected[i][0];
            assertNotNull("failed for property:" + propName, mapType.getGetter(propName));
        }

        // assert property types
        for (int i = 0; i < expected.length; i++)
        {
            String propName = (String) expected[i][0];
            Class propType = (Class) expected[i][1];
            assertEquals("failed for property:" + propName, propType, mapType.getPropertyType(propName));
        }

        // assert property names
        String[] expectedPropNames = new String[] {"simple", "obj", "map", "nodefmap"};
        String[] receivedPropNames = mapType.getPropertyNames();
        ArrayAssertionUtil.assertEqualsAnyOrder(expectedPropNames, receivedPropNames);

        // assert get value through (1) type getter  (2) event-get
        for (int i = 0; i < expected.length; i++)
        {
            String propName = (String) expected[i][0];
            Object valueExpected = expected[i][2];
            assertEquals("failed for property type-getter:" + propName, valueExpected, mapType.getGetter(propName).get(event));
            assertEquals("failed for property event-getter:" + propName, valueExpected, event.get(propName));
        }

        // assert access to objects nested within
        expected = new Object[][] {
                {"map.objOne.id",       String.class,    "B1"},
                {"map.mapOne.objTwo.id", String.class,   "C1"},
                {"obj.id",              String.class,    "A1"},
                    };
        for (int i = 0; i < expected.length; i++)
        {
            String propName = (String) expected[i][0];
            Class propType = (Class) expected[i][1];
            Object valueExpected = expected[i][2];
            EventPropertyGetter getter = mapType.getGetter(propName);
            assertEquals("failed for property:" + propName, propType, mapType.getPropertyType(propName));
            assertEquals("failed for property type-getter:" + propName, valueExpected, getter.get(event));
            assertEquals("failed for property event-getter:" + propName, valueExpected, event.get(propName));
        }
    }

    private Map<String, Object> getTestData()
    {
        Map<String, Object> levelThree = new HashMap<String, Object>();
        levelThree.put("simpleThree", 4000L);
        levelThree.put("objThree", new SupportBean_D("D1"));

        Map<String, Object> levelTwo = new HashMap<String, Object>();
        levelTwo.put("simpleTwo", 300f);
        levelTwo.put("objTwo", new SupportBean_C("C1"));
        levelTwo.put("mapTwo", levelThree);

        Map<String, Object> levelOne = new HashMap<String, Object>();
        levelOne.put("simpleOne", 20);
        levelOne.put("objOne", new SupportBean_B("B1"));
        levelOne.put("mapOne", levelTwo);

        Map<String, Object> levelZero = new HashMap<String, Object>();
        levelZero.put("simple", 1d);
        levelZero.put("obj", new SupportBean_A("A1"));
        levelZero.put("map", levelOne);
        Map<String, Object> noDefZero = new HashMap<String, Object>();
        noDefZero.put("item", "|nodefmap.item|");
        levelZero.put("nodefmap", noDefZero);

        return levelZero;
    }

    private static final Log log = LogFactory.getLog(TestMapEventType.class);
}
