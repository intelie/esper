/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.eql.agg.AggregationService;
import net.esper.eql.expression.ExprNode;

import java.util.Set;

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

    public ResultSetProcessorResult processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();

        EventBean[] selectOldEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, false);
        result.setOldOut(selectOldEvents);

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

        EventBean[] selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, true);
        result.setNewOut(selectNewEvents);

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }
        return result;
    }

    public ResultSetProcessorResult processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();

        EventBean[] selectOldEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, false);
        result.setOldOut(selectOldEvents);

        EventBean[] buffer = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                buffer[0] = oldData[i];
                aggregationService.applyLeave(buffer, null);
            }
        }

        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                buffer[0] = newData[i];
                aggregationService.applyEnter(buffer, null);
            }
        }

        // generate new events using select expressions
        EventBean[] selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, true);
        result.setNewOut(selectNewEvents);

        if ((selectNewEvents == null) && (selectOldEvents == null))
        {
            return null;
        }

        return result;
    }

    private static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, ExprNode optionalHavingNode, boolean isNewData)
    {
        // Since we are dealing with strictly aggregation nodes, there are no events required for evaluating
        EventBean event = exprProcessor.process(null, isNewData);

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

    public boolean addViewResult(EventBean[] newData, EventBean[] oldData)
    {
        EventBean[] buffer = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                buffer[0] = oldData[i];
                aggregationService.applyLeave(buffer, null);
            }
        }

        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                buffer[0] = newData[i];
                aggregationService.applyEnter(buffer, null);
            }
        }
        return true;
    }

    public boolean addJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
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
        return true;
    }

    public ResultSetProcessorResult generateResult()
    {
        ResultSetProcessorResult result = new ResultSetProcessorResult();
        EventBean[] selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode, true);
        result.setNewOut(selectNewEvents);
        return result;
    }
}
