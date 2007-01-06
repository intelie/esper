package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportMarketDataBean;

import java.util.concurrent.*;

/**
 * Test for multithread-safety for adding and receiving events through emitted event listeners.
 */
public class TestMTEmitListener extends TestCase
{
    private EPServiceProvider engine;

    private final static String EVENT_NAME = SupportMarketDataBean.class.getName();

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
    }

    public void testEQL() throws Exception
    {
        tryEmitAndListen(10, 1000);
    }

    private void tryEmitAndListen(int numThreads, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new EmitListenerCallable(i, engine, numRepeats);
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
