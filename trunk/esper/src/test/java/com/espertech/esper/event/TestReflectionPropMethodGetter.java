package com.espertech.esper.event;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestReflectionPropMethodGetter extends TestCase
{
    EventBean unitTestBean;

    public void setUp()
    {
        SupportBean testEvent = new SupportBean();
        testEvent.setIntPrimitive(10);
        testEvent.setString("a");
        testEvent.setDoubleBoxed(null);

        unitTestBean = SupportEventBeanFactory.createObject(testEvent);
    }

    public void testGetter() throws Exception
    {
        ReflectionPropMethodGetter getter = makeGetter(SupportBean.class, "getIntPrimitive");
        assertEquals(10, getter.get(unitTestBean));

        getter = makeGetter(SupportBean.class, "getString");
        assertEquals("a", getter.get(unitTestBean));

        getter = makeGetter(SupportBean.class, "getDoubleBoxed");
        assertEquals(null, getter.get(unitTestBean));

        try
        {
            EventBean eventBean = SupportEventBeanFactory.createObject(new Object());
            getter.get(eventBean);
            assertTrue(false);
        }
        catch (PropertyAccessException ex)
        {
            // Expected
            log.debug(".testGetter Expected exception, msg=" + ex.getMessage());
        }
    }

    public void testPerformance() throws Exception
    {
        ReflectionPropMethodGetter getter = makeGetter(SupportBean.class, "getIntPrimitive");

        log.info(".testPerformance Starting test");

        for (int i = 0; i < 10; i++)   // Change to 1E8 for performance testing
        {
            int value = (Integer) getter.get(unitTestBean);
            assertEquals(10, value);
        }

        log.info(".testPerformance Done test");
    }

    private ReflectionPropMethodGetter makeGetter(Class clazz, String methodName) throws Exception
    {
        Method method = clazz.getMethod(methodName, new Class[] {});

        ReflectionPropMethodGetter getter = new ReflectionPropMethodGetter(method);

        return getter;
    }

    private static final Log log = LogFactory.getLog(TestReflectionPropMethodGetter.class);
}
