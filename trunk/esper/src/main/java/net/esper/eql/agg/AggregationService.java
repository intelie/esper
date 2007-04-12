/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.agg;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;

/**
 * Service for maintaing aggregation state. Processes events entering (a window, a join etc,) and
 * events leaving. Answers questions about current aggrataion state for a given row.
 */
public interface AggregationService extends AggregationResultFuture
{
    /**
     * Apply events as entering a window (new events).
     * @param eventsPerStream - events for each stream entering window
     * @param optionalGroupKeyPerRow - can be null if grouping without keys is desired, else the keys
     * to use for grouping, each distinct key value results in a new row of aggregation state.
     */
    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow);

    /**
     * Apply events as leaving a window (old events).
     * @param eventsPerStream - events for each stream entering window
     * @param optionalGroupKeyPerRow - can be null if grouping without keys is desired, else the keys
     * to use for grouping, each distinct key value results in a new row of aggregation state.
     */
    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow);

    /**
     * Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
     * @param groupKey - key identify the row of aggregation states
     */
    public void setCurrentRow(MultiKeyUntyped groupKey);
}