package net.esper.collection;

import net.esper.event.EventBean;

import java.util.Iterator;

public class TransformEventIterator implements Iterator<EventBean>
{
    private Iterator<EventBean> sourceIterator;
    private TransformEventMethod transformEventMethod;

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
