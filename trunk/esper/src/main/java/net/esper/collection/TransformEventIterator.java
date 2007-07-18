/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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