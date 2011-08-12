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

package com.espertech.esper.epl.agg;

import junit.framework.TestCase;

public class TestStddevAggregator extends TestCase
{
    public void testAggregateFunction()
    {
        AggregationMethod agg = new StddevAggregator();
        assertEquals(Double.class, agg.getValueType());

        assertNull(agg.getValue());

        agg.enter(10);
        assertNull(agg.getValue());

        agg.enter(8);
        double result = (Double)agg.getValue();
        assertEquals("1.4142", Double.toString(result).substring(0, 6));

        agg.enter(5);
        result = (Double)agg.getValue();
        assertEquals("2.5166", Double.toString(result).substring(0, 6));

        agg.enter(9);
        result = (Double)agg.getValue();
        assertEquals("2.1602", Double.toString(result).substring(0, 6));

        agg.leave(10);
        result = (Double)agg.getValue();
        assertEquals("2.0816", Double.toString(result).substring(0, 6));
    }

    public void testAllOne() {
        AggregationMethod agg = new StddevAggregator();
        agg.enter(1);
        agg.enter(1);
        agg.enter(1);
        agg.enter(1);
        agg.enter(1);
        assertEquals(0.0d, agg.getValue());
    }

}


