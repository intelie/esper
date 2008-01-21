package net.esper.eql.core;

import net.esper.collection.*;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * // TODO: fix javadoc
 * Result set processor for the simplest case: no aggregation functions used in the select clause, and no group-by.
 * <p>
 * The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
 */
public class ResultSetProcessorHandThrough implements ResultSetProcessor
{
    private static final Log log = LogFactory.getLog(ResultSetProcessorHandThrough.class);

    private final SelectExprProcessor selectExprProcessor;
    private final Set<MultiKey<EventBean>> emptyRowSet = new HashSet<MultiKey<EventBean>>();

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * a row per group even if groups didn't change
     */
    public ResultSetProcessorHandThrough(SelectExprProcessor selectExprProcessor)
    {
        this.selectExprProcessor = selectExprProcessor;
    }

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, boolean isSynthesize)
    {
        EventBean[] selectOldEvents;
        EventBean[] selectNewEvents;

        selectOldEvents = getSelectEventsNoHaving(selectExprProcessor, oldEvents, false, isSynthesize);
        selectNewEvents = getSelectEventsNoHaving(selectExprProcessor, newEvents, true, isSynthesize);

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData, boolean isSynthesize)
    {
        EventBean[] selectOldEvents = getSelectEventsNoHaving(selectExprProcessor, oldData, false, isSynthesize);
        EventBean[] selectNewEvents = getSelectEventsNoHaving(selectExprProcessor, newData, true, isSynthesize);

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * @param exprProcessor - processes each input event and returns output event
     * @param events - input events
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectEventsNoHaving(SelectExprProcessor exprProcessor, EventBean[] events, boolean isNewData, boolean isSynthesize)
    {
        if (events == null)
        {
            return null;
        }

        EventBean[] result = new EventBean[events.length];

        EventBean[] eventsPerStream = new EventBean[1];
        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];

            // Wildcard select case
            if(exprProcessor == null)
            {
                result[i] = events[i];
            }
            else
            {
                result[i] = exprProcessor.process(eventsPerStream, isNewData, isSynthesize);
            }
        }

        return result;
    }

    /**
     * Applies the last/all event output limit clause.
     * @param eventSet to output
     * @param isOutputLimitLastOnly - flag to indicate output all versus output last
     * @return events to output
     */
    protected static Set<MultiKey<EventBean>> applyOutputLimit(Set<MultiKey<EventBean>> eventSet, boolean isOutputLimitLastOnly)
    {
        if(isOutputLimitLastOnly && eventSet != null && !eventSet.isEmpty())
        {
            Object[] events = eventSet.toArray();
            Set<MultiKey<EventBean>> resultSet = new LinkedHashSet<MultiKey<EventBean>>();
            resultSet.add((MultiKey<EventBean>)events[events.length - 1]);
            return resultSet;
        }
        else
        {
            return eventSet;
        }
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * @param exprProcessor - processes each input event and returns output event
     * @param events - input events
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectEventsNoHaving(SelectExprProcessor exprProcessor, Set<MultiKey<EventBean>> events, boolean isNewData, boolean isSynthesize)
    {
        int length = events.size();
        if (length == 0)
        {
            return null;
        }

        EventBean[] result = new EventBean[length];
        int count = 0;
        for (MultiKey<EventBean> key : events)
        {
            EventBean[] eventsPerStream = key.getArray();
            result[count] = exprProcessor.process(eventsPerStream, isNewData, isSynthesize);
            count++;
        }

        return result;
    }

    public Iterator<EventBean> getIterator(Viewable parent)
    {
        // Return an iterator that gives row-by-row a result
        return new TransformEventIterator(parent.iterator(), new ResultSetProcessorHandThrough.ResultSetSimpleTransform(this));
    }

    public Iterator<EventBean> getIterator(Set<MultiKey<EventBean>> joinSet)
    {
        // Process join results set as a regular join, includes sorting and having-clause filter
        Pair<EventBean[], EventBean[]> result = processJoinResult(joinSet, emptyRowSet, true);
        return new ArrayEventIterator(result.getFirst());
    }

    public void clear()
    {
        // No need to clear state, there is no state held
    }

    /**
     * Method to transform an event based on the select expression.
     */
    public static class ResultSetSimpleTransform implements TransformEventMethod
    {
        private final ResultSetProcessorHandThrough resultSetProcessor;
        private final EventBean[] newData;

        /**
         * Ctor.
         * @param resultSetProcessor is applying the select expressions to the events for the transformation
         */
        public ResultSetSimpleTransform(ResultSetProcessorHandThrough resultSetProcessor) {
            this.resultSetProcessor = resultSetProcessor;
            newData = new EventBean[1];
        }

        public EventBean transform(EventBean event)
        {
            newData[0] = event;
            Pair<EventBean[], EventBean[]> pair = resultSetProcessor.processViewResult(newData, null, true);
            return pair.getFirst()[0];
        }
    }
}
