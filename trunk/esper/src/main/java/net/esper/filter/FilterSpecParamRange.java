package net.esper.filter;

import net.esper.pattern.MatchedEventMap;
import net.esper.event.EventType;

import java.util.Map;

/**
 * This class represents a range filter parameter in an {@link FilterSpec} filter specification.
 */
public final class FilterSpecParamRange extends FilterSpecParam
{
    private final FilterSpecParamRangeValue min;
    private final FilterSpecParamRangeValue max;

    /**
     * Constructor.
     * @param propertyName is the event property name
     * @param filterOperator is the type of range operator
     * @param min is the begin point of the range
     * @param max is the end point of the range
     * @throws IllegalArgumentException if an operator was supplied that does not take a double range value
     */
    public FilterSpecParamRange(String propertyName, FilterOperator filterOperator, FilterSpecParamRangeValue min, FilterSpecParamRangeValue max)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        this.min = min;
        this.max = max;

        if (!(filterOperator.isRangeOperator()) && (!(filterOperator.isInvertedRangeOperator())))
        {
            throw new IllegalArgumentException("Illegal filter operator " + filterOperator + " supplied to " +
                    "range filter parameter");
        }
    }

    public final Class getFilterValueClass(Map<String, EventType> taggedEventTypes)
    {
        min.checkType(taggedEventTypes);
        max.checkType(taggedEventTypes);
        return DoubleRange.class;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        double begin = min.getFilterValue(matchedEvents);
        double end = max.getFilterValue(matchedEvents);
        return new DoubleRange(begin, end);
    }

    public final String toString()
    {
        return super.toString() + "  range=(min=" + min.toString() + ",max=" + max.toString() + ')';
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamRange))
        {
            return false;
        }

        FilterSpecParamRange other = (FilterSpecParamRange) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if ((this.min.equals(other.min) &&
            (this.max.equals(other.max))))
        {
            return true;
        }
        return false;
    }
}
