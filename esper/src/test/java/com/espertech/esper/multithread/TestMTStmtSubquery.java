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

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Test for multithread-safety of a lookup statement.
 */
public class TestMTStmtSubquery extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("S0", SupportBean_S0.class);
        config.addEventType("S1", SupportBean_S1.class);
        engine = EPServiceProviderManager.getProvider("TestMTStmtSubquery", config);
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testSubquery() throws Exception
    {
        trySend(4, 10000);
        trySend(3, 10000);
        trySend(2, 10000);
    }

    private void trySend(int numThreads, int numRepeats) throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEPL(
                "select (select id from S0.win:length(1000000) where id = s1.id) as value from S1 as s1");

        listener = new SupportMTUpdateListener();
        stmt.addListener(listener);

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtSubqueryCallable(i, engine, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // Assert results
        int totalExpected = numThreads * numRepeats;

        // assert new data
        EventBean[] resultNewData = listener.getNewDataListFlattened();
        assertEquals(totalExpected, resultNewData.length);

        Set<Integer> values = new HashSet<Integer>();
        for (EventBean event : resultNewData)
        {
            values.add((Integer)event.get("value"));
        }
        assertEquals("Unexpected duplicates", totalExpected, values.size());

        listener.reset();
        stmt.stop();
    }
}
