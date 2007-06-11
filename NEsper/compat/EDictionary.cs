using System;
using System.Collections.Generic;

namespace net.esper.compat
{
    /// <summary>
    /// Extended dictionary functionality.
    /// </summary>
    /// <typeparam name="K"></typeparam>
    /// <typeparam name="V"></typeparam>

	public interface EDictionary<K,V> : IDictionary<K,V>
	{
		/// <summary>
		/// Fetches the value associated with the specified key.
		/// If no value can be found, then the defaultValue is
		/// returned.
		/// </summary>
		/// <param name="key"></param>
		/// <param name="defaultValue"></param>
		/// <returns></returns>
		
		V Fetch( K key, V defaultValue ) ;
		
		/// <summary>
		/// Fetches the value associated with the specified key.
		/// If no value can be found, then default(V) is returned.
		/// </summary>
		/// <param name="key"></param>
		/// <returns></returns>
		
		V Fetch( K key ) ;

		/// <summary>
		/// Sets the given key in the dictionary.  If the key
		/// already exists, then it is remapped to thenew value.
		/// </summary>

		void Put( K key, V value ) ;

		/// <summary>
		/// Puts all values from the source dictionary into
		/// this dictionary.
		/// </summary>
		/// <param name="source"></param>
		
		void PutAll( IDictionary<K,V> source ) ;
		
		/// <summary>
		/// Returns the first value in the enumeration of values
		/// </summary>
		/// <returns></returns>
		
		V FirstValue { get ; }

        /// <summary>
        /// Removes the item from the dictionary that is associated with
        /// the specified key.
        /// </summary>
        /// <param name="key">Search key into the dictionary</param>
        /// <param name="value">The value removed from the dictionary (if found).</param>
        /// <returns></returns>

        bool Remove(K key, out V value);
	}
}
