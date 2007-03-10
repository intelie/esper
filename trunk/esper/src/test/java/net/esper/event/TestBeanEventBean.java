package net.esper.event;

import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.bean.SupportBeanCombinedProps;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;
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
        BeanEventBean eventBean = new BeanEventBean(testEvent, eventType, 0);

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
            eventBean = new BeanEventBean(testEvent, eventType, 1);
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
        assertEquals("0ma1", eventBean.get("indexed[0].mapped('0mb').value"));
        assertEquals("1ma0", eventBean.get("indexed[1].mapped('1ma').value"));
        assertEquals("1ma1", eventBean.get("indexed[1].mapped('1mb').value"));

        assertEquals("0ma0", eventBean.get("array[0].mapped('0ma').value"));
        assertEquals("1ma1", eventBean.get("array[1].mapped('1mb').value"));

        tryInvalid(eventBean, "array[0].mapprop('0ma').value");
        tryInvalid(eventBean, "dummy");
        tryInvalid(eventBean, "dummy[1]");
        tryInvalid(eventBean, "dummy('dd')");
        tryInvalid(eventBean, "dummy.dummy1");
    }

    private static void tryInvalid(EventBean eventBean, String propName)
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
    }

    private static final Log log = LogFactory.getLog(TestBeanEventBean.class);
}
