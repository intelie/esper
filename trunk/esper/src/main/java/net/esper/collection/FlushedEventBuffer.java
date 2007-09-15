/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.collection;

import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;

import java.util.LinkedList;

/**
 * Buffer for events - accumulates events until flushed.
 */
public class FlushedEventBuffer
{
    private LinkedList<EventBean[]> remainEvents = new LinkedList<EventBean[]>();

    /**
     * Add an event array to buffer.
     * @param events to add
     */
    public void add(EventBean[] events)
    {
        if (events != null)
        {
            remainEvents.add(events);
        }
    }

    /**
     * Get the events currently buffered. Returns null if the buffer is empty. Flushes the buffer.
     * @return array of events in buffer or null if empty
     */
    public EventBean[] getAndFlush()
    {
        EventBean[] flattened = EventBeanUtility.flatten(remainEvents);
        remainEvents.clear();
        return flattened;
    }

    /**
     * Empty buffer.
     */
    public void flush()
    {
        remainEvents.clear();
    }
}
