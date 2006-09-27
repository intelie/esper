package net.esper.event.property;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportLegacyBean;
import net.esper.support.event.SupportEventBeanFactory;
import java.lang.reflect.Field;

public class TestArrayFieldPropertyGetter extends TestCase
{
    private ArrayFieldPropertyGetter getter;
    private ArrayFieldPropertyGetter getterOutOfBounds;
    private EventBean event;
    private SupportLegacyBean bean;

    public void setUp() throws Exception
    {
        bean = new SupportLegacyBean(new String[] {"a", "b"});
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
        assertEquals(bean.fieldStringArray[0], getter.get(event));

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

    private ArrayFieldPropertyGetter makeGetter(int index) throws Exception
    {
        Field field = SupportLegacyBean.class.getField("fieldStringArray");
        return new ArrayFieldPropertyGetter(field, index);
    }
}
