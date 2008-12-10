package com.espertech.esper.event;

import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.bean.SupportBeanCombinedProps;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.PropertyAccessException;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestBeanEventBean extends TestCase
{
    SupportBean testEvent;

    public void setUp()
    {
        testEvent = new SupportBean();
        testEvent.setIntPrimitive(10);
    }

    public void testGet()
    {
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        BeanEventBean eventBean = new BeanEventBean(testEvent, eventType);

        assertEquals(eventType, eventBean.getEventType());
        assertEquals(testEvent, eventBean.getUnderlying());

        assertEquals(10, eventBean.get("intPrimitive"));

        // Test wrong property name
        try
        {
            eventBean.get("dummy");
            assertTrue(false);
        }
        catch (PropertyAccessException ex)
        {
            // Expected
            log.debug(".testGetter Expected exception, msg=" + ex.getMessage());
        }

        // Test wrong event type - not possible to happen under normal use
        try
        {
            eventType = SupportEventTypeFactory.createBeanType(SupportBeanSimple.class);
            eventBean = new BeanEventBean(testEvent, eventType);
            eventBean.get("myString");
            assertTrue(false);
        }
        catch (PropertyAccessException ex)
        {
            // Expected
            log.debug(".testGetter Expected exception, msg=" + ex.getMessage());
        }
    }

    public void testGetComplexProperty()
    {
        SupportBeanCombinedProps event = SupportBeanCombinedProps.makeDefaultBean();
        EventBean eventBean = SupportEventBeanFactory.createObject(event);

        assertEquals("0ma0", eventBean.get("indexed[0].mapped('0ma').value"));
        assertEquals(String.class, eventBean.getEventType().getPropertyType("indexed[0].mapped('0ma').value"));
        assertNotNull(eventBean.getEventType().getGetter("indexed[0].mapped('0ma').value"));
        assertEquals("0ma1", eventBean.get("indexed[0].mapped('0mb').value"));
        assertEquals("1ma0", eventBean.get("indexed[1].mapped('1ma').value"));
        assertEquals("1ma1", eventBean.get("indexed[1].mapped('1mb').value"));

        assertEquals("0ma0", eventBean.get("array[0].mapped('0ma').value"));
        assertEquals("1ma1", eventBean.get("array[1].mapped('1mb').value"));

        tryInvalidGet(eventBean, "array[0].mapprop('0ma').value");
        tryInvalidGet(eventBean, "dummy");
        tryInvalidGet(eventBean, "dummy[1]");
        tryInvalidGet(eventBean, "dummy('dd')");
        tryInvalidGet(eventBean, "dummy.dummy1");

        tryInvalidGetFragment(eventBean, "indexed");
        assertEquals(SupportBeanCombinedProps.NestedLevOne.class, ((EventBean) eventBean.getFragment("indexed[0]")).getEventType().getUnderlyingType());
    }

    private static void tryInvalidGet(EventBean eventBean, String propName)
    {
        try
        {
            eventBean.get(propName);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }

        assertNull(eventBean.getEventType().getPropertyType(propName));
        assertNull(eventBean.getEventType().getGetter(propName));
    }

    private static void tryInvalidGetFragment(EventBean eventBean, String propName)
    {
        try
        {
            eventBean.getFragment(propName);
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }

    private static final Log log = LogFactory.getLog(TestBeanEventBean.class);
}
