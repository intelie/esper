/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.collection.*;
import net.esper.eql.agg.AggregationService;
import net.esper.eql.expression.ExprNode;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.Viewable;

import java.util.Set;
import java.util.Iterator;

/**
 * Result set processor for the case: aggregation functions used in the select clause, and no group-by,
 * and all properties in the select clause are under an aggregation function.
 * <p>
 * This processor does not perform grouping, every event entering and leaving is in the same group.
 * Produces one old event and one new event row every time either at least one old or new event is received.
 * Aggregation state is simply one row holding all the state.
 */
public class ResultSetProcessorRowForAll implements ResultSetProcessor
{
    private final SelectExprProcessor selectExprProcessor;
    private final AggregationService aggregationService;
    private final ExprNode optionalHavingNode;

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param aggregationService - handles aggregation
     * @param optionalHavingNode - having clause expression node
     */
    public ResultSetProcessorRowForAll(SelectExprProcessor selectExprProcessor,
                                       AggregationService aggregationService,
                                       ExprNode optionalHavingNode)
    {
        this.selectExprProcessor = selectExprProcessor;
        this.aggregationService = aggregationService;
        this.optionalHavingNode = optionalHavingNode;
    }

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, boolean isSynthesize)
    {
        EventBean[] selectOldEvents;
        EventBean[] selectNewEvents;

        selectOldEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, false, isSynthesize);

        if (!newEvents.isEmpty())
        {
            // apply new data to aggregates
            for (MultiKey<EventBean> events : newEvents)
            {
                aggregationService.applyEnter(events.getArray(), null);
            }
        }
        if (!oldEvents.isEmpty())
        {
            // apply old data to aggregates
            for (MultiKey<EventBean> events : oldEvents)
            {
                aggregationService.applyLeave(events.getArray(), null);
            }
        }

        selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, true, isSynthesize);

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }
        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData, boolean isSynthesize)
    {
        EventBean[] selectOldEvents = null;
        EventBean[] selectNewEvents = null;

        selectOldEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, false, isSynthesize);

        EventBean[] eventsPerStream = new EventBean[1];
        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];
                aggregationService.applyEnter(eventsPerStream, null);
            }
        }
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];
                aggregationService.applyLeave(eventsPerStream, null);
            }
        }

        // generate new events using select expressions
        selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, true, isSynthesize);

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    private static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, ExprNode optionalHavingNode, boolean isNewData, boolean isSynthesize)
    {
        // Since we are dealing with strictly aggregation nodes, there are no events required for evaluating
        EventBean event = exprProcessor.process(null, isNewData, isSynthesize);

        if (optionalHavingNode != null)
        {
            Boolean result = (Boolean) optionalHavingNode.evaluate(null, isNewData);
            if (!result)
            {
                return null;
            }
        }

        // The result is always a single row
        return new EventBean[] {event};
    }

    public Iterator<EventBean> getIterator(Viewable parent)
    {
        EventBean[] selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, true, true);
        if (selectNewEvents == null)
        {
            return new NullIterator();
        }
        return new SingleEventIterator(selectNewEvents[0]);
    }

    public Iterator<EventBean> getIterator(Set<MultiKey<EventBean>> joinSet)
    {
        EventBean[] result = getSelectListEvents(selectExprProcessor, optionalHavingNode, true, true);
        return new ArrayEventIterator(result);
    }

    public void clear()
    {
        aggregationService.clearResults();
    }        
}
