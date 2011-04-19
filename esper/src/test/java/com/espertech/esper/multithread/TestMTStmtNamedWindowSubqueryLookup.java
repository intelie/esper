package com.espertech.esper.multithread;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Test for multithread-safety and named window subqueries and direct index-based lookup.
 */
public class TestMTStmtNamedWindowSubqueryLookup extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTStmtNamedWindowSubqueryLookup.class);

    private EPServiceProvider engine;

    public void tearDown()
    {
        engine.initialize();
    }

    public void testConcurrentSubquery() throws Exception
    {
        trySend(3, 10000);
    }

    private void trySend(int numThreads, int numEventsPerThread) throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        // setup statements
        engine.getEPAdministrator().createEPL("create schema MyUpdateEvent as (key string, intupd int)");
        engine.getEPAdministrator().createEPL("create schema MySchema as (string string, intval int)");
        EPStatement namedWindow = engine.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as MySchema");
        engine.getEPAdministrator().createEPL("on MyUpdateEvent mue merge MyWindow mw " +
                "where mw.string = mue.key " +
                "when not matched then insert select key as string, intupd as intval " +
                "when matched then delete");
        EPStatement targetStatement = engine.getEPAdministrator().createEPL("select (select intval from MyWindow mw where mw.string = sb.string) as val from SupportBean sb");

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future<Boolean> future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new StmtNamedWindowSubqueryLookupCallable(i, engine, numEventsPerThread, targetStatement));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        // total up result
        for (int i = 0; i < numThreads; i++)
        {
            Boolean result = future[i].get();
            assertTrue(result);
        }
        
        EventBean[] events = ArrayAssertionUtil.iteratorToArray(namedWindow.iterator());
        assertEquals(0, events.length);
    }
}
