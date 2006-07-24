package net.esper.event;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterServiceImpl;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

public class TestEventAdapterServiceImpl extends TestCase
{
    private EventAdapterServiceImpl typeResService;

    public void setUp()
    {
        typeResService = new EventAdapterServiceImpl();
    }

    public void testCreateMapType()
    {
        Map<String, Class> testTypesMap;
        testTypesMap = new HashMap<String, Class>();
        testTypesMap.put("key1", String.class);
        EventType eventType = typeResService.createAnonymousMapType(testTypesMap);

        assertEquals(Map.class, eventType.getUnderlyingType());
        assertEquals(1, eventType.getPropertyNames().length);
        assertEquals("key1", eventType.getPropertyNames()[0]);
    }

    public void testGetType()
    {
        typeResService.addBeanType("NAME", TestEventAdapterServiceImpl.class.getName());

        EventType type = typeResService.getEventType("NAME");
        assertEquals(TestEventAdapterServiceImpl.class, type.getUnderlyingType());

        EventType typeTwo = typeResService.getEventType(TestEventAdapterServiceImpl.class.getName());
        assertSame(typeTwo, typeTwo);

        assertNull(typeResService.getEventType("xx"));
    }

    public void testAddInvalid()
    {
        try
        {
            typeResService.addBeanType("x", "xx");
            fail();
        }
        catch (EventAdapterException ex)
        {
            // Expected
        }
    }

    public void testAddMapType()
    {
        Map<String, Class> props = new HashMap<String, Class>();
        props.put("a", Long.class);
        props.put("b", String.class);

        // check result type
        EventType typeOne = typeResService.addMapType("latencyEvent", props);
        assertEquals(Long.class, typeOne.getPropertyType("a"));
        assertEquals(String.class, typeOne.getPropertyType("b"));
        assertEquals(2, typeOne.getPropertyNames().length);

        // add the same type with the same name, should succeed and return the same reference
        EventType typeTwo = typeResService.addMapType("latencyEvent", props);
        assertSame(typeOne, typeTwo);

        // add the same name with a different type, should fail
        props.put("b", boolean.class);
        try
        {
            typeResService.addMapType("latencyEvent", props);
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testAddClassName()
    {
        EventType typeOne = typeResService.addBeanType("latencyEvent", SupportBean.class.getName());
        assertEquals(SupportBean.class, typeOne.getUnderlyingType());

        EventType typeTwo = typeResService.addBeanType("latencyEvent", SupportBean.class.getName());
        assertSame(typeOne, typeTwo);

        try
        {
            typeResService.addBeanType("latencyEvent", SupportBean_A.class.getName());
            fail();
        }
        catch (EventAdapterException ex)
        {
            // expected
        }
    }

    public void testWrap()
    {
        SupportBean bean = new SupportBean();
        EventBean event = typeResService.adapterForBean(bean);
        assertSame(event.getUnderlying(), bean);
    }

    public void testCreateAddToEventType()
    {
        Map<String, Class> schema = new HashMap<String, Class>();
        schema.put("STDDEV", Double.class);
        EventType parentEventType = typeResService.createAnonymousMapType(schema);

        EventType newEventType = typeResService.createAddToEventType(parentEventType, new String[] {"test"}, new Class[] {Integer.class});

        assertTrue(newEventType.isProperty("test"));
        assertEquals(Integer.class, newEventType.getPropertyType("test"));
    }
}
