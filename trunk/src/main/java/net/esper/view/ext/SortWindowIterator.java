package net.esper.view.ext;

import net.esper.event.EventBean;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.NoSuchElementException;

/**
 * Iterator for use by {@link SortWindowView}.
 */
public final class SortWindowIterator implements Iterator<EventBean>
{
    private final SortedMap<Object, LinkedList<EventBean>> window;

    private final Iterator<Object> keyIterator;
    private Iterator<EventBean> currentListIterator;

    /**
     * Ctor.
     * @param window - sorted map with events
     */
    public SortWindowIterator(SortedMap<Object, LinkedList<EventBean>> window)
    {
        this.window = window;
        keyIterator = window.keySet().iterator();
        if (keyIterator.hasNext())
        {
            Object initialKey = (Object) keyIterator.next();
            currentListIterator = window.get(initialKey).iterator();
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
                Object nextKey = (Object) keyIterator.next();
                currentListIterator = window.get(nextKey).iterator();
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
