using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.collection
{
    /// <summary> Collection to hold indexed data. Each key maps to multiple values.
    /// Objects can be added to keys. The key class should override the equals and hashCode methods.
    /// Same value objects can be added twice to the collection - the collection does not enforce set behavior.
    /// </summary>

    public sealed class IndexedDataCollection
    {
        private readonly EDictionary<Object, List<Object>> eventIndex = new HashDictionary<Object, List<Object>>();

        /// <summary> Add a value object to the index. If the value object already exists for the same key,
        /// no error is thrown and the object is still added (no set behavior).
        /// </summary>
        /// <param name="key">is the key value
        /// </param>
        /// <param name="bean">is the value object to add
        /// </param>

        public void Add(Object key, Object bean)
        {
        	List<Object> listOfBeans = eventIndex.Fetch( key ) ;
        	if ( listOfBeans != null )
            {
                listOfBeans.Add(bean);
                return;
            }

            listOfBeans = new List<Object>();
            listOfBeans.Add(bean);
            eventIndex[key] = listOfBeans;
        }

        /// <summary> Removes a value object from the index, returning a bool value to indicate if the value object was found.</summary>
        /// <param name="key">is the key value
        /// </param>
        /// <param name="bean">is the value object to remove
        /// </param>
        /// <returns> true if the value object was successfully removed, false if the key or value object could not be found
        /// </returns>
        public bool Remove(Object key, Object bean)
        {
            List<Object> listOfBeans = eventIndex.Fetch( key ) ;
            if ( listOfBeans == null )
            {
                return false;
            }

            bool result = listOfBeans.Remove(bean);
            if (listOfBeans.Count == 0)
            {
                eventIndex.Remove(key);
            }
            return result;
        }

        /// <summary> Returns a list of value objects for the given key, or null if there are no value objects for this key.</summary>
        /// <param name="key">is the index key value
        /// </param>
        /// <returns> null if key has no associated value objects, or the list of value objects for the key
        /// </returns>

        public List<Object> this[Object key]
        {
            get
            {
                List<Object> listOfBeans = eventIndex.Fetch(key);
                return listOfBeans;
            }
        }
    }
}
