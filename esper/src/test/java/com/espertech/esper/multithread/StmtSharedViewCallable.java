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
import com.espertech.esper.support.bean.SupportMarketDataBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Callable;

public class StmtSharedViewCallable implements Callable
{
    private final int numRepeats;
    private final EPServiceProvider engine;
    private final String[] symbols;

    public StmtSharedViewCallable(int numRepeats, EPServiceProvider engine, String[] symbols)
    {
        this.numRepeats = numRepeats;
        this.engine = engine;
        this.symbols = symbols;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                for (String symbol : symbols)
                {
                    Object event = makeEvent(symbol, loop);
                    engine.getEPRuntime().sendEvent(event);
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

    private SupportMarketDataBean makeEvent(String symbol, double price)
    {
        return new SupportMarketDataBean(symbol, price, null, null);
    }

    private static final Log log = LogFactory.getLog(StmtSharedViewCallable.class);
}
