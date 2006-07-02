package net.esper.eql.expression;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

/**
 * Result set processor for the case: aggregation functions used in the select clause, and no group-by,
 * and not all of the properties in the select clause are under an aggregation function.
 * <p>
 * This processor does not perform grouping, every event entering and leaving is in the same group.
 * The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
 * Aggregation state is simply one row holding all the state.
 */
public class ResultSetProcessorAggregateAll implements ResultSetProcessor
{
    private final SelectExprProcessor selectExprProcessor;
    private final OrderByProcessor orderByProcessor;
    private final AggregationService aggregationService;
    private final ExprNode optionalHavingNode;
    private final boolean isOutputLimiting;
    private final boolean isOutputLimitLastOnly;

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting the outgoing events according to the order-by clause
     * @param aggregationService - handles aggregation
     * @param optionalHavingNode - having clause expression node
     * @param isSorting - true if the output needs to be sorted
     */
    public ResultSetProcessorAggregateAll(SelectExprProcessor selectExprProcessor,
                                          OrderByProcessor orderByProcessor,
                                          AggregationService aggregationService,
                                          ExprNode optionalHavingNode,
                                          boolean isOutputLimiting,
                                          boolean isOutputLimitLastOnly)
    {
        this.selectExprProcessor = selectExprProcessor;
        this.orderByProcessor = orderByProcessor;
        this.aggregationService = aggregationService;
        this.optionalHavingNode = optionalHavingNode;
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

        if (optionalHavingNode == null)
        {
            selectOldEvents = ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly);
        }
        else
        {
            selectOldEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, oldEvents, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        if (!oldEvents.isEmpty())
        {
            // apply old data to aggregates
            for (MultiKey<EventBean> events : oldEvents)
            {
                aggregationService.applyLeave(events.getArray(), null);
            }
        }

        if (!newEvents.isEmpty())
        {
            // apply new data to aggregates
            for (MultiKey<EventBean> events : newEvents)
            {
                aggregationService.applyEnter(events.getArray(), null);
            }
        }

        if (optionalHavingNode == null)
        {
            selectNewEvents = ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly);
        }
        else
        {
            selectNewEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, newEvents, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }
        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        EventBean[] selectOldEvents = null;
        EventBean[] selectNewEvents = null;

        // generate old events using select expressions
        if (optionalHavingNode == null)
        {
            selectOldEvents = ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly);
        }
        // generate old events using having then select
        else
        {
            selectOldEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, oldData, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        EventBean[] eventsPerStream = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];
                aggregationService.applyLeave(eventsPerStream, null);
            }
        }

        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];
                aggregationService.applyEnter(eventsPerStream, null);
            }
        }

        // generate new events using select expressions
        if (optionalHavingNode == null)
        {
            selectNewEvents = ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly);
        }
        else
        {
            selectNewEvents = getSelectListEvents(selectExprProcessor, orderByProcessor, newData, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    private static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, ExprNode optionalHavingNode, boolean isOutputLimiting, boolean isOutputLimitLastOnly)
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

            Boolean passesHaving = (Boolean) optionalHavingNode.evaluate(eventsPerStream);
            if (!passesHaving)
            {
                continue;
            }

            result.add(exprProcessor.process(eventsPerStream));
            if(orderByProcessor != null)
            {
            	eventGenerators.add(new EventBean[] { events[i] });
            }
        }

        if (result.size() > 0)
        {
        	if(orderByProcessor != null)
        	{
                return orderByProcessor.sort(result.toArray(new EventBean[0]), eventGenerators.toArray(new EventBean[0][]));
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

    private static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, Set<MultiKey<EventBean>> events, ExprNode optionalHavingNode, boolean isOutputLimiting, boolean isOutputLimitLastOnly)
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

            Boolean passesHaving = (Boolean) optionalHavingNode.evaluate(eventsPerStream);
            if (!passesHaving)
            {
                continue;
            }

            EventBean resultEvent = exprProcessor.process(eventsPerStream);
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
                return orderByProcessor.sort(result.toArray(new EventBean[0]), eventGenerators.toArray(new EventBean[0][]));
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
