using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// An extended dictionary that uses a tree-based backing store.
    /// As such, the dictionary is always sorted.
    /// </summary>
    /// <typeparam name="K"></typeparam>
    /// <typeparam name="V"></typeparam>

	public class ETreeDictionary<K,V> : EDictionary<K,V>
	{
		private C5.TreeDictionary<K, V> m_subDictionary;

        /// <summary>
        /// Initializes a new instance of the <see cref="ETreeDictionary&lt;K, V&gt;"/> class.
        /// </summary>
		public ETreeDictionary() {
			m_subDictionary = new C5.TreeDictionary<K, V>() ;
		}

        /// <summary>
        /// Initializes a new instance of the <see cref="ETreeDictionary&lt;K, V&gt;"/> class.
        /// </summary>
        /// <param name="subDictionary">The sub dictionary.</param>
		public ETreeDictionary( C5.TreeDictionary<K, V> subDictionary ) {
			m_subDictionary = subDictionary ;
		}

        /// <summary>
        /// Initializes a new instance of the <see cref="ETreeDictionary&lt;K, V&gt;"/> class.
        /// </summary>
        /// <param name="comparer">The comparer.</param>
		public ETreeDictionary( IComparer<K> comparer ) {
			m_subDictionary = new C5.TreeDictionary<K, V>( comparer ) ;
		}
		
		/// <summary>
		/// Retrieves a dictionary that includes all elements less than
		/// or equal to the key.  This operation can be expensive, use with
		/// care.
		/// </summary>
		/// <param name="key"></param>
		/// <returns></returns>

		public virtual ETreeDictionary<K,V> Head( K key ) {
            C5.TreeDictionary<K, V> child = new C5.TreeDictionary<K, V>(m_subDictionary.Comparer);
			child.AddAll( m_subDictionary.RangeTo( key ) ) ;
			return new ETreeDictionary<K, V>( child ) ;
		}

        /// <summary>
        /// Retrieves the list of key-value pairs for all elements in the
        /// dictionary less than or equal to the key.  This operation is less
        /// expensive that the Head() method.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>

        public virtual IEnumerator<KeyValuePair<K,V>> HeadFast(K key)
        {
            foreach (C5.KeyValuePair<K, V> keyValuePair in m_subDictionary.RangeTo(key))
            {
                yield return new KeyValuePair<K, V>(keyValuePair.Key, keyValuePair.Value);
            }
        }
		
		/// <summary>
		/// Retrieves a dictionary that includes all elements greater than
		/// or equal to the key.  This operation can be expensive, use with
		/// care.
		/// </summary>
		/// <param name="key"></param>
		/// <returns></returns>
		
		public virtual ETreeDictionary<K,V> Tail( K key ) {
            C5.TreeDictionary<K, V> child = new C5.TreeDictionary<K, V>(m_subDictionary.Comparer);
			child.AddAll( m_subDictionary.RangeFrom( key ) ) ;
			return new ETreeDictionary<K, V>( child ) ;
		}


        /// <summary>
        /// Retrieves the list of key-value pairs for all elements in the
        /// dictionary greater than or equal to the key.  This operation is less
        /// expensive that the Tail() method.
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>

        public virtual IEnumerator<KeyValuePair<K, V>> TailFast(K key)
        {
            foreach (C5.KeyValuePair<K, V> keyValuePair in m_subDictionary.RangeFrom(key))
            {
                yield return new KeyValuePair<K, V>(keyValuePair.Key, keyValuePair.Value);
            }
        }

		/// <summary>
		/// Retrieves a dictionary that includes all elements greater than
		/// or equal to the lower key and less than or equal to the upper key.
		/// This operation can be expensive, use with care.
		/// </summary>
		/// <param name="lowerKey"></param>
		/// <param name="upperKey"></param>
		/// <returns></returns>
		
		public virtual ETreeDictionary<K,V> Range( K lowerKey, K upperKey ) {
            C5.TreeDictionary<K, V> child = new C5.TreeDictionary<K, V>(m_subDictionary.Comparer);
			child.AddAll( m_subDictionary.RangeFromTo( lowerKey, upperKey ) ) ;
			return new ETreeDictionary<K, V>( child ) ;
		}

		/// <summary>
		/// Retrieves a dictionary that includes all elements greater than
		/// or equal to the lower key and less than or equal to the upper key.
		/// This operation is less expensive than the Range operation.
		/// </summary>
		/// <param name="lowerKey"></param>
		/// <param name="upperKey"></param>
		/// <returns></returns>
		
		public virtual IEnumerator<KeyValuePair<K, V>> RangeFast( K lowerKey, K upperKey )
		{
            foreach (C5.KeyValuePair<K, V> keyValuePair in m_subDictionary.RangeFromTo(lowerKey, upperKey))
            {
                yield return new KeyValuePair<K, V>(keyValuePair.Key, keyValuePair.Value);
            }
		}
		
		/// <summary>
		/// Fetches the value associated with the specified key.
		/// If no value can be found, then the defaultValue is
		/// returned.
		/// </summary>
		/// <param name="key"></param>
		/// <param name="defaultValue"></param>
		/// <returns></returns>

		public virtual V Fetch( K key, V defaultValue ) {
			V returnValue ;
			if ( ! m_subDictionary.Find( key, out returnValue ) ) {
				returnValue = defaultValue;
			}
			return returnValue;			
		}

		/// <summary>
		/// Fetches the value associated with the specified key.
		/// If no value can be found, then default(V) is returned.
		/// </summary>
		/// <param name="key"></param>
		/// <returns></returns>
		
		public virtual V Fetch( K key ) {
			return Fetch( key, default(V) );			
		}
		
		/// <summary>
		/// Sets the given key in the dictionary.  If the key
		/// already exists, then it is remapped to thenew value.
		/// </summary>

		public virtual void Put( K key, V value )
		{
			this[key] = value ;
		}

		/// <summary>
		/// Puts all values from the source dictionary into
		/// this dictionary.
		/// </summary>
		/// <param name="source"></param>
		
		public virtual void PutAll( IDictionary<K,V> source )
		{
			foreach( KeyValuePair<K,V> kvPair in source ) {
				this[kvPair.Key] = kvPair.Value;
			}
		}

        /// <summary>
        /// Gets the first key.
        /// </summary>
        /// <value>The first key.</value>
		public virtual K FirstKey {
			get {
				return m_subDictionary.FindMin().Key ;
			}
		}

        /// <summary>
        /// Gets the last key.
        /// </summary>
        /// <value>The last key.</value>
		public virtual K LastKey {
			get {
				return m_subDictionary.FindMax().Key;
			}
		}
		
		/// <summary>
		/// Returns the first value in the enumeration of values
		/// </summary>
		/// <returns></returns>
		
		public virtual V FirstValue {
			get {
				IEnumerator<KeyValuePair<K,V>> kvPairEnum = GetEnumerator() ;
				kvPairEnum.MoveNext() ;
				return kvPairEnum.Current.Value;
			}
		}

        /// <summary>
        /// Removes the item from the dictionary that is associated with
        /// the specified key.  Returns the value that was found at that
        /// location and removed or the defaultValue.
        /// </summary>
        /// <param name="key">Search key into the dictionary</param>
        /// <param name="value">The value removed from the dictionary (if found).</param>
        /// <returns></returns>

        public bool Remove(K key, out V value)
        {
            return m_subDictionary.Remove(key, out value);
        }

        /// <summary>
        /// Gets or sets the <see cref="V"/> with the specified key.
        /// </summary>
        /// <value></value>
		public V this[K key]
		{
			get { return m_subDictionary[key] ; }
			set { m_subDictionary[key] = value ; }
		}

        /// <summary>
        /// Gets an <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the keys of the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>An <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the keys of the object that implements <see cref="T:System.Collections.Generic.IDictionary`2"></see>.</returns>
		public ICollection<K> Keys {
			get { return new C5CollectionWrapper<K>( m_subDictionary.Keys ) ; }
		}

        /// <summary>
        /// Gets an <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the values in the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>An <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the values in the object that implements <see cref="T:System.Collections.Generic.IDictionary`2"></see>.</returns>
		public ICollection<V> Values {
			get { return new C5CollectionWrapper<V>( m_subDictionary.Values ) ; }
		}

        /// <summary>
        /// Gets the number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>The number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</returns>
		public int Count {
			get { return m_subDictionary.Count ; }
		}

        /// <summary>
        /// Gets a value indicating whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.
        /// </summary>
        /// <value></value>
        /// <returns>true if the <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only; otherwise, false.</returns>
		public bool IsReadOnly {
			get { return m_subDictionary.IsReadOnly ; }
		}

        /// <summary>
        /// Determines whether the <see cref="T:System.Collections.Generic.IDictionary`2"></see> contains an element with the specified key.
        /// </summary>
        /// <param name="key">The key to locate in the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.</param>
        /// <returns>
        /// true if the <see cref="T:System.Collections.Generic.IDictionary`2"></see> contains an element with the key; otherwise, false.
        /// </returns>
        /// <exception cref="T:System.ArgumentNullException">key is null.</exception>
		public bool ContainsKey(K key)
		{
			return m_subDictionary.Contains( key ) ;
		}

        /// <summary>
        /// Adds an element with the provided key and value to the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </summary>
        /// <param name="key">The object to use as the key of the element to add.</param>
        /// <param name="value">The object to use as the value of the element to add.</param>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.IDictionary`2"></see> is read-only.</exception>
        /// <exception cref="T:System.ArgumentException">An element with the same key already exists in the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.</exception>
        /// <exception cref="T:System.ArgumentNullException">key is null.</exception>
		public void Add(K key, V value)
		{
			m_subDictionary.Add( key, value ) ;
		}

        /// <summary>
        /// Removes the element with the specified key from the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </summary>
        /// <param name="key">The key of the element to remove.</param>
        /// <returns>
        /// true if the element is successfully removed; otherwise, false.  This method also returns false if key was not found in the original <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </returns>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.IDictionary`2"></see> is read-only.</exception>
        /// <exception cref="T:System.ArgumentNullException">key is null.</exception>
		public bool Remove(K key)
		{
			return m_subDictionary.Remove( key ) ;
		}

        /// <summary>
        /// Tries the get value.
        /// </summary>
        /// <param name="key">The key.</param>
        /// <param name="value">The value.</param>
        /// <returns></returns>
		public bool TryGetValue(K key, out V value)
		{
			return m_subDictionary.Find( key, out value ) ;
		}

        /// <summary>
        /// Adds an item to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to add to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
		public void Add(KeyValuePair<K, V> item)
		{
			Add( item.Key, item.Value ) ;
		}

        /// <summary>
        /// Removes all items from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only. </exception>
		public void Clear()
		{
			m_subDictionary.Clear() ;
		}

        /// <summary>
        /// Determines whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> contains a specific value.
        /// </summary>
        /// <param name="item">The object to locate in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item is found in the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false.
        /// </returns>
		public bool Contains(KeyValuePair<K, V> item)
		{
			return m_subDictionary.Contains( item.Key ) ;
		}

        /// <summary>
        /// Copies the elements of the <see cref="T:System.Collections.Generic.ICollection`1"></see> to an <see cref="T:System.Array"></see>, starting at a particular <see cref="T:System.Array"></see> index.
        /// </summary>
        /// <param name="array">The one-dimensional <see cref="T:System.Array"></see> that is the destination of the elements copied from <see cref="T:System.Collections.Generic.ICollection`1"></see>. The <see cref="T:System.Array"></see> must have zero-based indexing.</param>
        /// <param name="arrayIndex">The zero-based index in array at which copying begins.</param>
        /// <exception cref="T:System.ArgumentOutOfRangeException">arrayIndex is less than 0.</exception>
        /// <exception cref="T:System.ArgumentNullException">array is null.</exception>
        /// <exception cref="T:System.ArgumentException">array is multidimensional.-or-arrayIndex is equal to or greater than the length of array.-or-The number of elements in the source <see cref="T:System.Collections.Generic.ICollection`1"></see> is greater than the available space from arrayIndex to the end of the destination array.-or-Type T cannot be cast automatically to the type of the destination array.</exception>
		public void CopyTo(KeyValuePair<K, V>[] array, int arrayIndex)
		{
			foreach( C5.KeyValuePair<K,V> item in m_subDictionary )
			{
				array.SetValue( new KeyValuePair<K,V>( item.Key, item.Value ), arrayIndex ) ;
				arrayIndex++ ;
			}
		}

        /// <summary>
        /// Removes the first occurrence of a specific object from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to remove from the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item was successfully removed from the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false. This method also returns false if item is not found in the original <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </returns>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
		public bool Remove(KeyValuePair<K, V> item)
		{
			return m_subDictionary.Remove( item.Key ) ;
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
		public IEnumerator<KeyValuePair<K, V>> GetEnumerator()
		{
            foreach (C5.KeyValuePair<K, V> keyValuePair in m_subDictionary)
            {
                yield return (new KeyValuePair<K, V>(keyValuePair.Key, keyValuePair.Value));
            }
		}

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            foreach (C5.KeyValuePair<K, V> keyValuePair in m_subDictionary)
            {
                yield return (new KeyValuePair<K, V>(keyValuePair.Key, keyValuePair.Value));
            }
        }

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>

        public override bool Equals(object obj)
        {
            if ((obj == null) || !(obj is ETreeDictionary<K,V>))
            {
                return false;
            }

            ETreeDictionary<K, V> oDictionary = (ETreeDictionary<K,V>)obj;
            if (m_subDictionary.Count != oDictionary.Count)
            {
                return false;
            }

            IEnumerator<C5.KeyValuePair<K, V>> enumA = m_subDictionary.GetEnumerator();
            IEnumerator<C5.KeyValuePair<K, V>> enumB = oDictionary.m_subDictionary.GetEnumerator();

            bool result = CollectionHelper.AreEqual(enumA, enumB) ;
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
            return m_subDictionary.GetHashCode();
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>

        public override string ToString()
        {
            return m_subDictionary.ToString();
        }
	}
}
