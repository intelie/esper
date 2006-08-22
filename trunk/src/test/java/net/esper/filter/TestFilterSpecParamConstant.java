package net.esper.filter;

import junit.framework.TestCase;

public class TestFilterSpecParamConstant extends TestCase
{
    public void testConstruct()
    {
        new FilterSpecParamConstant("a", FilterOperator.GREATER, 5);

        try
        {
            new FilterSpecParamConstant("a", FilterOperator.RANGE_CLOSED, 5);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testEquals()
    {
        FilterSpecParam c1 = new FilterSpecParamConstant("a", FilterOperator.GREATER, 5);
        FilterSpecParam c2 = new FilterSpecParamConstant("a", FilterOperator.GREATER, 6);
        FilterSpecParam c3 = new FilterSpecParamConstant("b", FilterOperator.GREATER, 5);
        FilterSpecParam c4 = new FilterSpecParamConstant("a", FilterOperator.EQUAL, 5);
        FilterSpecParam c5 = new FilterSpecParamConstant("a", FilterOperator.GREATER, 5);

        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertTrue(c1.equals(c5));
    }
}
