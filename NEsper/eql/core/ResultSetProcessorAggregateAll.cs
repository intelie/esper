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
    /// and not all of the properties in the select clause are under an aggregation function.
    /// 
    /// This processor does not perform grouping, every event entering and leaving is in the same group.
    /// The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
    /// Aggregation state is simply one row holding all the state.
    /// </summary>
    public class ResultSetProcessorAggregateAll : ResultSetProcessor
    {
        /// <summary>
        /// Returns the event type of processed results.
        /// </summary>
        /// <value></value>
        /// <returns> event type of the resulting events posted by the processor.
        /// </returns>
        virtual public EventType ResultEventType
        {
            get { return selectExprProcessor.ResultEventType; }
        }

        private readonly SelectExprProcessor selectExprProcessor;
        private readonly OrderByProcessor orderByProcessor;
        private readonly AggregationService aggregationService;
        private readonly ExprNode optionalHavingNode;
        private readonly bool isOutputLimiting;
        private readonly bool isOutputLimitLastOnly;

        /// <summary> Ctor.</summary>
        /// <param name="selectExprProcessor">for processing the select expression and generting the final output rows
        /// </param>
        /// <param name="orderByProcessor">for sorting the outgoing events according to the order-by clause
        /// </param>
        /// <param name="aggregationService">handles aggregation
        /// </param>
        /// <param name="optionalHavingNode">having clause expression node
        /// </param>
        /// <param name="isOutputLimiting">true to indicate that we limit output
        /// </param>
        /// <param name="isOutputLimitLastOnly">true to indicate that we limit output to the last event
        /// </param>
        public ResultSetProcessorAggregateAll(SelectExprProcessor selectExprProcessor, OrderByProcessor orderByProcessor, AggregationService aggregationService, ExprNode optionalHavingNode, bool isOutputLimiting, bool isOutputLimitLastOnly)
        {
            this.selectExprProcessor = selectExprProcessor;
            this.orderByProcessor = orderByProcessor;
            this.aggregationService = aggregationService;
            this.optionalHavingNode = optionalHavingNode;
            this.isOutputLimiting = isOutputLimiting;
            this.isOutputLimitLastOnly = isOutputLimitLastOnly;
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

            if (optionalHavingNode == null)
            {
                selectOldEvents = ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly, false);
            }
            else
            {
                selectOldEvents = ResultSetProcessorSimple.GetSelectEventsHaving(selectExprProcessor, orderByProcessor, oldEvents, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, false);
            }

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

            if (optionalHavingNode == null)
            {
                selectNewEvents = ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly, true);
            }
            else
            {
                selectNewEvents = ResultSetProcessorSimple.GetSelectEventsHaving(selectExprProcessor, orderByProcessor, newEvents, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, true);
            }

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

            // generate old events using select expressions
            if (optionalHavingNode == null)
            {
                selectOldEvents = ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly, false);
            }
            // generate old events using having then select
            else
            {
                selectOldEvents = ResultSetProcessorSimple.GetSelectEventsHaving(selectExprProcessor, orderByProcessor, oldData, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, false);
            }

            EventBean[] eventsPerStream = new EventBean[1];
            if (oldData != null)
            {
                // apply old data to aggregates
                for (int i = 0; i < oldData.Length; i++)
                {
                    eventsPerStream[0] = oldData[i];
                    aggregationService.ApplyLeave(eventsPerStream, null);
                }
            }

            if (newData != null)
            {
                // apply new data to aggregates
                for (int i = 0; i < newData.Length; i++)
                {
                    eventsPerStream[0] = newData[i];
                    aggregationService.ApplyEnter(eventsPerStream, null);
                }
            }

            // generate new events using select expressions
            if (optionalHavingNode == null)
            {
                selectNewEvents = ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly, true);
            }
            else
            {
                selectNewEvents = ResultSetProcessorSimple.GetSelectEventsHaving(selectExprProcessor, orderByProcessor, newData, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly, true);
            }

            if ((selectNewEvents == null) && (selectOldEvents == null))
            {
                return null;
            }

            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }
    }
}