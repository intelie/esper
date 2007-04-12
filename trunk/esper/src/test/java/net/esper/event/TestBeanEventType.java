package net.esper.event;

import junit.framework.*;
import net.esper.support.bean.*;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.*;
import java.io.Serializable;

public class TestBeanEventType extends TestCase
{
    private BeanEventAdapter beanEventAdapter;
    private BeanEventType eventTypeSimple;
    private BeanEventType eventTypeComplex;
    private BeanEventType eventTypeNested;

    private EventBean eventSimple;
    private EventBean eventComplex;
    private EventBean eventNested;

    private SupportBeanSimple objSimple;
    private SupportBeanComplexProps objComplex;
    private SupportBeanCombinedProps objCombined;

    public void setUp()
    {
        beanEventAdapter = new BeanEventAdapter();

        eventTypeSimple = new BeanEventType(SupportBeanSimple.class, beanEventAdapter, null, "1");
        eventTypeComplex = new BeanEventType(SupportBeanComplexProps.class, beanEventAdapter, null, "2");
        eventTypeNested = new BeanEventType(SupportBeanCombinedProps.class, beanEventAdapter, null, "3");

        objSimple = new SupportBeanSimple("a", 20);
        objComplex = SupportBeanComplexProps.makeDefaultBean();
        objCombined = SupportBeanCombinedProps.makeDefaultBean();

        eventSimple = beanEventAdapter.adapterForBean(objSimple, null);
        eventComplex = beanEventAdapter.adapterForBean(objComplex, null);
        eventNested = beanEventAdapter.adapterForBean(objCombined, null);
    }

    public void testGetPropertyNames()
    {
        String[] properties = eventTypeSimple.getPropertyNames();
        assertTrue(properties.length == 2);
        assertTrue(properties[0].equals("myInt"));
        assertTrue(properties[1].equals("myString"));

        properties = eventTypeComplex.getPropertyNames();
        ArrayAssertionUtil.assertEqualsAnyOrder(SupportBeanComplexProps.PROPERTIES, properties);

        properties = eventTypeNested.getPropertyNames();
        ArrayAssertionUtil.assertEqualsAnyOrder(SupportBeanCombinedProps.PROPERTIES, properties);
    }

    public void testGetUnderlyingType()
    {
        assertEquals(SupportBeanSimple.class, eventTypeSimple.getUnderlyingType());
    }

    public void testGetPropertyType()
    {
        assertEquals(String.class, eventTypeSimple.getPropertyType("myString"));
        assertNull(null, eventTypeSimple.getPropertyType("dummy"));
    }

    public void testIsValidProperty()
    {
        assertTrue(eventTypeSimple.isProperty("myString"));
        assertFalse(eventTypeSimple.isProperty("dummy"));
    }

    public void testGetGetter()
    {
        assertEquals(null, eventTypeSimple.getGetter("dummy"));

        EventPropertyGetter getter = eventTypeSimple.getGetter("myInt");
        assertEquals(20, getter.get(eventSimple));
        getter = eventTypeSimple.getGetter("myString");
        assertEquals("a", getter.get(eventSimple));

        try
        {
            // test mismatch between bean and object
            EventBean eventBean = beanEventAdapter.adapterForBean(new Object(), null);
            getter.get(eventBean);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // Expected
        }
    }

    public void testProperties()
    {
        Class nestedOne = SupportBeanCombinedProps.NestedLevOne.class;
        Class nestedOneArr = SupportBeanCombinedProps.NestedLevOne[].class;
        Class nestedTwo = SupportBeanCombinedProps.NestedLevTwo.class;

        // test nested/combined/indexed/mapped properties
        // PropertyName                 isProperty              getType         hasGetter   getterValue
        List<PropTestDesc> tests = new LinkedList<PropTestDesc>();

        tests = new LinkedList<PropTestDesc>();
        tests.add(new PropTestDesc("simpleProperty", true, String.class, true, "simple"));
        tests.add(new PropTestDesc("dummy", false, null, false, null));
        tests.add(new PropTestDesc("indexed", false, null, false, null));
        tests.add(new PropTestDesc("indexed[1]", true, int.class, true, 2));
        tests.add(new PropTestDesc("nested", true, SupportBeanComplexProps.SupportBeanSpecialGetterNested.class, true, objComplex.getNested()));
        tests.add(new PropTestDesc("nested.nestedValue", true, String.class, true, objComplex.getNested().getNestedValue()));
        tests.add(new PropTestDesc("nested.nestedNested", true, SupportBeanComplexProps.SupportBeanSpecialGetterNestedNested.class, true, objComplex.getNested().getNestedNested()));
        tests.add(new PropTestDesc("nested.nestedNested.nestedNestedValue", true, String.class, true, objComplex.getNested().getNestedNested().getNestedNestedValue()));
        tests.add(new PropTestDesc("nested.dummy", false, null, false, null));
        tests.add(new PropTestDesc("mapped", false, null, false, null));
        tests.add(new PropTestDesc("mapped('keyOne')", true, String.class, true, "valueOne"));
        tests.add(new PropTestDesc("arrayProperty", true, int[].class, true, objComplex.getArrayProperty()));
        tests.add(new PropTestDesc("arrayProperty[1]", true, int.class, true, 20));
        tests.add(new PropTestDesc("mapProperty('xOne')", false, null, false, null));
        tests.add(new PropTestDesc("google('x')", false, null, false, null));
        tests.add(new PropTestDesc("mapped('x')", true, String.class, true, null));
        tests.add(new PropTestDesc("mapped('x').x", false, null, false, null));
        tests.add(new PropTestDesc("mapProperty", true, Map.class, true, objComplex.getMapProperty()));
        runTest(tests, eventTypeComplex, eventComplex);

        tests = new LinkedList<PropTestDesc>();
        tests.add(new PropTestDesc("dummy", false, null, false, null));
        tests.add(new PropTestDesc("myInt", true, int.class, true, objSimple.getMyInt()));
        tests.add(new PropTestDesc("myString", true, String.class, true, objSimple.getMyString()));
        tests.add(new PropTestDesc("dummy('a')", false, null, false, null));
        tests.add(new PropTestDesc("dummy[1]", false, null, false, null));
        tests.add(new PropTestDesc("dummy.nested", false, null, false, null));
        runTest(tests, eventTypeSimple, eventSimple);

        tests = new LinkedList<PropTestDesc>();
        tests.add(new PropTestDesc("indexed", false, null, false, null));
        tests.add(new PropTestDesc("indexed[1]", true, nestedOne, true, objCombined.getIndexed(1)));
        tests.add(new PropTestDesc("indexed.mapped", false, null, false, null));
        tests.add(new PropTestDesc("indexed[1].mapped", false, null, false, null));
        tests.add(new PropTestDesc("array", true, nestedOneArr, true, objCombined.getArray()));
        tests.add(new PropTestDesc("array.mapped", false, null, false, null));
        tests.add(new PropTestDesc("array[0]", true, nestedOne, true, objCombined.getArray()[0]));
        tests.add(new PropTestDesc("array[1].mapped", false, null, false, null));
        tests.add(new PropTestDesc("array[1].mapped('x')", true, nestedTwo, true, objCombined.getArray()[1].getMapped("x")));
        tests.add(new PropTestDesc("array[1].mapped('1mb')", true, nestedTwo, true, objCombined.getArray()[1].getMapped("1mb")));
        tests.add(new PropTestDesc("indexed[1].mapped('x')", true, nestedTwo, true, objCombined.getIndexed(1).getMapped("x")));
        tests.add(new PropTestDesc("indexed[1].mapped('x').value", true, String.class, true, null));
        tests.add(new PropTestDesc("indexed[1].mapped('1mb')", true, nestedTwo, true, objCombined.getIndexed(1).getMapped("1mb")));
        tests.add(new PropTestDesc("indexed[1].mapped('1mb').value", true, String.class, true, objCombined.getIndexed(1).getMapped("1mb").getValue()));
        tests.add(new PropTestDesc("array[1].mapprop", true, Map.class, true, objCombined.getIndexed(1).getMapprop()));
        tests.add(new PropTestDesc("array[1].mapprop('a')", false, null, false, null));
        tests.add(new PropTestDesc("array[1].mapprop('a').value", false, null, false, null));
        tests.add(new PropTestDesc("indexed[1].mapprop", true, Map.class, true, objCombined.getIndexed(1).getMapprop()));
        runTest(tests, eventTypeNested, eventNested);

        tryInvalidIsProperty(eventTypeComplex, "x[");
        tryInvalidIsProperty(eventTypeComplex, "dummy()");
        tryInvalidIsProperty(eventTypeComplex, "nested.xx['a']");
        tryInvalidGetPropertyType(eventTypeComplex, "x[");
        tryInvalidGetPropertyType(eventTypeComplex, "dummy()");
        tryInvalidGetPropertyType(eventTypeComplex, "nested.xx['a']");
        tryInvalidGetPropertyType(eventTypeNested, "dummy[(");
        tryInvalidGetPropertyType(eventTypeNested, "array[1].mapprop[x].value");
    }

    public void testGetDeepSuperTypes()
    {
        BeanEventType type = new BeanEventType(ISupportAImplSuperGImplPlus.class, beanEventAdapter, null, "1");

        List<EventType> deepSuperTypes = new LinkedList<EventType>();
        for (Iterator<EventType> it = type.getDeepSuperTypes(); it.hasNext();)
        {
            deepSuperTypes.add(it.next());
        }


        assertEquals(5, deepSuperTypes.size());
        ArrayAssertionUtil.assertEqualsAnyOrder(
                deepSuperTypes.toArray(),
                new EventType[] {
                    beanEventAdapter.createOrGetBeanType(ISupportAImplSuperG.class),
                    beanEventAdapter.createOrGetBeanType(ISupportBaseAB.class),
                    beanEventAdapter.createOrGetBeanType(ISupportA.class),
                    beanEventAdapter.createOrGetBeanType(ISupportB.class),
                    beanEventAdapter.createOrGetBeanType(ISupportC.class)
                });
    }

    public void testGetSuper()
    {
        LinkedHashSet<Class> classes = new LinkedHashSet<Class>();
        BeanEventType.getSuper(ISupportAImplSuperGImplPlus.class, classes);

        assertEquals(7, classes.size());
        ArrayAssertionUtil.assertEqualsAnyOrder(
                classes.toArray(),
                new Class[] {
                    ISupportAImplSuperG.class, ISupportBaseAB.class,
                    ISupportA.class, ISupportB.class, ISupportC.class,
                    Serializable.class, Object.class,
                }
        );

        classes.clear();
        BeanEventType.getSuper(Object.class, classes);
        assertEquals(0, classes.size());
    }

    public void testGetSuperTypes()
    {
        eventTypeSimple = new BeanEventType(ISupportAImplSuperGImplPlus.class, beanEventAdapter, null, "1");

        EventType[] superTypes = eventTypeSimple.getSuperTypes();
        assertEquals(3, superTypes.length);
        assertEquals(ISupportAImplSuperG.class, superTypes[0].getUnderlyingType());
        assertEquals(ISupportB.class, superTypes[1].getUnderlyingType());
        assertEquals(ISupportC.class, superTypes[2].getUnderlyingType());

        eventTypeSimple = new BeanEventType(Object.class, beanEventAdapter, null, "2");
        superTypes = eventTypeSimple.getSuperTypes();
        assertEquals(0, superTypes.length);

        BeanEventType type = new BeanEventType(ISupportD.class, beanEventAdapter, null, "3");
        assertEquals(3, type.getPropertyNames().length);
        ArrayAssertionUtil.assertEqualsAnyOrder(
                type.getPropertyNames(),
                new String[] {"d", "baseD", "baseDBase"});
    }

    private static void tryInvalidGetPropertyType(BeanEventType type, String property)
    {
        try
        {
            type.getPropertyType(property);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }

    private static void tryInvalidIsProperty(BeanEventType type, String property)
    {
        try
        {
            type.getPropertyType(property);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }

    private static void runTest(List<PropTestDesc> tests, BeanEventType eventType, EventBean eventBean)
    {
        for (PropTestDesc desc : tests)
        {
            runTest(desc, eventType, eventBean);
        }
    }

    private static void runTest(PropTestDesc test, BeanEventType eventType, EventBean eventBean)
    {
        String propertyName = test.getPropertyName();

        assertEquals("isProperty mismatch on '" + propertyName + "',", test.isProperty(), eventType.isProperty(propertyName));
        assertEquals("getPropertyType mismatch on '" + propertyName + "',", test.getClazz(), eventType.getPropertyType(propertyName));

        EventPropertyGetter getter = eventType.getGetter(propertyName);
        if (getter == null)
        {
            assertFalse("getGetter null on '" + propertyName + "',", test.isHasGetter());
        }
        else
        {
            assertTrue("getGetter not null on '" + propertyName + "',", test.isHasGetter());
            if (test.getGetterReturnValue() == NullPointerException.class)
            {
                try
                {
                    getter.get(eventBean);
                    fail("getGetter not throwing null pointer on '" + propertyName);
                }
                catch (NullPointerException ex)
                {
                    // expected
                }
            }
            else
            {
                Object value = getter.get(eventBean);
                assertEquals("getter value mismatch on '" + propertyName + "',", test.getGetterReturnValue(), value);
            }
        }
    }

    public static class PropTestDesc
    {
        private String propertyName;
        private boolean isProperty;
        private Class clazz;
        private boolean hasGetter;
        private Object getterReturnValue;

        public PropTestDesc(String propertyName, boolean property, Class clazz, boolean hasGetter, Object getterReturnValue)
        {
            this.propertyName = propertyName;
            isProperty = property;
            this.clazz = clazz;
            this.hasGetter = hasGetter;
            this.getterReturnValue = getterReturnValue;
        }

        public String getPropertyName()
        {
            return propertyName;
        }

        public boolean isProperty()
        {
            return isProperty;
        }

        public Class getClazz()
        {
            return clazz;
        }

        public boolean isHasGetter()
        {
            return hasGetter;
        }

        public Object getGetterReturnValue()
        {
            return getterReturnValue;
        }
    }

}