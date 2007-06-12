using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
    /// <summary>
    /// A Double-typed value as a filter parameter representing a range.
    /// </summary>

    public class RangeValueDouble : FilterSpecParamRangeValue
    {
        private readonly double doubleValue;

        /// <summary> Ctor.</summary>
        /// <param name="doubleValue">is the value of the range endpoint
        /// </param>

        public RangeValueDouble(double doubleValue)
        {
            this.doubleValue = doubleValue;
        }

        /// <summary>
        /// Returns the filter value representing the endpoint.
        /// </summary>
        /// <param name="matchedEvents">is the prior results</param>
        /// <returns>filter value</returns>
	    public double? GetFilterValue(MatchedEventMap matchedEvents)
	    {
	        return doubleValue;
	    }

        /// <summary>
        /// Returns the constant value.
		/// </summary>
        public double DoubleValue
        {
            get { return doubleValue; }
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return doubleValue.ToString();
        }

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>
        public override bool Equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (!(obj is RangeValueDouble))
            {
                return false;
            }

            RangeValueDouble other = (RangeValueDouble)obj;
            return other.doubleValue == this.doubleValue;
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
