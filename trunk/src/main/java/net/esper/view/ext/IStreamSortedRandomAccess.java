package net.esper.view.ext;

import net.esper.view.window.RandomAccessByIndex;
import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Iterator;

public class IStreamSortedRandomAccess implements RandomAccessByIndex
{
    private TreeMap<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents;
    private int currentSize;

    private Iterator<LinkedList<EventBean>> iterator;
    private EventBean[] cache;
    private int cacheFilledTo;

    public void refresh(TreeMap<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents, int currentSize, int maxSize)
    {
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
