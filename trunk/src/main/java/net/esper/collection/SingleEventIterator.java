package net.esper.collection;

import net.esper.event.EventBean;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A utility class for an iterator that has one element.
 */
public class SingleEventIterator implements Iterator<EventBean>
{
    private EventBean eventBean;
    private boolean hasMore;

    /**
     * Constructor, takes the single event to iterate over as a parameter.
     * The single event can be null indicating that there are no more elements.
     * @param eventBean single bean that the iterator returns, or null for an empty iterator
     */
    public SingleEventIterator(EventBean eventBean)
    {
        if (eventBean == null)
        {
            hasMore = false;
        }
        else
        {
            this.eventBean = eventBean;
            this.hasMore = true;
        }
    }

    public boolean hasNext()
    {
        return hasMore;
    }

    public EventBean next()
    {
        if (hasMore == false)
        {
            throw new NoSuchElementException();
        }
        hasMore = false;
        return eventBean;
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}

