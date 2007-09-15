package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;
import net.esper.view.Viewable;

import java.util.Set;
import java.util.Iterator;

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
     * @return pair of new events and old events
     */
    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData);

    /**
     * For use by joins posting their result, process the event rows that are entered and removed (new and old events).
     * Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
     * old events as specified.
     * @param newEvents - new events posted by join
     * @param oldEvents - old events posted by join
     * @return pair of new events and old events
     */
    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents);

    /**
     * Returns the iterator implementing the group-by and aggregation and order-by logic
     * specific to each case of use of these construct.
     * @param parent is the parent view iterator
     * @return event iterator
     */
    public Iterator<EventBean> getIterator(Viewable parent);
}
