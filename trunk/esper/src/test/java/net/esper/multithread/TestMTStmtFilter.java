package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;

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
        engine = EPServiceProviderManager.getProvider("TestMTStmtFilter");
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
        EPStatement stmt = engine.getEPAdministrator().createEQL("select count(*) as mycount from " + SupportBean.class.getName());
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