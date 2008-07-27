package com.espertech.esper.client;

import java.io.Serializable;

/**
 * Expiring cache settings.
 */
public class ConfigurationExpiryTimeCache implements ConfigurationDataCache, Serializable
{
    private ConfigurationCacheReferenceType cacheReferenceType;
    private double maxAgeSeconds;
    private double purgeIntervalSeconds;

    /**
     * Ctor.
     * @param maxAgeSeconds is the maximum age in seconds
     * @param purgeIntervalSeconds is the purge interval
     * @param cacheReferenceType the reference type may allow garbage collection to remove entries from
     * cache unless HARD reference type indicates otherwise
     */
    public ConfigurationExpiryTimeCache(double maxAgeSeconds, double purgeIntervalSeconds, ConfigurationCacheReferenceType cacheReferenceType)
    {
        this.maxAgeSeconds = maxAgeSeconds;
        this.purgeIntervalSeconds = purgeIntervalSeconds;
        this.cacheReferenceType = cacheReferenceType;
    }

    /**
     * Returns the maximum age in seconds.
     * @return number of seconds
     */
    public double getMaxAgeSeconds()
    {
        return maxAgeSeconds;
    }

    /**
     * Returns the purge interval length.
     * @return purge interval in seconds
     */
    public double getPurgeIntervalSeconds()
    {
        return purgeIntervalSeconds;
    }

    /**
     * Returns the enumeration whether hard, soft or weak reference type are used
     * to control whether the garbage collection can remove entries from cache.
     * @return reference type
     */
    public ConfigurationCacheReferenceType getCacheReferenceType()
    {
        return cacheReferenceType;
    }

    public String toString()
    {
        return "ExpiryTimeCacheDesc maxAgeSeconds=" + maxAgeSeconds + " purgeIntervalSeconds=" + purgeIntervalSeconds;
    }
}

