package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.Configuration;
import net.esper.support.bean.SupportBean;

import java.util.concurrent.*;

/**
 * Test for multithread-safety (or lack thereof) for iterators: iterators fail with concurrent mods as expected behavior
 */
public class TestMTStmtIterate extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getProvider("TestMTStmtIterate", config);
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testIterator() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL(
                " select string from " + SupportBean.class.getName() + ".win:time(5 min)");

        /**
         * Iterator fail with concurrent mod exception.
         * (1) copy-on-write would be a performance drag
         * (2) clients may want to fail if a concurrent mod happened
         * (3) statement lock could prevent concurrent mod but could also become an issue for deadlock and lock contention
         */

        trySend(2, 10, stmt);
    }

    private void trySend(int numThreads, int numRepeats, EPStatement stmt) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtIterateCallable(i, engine, stmt, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }
    }
}
