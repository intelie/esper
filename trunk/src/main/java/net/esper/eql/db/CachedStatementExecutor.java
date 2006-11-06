package net.esper.eql.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CachedStatementExecutor implements StatementExecutor
{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void start()
    {

    }

    public void stop()
    {

    }

    public ResultSet execute()
    {
        return null;        
    }

}
