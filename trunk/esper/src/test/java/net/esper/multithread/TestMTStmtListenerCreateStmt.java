package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;

import java.util.concurrent.*;

/**
 * Test for update listeners that create and stop statements.
 *
 */
public class TestMTStmtListenerCreateStmt extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listener;

    public void setUp()
    {
        engine = EPServiceProviderManager.getProvider("TestMTStmtListenerCreateStmt");
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testListenerCreateStmt() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL(
                " select * from " + SupportBean.class.getName());

        tryListener(2, 100, stmt);
    }

    private void tryListener(int numThreads, int numRepeats, EPStatement stmt) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtListenerRouteCallable(i, engine, stmt, numRepeats);
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
