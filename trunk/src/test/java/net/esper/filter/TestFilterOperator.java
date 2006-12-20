package net.esper.filter;

import junit.framework.TestCase;

public class TestFilterOperator extends TestCase
{
    public void testOperatorsFromString()
    {
        assertTrue(FilterOperator.parseComparisonOperator("!=") == FilterOperator.NOT_EQUAL);
        assertTrue(FilterOperator.parseComparisonOperator(">") == FilterOperator.GREATER);
        assertTrue(FilterOperator.parseComparisonOperator("=") == FilterOperator.EQUAL);
        assertTrue(FilterOperator.parseComparisonOperator("<=") == FilterOperator.LESS_OR_EQUAL);
        assertTrue(FilterOperator.parseComparisonOperator("<") == FilterOperator.LESS);
        assertTrue(FilterOperator.parseComparisonOperator(">=") == FilterOperator.GREATER_OR_EQUAL);
        assertTrue(FilterOperator.parseComparisonOperator("d") == null);
        assertTrue(FilterOperator.parseComparisonOperator(null) == null);
    }

    public void testRanges()
    {
        assertTrue(FilterOperator.parseRangeOperator(false, false) == FilterOperator.RANGE_OPEN);
        assertTrue(FilterOperator.parseRangeOperator(true, true) == FilterOperator.RANGE_CLOSED);
        assertTrue(FilterOperator.parseRangeOperator(true, false) == FilterOperator.RANGE_HALF_OPEN);
        assertTrue(FilterOperator.parseRangeOperator(false, true) == FilterOperator.RANGE_HALF_CLOSED);
    }

    public void testIsComparison()
    {
        assertTrue(FilterOperator.GREATER.isComparisonOperator());
        assertTrue(FilterOperator.GREATER_OR_EQUAL.isComparisonOperator());
        assertTrue(FilterOperator.LESS.isComparisonOperator());
        assertTrue(FilterOperator.LESS_OR_EQUAL.isComparisonOperator());
        assertFalse(FilterOperator.RANGE_CLOSED.isComparisonOperator());
        assertFalse(FilterOperator.EQUAL.isComparisonOperator());
        assertFalse(FilterOperator.NOT_EQUAL.isComparisonOperator());
    }

    public void testIsRange()
    {
        assertTrue(FilterOperator.RANGE_OPEN.isRangeOperator());
        assertTrue(FilterOperator.RANGE_CLOSED.isRangeOperator());
        assertTrue(FilterOperator.RANGE_HALF_OPEN.isRangeOperator());
        assertTrue(FilterOperator.RANGE_HALF_CLOSED.isRangeOperator());
        assertFalse(FilterOperator.LESS.isRangeOperator());
        assertFalse(FilterOperator.EQUAL.isRangeOperator());
        assertFalse(FilterOperator.NOT_EQUAL.isRangeOperator());
    }
}