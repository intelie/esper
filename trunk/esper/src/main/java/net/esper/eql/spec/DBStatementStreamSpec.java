package net.esper.eql.spec;

import net.esper.view.ViewSpec;
import net.esper.event.EventAdapterService;
import net.esper.eql.core.AutoImportService;
import net.esper.util.MetaDefItem;
import java.util.List;

/**
 * Specification object for historical data poll via database SQL statement.
 */
public class DBStatementStreamSpec extends StreamSpecBase implements StreamSpecRaw, StreamSpecCompiled, MetaDefItem
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

    /**
     * Returns the database name.
     * @return name of database.
     */
    public String getDatabaseName()
    {
        return databaseName;
    }

    /**
     * Returns the SQL with substitution parameters.
     * @return SQL with parameters embedded as ${stream.param}
     */
    public String getSqlWithSubsParams()
    {
        return sqlWithSubsParams;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      AutoImportService autoImportService)
    {
        return this;
    }

}
