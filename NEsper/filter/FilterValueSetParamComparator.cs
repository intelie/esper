///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.filter
{
	/// <summary>
	/// Sort comparator for filter parameters that sorts filter parameters according to filter operator type, and
	/// within the same filter operator sorts by event property name.
	/// </summary>
	public class FilterValueSetParamComparator : Comparer<FilterValueSetParam>
	{
	    /// <summary>
	    /// Defines the sort order among filter operator types. The idea is to sort EQUAL-type operators first
	    /// then RANGE then other operators, ie. sorting from a more restrictive (usually, not necessarily,
	    /// really depends on the client application) to a less restrictive operand.
	    /// </summary>
	    private static readonly FilterOperator[] FilterOperator_SortOrder =
        {
            FilterOperator.EQUAL,
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
            FilterOperator.BOOLEAN_EXPRESSION
	    };

	    private static int[] filterSortOrder;

	    static FilterValueSetParamComparator()
	    {
	        Array enumValues = Enum.GetValues(typeof (FilterOperator));

	        filterSortOrder = new int[enumValues.Length];
	        for (int i = 0; i < filterSortOrder.Length ; i++)
	        {
	            filterSortOrder[i] = IndexOf((FilterOperator) enumValues.GetValue(i));
	        }
	    }

	    public override int Compare(FilterValueSetParam param1, FilterValueSetParam param2)
	    {
	        // Within the same filter operator type sort by attribute name
	        if (param1.FilterOperator == param2.FilterOperator)
	        {
	            return param1.PropertyName.CompareTo(param2.PropertyName);
	        }

	        // Within different filter operator types sort by the table above
	        int opIndex1 = filterSortOrder[(int)param1.FilterOperator];
            int opIndex2 = filterSortOrder[(int)param2.FilterOperator];

	        if (opIndex1 < opIndex2)
	        {
	            return -1;
	        }
	        else
	        {
	            return 1;
	        }
	    }

	    private static int IndexOf(FilterOperator filterOperator)
	    {
	        for (int i = 0; i < FilterOperator_SortOrder.Length; i++)
	        {
	            if (FilterOperator_SortOrder[i] == filterOperator)
	            {
	                return i;
	            }
	        }

	        return FilterOperator_SortOrder.Length;
	    }
	}
} // End of namespace
