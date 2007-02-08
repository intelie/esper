using System;
using System.Collections.Generic;

namespace net.esper.collection
{
	/// <summary>
    /// Sorted, reference-counting set based on a SortedDictionary implementation that stores keys and a reference counter for
	/// each unique key value. Each time the same key is added, the reference counter increases.
	/// Each time a key is removed, the reference counter decreases.
	/// </summary>

    public class SortedRefCountedSet<K>
	{
    	private C5.TreeDictionary<K, int> refSet;
		
		/// <summary>
		///  Constructor.
		/// </summary>
		
		public SortedRefCountedSet()
		{
			refSet = new C5.TreeDictionary<K, int>() ;
		}
		
		/// <summary> Add a key to the set. Add with a reference count of one if the key didn't exist in the set.
		/// Increase the reference count by one if the key already exists.
		/// </summary>
		/// <param name="key">to add
		/// </param>
		
		public virtual void Add(K key)
		{
            int value = 1;
            if (refSet.FindOrAdd(key, ref value))
            {
                refSet.Update(key, ++value);
            }
		}
		
		/// <summary> Remove a key from the set. Removes the key if the reference count is one.
		/// Decreases the reference count by one if the reference count is more then one.
		/// </summary>
		/// <param name="key">to add
		/// </param>
		/// <throws>  IllegalStateException is a key is removed that wasn't added to the map </throws>
		
        public virtual void Remove(K key)
		{
            int value;
            
            if (!refSet.Find(ref key, out value))
            {
                throw new SystemException("Attempting to remove key from map that wasn't added");
            }
			
			if (value == 1)
			{
				refSet.Remove(key);
				return ;
			}
			
			value--;
            refSet[key] = value;
			return ;
		}
		
		/// <summary> Returns the largest key value, or null if the collection is empty.</summary>
		/// <returns> largest key value, null if none
		/// </returns>
		
        public virtual K MaxValue
		{
        	get
        	{
        		return
        			( refSet.Count != 0 ) ?
        			( refSet.FindMax().Key ) :
        			( default(K) ) ;
        	}
		}
		
		/// <summary> Returns the smallest key value, or null if the collection is empty.</summary>
		/// <returns> smallest key value, default(K) if none
		/// </returns>
		
        public virtual K MinValue
		{
        	get
        	{
        		return
        			( refSet.Count != 0 ) ?
        			( refSet.FindMin().Key ) :
        			( default(K) ) ;
        	}
		}
	}
}
