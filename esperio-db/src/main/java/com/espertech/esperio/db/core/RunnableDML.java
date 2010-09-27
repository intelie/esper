/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.db.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.db.DatabaseConfigException;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class RunnableDML implements Runnable
{
    private static Log log = LogFactory.getLog(RunnableDML.class);

    private final RunnableDMLContext context;
    private final EventBean event;

    public RunnableDML(RunnableDMLContext context, EventBean event)
    {
        this.context = context;
        this.event = event;
    }

    public void run()
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled() && (ExecutionPathDebugLog.isTimerDebugEnabled))) {
            log.debug("Executing DML work unit for event " + event);
        }

        int retryMax = context.getRetry() == null ? 1 : context.getRetry();
        int retryCount = 0;

        while(true) {
            try {
                tryDML();
                break;
            }
            catch (Throwable t) {
                log.error("Error in DML named '" + context.getName() + "' :" + t.getMessage(), t);
                retryCount++;
                if (retryCount >= retryMax) {
                    log.warn("Failed DML named '" + context.getName() + "', retry count reached");
                    break;
                }
                if ((context.getRetryWait() != null) && (context.getRetryWait() > 0)) {
                    long interval = (long) (context.getRetryWait() * 1000);
                    log.warn("Retry DML named '" + context.getName() + "', retry interval msec " + interval + " retry count " + retryCount + " max " + retryMax);
                    try {
                        Thread.sleep(interval);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
                else {
                    log.warn("Retry DML named '" + context.getName() + "', retry count " + retryCount + " max " + retryMax);
                }
            }
        }
    }

    private void tryDML() throws DatabaseConfigException, SQLException
    {
        Connection connection = context.getConnectionFactory().getConnection();
        try {
            context.getDmlStatement().execute(connection, event);
        }
        finally {
            connection.close();
        }
    }
}