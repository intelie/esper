package net.esper.filter;

import net.esper.pattern.MatchedEventMap;
import net.esper.util.MetaDefItem;

/**
 * This class represents one filter parameter in an {@link FilterSpecCompiled} filter specification.
 * <p> Each filerting parameter has an attribute name and operator type.
 */
public abstract class FilterSpecParam implements MetaDefItem
{
    private final String propertyName;
    private final FilterOperator filterOperator;

    FilterSpecParam(String propertyName, FilterOperator filterOperator)
    {
        this.propertyName = propertyName;
        this.filterOperator = filterOperator;
    }

    /**
     * Return the filter parameter constant to filter for.
     * @param matchedEvents is the prior results that can be used to determine filter parameters
     * @return filter parameter constant's value
     */
    public abstract Object getFilterValue(MatchedEventMap matchedEvents);

    /**
     * Returns the property name for the filter parameter.
     * @return property name
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * Returns the filter operator type.
     * @return filter operator type
     */
    public FilterOperator getFilterOperator()
    {
        return filterOperator;
    }


    public String toString()
    {
        return "FilterSpecParam" +
               " property=" + propertyName +
               " filterOp=" + filterOperator;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParam))
        {
            return false;
        }

        FilterSpecParam other = (FilterSpecParam) obj;

        if (this.propertyName != other.propertyName)
        {
            return false;
        }
        if (this.filterOperator != other.filterOperator)
        {
            return false;
        }
        return true;
    }

}
