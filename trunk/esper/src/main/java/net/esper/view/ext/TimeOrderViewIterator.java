package net.esper.view.ext;

import net.esper.event.EventBean;
import net.esper.collection.MultiKeyUntyped;
import net.esper.collection.Pair;

import java.util.*;

/**
 * Iterator for use by {@link net.esper.view.ext.TimeOrderView}.
 */
public final class TimeOrderViewIterator implements Iterator<EventBean>
{
    private final SortedMap<Long, ArrayList<EventBean>> window;

    private final Iterator<Long> keyIterator;
    private Iterator<EventBean> currentListIterator;

    /**
     * Ctor.
     * @param window - sorted map with events
     */
    public TimeOrderViewIterator(SortedMap<Long, ArrayList<EventBean>> window)
    {
        this.window = window;
        keyIterator = window.keySet().iterator();
        if (keyIterator.hasNext())
        {
            Long key = keyIterator.next();

            ArrayList<EventBean> list = window.get(key);
            while((list.isEmpty()) && (keyIterator.hasNext()))
            {
                key = keyIterator.next();
                list = window.get(key);
            }
            currentListIterator = list.iterator();
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
                Long key = keyIterator.next();

                ArrayList<EventBean> list = window.get(key);
                while((list.isEmpty()) && (keyIterator.hasNext()))
                {
                    key = keyIterator.next();
                    list = window.get(key);
                }
                currentListIterator = list.iterator();
            }
        }

        return eventBean;
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

    public final void remove()
    {
        throw new UnsupportedOperationException();
    }
}
