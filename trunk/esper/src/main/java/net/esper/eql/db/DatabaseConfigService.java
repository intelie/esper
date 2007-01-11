package net.esper.eql.db;

import net.esper.core.EPStatementHandle;

/**
 * Service providing database connection factory and configuration information
 * for use with historical data polling.
 */
public interface DatabaseConfigService
{
    /**
     * Returns a connection factory for a configured database.
     * @param databaseName is the name of the database
     * @return is a connection factory to use to get connections to the database
     * @throws DatabaseConfigException is thrown to indicate database configuration errors
     */
    public DatabaseConnectionFactory getConnectionFactory(String databaseName) throws DatabaseConfigException;

    /**
     * Returns true to indicate a setting to retain connections between lookups.
     * @param databaseName is the name of the database
     * @param preparedStatementText is the sql text
     * @return a cache implementation to cache connection and prepared statements
     * @throws DatabaseConfigException is thrown to indicate database configuration errors
     */
    public ConnectionCache getConnectionCache(String databaseName, String preparedStatementText) throws DatabaseConfigException;

    /**
     * Returns a new cache implementation for this database.
     * @param databaseName is the name of the database to return a new cache implementation for for
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     * @return cache implementation
     * @throws DatabaseConfigException is thrown to indicate database configuration errors
     */
    public DataCache getDataCache(String databaseName, EPStatementHandle epStatementHandle) throws DatabaseConfigException;
}
