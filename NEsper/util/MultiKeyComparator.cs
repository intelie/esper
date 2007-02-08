using System;
using System.Collections.Generic;

using net.esper.collection;

namespace net.esper.util
{
	
	/// <summary> A comparator on multikeys. The multikeys must contain the same
	/// number of values.
	/// </summary>

    public sealed class MultiKeyComparator<T> : IComparer<MultiKey<T>>
	{
		private readonly Boolean[] isDescendingValues;
		
		/// <summary> Ctor.</summary>
		/// <param name="isDescendingValues">- each value is true if the corresponding (same index)
		/// entry in the multi-keys is to be sorted in descending order. The multikeys
		/// to be compared must have the same number of values as this array.
		/// </param>
		
        public MultiKeyComparator(Boolean[] isDescendingValues)
		{
			this.isDescendingValues = isDescendingValues;
		}
		
		public int Compare(MultiKey<T> firstValues, MultiKey<T> secondValues)
		{
			if (firstValues.Count != isDescendingValues.Length || secondValues.Count != isDescendingValues.Length)
			{
				throw new ArgumentException("Incompatible size MultiKey sizes for comparison");
			}
			
			for (int i = 0; i < firstValues.Count; i++)
			{
				Object valueOne = firstValues[i] ;
				Object valueTwo = secondValues[i];
				bool isDescending = isDescendingValues[i];
				
				int comparisonResult = compareValues(valueOne, valueTwo, isDescending);
				if (comparisonResult != 0)
				{
					return comparisonResult;
				}
			}
			
			// Make the comparator compatible with equals
			if (!firstValues.Equals(secondValues))
			{
				return - 1;
			}
			else
			{
				return 0;
			}
		}
		
		private static int compareValues(Object valueOne, Object valueTwo, bool isDescending)
		{
			if (isDescending)
			{
				Object temp = valueOne;
				valueOne = valueTwo;
				valueTwo = temp;
			}
			
			if (valueOne == null || valueTwo == null)
			{
				// A null value is considered equal to another null
				// value and smaller than any nonnull value
				if (valueOne == null && valueTwo == null)
				{
					return 0;
				}
				if (valueOne == null)
				{
					return - 1;
				}
				return 1;
			}
			
			IComparable comparable1;
			if (valueOne is IComparable)
			{
				comparable1 = (IComparable) valueOne;
			}
			else
			{
				throw new System.InvalidCastException("Cannot sort objects of type " + valueOne.GetType());
			}
			
			return comparable1.CompareTo(valueTwo);
		}
    }
}