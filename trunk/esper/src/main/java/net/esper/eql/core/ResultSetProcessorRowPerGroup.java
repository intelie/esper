package net.esper.eql.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.eql.core.AggregationService;
import net.esper.eql.core.OrderByProcessor;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.expression.ExprNode;

/**
 * Result set processor for the fully-grouped case:
 * there is a group-by and all non-aggregation event properties in the select clause are listed in the group by,
 * and there are aggregation functions.
 * <p>
 * Produces one row for each group that changed (and not one row per event). Computes MultiKey group-by keys for
 * each event and uses a set of the group-by keys to generate the result rows, using the first (old or new, anyone) event
 * for each distinct group-by key.
 */
public class ResultSetProcessorRowPerGroup implements ResultSetProcessor
{
    private final SelectExprProcessor selectExprProcessor;
    private final OrderByProcessor orderByProcessor;
    private final AggregationService aggregationService;
    private final List<ExprNode> groupKeyNodes;
    private final ExprNode optionalHavingExpr;
    private final boolean isOutputLimiting;
    private final boolean isOutputLimitLastOnly;
    private final boolean isSorting;

    // For output rate limiting, keep a representative event for each group for
    // representing each group in an output limit clause
    private final Map<MultiKey, EventBean> newEventGroupReps = new LinkedHashMap<MultiKey, EventBean>();
    private final Map<MultiKey, EventBean> oldEventGroupReps = new LinkedHashMap<MultiKey, EventBean>();

    // For sorting, keep the generating events for each outgoing event
    private final Map<MultiKey, EventBean[]> newGenerators = new HashMap<MultiKey, EventBean[]>();
    private final Map<MultiKey, EventBean[]> oldGenerators = new HashMap<MultiKey, EventBean[]>();

    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting outgoing events according to the order-by clause
     * @param aggregationService - handles aggregation
     * @param groupKeyNodes - list of group-by expression nodes needed for building the group-by keys
     * @param optionalHavingNode - expression node representing validated HAVING clause, or null if none given.
     * Aggregation functions in the having node must have been pointed to the AggregationService for evaluation.
     * @param isOutputLimiting - true to indicate we are output limiting and must keep producing
     * a row per group even if groups didn't change
     * @param isOutputLimitLastOnly - true if output limiting and interested in last event only
     */
    public ResultSetProcessorRowPerGroup(SelectExprProcessor selectExprProcessor,
                                         OrderByProcessor orderByProcessor,
                                         AggregationService aggregationService,
                                         List<ExprNode> groupKeyNodes,
                                         ExprNode optionalHavingNode,
                                         boolean isOutputLimiting, boolean isOutputLimitLastOnly)
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

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        // Generate group-by keys for all events, collect all keys in a set for later event generation
        Map<MultiKey, EventBean[]> keysAndEvents = new HashMap<MultiKey, EventBean[]>();
        MultiKey[] newDataMultiKey = generateGroupKeys(newEvents, keysAndEvents);
        MultiKey[] oldDataMultiKey = generateGroupKeys(oldEvents, keysAndEvents);

        // generate old events
        EventBean[] selectOldEvents = generateOutputEventsJoin(keysAndEvents, optionalHavingExpr, oldEventGroupReps, oldGenerators);

        // update aggregates
        if (!oldEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : oldEvents)
            {
                aggregationService.applyLeave(eventsPerStream.getArray(), oldDataMultiKey[count]);
                count++;
            }
        }
        if (!newEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : newEvents)
            {
                aggregationService.applyEnter(eventsPerStream.getArray(), newDataMultiKey[count]);
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

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        // Generate group-by keys for all events, collect all keys in a set for later event generation
        Map<MultiKey, EventBean> keysAndEvents = new HashMap<MultiKey, EventBean>();
        MultiKey[] newDataMultiKey = generateGroupKeys(newData, keysAndEvents);
        MultiKey[] oldDataMultiKey = generateGroupKeys(oldData, keysAndEvents);

        // generate old events
        EventBean[] selectOldEvents = generateOutputEventsView(keysAndEvents, optionalHavingExpr, oldEventGroupReps, oldGenerators);

        // update aggregates
        EventBean[] eventsPerStream = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];
                aggregationService.applyLeave(eventsPerStream, oldDataMultiKey[i]);
            }
        }
        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];
                aggregationService.applyEnter(eventsPerStream, newDataMultiKey[i]);
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

    private EventBean[] generateOutputEventsView(Map<MultiKey, EventBean> keysAndEvents, ExprNode optionalHavingExpr, Map<MultiKey, EventBean> groupReps, Map<MultiKey, EventBean[]> generators)
    {
        EventBean[] eventsPerStream = new EventBean[1];
        EventBean[] events = new EventBean[keysAndEvents.size()];
        MultiKey[] keys = new MultiKey[keysAndEvents.size()];
        EventBean[][] currentGenerators = null;
        if(isSorting)
        {
            currentGenerators = new EventBean[keysAndEvents.size()][];
        }

        int count = 0;
        for (Map.Entry<MultiKey, EventBean> entry : keysAndEvents.entrySet())
        {
            // Set the current row of aggregation states
            aggregationService.setCurrentRow(entry.getKey());

            eventsPerStream[0] = entry.getValue();

            // Filter the having clause
            if (optionalHavingExpr != null)
            {
                Boolean result = (Boolean) optionalHavingExpr.evaluate(eventsPerStream);
                if (!result)
                {
                    continue;
                }
            }

            events[count] = selectExprProcessor.process(eventsPerStream);
            keys[count] = entry.getKey();
            if(isSorting)
            {
                EventBean[] currentEventsPerStream = new EventBean[] { entry.getValue() };
                generators.put(keys[count], currentEventsPerStream);
                currentGenerators[count] = currentEventsPerStream;
            }

            count++;
        }

        // Resize if some rows were filtered out
        if (count != events.length)
        {
            if (count == 0)
            {
                return null;
            }
            EventBean[] out = new EventBean[count];
            System.arraycopy(events, 0, out, 0, count);
            events = out;

            if(isSorting || (isOutputLimiting && !isOutputLimitLastOnly))
            {
                MultiKey[] outKeys = new MultiKey[count];
                System.arraycopy(keys, 0, outKeys, 0, count);
                keys = outKeys;
            }

            if(isSorting)
            {
                EventBean[][] outGens = new EventBean[count][];
                System.arraycopy(currentGenerators, 0, outGens, 0, count);
                currentGenerators = outGens;
            }
        }

        return applyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators);
    }

    private EventBean[] generateOutputEventsJoin(Map<MultiKey, EventBean[]> keysAndEvents, ExprNode optionalHavingExpr, Map<MultiKey, EventBean> groupReps, Map<MultiKey, EventBean[]> generators)
    {
        EventBean[] events = new EventBean[keysAndEvents.size()];
        MultiKey[] keys = new MultiKey[keysAndEvents.size()];
        EventBean[][] currentGenerators = null;
        if(isSorting)
        {
            currentGenerators = new EventBean[keysAndEvents.size()][];
        }

        int count = 0;
        for (Map.Entry<MultiKey, EventBean[]> entry : keysAndEvents.entrySet())
        {
            aggregationService.setCurrentRow(entry.getKey());
            EventBean[] eventsPerStream = entry.getValue();

            // Filter the having clause
            if (optionalHavingExpr != null)
            {
                Boolean result = (Boolean) optionalHavingExpr.evaluate(eventsPerStream);
                if (!result)
                {
                    continue;
                }
            }

            events[count] = selectExprProcessor.process(eventsPerStream);
            keys[count] = entry.getKey();
            if(isSorting)
            {
                generators.put(keys[count], eventsPerStream);
                currentGenerators[count] = eventsPerStream;
            }

            count++;
        }

        // Resize if some rows were filtered out
        if (count != events.length)
        {
            if (count == 0)
            {
                return null;
            }
            EventBean[] out = new EventBean[count];
            System.arraycopy(events, 0, out, 0, count);
            events = out;

            if(isSorting || (isOutputLimiting && !isOutputLimitLastOnly))
            {
                MultiKey[] outKeys = new MultiKey[count];
                System.arraycopy(keys, 0, outKeys, 0, count);
                keys = outKeys;
            }

            if(isSorting)
            {
                EventBean[][] outGens = new EventBean[count][];
                System.arraycopy(currentGenerators, 0, outGens, 0, count);
                currentGenerators = outGens;
            }
        }

        return applyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators);
    }

    private EventBean[] applyOutputLimitAndOrderBy(EventBean[] events, EventBean[][] currentGenerators, MultiKey[] keys, Map<MultiKey, EventBean> groupReps, Map<MultiKey, EventBean[]> generators)
    {
        if(isOutputLimiting && !isOutputLimitLastOnly)
        {
            // Update the group representatives
            int count = 0;
            for(MultiKey key : keys)
            {
                groupReps.put(key, events[count++]);
            }

            // Update the outgoing events
            events = groupReps.values().toArray(new EventBean[0]);

            // Update the generating events and group-by keys if needed
            if(isSorting)
            {
                currentGenerators = generators.values().toArray(new EventBean[0][]);
                keys = groupReps.keySet().toArray(new MultiKey[0]);
            }
        }

        if(isSorting)
        {
            events =  orderByProcessor.sort(events, currentGenerators, keys);
        }

        return events;
    }


    private MultiKey[] generateGroupKeys(EventBean[] events, Map<MultiKey, EventBean> eventPerKey)
    {
        if (events == null)
        {
            return null;
        }

        EventBean[] eventsPerStream = new EventBean[1];
        MultiKey keys[] = new MultiKey[events.length];

        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];
            keys[i] = generateGroupKey(eventsPerStream);

            if (!eventPerKey.containsKey(keys[i]))
            {
                eventPerKey.put(keys[i], events[i]);
            }
        }

        return keys;
    }

    private MultiKey[] generateGroupKeys(Set<MultiKey<EventBean>> resultSet, Map<MultiKey, EventBean[]> eventPerKey)
    {
        if (resultSet.isEmpty())
        {
            return null;
        }

        MultiKey keys[] = new MultiKey[resultSet.size()];

        int count = 0;
        for (MultiKey<EventBean> eventsPerStream : resultSet)
        {
            keys[count] = generateGroupKey(eventsPerStream.getArray());

            if (!eventPerKey.containsKey(keys[count]))
            {
                eventPerKey.put(keys[count], eventsPerStream.getArray());
            }

            count++;
        }

        return keys;
    }

    private MultiKey generateGroupKey(EventBean[] eventsPerStream)
    {
        Object[] keys = new Object[groupKeyNodes.size()];

        int count = 0;
        for (ExprNode exprNode : groupKeyNodes)
        {
            keys[count] = exprNode.evaluate(eventsPerStream);
            count++;
        }

        return new MultiKey(keys);
    }
}
