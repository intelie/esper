package net.esper.eql.db;

import net.esper.collection.Pair;
import net.esper.client.EPException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Base class for a Connection and PreparedStatement cache.
 * <p>
 * Implementations control the lifecycle via lifecycle methods, or
 * may simple obtain new resources and close new resources every time.
 * <p>
 * This is not a pool - a cache is associated with one client class and that
 * class is expected to use cache methods in well-defined order of get, done-with and destroy.
 */
public abstract class ConnectionCache
{
    private DatabaseConnectionFactory databaseConnectionFactory;
    private String sql;

    /**
     * Returns a cached or new connection and statement pair.
     * @return connection and statement pair
     */
    public abstract Pair<Connection, PreparedStatement> getConnection();

    /**
     * Indicate to return the connection and statement pair after use.
     * @param pair is the resources to return
     */
    public abstract void doneWith(Pair<Connection, PreparedStatement> pair);

    /**
     * Destroys cache closing all resources cached, if any.
     */
    public abstract void destroy();

    /**
     * Ctor.
     * @param databaseConnectionFactory - connection factory
     * @param sql - statement sql
     */
    protected ConnectionCache(DatabaseConnectionFactory databaseConnectionFactory, String sql)
    {
        this.databaseConnectionFactory = databaseConnectionFactory;
        this.sql = sql;
    }

    /**
     * Close resources.
     * @param pair is the resources to close.
     */
    protected void close(Pair<Connection, PreparedStatement> pair)
    {
        try
        {
            pair.getSecond().close();
        }
        catch (SQLException ex)
        {
            try
            {
                pair.getFirst().close();
            }
            catch (SQLException e) {}
            throw new EPException("Error closing statement", ex);
        }

        try
        {
            pair.getFirst().close();
        }
        catch (SQLException ex)
        {
            throw new EPException("Error closing statement", ex);
        }
    }

    /**
     * Make a new pair of resources.
     * @return pair of resources
     */
    protected Pair<Connection, PreparedStatement> makeNew()
    {
        Connection connection = null;
        try
        {
            connection = databaseConnectionFactory.getConnection();
        }
        catch (DatabaseException ex)
        {
            throw new EPException("Error obtaining connection", ex);
        }

        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = connection.prepareStatement(sql);
        }
        catch (SQLException ex)
        {
            throw new EPException("Error preparing statement '" + sql + "'", ex);
        }

        return new Pair<Connection, PreparedStatement>(connection, preparedStatement);
    }
}
