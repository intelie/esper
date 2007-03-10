package net.esper.filter;

import junit.framework.TestCase;

public class TestRangeValueDouble extends TestCase
{
    private FilterSpecParamRangeValue params[] = new FilterSpecParamRangeValue[5];

    public void setUp()
    {
        params[0] = new RangeValueDouble(5.5);
        params[1] = new RangeValueDouble(0);
        params[2] = new RangeValueDouble(5.5);
    }

    public void testGetFilterValue()
    {
        assertEquals(5.5, params[0].getFilterValue(null));
    }

    public void testEquals()
    {
        assertFalse(params[0].equals(params[1]));
        assertFalse(params[1].equals(params[2]));
        assertTrue(params[0].equals(params[2]));
    }
}
