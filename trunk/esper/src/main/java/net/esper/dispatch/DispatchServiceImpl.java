/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.dispatch;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.LinkedList;

import net.esper.util.ExecutionPathDebugLog;

/**
 * Implements dispatch service using a thread-local linked list of Dispatchable instances.
 */
public class DispatchServiceImpl implements DispatchService
{
    private static final ThreadLocal<LinkedList<Dispatchable>> threadDispatchQueue = new ThreadLocal<LinkedList<Dispatchable>>()
    {
        protected synchronized LinkedList<Dispatchable> initialValue()
        {
            return new LinkedList<Dispatchable>();
        }
    };

    public void dispatch()
    {
        dispatchFromQueue(threadDispatchQueue.get());
    }

    public void addExternal(Dispatchable dispatchable)
    {
        LinkedList<Dispatchable> dispatchQueue = threadDispatchQueue.get();
        addToQueue(dispatchable, dispatchQueue);
    }

    private static void addToQueue(Dispatchable dispatchable, LinkedList<Dispatchable> dispatchQueue)
    {
        dispatchQueue.add(dispatchable);
    }

    private static void dispatchFromQueue(LinkedList<Dispatchable> dispatchQueue)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".dispatchFromQueue Dispatch queue is " + dispatchQueue.size() + " elements");
        }

        while(true)
        {
            Dispatchable next = dispatchQueue.poll();
            if (next != null)
            {
                next.execute();
            }
            else
            {
                break;
            }
        }
    }

    private static final Log log = LogFactory.getLog(DispatchServiceImpl.class);
}
