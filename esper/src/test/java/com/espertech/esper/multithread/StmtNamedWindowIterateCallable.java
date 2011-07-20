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
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.client.EventBean;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.Assert;

public class StmtNamedWindowIterateCallable implements Callable
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final String threadKey;
    private EPStatement statement;

    public StmtNamedWindowIterateCallable(String threadKey, EPServiceProvider engine, int numRepeats)
    {
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.threadKey = threadKey;

        statement = engine.getEPAdministrator().createEPL("select string, sum(longPrimitive) as sumLong from MyWindow group by string");
    }

    public Object call() throws Exception
    {
        try
        {
            long total = 0;
            for (int loop = 0; loop < numRepeats; loop++)
            {
                // Insert event into named window
                sendMarketBean(threadKey, loop + 1);
                total += loop + 1;

                // iterate over private statement
                SafeIterator safeIter = statement.safeIterator();
                EventBean[] received = ArrayAssertionUtil.iteratorToArray(safeIter);
                safeIter.close();

                for (int i = 0; i < received.length; i++)
                {
                    if (received[i].get("string").equals(threadKey))
                    {
                        long sum = (Long) received[i].get("sumLong");
                        Assert.assertEquals(total, sum);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        return true;
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        engine.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(StmtNamedWindowIterateCallable.class);
}
