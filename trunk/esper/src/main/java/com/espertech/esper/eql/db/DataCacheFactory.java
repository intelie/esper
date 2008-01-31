package com.espertech.esper.eql.db;

import com.espertech.esper.client.ConfigurationDBRef;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.schedule.ScheduleBucket;
import com.espertech.esper.schedule.SchedulingService;

/**
 * Factory for data caches for use caching database query results and method invocation results.
 */
public class DataCacheFactory
{
    /**
     * Creates a cache implementation for the strategy as defined by the cache descriptor.
     * @param cacheDesc cache descriptor
     * @param epStatementHandle statement handle for timer invocations
     * @param schedulingService scheduling service for time-based caches
     * @param scheduleBucket for ordered timer invokation
     * @return data cache implementation
     */
    public static DataCache getDataCache(ConfigurationDBRef.DataCacheDesc cacheDesc,
                                         EPStatementHandle epStatementHandle,
                                         SchedulingService schedulingService,
                                         ScheduleBucket scheduleBucket)
    {
        if (cacheDesc == null)
        {
            return new DataCacheNullImpl();
        }

        if (cacheDesc instanceof ConfigurationDBRef.LRUCacheDesc)
        {
            ConfigurationDBRef.LRUCacheDesc lruCache = (ConfigurationDBRef.LRUCacheDesc) cacheDesc;
            return new DataCacheLRUImpl(lruCache.getSize());
        }

        if (cacheDesc instanceof ConfigurationDBRef.ExpiryTimeCacheDesc)
        {
            ConfigurationDBRef.ExpiryTimeCacheDesc expCache = (ConfigurationDBRef.ExpiryTimeCacheDesc) cacheDesc;
            return new DataCacheExpiringImpl(expCache.getMaxAgeSeconds(), expCache.getPurgeIntervalSeconds(), expCache.getCacheReferenceType(),
                    schedulingService, scheduleBucket.allocateSlot(), epStatementHandle);
        }

        throw new IllegalStateException("Cache implementation class not configured");
    }
}
