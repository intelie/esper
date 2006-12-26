package net.esper.eql.db;

import net.esper.event.EventBean;
import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.ScheduleCallback;
import net.esper.collection.MultiKey;

import java.util.List;
import java.util.WeakHashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Implements an expiry-time cache that evicts data when data becomes stale
 * after a given number of seconds.
 */
public class DataCacheExpiringImpl implements DataCache, ScheduleCallback
{
    private final long maxAgeMSec;
    private final long purgeIntervalMSec;
    private final SchedulingService schedulingService;
    private final ScheduleSlot scheduleSlot;
    private final WeakHashMap<MultiKey<Object>, Item> cache;

    private boolean isScheduled;

    /**
     * Ctor.
     * @param maxAgeSec is the maximum age in seconds
     * @param purgeIntervalSec is the purge interval in seconds
     * @param schedulingService is a service for call backs at a scheduled time, for purging
     * @param scheduleSlot slot for scheduling callbacks for this cache
     */
    public DataCacheExpiringImpl(double maxAgeSec, double purgeIntervalSec, SchedulingService schedulingService,
                                 ScheduleSlot scheduleSlot)
    {
        this.maxAgeMSec = (long) maxAgeSec * 1000;
        this.purgeIntervalMSec = (long) purgeIntervalSec * 1000;
        this.schedulingService = schedulingService;
        this.scheduleSlot = scheduleSlot;
        this.cache = new WeakHashMap<MultiKey<Object>, Item>();
    }

    public List<EventBean> getCached(Object[] lookupKeys)
    {
        MultiKey key = new MultiKey<Object>(lookupKeys);
        Item item = cache.get(key);
        if (item == null)
        {
            return null;
        }

        long now = schedulingService.getTime();
        if ((now - item.getTime()) > maxAgeMSec)
        {
            cache.remove(key);
            return null;
        }

        return item.getData();
    }

    public void put(Object[] lookupKeys, List<EventBean> rows)
    {
        MultiKey key = new MultiKey<Object>(lookupKeys);
        long now = schedulingService.getTime();
        Item item = new Item(rows, now);
        cache.put(key, item);

        if (!isScheduled)
        {
            schedulingService.add(purgeIntervalMSec, this, scheduleSlot);
            isScheduled = true;
        }
    }

    /**
     * Returns the maximum age in milliseconds.
     * @return millisecon max age
     */
    protected long getMaxAgeMSec()
    {
        return maxAgeMSec;
    }

    /**
     * Returns the purge interval in milliseconds.
     * @return millisecond purge interval
     */
    protected long getPurgeIntervalMSec()
    {
        return purgeIntervalMSec;
    }

    /**
     * Returns the current cache size.
     * @return cache size
     */
    protected long getSize()
    {
        return cache.size();
    }

    public void scheduledTrigger()
    {
        // purge expired
        long now = schedulingService.getTime();
        Iterator<MultiKey<Object>> it = cache.keySet().iterator();
        for (;it.hasNext();)
        {
            Item item = cache.get(it.next());
            if ((now - item.getTime()) > maxAgeMSec)
            {
                it.remove();
            }
        }

        isScheduled = false;
    }

    private static class Item
    {
        private List<EventBean> data;
        private long time;

        public Item(List<EventBean> data, long time)
        {
            this.data = data;
            this.time = time;
        }

        public List<EventBean> getData()
        {
            return data;
        }

        public long getTime()
        {
            return time;
        }
    }
}
