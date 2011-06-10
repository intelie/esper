/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.db.core;

import com.espertech.esper.adapter.BaseSubscription;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.filter.FilterHandleCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Executor;

public class EsperIODBBaseSubscription extends BaseSubscription
{
    private static Log log = LogFactory.getLog(EsperIODBBaseSubscription.class);

    private String uuid = UUID.randomUUID().toString();

    private final RunnableFactory runnableFactory;
    private final Executor executor;

    public EsperIODBBaseSubscription(RunnableFactory runnableFactory, Executor executor)
    {
        this.runnableFactory = runnableFactory;
        this.executor = executor;
    }

    public void matchFound(EventBean event, Collection<FilterHandleCallback> allStmtMatches)
    {
        try {
            Runnable runnable = runnableFactory.makeRunnable(event);
            executor.execute(runnable);
        }
        catch (Throwable t) {
            log.error("Error executing database action:" + t.getMessage(), t);
        }
    }

    public boolean isSubSelect()
    {
        return false;
    }

    public String getStatementId()
    {
        return uuid;
    }
}
