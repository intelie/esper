/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.core;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.collection.ArrayEventIterator;
import com.espertech.esper.eql.agg.AggregationService;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.Viewable;

import java.util.*;

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
     * @param isOutputLimiting - true to indicate that we limit output
     * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
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

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, boolean isSynthesize)
    {
        EventBean[] selectOldEvents = null;
        EventBean[] selectNewEvents = null;

        if (optionalHavingNode == null)
        {
            selectOldEvents = ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly, false, isSynthesize);
        }
        else
        {
            selectOldEvents = ResultSetProcessorSimple.getSelectEventsHaving(selectExprProcessor, orderByProcessor, oldEvents, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, false, isSynthesize);
        }

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

        if (optionalHavingNode == null)
        {
            selectNewEvents = ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly, true, isSynthesize);
        }
        else
        {
            selectNewEvents = ResultSetProcessorSimple.getSelectEventsHaving(selectExprProcessor, orderByProcessor, newEvents, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, true, isSynthesize);
        }

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

        // generate old events using select expressions
        if (optionalHavingNode == null)
        {
            selectOldEvents = ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly, false, isSynthesize);
        }
        // generate old events using having then select
        else
        {
            selectOldEvents = ResultSetProcessorSimple.getSelectEventsHaving(selectExprProcessor, orderByProcessor, oldData, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, false, isSynthesize);
        }

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
        if (optionalHavingNode == null)
        {
            selectNewEvents = ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly, true, isSynthesize);
        }
        else
        {
            selectNewEvents = ResultSetProcessorSimple.getSelectEventsHaving(selectExprProcessor, orderByProcessor, newData, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, true, isSynthesize);
        }

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }

        return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
    }

    public Iterator<EventBean> getIterator(Viewable parent)
    {
        if (orderByProcessor == null)
        {
            return new ResultSetAggregateAllIterator(parent.iterator(), this);
        }

        // Pull all parent events, generate order keys
        EventBean[] eventsPerStream = new EventBean[1];
        List<EventBean> outgoingEvents = new ArrayList<EventBean>();
        List<MultiKeyUntyped> orderKeys = new ArrayList<MultiKeyUntyped>();

        for (Iterator<EventBean> it = parent.iterator(); it.hasNext();)
        {
            EventBean candidate = it.next();
            eventsPerStream[0] = candidate;

            Boolean pass = true;
            if (optionalHavingNode != null)
            {
                pass = (Boolean) optionalHavingNode.evaluate(eventsPerStream, true);
            }
            if (!pass)
            {
                continue;
            }

            outgoingEvents.add(selectExprProcessor.process(eventsPerStream, true, true));

            MultiKeyUntyped orderKey = orderByProcessor.getSortKey(eventsPerStream, true);
            orderKeys.add(orderKey);
        }

        // sort
        EventBean[] outgoingEventsArr = outgoingEvents.toArray(new EventBean[0]);
        MultiKeyUntyped[] orderKeysArr = orderKeys.toArray(new MultiKeyUntyped[0]);
        EventBean[] orderedEvents = orderByProcessor.sort(outgoingEventsArr, orderKeysArr);

        return new ArrayEventIterator(orderedEvents);
    }

    /**
     * Returns the select expression processor
     * @return select processor.
     */
    public SelectExprProcessor getSelectExprProcessor()
    {
        return selectExprProcessor;
    }

    /**
     * Returns the optional having expression.
     * @return having expression node
     */
    public ExprNode getOptionalHavingNode()
    {
        return optionalHavingNode;
    }

    public Iterator<EventBean> getIterator(Set<MultiKey<EventBean>> joinSet)
    {
        EventBean[] result;
        if (optionalHavingNode == null)
        {
            result = ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, joinSet, isOutputLimiting, isOutputLimitLastOnly, true, true);
        }
        else
        {
            result = ResultSetProcessorSimple.getSelectEventsHaving(selectExprProcessor, orderByProcessor, joinSet, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, true, true);
        }
        return new ArrayEventIterator(result);
    }

    public void clear()
    {
        aggregationService.clearResults();
    }
}
