package com.espertech.esper.support.util;

import com.espertech.esper.client.EventBean;

public class ArrayCompareUtil
{
    /**
     * Compare the events in the two object arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static boolean compareEqualsExactOrder(EventBean[] data, EventBean[] expectedValues)
    {
        if ((expectedValues == null) && (data == null))
        {
            return true;
        }
        if ( ((expectedValues == null) && (data != null)) ||
             ((expectedValues != null) && (data == null)) )
        {
            return false;
        }

        if (expectedValues.length != data.length)
        {
            return false;
        }

        for (int i = 0; i < expectedValues.length; i++)
        {
            if ((data[i] == null) && (expectedValues[i] == null))
            {
                continue;
            }

            if (!data[i].equals(expectedValues[i]))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Compare the objects in the two object arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static boolean compareRefExactOrder(Object[] data, Object[] expectedValues)
    {
        if ((expectedValues == null) && (data == null))
        {
            return true;
        }
        if ( ((expectedValues == null) && (data != null)) ||
             ((expectedValues != null) && (data == null)) )
        {
            return false;
        }

        if (expectedValues.length != data.length)
        {
            return false;
        }

        for (int i = 0; i < expectedValues.length; i++)
        {
            if (expectedValues[i] != data[i])
            {
                return false;
            }
        }

        return true;
    }
}
