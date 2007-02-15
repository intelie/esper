using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class ETreeDictionary<K,V> : EDictionary<K,V>
	{
		private C5.TreeDictionary<K, V> m_subDictionary;
		
		public ETreeDictionary() {
			m_subDictionary = new C5.TreeDictionary<K, V>() ;
		}
		
		public ETreeDictionary( C5.TreeDictionary<K, V> subDictionary ) {
			m_subDictionary = subDictionary ;
		}
		
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
		
		public virtual K FirstKey {
			get {
				return m_subDictionary.FindMin().Key ;
			}
		}
		
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
		
		public V this[K key]
		{
			get { return m_subDictionary[key] ; }
			set { m_subDictionary[key] = value ; }
		}
		
		public ICollection<K> Keys {
			get { return new C5CollectionWrapper<K>( m_subDictionary.Keys ) ; }
		}
		
		public ICollection<V> Values {
			get { return new C5CollectionWrapper<V>( m_subDictionary.Values ) ; }
		}
		
		public int Count {
			get { return m_subDictionary.Count ; }
		}
		
		public bool IsReadOnly {
			get { return m_subDictionary.IsReadOnly ; }
		}
		
		public bool ContainsKey(K key)
		{
			return m_subDictionary.Contains( key ) ;
		}
		
		public void Add(K key, V value)
		{
			m_subDictionary.Add( key, value ) ;
		}
		
		public bool Remove(K key)
		{
			return m_subDictionary.Remove( key ) ;
		}
		
		public bool TryGetValue(K key, out V value)
		{
			return m_subDictionary.Find( key, out value ) ;
		}
		
		public void Add(KeyValuePair<K, V> item)
		{
			Add( item.Key, item.Value ) ;
		}
		
		public void Clear()
		{
			m_subDictionary.Clear() ;
		}
		
		public bool Contains(KeyValuePair<K, V> item)
		{
			return m_subDictionary.Contains( item.Key ) ;
		}
		
		public void CopyTo(KeyValuePair<K, V>[] array, int arrayIndex)
		{
			foreach( C5.KeyValuePair<K,V> item in m_subDictionary )
			{
				array.SetValue( new KeyValuePair<K,V>( item.Key, item.Value ), arrayIndex ) ;
				arrayIndex++ ;
			}
		}
		
		public bool Remove(KeyValuePair<K, V> item)
		{
			return m_subDictionary.Remove( item.Key ) ;
		}
		
		public IEnumerator<KeyValuePair<K, V>> GetEnumerator()
		{
            foreach (C5.KeyValuePair<K, V> keyValuePair in m_subDictionary)
            {
                yield return (new KeyValuePair<K, V>(keyValuePair.Key, keyValuePair.Value));
            }
		}

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
