package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.event.EventBean;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test for multithread-safety of insert-into and aggregation per group.
 */
public class TestMTStmtNamedWindowIterate extends TestCase
{
    private EPServiceProvider engine;

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.initialize();
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        engine.getEPAdministrator().createEQL(
                "create window MyWindow.std:groupby('string').win:keepall() as select string, longPrimitive from " + SupportBean.class.getName());

        engine.getEPAdministrator().createEQL(
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
