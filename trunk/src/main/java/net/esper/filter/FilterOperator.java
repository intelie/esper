package net.esper.filter;

/**
 * Defines the different operator types available for event filters.
 *
 * Mathematical notation for defining ranges of floating point numbers is used as defined below:
 * <p>[a,b]  a closed range from value a to value b with the end-points a and b included in the range
 * <p>(a,b)  an open range from value a to value b with the end-points a and b not included in the range
 * <p>[a,b)  a half-open range from value a to value b with the end-point a included and end-point b not included
 * in the range
 * <p>(a,b]  a half-open range from value a to value b with the end-point a not included and end-point b included in the range
 */
public enum FilterOperator
{
    /**
     * Exact matches (=).
     */
    EQUAL,

    /**
     * Exact not matche (!=).
     */
    NOT_EQUAL,

    /**
     * Less (<).
     */
    LESS,

    /**
     * Less or equal (<=).
     */
    LESS_OR_EQUAL,

    /**
     * Greater or equal (>=).
     */
    GREATER_OR_EQUAL,

    /**
     * Greater (>).
     */
    GREATER,

    /**
     * Range contains neither endpoint, i.e. (a,b)
     */
    RANGE_OPEN,

    /**
     * Range contains low and high endpoint, i.e. [a,b]
     */
    RANGE_CLOSED,

    /**
     * Range includes low endpoint but not high endpoint, i.e. [a,b)
     */
    RANGE_HALF_OPEN,

    /**
     * Range includes high endpoint but not low endpoint, i.e. (a,b]
     */
    RANGE_HALF_CLOSED;

    private final static String EQUAL_OP = "=";
    private final static String NOT_EQUAL_OP = "!=";
    private final static String LESS_OP = "<";
    private final static String LESS_EQUAL_OP = "<=";
    private final static String GREATER_OP = ">";
    private final static String GREATER_EQUAL_OP = ">=";

    /**
     * Returns true for range operator, false if not a range operator.
     * @return true for ranges, false for anyting else
     */
    public boolean isRangeOperator()
    {
        if ((this == FilterOperator.RANGE_CLOSED) ||
            (this == FilterOperator.RANGE_OPEN) ||
            (this == FilterOperator.RANGE_HALF_OPEN) ||
            (this == FilterOperator.RANGE_HALF_CLOSED))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns true for relational comparison operators which excludes the = equals operator, else returns false.
     * @return true for lesser or greater -type operators, false for anyting else
     */
    public boolean isComparisonOperator()
    {
        if ((this == FilterOperator.LESS) ||
            (this == FilterOperator.LESS_OR_EQUAL) ||
            (this == FilterOperator.GREATER) ||
            (this == FilterOperator.GREATER_OR_EQUAL))
        {
            return true;
        }
        return false;
    }

    /**
     * Parse the comparison operator returning null if not a valid operator.
     * @param operator
     * @return FilterOperator or null if not valid
     */
    public static FilterOperator parseComparisonOperator(String operator)
    {
        if (operator == null)
        {
            return null;
        }

        if (operator.equals(EQUAL_OP))
        {
            return FilterOperator.EQUAL;
        }
        if (operator.equals(NOT_EQUAL_OP))
        {
            return FilterOperator.NOT_EQUAL;
        }
        if (operator.equals(LESS_OP))
        {
            return FilterOperator.LESS;
        }
        if (operator.equals(LESS_EQUAL_OP))
        {
            return FilterOperator.LESS_OR_EQUAL;
        }
        if (operator.equals(GREATER_OP))
        {
            return FilterOperator.GREATER;
        }
        if (operator.equals(GREATER_EQUAL_OP))
        {
            return FilterOperator.GREATER_OR_EQUAL;
        }

        return null;
    }

    /**
     * Parse the range operator from booleans describing whether the start or end values are exclusive.
     * @param isInclusiveFirst true if low endpoint is inclusive, false if not
     * @param isInclusiveLast true if high endpoint is inclusive, false if not
     * @return FilterOperator for the combination inclusive or exclusive
     */
    public static FilterOperator parseRangeOperator(boolean isInclusiveFirst, boolean isInclusiveLast)
    {
        if (isInclusiveFirst && isInclusiveLast)
        {
            return FilterOperator.RANGE_CLOSED;
        }
        if (isInclusiveFirst && !isInclusiveLast)
        {
            return FilterOperator.RANGE_HALF_OPEN;
        }
        if (isInclusiveLast)
        {
            return FilterOperator.RANGE_HALF_CLOSED;
        }
        return FilterOperator.RANGE_OPEN;
    }
}
