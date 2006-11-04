package net.esper.eql.spec;

import net.esper.view.ViewSpec;

import java.util.List;

public class DBStatementStreamSpec extends StreamSpec
{
    private String databaseName;
    private String sqlWithSubsParams;

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
