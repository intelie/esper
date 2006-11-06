package net.esper.eql.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface StatementExecutor
{
    public void stop();
    public ResultSet execute();
}
