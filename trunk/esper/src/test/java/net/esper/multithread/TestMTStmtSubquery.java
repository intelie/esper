package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.util.SupportMTUpdateListener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Test for multithread-safety of a lookup statement.
 */
public class TestMTStmtSubquery extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listener;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.addEventTypeAlias("S0", SupportBean_S0.class);
        config.addEventTypeAlias("S1", SupportBean_S1.class);
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getProvider("TestMTStmtSubquery", config);
        // Use external time for this test, since time is not used here
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testSubquery() throws Exception
    {
        trySend(4, 10000);
        trySend(3, 10000);
        trySend(2, 10000);
    }

    private void trySend(int numThreads, int numRepeats) throws Exception
    {
        EPStatement stmt = engine.getEPAdministrator().createEQL(
                "select (select id from S0.win:length(1000000) where id = s1.id) as value from S1 as s1");

        listener = new SupportMTUpdateListener();
        stmt.addListener(listener);

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtSubqueryCallable(i, engine, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // Assert results
        int totalExpected = numThreads * numRepeats;

        // assert new data
        EventBean[] resultNewData = listener.getNewDataListFlattened();
        assertEquals(totalExpected, resultNewData.length);

        Set<Integer> values = new HashSet<Integer>();
        for (EventBean event : resultNewData)
        {
            values.add((Integer)event.get("value"));
        }
        assertEquals("Unexpected duplicates", totalExpected, values.size());

        listener.reset();
        stmt.stop();
    }
}
