using System;
using System.Collections.Generic;

namespace net.esper.filter
{
	
	/// <summary>
    /// Comparator for DoubleRange values.
	/// <para>
    /// Sorts double ranges as this:     sort by min asc, max asc.
	/// I.e. same minimum value sorts maximum value ascending.
    /// </para>
	/// </summary>

    public sealed class DoubleRangeComparator : IComparer<DoubleRange>
	{
        /// <summary>
        /// Compares the specified double ranges.
        /// </summary>
        /// <param name="r1">The r1.</param>
        /// <param name="r2">The r2.</param>
        /// <returns></returns>
		public int Compare(DoubleRange r1, DoubleRange r2)
		{
			double? minOne = r1.Min;
			double? minTwo = r2.Min;
			double? maxOne = r1.Max;
			double? maxTwo = r2.Max;
			
			if (minOne < minTwo)
			{
				return - 1;
			}
			if (minOne > minTwo)
			{
				return 1;
			}
			if (maxOne < maxTwo)
			{
				return - 1;
			}
			if (maxOne > maxTwo)
			{
				return 1;
			}
			
			return 0;
		}
    }
}
