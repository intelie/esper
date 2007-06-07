/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.Iterator;

/**
 * Interface to iterate over events.
 */
public interface EPIterable
{
    /**
     * Returns an iterator over events.
     * @return event iterator
     */
    public Iterator<EventBean> iterator();

    /**
     * Returns the type of events the iterable returns.
     * @return event type of events the iterator returns
     */
    public EventType getEventType();
}

