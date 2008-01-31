package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportMTUpdateListener;

import java.util.concurrent.*;

/**
 * Test for multithread-safety for adding and removing listener.
 */
public class TestMTStmtListenerAddRemove extends TestCase
{
    private EPServiceProvider engine;

    private final static String EVENT_NAME = SupportMarketDataBean.class.getName();

    public void setUp()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getProvider("TestMTStmtListenerAddRemove", config);
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testPatterns() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createPattern("every a=" + EVENT_NAME + "(symbol='IBM')");
        int numThreads = 2;
        tryStatementListenerAddRemove(numThreads, stmt, false, 10000);
    }

    public void testEQL() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL("select * from " + EVENT_NAME + " (symbol='IBM', feed='RT')");
        int numThreads = 2;
        tryStatementListenerAddRemove(numThreads, stmt, true, 10000);
    }

    private void tryStatementListenerAddRemove(int numThreads, EPStatement statement, boolean isEQL, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtListenerAddRemoveCallable(engine, statement, isEQL, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue("Failed stmt=" + statement.getText(), (Boolean) future[i].get());
        }
    }
}
