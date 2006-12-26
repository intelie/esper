package net.esper.eql.core;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.eql.expression.ExprNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    private final boolean isOutputLimiting;
    private final boolean isOutputLimitLastOnly;
    private final boolean isSorting;

    // For output limiting, keep a representative of each group-by group
    private final Map<MultiKeyUntyped, EventBean> oldEventGroupReps = new HashMap<MultiKeyUntyped, EventBean>();
    private final Map<MultiKeyUntyped, EventBean> newEventGroupReps = new HashMap<MultiKeyUntyped, EventBean>();

    // For sorting, keep the generating events for each outgoing event
    private final Map<MultiKeyUntyped, EventBean[]> newGenerators = new HashMap<MultiKeyUntyped, EventBean[]>();
	private final Map<MultiKeyUntyped, EventBean[]> oldGenerators = new HashMap<MultiKeyUntyped, EventBean[]>();


    /**
     * Ctor.
     * @param selectExprProcessor - for processing the select expression and generting the final output rows
     * @param orderByProcessor - for sorting outgoing events according to the order-by clause
     * @param aggregationService - handles aggregation
     * @param groupKeyNodes - list of group-by expression nodes needed for building the group-by keys
     * @param optionalHavingNode - expression node representing validated HAVING clause, or null if none given.
     * Aggregation functions in the having node must have been pointed to the AggregationService for evaluation.
     * @param isOutputLimiting - true to indicate that we limit output
     * @param isOutputLimitLastOnly - true to indicate that we limit output to the last event
     */
    public ResultSetProcessorAggregateGrouped(SelectExprProcessor selectExprProcessor,
                                      		  OrderByProcessor orderByProcessor,
                                      		  AggregationService aggregationService,
                                      		  List<ExprNode> groupKeyNodes,
                                      		  ExprNode optionalHavingNode,
                                      		  boolean isOutputLimiting,
                                      		  boolean isOutputLimitLastOnly)
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

    public EventType getResultEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        // Generate group-by keys for all events
        MultiKeyUntyped[] newDataGroupByKeys = generateGroupKeys(newEvents, true);
        MultiKeyUntyped[] oldDataGroupByKeys = generateGroupKeys(oldEvents, false);

        // generate old events
        log.debug(".processJoinResults creating old output events");
        EventBean[] selectOldEvents = generateOutputEventsJoin(oldEvents, oldDataGroupByKeys, optionalHavingNode, oldEventGroupReps, oldGenerators, false);

        // update aggregates
        if (!oldEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : oldEvents)
            {
                aggregationService.applyLeave(eventsPerStream.getArray(), oldDataGroupByKeys[count]);
                count++;
            }
        }
        if (!newEvents.isEmpty())
        {
            // apply old data to aggregates
            int count = 0;
            for (MultiKey<EventBean> eventsPerStream : newEvents)
            {
                aggregationService.applyEnter(eventsPerStream.getArray(), newDataGroupByKeys[count]);
                count++;
            }
        }

        // generate new events using select expressions
        log.debug(".processJoinResults creating new output events");
        EventBean[] selectNewEvents = generateOutputEventsJoin(newEvents, newDataGroupByKeys, optionalHavingNode, newEventGroupReps, newGenerators, true);

        if ((selectNewEvents != null) || (selectOldEvents != null))
        {
            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }
        return null;
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        // Generate group-by keys for all events
        MultiKeyUntyped[] newDataGroupByKeys = generateGroupKeys(newData, true);
        MultiKeyUntyped[] oldDataGroupByKeys = generateGroupKeys(oldData, false);

        // generate old events
        log.debug(".processViewResults creating old output events");
        EventBean[] selectOldEvents = generateOutputEventsView(oldData, oldDataGroupByKeys, optionalHavingNode, oldEventGroupReps, oldGenerators, false);

        // update aggregates
        EventBean[] eventsPerStream = new EventBean[1];
        if (oldData != null)
        {
            // apply old data to aggregates
            for (int i = 0; i < oldData.length; i++)
            {
                eventsPerStream[0] = oldData[i];
                aggregationService.applyLeave(eventsPerStream, oldDataGroupByKeys[i]);
            }
        }
        if (newData != null)
        {
            // apply new data to aggregates
            for (int i = 0; i < newData.length; i++)
            {
                eventsPerStream[0] = newData[i];
                aggregationService.applyEnter(eventsPerStream, newDataGroupByKeys[i]);
            }
        }

        // generate new events using select expressions
        log.debug(".processViewResults creating new output events");
        EventBean[] selectNewEvents = generateOutputEventsView(newData, newDataGroupByKeys, optionalHavingNode, newEventGroupReps, newGenerators, true);

        if ((selectNewEvents != null) || (selectOldEvents != null))
        {
            return new Pair<EventBean[], EventBean[]>(selectNewEvents, selectOldEvents);
        }
        return null;
    }

	private EventBean[] applyOutputLimitAndOrderBy(EventBean[] events, EventBean[][] currentGenerators, MultiKeyUntyped[] keys, Map<MultiKeyUntyped, EventBean> groupReps, Map<MultiKeyUntyped, EventBean[]> generators, boolean isNewData)
	{
		if(isOutputLimiting && !isOutputLimitLastOnly)
		{
			// Update the group representatives while keeping track
			// of the groups that weren't updated
			Set<MultiKeyUntyped> notUpdatedKeys = new LinkedHashSet<MultiKeyUntyped>(groupReps.keySet());
			int countOne = 0;
			for(MultiKeyUntyped key : keys)
			{
				notUpdatedKeys.remove(key);
				groupReps.put(key, events[countOne++]);
			}


			int outputLength = events.length + notUpdatedKeys.size();
			EventBean[] result = new EventBean[outputLength];

			// Add all the current incoming events to the output
			int countTwo = 0;
			for(EventBean event : events)
			{
				result[countTwo++] =event;
			}

			// Also add a representative for all groups
			// that weren't updated
			for(MultiKeyUntyped key : notUpdatedKeys)
			{
				result[countTwo++] = groupReps.get(key);
			}

			events = result;

			if(isSorting)
			{
				// Update the group-by keys
				MultiKeyUntyped[] sortKeys = new MultiKeyUntyped[outputLength];
				int countThree = 0;
				for(MultiKeyUntyped key : keys)
				{
					sortKeys[countThree++] = key;
				}
				for(MultiKeyUntyped key : notUpdatedKeys)
				{
					sortKeys[countThree++] = key;
				}
				keys = sortKeys;

				// Update the generating events
				EventBean[][] sortGenerators = new EventBean[outputLength][];
				int countFour = 0;
				for(EventBean[] gens : currentGenerators)
				{
					sortGenerators[countFour++] = gens;
				}
				for(MultiKeyUntyped key : notUpdatedKeys)
				{
					sortGenerators[countFour++] = generators.get(key);
				}
				currentGenerators = sortGenerators;
			}
		}

		if(isSorting)
		{
			events = orderByProcessor.sort(events, currentGenerators, keys, isNewData);
		}

		return events;
	}

	private EventBean[] generateOutputEventsView(EventBean[] outputEvents, MultiKeyUntyped[] groupByKeys, ExprNode optionalHavingExpr, Map<MultiKeyUntyped, EventBean> groupReps, Map<MultiKeyUntyped, EventBean[]> generators, boolean isNewData)
    {
        if (outputEvents == null)
        {
            return null;
        }

        EventBean[] eventsPerStream = new EventBean[1];
        EventBean[] events = new EventBean[outputEvents.length];
        MultiKeyUntyped[] keys = new MultiKeyUntyped[outputEvents.length];
        EventBean[][] currentGenerators = null;
        if(isSorting)
        {
        	currentGenerators = new EventBean[outputEvents.length][];
        }

        int count = 0;
        for (int i = 0; i < outputEvents.length; i++)
        {
            aggregationService.setCurrentRow(groupByKeys[count]);
            eventsPerStream[0] = outputEvents[count];

            // Filter the having clause
            if (optionalHavingExpr != null)
            {
                Boolean result = (Boolean) optionalHavingExpr.evaluate(eventsPerStream, isNewData);
                if (!result)
                {
                    continue;
                }
            }

            events[count] = selectExprProcessor.process(eventsPerStream, isNewData);
            keys[count] = groupByKeys[count];
            if(isSorting)
            {
            	EventBean[] currentEventsPerStream = new EventBean[] { outputEvents[count] };
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
            	MultiKeyUntyped[] outKeys = new MultiKeyUntyped[count];
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

        return applyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators, isNewData);
    }

    private MultiKeyUntyped[] generateGroupKeys(Set<MultiKey<EventBean>> resultSet, boolean isNewData)
    {
        if (resultSet.isEmpty())
        {
            return null;
        }

        MultiKeyUntyped keys[] = new MultiKeyUntyped[resultSet.size()];

        int count = 0;
        for (MultiKey<EventBean> eventsPerStream : resultSet)
        {
            keys[count] = generateGroupKey(eventsPerStream.getArray(), isNewData);
            count++;
        }

        return keys;
    }

    private MultiKeyUntyped[] generateGroupKeys(EventBean[] events, boolean isNewData)
    {
        if (events == null)
        {
            return null;
        }

        EventBean[] eventsPerStream = new EventBean[1];
        MultiKeyUntyped keys[] = new MultiKeyUntyped[events.length];

        for (int i = 0; i < events.length; i++)
        {
            eventsPerStream[0] = events[i];
            keys[i] = generateGroupKey(eventsPerStream, isNewData);
        }

        return keys;
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

    private EventBean[] generateOutputEventsJoin(Set<MultiKey<EventBean>> resultSet, MultiKeyUntyped[] groupByKeys, ExprNode optionalHavingExpr, Map<MultiKeyUntyped, EventBean> groupReps, Map<MultiKeyUntyped, EventBean[]> generators, boolean isNewData)
    {
        if (resultSet.isEmpty())
        {
            return null;
        }

        EventBean[] events = new EventBean[resultSet.size()];
        MultiKeyUntyped[] keys = new MultiKeyUntyped[resultSet.size()];
        EventBean[][] currentGenerators = null;
        if(isSorting)
        {
        	currentGenerators = new EventBean[resultSet.size()][];
        }

        int count = 0;
        for (MultiKey<EventBean> row : resultSet)
        {
            EventBean[] eventsPerStream = row.getArray();

            aggregationService.setCurrentRow(groupByKeys[count]);

            // Filter the having clause
            if (optionalHavingExpr != null)
            {
                Boolean result = (Boolean) optionalHavingExpr.evaluate(eventsPerStream, isNewData);
                if (!result)
                {
                    continue;
                }
            }

            events[count] = selectExprProcessor.process(eventsPerStream, isNewData);
            keys[count] = groupByKeys[count];
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
            	MultiKeyUntyped[] outKeys = new MultiKeyUntyped[count];
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

        return applyOutputLimitAndOrderBy(events, currentGenerators, keys, groupReps, generators, isNewData);
    }
}
