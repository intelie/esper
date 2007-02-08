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

        public void Dispose()
        {
        }

        #endregion

        #region IEnumerator Members

        object System.Collections.IEnumerator.Current
        {
            get
            {
                return this.Current;
            }
        }

        public bool MoveNext()
        {
            return permutationEnumeration.MoveNext();
        }

        public void Reset()
        {
            permutationEnumeration.Reset();
        }

        #endregion
    }
}
