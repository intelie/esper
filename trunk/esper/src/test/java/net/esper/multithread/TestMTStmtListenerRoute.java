package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

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
        engine = EPServiceProviderManager.getDefaultProvider();
    }

    public void testListenerCreateStmt() throws Exception
    {
        tryListener(4, 500);
    }

    private void tryListener(int numThreads, int numRoutes) throws Exception
    {
        EPStatement stmtTrigger = engine.getEPAdministrator().createEQL(
                " select * from " + SupportBean.class.getName());

        EPStatement stmtListen = engine.getEPAdministrator().createEQL(
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
