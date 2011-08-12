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
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;

import java.util.concurrent.Callable;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtIterateCallable implements Callable
{
    private static final Log log = LogFactory.getLog(StmtIterateCallable.class);
    private final int threadNum;
    private final EPServiceProvider engine;
    private final EPStatement stmt[];
    private final int numRepeats;

    public StmtIterateCallable(int threadNum, EPServiceProvider engine, EPStatement stmt[], int numRepeats)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.stmt = stmt;
        this.numRepeats = numRepeats;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                log.info(".call Thread " + Thread.currentThread().getId() + " sending event " + loop);
                String id = Long.toString(threadNum * 100000000 + loop);
                SupportBean bean = new SupportBean(id, 0);
                engine.getEPRuntime().sendEvent(bean);

                for (int i = 0; i < stmt.length; i++)
                {
                    log.info(".call Thread " + Thread.currentThread().getId() + " starting iterator " + loop);
                    SafeIterator<EventBean> it = stmt[i].safeIterator();
                    boolean found = false;
                    for (;it.hasNext();)
                    {
                        EventBean event = it.next();
                        if (event.get("string").equals(id))
                        {
                            found = true;
                        }
                    }
                    it.close();
                    Assert.assertTrue(found);
                    log.info(".call Thread " + Thread.currentThread().getId() + " end iterator " + loop);
                }
            }
        }
        catch (AssertionFailedError ex)
        {
            log.fatal("Assertion error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        catch (Throwable t)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), t);
            return false;
        }
        return true;
    }
}
