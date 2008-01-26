package net.esper.eql.expression;

import net.esper.support.eql.SupportAggregator;
import net.esper.eql.agg.DistinctValueAggregator;
import junit.framework.TestCase;

public class TestUniqueValueAggregator extends TestCase
{
    private DistinctValueAggregator agg;

    public void setUp()
    {
        agg = new DistinctValueAggregator(new SupportAggregator(), Integer.class);
    }

    public void testEnter()
    {
        agg.enter(1);
        agg.enter(new Integer(10));
        agg.enter(null);
    }

    public void testLeave()
    {
        agg.enter(1);
        agg.leave(1);

        try
        {
            agg.leave(1);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testGetValue()
    {
        assertEquals(0, agg.getValue());

        agg.enter(10);
        assertEquals(10, agg.getValue());

        agg.enter(10);
        assertEquals(10, agg.getValue());

        agg.enter(2);
        assertEquals(12, agg.getValue());

        agg.leave(10);
        assertEquals(12, agg.getValue());

        agg.leave(10);
        assertEquals(2, agg.getValue());

        agg.leave(2);
        assertEquals(0, agg.getValue());
    }

    public void testGetType()
    {
        assertEquals(Integer.class, agg.getValueType());
    }
}
