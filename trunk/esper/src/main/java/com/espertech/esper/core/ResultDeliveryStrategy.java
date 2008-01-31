package com.espertech.esper.core;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.UniformPair;

/**
 * Strategy for use with {@link StatementResultService} to dispatch to a statement's subscriber
 * via method invocations.
 */
public interface ResultDeliveryStrategy
{
    /**
     * Execute the dispatch.
     * @param result is the insert and remove stream to indicate
     */
    public void execute(UniformPair<EventBean[]> result);
}
