package net.esper.eql.db;

import java.sql.Connection;

public interface DatabaseConnectionFactory
{
    public Connection getConnection() throws DatabaseException;
}
