using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;

namespace net.esper.eql.parse
{
    /// <summary>
    /// Interface to generate a set of integers from parameters that include ranges, lists and frequencies.
    /// </summary>

    public interface NumberSetParameter
	{
		/// <summary> Returns true if all values between and including min and max are supplied by the parameter.</summary>
		/// <param name="min">- lower end of range
		/// </param>
		/// <param name="max">- upper end of range
		/// </param>
		/// <returns> true if parameter specifies all int values between min and max, false if not
		/// </returns>
	
        bool IsWildcard(int min, int max);
		
		/// <summary> Return a set of int values representing the value of the parameter for the given range.</summary>
		/// <param name="min">- lower end of range
		/// </param>
		/// <param name="max">- upper end of range
		/// </param>
		/// <returns> set of integer
		/// </returns>
    	
        ISet<Int32> GetValuesInRange(int min, int max) ;
	}
}