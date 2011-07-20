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
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Test for multithread-safety and deterministic behavior when using insert-into.
 */
public class TestMTIsolation extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTIsolation.class);

    public void testSceneOne() throws Exception
    {
        tryIsolated(2, 500);
    }

    private void tryIsolated(int numThreads, int numLoops) throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getViewResources().setShareViews(false);
        config.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        ReentrantReadWriteLock sharedStartLock = new ReentrantReadWriteLock();
        sharedStartLock.writeLock().lock();
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new IsolateUnisolateCallable(i, engine, numLoops));
        }
        Thread.sleep(100);
        sharedStartLock.writeLock().unlock();

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }
    }
}