/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.collection;

/**
 * Simple queue implementation based on a Linked List per thread.
 * Objects can be added to the queue tail or queue head.
 */
public class ThreadWorkQueue
{
    private final ThreadLocal<DualWorkQueue> threadQueue = new ThreadLocal<DualWorkQueue>()
    {
        protected synchronized DualWorkQueue initialValue()
        {
            return new DualWorkQueue();
        }
    };

    /**
     * Adds event to the back queue.
     * @param event to add
     */
    public void addBack(Object event)
    {
        DualWorkQueue queue = threadQueue.get();
        queue.getBackQueue().addLast(event);
    }

    /**
     * Adds event to the front queue.
     * @param event to add
     */
    public void addFront(Object event)
    {
        DualWorkQueue queue = threadQueue.get();
        queue.getFrontQueue().addLast(event);
    }

    public DualWorkQueue getThreadQueue() {
        return threadQueue.get();
    }
}
