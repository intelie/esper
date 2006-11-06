package net.esper.eql.db;

import net.esper.client.EPException;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Executor that acquires and releases a new database connection for each lookup
 */
public class StatementExecutorImpl implements StatementExecutor
{
    private DatabaseConnectionFactory databaseConnectionFactory;
    private String sql;

    public void stop()
    {
        // no need to release resources
    }

    public ResultSet execute()
    {



        return null;
    }
}
