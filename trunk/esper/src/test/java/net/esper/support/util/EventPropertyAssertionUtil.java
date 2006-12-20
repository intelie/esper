package net.esper.support.util;

import net.esper.event.EventBean;

import java.util.*;

import junit.framework.TestCase;

public class EventPropertyAssertionUtil
{
    public static void compare(EventBean[] events, List<Map<String, Object>> expectedValues)
    {
        if ((expectedValues == null) && (events == null))
        {
            return;
        }
        if ( ((expectedValues == null) && (events != null)) ||
             ((expectedValues != null) && (events == null)) )
        {
            TestCase.assertTrue(false);
        }

        TestCase.assertEquals(expectedValues.size(), events.length);

        for (int i = 0; i < expectedValues.size(); i++)
        {
            compare(events[i], expectedValues.get(i));
        }
    }

    public static void compare(Iterator<EventBean> iterator, List<Map<String, Object>> expectedValues)
    {
        ArrayList<EventBean> values = new ArrayList<EventBean>();
        while (iterator.hasNext())
        {
            values.add(iterator.next());
        }

        try
        {
            iterator.next();
            TestCase.fail();
        }
        catch (NoSuchElementException ex)
        {
            // Expected exception - next called after hasNext returned false, for testing
        }

        EventBean[] data = null;
        if (values.size() > 0)
        {
            data = values.toArray(new EventBean[0]);
        }

        compare(data, expectedValues);
    }


    private static void compare(EventBean event, Map<String, Object> expected)
    {
        for (Map.Entry<String, Object> entry : expected.entrySet())
        {
            Object valueExpected = entry.getValue();
            Object property = event.get(entry.getKey());

            TestCase.assertEquals(valueExpected, property);
        }
    }
}
