using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;

namespace net.esper.filter
{
	/// <summary>
    /// Utility class for matching filter parameters to indizes. Matches are indicated by the index <seealso cref="FilterParamIndexBase" />
	/// and the filter parameter <seealso cref="FilterSpecParam"/> featuring the same event property name and filter operator.
	/// </summary>

    public class IndexHelper
    {
        /// <summary> Find an index that matches one of the filter parameters passed.
        /// The parameter type and index type match up if the property name and
        /// filter operator are the same for the index and the filter parameter.
        /// For instance, for a filter parameter of "count EQUALS 10", the index against property "count" with
        /// operator type EQUALS will be returned, if present.
        /// NOTE: The caller is expected to obtain locks, if necessary, on the collections passed in.
		/// NOTE: Doesn't match non-property based index - thus boolean expressions don't get found and are always entered as a new index
        /// 
        /// </summary>
        /// <param name="parameters">is the list of sorted filter parameters
        /// </param>
        /// <param name="indizes">is the collection of indexes
        /// </param>
        /// <returns> A matching pair of filter parameter and index, if any matches were found. Null if no matches were found.
        /// </returns>

        public static Pair<FilterValueSetParam, FilterParamIndexBase> FindIndex(
        	TreeSet<FilterValueSetParam> parameters,
        	IList<FilterParamIndexBase> indizes)
        {
	        foreach (FilterValueSetParam parameter in parameters)
	        {
	            String property = parameter.PropertyName;
	            FilterOperator _operator = parameter.FilterOperator;

	            foreach (FilterParamIndexBase index in indizes)
	            {
	                // if property-based index, we prefer this in matching
	                if (index is FilterParamIndexPropBase)
	                {
	                    FilterParamIndexPropBase propBasedIndex = (FilterParamIndexPropBase) index;
	                    if ( (property.Equals(propBasedIndex.PropertyName)) &&
	                         (_operator.Equals(propBasedIndex.FilterOperator)) )
	                    {
	                        return new Pair<FilterValueSetParam, FilterParamIndexBase>(parameter, index);
	                    }
	                }
	            }
			}

			return null;
        }

        /// <summary> Determine among the passed in filter parameters any parameter that matches the given index on property name and
        /// filter operator type. Returns null if none of the parameters matches the index.
        /// </summary>
        /// <param name="parameters">is the filter parameter list
        /// </param>
        /// <param name="index">is a filter parameter constant value index
        /// </param>
        /// <returns> filter parameter, or null if no matching parameter found.
        /// </returns>
        
        public static FilterValueSetParam FindParameter(
        	TreeSet<FilterValueSetParam> parameters,
        	FilterParamIndexBase index)
        {
	        if (index is FilterParamIndexPropBase)
	        {
	            FilterParamIndexPropBase propBasedIndex = (FilterParamIndexPropBase) index;
	            String indexProperty = propBasedIndex.PropertyName;
	            FilterOperator indexOperator = propBasedIndex.FilterOperator;

	            foreach (FilterValueSetParam parameter in parameters)
	            {
	                String paramProperty = parameter.PropertyName;
	                FilterOperator paramOperator = parameter.FilterOperator;

	                if ( (paramProperty.Equals(indexProperty)) &&
	                     (paramOperator.Equals(indexOperator)) )
	                {
	                    return parameter;
	                }
	            }
	        }
	        else
	        {
	            foreach (FilterValueSetParam parameter in parameters)
	            {
	                FilterOperator paramOperator = parameter.FilterOperator;

	                if (paramOperator.Equals(index.FilterOperator))
	                {
	                    return parameter;
	                }
	            }
	        }

	        return null;
        }
    }
}
