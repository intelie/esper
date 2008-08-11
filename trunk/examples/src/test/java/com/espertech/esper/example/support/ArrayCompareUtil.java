/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.support;

import com.espertech.esper.event.EventBean;

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
            if (!data[i].equals(expectedValues[i]))
            {
                return false;
            }
        }
        return true;
    }
}
