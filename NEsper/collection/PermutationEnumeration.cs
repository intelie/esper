using System;
using System.Collections;
using System.Collections.Generic;

namespace net.esper.collection
{
	/// <summary> Provides a N! (n-faculty) number of permutations for N elements.
	/// Example: for 3 elements provides 6 permutations exactly as follows:
	/// {0, 1, 2}
	/// {0, 2, 1}
	/// {1, 0, 2}
	/// {1, 2, 0}
	/// {2, 0, 1}
	/// {2, 1, 0}
	/// </summary>

    public class PermutationEnumeration : IEnumerator<int[]>
    {
        private readonly int[] factors;
        private readonly int numElements;
        private readonly int maxNumPermutation;
        private int currentPermutation;
        private int[] element;

        /**
         * Ctor.
         * @param numElements - number of elements in each permutation.
         */
        
        public PermutationEnumeration(int numElements)
        {
            if (numElements < 1)
            {
                throw new ArgumentException("Invalid element number of 1");
            }
            this.numElements = numElements;
            this.factors = getFactors(numElements);
            this.maxNumPermutation = faculty(numElements);
        }

        public Boolean MoveNext()
        {
            if (currentPermutation >= maxNumPermutation)
            {
                currentPermutation = maxNumPermutation + 1;
                return false;
            }

            element = getPermutation(numElements, currentPermutation, factors);
            currentPermutation++;
            return true;
        }

        public int[] Current
        {
            get
            {
                if (currentPermutation > maxNumPermutation)
                {
                	throw new ArgumentOutOfRangeException() ;
                }

                return element;
            }
        }

        /**
         * Returns permutation.
         * @param numElements - number of elements in each permutation 
         * @param permutation - number of permutation to compute, between 0 and numElements!
         * @param factors - factors for each index
         * @return permutation
         */
        public static int[] getPermutation(int numElements, int permutation, int[] factors)
        {
            /*
            Example:
                numElements = 4
                permutation = 21
                factors = {6, 2, 1, 0}

            Init:   out {0, 1, 2, 3}

            21 / 6                      == index 3 -> result {3}, out {0, 1, 2}
            remainder 21 - 3 * 6        == 3
            3 / 2 = second number       == index 1 -> result {3, 1}, out {0, 2}
            remainder 3 - 1 * 2         == 1
                                        == index 1 -> result {3, 1, 2} out {0}
            */

            int[] result = new int[numElements];
            IList<Int32> outList = new List<Int32>();
            for (int i = 0; i < numElements; i++)
            {
                outList.Add(i);
            }
            int currentVal = permutation;

            for (int position = 0; position < numElements - 1; position++)
            {
                int factor = factors[position];
                int index = currentVal / factor;
                result[position] = outList[index];
                outList.RemoveAt(index);
                currentVal = currentVal - index * factor;
            }
            result[numElements - 1] = outList[0];

            return result;
        }

        /**
         * Returns factors for computing the permutation.
         * @param numElements - number of factors to compute
         * @return factors list
         */
        public static int[] getFactors(int numElements)
        {
            int[] facultyFactors = new int[numElements];

            for (int i = 0; i < numElements - 1; i++)
            {
                facultyFactors[i] = faculty(numElements - i - 1);
            }

            return facultyFactors;
        }

        /**
         * Computes faculty of N.
         * @param num to compute faculty for
         * @return N!
         */
        public static int faculty(int num)
        {
            if (num == 0)
            {
                return 0;
            }

            int fac = 1;
            for (int i = 1; i <= num; i++)
            {
                fac = fac * i;
            }
            return fac;
        }

        #region IDisposable Members

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        public void Dispose()
        {
        }

        #endregion

        #region IEnumerator Members

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        /// <value></value>
        /// <returns>The element in the collection at the current position of the enumerator.</returns>
        Object System.Collections.IEnumerator.Current
        {
            get { return this.Current; }
        }

        /// <summary>
        /// Sets the enumerator to its initial position, which is before the first element in the collection.
        /// </summary>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>
        public void Reset()
        {
            throw new Exception("The method or operation is not implemented.");
        }

        #endregion
    }
}
