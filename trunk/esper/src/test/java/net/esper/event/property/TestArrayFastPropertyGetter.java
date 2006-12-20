package net.esper.event.property;

import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;

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
        FastClass fastClass = FastClass.create(SupportBeanComplexProps.class);
        FastMethod method = fastClass.getMethod("getArrayProperty", new Class[0]);
        return new ArrayFastPropertyGetter(method, index);
    }
}
