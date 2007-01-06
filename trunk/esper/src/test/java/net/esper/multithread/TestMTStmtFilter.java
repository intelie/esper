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
 * TODO: Changes made were
 * - UpdateDispatchView all threadlocal
 * - new StreamFactorySvcCreate to disable view resource sharing
 * - StreamFactorySvcCreate uses lock around eventStream.
 *
 * TODO: remaining tests
 * - timer threading
 * - route from listener
 * - pattern statement
 * - output rate limiting
 */

/**
 * Test for multithread-safety for simple filter-based statements
 */
public class TestMTStmtFilter extends TestCase
{
    private final static int NUM_THREADS = 2;
    private final static int NUM_MESSAGES = 1000;

    private EPServiceProvider engine;
    private ExecutorService threadPool;

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        threadPool = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public void testCount() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL("select count(*) as mycount from " + SupportBean.class.getName());
        MTListener listener = new MTListener("mycount");
        stmt.addListener(listener);

        Future future[] = new Future[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++)
        {
            future[i] = threadPool.submit(new SendEventCallable(i, engine, new GeneratorIterator(NUM_MESSAGES)));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < NUM_THREADS; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // verify results
        assertEquals(NUM_MESSAGES * NUM_THREADS, listener.getValues().size());
        TreeSet<Integer> result = new TreeSet<Integer>();
        for (Object row : listener.getValues())
        {
            result.add(((Number) row).intValue());
        }
        assertEquals(NUM_MESSAGES * NUM_THREADS, result.size());
        assertEquals(1, (Object) result.first());
        assertEquals(NUM_MESSAGES * NUM_THREADS, (Object) result.last());
    }
}
