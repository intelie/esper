package net.esper.eql.spec;

import net.esper.view.ViewSpec;

import java.util.List;

/**
 * Specification object for historical data poll via database SQL statement.
 */
public class DBStatementStreamSpec extends StreamSpec
{
    private String databaseName;
    private String sqlWithSubsParams;

    /**
     * Ctor.
     * @param optionalStreamName is a stream name optionally given to stream
     * @param viewSpecs is a list of views onto the stream
     * @param databaseName is the database name to poll
     * @param sqlWithSubsParams is the SQL with placeholder parameters
     */
    public DBStatementStreamSpec(String optionalStreamName, List<ViewSpec> viewSpecs, String databaseName, String sqlWithSubsParams)
    {
        super(optionalStreamName, viewSpecs);

        this.databaseName = databaseName;
        this.sqlWithSubsParams = sqlWithSubsParams;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public String getSqlWithSubsParams()
    {
        return sqlWithSubsParams;
    }
}
