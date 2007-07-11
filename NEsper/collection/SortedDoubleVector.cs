using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.collection
{
    /// <summary>
    /// Sorted, reference-counting set based on a SortedDictionary implementation that stores keys and a
    /// reference counter for each unique key value. Each time the same key is added, the reference
    /// counter increases. Each time a key is removed, the reference counter decreases.
    /// </summary>
    
    public class SortedDoubleVector
    {
        private IList<Double> values;

        /// <summary>
        /// Constructor.
        /// </summary>

        public SortedDoubleVector()
        {
            values = new List<Double>();
        }

        /// <summary> Returns the number of items in the collection.</summary>
        /// <returns> size
        /// </returns>
        public virtual int Count
        {
            get
            {
                return values.Count;
            }
        }

        /// <summary> Returns the value at a given index.</summary>
        /// <param name="index">for which to return value for
        /// </param>
        /// <returns> value at index
        /// </returns>
        public virtual double this[int index]
        {
			get
			{
				return values[index];
			}
        }

        /// <summary> Add a value to the collection.</summary>
        /// <param name="val">is the double-type value to add
        /// </param>
        public virtual void Add(double val)
        {
            int index = FindInsertIndex(val);

            if (index == -1)
            {
                values.Add(val);
            }
            else
            {
                values.Insert(index, val);
            }
        }

        /// <summary> Remove a value from the collection.</summary>
        /// <param name="val">to remove
        /// </param>
        /// <throws>  IllegalStateException if the value has not been added </throws>
        
        public virtual void Remove(double val)
        {
            int index = FindInsertIndex(val);
            if ((index == -1) || (values[index] != val))
            {
                throw new IllegalStateException("Value not found in collection");
            }
            values.RemoveAt(index) ;
            return;
        }

        /// <summary>
        /// Returns underlying vector, for testing purposes only.
        /// </summary>
        /// <returns>vector with double values</returns>

        public IList<Double> Values
        {
			get
			{
				return values;
			}
        }

        /// <summary> Returns the index into which to insert to.
        /// Proptected access level for convenient testing.
        /// </summary>
        /// <param name="val">to find insert index
        /// </param>
        /// <returns> position to insert the value to, or -1 to indicate to add to the end.
        /// </returns>
        public virtual int FindInsertIndex(double val)
        {
            if (values.Count > 2)
            {
                int startIndex = values.Count >> 1 ;
                double startValue = values[startIndex];
                int insertAt = -1;

                if (val < startValue)
                {
                    // find in lower half
                    insertAt = FindInsertIndex(0, startIndex - 1, val);
                }
                else if (val > startValue)
                {
                    // find in upper half
                    insertAt = FindInsertIndex(startIndex + 1, values.Count - 1, val);
                }
                else
                {
                    // we hit the value
                    insertAt = startIndex;
                }

                if (insertAt == values.Count)
                {
                    return -1;
                }
                return insertAt;
            }

            if (values.Count == 2)
            {
                if (val > values[1])
                {
                    return -1;
                }
                else if (val <= values[0])
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }

            if (values.Count == 1)
            {
                if (val > values[0])
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }

            return -1;
        }

        private int FindInsertIndex(int lowerBound, int upperBound, double val)
        {
			while( true )
			{
	            if (upperBound == lowerBound)
	            {
	                double valueLowerBound = values[lowerBound];
	                if (val <= valueLowerBound)
	                {
	                    return lowerBound;
	                }
	                else
	                {
	                    return lowerBound + 1;
	                }
	            }

	            if (upperBound - lowerBound == 1)
	            {
	                double valueLowerBound = values[lowerBound];
	                if (val <= valueLowerBound)
	                {
	                    return lowerBound;
	                }

	                double valueUpperBound = values[upperBound];
	                if (val > valueUpperBound)
	                {
	                    return upperBound + 1;
	                }

	                return upperBound;
	            }

	            int nextMiddle = lowerBound + ((upperBound - lowerBound) >> 1);
	            double valueAtMiddle = values[nextMiddle];

	            if (val < valueAtMiddle)
	            {
	                // find in lower half
	                upperBound = nextMiddle - 1;
	            }
	            else if (val > valueAtMiddle)
	            {
	                // find in upper half
	                lowerBound = nextMiddle;
	            }
	            else
	            {
	                return nextMiddle;
	            }
			}
        }
    }
}
