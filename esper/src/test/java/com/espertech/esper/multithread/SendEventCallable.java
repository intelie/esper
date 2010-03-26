package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;

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
        log.info(".call Thread " + Thread.currentThread().getId() + " starting");
        try
        {
            while (events.hasNext())
            {
                engine.getEPRuntime().sendEvent(events.next());
            }
        }
        catch (RuntimeException ex)
        {
            log.fatal("Error in thread " + threadNum, ex);
            return false;
        }
        log.info(".call Thread " + Thread.currentThread().getId() + " done");
        return true;
    }

    private static final Log log = LogFactory.getLog(SendEventCallable.class);            
}
