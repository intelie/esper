using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.filter
{
    /// <summary>
    /// Encapsulates the information required by <seealso cref="IndexTreeBuilder"/> to maintain the filter parameter tree structure
    /// when filters are added and removed from the tree.
    /// </summary>

    public class IndexTreePath
    {
		private readonly ELinkedList<Pair<FilterParamIndexBase, Object>> indizes;

        /// <summary>
        /// Constructor.
        /// </summary>

        public IndexTreePath()
        {
            indizes = new ELinkedList<Pair<FilterParamIndexBase, Object>>();
        }

        /// <summary> Add an index to end of the list representing a path through indexes.</summary>
        /// <param name="index">to add
        /// </param>
        /// <param name="filteredForValue">is the value the index filters
        /// </param>

        public void Add(FilterParamIndexBase index, Object filteredForValue)
        {
            indizes.Add(new Pair<FilterParamIndexBase, Object>(index, filteredForValue));
        }

        /// <summary> Remove and return first index.</summary>
        /// <returns> first index
        /// </returns>

        public Pair<FilterParamIndexBase, Object> RemoveFirst()
        {
            if (indizes.Count > 0)
            {
                return indizes.RemoveFirst();
            }
            else
            {
                return null;
            }
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return indizes.ToString();
        }
    }
}