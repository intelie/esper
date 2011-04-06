/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.dispatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayDeque;

/**
 * Implements dispatch service using a thread-local linked list of Dispatchable instances.
 */
public class DispatchServiceImpl implements DispatchService
{
    private final ThreadLocal<ArrayDeque<Dispatchable>> threadDispatchQueue = new ThreadLocal<ArrayDeque<Dispatchable>>()
    {
        protected synchronized ArrayDeque<Dispatchable> initialValue()
        {
            return new ArrayDeque<Dispatchable>();
        }
    };

    public void dispatch()
    {
        dispatchFromQueue(threadDispatchQueue.get());
    }

    public void addExternal(Dispatchable dispatchable)
    {
        ArrayDeque<Dispatchable> dispatchQueue = threadDispatchQueue.get();
        addToQueue(dispatchable, dispatchQueue);
    }

    private static void addToQueue(Dispatchable dispatchable, ArrayDeque<Dispatchable> dispatchQueue)
    {
        dispatchQueue.add(dispatchable);
    }

    private static void dispatchFromQueue(ArrayDeque<Dispatchable> dispatchQueue)
    {
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
