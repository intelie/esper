package net.esper.client.soda;

import java.io.StringWriter;

/**
 * An SQL stream that polls via SQL for events via join.
 */
public class SQLStream extends Stream
{
    private String databaseName;
    private String sqlWithSubsParams;

    /**
     * Creates a new SQL-based stream.
     * @param databaseName is the database name to poll
     * @param sqlWithSubsParams is the SQL to use
     * @return stream
     */
    public static SQLStream create(String databaseName, String sqlWithSubsParams)
    {
        return new SQLStream(databaseName, sqlWithSubsParams, null);
    }

    /**
     * Creates a new SQL-based stream.
     * @param databaseName is the database name to poll
     * @param sqlWithSubsParams is the SQL to use
     * @param optStreamName is the as-name of the stream 
     * @return stream
     */
    public static SQLStream create(String databaseName, String sqlWithSubsParams, String optStreamName)
    {
        return new SQLStream(databaseName, sqlWithSubsParams, optStreamName);
    }

    /**
     * Ctor.
     * @param databaseName is the database name to poll
     * @param sqlWithSubsParams is the SQL to use
     * @param optStreamName is the optional as-name of the stream, or null if unnamed 
     */
    public SQLStream(String databaseName, String sqlWithSubsParams, String optStreamName)
    {
        super(optStreamName);
        this.databaseName = databaseName;
        this.sqlWithSubsParams = sqlWithSubsParams;
    }

    /**
     * Returns the database name.
     * @return database name
     */
    public String getDatabaseName()
    {
        return databaseName;
    }

    /**
     * Sets the database name.
     * @param databaseName database name
     */
    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    /**
     * Returns the SQL with optional substitution parameters in the SQL.
     * @return SQL
     */
    public String getSqlWithSubsParams()
    {
        return sqlWithSubsParams;
    }

    /**
     * Sets the SQL with optional substitution parameters in the SQL.
     * @param sqlWithSubsParams SQL set set
     */
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
