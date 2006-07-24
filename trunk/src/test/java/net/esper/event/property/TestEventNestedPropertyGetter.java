package net.esper.event.property;

import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import net.esper.support.bean.SupportBeanCombinedProps;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.esper.event.EventPropertyGetter;
import net.esper.event.BeanEventAdapter;

import java.util.List;
import java.util.LinkedList;

public class TestEventNestedPropertyGetter extends TestCase
{
    private EventNestedPropertyGetter getter;
    private EventNestedPropertyGetter getterNull;
    private EventBean event;
    private SupportBeanCombinedProps bean;
    private BeanEventAdapter beanEventAdapter;

    public void setUp() throws Exception
    {
        beanEventAdapter = new BeanEventAdapter();
        bean = SupportBeanCombinedProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);

        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();
        getters.add(makeGetterOne(0));
        getters.add(makeGetterTwo("0ma"));
        getter = new EventNestedPropertyGetter(getters, beanEventAdapter);

        getters = new LinkedList<EventPropertyGetter>();
        getters.add(makeGetterOne(2));
        getters.add(makeGetterTwo("0ma"));
        getterNull = new EventNestedPropertyGetter(getters, beanEventAdapter);
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

    private EventKeyedPropertyGetter makeGetterOne(int index)
    {
        FastClass fastClassOne = FastClass.create(SupportBeanCombinedProps.class);
        FastMethod methodOne = fastClassOne.getMethod("getIndexed", new Class[] {int.class});
        return new EventKeyedPropertyGetter(methodOne, index);
    }

    private EventKeyedPropertyGetter makeGetterTwo(String key)
    {
        FastClass fastClassTwo = FastClass.create(SupportBeanCombinedProps.NestedLevOne.class);
        FastMethod methodTwo = fastClassTwo.getMethod("getMapped", new Class[] {String.class});
        return new EventKeyedPropertyGetter(methodTwo, key);
    }
}
