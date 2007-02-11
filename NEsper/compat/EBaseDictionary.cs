using System;
using System.Collections.Generic;

namespace net.esper.compat
{
	public class EBaseDictionary<K,V> : EDictionary<K,V>
	{
		private IDictionary<K,V> m_subDictionary;
		
		/// <summary>
		/// Gets or sets the subdictionary.
		/// </summary>
		
		protected IDictionary<K,V> SubDictionary
		{
			get { return m_subDictionary ; }
			set { m_subDictionary = value ; }
		}
		
		/// <summary>
		/// Allows subclasses to bind the subdictionary later in their
		/// initialization process.
		/// </summary>
		
		protected EBaseDictionary()
		{
		}
		
		/// <summary>
		/// Constructs a new dictionary
		/// </summary>
		/// <param name="subDictionary"></param>
		
		public EBaseDictionary( IDictionary<K,V> subDictionary )
		{
			m_subDictionary = subDictionary ;
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
            V returnValue = defaultValue;
            if (key != null)
            {
                if (!TryGetValue(key, out returnValue))
                {
                    returnValue = defaultValue;
                }
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
		/// Returns the first value in the enumeration of values
		/// </summary>
		/// <returns></returns>
		
		public virtual V FirstValue
		{
			get
			{
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
			get { return m_subDictionary.Keys; }
		}
		
		public ICollection<V> Values {
			get { return m_subDictionary.Values; }
		}
		
		public int Count {
			get { return m_subDictionary.Count ; }
		}
		
		public bool IsReadOnly {
			get { return m_subDictionary.IsReadOnly ; }
		}
		
		public bool ContainsKey(K key)
		{
			return m_subDictionary.ContainsKey( key ) ;
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
			return m_subDictionary.TryGetValue( key, out value ) ;
		}
		
		public void Add(KeyValuePair<K, V> item)
		{
			m_subDictionary.Add( item );
		}
		
		public void Clear()
		{
			m_subDictionary.Clear() ;
		}
		
		public bool Contains(KeyValuePair<K, V> item)
		{
			return m_subDictionary.Contains( item ) ;
		}
		
		public void CopyTo(KeyValuePair<K, V>[] array, int arrayIndex)
		{
			m_subDictionary.CopyTo( array, arrayIndex ) ;
		}
		
		public bool Remove(KeyValuePair<K, V> item)
		{
			return m_subDictionary.Remove( item ) ;
		}
		
		public IEnumerator<KeyValuePair<K, V>> GetEnumerator()
		{
			return m_subDictionary.GetEnumerator() ;
		}
		
		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			return m_subDictionary.GetEnumerator() ;
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
            if ((obj == null) || (obj.GetType() != GetType()))
            {
                return false;
            }

            EBaseDictionary<K,V> oDictionary = (EBaseDictionary<K,V>) obj ;
            bool result = CollectionHelper.AreEqual( m_subDictionary, oDictionary.m_subDictionary ) ;
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
