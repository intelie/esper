using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.eql.parse
{
	/// <summary>
    /// Encapsulates a parameter specifying a frequency, i.e. '* / 5'.
    /// </summary>
	
    public class FrequencyParameter : NumberSetParameter
	{
        private int frequency;

        /// <summary> Returns frequency.</summary>
		/// <returns> frequency divisor
		/// </returns>
		virtual public int Frequency
		{
			get { return frequency; }
		}

		/// <summary> Ctor.</summary>
		/// <param name="frequency">divisor specifying frequency
		/// </param>
		
        public FrequencyParameter(int frequency)
		{
			this.frequency = frequency;
			if (frequency <= 0)
			{
				throw new ArgumentException("Zero or negative value supplied as freqeuncy");
			}
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
			if (frequency == 1)
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
		public Set<Int32> GetValuesInRange(int min, int max)
		{
			Set<Int32> values = new EHashSet<Int32>();
			int start = min - min % frequency;
			
			do 
			{
				if (start >= min)
				{
                    values.Add(start);
				}
				start += frequency;
			}
			while (start <= max);
			
			return values;
		}
	}
}
