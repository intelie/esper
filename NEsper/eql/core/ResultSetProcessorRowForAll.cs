using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.eql.expression;

namespace net.esper.eql.core
{

    /// <summary> Result set processor for the case: aggregation functions used in the select clause, and no group-by,
    /// and all properties in the select clause are under an aggregation function.
    /// <p>
    /// This processor does not perform grouping, every event entering and leaving is in the same group.
    /// Produces one old event and one new event row every time either at least one old or new event is received.
    /// Aggregation state is simply one row holding all the state.
    /// </summary>
    public class ResultSetProcessorRowForAll : ResultSetProcessor
    {
        private readonly SelectExprProcessor selectExprProcessor;
        private readonly AggregationService aggregationService;
        private readonly ExprNode optionalHavingNode;

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

        public EventType ResultEventType
        {
            get
            {
                return selectExprProcessor.ResultEventType;
            }
        }

        public Pair<EventBean[], EventBean[]> processJoinResult(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents)
        {
            EventBean[] selectOldEvents = null;
            EventBean[] selectNewEvents = null;

            selectOldEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode);

            if (!oldEvents.IsEmpty)
            {
                // apply old data to aggregates
                foreach (MultiKey<EventBean> events in oldEvents)
                {
                    aggregationService.applyLeave(events.Array, null);
                }
            }

            if (!newEvents.IsEmpty)
            {
                // apply new data to aggregates
                foreach (MultiKey<EventBean> events in newEvents)
                {
                    aggregationService.applyEnter(events.Array, null);
                }
            }

            selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode);

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

            selectOldEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode);

            EventBean[] buffer = new EventBean[1];
            if (oldData != null)
            {
                // apply old data to aggregates
                for (int i = 0; i < oldData.Length; i++)
                {
                    buffer[0] = oldData[i];
                    aggregationService.applyLeave(buffer, null);
                }
            }

            if (newData != null)
            {
                // apply new data to aggregates
                for (int i = 0; i < newData.Length; i++)
                {
                    buffer[0] = newData[i];
                    aggregationService.applyEnter(buffer, null);
                }
            }

            // generate new events using select expressions
            selectNewEvents = getSelectListEvents(selectExprProcessor, optionalHavingNode);

            if ((selectNewEvents == null) && (selectOldEvents == null))
            {
                return null;
            }

            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }

        private static EventBean[] getSelectListEvents(SelectExprProcessor exprProcessor, ExprNode optionalHavingNode)
        {
            // Since we are dealing with strictly aggregation nodes, there are no events required for evaluating
            EventBean ev = exprProcessor.Process(null);

            if (optionalHavingNode != null)
            {
                Boolean result = (Boolean)optionalHavingNode.Evaluate(null);
                if (!result)
                {
                    return null;
                }
            }

            // The result is always a single row
            return new EventBean[] { ev };
        }
    }
}