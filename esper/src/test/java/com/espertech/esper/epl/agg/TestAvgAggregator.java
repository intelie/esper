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
