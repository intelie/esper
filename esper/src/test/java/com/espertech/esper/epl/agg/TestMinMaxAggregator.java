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

import com.espertech.esper.type.MinMaxTypeEnum;
import junit.framework.TestCase;

public class TestMinMaxAggregator extends TestCase
{
    public void testAggregatorMax()
    {
        MinMaxAggregator agg = new MinMaxAggregator(MinMaxTypeEnum.MAX, int.class);
        assertEquals(null, agg.getValue());
        agg.enter(10);
        assertEquals(10, agg.getValue());
        agg.enter(20);
        assertEquals(20, agg.getValue());
        agg.enter(10);
        assertEquals(20, agg.getValue());
        agg.leave(10);
        assertEquals(20, agg.getValue());
        agg.leave(20);
        assertEquals(10, agg.getValue());
        agg.leave(10);
        assertEquals(null, agg.getValue());
    }

    public void testAggregatorMin()
    {
        MinMaxAggregator agg = new MinMaxAggregator(MinMaxTypeEnum.MIN, int.class);
        assertEquals(null, agg.getValue());
        agg.enter(10);
        assertEquals(10, agg.getValue());
        agg.enter(20);
        assertEquals(10, agg.getValue());
        agg.enter(10);
        assertEquals(10, agg.getValue());
        agg.leave(10);
        assertEquals(10, agg.getValue());
        agg.leave(20);
        assertEquals(10, agg.getValue());
        agg.leave(10);
        assertEquals(null, agg.getValue());
    }
}
