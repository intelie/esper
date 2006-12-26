package net.esper.filter;

import junit.framework.TestCase;

public class TestDoubleRange extends TestCase
{
    public void testNew()
    {
        DoubleRange range = new DoubleRange(10, 20);
        assertEquals(20d, range.getMax());
        assertEquals(10d, range.getMin());

        range = new DoubleRange(20, 10);
        assertEquals(20d, range.getMax());
        assertEquals(10d, range.getMin());
    }

    public void testEquals()
    {
        DoubleRange rangeOne = new DoubleRange(10, 20);
        DoubleRange rangeTwo = new DoubleRange(20, 10);
        DoubleRange rangeThree = new DoubleRange(20, 11);
        DoubleRange rangeFour = new DoubleRange(21, 10);

        assertEquals(rangeOne, rangeTwo);
        assertEquals(rangeTwo, rangeOne);
        assertFalse(rangeOne.equals(rangeThree));
        assertFalse(rangeOne.equals(rangeFour));
        assertFalse(rangeThree.equals(rangeFour));
    }

    public void testHash()
    {
        DoubleRange range = new DoubleRange(10, 20);
        int hashCode = Double.valueOf(10).hashCode() ^ Double.valueOf(20).hashCode();

        assertEquals(hashCode, range.hashCode());        
    }
}
