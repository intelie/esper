package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;
import java.io.StringWriter;

public class SQLStream extends ProjectedStream
{
    private String databaseName;
    private String sqlWithSubsParams;

    public static SQLStream create(String databaseName, String sqlWithSubsParams)
    {
        return new SQLStream(databaseName, sqlWithSubsParams, null);
    }

    public static SQLStream create(String databaseName, String sqlWithSubsParams, String optStreamName)
    {
        return new SQLStream(databaseName, sqlWithSubsParams, optStreamName);
    }

    public SQLStream(String databaseName, String sqlWithSubsParams, String optStreamName)
    {
        super(new ArrayList<View>(), optStreamName);
        this.databaseName = databaseName;
        this.sqlWithSubsParams = sqlWithSubsParams;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    public String getSqlWithSubsParams()
    {
        return sqlWithSubsParams;
    }

    public void setSqlWithSubsParams(String sqlWithSubsParams)
    {
        this.sqlWithSubsParams = sqlWithSubsParams;
    }

    public void toEQLStream(StringWriter writer)
    {
        writer.write("sql:");
        writer.write(databaseName);
        writer.write('[');
        writer.write(sqlWithSubsParams);
        writer.write(']');
    }
}
