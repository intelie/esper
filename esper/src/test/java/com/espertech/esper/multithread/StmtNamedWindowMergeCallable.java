/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.support.bean.SupportBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class StmtNamedWindowMergeCallable implements Callable<Boolean>
{
    private final EPRuntimeSPI engine;
    private final int numEvents;

    public StmtNamedWindowMergeCallable(EPServiceProvider engine, int numEvents)
    {
        this.engine = (EPRuntimeSPI) engine.getEPRuntime();
        this.numEvents = numEvents;
    }

    public Boolean call() throws Exception
    {
        long start = System.currentTimeMillis();
        try
        {
            for (int i = 0; i < numEvents; i++)
            {
                engine.sendEvent(new SupportBean("E" + Integer.toString(i), 0));
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return null;
        }
        long end = System.currentTimeMillis();
        return true;
    }

    private static final Log log = LogFactory.getLog(StmtNamedWindowMergeCallable.class);
}
