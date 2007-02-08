using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;

namespace net.esper.eql.db
{
	/// <summary> Query result data cache implementation that uses a least-recently-used algorithm
	/// to store and evict query results.
	/// </summary>

    public class DataCacheLRUImpl : DataCache
    {
        private readonly int cacheSize;
        private readonly float hashTableLoadFactor = 0.75f;
        private readonly LinkedDictionary<MultiKey<Object>, IList<EventBean>> cache;

        /// <summary> Ctor.</summary>
        /// <param name="cacheSize">is the maximum cache size
        /// </param>

        public DataCacheLRUImpl(int cacheSize)
        {
            this.cacheSize = cacheSize;
            int hashTableCapacity = (int)Math.Ceiling(cacheSize / hashTableLoadFactor) + 1;
            this.cache = new LinkedDictionary<MultiKey<Object>, IList<EventBean>>( hashTableCapacity ) ;

            // TODO
            //{
            //private static final long serialVersionUID = 1;
            //
            //@Override protected boolean removeEldestEntry (Map.Entry<MultiKey<Object>,List<EventBean>> eldest)
            //{
            //    return size() > DataCacheLRUImpl.this.cacheSize;
            //}
        }

        /// <summary> Retrieves an entry from the cache.
        /// The retrieved entry becomes the MRU (most recently used) entry.
        /// </summary>
        /// <param name="lookupKeys">the key whose associated value is to be returned.
        /// </param>
        /// <returns> the value associated to this key, or null if no value with this key exists in the cache.
        /// </returns>
        public IList<EventBean> GetCached(Object[] lookupKeys)
        {
			IList<EventBean> rvalue = null ;
            MultiKey<Object> keys = new MultiKey<Object>(lookupKeys);
			cache.TryGetValue( keys, out rvalue );
			return rvalue;
        }

        /// <summary> Adds an entry to this cache.
        /// If the cache is full, the LRU (least recently used) entry is dropped.
        /// </summary>
        /// <param name="key">the key with which the specified value is to be associated.
        /// </param>
        /// <param name="value">a value to be associated with the specified key.
        /// </param>

        public void PutCached(Object[] key, IList<EventBean> value)
        {
            lock (this)
            {
                MultiKey<Object> mkeys = new MultiKey<Object>(key);
                cache[mkeys] = value;
            }
        }

        /// <summary> Returns the maximum cache size.</summary>
        /// <returns> maximum cache size
        /// </returns>
        public int CacheSize
        {
            get { return cacheSize; }
        }
    }
}
