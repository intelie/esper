package com.espertech.esper.event;

import junit.framework.TestCase;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportLegacyBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestReflectionPropFieldGetter extends TestCase
{
    EventBean unitTestBean;

    public void setUp()
    {
        SupportLegacyBean testEvent = new SupportLegacyBean("a");
        unitTestBean = SupportEventBeanFactory.createObject(testEvent);
    }

    public void testGetter() throws Exception
    {
        ReflectionPropFieldGetter getter = makeGetter(SupportLegacyBean.class, "fieldLegacyVal");
        assertEquals("a", getter.get(unitTestBean));

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

    private ReflectionPropFieldGetter makeGetter(Class clazz, String fieldName) throws Exception
    {
        Field field = clazz.getField(fieldName);
        ReflectionPropFieldGetter getter = new ReflectionPropFieldGetter(field);
        return getter;
    }

    private static final Log log = LogFactory.getLog(TestReflectionPropFieldGetter.class);
}
