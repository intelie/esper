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

    /// <summary> Result set processor for the simplest case: no aggregation functions used in the select clause, and no group-by.
    /// <p>
    /// The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
    /// </summary>
    
    public class ResultSetProcessorSimple : ResultSetProcessor
    {
        private static readonly Log log = LogFactory.GetLog(typeof(ResultSetProcessorSimple));

        private readonly SelectExprProcessor selectExprProcessor;
        private readonly bool isOutputLimiting;
        private readonly bool isOutputLimitLastOnly;
        private readonly OrderByProcessor orderByProcessor;
        private readonly ExprNode optionalHavingExpr;

        /**
         * Ctor.
         * @param selectExprProcessor - for processing the select expression and generting the final output rows
         * @param orderByProcessor - for sorting the outgoing events according to the order-by clause
         * @param optionalHavingNode - having clause expression node
         * @param isOutputLimiting - true to indicate we are output limiting and must keep producing
         * a row per group even if groups didn't change
         * @param isOutputLimitLastOnly - true if output limiting and interested in last event only
         */
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

            if (optionalHavingExpr == null)
            {
                selectOldEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldEvents, isOutputLimiting, isOutputLimitLastOnly);
                selectNewEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newEvents, isOutputLimiting, isOutputLimitLastOnly);
            }
            else
            {
                selectOldEvents = getSelectEventsHaving(selectExprProcessor, orderByProcessor, oldEvents, optionalHavingExpr, isOutputLimiting, isOutputLimitLastOnly);
                selectNewEvents = getSelectEventsHaving(selectExprProcessor, orderByProcessor, newEvents, optionalHavingExpr, isOutputLimiting, isOutputLimitLastOnly);
            }

            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }

        public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
        {
            EventBean[] selectOldEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, oldData, isOutputLimiting, isOutputLimitLastOnly);
            EventBean[] selectNewEvents = getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, newData, isOutputLimiting, isOutputLimitLastOnly);

            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }

        /**
         * Applies the select-clause to the given events returning the selected events. The number of events stays the
         * same, i.e. this method does not filter it just transforms the result set.
         * @param exprProcessor - processes each input event and returns output event
         * @param orderByProcessor - orders the outgoing events according to the order-by clause
         * @param events - input events
         * @param isOutputLimiting - true to indicate that we limit output
         * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
         * @return output events, one for each input event
         */
        public static EventBean[] getSelectEventsNoHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = applyOutputLimit(events, isOutputLimitLastOnly);
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
                return orderByProcessor.sort(result, eventGenerators);
            }
            else
            {
                return result;
            }
        }

        /**
         * Applies the last/all event output limit clause.
         * @param events to output
         * @param isOutputLimitLastOnly - flag to indicate output all versus output last
         * @return events to output
         */
        public static EventBean[] applyOutputLimit(EventBean[] events, Boolean isOutputLimitLastOnly)
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

        /**
         * Applies the last/all event output limit clause.
         * @param eventSet to output
         * @param isOutputLimitLastOnly - flag to indicate output all versus output last
         * @return events to output
         */
        public static ISet<MultiKey<EventBean>> applyOutputLimit(ISet<MultiKey<EventBean>> eventSet, Boolean isOutputLimitLastOnly)
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

        /**
         * Applies the select-clause to the given events returning the selected events. The number of events stays the
         * same, i.e. this method does not filter it just transforms the result set.
         * @param exprProcessor - processes each input event and returns output event
         * @param orderByProcessor - for sorting output events according to the order-by clause
         * @param events - input events
         * @param isOutputLimiting - true to indicate that we limit output
         * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
         * @return output events, one for each input event
         */
        public static EventBean[] getSelectEventsNoHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, ISet<MultiKey<EventBean>> events, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = applyOutputLimit(events, isOutputLimitLastOnly);
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
                return orderByProcessor.sort(result, eventGenerators);
            }
            else
            {
                return result;
            }
        }

        /**
         * Applies the select-clause to the given events returning the selected events. The number of events stays the
         * same, i.e. this method does not filter it just transforms the result set.
         * <p>
         * Also applies a having clause.
         * @param exprProcessor - processes each input event and returns output event
         * @param orderByProcessor - for sorting output events according to the order-by clause
         * @param events - input events
         * @param optionalHavingNode - supplies the having-clause expression
         * @param isOutputLimiting - true to indicate that we limit output
         * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
         * @return output events, one for each input event
         */
        public static EventBean[] getSelectEventsHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, EventBean[] events, ExprNode optionalHavingNode, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = ResultSetProcessorSimple.applyOutputLimit(events, isOutputLimitLastOnly);
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
                    return orderByProcessor.sort(result.ToArray(), eventGenerators.ToArray());
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

        /**
         * Applies the select-clause to the given events returning the selected events. The number of events stays the
         * same, i.e. this method does not filter it just transforms the result set.
         * <p>
         * Also applies a having clause.
         * @param exprProcessor - processes each input event and returns output event
         * @param orderByProcessor - for sorting output events according to the order-by clause
         * @param events - input events
         * @param optionalHavingNode - supplies the having-clause expression
         * @param isOutputLimiting - true to indicate that we limit output
         * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
         * @return output events, one for each input event
         */
        public static EventBean[] getSelectEventsHaving(SelectExprProcessor exprProcessor, OrderByProcessor orderByProcessor, ISet<MultiKey<EventBean>> events, ExprNode optionalHavingNode, Boolean isOutputLimiting, Boolean isOutputLimitLastOnly)
        {
            if (isOutputLimiting)
            {
                events = ResultSetProcessorSimple.applyOutputLimit(events, isOutputLimitLastOnly);
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
                    return orderByProcessor.sort(result.ToArray(), eventGenerators.ToArray()) ;
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