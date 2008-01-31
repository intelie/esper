package com.espertech.esper.eql.db;

import com.espertech.esper.collection.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Caches the Connection and PreparedStatement instance for reuse.
 */
public class ConnectionCacheImpl extends ConnectionCache
{
    private Pair<Connection, PreparedStatement> cache;

    /**
     * Ctor.
     * @param databaseConnectionFactory - connection factory
     * @param sql - statement sql
     */
    public ConnectionCacheImpl(DatabaseConnectionFactory databaseConnectionFactory, String sql)
    {
        super(databaseConnectionFactory, sql);
    }

    public Pair<Connection, PreparedStatement> getConnection()
    {
        if (cache == null)
        {
            cache = makeNew();
        }
        return cache;
    }

    public void doneWith(Pair<Connection, PreparedStatement> pair)
    {
        // no need to implement
    }

    public void destroy()
    {
        if (cache != null)
        {
            close(cache);
        }
        cache = null;
    }
}
