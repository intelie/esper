using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.collection
{
	/// <summary> reference-counting set based on a HashMap implementation that stores keys and a reference counter for
	/// each unique key value. Each time the same key is added, the reference counter increases.
	/// Each time a key is removed, the reference counter decreases.
	/// </summary>

	public class RefCountedSet<K>
	{
        private bool hasNullEntry;
        private int nullEntry;
		private IDictionary<K, int> refSet;
		private int numValues;

		/// <summary>
		/// Constructor.
		/// </summary>

		public RefCountedSet()
		{
			refSet = new Dictionary<K, Int32>();
		}

        /// <summary>
        /// Adds a key to the set, but the key is null.  It behaves the same, but has its own
        /// variables that need to be incremented.
        /// </summary>

        private bool AddNull()
        {
            if (!hasNullEntry)
            {
                hasNullEntry = true;
                numValues++;
                nullEntry = 0;
                return true;
            }

            numValues++;
            nullEntry++;

            return false;
        }

		/// <summary> Add a key to the set. Add with a reference count of one if the key didn't exist in the set.
		/// Increase the reference count by one if the key already exists.
		/// Return true if this is the first time the key was encountered, or false if key is already in set.
		/// </summary>
		/// <param name="key">to add
		/// </param>
		/// <returns> true if the key is not in the set already, false if the key is already in the set
		/// </returns>
		public virtual bool Add( K key )
		{
            if (key == null)
            {
                return AddNull();
            }

            int value;
			if ( !refSet.TryGetValue( key, out value ) )
			{
				refSet[key] = 1 ;
				numValues++;
				return true;
			}

			value++;
			numValues++;
			refSet[key] = value;
			return false;
		}

        /// <summary>
        /// Removes the null key
        /// </summary>

        private bool RemoveNull()
        {
            if (nullEntry == 1)
            {
                hasNullEntry = false;
                nullEntry--;
                return true;
            }

            nullEntry--;
            numValues--;

            return false;
        }

		/// <summary> Removed a key to the set. Removes the key if the reference count is one.
		/// Decreases the reference count by one if the reference count is more then one.
		/// Return true if the reference count was one and the key thus removed, or false if key is stays in set.
		/// </summary>
		/// <param name="key">to add
		/// </param>
		/// <returns> true if the key is removed, false if it stays in the set
		/// </returns>
		/// <throws>  IllegalStateException is a key is removed that wasn't added to the map </throws>

		public virtual bool Remove( K key )
		{
            if (key == null)
            {
                return RemoveNull();
            }

			int value;
			if ( !refSet.TryGetValue( key, out value ) )
			{
				throw new IllegalStateException( "Attempting to remove key from map that wasn't added" );
			}

			if ( value == 1 )
			{
				refSet.Remove( key );
				numValues--;
				return true;
			}

			value--;
			refSet[key] = value;
			numValues--;
			return false;
		}

		/// <summary> Returns an iterator over the entry set.</summary>
		/// <returns> entry set iterator
		/// </returns>

		public IEnumerator<KeyValuePair<K, int>> GetEnumerator()
		{
            if (hasNullEntry)
            {
                yield return new KeyValuePair<K, int>(default(K), nullEntry);
            }
            
            foreach (KeyValuePair<K, int> value in refSet)
            {
                yield return value;
            }
		}

		/// <summary> Returns the number of values in the collection.</summary>
		/// <returns> size
		/// </returns>

		public virtual int Count
		{
            get { return numValues; }
		}
	}
}
