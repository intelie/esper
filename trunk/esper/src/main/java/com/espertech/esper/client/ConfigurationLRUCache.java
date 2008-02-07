package com.espertech.esper.client;

import java.io.Serializable;

/**
 * LRU cache settings.
 */
public class ConfigurationLRUCache implements ConfigurationDataCache, Serializable
{
    private int size;

    /**
     * Ctor.
     * @param size is the maximum cache size
     */
    public ConfigurationLRUCache(int size)
    {
        this.size = size;
    }

    /**
     * Returns the maximum cache size.
     * @return max cache size
     */
    public int getSize()
    {
        return size;
    }

    public String toString()
    {
        return "LRUCacheDesc size=" + size;
    }
}
