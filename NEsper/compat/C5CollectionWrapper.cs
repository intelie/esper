using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// Provides a collection facade around a C5 collection.
    /// </summary>
    /// <typeparam name="T"></typeparam>

	public class C5CollectionWrapper<T> : ICollection<T>
	{
		private C5.ICollectionValue<T> m_subCollection;

        /// <summary>
        /// Initializes a new instance of the <see cref="C5CollectionWrapper&lt;T&gt;"/> class.
        /// </summary>
        /// <param name="subCollection">The sub collection.</param>
		public C5CollectionWrapper( C5.ICollectionValue<T> subCollection )
		{
			m_subCollection = subCollection ;
		}

        /// <summary>
        /// Gets the number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>The number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</returns>
		public int Count {
			get { return m_subCollection.Count ; }
		}

        /// <summary>
        /// Gets a value indicating whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.
        /// </summary>
        /// <value></value>
        /// <returns>true if the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only; otherwise, false.</returns>
		public bool IsReadOnly {
			get {
				return
					( m_subCollection is C5.ICollection<T> ) ?
					( m_subCollection as C5.ICollection<T> ).IsReadOnly :
					( true ) ;
			}
		}

        /// <summary>
        /// Adds an item to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to add to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
		public void Add(T item)
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				( m_subCollection as C5.ICollection<T> ).Add( item ) ;
			} else {
				throw new NotSupportedException() ;
			}
		}

        /// <summary>
        /// Removes all items from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only. </exception>
		public void Clear()
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				( m_subCollection as C5.ICollection<T> ).Clear() ;
			} else {
				throw new NotSupportedException() ;
			}
		}

        /// <summary>
        /// Determines whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> contains a specific value.
        /// </summary>
        /// <param name="item">The object to locate in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item is found in the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false.
        /// </returns>
		public bool Contains(T item)
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				return ( m_subCollection as C5.ICollection<T> ).Contains( item ) ;
			} else {
				throw new NotSupportedException() ;
			}
		}

        /// <summary>
        /// Copies the elements of the <see cref="T:System.Collections.Generic.ICollection`1"></see> to an <see cref="T:System.Array"></see>, starting at a particular <see cref="T:System.Array"></see> index.
        /// </summary>
        /// <param name="array">The one-dimensional <see cref="T:System.Array"></see> that is the destination of the elements copied from <see cref="T:System.Collections.Generic.ICollection`1"></see>. The <see cref="T:System.Array"></see> must have zero-based indexing.</param>
        /// <param name="arrayIndex">The zero-based index in array at which copying begins.</param>
        /// <exception cref="T:System.ArgumentOutOfRangeException">arrayIndex is less than 0.</exception>
        /// <exception cref="T:System.ArgumentNullException">array is null.</exception>
        /// <exception cref="T:System.ArgumentException">array is multidimensional.-or-arrayIndex is equal to or greater than the length of array.-or-The number of elements in the source <see cref="T:System.Collections.Generic.ICollection`1"></see> is greater than the available space from arrayIndex to the end of the destination array.-or-Type T cannot be cast automatically to the type of the destination array.</exception>
		public void CopyTo(T[] array, int arrayIndex)
		{
			m_subCollection.CopyTo( array, arrayIndex ) ;
		}

        /// <summary>
        /// Removes the first occurrence of a specific object from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to remove from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item was successfully removed from the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false. This method also returns false if item is not found in the original <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </returns>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
		public bool Remove(T item)
		{
			if ( m_subCollection is C5.ICollection<T> ) {
				return ( m_subCollection as C5.ICollection<T> ).Remove( item ) ;
			} else {
				throw new NotSupportedException() ;
			}
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
		public IEnumerator<T> GetEnumerator()
		{
			return m_subCollection.GetEnumerator() ;
		}

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>
		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			return m_subCollection.GetEnumerator() ;
		}
	}
}
