/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.filter;

import net.esper.event.EventBean;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of the filter service interface.
 * Does not allow the same filter callback to be added more then once.
 */
public final class FilterServiceImpl implements FilterService
{
    private final EventTypeIndexBuilder indexBuilder;
    private final EventTypeIndex eventTypeIndex;
    private final AtomicInteger numEventsEvaluated = new AtomicInteger();

    /**
     * Constructor.
     */
    protected FilterServiceImpl()
    {
        eventTypeIndex = new EventTypeIndex();
        indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);
    }

    public final void add(FilterValueSet filterValueSet, FilterHandle filterCallback)
    {
        indexBuilder.add(filterValueSet, filterCallback);
    }

    public final void remove(FilterHandle filterCallback)
    {
        indexBuilder.remove(filterCallback);
    }

    public final void evaluate(EventBean eventBean, Collection<FilterHandle> matches)
    {
        numEventsEvaluated.incrementAndGet();

        // Finds all matching filters and return their callbacks
        eventTypeIndex.matchEvent(eventBean, matches);
    }

    public final int getNumEventsEvaluated()
    {
        return numEventsEvaluated.get();
    }
}
