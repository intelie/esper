package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.concurrent.*;

/**
 * Test for multithread-safety of insert-into and aggregation per group.
 */
public class TestMTStmtNamedWindowIterate extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getDefaultProvider(configuration);
        engine.initialize();
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        engine.getEPAdministrator().createEPL(
                "create window MyWindow.std:groupby(string).win:keepall() as select string, longPrimitive from " + SupportBean.class.getName());

        engine.getEPAdministrator().createEPL(
                "insert into MyWindow(string, longPrimitive) " +
                " select symbol, volume \n" +
                " from " + SupportMarketDataBean.class.getName());
    }

    public void test4Threads() throws Exception
    {
        tryIterate(4, 250);
    }

    public void test2Threads() throws Exception
    {
        tryIterate(2, 500);
    }

    private void tryIterate(int numThreads, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future<Boolean> future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtNamedWindowIterateCallable(Integer.toString(i), engine, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue(future[i].get());
        }
    }
}