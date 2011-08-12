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

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;

import java.util.concurrent.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * Test for update listeners that route events.
 */
public class TestMTStmtListenerRoute extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        engine = EPServiceProviderManager.getDefaultProvider(configuration);
    }

    public void testListenerCreateStmt() throws Exception
    {
        tryListener(4, 500);
    }

    private void tryListener(int numThreads, int numRoutes) throws Exception
    {
        EPStatement stmtTrigger = engine.getEPAdministrator().createEPL(
                " select * from " + SupportBean.class.getName());

        EPStatement stmtListen = engine.getEPAdministrator().createEPL(
                " select * from " + SupportMarketDataBean.class.getName());
        SupportMTUpdateListener listener = new SupportMTUpdateListener();
        stmtListen.addListener(listener);

        // Set of events routed by each listener
        Set<SupportMarketDataBean> routed = Collections.synchronizedSet(new HashSet<SupportMarketDataBean>());

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtListenerCreateStmtCallable(i, engine, stmtTrigger, numRoutes, routed);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean)future[i].get());
        }

        // assert
        EventBean[] results = listener.getNewDataListFlattened();
        assertTrue(results.length >= numThreads * numRoutes);

        for (SupportMarketDataBean routedEvent : routed)
        {
            boolean found = false;
            for (int i = 0; i < results.length; i++)
            {
                if (results[i].getUnderlying() == routedEvent)
                {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
}
