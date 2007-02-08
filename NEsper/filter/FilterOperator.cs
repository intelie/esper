using System;
namespace net.esper.filter
{
	/// <summary> Defines the different operator types available for event filters.
	/// 
	/// Mathematical notation for defining ranges of floating point numbers is used as defined below:
	/// <p>[a,b]  a closed range from value a to value b with the end-points a and b included in the range
	/// <p>(a,b)  an open range from value a to value b with the end-points a and b not included in the range
	/// <p>[a,b)  a half-open range from value a to value b with the end-point a included and end-point b not included
	/// in the range
	/// <p>(a,b]  a half-open range from value a to value b with the end-point a not included and end-point b included in the range
	/// </summary>
    public enum FilterOperator
    {
    	/// <summary> Exact matches (=).</summary>
        EQUAL,
        /// <summary> Exact not matches (!=).</summary>
        NOT_EQUAL,
        /// <summary> Less (<).</summary>
        LESS,
        /// <summary> Less or equal (<=).</summary>
        LESS_OR_EQUAL,
        /// <summary> Greater or equal (>=).</summary>
        GREATER_OR_EQUAL,
        /// <summary> Greater (>).</summary>
        GREATER,
        /// <summary> Range contains neither endpoint, i.e. (a,b)</summary>
        RANGE_OPEN,
        /// <summary> Range contains low and high endpoint, i.e. [a,b]</summary>
        RANGE_CLOSED,
        /// <summary> Range includes low endpoint but not high endpoint, i.e. [a,b)</summary>
        RANGE_HALF_OPEN,
        /// <summary> Range includes high endpoint but not low endpoint, i.e. (a,b]</summary>
        RANGE_HALF_CLOSED
    };
    
    public class FilterOperatorHelper
    {
        private const String EQUAL_OP = "=";
        private const String NOT_EQUAL_OP = "!=";
        private const String LESS_OP = "<";
        private const String LESS_EQUAL_OP = "<=";
        private const String GREATER_OP = ">";
        private const String GREATER_EQUAL_OP = ">=";

        /// <summary> Returns true for range operator, false if not a range operator.</summary>
        /// <returns> true for ranges, false for anyting else
        /// </returns>
        public static bool isRangeOperator( FilterOperator op )
        {
            if ((op == FilterOperator.RANGE_CLOSED) ||
    	        (op == FilterOperator.RANGE_OPEN) ||
        	    (op == FilterOperator.RANGE_HALF_OPEN) ||
            	(op == FilterOperator.RANGE_HALF_CLOSED))
            {
                return true;
            }
            return false;
        }

        /// <summary> Returns true for relational comparison operators which excludes the = equals operator, else returns false.</summary>
        /// <returns> true for lesser or greater -type operators, false for anyting else
        /// </returns>
        public static bool isComparisonOperator( FilterOperator op )
        {
            if ((op == FilterOperator.LESS) ||
            	(op == FilterOperator.LESS_OR_EQUAL) ||
            	(op == FilterOperator.GREATER) ||
            	(op == FilterOperator.GREATER_OR_EQUAL))
            {
                return true;
            }
            return false;
        }

        /// <summary> Parse the comparison operator returning null if not a valid operator.</summary>
        /// <param name="operator">
        /// </param>
        /// <returns> FilterOperator or null if not valid
        /// </returns>

        public static FilterOperator? parseComparisonOperator(String op)
        {
            if (op == null)
            {
                return null;
            }

            switch( op )
            {
            	case EQUAL_OP:
            		return FilterOperator.EQUAL;
            	case NOT_EQUAL_OP:
            		return FilterOperator.NOT_EQUAL;
            	case LESS_OP:
            		return FilterOperator.LESS;
            	case LESS_EQUAL_OP:
            		return FilterOperator.LESS_OR_EQUAL;
            	case GREATER_OP:
            		return FilterOperator.GREATER;
            	case GREATER_EQUAL_OP:
            		return FilterOperator.GREATER_OR_EQUAL;
            	default:
		            return null;
            }
        }

        /// <summary> Parse the range operator from booleans describing whether the Start or end values are exclusive.</summary>
        /// <param name="isInclusiveFirst">true if low endpoint is inclusive, false if not
        /// </param>
        /// <param name="isInclusiveLast">true if high endpoint is inclusive, false if not
        /// </param>
        /// <returns> FilterOperator for the combination inclusive or exclusive
        /// </returns>
        public static FilterOperator parseRangeOperator(bool isInclusiveFirst, bool isInclusiveLast)
        {
            if (isInclusiveFirst && isInclusiveLast)
            {
                return FilterOperator.RANGE_CLOSED;
            }
            
            if (isInclusiveFirst && !isInclusiveLast)
            {
                return FilterOperator.RANGE_HALF_OPEN;
            }
            
            if (isInclusiveLast)
            {
                return FilterOperator.RANGE_HALF_CLOSED;
            }
            
            return FilterOperator.RANGE_OPEN;
        }
    }
}
