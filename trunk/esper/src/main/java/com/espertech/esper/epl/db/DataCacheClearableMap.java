package com.espertech.esper.epl.db;

import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.collection.MultiKey;

import java.util.Map;
import java.util.HashMap;

public class DataCacheClearableMap implements DataCache
{
    private Map<MultiKey<Object>, EventTable> cache;

    public DataCacheClearableMap()
    {
        this.cache = new HashMap<MultiKey<Object>, EventTable>();
    }

    public EventTable getCached(Object[] lookupKeys)
    {
        MultiKey<Object> key = new MultiKey<Object>(lookupKeys);
        return cache.get(key);
    }

    public void put(Object[] lookupKeys, EventTable rows)
    {
        MultiKey<Object> key = new MultiKey<Object>(lookupKeys);
        cache.put(key, rows);
    }

    public boolean isActive()
    {
        return false;
    }

    public void clear()
    {
        cache.clear();
    }
}
