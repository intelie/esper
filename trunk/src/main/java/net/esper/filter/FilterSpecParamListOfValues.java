package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a 'in' filter parameter in an {@link net.esper.filter.FilterSpec} filter specification.
 * <p>
 * The 'in' checks for a list of values.
 */
public final class FilterSpecParamListOfValues extends FilterSpecParam
{
    private final List<Object> listofValues;

    /**
     * Constructor.
     * @param propertyName is the event property name
     * @throws IllegalArgumentException if an operator was supplied that does not take a double range value
     */
    public FilterSpecParamListOfValues(String propertyName, FilterOperator filterOperator, List<Object> listofValues)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        this.listofValues = listofValues;

        if (!(filterOperator == FilterOperator.IN_LIST_OF_VALUES))
        {
            throw new IllegalArgumentException("Illegal filter operator " + filterOperator + " supplied to " +
                    "in-values filter parameter");
        }
    }

    public final Class getFilterValueClass(Map<String, EventType> taggedEventTypes)
    {
        // TODO: check types for dynamic usage
        return InSetOfValues.class;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        // TODO: determine values for dynamic usage
        return new InSetOfValues();
    }

    public List<Object> getListofValues()
    {
        return listofValues;
    }

    public final String toString()
    {
        return super.toString() + "  in=(listofValues=" + listofValues.toString() + ")";
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamListOfValues))
        {
            return false;
        }

        FilterSpecParamListOfValues other = (FilterSpecParamListOfValues) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if (listofValues.size() != other.listofValues.size())
        {
            return false;
        }

        if (!(Arrays.deepEquals(listofValues.toArray(), other.listofValues.toArray())))
        {
            return false;
        }
        return true;
    }
}
