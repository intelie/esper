package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.concurrent.*;

/**
 * Test for pattern statement parallel execution by threads.
 */
public class TestMTStmtPattern extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        engine = EPServiceProviderManager.getDefaultProvider(configuration);
        engine.initialize();
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
