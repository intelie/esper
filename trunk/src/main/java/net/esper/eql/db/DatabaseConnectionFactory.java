package net.esper.eql.db;

import java.sql.Connection;

/**
 * Factory for new database connections.
 */
public interface DatabaseConnectionFactory
{
    /**
     * Creates a new database connection.
     * @return new connection
     * @throws DatabaseException throws to indicate a problem getting a new connection
     */
    public Connection getConnection() throws DatabaseException;
}
