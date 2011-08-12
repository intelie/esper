/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
