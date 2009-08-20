package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.concurrent.*;

/**
 * Test for multithread-safety (or lack thereof) for iterators: iterators fail with concurrent mods as expected behavior
 */
public class TestMTStmtIterate extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        engine = EPServiceProviderManager.getProvider("TestMTStmtIterate", config);
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testIteratorSingleStmt() throws Exception
    {
        EPStatement stmt[] = new EPStatement[] {engine.getEPAdministrator().createEPL(
                " select string from " + SupportBean.class.getName() + ".win:time(5 min)")};

        trySend(2, 10, stmt);
    }

    public void testIteratorMultiStmt() throws Exception
    {
        EPStatement stmt[] = new EPStatement[3];
        for (int i = 0; i < stmt.length; i++)
        {
            String stmtText = " select string from " + SupportBean.class.getName() + ".win:time(5 min)";
            stmt[i] = engine.getEPAdministrator().createEPL(stmtText);
        }

        trySend(2, 10, stmt);
    }

    private void trySend(int numThreads, int numRepeats, EPStatement stmt[]) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtIterateCallable(i, engine, stmt, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(5, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }
    }
}
