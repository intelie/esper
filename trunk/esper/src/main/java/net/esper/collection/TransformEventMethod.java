package net.esper.collection;

import net.esper.event.EventBean;
import net.esper.eql.core.ResultSetProcessor;

import java.util.Iterator;

/**
 * Interface that transforms one event into another event, for use with {@link TransformEventIterator}.
 */
public interface TransformEventMethod
{
    /**
     * Transform event returning the transformed event.
     * @param event to transform
     * @return transformed event
     */
    public EventBean transform(EventBean event);
}
