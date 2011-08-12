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
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;

import java.util.concurrent.Callable;

import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtSubqueryCallable implements Callable
{
    private final int threadNum;
    private final EPServiceProvider engine;
    private final int numRepeats;

    public StmtSubqueryCallable(int threadNum, EPServiceProvider engine, int numRepeats)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.numRepeats = numRepeats;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                int id = threadNum * 10000000 + loop;
                Object eventS0 = new SupportBean_S0(id);
                Object eventS1 = new SupportBean_S1(id);

                engine.getEPRuntime().sendEvent(eventS0);
                engine.getEPRuntime().sendEvent(eventS1);
            }
        }
        catch (AssertionFailedError ex)
        {
            log.fatal("Assertion error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        return true;
    }

    private static final Log log = LogFactory.getLog(StmtSubqueryCallable.class);
}
