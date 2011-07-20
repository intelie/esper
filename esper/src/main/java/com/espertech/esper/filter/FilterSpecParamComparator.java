/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sort comparator for filter parameters that sorts filter parameters according to filter operator type.
 */
public class FilterSpecParamComparator implements Comparator<FilterOperator>, MetaDefItem, Serializable
{
    /**
     * Defines the sort order among filter operator types. The idea is to sort EQUAL-type operators first
     * then RANGE then other operators, ie. sorting from a more restrictive (usually, not necessarily,
     * really depends on the client application) to a less restrictive operand.
     */
    private static final FilterOperator[] FilterOperator_SortOrder =
            {
            FilterOperator.EQUAL,
            FilterOperator.IS,
            FilterOperator.IN_LIST_OF_VALUES,
            FilterOperator.RANGE_OPEN,
            FilterOperator.RANGE_HALF_OPEN,
            FilterOperator.RANGE_HALF_CLOSED,
            FilterOperator.RANGE_CLOSED,
            FilterOperator.LESS,
            FilterOperator.LESS_OR_EQUAL,
            FilterOperator.GREATER_OR_EQUAL,
            FilterOperator.GREATER,
            FilterOperator.NOT_RANGE_CLOSED,
            FilterOperator.NOT_RANGE_HALF_CLOSED,
            FilterOperator.NOT_RANGE_HALF_OPEN,
            FilterOperator.NOT_RANGE_OPEN,
            FilterOperator.NOT_IN_LIST_OF_VALUES,
            FilterOperator.NOT_EQUAL,
            FilterOperator.IS_NOT,
            FilterOperator.BOOLEAN_EXPRESSION
     };

    private static int[] filterSortOrder;
    private static final long serialVersionUID = -3001682034930099256L;

    static
    {
        filterSortOrder = new int[FilterOperator.values().length];
        for (int i = 0; i < filterSortOrder.length; i++)
        {
            filterSortOrder[i] = indexOf(FilterOperator.values()[i]);
        }
    }

    public int compare(FilterOperator param1, FilterOperator param2) {
        // Within the same filter operator type sort by attribute name
        if (param1 == param2)
        {
            return 0;
        }

        // Within different filter operator types sort by the table above
        int opIndex1 = filterSortOrder[param1.ordinal()];
        int opIndex2 = filterSortOrder[param2.ordinal()];

        if (opIndex1 < opIndex2)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }

    private static int indexOf(FilterOperator filterOperator)
    {
        for (int i = 0; i < FilterOperator_SortOrder.length; i++)
        {
            if (FilterOperator_SortOrder[i] == filterOperator)
            {
                return i;
            }
        }

        return FilterOperator_SortOrder.length;
    }
}
