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
 * Container for events per time slot. The time is provided as long milliseconds by client classes.
 * Events are for a specified timestamp and the implementation creates and adds the event to a slot for that timestamp.
 * Events can be expired from the window via the expireEvents method when their timestamp is before
 * (or less then) an expiry timestamp passed in. Expiry removes the event from the window.
 * The window allows iteration through its contents.
 *
 * It is assumed that the timestamp passed to the add method is ascending. The window is backed by a
 * LinkedList reflecting the timestamp order rather then any sorted map or linked hash map for performance reasons.
 */
public final class TimeWindow implements Iterable
{
    private final LinkedList<Pair<Long, LinkedList<EventBean>>> window;
    private Long oldestTimestamp;
    private Map<EventBean, LinkedList<EventBean>> reverseIndex;

    /**
     * Ctor.
     */
    public TimeWindow(boolean isSupportRemoveStream)
    {
        this.window = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        this.oldestTimestamp = null;

        if (isSupportRemoveStream)
        {
            reverseIndex = new HashMap<EventBean, LinkedList<EventBean>>();
        }
    }

    /**
     * Adds event to the time window for the specified timestamp.
     * @param timestamp - the time slot for the event
     * @param bean - event to add
     */
    public final void add(long timestamp, EventBean bean)
    {
        // On add to an empty window, set the oldest event's timestamp
        if (oldestTimestamp == null)
        {
            oldestTimestamp = timestamp;
        }

        // Empty window
        if (window.isEmpty())
        {
            LinkedList<EventBean> listOfBeans = new LinkedList<EventBean>();
            listOfBeans.add(bean);
            Pair<Long, LinkedList<EventBean>> pair = new Pair<Long, LinkedList<EventBean>>(timestamp, listOfBeans);
            window.add(pair);

            if (reverseIndex != null)
            {
                reverseIndex.put(bean, listOfBeans);
            }
            return;
        }

        Pair<Long, LinkedList<EventBean>> lastPair = window.getLast();

        // Windows last timestamp matches the one supplied
        if (lastPair.getFirst() == timestamp)
        {
            lastPair.getSecond().add(bean);
            if (reverseIndex != null)
            {
                reverseIndex.put(bean, lastPair.getSecond());
            }
            return;
        }

        // Append to window
        LinkedList<EventBean> listOfBeans = new LinkedList<EventBean>();
        listOfBeans.add(bean);
        Pair<Long, LinkedList<EventBean>> pair = new Pair<Long, LinkedList<EventBean>>(timestamp, listOfBeans);
        if (reverseIndex != null)
        {
            reverseIndex.put(bean, listOfBeans);
        }
        window.add(pair);
    }

    public final void remove(EventBean event)
    {
        if (reverseIndex == null)
        {
            throw new UnsupportedOperationException("Time window does not accept event removal");
        }
        List<EventBean> list = reverseIndex.get(event);
        if (list != null)
        {
            list.remove(event);
        }
        reverseIndex.remove(event);
    }

    /**
     * Return and remove events in time-slots earlier (less) then the timestamp passed in,
     * returning the list of events expired.
     * @param expireBefore is the timestamp from which on to keep events in the window
     * @return a list of events expired and removed from the window, or null if none expired
     */
    public final List<EventBean> expireEvents(long expireBefore)
    {
        if (window.isEmpty())
        {
            return null;
        }

        Pair<Long, LinkedList<EventBean>> pair = window.getFirst();

        // If the first entry's timestamp is after the expiry date, nothing to expire
        if (pair.getFirst() >= expireBefore)
        {
            return null;
        }

        LinkedList<EventBean> resultBeans = new LinkedList<EventBean>();

        // Repeat until the window is empty or the timestamp is above the expiry time
        do
        {
            resultBeans.addAll(pair.getSecond());
            window.removeFirst();

            if (window.isEmpty())
            {
                break;
            }

            pair = window.getFirst();
        }
        while (pair.getFirst() < expireBefore);

        if (window.isEmpty())
        {
            oldestTimestamp = null;
        }
        else
        {
            oldestTimestamp = pair.getFirst();
        }

        if (reverseIndex != null)
        {
            for (EventBean expired : resultBeans)
            {
                reverseIndex.remove(expired);
            }
        }

        return resultBeans;
    }

    /**
     * Returns event iterator.
     * @return iterator over events currently in window
     */
    public final Iterator<EventBean> iterator()
    {
        return new TimeWindowIterator(window);
    }

    /**
     * Returns the oldest timestamp in the collection if there is at least one entry,
     * else it returns null if the window is empty.
     * @return null if empty, oldest timestamp if not empty
     */
    public final Long getOldestTimestamp()
    {
        return oldestTimestamp;
    }

    /**
     * Returns true if the window is currently empty.
     * @return true if empty, false if not
     */
    public final boolean isEmpty()
    {
        return window.isEmpty();
    }

    protected Map<EventBean, LinkedList<EventBean>> getReverseIndex()
    {
        return reverseIndex;
    }
}
