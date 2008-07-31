package com.espertech.esper.epl.core;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

/**
 * Sorter and row limiter in one: sorts using a sorter and row limits 
 */
public class OrderByProcessorOrderedLimit implements OrderByProcessor
{
    private final OrderByProcessorImpl orderByProcessor;
    private final OrderByProcessorRowLimit orderByProcessorRowLimit;

    /**
     * Ctor.
     * @param orderByProcessor the sorter
     * @param orderByProcessorRowLimit the row limiter
     */
    public OrderByProcessorOrderedLimit(OrderByProcessorImpl orderByProcessor, OrderByProcessorRowLimit orderByProcessorRowLimit)
    {
        this.orderByProcessor = orderByProcessor;
        this.orderByProcessorRowLimit = orderByProcessorRowLimit;
    }

    public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, boolean isNewData)
    {
        EventBean[] sorted = orderByProcessor.sort(outgoingEvents, generatingEvents, isNewData);
        return orderByProcessorRowLimit.applyLimit(sorted);
    }

    public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, MultiKeyUntyped[] groupByKeys, boolean isNewData)
    {
        EventBean[] sorted = orderByProcessor.sort(outgoingEvents, generatingEvents, groupByKeys, isNewData);
        return orderByProcessorRowLimit.applyLimit(sorted);
    }

    public MultiKeyUntyped getSortKey(EventBean[] eventsPerStream, boolean isNewData)
    {
        return orderByProcessor.getSortKey(eventsPerStream, isNewData);
    }

    public MultiKeyUntyped[] getSortKeyPerRow(EventBean[] generatingEvents, boolean isNewData)
    {
        return orderByProcessor.getSortKeyPerRow(generatingEvents, isNewData);
    }

    public EventBean[] sort(EventBean[] outgoingEvents, MultiKeyUntyped[] orderKeys)
    {
        EventBean[] sorted = orderByProcessor.sort(outgoingEvents, orderKeys);
        return orderByProcessorRowLimit.applyLimit(sorted);
    }
}
