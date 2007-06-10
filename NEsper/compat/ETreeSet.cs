using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// An extended set that uses a tree-based backing store.
    /// As such, the set is always sorted.
    /// </summary>
    /// <typeparam name="T"></typeparam>
    
    public class ETreeSet<T> : C5.TreeSet<T>, Set<T>
	{
        /// <summary>
        /// Initializes a new instance of the <see cref="ETreeSet&lt;T&gt;"/> class.
        /// </summary>
        public ETreeSet()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="ETreeSet&lt;T&gt;"/> class.
        /// </summary>
        /// <param name="comparer">The comparer.</param>
        public ETreeSet(IComparer<T> comparer)
            : base(comparer)
        {
        }
		
		/// <summary>
		/// Gets the first item in the set.
		/// </summary>
		/// <value>The first item in the set.</value>

		public T First
		{
			get { return this.FindMin(); }
		}

		/// <summary>
		/// Gets the last item in the set.
		/// </summary>

		public T Last
		{
			get { return this.FindMax(); }
		}

		/// <summary>
		/// Returns a set that includes all items that are greater than
		/// or equal to the index value.
		/// </summary>
		/// <param name="from">Value to index from.</param>
		/// <returns></returns>
		
		public ETreeSet<T> TailSet( T from )
		{
			ETreeSet<T> sortedSet = new ETreeSet<T>();
			sortedSet.AddAll<T>( RangeFrom( from ) );
			return sortedSet;
		}

		#region Set<T> Members

		/// <summary>
		/// Adds an item to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
		/// </summary>
		/// <param name="item">The object to add to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
		/// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
		
		public new void Add( T item )
		{
			base.Add( item );
		}

		/// <summary>
		/// Adds all of the items in the source.
		/// </summary>
		/// <param name="source">The source.</param>

		public void AddAll( IEnumerable<T> source )
		{
			base.AddAll<T>( source );
		}

        /// <summary>
        /// Removes all items.
        /// </summary>
        /// <param name="items"></param>

        public void RemoveAll(IEnumerable<T> items)
        {
            foreach (T item in items)
            {
                Remove(item);
            }
        }

		#endregion
	}
}
