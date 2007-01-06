package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

import java.util.concurrent.*;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;

/**
 * Test for insert-into and aggregation
 *
 */
public class TestMTStmtPattern extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listener;

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        // Less much debug output can be obtained by using external times
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testOrPattern() throws Exception
    {
        tryOrPattern(10, 100);
    }

    private void tryOrPattern(int numThreads, int numEvents) throws Exception
    {
        String type = SupportBean.class.getName();
        String pattern = "a=" + type;
        Object semaphore = new Object();

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new SendEventWaitCallable(i, engine, semaphore, new GeneratorIterator(numEvents));
            future[i] = threadPool.submit(callable);
        }

        for (int i = 0; i < numEvents; i++)
        {
            EPStatement stmt = engine.getEPAdministrator().createPattern(pattern);
            SupportMTUpdateListener listener = new SupportMTUpdateListener();
            stmt.addListener(listener);

            synchronized(semaphore)
            {
                semaphore.notifyAll();
            }
            Thread.sleep(100);
            // Should be received exactly one
            assertTrue(listener.assertOneGetNewAndReset().get("a") instanceof SupportBean);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
