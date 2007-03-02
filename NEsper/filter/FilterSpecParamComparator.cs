using System;
using System.Collections.Generic;

namespace net.esper.filter
{
	/// <summary> Sort comparator for filter parameters that sorts filter parameters according to filter operator type, and
	/// within the same filter operator sorts by event property name.
	/// </summary>
	
    public class FilterSpecParamComparator : IComparer<FilterValueSetParam>
	{
		/// <summary> Defines the sort order among filter operator types. The idea is to sort EQUAL-type operators first
		/// then RANGE then other operators, ie. sorting from a more restrictive (usually, not necessarily,
		/// really depends on the client application) to a less restrictive operand.
		/// </summary>

        private static readonly FilterOperator[] FilterOperator_SortOrder ;

        private static int[] filterSortOrder;
		
		public int Compare(FilterValueSetParam param1, FilterValueSetParam param2)
		{
			// Within the same filter operator type sort by attribute name
			if (param1.FilterOperator == param2.FilterOperator)
			{
				return String.CompareOrdinal(param1.PropertyName, param2.PropertyName);
			}
			
			// Within different filter operator types sort by the table above
			int opIndex1 = filterSortOrder[(int) param1.FilterOperator];
			int opIndex2 = filterSortOrder[(int) param2.FilterOperator];
			
			if (opIndex1 < opIndex2)
			{
				return - 1;
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

		static FilterSpecParamComparator()
		{
			FilterOperator_SortOrder = new FilterOperator[]
            {
                FilterOperator.EQUAL,
                FilterOperator.RANGE_OPEN,
                FilterOperator.RANGE_HALF_OPEN,
                FilterOperator.RANGE_HALF_CLOSED,
                FilterOperator.RANGE_CLOSED,
                FilterOperator.LESS, 
                FilterOperator.LESS_OR_EQUAL, 
                FilterOperator.GREATER_OR_EQUAL, 
                FilterOperator.GREATER
            };

			Array enumValues = Enum.GetValues(typeof(FilterOperator));

			filterSortOrder = new int[enumValues.Length];
			for (int i = 0; i < filterSortOrder.Length; i++)
			{
				filterSortOrder[i] = IndexOf( (FilterOperator) enumValues.GetValue( i ) ) ;
			}
		}
	}
}
