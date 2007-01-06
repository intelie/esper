package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.bean.SupportBean;

import java.util.concurrent.*;

/**
 * Test for multithread-safety for joins.
 */
public class TestMTStmtJoin extends TestCase
{
    private EPServiceProvider engine;

    private final static String EVENT_NAME = SupportBean.class.getName();

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
    }

    public void testJoin() throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL("select istream * \n" +
                "  from " + EVENT_NAME + "(string='s0').win:length(1000000) as s0,\n" +
                "       " + EVENT_NAME + "(string='s1').win:length(1000000) as s1\n" +
                "where s0.longPrimitive = s1.longPrimitive\n"
                );
        trySendAndReceive(4, stmt, 10000);
        trySendAndReceive(2, stmt, 20000);
    }

    private void trySendAndReceive(int numThreads, EPStatement statement, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtJoinCallable(i, engine, statement, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue("Failed in " + statement.getText(), (Boolean) future[i].get());
        }
    }
}
