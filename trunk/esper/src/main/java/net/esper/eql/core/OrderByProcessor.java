package net.esper.eql.core;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;

/**
 * A processor for ordering output events according to the order
 * specified in the order-by clause. 
 */
public interface OrderByProcessor {

	/**
	 * Sort the output events. If the order-by processor needs group-by
	 * keys to evaluate the expressions in the order-by clause, these will
	 * be computed from the generating events.
	 * @param outgoingEvents - the events to be sorted
	 * @param generatingEvents - the events that generated the output events (each event has a corresponding array of generating events per different event streams)
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
	 * @return an array containing the output events in sorted order
	 */
	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, boolean isNewData);

	/**
	 * Sort the output events, using the provided group-by keys for 
	 * evaluating grouped aggregation functions, and avoiding the cost of
	 * recomputing the keys.
	 * @param outgoingEvents - the events to sort
	 * @param generatingEvents - the events that generated the output events (each event has a corresponding array of generating events per different event streams)
	 * @param groupByKeys - the keys to use for determining the group-by group of output events 
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
	 * @return an array containing the output events in sorted order
	 */
	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, MultiKeyUntyped[] groupByKeys, boolean isNewData);
}	
