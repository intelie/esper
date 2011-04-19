package com.espertech.esper.event.bean;

import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.event.bean.ArrayFastPropertyGetter;

public class TestArrayFastPropertyGetter extends TestCase
{
    private ArrayFastPropertyGetter getter;
    private ArrayFastPropertyGetter getterOutOfBounds;
    private EventBean event;
    private SupportBeanComplexProps bean;

    public void setUp() throws Exception
    {
        bean = SupportBeanComplexProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);
        getter = makeGetter(0);
        getterOutOfBounds = makeGetter(Integer.MAX_VALUE);
    }

    public void testCtor()
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
        assertEquals(bean.getArrayProperty()[0], getter.get(event, 0));

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

    private ArrayFastPropertyGetter makeGetter(int index)
    {
        FastClass fastClass = FastClass.create(Thread.currentThread().getContextClassLoader(), SupportBeanComplexProps.class);
        FastMethod method = fastClass.getMethod("getArrayProperty", new Class[0]);
        return new ArrayFastPropertyGetter(method, index, SupportEventAdapterService.getService());
    }
}
