package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;

import java.util.concurrent.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Test for multithread-safety and deterministic behavior when using insert-into.
 */
public class TestMTDeterminismInsertInto extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTDeterminismInsertInto.class);
    private EPServiceProvider engine;

    public void setUp()
    {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.initialize();
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testSceneOne() throws Exception
    {
        trySend(4, 100);
    }

    private void trySend(int numThreads, int numEvents) throws Exception
    {
        // setup statements
        EPStatement stmtInsert = engine.getEPAdministrator().createEQL("insert into MyStream select count(*) as cnt from " + SupportBean.class.getName());
        stmtInsert.addListener(new UpdateListener() {

            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                log.debug(".update cnt=" + newEvents[0].get("cnt"));
            }
        });

        SupportUpdateListener listeners[] = new SupportUpdateListener[numEvents];
        for (int i = 0; i < numEvents; i++)
        {
            String text = "select * from pattern [MyStream(cnt=" + (i + 1) + ") -> MyStream(cnt=" + (i + 2) + ")]";
            EPStatement stmt = engine.getEPAdministrator().createEQL(text);
            listeners[i] = new SupportUpdateListener();
            stmt.addListener(listeners[i]);
        }

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new SendEventCallable(i, engine, new GeneratorIterator(numEvents)));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // assert result
        for (int i = 0; i < numEvents - 1; i++)
        {
            assertEquals("Listener not invoked: #" + i, 1, listeners[i].getNewDataList().size());
        }
    }
}
