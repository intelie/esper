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
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.TreeSet;

/**
 * Test for multithread-safety for a simple aggregation case using count(*).
 */
public class TestMTStmtFilter extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        engine = EPServiceProviderManager.getProvider("TestMTStmtFilter", config);
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testCount() throws Exception
    {
        tryCount(2, 1000);
        tryCount(4, 1000);
    }

    public void tryCount(int numThreads, int numMessages) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        EPStatement stmt = engine.getEPAdministrator().createEPL("select count(*) as mycount from " + SupportBean.class.getName());
        MTListener listener = new MTListener("mycount");
        stmt.addListener(listener);

        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new SendEventCallable(i, engine, new GeneratorIterator(numMessages)));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // verify results
        assertEquals(numMessages * numThreads, listener.getValues().size());
        TreeSet<Integer> result = new TreeSet<Integer>();
        for (Object row : listener.getValues())
        {
            result.add(((Number) row).intValue());
        }
        assertEquals(numMessages * numThreads, result.size());
        assertEquals(1, (Object) result.first());
        assertEquals(numMessages * numThreads, (Object) result.last());
    }
}
