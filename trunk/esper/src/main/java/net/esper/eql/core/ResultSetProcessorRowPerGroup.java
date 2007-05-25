/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.collection.MultiKeyUntyped;
import net.esper.eql.agg.AggregationService;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.spec.OutputLimitType;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.*;

/**
 * Result set processor for the fully-grouped case:
 * there is a group-by and all non-aggregation event properties in the select clause are listed in the group by,
 * and there are aggregation functions.
 * <p>
 * Produces one row for each group that changed (and not one row per event). Computes MultiKey group-by keys for
 * each event and uses a set of the group-by keys to generate the result rows, using the first (old or new, anyone) event
 * for each distinct group-by key.
 */
public class ResultSetProcessorRowPerGroup implements ResultSetProcessor
{
    private final SelectExprProcessor selectExprProcessor;
    private final OrderByProcessor orderByProcessor;
    private final AggregationService aggregationService;
    private final List<ExprNode> groupKeyNodes;
    private final ExprNode optionalHavingExpr;

    private Map<MultiKeyUntyped, EventBean> lastNewViewData;
    private Map<MultiKeyUntyped, EventBean[]> lastNewJoinData;
    private EventBean[] lastBatchOldData;
    private MultiKeyUntyped[] lastBatchOrderKeys;
    private OutputLimitType outputLimitType;

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting outgoing events according to the order-by clause
     * @param aggregationService - handles aggregation
     * @param groupKeyNodes - list of group-by expression nodes needed for building the group-by keys
     * @param optionalHavingNode - expression node representing validated HAVING clause, or null if none given.
     * Aggregation functions in the having node must have been pointed to the AggregationService for evaluation.
     */
    public ResultSetProcessorRowPerGroup(SelectExprProcessor selectExprProcessor,
                                         OrderByProcessor orderByProcessor,
                                         AggregationService aggregationService,
                                         List<ExprNode> groupKeyNodes,
                                         ExprNode optionalHavingNode,
                                         OutputLimitType outputLimitType)
    {
        this.selectExprProcessor = selectExprProcessor;
        this.orderByProcessor = orderByProcessor;
        this.aggregationService = aggregationService;
        this.groupKeyNodes = groupKeyNodes;
        this.optionalHavingExpr = optionalHavingNode;
        this.outputLimitType = outputLimitType;
    }

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public ResultSetProcessorResult processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();

        // Generate group-by keys for all events, collect all keys in a set for later event generation
        Map<MultiKeyUntyped, EventBean[]> keysAndEvents = new HashMap<MultiKeyUntyped, EventBean[]>();
        MultiKeyUntyped[] newDataMultiKey = generateGroupKeys(newEvents, keysAndEvents, true);
        MultiKeyUntyped[] oldDataMultiKey = generateGroupKeys(oldEvents, keysAndEvents, false);

        // generate old events
        ResultSetSelect selectOldEvents = generateOutputEventsJoin(keysAndEvents, optionalHavingExpr, false);
        result.setOldOut(selectOldEvents.getEvents());
        result.setOldOrderKey(selectOldEvents.getOrderKeys());

        // update aggregates
        if (!oldEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : oldEvents)
            {
                aggregationService.applyLeave(eventsPerStream.getArray(), oldDataMultiKey[count]);
                count++;
            }
        }
        if (!newEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : newEvents)
            {
                aggregationService.applyEnter(eventsPerStream.getArray(), newDataMultiKey[count]);
                count++;
            }
        }

        // generate new events using select expressions
        ResultSetSelect selectNewEvents = generateOutputEventsJoin(keysAndEvents, optionalHavingExpr, true);
        result.setNewOut(selectNewEvents.getEvents());
        result.setNewOrderKey(selectNewEvents.getOrderKeys());

        if ((selectNewEvents.getEvents() != null) || (selectOldEvents.getEvents() != null))
        {
            return result;
        }
        return null;
    }

    public ResultSetProcessorResult processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();
        
        // Generate group-by keys for all events, collect all keys in a set for later event generation
        Map<MultiKeyUntyped, EventBean> keysAndEvents = new HashMap<MultiKeyUntyped, EventBean>();
        MultiKeyUntyped[] newDataMultiKey = generateGroupKeys(newData, keysAndEvents, true);
        MultiKeyUntyped[] oldDataMultiKey = generateGroupKeys(oldData, keysAndEvents, false);

        // generate old events
        ResultSetSelect selectOldEvents = generateOutputEventsView(keysAndEvents, optionalHavingExpr, false);
        result.setOldOut(selectOldEvents.getEvents());
        result.setOldOrderKey(selectOldEvents.getOrderKeys());

        // update aggregates
        EventBean[] eventsPerStream = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];
                aggregationService.applyLeave(eventsPerStream, oldDataMultiKey[i]);
            }
        }
        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];
                aggregationService.applyEnter(eventsPerStream, newDataMultiKey[i]);
            }
        }

        // generate new events using select expressions
        ResultSetSelect selectNewEvents = generateOutputEventsView(keysAndEvents, optionalHavingExpr, true);
        result.setNewOut(selectNewEvents.getEvents());
        result.setNewOrderKey(selectNewEvents.getOrderKeys());

        if ((selectNewEvents.getEvents() != null) || (selectOldEvents.getEvents() != null))
        {
            return result;
        }
        return null;
    }

    private ResultSetSelect generateOutputEventsView(Map<MultiKeyUntyped, EventBean> keysAndEvents, ExprNode optionalHavingExpr, boolean isNewData)
    {
        ResultSetSelect result = new ResultSetSelect();
        
        EventBean[] eventsPerStream = new EventBean[1];
        EventBean[] events = new EventBean[keysAndEvents.size()];
        MultiKeyUntyped[] orderKeys = null;
        if (orderByProcessor != null)
        {
            orderKeys = new MultiKeyUntyped[keysAndEvents.size()];
        }

        int count = 0;
        for (Map.Entry<MultiKeyUntyped, EventBean> entry : keysAndEvents.entrySet())
        {
            // Set the current row of aggregation states
            aggregationService.setCurrentRow(entry.getKey());

            eventsPerStream[0] = entry.getValue();

            // Filter the having clause
            if (optionalHavingExpr != null)
            {
                Boolean pass = (Boolean) optionalHavingExpr.evaluate(eventsPerStream, isNewData);
                if (!pass)
                {
                    continue;
                }
            }

            events[count] = selectExprProcessor.process(eventsPerStream, isNewData);
            if (orderByProcessor != null)
            {
                orderKeys[count] = orderByProcessor.getSortKey(eventsPerStream, isNewData);
            }

            count++;
        }

        // Resize if some rows were filtered out
        if (count != events.length)
        {
            if (count == 0)
            {
                return result;
            }
            EventBean[] out = new EventBean[count];
            System.arraycopy(events, 0, out, 0, count);
            events = out;

            if (orderByProcessor != null)
            {
                MultiKeyUntyped[] orderKeysResized = new MultiKeyUntyped[count];
                System.arraycopy(orderKeys, 0, orderKeysResized, 0, count);
                orderKeys = orderKeysResized;
            }
        }

        result.setEvents(events);
        result.setOrderKeys(orderKeys);
        return result;
    }

    private ResultSetSelect generateOutputEventsJoin(Map<MultiKeyUntyped, EventBean[]> keysAndEvents, ExprNode optionalHavingExpr, boolean isNewData)
    {
        ResultSetSelect result = new ResultSetSelect();        
        EventBean[] events = new EventBean[keysAndEvents.size()];

        MultiKeyUntyped[] orderKeys = null;
        if (orderByProcessor != null)
        {
            orderKeys = new MultiKeyUntyped[keysAndEvents.size()];
        }

        int count = 0;
        for (Map.Entry<MultiKeyUntyped, EventBean[]> entry : keysAndEvents.entrySet())
        {
            aggregationService.setCurrentRow(entry.getKey());
            EventBean[] eventsPerStream = entry.getValue();

            // Filter the having clause
            if (optionalHavingExpr != null)
            {
                Boolean pass = (Boolean) optionalHavingExpr.evaluate(eventsPerStream, isNewData);
                if (!pass)
                {
                    continue;
                }
            }

            events[count] = selectExprProcessor.process(eventsPerStream, isNewData);
            if (orderByProcessor != null)
            {
                orderKeys[count] = orderByProcessor.getSortKey(eventsPerStream, isNewData);
            }

            count++;
        }

        // Resize if some rows were filtered out
        if (count != events.length)
        {
            if (count == 0)
            {
                return result;
            }
            EventBean[] out = new EventBean[count];
            System.arraycopy(events, 0, out, 0, count);
            events = out;

            if (orderByProcessor != null)
            {
                MultiKeyUntyped[] orderKeysResized = new MultiKeyUntyped[count];
                System.arraycopy(orderKeys, 0, orderKeysResized, 0, count);
                orderKeys = orderKeysResized;                
            }
        }

        result.setEvents(events);
        result.setOrderKeys(orderKeys);
        return result;
    }

    private MultiKeyUntyped[] generateGroupKeys(EventBean[] events, Map<MultiKeyUntyped, EventBean> eventPerKey, boolean isNewData)
    {
        if (events == null)
        {
            return null;
        }

        EventBean[] eventsPerStream = new EventBean[1];
        MultiKeyUntyped keys[] = new MultiKeyUntyped[events.length];

        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];
            keys[i] = generateGroupKey(eventsPerStream, isNewData);

            if (!eventPerKey.containsKey(keys[i]))
            {
                eventPerKey.put(keys[i], events[i]);
            }
        }

        return keys;
    }

    private MultiKeyUntyped[] generateGroupKeys(Set<MultiKey<EventBean>> resultSet, Map<MultiKeyUntyped, EventBean[]> eventPerKey, boolean isNewData)
    {
        if (resultSet.isEmpty())
        {
            return null;
        }

        MultiKeyUntyped keys[] = new MultiKeyUntyped[resultSet.size()];

        int count = 0;
        for (MultiKey<EventBean> eventsPerStream : resultSet)
        {
            keys[count] = generateGroupKey(eventsPerStream.getArray(), isNewData);

            if (!eventPerKey.containsKey(keys[count]))
            {
                eventPerKey.put(keys[count], eventsPerStream.getArray());
            }

            count++;
        }

        return keys;
    }

    private MultiKeyUntyped generateGroupKey(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object[] keys = new Object[groupKeyNodes.size()];

        int count = 0;
        for (ExprNode exprNode : groupKeyNodes)
        {
            keys[count] = exprNode.evaluate(eventsPerStream, isNewData);
            count++;
        }

        return new MultiKeyUntyped(keys);
    }

    public boolean addViewResult(EventBean[] newData, EventBean[] oldData)
    {
        if (lastNewViewData == null)
        {
            lastNewViewData = new HashMap<MultiKeyUntyped, EventBean>();
        }

        MultiKeyUntyped[] newDataMultiKey = generateGroupKeys(newData, lastNewViewData, true);
        MultiKeyUntyped[] oldDataMultiKey = generateGroupKeys(oldData, lastNewViewData, false);

        EventBean[] eventsPerStream = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];
                aggregationService.applyLeave(eventsPerStream, oldDataMultiKey[i]);
            }
        }
        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];
                aggregationService.applyEnter(eventsPerStream, newDataMultiKey[i]);
            }
        }
        return true;
    }

    public boolean addJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        if (lastNewJoinData == null)
        {
            lastNewJoinData = new HashMap<MultiKeyUntyped, EventBean[]>();
        }

        MultiKeyUntyped[] newDataMultiKey = generateGroupKeys(newEvents, lastNewJoinData, true);
        MultiKeyUntyped[] oldDataMultiKey = generateGroupKeys(oldEvents, lastNewJoinData, false);

        if (!oldEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : oldEvents)
            {
                aggregationService.applyLeave(eventsPerStream.getArray(), oldDataMultiKey[count]);
                count++;
            }
        }
        if (!newEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : newEvents)
            {
                aggregationService.applyEnter(eventsPerStream.getArray(), newDataMultiKey[count]);
                count++;
            }
        }
        return true;
    }

    public ResultSetProcessorResult generateResult()
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();
        ResultSetSelect resultSetSelect;

        if (lastNewViewData != null)
        {
            resultSetSelect = generateOutputEventsView(lastNewViewData, optionalHavingExpr, true);
        }
        else if (lastNewJoinData != null)
        {
            resultSetSelect = generateOutputEventsJoin(lastNewJoinData, optionalHavingExpr, true);
        }
        else
        {
            return result;
        }
        
        result.setNewOut(resultSetSelect.getEvents());
        result.setOldOut(lastBatchOldData);
        result.setNewOrderKey(resultSetSelect.getOrderKeys());
        result.setOldOrderKey(lastBatchOrderKeys);

        lastBatchOldData = resultSetSelect.getEvents();
        lastBatchOrderKeys = resultSetSelect.getOrderKeys();

        if (outputLimitType == OutputLimitType.LAST)
        {
            if (lastNewViewData != null)
            {
                lastNewViewData.clear();
            }
            if (lastNewJoinData != null)
            {
                lastNewJoinData.clear();
            }
        }
        return result;
    }
}
