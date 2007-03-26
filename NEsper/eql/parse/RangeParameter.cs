using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;

namespace net.esper.eql.parse
{
	/// <summary>
    /// Represents a range of numbers as a parameter.
    /// </summary>
	
    public class RangeParameter : NumberSetParameter
	{
		/// <summary> Returns Start of range.</summary>
		/// <returns> Start of range
		/// </returns>
		virtual public int Low
		{
			get
			{
				return low;
			}
		}

        /// <summary> Returns end of range.</summary>
		/// <returns> end of range
		/// </returns>
		virtual public int High
		{
			get
			{
				return high;
			}
		}

        private int low;
		private int high;
		
		/// <summary> Ctor.</summary>
		/// <param name="low">Start of range
		/// </param>
		/// <param name="high">end of range
		/// </param>
		
        public RangeParameter(int low, int high)
		{
			this.low = low;
			this.high = high;
		}

        /// <summary>
        /// Returns true if all values between and including min and max are supplied by the parameter.
        /// </summary>
        /// <param name="min">lower end of range</param>
        /// <param name="max">upper end of range</param>
        /// <returns>
        /// true if parameter specifies all int values between min and max, false if not
        /// </returns>
		public virtual bool IsWildcard(int min, int max)
		{
			if ((min <= low) && (max >= high))
			{
				return true;
			}
			return false;
		}

        /// <summary>
        /// Return a set of int values representing the value of the parameter for the given range.
        /// </summary>
        /// <param name="min">lower end of range</param>
        /// <param name="max">upper end of range</param>
        /// <returns>set of integer</returns>
        public ISet<Int32> GetValuesInRange(int min, int max)
        {
            ISet<Int32> values = new EHashSet<Int32>();

            int start = (min > low) ? min : low;
            int end = (max > high) ? high : max;

            while (start <= end)
            {
                values.Add(start);
                start++;
            }

            return values;
        }
	}
}