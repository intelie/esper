package net.esper.eql.db;

/**
 * Service providing database connection factory and configuration information
 * for use with historical data polling.
 */
public interface DatabaseService
{
    /**
     * Returns a connection factory for a configured database.
     * @param databaseName is the name of the database
     * @return is a connection factory to use to get connections to the database
     * @throws DatabaseException is throws to indicate database configuration errors
     */
    public DatabaseConnectionFactory getConnectionFactory(String databaseName) throws DatabaseException;

    /**
     * Returns true to indicate a setting to retain connections between lookups.
     * @param databaseName is the name of the database
     * @return true to indicate to retain existing connections between lookup, or false if pooled
     * connections that are to be closed between lookups
     * @throws DatabaseException is throws to indicate database configuration errors
     */
    public boolean isRetainConnection(String databaseName) throws DatabaseException;
}
