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
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Test for multithread-safety and deterministic behavior when using insert-into.
 */
public class TestMTDeterminismListener extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTDeterminismListener.class);
    private EPServiceProvider engine;

    public void tearDown()
    {
        engine.initialize();
    }

    public void testOrderedDeliverySuspend() throws Exception
    {
        trySend(4, 10000, true, ConfigurationEngineDefaults.Threading.Locking.SUSPEND);
    }

    public void testOrderedDeliverySpin() throws Exception
    {
        trySend(4, 10000, true, ConfigurationEngineDefaults.Threading.Locking.SPIN);
    }

    public void manualTestOrderedDeliveryFail() throws Exception
    {
        /**
         * Commented out as this is a manual test -- it should fail since the disable preserve order. 
         */
        trySend(3, 1000, false, null);
    }

    private void trySend(int numThreads, int numEvents, boolean isPreserveOrder, ConfigurationEngineDefaults.Threading.Locking locking) throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setListenerDispatchPreserveOrder(isPreserveOrder);
        config.getEngineDefaults().getThreading().setListenerDispatchLocking(locking);

        engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        // setup statements
        EPStatement stmtInsert = engine.getEPAdministrator().createEPL("select count(*) as cnt from " + SupportBean.class.getName());
        SupportMTUpdateListener listener = new SupportMTUpdateListener();       
        stmtInsert.addListener(listener);

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new SendEventCallable(i, engine, new GeneratorIterator(numEvents)));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        EventBean events[] = listener.getNewDataListFlattened();
        long[] result = new long[events.length];
        for (int i = 0; i < events.length; i++)
        {
            result[i] = (Long) events[i].get("cnt");
        }
        //log.info(".trySend result=" + Arrays.toString(result));

        // assert result
        assertEquals(numEvents * numThreads, events.length);
        for (int i = 0; i < numEvents * numThreads; i++)
        {
            assertEquals(result[i], (long) i + 1);
        }
    }
}
