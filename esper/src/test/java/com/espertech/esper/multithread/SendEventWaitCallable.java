package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.util.ThreadLogUtil;

import java.util.concurrent.Callable;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendEventWaitCallable implements Callable
{
    private final int threadNum;
    private final EPServiceProvider engine;
    private final Iterator<Object> events;
    private final Object sendLock;
    private boolean isShutdown;

    public SendEventWaitCallable(int threadNum, EPServiceProvider engine, Object sendLock, Iterator<Object> events)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.events = events;
        this.sendLock = sendLock;
    }

    public void setShutdown(boolean shutdown)
    {
        isShutdown = shutdown;
    }

    public Object call() throws Exception
    {
        try
        {
            while ((events.hasNext() && (!isShutdown)))
            {
                synchronized(sendLock) {
                    sendLock.wait();
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
