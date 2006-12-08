package net.esper.filter;

import net.esper.event.EventType;

/**
 * Factory for {@link FilterParamIndex} instances based on event property name and filter operator type.
 */
public class IndexFactory
{
    /**
     * Factory for indexes that store filter parameter constants for a given event property and filter
     * operator.
     * <p>Does not perform any check of validity of property name.
     * @param eventType is the event type to create an index for
     * @param propertyName is the event property name
     * @param filterOperator is the type of index to use
     * @return the proper index based on the filter operator type
     */
    public static FilterParamIndex createIndex(EventType eventType, String propertyName, FilterOperator filterOperator)
    {
        FilterParamIndex index;

        // Handle all EQUAL comparisons
        if (filterOperator == FilterOperator.EQUAL)
        {
            index = new FilterParamIndexEquals(propertyName, eventType);
            return index;
        }

        // Handle all NOT-EQUAL comparisons
        if (filterOperator == FilterOperator.NOT_EQUAL)
        {
            index = new FilterParamIndexNotEquals(propertyName, eventType);
            return index;
        }

        // Handle all GREATER, LESS etc. comparisons
        if ((filterOperator == FilterOperator.GREATER) ||
            (filterOperator == FilterOperator.GREATER_OR_EQUAL) ||
            (filterOperator == FilterOperator.LESS) ||
            (filterOperator == FilterOperator.LESS_OR_EQUAL))
        {
            index = new FilterParamIndexCompare(propertyName, filterOperator, eventType);
            return index;
        }

        // Handle all RANGE comparisons
        if (filterOperator.isRangeOperator())
        {
            index = new FilterParamIndexRange(propertyName, filterOperator, eventType);
            return index;
        }

        throw new IllegalArgumentException("Cannot create filter index instance for filter operator " + filterOperator);
    }
}
