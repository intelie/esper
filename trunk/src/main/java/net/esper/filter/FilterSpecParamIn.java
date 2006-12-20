package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;
import net.esper.collection.MultiKeyUntyped;
import net.esper.util.JavaClassHelper;

import java.util.Map;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a 'in' filter parameter in an {@link net.esper.filter.FilterSpec} filter specification.
 * <p>
 * The 'in' checks for a list of values.
 */
public final class FilterSpecParamIn extends FilterSpecParam
{
    private final List<FilterSpecParamInValue> listOfValues;
    private MultiKeyUntyped inListConstantsOnly;
    private Class propertyType;

    /**
     * Ctor.
     * @param propertyName is the event property name
     * @param filterOperator is expected to be the IN-list operator
     * @param listofValues is a list of constants and event property names
     * @param isAllConstants true if there are only constants, nd false if there is one or more property in the values
     * @param propertyType is the type of the property
     * @throws IllegalArgumentException for illegal args
     */
    public FilterSpecParamIn(String propertyName,
                             FilterOperator filterOperator,
                             List<FilterSpecParamInValue> listofValues,
                             boolean isAllConstants,
                             Class propertyType)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);

        this.propertyType = propertyType;
        this.listOfValues = listofValues;
        if (isAllConstants)
        {
            Object[] constants = new Object[listOfValues.size()];
            int count = 0;
            for (FilterSpecParamInValue valuePlaceholder : listOfValues)
            {
                constants[count++] = valuePlaceholder.getFilterValue(null);
            }
            inListConstantsOnly = new MultiKeyUntyped(constants);
        }

        if (!(filterOperator == FilterOperator.IN_LIST_OF_VALUES))
        {
            throw new IllegalArgumentException("Illegal filter operator " + filterOperator + " supplied to " +
                    "in-values filter parameter");
        }
    }

    public final Class getFilterValueClass(Map<String, EventType> taggedEventTypes)
    {
        for (FilterSpecParamInValue valuePlaceholder : listOfValues)
        {
            Class clazz = valuePlaceholder.validate(taggedEventTypes);
            Class clazzBoxed = JavaClassHelper.getBoxedType(clazz);
            Class propBoxed = JavaClassHelper.getBoxedType(propertyType); 
            if (clazzBoxed != propBoxed)
            {
                throw new IllegalStateException("Automatic conversion of type " + clazz + " to type " + propertyType + " not allowed");
            }
        }

        return MultiKeyUntyped.class;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        // If the list of values consists of all-constants and no event properties, then use cached version
        if (inListConstantsOnly != null)
        {
            return inListConstantsOnly;
        }

        // Determine actual values since the in-list of values contains one or more event properties
        Object[] actualValues = new Object[listOfValues.size()];
        int count = 0;
        for (FilterSpecParamInValue valuePlaceholder : listOfValues)
        {
            actualValues[count++] = valuePlaceholder.getFilterValue(matchedEvents);
        }
        return new MultiKeyUntyped(actualValues);
    }

    public List<FilterSpecParamInValue> getListOfValues()
    {
        return listOfValues;
    }

    public final String toString()
    {
        return super.toString() + "  in=(listOfValues=" + listOfValues.toString() + ")";
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamIn))
        {
            return false;
        }

        FilterSpecParamIn other = (FilterSpecParamIn) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if (listOfValues.size() != other.listOfValues.size())
        {
            return false;
        }

        if (!(Arrays.deepEquals(listOfValues.toArray(), other.listOfValues.toArray())))
        {
            return false;
        }
        return true;
    }
}
