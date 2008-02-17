package com.espertech.esper.event.property;

import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import com.espertech.esper.support.bean.SupportBeanCombinedProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.event.*;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class TestNestedPropertyGetter extends TestCase
{
    private NestedPropertyGetter getter;
    private NestedPropertyGetter getterNull;
    private EventBean event;
    private SupportBeanCombinedProps bean;
    private BeanEventTypeFactory beanEventTypeFactory;

    public void setUp() throws Exception
    {
        beanEventTypeFactory = new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>());
        bean = SupportBeanCombinedProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);

        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();
        getters.add(makeGetterOne(0));
        getters.add(makeGetterTwo("0ma"));
        getter = new NestedPropertyGetter(getters, beanEventTypeFactory);

        getters = new LinkedList<EventPropertyGetter>();
        getters.add(makeGetterOne(2));
        getters.add(makeGetterTwo("0ma"));
        getterNull = new NestedPropertyGetter(getters, beanEventTypeFactory);
    }

    public void testGet()
    {
        assertEquals(bean.getIndexed(0).getMapped("0ma"), getter.get(event));

        // test null value returned
        assertNull(getterNull.get(event));

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

    private KeyedFastPropertyGetter makeGetterOne(int index)
    {
        FastClass fastClassOne = FastClass.create(SupportBeanCombinedProps.class);
        FastMethod methodOne = fastClassOne.getMethod("getIndexed", new Class[] {int.class});
        return new KeyedFastPropertyGetter(methodOne, index);
    }

    private KeyedFastPropertyGetter makeGetterTwo(String key)
    {
        FastClass fastClassTwo = FastClass.create(SupportBeanCombinedProps.NestedLevOne.class);
        FastMethod methodTwo = fastClassTwo.getMethod("getMapped", new Class[] {String.class});
        return new KeyedFastPropertyGetter(methodTwo, key);
    }
}
