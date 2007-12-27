package net.esper.eql.join.table;

import net.esper.event.EventBean;
import net.esper.collection.MultiKeyUntyped;

import java.util.*;

/**
 * Iterator for use by {@link PropertyIndexedEventTable}.
 */
public final class PropertyIndexedEventTableIterator implements Iterator<EventBean>
{
    private final Map<MultiKeyUntyped, Set<EventBean>> window;

    private final Iterator<MultiKeyUntyped> keyIterator;
    private Iterator<EventBean> currentListIterator;

    /**
     * Ctor.
     * @param window - sorted map with events
     */
    public PropertyIndexedEventTableIterator(Map<MultiKeyUntyped, Set<EventBean>> window)
    {
        this.window = window;
        keyIterator = window.keySet().iterator();
        if (keyIterator.hasNext())
        {
            MultiKeyUntyped initialKey = keyIterator.next();
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
                MultiKeyUntyped nextKey = keyIterator.next();
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
