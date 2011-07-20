/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.filter;

import junit.framework.TestCase;

public class TestFilterSpecParamRange extends TestCase
{
    public void testConstruct()
    {
        DoubleRange range = new DoubleRange(3d,3d);

        makeParam("a", FilterOperator.RANGE_HALF_OPEN, range);

        try
        {
            makeParam("a", FilterOperator.EQUAL, range);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testEquals()
    {
        FilterSpecParam c1 = makeParam("a", FilterOperator.RANGE_CLOSED, new DoubleRange(5d, 6d));
        FilterSpecParam c2 = makeParam("b", FilterOperator.RANGE_CLOSED, new DoubleRange(5d, 6d));
        FilterSpecParam c3 = makeParam("a", FilterOperator.RANGE_HALF_CLOSED, new DoubleRange(5d, 6d));
        FilterSpecParam c4 = makeParam("a", FilterOperator.RANGE_CLOSED, new DoubleRange(7d, 6d));
        FilterSpecParam c5 = makeParam("a", FilterOperator.RANGE_CLOSED, new DoubleRange(5d, 6d));

        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertTrue(c1.equals(c5));
    }

    private FilterSpecParamRange makeParam(String propertyName, FilterOperator filterOp, DoubleRange doubleRange)
    {
        return new FilterSpecParamRange(propertyName, filterOp,
                new RangeValueDouble(doubleRange.getMin()),
                new RangeValueDouble(doubleRange.getMax()), Double.class);
    }
}
