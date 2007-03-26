using System;

namespace net.esper.collection
{
	/// <summary> Based on the {@link PermutationEnumeration} this enumeration provides, among a set of supplied integer
	/// values, all permutations of order these values can come in, ie.
	/// Example: {0, 2, 3} results in 6 enumeration values ending in {3, 2, 0}.
	/// </summary>
	public class NumberSetPermutationEnumeration : System.Collections.Generic.IEnumerator<int[]>
	{
        private readonly int[] numberSet;
        private readonly PermutationEnumeration permutationEnumeration;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="numberSet">set of integer numbers to permutate and provide each combination.</param>
        
        public NumberSetPermutationEnumeration(int[] numberSet)
    	{
        	this.numberSet = numberSet;
        	this.permutationEnumeration = new PermutationEnumeration(numberSet.Length);
    	}

        #region IEnumerator<int[]> Members

        /// <summary>
        /// Gets the element in the collection at the current position of the enumerator.
        /// </summary>
        /// <value></value>
        /// <returns>The element in the collection at the current position of the enumerator.</returns>
        public int[] Current
        {
            get
            {
                int[] permutation = permutationEnumeration.Current;
                int[] result = new int[numberSet.Length];
                for (int i = 0; i < numberSet.Length; i++)
                {
                    result[i] = numberSet[permutation[i]];
                }

                return result;
            }
        }

        #endregion

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
        object System.Collections.IEnumerator.Current
        {
            get
            {
                return this.Current;
            }
        }

        /// <summary>
        /// Advances the enumerator to the next element of the collection.
        /// </summary>
        /// <returns>
        /// true if the enumerator was successfully advanced to the next element; false if the enumerator has passed the end of the collection.
        /// </returns>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>
        public bool MoveNext()
        {
            return permutationEnumeration.MoveNext();
        }

        /// <summary>
        /// Sets the enumerator to its initial position, which is before the first element in the collection.
        /// </summary>
        /// <exception cref="T:System.InvalidOperationException">The collection was modified after the enumerator was created. </exception>
        public void Reset()
        {
            permutationEnumeration.Reset();
        }

        #endregion
    }
}
