package net.esper.multithread;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.NoActionUpdateListener;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMTInsertIntoTimerConcurrency extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTInsertIntoTimerConcurrency.class);
    private AtomicLong idCounter;
    private ExecutorService executorService;
    private EPRuntime epRuntime;
    private EPAdministrator epAdministrator;
    private NoActionUpdateListener noActionUpdateListener;

    public void testRun() throws Exception
    {
        idCounter = new AtomicLong(0);
        executorService = Executors.newCachedThreadPool();
        noActionUpdateListener = new NoActionUpdateListener();

        Configuration epConfig = new Configuration();
        epConfig.addEventTypeAliasSimpleName(SupportBean.class);
        epConfig.getEngineDefaults().getThreading().setInsertIntoDispatchLocking(ConfigurationEngineDefaults.Threading.Locking.SUSPEND);

        final EPServiceProvider epServiceProvider = EPServiceProviderManager.getDefaultProvider(epConfig);
        epServiceProvider.initialize();
        
        epAdministrator = epServiceProvider.getEPAdministrator();
        epRuntime = epServiceProvider.getEPRuntime();

        epAdministrator.startAllStatements();

        String eql = "insert into Stream1 select count(*) as cnt from SupportBean.win:time(7 sec)";
        createEQL(eql, noActionUpdateListener);
        eql = eql + " output every 10 seconds";
        createEQL(eql, noActionUpdateListener);

        SendEventRunnable sendTickEventRunnable = new SendEventRunnable();
        start(sendTickEventRunnable, 4);

        // Adjust here for long-running test
        Thread.sleep(3000);
        
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    private EPStatement createEQL(String eql, UpdateListener updateListener)
    {
        EPStatement statement = epAdministrator.createEQL(eql);
        statement.addListener(updateListener);
        return statement;
    }

    private <T> void start(Callable<T> task, int numInstances)
    {
        for (int i = 0; i < numInstances; i++)
        {
            start(task);
        }
    }

    private <T> Future<T> start(Callable<T> task)
    {
        Future<T> future = executorService.submit(task);
        return future;
    }

    private void sendEvent()
    {
        long id = idCounter.getAndIncrement();
        SupportBean event = new SupportBean();
        event.setLongPrimitive(id);
        epRuntime.sendEvent(event);
    }

    class SendEventRunnable implements Callable<Object>
    {
        public Object call() throws Exception
        {
            int count = 0;
            while (true)
            {
                sendEvent();
                Thread.sleep(1);
                count++;

                if (count % 1000 == 0)
                {
                    log.info("Thread " + Thread.currentThread().getId() + " send " + count + " events");
                }
            }
        }
    }
}
