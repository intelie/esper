package net.esper.filter;

import net.esper.pattern.MatchedEventMap;
import net.esper.event.EventType;

import java.util.Map;

/**
 * This class represents a single, constant value filter parameter in an {@link FilterSpec} filter specification.
 */
public final class FilterSpecParamConstant extends FilterSpecParam
{
    private final Object filterConstant;

    /**
     * Constructor.
     * @param propertyName is the event property name
     * @param filterOperator is the type of compare
     * @param filterConstant contains the value to match against the event's property value
     * @throws IllegalArgumentException if an operator was supplied that does not take a single constant value
     */
    public FilterSpecParamConstant(String propertyName, FilterOperator filterOperator, Object filterConstant)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        this.filterConstant = filterConstant;

        if (filterOperator.isRangeOperator())
        {
            throw new IllegalArgumentException("Illegal filter operator " + filterOperator + " supplied to " +
                    "constant filter parameter");
        }
    }

    public Class getFilterValueClass(Map<String, EventType> taggedEventTypes)
    {
        if (filterConstant == null)
        {
            return null;
        }
        return filterConstant.getClass();
    }

    public Object getFilterValue(MatchedEventMap matchedEvents)
    {
        return filterConstant;
    }

    public final String toString()
    {
        return super.toString() + " filterConstant=" + filterConstant;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamConstant))
        {
            return false;
        }

        FilterSpecParamConstant other = (FilterSpecParamConstant) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if (this.filterConstant != other.filterConstant)
        {
            return false;
        }
        return true;
    }
}
