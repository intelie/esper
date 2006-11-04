package net.esper.eql.core;

import java.sql.Connection;

public interface DatabaseRefService
{
    public Connection getConnection(String databaseName) throws DatabaseRefException;
    public void returnConnection(String databaseName, Connection connection) throws DatabaseRefException;
}
