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

public class TestAvgAggregator extends TestCase
{
    public void testResult()
    {
        AvgAggregator agg = new AvgAggregator();
        agg.enter(100);
        assertEquals(100d, agg.getValue());
        agg.enter(150);
        assertEquals(125d, agg.getValue());
        agg.enter(200);
        assertEquals(150d, agg.getValue());
        agg.leave(100);
        assertEquals(175d, agg.getValue());
    }

}
