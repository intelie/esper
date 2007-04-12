/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.event.EventType;

/**
 * Interface for processors of select-clause items, implementors are computing results based on matching events.
 */
public interface SelectExprProcessor
{
    /**
     * Returns the event type that represents the select-clause items.
     * @return event type representing select-clause items
     */
    public EventType getResultEventType();

    /**
     * Computes the select-clause results and returns an event of the result event type that contains, in it's
     * properties, the selected items.
     * @param eventsPerStream - is per stream the event
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return event with properties containing selected items
     */
    public EventBean process(EventBean[] eventsPerStream, boolean isNewData);
}