package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;

import java.util.concurrent.*;

/**
 * Test for pattern statement parallel execution by threads.
 */
public class TestMTStmtPattern extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.initialize();
        
        // Less much debug output can be obtained by using external times
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testPattern() throws Exception
    {
        String type = SupportBean.class.getName();

        String pattern = "a=" + type;
        tryPattern(pattern, 4, 20);

        pattern = "a=" + type + " or a=" + type;
        tryPattern(pattern, 2, 20);
    }

    private void tryPattern(String pattern, int numThreads, int numEvents) throws Exception
    {
        Object sendLock = new Object();
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        SendEventWaitCallable[] callables = new SendEventWaitCallable[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            callables[i] = new SendEventWaitCallable(i, engine, sendLock, new GeneratorIterator(numEvents));
            future[i] = threadPool.submit(callables[i]);
        }

        for (int i = 0; i < numEvents; i++)
        {
            EPStatement stmt = engine.getEPAdministrator().createPattern(pattern);
            SupportMTUpdateListener listener = new SupportMTUpdateListener();
            stmt.addListener(listener);

            synchronized(sendLock)
            {
                sendLock.notifyAll();
            }
            Thread.sleep(100);
            // Should be received exactly one
            assertTrue(listener.assertOneGetNewAndReset().get("a") instanceof SupportBean);
        }

        for (SendEventWaitCallable callable : callables)
        {
            callable.setShutdown(true);
        }        
        synchronized(sendLock)
        {
            sendLock.notifyAll();
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
