package net.esper.regression.mt;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;

public class SingleStmtSimpleTest extends TestCase
{
    private final static int NUM_THREADS = 1;
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

        assertEquals(NUM_MESSAGES * NUM_THREADS, listener.getValues().size());
    }
}
