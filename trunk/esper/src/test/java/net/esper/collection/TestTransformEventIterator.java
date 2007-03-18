package net.esper.collection;

import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class TestTransformEventIterator extends TestCase
{
    private TransformEventIterator iterator;

    public void testEmpty()
    {
        iterator = makeIterator(new int[0]);
        assertFalse(iterator.hasNext());
    }

    public void testOne()
    {
        iterator = makeIterator(new int[] { 10 });
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next().get("id"));
        assertFalse(iterator.hasNext());
    }

    public void testTwo()
    {
        iterator = makeIterator(new int[] { 10, 20 });
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next().get("id"));
        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next().get("id"));
        assertFalse(iterator.hasNext());
    }

    private TransformEventIterator makeIterator(int[] values)
    {
        List<EventBean> events = new LinkedList<EventBean>();
        for (int i = 0; i < values.length; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setIntPrimitive(values[i]);
            EventBean event = SupportEventBeanFactory.createObject(bean);
            events.add(event);
        }
        return new TransformEventIterator(events.iterator(), new MyTransform());
    }

    public class MyTransform implements TransformEventMethod
    {
        public EventBean transform(EventBean event)
        {
            Integer value = (Integer) event.get("intPrimitive");
            return SupportEventBeanFactory.createObject(new SupportBean_S0(value));
        }
    }
}
