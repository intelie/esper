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
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import com.espertech.esper.client.EventBean;

import java.util.concurrent.*;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Test for multithread-safety of statements that are very similar that is share the same filter and views.
 * <p>
 * The engine shares locks between statements that share filters and views.
 */
public class TestMTStmtSharedView extends TestCase
{
    private static String[] SYMBOLS = {"IBM", "MSFT", "GE"};
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getProvider("TestMTStmtSharedView", config);
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testSharedViews() throws Exception
    {
        trySend(4, 500, 100);
        //trySend(2, 1000, 100);
        //trySend(3, 2000, 20);
    }

    private void trySend(int numThreads, int numRepeats, int numStatements) throws Exception
    {
        // Create same statement X times
        EPStatement stmt[] = new EPStatement[numStatements];
        SupportMTUpdateListener listeners[] = new SupportMTUpdateListener[stmt.length];
        for (int i = 0; i < stmt.length; i++)
        {
            stmt[i] = engine.getEPAdministrator().createEPL(
                " select * " +
                " from " + SupportMarketDataBean.class.getName() + ".std:groupwin(symbol).stat:uni(price)");
            listeners[i] = new SupportMTUpdateListener();
            stmt[i].addListener(listeners[i]);
        }

        // Start send threads
        // Each threads sends each symbol with price = 0 to numRepeats
        long startTime = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtSharedViewCallable(numRepeats, engine, SYMBOLS);
            future[i] = threadPool.submit(callable);
        }

        // Shut down
        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("delta=" + delta + " not less then 5 sec", delta < 5000);   // should take less then 5 seconds even for 100 statements as they need to share resources thread-safely

        // Assert results
        for (SupportMTUpdateListener listener : listeners)
        {
            assertEquals(numRepeats * numThreads * SYMBOLS.length, listener.getNewDataList().size());
            EventBean[] newDataLast = listener.getNewDataList().get(listener.getNewDataList().size() - 1);
            assertEquals(1, newDataLast.length);
            EventBean result = newDataLast[0];
            assertEquals(numRepeats * numThreads, ((Long) result.get("datapoints")).longValue());
            assertTrue(Arrays.asList(SYMBOLS).contains(result.get("symbol")));
            assertEquals(sumToN(numRepeats) * numThreads, result.get("total"));
            listener.reset();
        }

        for (int i = 0; i < stmt.length; i++)
        {
            stmt[i].stop();
        }
    }

    private double sumToN(int N)
    {
        double sum = 0;
        for (int i = 0; i < N; i++)
        {
            sum += i;
        }
        return sum;
    }

    private static Log log = LogFactory.getLog(TestMTStmtSharedView.class);
}
