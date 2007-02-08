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

        public virtual void scheduledTrigger()
        {
            // purge expired
            long now = schedulingService.Time;
            
            // Declare a list that is used to keep around
            // keys that must be removed.
            IList<MultiKey<Object>> deadKeyList = null ;
            // Iterate through the cache
            foreach( MultiKey<Object> itemKey in cache.Keys )
            {
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
