package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Test for multithread-safety of insert-into and aggregation per group.
 */
public class TestMTStmtInsertInto extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getDefaultProvider(configuration);
        engine.initialize();
    }

    public void testInsertInto() throws Exception
    {
        engine.getEPAdministrator().createEQL(
                "insert into XStream " +
                " select string as key, count(*) as mycount\n" +
                " from " + SupportBean.class.getName() + ".win:time(5 min)" +
                " group by string"
                );
        engine.getEPAdministrator().createEQL(
                "insert into XStream " +
                " select symbol as key, count(*) as mycount\n" +
                " from " + SupportMarketDataBean.class.getName() + ".win:time(5 min)" +
                " group by symbol"
                );
        
        EPStatement stmtConsolidated = engine.getEPAdministrator().createEQL("select key, mycount from XStream");
        listener = new SupportMTUpdateListener();
        stmtConsolidated.addListener(listener);

        trySend(10, 5000);
        trySend(4, 10000);
    }

    private void trySend(int numThreads, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtInsertIntoCallable(Integer.toString(i), engine, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // Assert results
        int totalExpected = numThreads * numRepeats * 2;
        EventBean[] result = listener.getNewDataListFlattened();
        assertEquals(totalExpected, result.length);
        HashMap<Long, Set<String>> results = new LinkedHashMap<Long, Set<String>>();
        for (EventBean event : result)
        {
            long count = (Long) event.get("mycount");
            String key = (String) event.get("key");

            Set<String> entries = results.get(count);
            if (entries == null)
            {
                entries = new HashSet<String>();
                results.put(count, entries);
            }
            entries.add(key);
        }

        assertEquals(numRepeats, results.size());
        for (Set<String> value : results.values())
        {
            assertEquals(2 * numThreads, value.size());
            for (int i = 0; i < numThreads; i++)
            {
                assertTrue(value.contains("E1_" + i));
                assertTrue(value.contains("E2_" + i));
            }            
        }

        listener.reset();
    }
}
