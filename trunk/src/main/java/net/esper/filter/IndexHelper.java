package net.esper.filter;

import net.esper.collection.Pair;
import java.util.SortedSet;
import java.util.List;

/**
 * Utility class for matching filter parameters to indizes. Matches are indicated by the index {@link FilterParamIndex}
 * and the filter parameter {@link FilterSpecParam} featuring the same event property name and filter operator.
 */
public class IndexHelper
{
    /**
     * Find an index that matches one of the filter parameters passed.
     * The parameter type and index type match up if the property name and
     * filter operator are the same for the index and the filter parameter.
     * For instance, for a filter parameter of "count EQUALS 10", the index against property "count" with
     * operator type EQUALS will be returned, if present.
     * NOTE: The caller is expected to obtain locks, if necessary, on the collections passed in.
     *
     * @param parameters is the list of sorted filter parameters
     * @param indizes is the collection of indexes
     * @return A matching pair of filter parameter and index, if any matches were found. Null if no matches were found.
     */
    public static Pair<FilterValueSetParam, FilterParamIndex> findIndex(SortedSet<FilterValueSetParam> parameters, List<FilterParamIndex> indizes)
    {
        for (FilterValueSetParam parameter : parameters)
        {
            String property = parameter.getPropertyName();
            FilterOperator operator = parameter.getFilterOperator();

            for (FilterParamIndex index : indizes)
            {
                if ( (property.equals(index.getPropertyName())) &&
                     (operator.equals(index.getFilterOperator())) )
                {
                    return new Pair<FilterValueSetParam, FilterParamIndex>(parameter, index);
                }
            }
        }

        return null;
    }

    /**
     * Determine among the passed in filter parameters any parameter that matches the given index on property name and
     * filter operator type. Returns null if none of the parameters matches the index.
     * @param parameters is the filter parameter list
     * @param index is a filter parameter constant value index
     * @return filter parameter, or null if no matching parameter found.
     */
    public static FilterValueSetParam findParameter(SortedSet<FilterValueSetParam> parameters,
                                                FilterParamIndex index)
    {
        String indexProperty = index.getPropertyName();
        FilterOperator indexOperator = index.getFilterOperator();

        for (FilterValueSetParam parameter : parameters)
        {
            String paramProperty = parameter.getPropertyName();
            FilterOperator paramOperator = parameter.getFilterOperator();

            if ( (paramProperty.equals(indexProperty)) &&
                 (paramOperator.equals(indexOperator)) )
            {
                return parameter;
            }
        }

        return null;
    }
}
