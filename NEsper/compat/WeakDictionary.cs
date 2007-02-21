using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// A generic dictionary, which allows its keys
    /// to be garbage collected if there are no other references
    /// to them than from the dictionary itself.
    /// </summary>
    ///
    /// <remarks>
    /// If the key of a particular entry in the dictionary has been
    /// collected, then both the key and value become effectively
    /// unreachable. However, left-over WeakReference objects for the key
    /// will physically remain in the dictionary until RemoveCollectedEntries
    /// is called. This will lead to a discrepancy between the Count property
    /// and the number of iterations required to visit all of the elements of
    /// the dictionary using its enumerator or those of the Keys and Values
    /// collections. Similarly, CopyTo will copy fewer than Count elements
    /// in this situation.
    /// </remarks>
    
    public sealed class WeakDictionary<TKey, TValue> : IDictionary<TKey, TValue>
        where TKey : class
        where TValue : class
    {
    	private C5.HashDictionary<Object, TValue> dictionary;
        //private Dictionary<object, TValue> dictionary;
        private WeakKeyComparer<TKey> comparer;

        public WeakDictionary()
            : this(16, null) { }

        public WeakDictionary(int capacity)
            : this(capacity, null) { }

        public WeakDictionary(IEqualityComparer<TKey> comparer)
            : this(16, comparer) { }

        public WeakDictionary(int capacity, IEqualityComparer<TKey> comparer)
        {
            this.comparer = new WeakKeyComparer<TKey>(comparer);
            this.dictionary = new C5.HashDictionary<object, TValue>(capacity, 0.6, this.comparer);
        }

        // WARNING: The count returned here may include entries for which
        // either the key or value objects have already been garbage
        // collected. Call RemoveCollectedEntries to weed out collected
        // entries and update the count accordingly.
        
        public int Count
        {
            get { return this.dictionary.Count; }
        }

        public void Add(TKey key, TValue value)
        {
            if (key == null) throw new ArgumentNullException("key");
            WeakReference<TKey> weakKey = new WeakKeyReference<TKey>(key, this.comparer);
            this.dictionary.Add(weakKey, value);
        }

        public bool ContainsKey(TKey key)
        {
        	return this.dictionary.Contains( key ) ;
        }

        public bool Remove(TKey key)
        {
            return this.dictionary.Remove(key);
        }

        public bool TryGetValue(TKey key, out TValue value)
        {
        	Object tempKey = key ;
        	TValue rvalue = null ;
        	if ( this.dictionary.Find( ref tempKey, out rvalue ) )
        	{
        		WeakReference<TKey> weakKey = (WeakReference<TKey>) tempKey ;
        		if ( weakKey.IsAlive )
        		{
        			value = rvalue ;
        			return true ;
        		}
        		
        		this.dictionary.Remove( key ) ;            		
        	}
        	
        	value = default(TValue);
        	return false ;
        }

        private void SetValue(TKey key, TValue value)
        {
            WeakReference<TKey> weakKey = new WeakKeyReference<TKey>(key, this.comparer);
            this.dictionary[weakKey] = value;
        }

        public void Clear()
        {
            this.dictionary.Clear();
        }

        public IEnumerator<KeyValuePair<TKey, TValue>> GetEnumerator()
        {
            foreach (C5.KeyValuePair<object, TValue> kvp in this.dictionary)
            {
                WeakReference<TKey> weakKey = (WeakReference<TKey>)(kvp.Key);
                TKey key = weakKey.Target;
                TValue value = kvp.Value;
                if (weakKey.IsAlive)
                {
                    yield return new KeyValuePair<TKey, TValue>(key, value);
                }
            }
        }

        // Removes the left-over weak references for entries in the dictionary
        // whose key or value has already been reclaimed by the garbage
        // collector. This will reduce the dictionary's Count by the number
        // of dead key-value pairs that were eliminated.
        
        public void RemoveCollectedEntries()
        {
            List<object> toRemove = null;
            foreach (C5.KeyValuePair<object, TValue> pair in this.dictionary)
            {
                WeakReference<TKey> weakKey = (WeakReference<TKey>)(pair.Key);

                if (!weakKey.IsAlive)
                {
                	if (toRemove == null)
                	{
                        toRemove = new List<object>();
                	}
                    toRemove.Add(weakKey);
                }
            }

            if (toRemove != null)
            {
                foreach (object key in toRemove)
                {
                    this.dictionary.Remove(key);
                }
            }
        }

        public IEnumerator<TKey> KeysEnum {
        	get 
        	{
        		foreach( WeakReference<TKey> weakKey in this.dictionary.Keys )
        		{
        			if ( weakKey.IsAlive )
        			{
        				yield return weakKey.Target;
        			}
        		}
        	}
        }
        
        
        #region IDictionary<TKey,TValue> Members

        public ICollection<TKey> Keys
        {
            get
            {
        		List<TKey> keyList = new List<TKey>() ;
        		IEnumerator<TKey> keyEnum = this.KeysEnum ;
        		while( keyEnum.MoveNext() )
        		{
        			keyList.Add( keyEnum.Current ) ;
        		}
        		
        		return keyList ;
        	}
        }

        public ICollection<TValue> Values
        {
            get { throw new Exception("The method or operation is not implemented."); }
        }

        public TValue this[TKey key]
        {
            get
            {
            	Object tempKey = key ;
            	TValue rvalue = null ;
            	if ( this.dictionary.Find( ref tempKey, out rvalue ) )
            	{
            		WeakReference<TKey> weakKey = (WeakReference<TKey>) tempKey ;
            		if ( weakKey.IsAlive )
            		{
            			return rvalue ;
            		}
            		
            		this.dictionary.Remove( key ) ;            		
            	}
            	
            	throw new KeyNotFoundException( "Key '" + key + "' not found" ) ;
            }
            set
            {
                if (key == null) throw new ArgumentNullException("key");
                WeakReference<TKey> weakKey = new WeakKeyReference<TKey>(key, this.comparer);
                this.dictionary[weakKey] = value;
            }
        }

        #endregion

        #region ICollection<KeyValuePair<TKey,TValue>> Members

        /// <summary>
        /// Adds an item to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.
        /// </summary>
        /// <param name="item">The object to add to the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <exception cref="T:System.NotSupportedException">The <see cref="T:System.Collections.Generic.ICollection`1"></see> is read-only.</exception>
        
        public void Add(KeyValuePair<TKey, TValue> item)
        {
            Add(item.Key, item.Value);
        }

        /// <summary>
        /// Determines whether the <see cref="T:System.Collections.Generic.ICollection`1"></see> contains a specific value.
        /// </summary>
        /// <param name="item">The object to locate in the <see cref="T:System.Collections.Generic.ICollection`1"></see>.</param>
        /// <returns>
        /// true if item is found in the <see cref="T:System.Collections.Generic.ICollection`1"></see>; otherwise, false.
        /// </returns>

        public bool Contains(KeyValuePair<TKey, TValue> item)
        {
            Object tempKey = item.Key ;
            
        	TValue value ;
        	if ( this.dictionary.Find( ref tempKey, out value ) ) {
        		WeakReference<TKey> weakKey = (WeakReference<TKey>) tempKey ;
            	if ( weakKey.IsAlive )
            	{
            		return Object.Equals( value, item.Value ) ;
            	}
            	
            	this.dictionary.Remove( item.Key ) ;
        	}
            
            return false;
        }

        /// <summary>
        /// Copies the elements of the <see cref="T:System.Collections.Generic.ICollection`1"></see> to an <see cref="T:System.Array"></see>, Starting at a particular <see cref="T:System.Array"></see> index.
        /// </summary>
        /// <param name="array">The one-dimensional <see cref="T:System.Array"></see> that is the destination of the elements copied from <see cref="T:System.Collections.Generic.ICollection`1"></see>. The <see cref="T:System.Array"></see> must have zero-based indexing.</param>
        /// <param name="arrayIndex">The zero-based index in array at which copying begins.</param>
        /// <exception cref="T:System.ArgumentOutOfRangeException">arrayIndex is less than 0.</exception>
        /// <exception cref="T:System.ArgumentNullException">array is null.</exception>
        /// <exception cref="T:System.ArgumentException">array is multidimensional.-or-arrayIndex is equal to or greater than the length of array.-or-The number of elements in the source <see cref="T:System.Collections.Generic.ICollection`1"></see> is greater than the available space from arrayIndex to the end of the destination array.-or-Type T cannot be cast automatically to the type of the destination array.</exception>
        
        public void CopyTo(KeyValuePair<TKey, TValue>[] array, int arrayIndex)
        {
            throw new Exception("The method or operation is not implemented.");
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
        
        public bool Remove(KeyValuePair<TKey, TValue> item)
        {
            return Remove(item.Key);
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
            return this.GetEnumerator();
        }

        #endregion
    } 
}
