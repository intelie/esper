package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.util.ThreadLogUtil;

import java.util.concurrent.Callable;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendEventWaitCallable implements Callable
{
    private final int threadNum;
    private final EPServiceProvider engine;
    private final Iterator<Object> events;
    private final Object semaphore;

    public SendEventWaitCallable(int threadNum, EPServiceProvider engine, Object semaphore, Iterator<Object> events)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.events = events;
        this.semaphore = semaphore;
    }

    public Object call() throws Exception
    {
        try
        {
            while (events.hasNext())
            {
                synchronized(semaphore) {
                    semaphore.wait();
                }
                ThreadLogUtil.info("sending event");
                engine.getEPRuntime().sendEvent(events.next());
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + threadNum, ex);
            return false;
        }
        return true;
    }

    private static final Log log = LogFactory.getLog(SendEventWaitCallable.class);
}
