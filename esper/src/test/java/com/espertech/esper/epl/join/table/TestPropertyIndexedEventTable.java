package com.espertech.esper.epl.join.table;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.Set;

public class TestPropertyIndexedEventTable extends TestCase
{
    private String[] propertyNames;
    private EventType eventType;
    private EventBean[] testEvents;
    private Object[] testEventsUnd;
    private PropertyIndexedEventTable index;

    public void setUp()
    {
        propertyNames = new String[] { "intPrimitive", "string" };
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        index = new PropertyIndexedEventTable(1, eventType, propertyNames, null);

        // Populate with testEvents
        int intValues[] = new int[] {0, 1, 1, 2, 1, 0};
        String stringValues[] = new String[] { "a", "b", "c", "a", "b", "c" };

        testEvents = new EventBean[intValues.length];
        testEventsUnd = new Object[intValues.length];
        for (int i = 0; i < intValues.length; i++)
        {
            testEvents[i] = makeBean(intValues[i], stringValues[i]);
            testEventsUnd[i] = testEvents[i].getUnderlying();
        }
        index.add(testEvents);
    }

    public void testFind()
    {
        Set<EventBean> result = index.lookup(new Object[] {1, "a"});
        assertNull(result);

        result = index.lookup(new Object[] {1, "b"});
        assertEquals(2, result.size());
        assertTrue(result.contains(testEvents[1]));
        assertTrue(result.contains(testEvents[4]));

        result = index.lookup(new Object[] {0, "c"});
        assertEquals(1, result.size());
        assertTrue(result.contains(testEvents[5]));

        result = index.lookup(new Object[] {0, "a"});
        assertEquals(1, result.size());
        assertTrue(result.contains(testEvents[0]));
    }

    public void testAdd()
    {
        // Add event without these properties should fail
        EventBean event = SupportEventBeanFactory.createObject(new SupportBean_A("d"));
        try
        {
            index.add(new EventBean[] {event});
            TestCase.fail();
        }
        catch (PropertyAccessException ex)
        {
            // Expected
        }

        // Add null should fail
        try
        {
            index.add(new EventBean[] {null});
            TestCase.fail();
        }
        catch (NullPointerException ex)
        {
            // Expected
        }

        // Same event added twice fails
        event = makeBean(1, "aa");
        index.add(new EventBean[] {event});
        try
        {
            index.add(new EventBean[] {event});
            TestCase.fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testRemove()
    {
        index.remove(testEvents);
    }

    public void testAddArray()
    {
        index = new PropertyIndexedEventTable(1, eventType, propertyNames, null);

        // Add just 2
        EventBean[] events = new EventBean[2];
        events[0] = testEvents[1];
        events[1] = testEvents[4];
        index.add(events);

        Set<EventBean> result = index.lookup(new Object[] {1, "b"});
        assertEquals(2, result.size());

        try
        {
            index.add(testEvents);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testRemoveArray()
    {
        index.remove(testEvents);

        Set<EventBean> result = index.lookup(new Object[] {1, "b"});
        assertNull(result);

        // Remove again - already removed but won't throw an exception
        index.remove(testEvents);
    }

    public void testMixed()
    {
        index.remove(new EventBean[] {testEvents[1]});
        Set<EventBean> result = index.lookup(new Object[] {1, "b"});
        assertEquals(1, result.size());
        assertTrue(result.contains(testEvents[4]));

        // iterate
        Object[] underlying = ArrayAssertionUtil.iteratorToArrayUnderlying(index.iterator());
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {testEventsUnd[0], testEventsUnd[2], testEventsUnd[3], testEventsUnd[4], testEventsUnd[5]}, underlying);

        index.remove(new EventBean[] {testEvents[4]});
        result = index.lookup(new Object[] {1, "b"});
        assertNull(result);

        // iterate
        underlying = ArrayAssertionUtil.iteratorToArrayUnderlying(index.iterator());
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {testEventsUnd[0], testEventsUnd[2], testEventsUnd[3], testEventsUnd[5]}, underlying);

        index.add(new EventBean[] {testEvents[1]});
        result = index.lookup(new Object[] {1, "b"});
        assertEquals(1, result.size());
        assertTrue(result.contains(testEvents[1]));

        // iterate
        underlying = ArrayAssertionUtil.iteratorToArrayUnderlying(index.iterator());
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {testEventsUnd[0], testEventsUnd[1], testEventsUnd[2], testEventsUnd[3], testEventsUnd[5]}, underlying);
    }

    public void testIterator()
    {
        Object[] underlying = ArrayAssertionUtil.iteratorToArrayUnderlying(index.iterator());
        ArrayAssertionUtil.assertEqualsAnyOrder(testEventsUnd, underlying);
    }

    private EventBean makeBean (int intValue, String stringValue)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intValue);
        bean.setString(stringValue);
        return SupportEventBeanFactory.createObject(bean);
    }
}
