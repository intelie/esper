package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.Configuration;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test for multithread-safety of insert-into and aggregation per group.
 */
public class TestMTStmtNamedWindowConsume extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listenerWindow;
    private SupportMTUpdateListener listenerConsumers[];

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        engine = EPServiceProviderManager.getDefaultProvider(configuration);
        engine.initialize();
    }

    public void testNamedWindow() throws Exception
    {
        EPStatement stmtWindow = engine.getEPAdministrator().createEQL(
                "create window MyWindow.win:keepall() as select string, longPrimitive from " + SupportBean.class.getName());
        listenerWindow = new SupportMTUpdateListener();
        stmtWindow.addListener(listenerWindow);

        engine.getEPAdministrator().createEQL(
                "insert into MyWindow(string, longPrimitive) " +
                " select symbol, volume \n" +
                " from " + SupportMarketDataBean.class.getName());

        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " as s0 delete from MyWindow as win where win.string = s0.id";
        engine.getEPAdministrator().createEQL(stmtTextDelete);

        trySend(4, 1000, 8);
    }

    private void trySend(int numThreads, int numRepeats, int numConsumers) throws Exception
    {
        listenerConsumers = new SupportMTUpdateListener[numConsumers];
        for (int i = 0; i < listenerConsumers.length; i++)
        {
            EPStatement stmtConsumer = engine.getEPAdministrator().createEQL("select string, longPrimitive from MyWindow");
            listenerConsumers[i] = new SupportMTUpdateListener();
            stmtConsumer.addListener(listenerConsumers[i]);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtNamedWindowConsumeCallable(Integer.toString(i), engine, numRepeats);
            future[i] = threadPool.submit(callable);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        // compute list of expected
        List<String> expectedIdsList = new ArrayList<String>();
        for (int i = 0; i < numThreads; i++)
        {
            expectedIdsList.addAll((List<String>) future[i].get());
        }
        String[] expectedIds = expectedIdsList.toArray(new String[0]);

        assertEquals(numThreads * numRepeats, listenerWindow.getNewDataList().size());  // old and new each

        // compute list of received
        for (int i = 0; i < listenerConsumers.length; i++)
        {
            EventBean[] newEvents = listenerConsumers[i].getNewDataListFlattened();
            String[] receivedIds = new String[newEvents.length];
            for (int j = 0; j < newEvents.length; j++)
            {
                receivedIds[j] = (String) newEvents[j].get("string");
            }
            assertEquals(receivedIds.length, expectedIds.length);

            Arrays.sort(receivedIds);
            Arrays.sort(expectedIds);
            Arrays.deepEquals(expectedIds, receivedIds);
        }
    }
}
