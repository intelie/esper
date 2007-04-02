using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.schedule;

namespace net.esper.eql.db
{
    /// <summary>
    /// Implements an expiry-time cache that evicts data when data becomes stale
    /// after a given number of seconds.
    /// </summary>

    public class DataCacheExpiringImpl : DataCache, ScheduleCallback
    {
        /// <summary> Returns the maximum age in milliseconds.</summary>
        /// <returns> millisecon max age
        /// </returns>

        virtual public long MaxAgeMSec
        {
            get { return maxAgeMSec; }
        }

        /// <summary> Returns the purge interval in milliseconds.</summary>
        /// <returns> millisecond purge interval
        /// </returns>

        virtual public long PurgeIntervalMSec
        {
            get { return purgeIntervalMSec; }
        }

        /// <summary> Returns the current cache size.</summary>
        /// <returns> cache size
        /// </returns>

        virtual public long Size
        {
            get { return cache.Count; }
        }

        private readonly long maxAgeMSec;
        private readonly long purgeIntervalMSec;
        private readonly SchedulingService schedulingService;
        private readonly ScheduleSlot scheduleSlot;
        private readonly WeakDictionary<MultiKey<Object>, Item> cache;
        private bool isScheduled;

        /// <summary> Ctor.</summary>
        /// <param name="maxAgeSec">is the maximum age in seconds
        /// </param>
        /// <param name="purgeIntervalSec">is the purge interval in seconds
        /// </param>
        /// <param name="schedulingService">is a service for call backs at a scheduled time, for purging
        /// </param>
        /// <param name="scheduleSlot">slot for scheduling callbacks for this cache
        /// </param>

        public DataCacheExpiringImpl(double maxAgeSec, double purgeIntervalSec, SchedulingService schedulingService, ScheduleSlot scheduleSlot)
        {
            this.maxAgeMSec = (long)maxAgeSec * 1000;
            this.purgeIntervalMSec = (long)purgeIntervalSec * 1000;
            this.schedulingService = schedulingService;
            this.scheduleSlot = scheduleSlot;
            this.cache = new WeakDictionary<MultiKey<Object>, Item>();
        }

        /// <summary>
        /// Ask the cache if the keyed value is cached, returning a list or rows if the key is in the cache,
        /// or returning null to indicate no such key cached. Zero rows may also be cached.
        /// </summary>
        /// <param name="lookupKeys">is the keys to look up in the cache</param>
        /// <returns>
        /// a list of rows that can be empty is the key was found in the cache, or null if
        /// the key is not found in the cache
        /// </returns>
        public IList<EventBean> GetCached(Object[] lookupKeys)
        {
            MultiKey<Object> key = new MultiKey<Object>(lookupKeys);
            Item item = null;
            if ( ! cache.TryGetValue( key, out item ) )
            {
                return null;
            }

            long now = schedulingService.Time;
            if ((now - item.Time) > maxAgeMSec)
            {
                cache.Remove(key);
                return null;
            }

			return item.Data;
        }

        /// <summary>
        /// Puts into the cache a key and a list of rows, or an empty list if zero rows.
        /// <para>
        /// The put method is designed to be called when the cache does not contain a key as
        /// determined by the get method. Implementations typically simply overwrite
        /// any keys put into the cache that already existed in the cache.
        /// </para>
        /// </summary>
        /// <param name="lookupKeys">is the keys to the cache entry</param>
        /// <param name="rows">is a number of rows</param>
        public void PutCached(Object[] lookupKeys, IList<EventBean> rows)
        {
            MultiKey<Object> key = new MultiKey<Object>(lookupKeys);
            long now = schedulingService.Time;
            Item item = new Item(rows, now);
            cache[key] = item;

            if (!isScheduled)
            {
                schedulingService.Add(purgeIntervalMSec, this, scheduleSlot);
                isScheduled = true;
            }
        }

        /// <summary>
        /// Called when a scheduled callback occurs.
        /// </summary>
        public virtual void ScheduledTrigger()
        {
            // purge expired
            long now = schedulingService.Time;
            
            // Declare a list that is used to keep around
            // keys that must be removed.
            IList<MultiKey<Object>> deadKeyList = null ;
            // Iterate through the cache
            IEnumerator<MultiKey<Object>> itemKeyEnum = cache.KeysEnum ;
            while( itemKeyEnum.MoveNext() )
            {
            	MultiKey<Object> itemKey = itemKeyEnum.Current;
            	Item item = cache[itemKey] ;
                if ((now - item.Time) > maxAgeMSec)
                {
                	if ( deadKeyList == null )
                	{
                		deadKeyList = new List<MultiKey<Object>>() ;
                	}

                	deadKeyList.Add( itemKey ) ;
                }
            }
            
            if ( deadKeyList != null ) {
            	foreach( MultiKey<Object> itemKey in deadKeyList ) {
            		cache.Remove( itemKey ) ;
            	}
            	
            	cache.RemoveCollectedEntries() ;
            }

            isScheduled = false;
        }

        private class Item
        {
            private IList<EventBean> data;
            private long time;

            virtual public IList<EventBean> Data
            {
                get { return data; }
            }

            virtual public long Time
            {
                get { return time; }
            }

            public Item(IList<EventBean> data, long time)
            {
                this.data = data;
                this.time = time;
            }
        }
    }
}
