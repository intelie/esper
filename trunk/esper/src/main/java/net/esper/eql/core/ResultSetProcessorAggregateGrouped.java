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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Set;
import java.util.LinkedList;

/**
 * Result-set processor for the aggregate-grouped case:
 * there is a group-by and one or more non-aggregation event properties in the select clause are not listed in the group by,
 * and there are aggregation functions.
 * <p>
 * This processor does perform grouping by computing MultiKey group-by keys for each row.
 * The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
 * <p>
 * Aggregation state is a table of rows held by ${AggregationService} where the row key is the group-by MultiKey.
 */
public class ResultSetProcessorAggregateGrouped implements ResultSetProcessor
{
    private static final Log log = LogFactory.getLog(ResultSetProcessorAggregateGrouped.class);

    private final SelectExprProcessor selectExprProcessor;
    private final OrderByProcessor orderByProcessor;
    private final AggregationService aggregationService;
    private final List<ExprNode> groupKeyNodes;
    private final ExprNode optionalHavingNode;
    private final OutputLimitType outputLimitType;

    private LinkedList<ResultSetProcessorResult> outputLimitBatch = new LinkedList<ResultSetProcessorResult>();

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting outgoing events according to the order-by clause
     * @param aggregationService - handles aggregation
     * @param groupKeyNodes - list of group-by expression nodes needed for building the group-by keys
     * @param optionalHavingNode - expression node representing validated HAVING clause, or null if none given.
     * Aggregation functions in the having node must have been pointed to the AggregationService for evaluation.
     */
    public ResultSetProcessorAggregateGrouped(SelectExprProcessor selectExprProcessor,
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
            MultiKeyUntyped groupKeys[] = new MultiKeyUntyped[oldEvents.size()];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[oldEvents.size()];
            }

            int count = 0;
            for (MultiKey<EventBean> oldEventRow : oldEvents)
            {
                MultiKeyUntyped groupKey = generateGroupKey(oldEventRow.getArray(), false);
                aggregationService.setCurrentRow(groupKey);

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
                    groupKeys[count] = groupKey;
                    count++;
                }
                aggregationService.applyLeave(oldEventRow.getArray(), groupKey);
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultOld.length)
                {
                    EventBean[] resultOldResized = new EventBean[count];
                    System.arraycopy(resultOld, 0, resultOldResized, 0, count);
                    resultOld = resultOldResized;

                    MultiKeyUntyped[] groupKeysResized = new MultiKeyUntyped[count];
                    System.arraycopy(groupKeys, 0, groupKeysResized, 0, count);
                    groupKeys = groupKeysResized;
                    
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
            MultiKeyUntyped groupKeys[] = new MultiKeyUntyped[newEvents.size()];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[newEvents.size()];
            }

            int count = 0;
            for (MultiKey<EventBean> newEventRow : newEvents)
            {
                MultiKeyUntyped groupKey = generateGroupKey(newEventRow.getArray(), true);
                aggregationService.setCurrentRow(groupKey);
                aggregationService.applyEnter(newEventRow.getArray(), groupKey);

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
                    groupKeys[count] = groupKey;
                    count++;
                }
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultNew.length)
                {
                    EventBean[] resultNewResized = new EventBean[count];
                    System.arraycopy(resultNew, 0, resultNewResized, 0, count);
                    resultNew = resultNewResized;

                    MultiKeyUntyped[] groupKeysResized = new MultiKeyUntyped[count];
                    System.arraycopy(groupKeys, 0, groupKeysResized, 0, count);
                    groupKeys = groupKeysResized;

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
            MultiKeyUntyped groupKeys[] = new MultiKeyUntyped[oldData.length];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[oldData.length];
            }

            int count = 0;
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];

                MultiKeyUntyped groupKey = generateGroupKey(eventsPerStream, false);
                aggregationService.setCurrentRow(groupKey);

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
                    groupKeys[count] = groupKey;
                    count++;
                }
                aggregationService.applyLeave(eventsPerStream, groupKey);
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultOld.length)
                {
                    EventBean[] resultOldResized = new EventBean[count];
                    System.arraycopy(resultOld, 0, resultOldResized, 0, count);
                    resultOld = resultOldResized;

                    MultiKeyUntyped[] groupKeysResized = new MultiKeyUntyped[count];
                    System.arraycopy(groupKeys, 0, groupKeysResized, 0, count);
                    groupKeys = groupKeysResized;

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
            MultiKeyUntyped groupKeys[] = new MultiKeyUntyped[newData.length];
            MultiKeyUntyped orderKeys[] = null;
            if (orderByProcessor != null)
            {
                orderKeys = new MultiKeyUntyped[newData.length];
            }

            int count = 0;
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];

                MultiKeyUntyped groupKey = generateGroupKey(eventsPerStream, true);
                aggregationService.setCurrentRow(groupKey);
                aggregationService.applyEnter(eventsPerStream, groupKey);

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
                    groupKeys[count] = groupKey;
                    count++;
                }
            }

            // resize if required
            if (count > 0)
            {
                if (count != resultNew.length)
                {
                    EventBean[] resultNewResized = new EventBean[count];
                    System.arraycopy(resultNew, 0, resultNewResized, 0, count);
                    resultNew = resultNewResized;

                    MultiKeyUntyped[] groupKeysResized = new MultiKeyUntyped[count];
                    System.arraycopy(groupKeys, 0, groupKeysResized, 0, count);
                    groupKeys = groupKeysResized;

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
        ResultSetProcessorResult result = processViewResult(newData, oldData);
        if (outputLimitType == OutputLimitType.ALL)
        {
            outputLimitBatch.add(result);
        }
        else if (outputLimitType == OutputLimitType.LAST)
        {
            outputLimitBatch.add(result);
            if (outputLimitBatch.size() > 1)
            {
                outputLimitBatch.removeFirst();
            }
        }
        else
        {
            throw new UnsupportedOperationException("Adding results for output limit first not supported");
        }
        return true;
    }

    public boolean addJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetProcessorResult result = processJoinResult(newEvents, oldEvents);

        if (outputLimitType == OutputLimitType.ALL)
        {
            outputLimitBatch.add(result);
        }
        else if (outputLimitType == OutputLimitType.LAST)
        {
            outputLimitBatch.add(result);
            if (outputLimitBatch.size() > 1)
            {
                outputLimitBatch.removeFirst();
            }
        }
        else
        {
            throw new UnsupportedOperationException("Adding results for output limit first not supported");
        }
        return true;
    }

    public ResultSetProcessorResult generateResult()
    {
        ResultSetProcessorResult result = ResultSetProcessorSimple.flatten(outputLimitBatch, orderByProcessor);
        outputLimitBatch.clear();
        return result;
    }    
}
