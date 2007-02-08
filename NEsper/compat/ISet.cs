using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
	public interface ISet<T> : ICollection<T>
	{
		/// <summary>
		/// Converts the set to an array.
		/// </summary>
		/// <returns></returns>

		T[] ToArray();

        /// <summary>
        /// Adds all of the items in the source.
        /// </summary>
        /// <param name="source">The source.</param>
        
        void AddAll(IEnumerable<T> source);

        /// <summary>
        /// Returns the first item in the set
        /// </summary>
        /// <returns></returns>
        
        T First { get ; }
        
        /// <summary>
        /// Gets a value indicating whether this instance is empty.
        /// </summary>
        /// <value><c>true</c> if this instance is empty; otherwise, <c>false</c>.</value>
        
        bool IsEmpty { get; }

        /// <summary>
        /// Removes all items.
        /// </summary>
        /// <param name="items"></param>

        void RemoveAll(IEnumerable<T> items);
	}
}
