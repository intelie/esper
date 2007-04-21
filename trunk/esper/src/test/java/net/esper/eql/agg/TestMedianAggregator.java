package net.esper.eql.agg;

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


