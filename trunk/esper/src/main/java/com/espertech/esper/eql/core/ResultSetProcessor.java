package com.espertech.esper.eql.core;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.Viewable;

import java.util.Iterator;
import java.util.Set;

/**
 * Processor for result sets coming from 2 sources. First, out of a simple view (no join).
 * And second, out of a join of event streams. The processor must apply the select-clause, grou-by-clause and having-clauses
 * as supplied. It must state what the event type of the result rows is.
 */
public interface ResultSetProcessor
{
    /**
     * Returns the event type of processed results.
     * @return event type of the resulting events posted by the processor.
     */
    public EventType getResultEventType();

    /**
     * For use by views posting their result, process the event rows that are entered and removed (new and old events).
     * Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
     * old events as specified.
     * @param newData - new events posted by view
     * @param oldData - old events posted by view
     * @param isSynthesize - set to true to indicate that synthetic events are required for an iterator result set
     * @return pair of new events and old events
     */
    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData, boolean isSynthesize);

    /**
     * For use by joins posting their result, process the event rows that are entered and removed (new and old events).
     * Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
     * old events as specified.
     * @param newEvents - new events posted by join
     * @param oldEvents - old events posted by join
     * @param isSynthesize - set to true to indicate that synthetic events are required for an iterator result set
     * @return pair of new events and old events
     */
    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, boolean isSynthesize);

    /**
     * Returns the iterator implementing the group-by and aggregation and order-by logic
     * specific to each case of use of these construct.
     * @param parent is the parent view iterator
     * @return event iterator
     */
    public Iterator<EventBean> getIterator(Viewable parent);

    /**
     * Returns the iterator for iterating over a join-result.
     * @param joinSet is the join result set
     * @return iterator over join results
     */
    public Iterator<EventBean> getIterator(Set<MultiKey<EventBean>> joinSet);

    /**
     * Clear out current state.
     */
    public void clear();
}
