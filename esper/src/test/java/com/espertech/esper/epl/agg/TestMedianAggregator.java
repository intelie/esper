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

public class TestMedianAggregator extends TestCase
{
    public void testAggregator()
    {
        MedianAggregator median = new MedianAggregator();
        assertEquals(null, median.getValue());
        median.enter(10);
        assertEquals(10D, median.getValue());
        median.enter(20);
        assertEquals(15D, median.getValue());
        median.enter(10);
        assertEquals(10D, median.getValue());

        median.leave(10);
        assertEquals(15D, median.getValue());
        median.leave(10);
        assertEquals(20D, median.getValue());
        median.leave(20);
        assertEquals(null, median.getValue());
    }
}


