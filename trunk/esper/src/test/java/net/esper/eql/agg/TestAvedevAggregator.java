package net.esper.eql.agg;

import junit.framework.TestCase;

public class TestAvedevAggregator extends TestCase
{
    public void testAggregateFunction()
    {
        AvedevAggregator agg = new AvedevAggregator();
        assertEquals(Double.class, agg.getValueType());

        assertNull(agg.getValue());

        agg.enter(82);
        assertEquals(0D, agg.getValue());

        agg.enter(78);
        assertEquals(2D, agg.getValue());

        agg.enter(70);
        double result = (Double)agg.getValue();
        assertEquals("4.4444", Double.toString(result).substring(0, 6));

        agg.enter(58);
        assertEquals(8D, agg.getValue());

        agg.enter(42);
        assertEquals(12.8D, agg.getValue());

        agg.leave(82);
        assertEquals(12D, agg.getValue());

        agg.leave(58);
        result = (Double)agg.getValue();
        assertEquals("14.2222", Double.toString(result).substring(0, 7));
    }

}
