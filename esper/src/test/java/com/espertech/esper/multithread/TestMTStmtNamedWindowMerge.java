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

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Test for multithread-safety and named window updates.
 */
public class TestMTStmtNamedWindowMerge extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTStmtNamedWindowMerge.class);

    public final static int NUM_STRINGS = 1;
    public final static int NUM_INTS = 1;

    private EPServiceProvider engine;

    public void tearDown()
    {
        engine.initialize();
    }

    public void testConcurrentMerge3Thread() throws Exception
    {
        trySend(3, 100);
    }

    public void testConcurrentMerge2Thread() throws Exception
    {
        trySend(2, 1000);
    }

    private void trySend(int numThreads, int numEventsPerThread) throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        // setup statements
        engine.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as select * from SupportBean");
        engine.getEPAdministrator().createEPL("on SupportBean sb " +
                "merge MyWindow nw where nw.string = sb.string " +
                " when not matched then insert select * " +
                " when matched then update set intPrimitive = nw.intPrimitive + 1");

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future<Boolean> future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new StmtNamedWindowMergeCallable(engine, numEventsPerThread));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        // total up result
        for (int i = 0; i < numThreads; i++)
        {
            Boolean result = future[i].get();
            assertTrue(result);
        }

        // compare
        EventBean[] rows = engine.getEPRuntime().executeQuery("select * from MyWindow").getArray();
        assertEquals(numEventsPerThread, rows.length);
        for (EventBean row : rows)
        {
            assertEquals(numThreads - 1, row.get("intPrimitive"));
        }
        //long deltaTime = endTime - startTime;
        //System.out.println("Totals updated: " + totalUpdates + "  Delta cumu: " + deltaCumulative + "  Delta pooled: " + deltaTime);
    }
}
