package net.esper.collection;

import net.esper.event.EventBean;

import java.util.Iterator;

/**
 * Iterator for reading and transforming a source event iterator.
 * <p>
 * Works with a {@link TransformEventMethod} as the transformation method.
 */
public class TransformEventIterator implements Iterator<EventBean>
{
    private Iterator<EventBean> sourceIterator;
    private TransformEventMethod transformEventMethod;

    /**
     * Ctor.
     * @param sourceIterator is the source event iterator
     * @param transformEventMethod is the method to transform each event
     */
    public TransformEventIterator(Iterator<EventBean> sourceIterator, TransformEventMethod transformEventMethod)
    {
        this.sourceIterator = sourceIterator;
        this.transformEventMethod = transformEventMethod;
    }

    public boolean hasNext()
    {
        if (!sourceIterator.hasNext())
        {
            return false;
        }
        return true;
    }

    public EventBean next()
    {
        EventBean event = sourceIterator.next();
        EventBean resultEvent = transformEventMethod.transform(event);
        return resultEvent;
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
