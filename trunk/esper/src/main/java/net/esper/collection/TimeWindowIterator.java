/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.collection;

import java.util.*;
import net.esper.event.EventBean;

/**
 * Iterator for {@link TimeWindow} to iterate over a timestamp slots that hold events.
 */
public final class TimeWindowIterator implements Iterator<EventBean>
{
    private final Iterator<Pair<Long, LinkedList<EventBean>>> keyIterator;
    private Iterator<EventBean> currentListIterator;

    /**
     * Ctor.
     * @param window is the time-slotted collection
     */
    public TimeWindowIterator(LinkedList<Pair<Long, LinkedList<EventBean>>> window)
    {
        keyIterator = window.iterator();
        if (keyIterator.hasNext())
        {
            Pair<Long, LinkedList<EventBean>> pair = (Pair<Long, LinkedList<EventBean>>) keyIterator.next();
            currentListIterator = pair.getSecond().iterator();
        }
    }

    public final EventBean next()
    {
        if (currentListIterator == null)
        {
            throw new NoSuchElementException();
        }

        EventBean eventBean = currentListIterator.next();

        if (!currentListIterator.hasNext())
        {
            currentListIterator = null;
            if (keyIterator.hasNext())
            {
                Pair<Long, LinkedList<EventBean>> pair = (Pair<Long, LinkedList<EventBean>>) keyIterator.next();
                currentListIterator = pair.getSecond().iterator();
            }
        }

        return eventBean;
    }

    public final void remove()
    {
        throw new UnsupportedOperationException();
    }

    public final boolean hasNext()
    {
        if (currentListIterator == null)
        {
            return false;
        }

        if (currentListIterator.hasNext())
        {
            return true;
        }

        currentListIterator = null;

        if (!keyIterator.hasNext())
        {
            return false;
        }

        return true;
    }
}