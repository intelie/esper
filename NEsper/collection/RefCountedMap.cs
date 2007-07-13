using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.collection
{
    /// <summary>
    /// Reference-counting map based on a HashMap implementation that stores as a value a pair of value and reference counter.
    /// The class provides a reference method that takes a key
    /// and increments the reference count for the key. It also provides a de-reference method that takes a key and
    /// decrements the reference count for the key, and removes the key if the reference count reached zero.
    /// Null values are not allowed as keys.
    /// </summary>

    public class RefCountedMap<K, V>
    {
        private IDictionary<K, Pair<V, Int32>> refMap;

        /// <summary>
        /// Constructor.
        /// </summary>

        public RefCountedMap()
        {
            refMap = new Dictionary<K, Pair<V, Int32>>();
        }


        /// <summary>
        /// Gets or sets the <see cref="V"/> with the specified key.
        /// </summary>
        /// <value></value>
        public virtual V this[K key]
        {
            get
            {
                Pair<V, Int32> refValue = null;
                if (!refMap.TryGetValue(key, out refValue))
                {
                    return default(V);
                }
                return refValue.First;
            }

            set
            {
                if (key == null)
                {
                    throw new ArgumentException("Collection does not allow null key values");
                }
                if (refMap.ContainsKey(key))
                {
                    throw new IllegalStateException("Key value already in collection");
                }

                Pair<V, Int32> refValue = new Pair<V, Int32>(value, 1);
                refMap[key] = refValue;

                //return val;
            }
        }

        /// <summary> Increase the reference count for a given key by one.
        /// Throws an ArgumentException if the key was not found.
        /// </summary>
        /// <param name="key">is the key to increase the ref count for
        /// </param>

        public virtual void Reference(K key)
        {
            Pair<V, Int32> refValue ;
            if (!refMap.TryGetValue(key, out refValue)) 
            {
                throw new IllegalStateException("Key value not found in collection");
            }
            refValue.Second = refValue.Second + 1;
        }

        /// <summary> Decreases the reference count for a given key by one. Returns true if the reference count reaches zero.
        /// Removes the key from the collection when the reference count reaches zero.
        /// Throw an ArgumentException if the key is not found.
        /// </summary>
        /// <param name="key">to de-reference
        /// </param>
        /// <returns> true to indicate the reference count reached zero, false to indicate more references to the key exist.
        /// </returns>

        public virtual bool Dereference(K key)
        {
            Pair<V, Int32> refValue ;
            if (!refMap.TryGetValue(key, out refValue))
            {
                throw new IllegalStateException("Key value not found in collection");
            }

            int refCounter = refValue.Second;
            if (refCounter < 1)
            {
                throw new IllegalStateException("Unexpected reference counter value " + refValue.Second + " encountered for key " + key);
            }

            // Remove key on dereference of last reference
            if (refCounter == 1)
            {
                refMap.Remove(key);
                return true;
            }

            refValue.Second = refCounter - 1;
            return false;
        }
    }
}