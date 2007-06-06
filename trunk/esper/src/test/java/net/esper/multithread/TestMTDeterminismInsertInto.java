package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
        Configuration config = new Configuration();
        // This should fail all test in this class
        // config.getEngineDefaults().getThreading().setInsertIntoDispatchPreserveOrder(false);

        engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();
        engine.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void tearDown()
    {
        engine.initialize();
    }

    public void testSceneOne() throws Exception
    {
        trySendCountFollowedBy(4, 10000);
    }

    public void testSceneTwo() throws Exception
    {
        tryChainedCountSum(3, 10000);
    }

    public void testSceneThree() throws Exception
    {
        tryMultiInsertGroup(3, 10, 1000);
    }

    private void tryMultiInsertGroup(int numThreads, int numStatements, int numEvents) throws Exception
    {
        // setup statements
        EPStatement[] insertIntoStmts = new EPStatement[numStatements];
        for (int i = 0; i < numStatements; i++)
        {
            insertIntoStmts[i] = engine.getEPAdministrator().createEQL("insert into MyStream select '" + i + "'" + " as ident,count(*) as cnt from " + SupportBean.class.getName());
        }
        EPStatement stmtInsertTwo = engine.getEPAdministrator().createEQL("select ident, sum(cnt) as mysum from MyStream group by ident");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmtInsertTwo.addListener(listener);

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        ReentrantReadWriteLock sharedStartLock = new ReentrantReadWriteLock();
        sharedStartLock.writeLock().lock();
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new SendEventRWLockCallable(i, sharedStartLock, engine, new GeneratorIterator(numEvents)));
        }
        Thread.sleep(100);
        sharedStartLock.writeLock().unlock();

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // assert result
        EventBean newEvents[] = listener.getNewDataListFlattened();
        int count = 0;
        for (int i = 0; i < numEvents - 1; i++)
        {
            long expected = total(i + 1);
            for (int j = 0; j < numStatements; j++)
            {
                String ident = (String) newEvents[count].get("ident");
                long mysum = (Long) newEvents[count].get("mysum");
                count++;

                assertEquals(Integer.toString(j), ident);
                assertEquals(expected, mysum);
            }
        }

        // destroy
        for (int i = 0; i < numStatements; i++)
        {
            insertIntoStmts[i].destroy();
        }
        stmtInsertTwo.destroy();
    }

    private void tryChainedCountSum(int numThreads, int numEvents) throws Exception
    {
        // setup statements
        EPStatement stmtInsertOne = engine.getEPAdministrator().createEQL("insert into MyStreamOne select count(*) as cnt from " + SupportBean.class.getName());
        EPStatement stmtInsertTwo = engine.getEPAdministrator().createEQL("insert into MyStreamTwo select sum(cnt) as mysum from MyStreamOne");
        EPStatement stmtInsertThree = engine.getEPAdministrator().createEQL("select * from MyStreamTwo");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmtInsertThree.addListener(listener);

        // execute
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        Future future[] = new Future[numThreads];
        ReentrantReadWriteLock sharedStartLock = new ReentrantReadWriteLock();
        sharedStartLock.writeLock().lock();
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new SendEventRWLockCallable(i, sharedStartLock, engine, new GeneratorIterator(numEvents)));
        }
        Thread.sleep(100);
        sharedStartLock.writeLock().unlock();

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < numThreads; i++)
        {
            assertTrue((Boolean) future[i].get());
        }

        // assert result
        EventBean newEvents[] = listener.getNewDataListFlattened();
        for (int i = 0; i < numEvents - 1; i++)
        {
            long expected = total(i + 1);
            assertEquals(expected, newEvents[i].get("mysum"));
        }

        stmtInsertOne.destroy();
        stmtInsertTwo.destroy();
        stmtInsertThree.destroy();
    }

    private long total(int num)
    {
        long total = 0;
        for (int i = 1; i < num + 1; i++)
        {
            total += i; 
        }
        return total;
    }

    private void trySendCountFollowedBy(int numThreads, int numEvents) throws Exception
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
        ReentrantReadWriteLock sharedStartLock = new ReentrantReadWriteLock();
        sharedStartLock.writeLock().lock();
        for (int i = 0; i < numThreads; i++)
        {
            future[i] = threadPool.submit(new SendEventRWLockCallable(i, sharedStartLock, engine, new GeneratorIterator(numEvents)));
        }
        Thread.sleep(100);
        sharedStartLock.writeLock().unlock();

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
