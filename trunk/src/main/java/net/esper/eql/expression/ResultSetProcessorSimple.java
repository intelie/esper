package net.esper.eql.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.event.EventType;

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

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting the outgoing events according to the order-by clause
     * @param isOutputLimiting - true to indicate we are output limiting and must keep producing
     * a row per group even if groups didn't change
     * @param isOutputLimitLastOnly - true if output limiting and interested in last event only
     */
    public ResultSetProcessorSimple(SelectExprProcessor selectExprProcessor,
                                    OrderByProcessor orderByProcessor,
                                    boolean isOutputLimiting,
                                    boolean isOutputLimitLastOnly)
    {
    	this.selectExprProcessor = selectExprProcessor;
    	this.orderByProcessor = orderByProcessor;
        this.isOutputLimiting = isOutputLimiting;
        this.isOutputLimitLastOnly = isOutputLimitLastOnly;
    }

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
    	EventBean[] selectOldEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly);
    	EventBean[] selectNewEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly);

    	return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
    {
    	EventBean[] selectOldEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly);
    	EventBean[] selectNewEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly);

    	return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    /**
     * Applies the select-clause to the given events returning the selected events. The number of events stays the
     * same, i.e. this method does not filter it just transforms the result set.
     * @param exprProcessor - processes each input event and returns output event
     * @param orderByProcessor - orders the outgoing events according to the order-by clause
     * @param events - input events
     * @param isSorting - true if outgoing events need to be sorted
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, boolean isOutputLimiting, boolean isOutputLimitLastOnly)
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
                result[i] = exprProcessor.process(eventsPerStream);
            }

            if(orderByProcessor != null)
            {
              	eventGenerators[i] = new EventBean[] {events[i]};
            }
        }

        if(orderByProcessor != null)
        {
        	return orderByProcessor.sort(result, eventGenerators);
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
     * @param orderByProcessor TODO
     * @param events - input events
     * @return output events, one for each input event
     */
    protected static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, Set<MultiKey<EventBean>> events, boolean isOutputLimiting, boolean isOutputLimitLastOnly)
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
            result[count] = exprProcessor.process(eventsPerStream);
            if(orderByProcessor != null)
            {
            	eventGenerators[count] = eventsPerStream;
            }
            count++;
        }

        if(orderByProcessor != null)
        {
        	return orderByProcessor.sort(result, eventGenerators);
        }
        else
        {
        	return result;
        }
    }
}
