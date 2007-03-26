using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
    /// <summary>
    /// An extended set using a hashing algorithm.
    /// </summary>
    /// <typeparam name="T"></typeparam>

	public sealed class EHashSet<T> : ISet<T>
	{
		private Dictionary<T, T> m_dataTable = new Dictionary<T, T>() ;

		/// <summary>
		/// Constructor
		/// </summary>
		
		public EHashSet() 
		{
		}
		
		/// <summary>
		/// Constructor with set for source data.
		/// </summary>
		/// <param name="sourceData"></param>
		
		public EHashSet( ISet<T> sourceData )
		{
			AddAll( sourceData ) ;
		}
		
		
        #region ISet<T> Members

		/// <summary>
		/// Converts the set to an array.
		/// </summary>
		/// <returns></returns>

		public T[] ToArray()
		{
			T[] array = new T[m_dataTable.Count];
			m_dataTable.Keys.CopyTo( array, 0 );
			return array;
		}

		/// <summary>
        /// Adds all of the items in the source.
        /// </summary>
        /// <param name="source">The source.</param>

        public void AddAll(IEnumerable<T> source)
        {
            foreach (T value in source)
            {
                Add(value);
            }
        }

        /// <summary>
        /// Returns the first item in the set
        /// </summary>
        /// <returns></returns>
        
        public T First
        {
        	get 
        	{
	        	IEnumerator<T> tableEnum = GetEnumerator() ;
	        	tableEnum.MoveNext() ;
	        	return tableEnum.Current ;
        	}
        }
        
        /// <summary>
        /// Gets a value indicating whether this instance is empty.
        /// </summary>
        /// <value><c>true</c> if this instance is empty; otherwise, <c>false</c>.</value>

        public bool IsEmpty
        {
            get { return this.Count == 0; }
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

        #region ICollection<T> Members

        /// <summary>
        /// Adds an item to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to add to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
        public void Add(T item)
        {
            m_dataTable[item] = item;
        }

        /// <summary>
        /// Removes all items from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only. </exception>
        public void Clear()
        {
            m_dataTable.Clear();
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
            return m_dataTable.ContainsKey(item);
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
            m_dataTable.Keys.CopyTo(array, arrayIndex);
        }

        /// <summary>
        /// Gets the number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>The number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</returns>
        public int Count
        {
            get { return m_dataTable.Count; }
        }

        /// <summary>
        /// Gets a value indicating whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.
        /// </summary>
        /// <value></value>
        /// <returns>true if the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only; otherwise, false.</returns>
        public bool IsReadOnly
        {
            get { return false; }
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
            return m_dataTable.Remove(item);
        }

        #endregion

        #region IEnumerable<T> Members

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public IEnumerator<T> GetEnumerator()
        {
            return m_dataTable.Keys.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return m_dataTable.Keys.GetEnumerator();
        }

        #endregion

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>

        public override bool Equals(object obj)
        {
            if ((obj == null) || !(obj is EHashSet<T>))
            {
                return false;
            }

            EHashSet<T> oHashTable = (EHashSet<T>)obj;
            bool result = CollectionHelper.AreEqual(m_dataTable.Keys, oHashTable.m_dataTable.Keys);
            return result;
        }

        /// <summary>
        /// Serves as a hash function for a particular type. <see cref="M:System.Object.GetHashCode"></see> is suitable for use in hashing algorithms and data structures like a hash table.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>

        public override int GetHashCode()
        {
            return m_dataTable.GetHashCode();
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>

        public override string ToString()
        {
            return m_dataTable.ToString();
        }
    }
}
