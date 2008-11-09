package com.espertech.esper.event.property;

import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

public class TestKeyedFastPropertyGetter extends TestCase
{
    private KeyedFastPropertyGetter getter;
    private EventBean event;
    private SupportBeanComplexProps bean;

    public void setUp() throws Exception
    {
        bean = SupportBeanComplexProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);
        FastClass fastClass = FastClass.create(Thread.currentThread().getContextClassLoader(), SupportBeanComplexProps.class);
        FastMethod method = fastClass.getMethod("getIndexed", new Class[] {int.class});
        getter = new KeyedFastPropertyGetter(method, 1);
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
