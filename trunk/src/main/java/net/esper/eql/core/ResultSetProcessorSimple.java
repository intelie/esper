package net.esper.eql.core;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.eql.expression.ExprNode;

/**
 * Result set processor for the simplest case: no aggregation functions used in the select clause, and no group-by.
 * <p>
 * The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
 */
public class ResultSetProcessorSimple implements ResultSetProcessor
{
    private static final Log log = LogFactory.getLog(ResultSetProcessorSimple.class);

    private final SelectExprProcessor selectExprProcessor;
    private final boolean isOutputLimiting;
    private final boolean isOutputLimitLastOnly;
    private final OrderByProcessor orderByProcessor;
    private final ExprNode optionalHavingExpr;

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting the outgoing events according to the order-by clause
     * @param optionalHavingNode - having clause expression node
     * @param isOutputLimiting - true to indicate we are output limiting and must keep producing
     * a row per group even if groups didn't change
     * @param isOutputLimitLastOnly - true if output limiting and interested in last event only
     */
    public ResultSetProcessorSimple(SelectExprProcessor selectExprProcessor,
                                    OrderByProcessor orderByProcessor,
                                    ExprNode optionalHavingNode,
                                    boolean isOutputLimiting,
                                    boolean isOutputLimitLastOnly)
    {
        this.selectExprProcessor = selectExprProcessor;
        this.orderByProcessor = orderByProcessor;
        this.optionalHavingExpr = optionalHavingNode;
        this.isOutputLimiting = isOutputLimiting;
        this.isOutputLimitLastOnly = isOutputLimitLastOnly;
    }

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        EventBean[] selectOldEvents = null;
        EventBean[] selectNewEvents = null;

        if (optionalHavingExpr == null)
        {
            selectOldEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly, false);
            selectNewEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly, true);
        }
        else
        {
            selectOldEvents = getSelectEventsHaving(selectExprProcessor, orderByProcessor, oldEvents, optionalHavingExpr, isOutputLimiting, isOutputLimitLastOnly, false);
            selectNewEvents = getSelectEventsHaving(selectExprProcessor, orderByProcessor, newEvents, optionalHavingExpr, isOutputLimiting, isOutputLimitLastOnly, true);
        }

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        EventBean[] selectOldEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly, false);
        EventBean[] selectNewEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly, true);

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * @param exprProcessor - processes each input event and returns output event
     * @param orderByProcessor - orders the outgoing events according to the order-by clause
     * @param events - input events
     * @param isOutputLimiting - true to indicate that we limit output
     * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectEventsNoHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, boolean isOutputLimiting, boolean isOutputLimitLastOnly, boolean isNewData)
    {
        if (isOutputLimiting)
        {
            events = applyOutputLimit(events, isOutputLimitLastOnly);
        }

        if (events == null)
        {
            return null;
        }

        EventBean[] result = new EventBean[events.length];
        EventBean[][] eventGenerators = null;
        if(orderByProcessor != null)
        {
            eventGenerators = new EventBean[events.length][];
        }

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
                result[i] = exprProcessor.process(eventsPerStream, isNewData);
            }

            if(orderByProcessor != null)
            {
                  eventGenerators[i] = new EventBean[] {events[i]};
            }
        }

        if(orderByProcessor != null)
        {
            return orderByProcessor.sort(result, eventGenerators, isNewData);
        }
        else
        {
            return result;
        }
    }

    /**
     * Applies the last/all event output limit clause.
     * @param events to output
     * @param isOutputLimitLastOnly - flag to indicate output all versus output last
     * @return events to output
     */
    protected static EventBean[] applyOutputLimit(EventBean[] events, boolean isOutputLimitLastOnly)
    {
        if(isOutputLimitLastOnly && events != null && events.length > 0)
        {
            return new EventBean[] {events[events.length - 1]};
        }
        else
        {
            return events;
        }
    }

    /**
     * Applies the last/all event output limit clause.
     * @param eventSet to output
     * @param isOutputLimitLastOnly - flag to indicate output all versus output last
     * @return events to output
     */
    protected static Set<MultiKey<EventBean>> applyOutputLimit(Set<MultiKey<EventBean>> eventSet, boolean isOutputLimitLastOnly)
    {
        if(isOutputLimitLastOnly && eventSet != null && eventSet.size() > 0)
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
     * @param orderByProcessor - for sorting output events according to the order-by clause
     * @param events - input events
     * @param isOutputLimiting - true to indicate that we limit output
     * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectEventsNoHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, Set<MultiKey<EventBean>> events, boolean isOutputLimiting, boolean isOutputLimitLastOnly, boolean isNewData)
    {
        if (isOutputLimiting)
        {
            events = applyOutputLimit(events, isOutputLimitLastOnly);
        }

        int length = events.size();
        if (length == 0)
        {
            return null;
        }

        EventBean[] result = new EventBean[length];
        EventBean[][] eventGenerators = null;
        if(orderByProcessor != null)
        {
            eventGenerators = new EventBean[length][];
        }

        int count = 0;
        for (MultiKey<EventBean> key : events)
        {
            EventBean[] eventsPerStream = key.getArray();
            result[count] = exprProcessor.process(eventsPerStream, isNewData);
            if(orderByProcessor != null)
            {
                eventGenerators[count] = eventsPerStream;
            }
            count++;
        }

        if(orderByProcessor != null)
        {
            return orderByProcessor.sort(result, eventGenerators, isNewData);
        }
        else
        {
            return result;
        }
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * <p>
     * Also applies a having clause.
     * @param exprProcessor - processes each input event and returns output event
     * @param orderByProcessor - for sorting output events according to the order-by clause
     * @param events - input events
     * @param optionalHavingNode - supplies the having-clause expression
     * @param isOutputLimiting - true to indicate that we limit output
     * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectEventsHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, ExprNode optionalHavingNode, boolean isOutputLimiting, boolean isOutputLimitLastOnly, boolean isNewData)
    {
        if (isOutputLimiting)
        {
            events = ResultSetProcessorSimple.applyOutputLimit(events, isOutputLimitLastOnly);
        }

        if (events == null)
        {
            return null;
        }

        LinkedList<EventBean> result = new LinkedList<EventBean>();
        List<EventBean[]> eventGenerators = null;
        if(orderByProcessor != null)
        {
            eventGenerators = new ArrayList<EventBean[]>();
        }

        EventBean[] eventsPerStream = new EventBean[1];
        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];

            Boolean passesHaving = (Boolean) optionalHavingNode.evaluate(eventsPerStream, isNewData);
            if (!passesHaving)
            {
                continue;
            }

            result.add(exprProcessor.process(eventsPerStream, isNewData));
            if(orderByProcessor != null)
            {
                eventGenerators.add(new EventBean[] { events[i] });
            }
        }

        if (result.size() > 0)
        {
            if(orderByProcessor != null)
            {
                return orderByProcessor.sort(result.toArray(new EventBean[0]), eventGenerators.toArray(new EventBean[0][]), isNewData);
            }
            else
            {
                return result.toArray(new EventBean[0]);
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * <p>
     * Also applies a having clause.
     * @param exprProcessor - processes each input event and returns output event
     * @param orderByProcessor - for sorting output events according to the order-by clause
     * @param events - input events
     * @param optionalHavingNode - supplies the having-clause expression
     * @param isOutputLimiting - true to indicate that we limit output
     * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectEventsHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, Set<MultiKey<EventBean>> events, ExprNode optionalHavingNode, boolean isOutputLimiting, boolean isOutputLimitLastOnly, boolean isNewData)
    {
        if (isOutputLimiting)
        {
            events = ResultSetProcessorSimple.applyOutputLimit(events, isOutputLimitLastOnly);
        }

        LinkedList<EventBean> result = new LinkedList<EventBean>();
        List<EventBean[]> eventGenerators = null;
        if(orderByProcessor != null)
        {
            eventGenerators = new ArrayList<EventBean[]>();
        }

        for (MultiKey<EventBean> key : events)
        {
            EventBean[] eventsPerStream = key.getArray();

            Boolean passesHaving = (Boolean) optionalHavingNode.evaluate(eventsPerStream, isNewData);
            if (!passesHaving)
            {
                continue;
            }

            EventBean resultEvent = exprProcessor.process(eventsPerStream, isNewData);
            result.add(resultEvent);
            if(orderByProcessor != null)
            {
                eventGenerators.add(eventsPerStream);
            }
        }

        if (result.size() > 0)
        {
            if(orderByProcessor != null)
            {
                return orderByProcessor.sort(result.toArray(new EventBean[0]), eventGenerators.toArray(new EventBean[0][]), isNewData);
            }
            else
            {
                return result.toArray(new EventBean[0]);
            }
        }
        else
        {
            return null;
        }
    }
}
