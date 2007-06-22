using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.eql.agg;
using net.esper.eql.expression;

using org.apache.commons.logging;

namespace net.esper.eql.core
{
    /// <summary>
    /// Result-set processor for the aggregate-grouped case:
    /// there is a group-by and one or more non-aggregation event properties in the select clause are not listed in the group by,
    /// and there are aggregation functions.
    /// 
    /// This processor does perform grouping by computing MultiKey group-by keys for each row.
    /// The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
    /// 
    /// Aggregation state is a table of rows held by ${AggregationService} where the row key is the group-by MultiKey.
    /// </summary>

    public class ResultSetProcessorAggregateGrouped : ResultSetProcessor
    {
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private readonly SelectExprProcessor selectExprProcessor;
        private readonly OrderByProcessor orderByProcessor;
        private readonly AggregationService aggregationService;
        private readonly IList<ExprNode> groupKeyNodes;
        private readonly ExprNode optionalHavingNode;
        private readonly Boolean isOutputLimiting;
        private readonly Boolean isOutputLimitLastOnly;
        private readonly Boolean isSorting;

        // For output limiting, keep a representative of each group-by group
        private readonly EDictionary<MultiKeyUntyped, EventBean> oldEventGroupReps = new HashDictionary<MultiKeyUntyped, EventBean>();
        private readonly EDictionary<MultiKeyUntyped, EventBean> newEventGroupReps = new HashDictionary<MultiKeyUntyped, EventBean>();

        // For sorting, keep the generating events for each outgoing event
        private readonly EDictionary<MultiKeyUntyped, EventBean[]> newGenerators = new HashDictionary<MultiKeyUntyped, EventBean[]>();
        private readonly EDictionary<MultiKeyUntyped, EventBean[]> oldGenerators = new HashDictionary<MultiKeyUntyped, EventBean[]>();

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

        /// <summary> Ctor.</summary>
        /// <param name="selectExprProcessor">for processing the select expression and generting the final output rows
        /// </param>
        /// <param name="orderByProcessor">for sorting outgoing events according to the order-by clause
        /// </param>
        /// <param name="aggregationService">handles aggregation
        /// </param>
        /// <param name="groupKeyNodes">list of group-by expression nodes needed for building the group-by keys
        /// </param>
        /// <param name="optionalHavingNode">expression node representing validated HAVING clause, or null if none given.
        /// Aggregation functions in the having node must have been pointed to the AggregationService for evaluation.
        /// </param>
        /// <param name="isOutputLimiting">true to indicate that we limit output
        /// </param>
        /// <param name="isOutputLimitLastOnly">true to indicate that we limit output to the last event
        /// </param>
        public ResultSetProcessorAggregateGrouped(SelectExprProcessor selectExprProcessor,
                                                    OrderByProcessor orderByProcessor,
                                                    AggregationService aggregationService,
                                                    IList<ExprNode> groupKeyNodes,
                                                    ExprNode optionalHavingNode,
                                                    Boolean isOutputLimiting,
                                                    Boolean isOutputLimitLastOnly)
        {
            this.selectExprProcessor = selectExprProcessor;
            this.orderByProcessor = orderByProcessor;
            this.aggregationService = aggregationService;
            this.groupKeyNodes = groupKeyNodes;
            this.optionalHavingNode = optionalHavingNode;
            this.isOutputLimiting = isOutputLimiting;
            this.isOutputLimitLastOnly = isOutputLimitLastOnly;
            this.isSorting = orderByProcessor != null;
        }

        /// <summary>
        /// For use by joins posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newEvents">new events posted by join</param>
        /// <param name="oldEvents">old events posted by join</param>
        /// <returns>pair of new events and old events</returns>
        public Pair<EventBean[], EventBean[]> ProcessJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
        {
            // Generate group-by keys for all events
            MultiKeyUntyped[] newDataGroupByKeys = GenerateGroupKeys(newEvents, true);
            MultiKeyUntyped[] oldDataGroupByKeys = GenerateGroupKeys(oldEvents, false);

            // generate old events
            log.Debug(".ProcessJoinResults creating old output events");
            EventBean[] selectOldEvents = GenerateOutputEventsJoin(oldEvents, oldDataGroupByKeys, optionalHavingNode, oldEventGroupReps, oldGenerators, false);

            // update aggregates
            if (!oldEvents.IsEmpty)
            {
                // apply old data to aggregates
                int count = 0;
                foreach (MultiKey<EventBean> eventsPerStream in oldEvents)
                {
                    aggregationService.ApplyLeave(eventsPerStream.Array, oldDataGroupByKeys[count]);
                    count++;
                }
            }
            if (!newEvents.IsEmpty)
            {
                // apply old data to aggregates
                int count = 0;
                foreach (MultiKey<EventBean> eventsPerStream in newEvents)
                {
                    aggregationService.ApplyEnter(eventsPerStream.Array, newDataGroupByKeys[count]);
                    count++;
                }
            }

            // generate new events using select expressions
            log.Debug(".ProcessJoinResults creating new output events");
            EventBean[] selectNewEvents = GenerateOutputEventsJoin(newEvents, newDataGroupByKeys, optionalHavingNode, newEventGroupReps, newGenerators, true);

            if ((selectNewEvents != null) || (selectOldEvents != null))
            {
                return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
            }
            return null;
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
            // Generate group-by keys for all events
            MultiKeyUntyped[] newDataGroupByKeys = GenerateGroupKeys(newData, true);
            MultiKeyUntyped[] oldDataGroupByKeys = GenerateGroupKeys(oldData, false);

            // generate old events
            log.Debug(".ProcessViewResults creating old output events");
            EventBean[] selectOldEvents = GenerateOutputEventsView(oldData, oldDataGroupByKeys, optionalHavingNode, oldEventGroupReps, oldGenerators, false);

            // update aggregates
            EventBean[] eventsPerStream = new EventBean[1];
            if (oldData != null)
            {
                // apply old data to aggregates
                for (int i = 0; i < oldData.Length; i++)
                {
                    eventsPerStream[0] = oldData[i];
                    aggregationService.ApplyLeave(eventsPerStream, oldDataGroupByKeys[i]);
                }
            }
            if (newData != null)
            {
                // apply new data to aggregates
                for (int i = 0; i < newData.Length; i++)
                {
                    eventsPerStream[0] = newData[i];
                    aggregationService.ApplyEnter(eventsPerStream, newDataGroupByKeys[i]);
                }
            }

            // generate new events using select expressions
            log.Debug(".ProcessViewResults creating new output events");
            EventBean[] selectNewEvents = GenerateOutputEventsView(newData, newDataGroupByKeys, optionalHavingNode, newEventGroupReps, newGenerators, true);

            if ((selectNewEvents != null) || (selectOldEvents != null))
            {
                return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
            }
            return null;
        }

        private EventBean[] ApplyOutputLimitAndOrderBy(
            EventBean[] events,
            EventBean[][] currentGenerators,
            MultiKeyUntyped[] keys,
            EDictionary<MultiKeyUntyped, EventBean> groupReps,
            EDictionary<MultiKeyUntyped, EventBean[]> generators,
			bool isNewData)
        {
            if (isOutputLimiting && !isOutputLimitLastOnly)
            {
                // Update the group representatives while keeping track
                // of the groups that weren't updated
                Set<MultiKeyUntyped> notUpdatedKeys = new LinkedHashSet<MultiKeyUntyped>() ;
                notUpdatedKeys.AddAll(groupReps.Keys);

                int countOne = 0;
                foreach (MultiKeyUntyped key in keys)
                {
                    notUpdatedKeys.Remove(key);
                    groupReps[key] = events[countOne++];
                }


                int outputLength = events.Length + notUpdatedKeys.Count;
                EventBean[] result = new EventBean[outputLength];

                // Add all the current incoming events to the output
                int countTwo = 0;
                foreach (EventBean ev in events)
                {
                    result[countTwo++] = ev;
                }

                // Also add a representative for all groups
                // that weren't updated
                foreach (MultiKeyUntyped key in notUpdatedKeys)
                {
                    result[countTwo++] = groupReps.Fetch(key, null);
                }

                events = result;

                if (isSorting)
                {
                    // Update the group-by keys
                    MultiKeyUntyped[] sortKeys = new MultiKeyUntyped[outputLength];
                    int countThree = 0;
                    foreach (MultiKeyUntyped key in keys)
                    {
                        sortKeys[countThree++] = key;
                    }
                    foreach (MultiKeyUntyped key in notUpdatedKeys)
                    {
                        sortKeys[countThree++] = key;
                    }
                    keys = sortKeys;

                    // Update the generating events
                    EventBean[][] sortGenerators = new EventBean[outputLength][];
                    int countFour = 0;
                    foreach (EventBean[] gens in currentGenerators)
                    {
                        sortGenerators[countFour++] = gens;
                    }
                    foreach (MultiKeyUntyped key in notUpdatedKeys)
                    {
                        sortGenerators[countFour++] = generators.Fetch(key, null);
                    }
                    currentGenerators = sortGenerators;
                }
            }

            if (isSorting)
            {
                events = orderByProcessor.Sort(events, currentGenerators, keys, isNewData);
            }

            return events;
        }

        private EventBean[] GenerateOutputEventsView(
            EventBean[] outputEvents,
            MultiKeyUntyped[] groupByKeys,
            ExprNode optionalHavingExpr,
            EDictionary<MultiKeyUntyped, EventBean> groupReps,
            EDictionary<MultiKeyUntyped, EventBean[]> generators,
			bool isNewData)
        {
            if (outputEvents == null)
            {
                return null;
            }

            EventBean[] eventsPerStream = new EventBean[1];
            EventBean[] events = new EventBean[outputEvents.Length];
            MultiKeyUntyped[] keys = new MultiKeyUntyped[outputEvents.Length];
            EventBean[][] currentGenerators = null;
            if (isSorting)
            {
                currentGenerators = new EventBean[outputEvents.Length][];
            }

            int count = 0;
            for (int i = 0; i < outputEvents.Length; i++)
            {
                aggregationService.SetCurrentRow(groupByKeys[count]);
                eventsPerStream[0] = outputEvents[count];

                // Filter the having clause
                if (optionalHavingExpr != null)
                {
                    bool result = (bool)optionalHavingExpr.Evaluate(eventsPerStream, isNewData);
                    if (!result)
                    {
                        continue;
                    }
                }

                events[count] = selectExprProcessor.Process(eventsPerStream, isNewData);
                keys[count] = groupByKeys[count];
                if (isSorting)
                {
                    EventBean[] currentEventsPerStream = new EventBean[] { outputEvents[count] };
                    generators[keys[count]] = currentEventsPerStream;
                    currentGenerators[count] = currentEventsPerStream;
                }

                count++;
            }

            // Resize if some rows were filtered out
            if (count != events.Length)
            {
                if (count == 0)
                {
                    return null;
                }
                EventBean[] outList = new EventBean[count];
                Array.Copy(events, 0, outList, 0, count);
                events = outList;

                if (isSorting || (isOutputLimiting && !isOutputLimitLastOnly))
                {
                    MultiKeyUntyped[] outKeys = new MultiKeyUntyped[count];
                    Array.Copy(keys, 0, outKeys, 0, count);
                    keys = outKeys;
                }

                if (isSorting)
                {
                    EventBean[][] outGens = new EventBean[count][];
                    Array.Copy(currentGenerators, 0, outGens, 0, count);
                    currentGenerators = outGens;
                }
            }

            return ApplyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators, isNewData);
        }

        private MultiKeyUntyped[] GenerateGroupKeys(Set<MultiKey<EventBean>> resultSet, bool isNewData)
        {
            if (resultSet.IsEmpty)
            {
                return null;
            }

            MultiKeyUntyped[] keys = new MultiKeyUntyped[resultSet.Count];

            int count = 0;
            foreach (MultiKey<EventBean> eventsPerStream in resultSet)
            {
                keys[count] = GenerateGroupKey(eventsPerStream.Array, isNewData);
                count++;
            }

            return keys;
        }

        private MultiKeyUntyped[] GenerateGroupKeys(EventBean[] events, bool isNewData)
        {
            if (events == null)
            {
                return null;
            }

            EventBean[] eventsPerStream = new EventBean[1];
            MultiKeyUntyped[] keys = new MultiKeyUntyped[events.Length];

            for (int i = 0; i < events.Length; i++)
            {
                eventsPerStream[0] = events[i];
                keys[i] = GenerateGroupKey(eventsPerStream, isNewData);
            }

            return keys;
        }

        private MultiKeyUntyped GenerateGroupKey(EventBean[] eventsPerStream, bool isNewData)
        {
            Object[] keys = new Object[groupKeyNodes.Count];

            int count = 0;
            foreach (ExprNode exprNode in groupKeyNodes)
            {
                keys[count] = exprNode.Evaluate(eventsPerStream, isNewData);
                count++;
            }

            return new MultiKeyUntyped(keys);
        }

        private EventBean[] GenerateOutputEventsJoin(
            Set<MultiKey<EventBean>> resultSet,
            MultiKeyUntyped[] groupByKeys,
            ExprNode optionalHavingExpr,
            EDictionary<MultiKeyUntyped, EventBean> groupReps,
            EDictionary<MultiKeyUntyped, EventBean[]> generators,
			bool isNewData)
        {
            if (resultSet.IsEmpty)
            {
                return null;
            }

            EventBean[] events = new EventBean[resultSet.Count];
            MultiKeyUntyped[] keys = new MultiKeyUntyped[resultSet.Count];
            EventBean[][] currentGenerators = null;
            if (isSorting)
            {
                currentGenerators = new EventBean[resultSet.Count][];
            }

            int count = 0;
            foreach (MultiKey<EventBean> row in resultSet)
            {
                EventBean[] eventsPerStream = row.Array;

                aggregationService.SetCurrentRow(groupByKeys[count]);

                // Filter the having clause
                if (optionalHavingExpr != null)
                {
                    bool result = (bool)optionalHavingExpr.Evaluate(eventsPerStream, isNewData);
                    if (!result)
                    {
                        continue;
                    }
                }

                events[count] = selectExprProcessor.Process(eventsPerStream, isNewData);
                keys[count] = groupByKeys[count];
                if (isSorting)
                {
                    generators[keys[count]] = eventsPerStream;
                    currentGenerators[count] = eventsPerStream;
                }

                count++;
            }

            // Resize if some rows were filtered out
            if (count != events.Length)
            {
                if (count == 0)
                {
                    return null;
                }
                EventBean[] outList = new EventBean[count];
                Array.Copy(events, 0, outList, 0, count);
                events = outList;

                if (isSorting || (isOutputLimiting && !isOutputLimitLastOnly))
                {
                    MultiKeyUntyped[] outKeys = new MultiKeyUntyped[count];
                    Array.Copy(keys, 0, outKeys, 0, count);
                    keys = outKeys;
                }

                if (isSorting)
                {
                    EventBean[][] outGens = new EventBean[count][];
                    Array.Copy(currentGenerators, 0, outGens, 0, count);
                    currentGenerators = outGens;
                }
            }

            return ApplyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators, isNewData);
        }
    }
}
