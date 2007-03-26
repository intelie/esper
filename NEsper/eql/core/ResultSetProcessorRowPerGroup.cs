using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.core
{
    /// <summary> Result set processor for the fully-grouped case:
    /// there is a group-by and all non-aggregation event properties in the select clause are listed in the group by,
    /// and there are aggregation functions.
    /// <para>
    /// Produces one row for each group that changed (and not one row per event). Computes MultiKey group-by keys for
    /// each event and uses a set of the group-by keys to generate the result rows, using the first (old or new, anyone) event
    /// for each distinct group-by key.
    /// </para>
    /// </summary>

    public class ResultSetProcessorRowPerGroup : ResultSetProcessor
    {
        private readonly SelectExprProcessor selectExprProcessor;
        private readonly OrderByProcessor orderByProcessor;
        private readonly AggregationService aggregationService;
        private readonly IList<ExprNode> groupKeyNodes;
        private readonly ExprNode optionalHavingExpr;
        private readonly bool isOutputLimiting;
        private readonly bool isOutputLimitLastOnly;
        private readonly bool isSorting;

        // For output rate limiting, keep a representative event for each group for
        // representing each group in an output limit clause
        private readonly IDictionary<MultiKey<Object>, EventBean> newEventGroupReps = new LinkedDictionary<MultiKey<Object>, EventBean>();
        private readonly IDictionary<MultiKey<Object>, EventBean> oldEventGroupReps = new LinkedDictionary<MultiKey<Object>, EventBean>();

        // For sorting, keep the generating events for each outgoing event
        private readonly IDictionary<MultiKey<Object>, EventBean[]> newGenerators = new Dictionary<MultiKey<Object>, EventBean[]>();
        private readonly IDictionary<MultiKey<Object>, EventBean[]> oldGenerators = new Dictionary<MultiKey<Object>, EventBean[]>();

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
        /// <param name="isOutputLimiting">true to indicate we are output limiting and must keep producing
        /// a row per group even if groups didn't change
        /// </param>
        /// <param name="isOutputLimitLastOnly">true if output limiting and interested in last event only
        /// </param>
        public ResultSetProcessorRowPerGroup(
            SelectExprProcessor selectExprProcessor,
            OrderByProcessor orderByProcessor,
            AggregationService aggregationService,
            IList<ExprNode> groupKeyNodes,
            ExprNode optionalHavingNode,
            bool isOutputLimiting,
            bool isOutputLimitLastOnly)
        {
            this.selectExprProcessor = selectExprProcessor;
            this.orderByProcessor = orderByProcessor;
            this.aggregationService = aggregationService;
            this.groupKeyNodes = groupKeyNodes;
            this.optionalHavingExpr = optionalHavingNode;
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
        public Pair<EventBean[], EventBean[]> ProcessJoinResult(
            ISet<MultiKey<EventBean>> newEvents,
            ISet<MultiKey<EventBean>> oldEvents)
        {
            // Generate group-by keys for all events, collect all keys in a set for later event generation
            IDictionary<MultiKey<Object>, EventBean[]> keysAndEvents = new Dictionary<MultiKey<Object>, EventBean[]>();
            MultiKey<Object>[] newDataMultiKey = generateGroupKeys(newEvents, keysAndEvents);
            MultiKey<Object>[] oldDataMultiKey = generateGroupKeys(oldEvents, keysAndEvents);

            // generate old events
            EventBean[] selectOldEvents = generateOutputEventsJoin(keysAndEvents, optionalHavingExpr, oldEventGroupReps, oldGenerators);

            // update aggregates
            if (!oldEvents.IsEmpty)
            {
                // apply old data to aggregates
                int count = 0;
                foreach (MultiKey<EventBean> eventsPerStream in oldEvents)
                {
                    aggregationService.ApplyLeave(eventsPerStream.Array, oldDataMultiKey[count]);
                    count++;
                }
            }
            if (!newEvents.IsEmpty)
            {
                // apply old data to aggregates
                int count = 0;
                foreach (MultiKey<EventBean> eventsPerStream in newEvents)
                {
                    aggregationService.ApplyEnter(eventsPerStream.Array, newDataMultiKey[count]);
                    count++;
                }
            }

            // generate new events using select expressions
            EventBean[] selectNewEvents = generateOutputEventsJoin(keysAndEvents, optionalHavingExpr, newEventGroupReps, newGenerators);

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
            // Generate group-by keys for all events, collect all keys in a set for later event generation
            IDictionary<MultiKey<Object>, EventBean> keysAndEvents = new Dictionary<MultiKey<Object>, EventBean>();
            MultiKey<Object>[] newDataMultiKey = generateGroupKeys(newData, keysAndEvents);
            MultiKey<Object>[] oldDataMultiKey = generateGroupKeys(oldData, keysAndEvents);

            // generate old events
            EventBean[] selectOldEvents = generateOutputEventsView(keysAndEvents, optionalHavingExpr, oldEventGroupReps, oldGenerators);

            // update aggregates
            EventBean[] eventsPerStream = new EventBean[1];
            if (oldData != null)
            {
                // apply old data to aggregates
                for (int i = 0; i < oldData.Length; i++)
                {
                    eventsPerStream[0] = oldData[i];
                    aggregationService.ApplyLeave(eventsPerStream, oldDataMultiKey[i]);
                }
            }
            if (newData != null)
            {
                // apply new data to aggregates
                for (int i = 0; i < newData.Length; i++)
                {
                    eventsPerStream[0] = newData[i];
                    aggregationService.ApplyEnter(eventsPerStream, newDataMultiKey[i]);
                }
            }

            // generate new events using select expressions
            EventBean[] selectNewEvents = generateOutputEventsView(keysAndEvents, optionalHavingExpr, newEventGroupReps, newGenerators);

            if ((selectNewEvents != null) || (selectOldEvents != null))
            {
                return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
            }
            return null;
        }

        /// <summary>
        /// Generates the output events view.
        /// </summary>
        /// <param name="keysAndEvents">The keys and events.</param>
        /// <param name="optionalHavingExpr">The optional having expr.</param>
        /// <param name="groupReps">The group reps.</param>
        /// <param name="generators">The generators.</param>
        /// <returns></returns>
        
        private EventBean[] generateOutputEventsView(
            IDictionary<MultiKey<Object>, EventBean> keysAndEvents, ExprNode optionalHavingExpr,
            IDictionary<MultiKey<Object>, EventBean> groupReps,
            IDictionary<MultiKey<Object>, EventBean[]> generators)
        {
            EventBean[] eventsPerStream = new EventBean[1];
            EventBean[] events = new EventBean[keysAndEvents.Count];
            MultiKey<Object>[] keys = new MultiKey<Object>[keysAndEvents.Count];
            EventBean[][] currentGenerators = null;
            if (isSorting)
            {
                currentGenerators = new EventBean[keysAndEvents.Count][];
            }

            int count = 0;
            foreach (KeyValuePair<MultiKey<Object>, EventBean> entry in keysAndEvents)
            {
                // Set the current row of aggregation states
                aggregationService.CurrentRow = entry.Key;

                eventsPerStream[0] = entry.Value;

                // Filter the having clause
                if (optionalHavingExpr != null)
                {
                    Boolean result = (Boolean)optionalHavingExpr.Evaluate(eventsPerStream);
                    if (!result)
                    {
                        continue;
                    }
                }

                events[count] = selectExprProcessor.Process(eventsPerStream);
                keys[count] = entry.Key;
                if (isSorting)
                {
                    EventBean[] currentEventsPerStream = new EventBean[] { entry.Value };
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
                EventBean[] _out = new EventBean[count];
                Array.Copy(events, 0, _out, 0, count);
                events = _out;

                if (isSorting || (isOutputLimiting && !isOutputLimitLastOnly))
                {
                    MultiKey<Object>[] outKeys = new MultiKey<Object>[count];
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

            return ApplyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators);
        }

        private EventBean[] generateOutputEventsJoin(
            IDictionary<MultiKey<Object>, EventBean[]> keysAndEvents,
            ExprNode optionalHavingExpr,
            IDictionary<MultiKey<Object>, EventBean> groupReps,
            IDictionary<MultiKey<Object>, EventBean[]> generators)
        {
            EventBean[] events = new EventBean[keysAndEvents.Count];
            MultiKey<Object>[] keys = new MultiKey<Object>[keysAndEvents.Count];
            EventBean[][] currentGenerators = null;
            if (isSorting)
            {
                currentGenerators = new EventBean[keysAndEvents.Count][];
            }

            int count = 0;
            foreach (KeyValuePair<MultiKey<Object>, EventBean[]> entry in keysAndEvents)
            {
                aggregationService.CurrentRow = entry.Key;
                EventBean[] eventsPerStream = entry.Value;

                // Filter the having clause
                if (optionalHavingExpr != null)
                {
                    Boolean result = (Boolean)optionalHavingExpr.Evaluate(eventsPerStream);
                    if (!result)
                    {
                        continue;
                    }
                }

                events[count] = selectExprProcessor.Process(eventsPerStream);
                keys[count] = entry.Key;
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
                EventBean[] _out = new EventBean[count];
                Array.Copy(events, 0, _out, 0, count);
                events = _out;

                if (isSorting || (isOutputLimiting && !isOutputLimitLastOnly))
                {
                    MultiKey<Object>[] outKeys = new MultiKey<Object>[count];
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

            return ApplyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators);
        }

        private EventBean[] ApplyOutputLimitAndOrderBy(
            EventBean[] events,
            EventBean[][] currentGenerators,
            MultiKey<Object>[] keys,
            IDictionary<MultiKey<Object>, EventBean> groupReps,
            IDictionary<MultiKey<Object>, EventBean[]> generators)
        {
            if (isOutputLimiting && !isOutputLimitLastOnly)
            {
                // Update the group representatives
                int count = 0;
                foreach (MultiKey<Object> key in keys)
                {
                    groupReps[key] = events[count++];
                }

                // Update the outgoing events
                events = CollectionHelper.ToArray(groupReps.Values);

                // Update the generating events and group-by keys if needed
                if (isSorting)
                {
                    currentGenerators = CollectionHelper.ToArray(generators.Values);
                    keys = CollectionHelper.ToArray( groupReps.Keys ) ;
                }
            }

            if (isSorting)
            {
                events = orderByProcessor.Sort(events, currentGenerators, keys);
            }

            return events;
        }

        private MultiKey<Object>[] generateGroupKeys(EventBean[] events, IDictionary<MultiKey<Object>, EventBean> eventPerKey)
        {
            if (events == null)
            {
                return null;
            }

            EventBean[] eventsPerStream = new EventBean[1];
            MultiKey<Object>[] keys = new MultiKey<Object>[events.Length];

            for (int i = 0; i < events.Length; i++)
            {
                eventsPerStream[0] = events[i];
                
                MultiKey<Object> tempKey = keys[i] = generateGroupKey(eventsPerStream);

                if (!eventPerKey.ContainsKey(tempKey))
                {
                    eventPerKey[tempKey] = events[i];
                }
            }

            return keys;
        }

        private MultiKey<Object>[] generateGroupKeys(ISet<MultiKey<EventBean>> resultSet, IDictionary<MultiKey<Object>, EventBean[]> eventPerKey)
        {
            if (resultSet.IsEmpty)
            {
                return null;
            }

            MultiKey<Object>[] keys = new MultiKey<Object>[resultSet.Count];

            int count = 0;
            foreach (MultiKey<EventBean> eventsPerStream in resultSet)
            {
                MultiKey<Object> tempKey = keys[count] = generateGroupKey(eventsPerStream.Array);

                if (!eventPerKey.ContainsKey(tempKey))
                {
                    eventPerKey[tempKey] = eventsPerStream.Array;
                }

                count++;
            }

            return keys;
        }

        private MultiKey<Object> generateGroupKey(EventBean[] eventsPerStream)
        {
            Object[] keys = new Object[groupKeyNodes.Count];

            int count = 0;
            foreach (ExprNode exprNode in groupKeyNodes)
            {
                keys[count] = exprNode.Evaluate(eventsPerStream);
                count++;
            }

            return new MultiKey<Object>(keys);
        }
    }
}
