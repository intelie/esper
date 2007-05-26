using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.eql.agg;
using net.esper.eql.expression;

namespace net.esper.eql.core
{
    /// <summary>
    /// Result set processor for the case: aggregation functions used in the select clause, and no group-by,
    /// and all properties in the select clause are under an aggregation function.
    /// <para>
    /// This processor does not perform grouping, every event entering and leaving is in the same group.
    /// Produces one old event and one new event row every time either at least one old or new event is received.
    /// Aggregation state is simply one row holding all the state.
    /// </para>
    /// </summary>
    public class ResultSetProcessorRowForAll : ResultSetProcessor
    {
        private readonly SelectExprProcessor selectExprProcessor;
        private readonly AggregationService aggregationService;
        private readonly ExprNode optionalHavingNode;

        /// <summary>Ctor.</summary>
        /// <param name="selectExprProcessor">for processing the select expression and generting the final output rows</param>
        /// <param name="aggregationService">handles aggregation</param>
        /// <param name="optionalHavingNode">having clause expression node</param>

        public ResultSetProcessorRowForAll(SelectExprProcessor selectExprProcessor,
                                           AggregationService aggregationService,
                                           ExprNode optionalHavingNode)
        {
            this.selectExprProcessor = selectExprProcessor;
            this.aggregationService = aggregationService;
            this.optionalHavingNode = optionalHavingNode;
        }

        /// <summary>
        /// Returns the event type of processed results.
        /// </summary>
        /// <value></value>
        /// <returns> event type of the resulting events posted by the processor.
        /// </returns>
        public EventType ResultEventType
        {
            get
            {
                return selectExprProcessor.ResultEventType;
            }
        }

        /// <summary>
        /// For use by joins posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newEvents">new events posted by join</param>
        /// <param name="oldEvents">old events posted by join</param>
        /// <returns>pair of new events and old events</returns>
        public Pair<EventBean[], EventBean[]> ProcessJoinResult(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents)
        {
            EventBean[] selectOldEvents = null;
            EventBean[] selectNewEvents = null;

            selectOldEvents = GetSelectListEvents(selectExprProcessor, optionalHavingNode);

            if (!oldEvents.IsEmpty)
            {
                // apply old data to aggregates
                foreach (MultiKey<EventBean> events in oldEvents)
                {
                    aggregationService.ApplyLeave(events.Array, null);
                }
            }

            if (!newEvents.IsEmpty)
            {
                // apply new data to aggregates
                foreach (MultiKey<EventBean> events in newEvents)
                {
                    aggregationService.ApplyEnter(events.Array, null);
                }
            }

            selectNewEvents = GetSelectListEvents(selectExprProcessor, optionalHavingNode);

            if ((selectNewEvents == null) && (selectOldEvents == null))
            {
                return null;
            }
            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }

        /// <summary>
        /// For use by views posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newData">new events posted by view</param>
        /// <param name="oldData">old events posted by view</param>
        /// <returns>pair of new events and old events</returns>
        public Pair<EventBean[], EventBean[]> ProcessViewResult(EventBean[] newData, EventBean[] oldData)
        {
            EventBean[] selectOldEvents = null;
            EventBean[] selectNewEvents = null;

            selectOldEvents = GetSelectListEvents(selectExprProcessor, optionalHavingNode);

            EventBean[] buffer = new EventBean[1];
            if (oldData != null)
            {
                // apply old data to aggregates
                for (int i = 0; i < oldData.Length; i++)
                {
                    buffer[0] = oldData[i];
                    aggregationService.ApplyLeave(buffer, null);
                }
            }

            if (newData != null)
            {
                // apply new data to aggregates
                for (int i = 0; i < newData.Length; i++)
                {
                    buffer[0] = newData[i];
                    aggregationService.ApplyEnter(buffer, null);
                }
            }

            // generate new events using select expressions
            selectNewEvents = GetSelectListEvents(selectExprProcessor, optionalHavingNode);

            if ((selectNewEvents == null) && (selectOldEvents == null))
            {
                return null;
            }

            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }

        private static EventBean[] GetSelectListEvents(SelectExprProcessor exprProcessor, ExprNode optionalHavingNode)
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