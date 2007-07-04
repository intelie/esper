package net.esper.view.ext;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;
import net.esper.view.window.RandomAccessByIndex;
import net.esper.view.window.RandomAccessByIndexObserver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Provides random access into a sorted-window's data.
 */
public class IStreamSortedRandomAccess implements RandomAccessByIndex
{
    private final RandomAccessByIndexObserver updateObserver;

    private TreeMap<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents;
    private int currentSize;

    private Iterator<LinkedList<EventBean>> iterator;
    private EventBean[] cache;
    private int cacheFilledTo;

    /**
     * Ctor.
     * @param updateObserver for indicating updates to
     */
    public IStreamSortedRandomAccess(RandomAccessByIndexObserver updateObserver)
    {
        this.updateObserver = updateObserver;
    }

    /**
     * Refreshes the random access data with the updated information.
     * @param sortedEvents is the sorted window contents
     * @param currentSize is the current size of the window
     * @param maxSize is the maximum size of the window
     */
    public void refresh(TreeMap<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents, int currentSize, int maxSize)
    {
        updateObserver.updated(this);
        this.sortedEvents = sortedEvents;
        this.currentSize = currentSize;

        this.iterator = null;
        this.cacheFilledTo = 0;
        if (cache == null)
        {
            cache = new EventBean[maxSize];
        }
    }

    public EventBean getNewData(int index)
    {
        if (iterator == null)
        {
            iterator = sortedEvents.values().iterator();
        }

        // if asking for more then the sorted window currently holds, return no data
        if (index >= currentSize)
        {
            return null;
        }

        // If we have it in cache, serve from cache
        if (index < cacheFilledTo)
        {
            return cache[index];
        }

        // Load more into cache
        while(true)
        {
            if (cacheFilledTo == currentSize)
            {
                break;
            }
            if (!iterator.hasNext())
            {
                break;
            }
            LinkedList<EventBean> events = iterator.next();
            for (EventBean event : events)
            {
                cache[cacheFilledTo] = event;
                cacheFilledTo++;
            }

            if (cacheFilledTo > index)
            {
                break;
            }
        }

        // If we have it in cache, serve from cache
        if (index <= cacheFilledTo)
        {
            return cache[index];
        }

        return null;
    }

    public EventBean getOldData(int index)
    {
        return null;
    }
}
