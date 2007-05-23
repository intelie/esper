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
    private final OutputLimitType outputLimitType;

    private LinkedList<ResultSetProcessorResult> outputLimitBatch = new LinkedList<ResultSetProcessorResult>();

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting the outgoing events according to the order-by clause
     * @param aggregationService - handles aggregation
     * @param optionalHavingNode - having clause expression node
     */
    public ResultSetProcessorAggregateAll(SelectExprProcessor selectExprProcessor,
                                          OrderByProcessor orderByProcessor,
                                          AggregationService aggregationService,
                                          ExprNode optionalHavingNode,
                                          OutputLimitType outputLimitType)
    {
        this.selectExprProcessor = selectExprProcessor;
        this.orderByProcessor = orderByProcessor;
        this.aggregationService = aggregationService;
        this.optionalHavingNode = optionalHavingNode;
        this.outputLimitType = outputLimitType;
    }

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public ResultSetProcessorResult processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();

        if (oldEvents != null)
        {
            EventBean[] resultOld = new EventBean[oldEvents.size()];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[oldEvents.size()];
            }

            int count = 0;
            for (MultiKey<EventBean> oldEventRow : oldEvents)
            {
                Boolean pass = true;
                if (optionalHavingNode != null)
                {
                    pass = (Boolean) optionalHavingNode.evaluate(oldEventRow.getArray(), false);
                }

                if (pass)
                {
                    EventBean event = selectExprProcessor.process(oldEventRow.getArray(), false);
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped orderKey = orderByProcessor.getSortKey(oldEventRow.getArray(), false);
                        orderKeys[count] = orderKey;
                    }
                    resultOld[count] = event;
                    count++;
                }
                aggregationService.applyLeave(oldEventRow.getArray(), null);
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultOld.length)
                {
                    EventBean[] resultOldResized = new EventBean[count];
                    System.arraycopy(resultOld, 0, resultOldResized, 0, count);
                    resultOld = resultOldResized;
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped[] orderKeysResized = new MultiKeyUntyped[count];
                        System.arraycopy(orderKeys, 0, orderKeysResized, 0, count);
                        orderKeys = orderKeysResized;
                    }
                }
                result.setOldOut(resultOld);
                result.setOldOrderKey(orderKeys);
            }
        }

        if (newEvents != null)
        {
            EventBean[] resultNew = new EventBean[newEvents.size()];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[newEvents.size()];
            }

            int count = 0;
            for (MultiKey<EventBean> newEventRow : newEvents)
            {
                aggregationService.applyEnter(newEventRow.getArray(), null);

                Boolean pass = true;
                if (optionalHavingNode != null)
                {
                    pass = (Boolean) optionalHavingNode.evaluate(newEventRow.getArray(), true);
                }

                if (pass)
                {
                    EventBean event = selectExprProcessor.process(newEventRow.getArray(), true);
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped orderKey = orderByProcessor.getSortKey(newEventRow.getArray(), true);
                        orderKeys[count] = orderKey;
                    }
                    resultNew[count] = event;
                    count++;
                }
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultNew.length)
                {
                    EventBean[] resultOldResized = new EventBean[count];
                    System.arraycopy(resultNew, 0, resultOldResized, 0, count);
                    resultNew = resultOldResized;
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped[] orderKeysResized = new MultiKeyUntyped[count];
                        System.arraycopy(orderKeys, 0, orderKeysResized, 0, count);
                        orderKeys = orderKeysResized;
                    }
                }
                result.setNewOut(resultNew);
                result.setNewOrderKey(orderKeys);
            }
        }

        return result;
    }

    public ResultSetProcessorResult processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();
        EventBean[] eventsPerStream = new EventBean[1];

        if (oldData != null)
        {
            EventBean[] resultOld = new EventBean[oldData.length];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[oldData.length];
            }

            int count = 0;
            for (EventBean anOldData : oldData)
            {
                eventsPerStream[0] = anOldData;

                Boolean pass = true;
                if (optionalHavingNode != null)
                {
                    pass = (Boolean) optionalHavingNode.evaluate(eventsPerStream, false);
                }

                if (pass)
                {
                    EventBean event = selectExprProcessor.process(eventsPerStream, false);
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped orderKey = orderByProcessor.getSortKey(eventsPerStream, false);
                        orderKeys[count] = orderKey;
                    }
                    resultOld[count] = event;
                    count++;
                }
                aggregationService.applyLeave(eventsPerStream, null);
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultOld.length)
                {
                    EventBean[] resultOldResized = new EventBean[count];
                    System.arraycopy(resultOld, 0, resultOldResized, 0, count);
                    resultOld = resultOldResized;
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped[] orderKeysResized = new MultiKeyUntyped[count];
                        System.arraycopy(orderKeys, 0, orderKeysResized, 0, count);
                        orderKeys = orderKeysResized;
                    }
                }
                result.setOldOut(resultOld);
                result.setOldOrderKey(orderKeys);
            }
        }

        if (newData != null)
        {
            EventBean[] resultNew = new EventBean[newData.length];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[newData.length];
            }

            int count = 0;
            for (EventBean aNewData : newData)
            {
                eventsPerStream[0] = aNewData;

                aggregationService.applyEnter(eventsPerStream, null);

                Boolean pass = true;
                if (optionalHavingNode != null)
                {
                    pass = (Boolean) optionalHavingNode.evaluate(eventsPerStream, true);
                }

                if (pass)
                {
                    EventBean event = selectExprProcessor.process(eventsPerStream, true);
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped orderKey = orderByProcessor.getSortKey(eventsPerStream, true);
                        orderKeys[count] = orderKey;
                    }
                    resultNew[count] = event;
                    count++;
                }
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultNew.length)
                {
                    EventBean[] resultOldResized = new EventBean[count];
                    System.arraycopy(resultNew, 0, resultOldResized, 0, count);
                    resultNew = resultOldResized;
                    if (orderByProcessor != null)
                    {
                        MultiKeyUntyped[] orderKeysResized = new MultiKeyUntyped[count];
                        System.arraycopy(orderKeys, 0, orderKeysResized, 0, count);
                        orderKeys = orderKeysResized;
                    }
                }
                result.setNewOut(resultNew);
                result.setNewOrderKey(orderKeys);
            }
        }

        return result;
    }

    public boolean addViewResult(EventBean[] newData, EventBean[] oldData)
    {
        ResultSetProcessorResult result = processViewResult(newData, oldData);
        if (outputLimitType == OutputLimitType.ALL)
        {
            outputLimitBatch.add(result);
            return result.getNewOut() != null;
        }
        else if (outputLimitType == OutputLimitType.LAST)
        {
            outputLimitBatch.add(result);
            if (outputLimitBatch.size() > 1)
            {
                outputLimitBatch.removeFirst();
            }
            return result.getNewOut() != null;
        }
        else
        {
            throw new UnsupportedOperationException("Adding results for output limit first not supported");
        }
    }

    public boolean addJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetProcessorResult result = processJoinResult(newEvents, oldEvents);
        if (outputLimitType == OutputLimitType.ALL)
        {
            outputLimitBatch.add(result);
            return result.getNewOut() != null;
        }
        else if (outputLimitType == OutputLimitType.LAST)
        {
            outputLimitBatch.add(result);
            if (outputLimitBatch.size() > 1)
            {
                outputLimitBatch.removeFirst();
            }
            return result.getNewOut() != null;
        }
        else
        {
            throw new UnsupportedOperationException("Adding results for output limit first not supported");
        }
    }

    public ResultSetProcessorResult generateResult()
    {
        ResultSetProcessorResult result = ResultSetProcessorSimple.flatten(outputLimitBatch, orderByProcessor);
        outputLimitBatch.clear();
        return result;
    }
}
