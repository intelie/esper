package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.eql.core.AggregationResultFuture;

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
    public void applyEnter(EventBean[] eventsPerStream, MultiKey optionalGroupKeyPerRow);

    /**
     * Apply events as leaving a window (old events).
     * @param eventsPerStream - events for each stream entering window
     * @param optionalGroupKeyPerRow - can be null if grouping without keys is desired, else the keys
     * to use for grouping, each distinct key value results in a new row of aggregation state.
     */
    public void applyLeave(EventBean[] eventsPerStream, MultiKey optionalGroupKeyPerRow);

    /**
     * Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
     * @param groupKey - key identify the row of aggregation states
     */
    public void setCurrentRow(MultiKey groupKey);
}
