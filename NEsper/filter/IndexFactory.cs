using System;

using net.esper.events;

namespace net.esper.filter
{
	/// <summary>
	/// Factory for <seealso cref="FilterParamIndex" /> instances based on
	/// event property name and filter operator type.
	/// </summary>

	public class IndexFactory
	{
        /// <summary>
        /// Factory for indexes that store filter parameter constants for a given event property and filter
        /// operator.
        /// <para>Does not perform any check of validity of property name.</para>
        /// </summary>
        /// <param name="eventType">is the event type to create an index for</param>
        /// <param name="propertyName">is the event property name</param>
        /// <param name="filterOperator">is the type of index to use</param>
        /// <returns>
        /// the proper index based on the filter operator type
        /// </returns>
		public static FilterParamIndexBase CreateIndex(
			EventType eventType,
			String propertyName,
			FilterOperator filterOperator)
		{
			FilterParamIndexBase index;
			
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
			
	        // Handle all normal and inverted RANGE comparisons
	        if (filterOperator.IsRangeOperator)
	        {
	            index = new FilterParamIndexRange(propertyName, filterOperator, eventType);
	            return index;
	        }
	        if (filterOperator.IsInvertedRangeOperator)
	        {
	            index = new FilterParamIndexNotRange(propertyName, filterOperator, eventType);
	            return index;
	        }

	        // Handle all IN and NOT IN comparisons
	        if (filterOperator == FilterOperator.IN_LIST_OF_VALUES)
	        {
	            index = new FilterParamIndexIn(propertyName, eventType);
	            return index;
	        }
	        if (filterOperator == FilterOperator.NOT_IN_LIST_OF_VALUES)
	        {
	            index = new FilterParamIndexNotIn(propertyName, eventType);
	            return index;
	        }

	        // Handle all boolean expression
	        if (filterOperator == FilterOperator.BOOLEAN_EXPRESSION)
	        {
	            index = new FilterParamIndexBooleanExpr(eventType);
	            return index;
	        }
		}
	}
}
