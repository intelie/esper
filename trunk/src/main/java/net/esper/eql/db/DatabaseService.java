package net.esper.eql.db;

public interface DatabaseService
{
    public DatabaseConnectionFactory getConnectionFactory(String databaseName) throws DatabaseException;
    public boolean isRetainConnection(String databaseName) throws DatabaseException;
}
