package com.espertech.esper.event.property;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;

import java.lang.reflect.Method;

public class TestArrayMethodPropertyGetter extends TestCase
{
    private ArrayMethodPropertyGetter getter;
    private ArrayMethodPropertyGetter getterOutOfBounds;
    private EventBean event;
    private SupportBeanComplexProps bean;

    public void setUp() throws Exception
    {
        bean = SupportBeanComplexProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);
        getter = makeGetter(0);
        getterOutOfBounds = makeGetter(Integer.MAX_VALUE);
    }

    public void testCtor() throws Exception
    {
        try
        {
            makeGetter(-1);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testGet()
    {
        assertEquals(bean.getArrayProperty()[0], getter.get(event));

        assertNull(getterOutOfBounds.get(event));

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

    private ArrayMethodPropertyGetter makeGetter(int index) throws Exception
    {
        Method method = SupportBeanComplexProps.class.getMethod("getArrayProperty", new Class[0]);
        return new ArrayMethodPropertyGetter(method, index, SupportEventAdapterService.getService());
    }
}
