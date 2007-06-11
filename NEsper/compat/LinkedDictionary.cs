using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.compat
{
    /// <summary>
    /// Hashtable and linked list implementation designed to mimic Java's LinkedHashMap
    /// functionality.
    /// </summary>
    /// <typeparam name="K"></typeparam>
    /// <typeparam name="V"></typeparam>

    public class LinkedDictionary<K,V> : EDictionary<K,V>
    {
        /// <summary>
        /// Delegate for handling events on dictionary entries.
        /// </summary>
        /// <param name="entry"></param>
        /// <returns></returns>

        public delegate bool EntryEventHandler(KeyValuePair<K, V> entry);
        
        /// <summary>
        /// A list of all key-value pairs added to the table.  The list
        /// preserves insertion order and is used to preserve enumeration
        /// ordering.
        /// </summary>

        private LinkedList<Pair<K, V>> m_hashList;

        /// <summary>
        /// Contains a reference to the key and is used for all lookups.  Refers
        /// to the node in the linked list node.  Provides for fast removal of
        /// the node upon removal.
        /// </summary>

        private Dictionary<K, LinkedListNode<Pair<K, V>>> m_hashTable;

        /// <summary>
        /// Shuffles items on access
        /// </summary>

        private bool m_shuffleOnAccess;

        /// <summary>
        /// Returns a value indicating if items should be shuffled (pushed to the
        /// head of the list) on access requests.
        /// </summary>

        public bool ShuffleOnAccess
        {
            get { return m_shuffleOnAccess; }
            set { m_shuffleOnAccess = value; }
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="LinkedDictionary&lt;K, V&gt;"/> class.
        /// </summary>
        
        public LinkedDictionary()
        {
            this.m_shuffleOnAccess = false;
            this.m_hashList = new LinkedList<Pair<K,V>>() ;
            this.m_hashTable = new Dictionary<K, LinkedListNode<Pair<K, V>>>() ;
        }
        
        /// <summary>
        /// Initializes a new instance of the <see cref="LinkedDictionary&lt;K, V&gt;"/> class.
        /// </summary>
        /// <param name="hashCapacity"></param>
        
        public LinkedDictionary( int hashCapacity )
        {
            this.m_hashList = new LinkedList<Pair<K,V>>() ;
            this.m_hashTable = new Dictionary<K, LinkedListNode<Pair<K, V>>>( hashCapacity ) ;
        }

        #region EDictionary<K,V> Members
        
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
			if ( ! TryGetValue( key, out returnValue ) ) {
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
            if (!TryGetValue(key, out value))
            {
                return false;
            }

            return Remove(key);
        }

		#endregion

        /// <summary>
        /// Occurs when a potentially destructive operations occurs on the dictionary
        /// and the dictionary is allowed to rebalance.
        /// </summary>

        public event EntryEventHandler RemoveEldest;

        #region IDictionary<K,V> Members

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
            if (m_hashTable.ContainsKey(key))
            {
                throw new ArgumentException("An element with the same key already exists");
            }

            Pair<K, V> keyValuePair = new Pair<K, V>(key, value);
            LinkedListNode<Pair<K, V>> linkedListNode = m_hashList.AddLast(keyValuePair);
            m_hashTable.Add(key, linkedListNode);

            CheckEldest();
        }

        /// <summary>
        /// Checks the eldest entry and see if we should remove it.
        /// </summary>
        
        private void CheckEldest()
        {
            if (RemoveEldest != null)
            {
                LinkedListNode<Pair<K, V>> linkedListNode = m_hashList.First;
                KeyValuePair<K, V> eldest = new KeyValuePair<K, V>(
                    linkedListNode.Value.First,
                    linkedListNode.Value.Second);
                if (RemoveEldest(eldest))
                {
                    m_hashList.Remove(linkedListNode);
                    m_hashTable.Remove(linkedListNode.Value.First);
                }
            }
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
            return m_hashTable.ContainsKey(key);
        }

        /// <summary>
        /// Gets an <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the keys of the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>An <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the keys of the object that implements <see cref="T:System.Collections.Generic.IDictionary`2"></see>.</returns>

        public ICollection<K> Keys
        {
            get
            {
        		IList<K> keysList = new List<K>() ;
        		foreach( Pair<K,V> keyValuePair in m_hashList )
        		{
        			keysList.Add( keyValuePair.First ) ;
        		}

        		return keysList ;
        	}
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
            LinkedListNode<Pair<K, V>> linkedListNode = null;
            if (m_hashTable.TryGetValue(key, out linkedListNode))
            {
                m_hashTable.Remove(key);
                m_hashList.Remove(linkedListNode);
                return true;
            }

            return false;
        }

        /// <summary>
        /// Tries the get value.
        /// </summary>
        /// <param name="key">The key.</param>
        /// <param name="value">The value.</param>
        /// <returns></returns>
        
        public bool TryGetValue(K key, out V value)
        {
            LinkedListNode<Pair<K, V>> linkedListNode = null;
            if (m_hashTable.TryGetValue(key, out linkedListNode))
            {
                value = linkedListNode.Value.Second ;
                if (ShuffleOnAccess)
                {
                    m_hashList.Remove(linkedListNode);
                    m_hashList.AddLast(linkedListNode);
                }
                return true;
            }

            value = default(V) ;
            
            return false;
        }

        /// <summary>
        /// Gets an <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the values in the <see cref="T:System.Collections.Generic.IDictionary`2"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>An <see cref="T:System.Collections.Generic.ICollection`1"></see> containing the values in the object that implements <see cref="T:System.Collections.Generic.IDictionary`2"></see>.</returns>
        
        public ICollection<V> Values
        {
            get
            {
        		IList<V> valuesList = new List<V>() ;
        		foreach( Pair<K,V> keyValuePair in m_hashList )
        		{
        			valuesList.Add( keyValuePair.Second ) ;
        		}

        		return valuesList ;
            }
        }

        /// <summary>
        /// Gets or sets the value the specified key.
        /// </summary>
        /// <value></value>

        public V this[K key]
        {
            get
            {
                LinkedListNode<Pair<K, V>> linkedListNode = null;
                linkedListNode = m_hashTable[key];
                if (ShuffleOnAccess)
                {
                    m_hashList.Remove(linkedListNode);
                    m_hashList.AddLast(linkedListNode);
                }

                return linkedListNode.Value.Second;
            }
            set
            {
                LinkedListNode<Pair<K, V>> linkedListNode = null;
                if (m_hashTable.TryGetValue(key, out linkedListNode))
                {
                	linkedListNode.Value.Second = value;
                }
                else
                {
                    Pair<K, V> keyValuePair = new Pair<K, V>(key, value);
                    linkedListNode = m_hashList.AddLast(keyValuePair);
                    m_hashTable.Add(key, linkedListNode);
                }

                CheckEldest();
            }
        }

        #endregion

        #region ICollection<KeyValuePair<K,V>> Members

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
            m_hashTable.Clear();
            m_hashList.Clear();
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
        	return m_hashTable.ContainsKey( item.Key ) ;
        }

        /// <summary>
        /// Copies the elements of the <see cref="T:System.Collections.Generic.ICollection`1"></see> to an <see cref="T:Array"></see>, starting at a particular <see cref="T:Array"></see> index.
        /// </summary>
        /// <param name="array">The one-dimensional <see cref="T:Array"></see> that is the destination of the elements copied from <see cref="T:System.Collections.Generic.ICollection`1"></see>. The <see cref="T:Array"></see> must have zero-based indexing.</param>
        /// <param name="arrayIndex">The zero-based index in array at which copying begins.</param>
        /// <exception cref="T:System.ArgumentOutOfRangeException">arrayIndex is less than 0.</exception>
        /// <exception cref="T:System.ArgumentNullException">array is null.</exception>
        /// <exception cref="T:System.ArgumentException">array is multidimensional.-or-arrayIndex is equal to or greater than the length of array.-or-The number of elements in the source <see cref="T:System.Collections.Generic.ICollection`1"></see> is greater than the available space from arrayIndex to the end of the destination array.-or-Type T cannot be cast automatically to the type of the destination array.</exception>

        public void CopyTo(KeyValuePair<K, V>[] array, int arrayIndex)
        {
        	if ( array == null ) {
        		throw new ArgumentNullException() ;
        	}
        	
        	if ( arrayIndex < 0 ) {
                throw new ArgumentOutOfRangeException();
        	}
        	
        	int ii = arrayIndex ;
        	
        	foreach( Pair<K,V> keyValuePair in m_hashList )
        	{
        		array[ii] = new KeyValuePair<K, V>( keyValuePair.First, keyValuePair.Second ) ;
        	}
        }

        /// <summary>
        /// Gets the number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <value></value>
        /// <returns>The number of elements contained in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</returns>

        public int Count
        {
            get { return m_hashTable.Count; }
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
        
        public bool Remove( KeyValuePair<K, V> item )
        {
            return Remove(item.Key);
        }

        #endregion

        #region IEnumerable<KeyValuePair<K,V>> Members

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        
        public IEnumerator<KeyValuePair<K, V>> GetEnumerator()
        {
            foreach (Pair<K, V> subPair in m_hashList)
            {
                yield return new KeyValuePair<K, V>(subPair.First, subPair.Second);
            }
        }

        #endregion

        #region IEnumerable Members

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            foreach (Pair<K, V> subPair in m_hashList)
            {
                yield return new KeyValuePair<K, V>(subPair.First, subPair.Second);
            }
        }
        
        #endregion
    }
}
