package net.esper.eql.db;

import net.esper.event.EventBean;

import java.util.List;

/**
 * Null implementation for a data cache that doesn't ever hit.
 */
public class DataCacheNullImpl implements DataCache
{
    public List<EventBean> getCached(Object[] lookupKeys)
    {
        return null;
    }

    public void put(Object[] lookupKeys, List<EventBean> rows)
    {

    }
}
