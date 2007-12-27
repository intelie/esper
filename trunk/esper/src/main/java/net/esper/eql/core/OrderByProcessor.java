/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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

    /**
     * Returns the sort key for a given row.
     * @param eventsPerStream is the row consisting of one event per stream
     * @param isNewData is true for new data
     * @return sort key
     */
    public MultiKeyUntyped getSortKey(EventBean[] eventsPerStream, boolean isNewData);

    /**
     * Returns the sort key for a each row where a row is a single event (no join, single stream).
     * @param generatingEvents is the rows consisting of one event per row
     * @param isNewData is true for new data
     * @return sort key for each row
     */
    public MultiKeyUntyped[] getSortKeyPerRow(EventBean[] generatingEvents, boolean isNewData);

    /**
     * Sort a given array of outgoing events using the sort keys returning a sorted outgoing event array.
     * @param outgoingEvents is the events to sort
     * @param orderKeys is the keys to sort by
     * @return sorted events
     */
    public EventBean[] sort(EventBean[] outgoingEvents, MultiKeyUntyped[] orderKeys);
}
