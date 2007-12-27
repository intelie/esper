/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.eql.spec.ViewSpec;
import net.esper.event.EventAdapterService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.named.NamedWindowService;
import net.esper.eql.variable.VariableService;
import net.esper.util.MetaDefItem;
import net.esper.pattern.PatternObjectResolutionService;
import net.esper.schedule.TimeProvider;

import java.util.List;

/**
 * Specification object for historical data poll via database SQL statement.
 */
public class DBStatementStreamSpec extends StreamSpecBase implements StreamSpecRaw, StreamSpecCompiled, MetaDefItem
{
    private String databaseName;
    private String sqlWithSubsParams;
    private String metadataSQL;

    /**
     * Ctor.
     * @param optionalStreamName is a stream name optionally given to stream
     * @param viewSpecs is a list of views onto the stream
     * @param databaseName is the database name to poll
     * @param sqlWithSubsParams is the SQL with placeholder parameters
     * @param metadataSQL is the sample SQL to retrieve statement metadata, if any was supplied
     */
    public DBStatementStreamSpec(String optionalStreamName, List<ViewSpec> viewSpecs, String databaseName, String sqlWithSubsParams, String metadataSQL)
    {
        super(optionalStreamName, viewSpecs);

        this.databaseName = databaseName;
        this.sqlWithSubsParams = sqlWithSubsParams;
        this.metadataSQL = metadataSQL;
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

    /**
     * Returns the optional sample metadata SQL
     * @return null if not supplied, or SQL to fire to retrieve metadata
     */
    public String getMetadataSQL()
    {
        return metadataSQL;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      MethodResolutionService methodResolutionService,
                                      PatternObjectResolutionService patternObjectResolutionService,
                                      TimeProvider timeProvider,
                                      NamedWindowService namedWindowService,
                                      VariableService variableService)
    {
        return this;
    }

}
