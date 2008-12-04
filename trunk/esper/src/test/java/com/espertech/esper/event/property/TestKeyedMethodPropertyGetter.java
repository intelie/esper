package com.espertech.esper.event.property;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;

import java.lang.reflect.Method;

public class TestKeyedMethodPropertyGetter extends TestCase
{
    private KeyedMethodPropertyGetter getter;
    private EventBean event;
    private SupportBeanComplexProps bean;

    public void setUp() throws Exception
    {
        bean = SupportBeanComplexProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);
        Method method = SupportBeanComplexProps.class.getMethod("getIndexed", new Class[] {int.class});
        getter = new KeyedMethodPropertyGetter(method, 1);
    }

    public void testGet()
    {
        assertEquals(bean.getIndexed(1), getter.get(event));

        try
        {
            getter.get(SupportEventBeanFactory.createObject(""));
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }
}
