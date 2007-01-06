package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportMarketDataBean;

import java.util.concurrent.*;

/**
 * Test for multithread-safety for adding and removing listener.
 *
 * Threads in this test get a handle to a statement and then add and remove listeners.
 *
 * TODO - done:
 * - EPStatementSupport added RW lock for Set<Listener> writes and iteration
 * - EPEQLStatement when adding and removing the last listener now uses the statement resource lock  
 */
public class TestMTStmtListener extends TestCase
{
    private EPServiceProvider engine;

    private final static String EVENT_NAME = SupportMarketDataBean.class.getName();

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        // Less much debug output can be obtained by using external times
        //engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
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
            Callable callable = new StmtListenerCallable(engine, statement, isEQL, numRepeats);
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
