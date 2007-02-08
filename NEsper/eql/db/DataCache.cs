using System;
using System.Collections.Generic;

using EventBean = net.esper.events.EventBean;

namespace net.esper.eql.db
{
    /// <summary>
    /// Implementations serve as caches for historical or reference data retrieved
    /// via lookup keys consisting or one or more rows represented by a list of events.
    /// </summary>
    
    public interface DataCache
    {
        /// <summary> Ask the cache if the keyed value is cached, returning a list or rows if the key is in the cache,
        /// or returning null to indicate no such key cached. Zero rows may also be cached.
        /// </summary>
        /// <param name="lookupKeys">is the keys to look up in the cache
        /// </param>
        /// <returns> a list of rows that can be empty is the key was found in the cache, or null if
        /// the key is not found in the cache
        /// </returns>
        
        IList<EventBean> GetCached(Object[] lookupKeys);

        /// <summary> Puts into the cache a key and a list of rows, or an empty list if zero rows.
        /// <p>
        /// The put method is designed to be called when the cache does not contain a key as
        /// determined by the get method. Implementations typically simply overwrite
        /// any keys put into the cache that already existed in the cache.
        /// </summary>
        /// <param name="lookupKeys">is the keys to the cache entry
        /// </param>
        /// <param name="rows">is a number of rows
        /// </param>

        void PutCached(Object[] lookupKeys, IList<EventBean> rows);
    }
}
