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
