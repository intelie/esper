package net.esper.multithread;

import net.esper.client.EPServiceProvider;

import java.util.Iterator;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendEventCallable implements Callable
{
    private final int threadNum;
    private final EPServiceProvider engine;
    private final Iterator<Object> events;

    public SendEventCallable(int threadNum, EPServiceProvider engine, Iterator<Object> events)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.events = events;
    }

    public Object call() throws Exception
    {
        try
        {
            while (events.hasNext())
            {
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

    private static final Log log = LogFactory.getLog(SendEventCallable.class);            
}
