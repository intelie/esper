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
import java.util.*;

/**
 * Test for multithread-safety of insert-into and aggregation per group.
 */
public class TestMTStmtNamedWindowDelete extends TestCase
{
    private EPServiceProvider engine;
    private SupportMTUpdateListener listenerWindow;
    private SupportMTUpdateListener listenerConsumer;

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.initialize();
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
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

        EPStatement stmtConsumer = engine.getEPAdministrator().createEQL("select string, longPrimitive from MyWindow");
        listenerConsumer = new SupportMTUpdateListener();
        stmtConsumer.addListener(listenerConsumer);

        trySend(4, 10000);
    }

    private void trySend(int numThreads, int numRepeats) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            Callable callable = new StmtNamedWindowDeleteCallable(Integer.toString(i), engine, numRepeats);
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

        assertEquals(2 * numThreads * numRepeats, listenerWindow.getNewDataList().size());  // old and new each
        assertEquals(2 * numThreads * numRepeats, listenerConsumer.getNewDataList().size());  // old and new each

        // compute list of received
        EventBean[] newEvents = listenerWindow.getNewDataListFlattened();
        String[] receivedIds = new String[newEvents.length];
        for (int i = 0; i < newEvents.length; i++)
        {
            receivedIds[i] = (String) newEvents[i].get("string");
        }
        assertEquals(receivedIds.length, expectedIds.length);

        Arrays.sort(receivedIds);
        Arrays.sort(expectedIds);
        Arrays.deepEquals(expectedIds, receivedIds);
    }
}