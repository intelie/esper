using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.eql;
using net.esper.eql.expression;

using org.apache.commons.logging;

namespace net.esper.eql.core
{
    /// <summary>
    /// Result set processor for the simplest case: no aggregation functions used
    /// in the select clause, and no group-by.
    /// <para>
    /// The processor generates one row for each event entering (new event) and one 
    /// row for each event leaving (old event).
    /// </para>
    /// </summary>
    
    public class ResultSetProcessorSimple : ResultSetProcessor
    {
        private static readonly Log log = LogFactory.GetLog(typeof(ResultSetProcessorSimple));

        private readonly SelectExprProcessor selectExprProcessor;
        private readonly bool isOutputLimiting;
        private readonly bool isOutputLimitLastOnly;
        private readonly OrderByProcessor orderByProcessor;
        private readonly ExprNode optionalHavingExpr;

        /// <summary>Ctor.</summary>
        /// <param name="selectExprProcessor">for processing the select expression and generting the final output rows</param>
        /// <param name="orderByProcessor">for sorting the outgoing events according to the order-by clause</param>
        /// <param name="optionalHavingNode">having clause expression node</param>
        /// <param name="isOutputLimiting">true to indicate we are output limiting and must keep producinga row per group even if groups didn't change</param>
        /// <param name="isOutputLimitLastOnly">true if output limiting and interested in last event only</param>

        public ResultSetProcessorSimple(SelectExprProcessor selectExprProcessor,
                                        OrderByProcessor orderByProcessor,
                                        ExprNode optionalHavingNode,
                                        Boolean isOutputLimiting,
                                        Boolean isOutputLimitLastOnly)
        {
            this.selectExprProcessor = selectExprProcessor;
            this.orderByProcessor = orderByProcessor;
            this.optionalHavingExpr = optionalHavingNode;
            this.isOutputLimiting = isOutputLimiting;
            this.isOutputLimitLastOnly = isOutputLimitLastOnly;
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

            if (optionalHavingExpr == null)
            {
                selectOldEvents = GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly);
                selectNewEvents = GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly);
            }
            else
            {
                selectOldEvents = GetSelectEventsHaving(selectExprProcessor, orderByProcessor, oldEvents, optionalHavingExpr, isOutputLimiting, isOutputLimitLastOnly);
                selectNewEvents = GetSelectEventsHaving(selectExprProcessor, orderByProcessor, newEvents, optionalHavingExpr, isOutputLimiting, isOutputLimitLastOnly);
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
            EventBean[] selectOldEvents = GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly);
            EventBean[] selectNewEvents = GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly);

            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }

        /// <summary>
        /// Applies the select-clause to the given events returning the selected
        /// events. The number of events stays thesame, i.e. this method does not
        /// filter it just transforms the result set.
        /// </summary>
        /// <param name="exprProcessor">processes each input event and returns output event</param>
        /// <param name="orderByProcessor">orders the outgoing events according to the order-by clause</param>
        /// <param name="events">input events</param>
        /// <param name="isOutputLimiting">true to indicate that we limit output</param>
        /// <param name="isOutputLimitLastOnly">true to indicate that we limit output to the last event</param>
        /// <returns>output events, one for each input event</returns>

        public static EventBean[] GetSelectEventsNoHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = ApplyOutputLimit(events, isOutputLimitLastOnly);
            }

            if (events == null)
            {
                return null;
            }

            EventBean[] result = new EventBean[events.Length];
            EventBean[][] eventGenerators = null;
            if (orderByProcessor != null)
            {
                eventGenerators = new EventBean[events.Length][];
            }

            EventBean[] eventsPerStream = new EventBean[1];
            for (int i = 0; i < events.Length; i++)
            {
                eventsPerStream[0] = events[i];

                // Wildcard select case
                if (exprProcessor == null)
                {
                    result[i] = events[i];
                }
                else
                {
                    result[i] = exprProcessor.Process(eventsPerStream);
                }

                if (orderByProcessor != null)
                {
                    eventGenerators[i] = new EventBean[] { events[i] };
                }
            }

            if (orderByProcessor != null)
            {
                return orderByProcessor.Sort(result, eventGenerators);
            }
            else
            {
                return result;
            }
        }

        /// <summary>Applies the last/all event output limit clause.</summary>
        /// <param name="events">to output</param>
        /// <param name="isOutputLimitLastOnly">flag to indicate output all versus output last</param>
        /// <returns>events to output</returns>

        public static EventBean[] ApplyOutputLimit(EventBean[] events, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimitLastOnly && events != null && events.Length > 0)
            {
                return new EventBean[] { events[events.Length - 1] };
            }
            else
            {
                return events;
            }
        }

        /// <summary>Applies the last/all event output limit clause.</summary>
        /// <param name="eventSet">to output</param>
        /// <param name="isOutputLimitLastOnly">flag to indicate output all versus output last</param>
        /// <returns>events to output</returns>

        public static ISet<MultiKey<EventBean>> ApplyOutputLimit(ISet<MultiKey<EventBean>> eventSet, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimitLastOnly && eventSet != null && eventSet.Count > 0)
            {
				Object[] events = eventSet.ToArray();
                ISet<MultiKey<EventBean>> resultSet = new LinkedHashSet<MultiKey<EventBean>>();
                resultSet.Add((MultiKey<EventBean>)events[events.Length - 1]);
                return resultSet;
            }
            else
            {
                return eventSet;
            }
        }

        /// <summary>
        /// Applies the select-clause to the given events returning the selected
        /// events. The number of events stays thesame, i.e. this method does not 
        /// filter it just transforms the result set.
        /// </summary>
        /// <param name="exprProcessor">processes each input event and returns output event</param>
        /// <param name="orderByProcessor">for sorting output events according to the order-by clause</param>
        /// <param name="events">input events</param>
        /// <param name="isOutputLimiting">true to indicate that we limit output</param>
        /// <param name="isOutputLimitLastOnly">true to indicate that we limit output to the last event</param>
        /// <returns>output events, one for each input event</returns>
        public static EventBean[] GetSelectEventsNoHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, ISet<MultiKey<EventBean>> events, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = ApplyOutputLimit(events, isOutputLimitLastOnly);
            }

            int length = events.Count;
            if (length == 0)
            {
                return null;
            }

            EventBean[] result = new EventBean[length];
            EventBean[][] eventGenerators = null;
            if (orderByProcessor != null)
            {
                eventGenerators = new EventBean[length][];
            }

            int count = 0;
            foreach (MultiKey<EventBean> key in events)
            {
                EventBean[] eventsPerStream = key.Array;
                result[count] = exprProcessor.Process(eventsPerStream);
                if (orderByProcessor != null)
                {
                    eventGenerators[count] = eventsPerStream;
                }
                count++;
            }

            if (orderByProcessor != null)
            {
                return orderByProcessor.Sort(result, eventGenerators);
            }
            else
            {
                return result;
            }
        }

        /// <summary>
        /// Applies the select-clause to the given events returning the selected events.
        /// The number of events stays thesame, i.e. this method does not filter it just
        /// transforms the result set.
        /// <para>Also applies a having clause.</para>
        /// </summary>
        /// <param name="exprProcessor">processes each input event and returns output event</param>
        /// <param name="orderByProcessor">for sorting output events according to the order-by clause</param>
        /// <param name="events">input events</param>
        /// <param name="optionalHavingNode">supplies the having-clause expression</param>
        /// <param name="isOutputLimiting">true to indicate that we limit output</param>
        /// <param name="isOutputLimitLastOnly">true to indicate that we limit output to the last event</param>
        /// <returns>output events, one for each input event</returns>
        public static EventBean[] GetSelectEventsHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, ExprNode optionalHavingNode, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = ResultSetProcessorSimple.ApplyOutputLimit(events, isOutputLimitLastOnly);
            }

            if (events == null)
            {
                return null;
            }

            ELinkedList<EventBean> result = new ELinkedList<EventBean>();
            List<EventBean[]> eventGenerators = null;
            if (orderByProcessor != null)
            {
                eventGenerators = new List<EventBean[]>();
            }

            EventBean[] eventsPerStream = new EventBean[1];
            for (int i = 0; i < events.Length; i++)
            {
                eventsPerStream[0] = events[i];

                Boolean passesHaving = (Boolean)optionalHavingNode.Evaluate(eventsPerStream);
                if (!passesHaving)
                {
                    continue;
                }

                result.Add(exprProcessor.Process(eventsPerStream));
                if (orderByProcessor != null)
                {
                    eventGenerators.Add(new EventBean[] { events[i] });
                }
            }

            if (result.Count > 0)
            {
                if (orderByProcessor != null)
                {
                    return orderByProcessor.Sort(result.ToArray(), eventGenerators.ToArray());
                }
                else
                {
                    return result.ToArray();
                }
            }
            else
            {
                return null;
            }
        }

        /// <summary>
        /// Applies the select-clause to the given events returning the selected events. The number of events stays the
        /// same, i.e. this method does not filter it just transforms the result set.
        ///<para>
        /// Also applies a having clause.
        /// </para>
        /// </summary>
        /// <param name="exprProcessor">processes each input event and returns output event</param>
        /// <param name="orderByProcessor">for sorting output events according to the order-by clause</param>
        /// <param name="events">input events</param>
        /// <param name="optionalHavingNode">supplies the having-clause expression</param>
        /// <param name="isOutputLimiting">true to indicate that we limit output</param>
        /// <param name="isOutputLimitLastOnly">true to indicate that we limit output to the last event</param>
        /// <returns>output events, one for each input event</returns>
        
        public static EventBean[] GetSelectEventsHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, ISet<MultiKey<EventBean>> events, ExprNode optionalHavingNode, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = ResultSetProcessorSimple.ApplyOutputLimit(events, isOutputLimitLastOnly);
            }

            ELinkedList<EventBean> result = new ELinkedList<EventBean>();
            List<EventBean[]> eventGenerators = null;
            if (orderByProcessor != null)
            {
                eventGenerators = new List<EventBean[]>();
            }

            foreach (MultiKey<EventBean> key in events)
            {
                EventBean[] eventsPerStream = key.Array;

                Boolean passesHaving = (Boolean)optionalHavingNode.Evaluate(eventsPerStream);
                if (!passesHaving)
                {
                    continue;
                }

                EventBean resultEvent = exprProcessor.Process(eventsPerStream);
                result.Add(resultEvent);
                if (orderByProcessor != null)
                {
                    eventGenerators.Add(eventsPerStream);
                }
            }

            if (result.Count > 0)
            {
                if (orderByProcessor != null)
                {
                    return orderByProcessor.Sort(result.ToArray(), eventGenerators.ToArray()) ;
                }
                else
                {
                    return result.ToArray();
                }
            }
            else
            {
                return null;
            }
        }
    }
}