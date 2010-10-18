/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.db.core;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.db.DatabaseConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RunnableDMLContext
{
    private static Log log = LogFactory.getLog(RunnableDMLContext.class);

    private final String name;
    private final DatabaseConnectionFactory connectionFactory;
    private final DMLStatement dmlStatement;
    private final Integer retry;
    private final Double retryWait;

    public RunnableDMLContext(String name, DatabaseConnectionFactory connectionFactory, DMLStatement dmlStatement, Integer retry, Double retryWait)
    {
        this.name = name;
        this.connectionFactory = connectionFactory;
        this.dmlStatement = dmlStatement;
        this.retry = retry;
        this.retryWait = retryWait;
    }

    public String getName()
    {
        return name;
    }

    public DatabaseConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    public DMLStatement getDmlStatement() {
        return dmlStatement;
    }

    public Integer getRetry()
    {
        return retry;
    }

    public Double getRetryWait()
    {
        return retryWait;
    }
}