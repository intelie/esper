package net.esper.eql.expression;

import net.esper.collection.MultiKey;
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
	 * @return an array containing the output events in sorted order
	 */
	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents);

	/**
	 * Sort the output events, using the provided group-by keys for 
	 * evaluating grouped aggregation functions, and avoiding the cost of
	 * recomputing the keys.
	 * @param outgoingEvents - the events to sort
	 * @param generatingEvents - the events that generated the output events (each event has a corresponding array of generating events per different event streams)
	 * @param groupByKeys - the keys to use for determining the group-by group of output events 
	 * @return an array containing the output events in sorted order
	 */
	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, MultiKey[] groupByKeys);
}	
