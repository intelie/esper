package net.esper.eql.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.util.MultiKeyComparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An order-by processor that sorts events according to the expressions
 * in the order_by clause.
 */
public class OrderByProcessorSimple implements OrderByProcessor {

	private static final Log log = LogFactory.getLog(OrderByProcessorSimple.class);

	private final List<Pair<ExprNode, Boolean>> orderByList;
	private final List<ExprNode> groupByNodes;
	private final boolean needsGroupByKeys;
	private final AggregationService aggregationService;

	private final Comparator<MultiKey> comparator;

	/**
	 * Ctor.
	 * 
	 * @param orderByList -
	 *            the nodes that generate the keys to sort events on
	 * @param groupByNodes -
	 *            generate the keys for determining aggregation groups
	 * @param needsGroupByKeys -
	 *            indicates whether this processor needs to have individual
	 *            group by keys to evaluate the sort condition successfully
	 * @param aggregationService -
	 *            used to evaluate aggregate functions in the group-by and
	 *            sort-by clauses
     * @param eventAdapterService - provides event wrappers
	 */
	public OrderByProcessorSimple(final List<Pair<ExprNode, Boolean>> orderByList,
								  List<ExprNode> groupByNodes, 
								  boolean needsGroupByKeys,
								  AggregationService aggregationService)
	{
		this.orderByList = orderByList;
		this.groupByNodes = groupByNodes;
		this.needsGroupByKeys = needsGroupByKeys;
		this.aggregationService = aggregationService;
        
		this.comparator = new MultiKeyComparator(getIsDescendingValues());
	}
	

	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents) 
	{
		if (outgoingEvents == null || outgoingEvents.length < 2) 
		{
			return outgoingEvents;
		}

		// Get the group by keys if needed
		MultiKey[] groupByKeys = null;
		if (needsGroupByKeys) 
		{
			groupByKeys = generateGroupKeys(generatingEvents);
		}

		return sort(outgoingEvents, generatingEvents, groupByKeys);
	}

	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, MultiKey[] groupByKeys) 
	{
		log.debug(".sort");
		if (outgoingEvents == null || outgoingEvents.length < 2) 
		{
			return outgoingEvents;
		}

		// Create the multikeys of sort values
		List<MultiKey> sortValuesMultiKeys = createSortProperties(generatingEvents, groupByKeys);

		// Map the sort values to the corresponding outgoing events
		Map<MultiKey, List<EventBean>> sortToOutgoing = new HashMap<MultiKey, List<EventBean>>();
		int countOne = 0;
		for (MultiKey sortValues : sortValuesMultiKeys) 
		{
			List<EventBean> list = sortToOutgoing.get(sortValues);
			if (list == null) 
			{
				list = new ArrayList<EventBean>();
			}
			list.add(outgoingEvents[countOne++]);
			sortToOutgoing.put(sortValues, list);
		}

		// Sort the sort values
		Collections.sort(sortValuesMultiKeys, comparator);

		// Sort the outgoing events in the same order
		Set<MultiKey> sortSet = new LinkedHashSet<MultiKey>(sortValuesMultiKeys);
		EventBean[] result = new EventBean[outgoingEvents.length];
		int countTwo = 0;
		for (MultiKey sortValues : sortSet) 
		{
			Collection<EventBean> output = sortToOutgoing.get(sortValues);
			for(EventBean event : output)
			{
				result[countTwo++] = event;
			}
		}

		return result;
	}

    /**
     * Compares values for sorting.
     * @param valueOne -first value to compare, can be null
     * @param valueTwo -second value to compare, can be null
     * @param descending - true if ascending, false if descending
     * @return 0 if equal, -1 if smaller, +1 if larger
     */
    protected static int compareValues(Object valueOne, Object valueTwo, boolean descending)
	{
		if (descending) 
		{
			Object temp = valueOne;
			valueOne = valueTwo;
			valueTwo = temp;
		}

		if (valueOne == null || valueTwo == null) 
		{
			// A null value is considered equal to another null
			// value and smaller than any nonnull value
			if (valueOne == null && valueTwo == null) 
			{
				return 0;
			}
			if (valueOne == null) 
			{
				return -1;
			}
			return 1;
		}

		Comparable comparable1;
		if (valueOne instanceof Comparable) 
		{
			comparable1 = (Comparable) valueOne;
		} 
		else 
		{
			throw new ClassCastException("Sort by clause cannot sort objects of type " + valueOne.getClass());
		}

		return comparable1.compareTo(valueTwo);
	}
	
	private List<MultiKey> createSortProperties(EventBean[][] generatingEvents, MultiKey[] groupByKeys) 
	{
		MultiKey[] sortProperties = new MultiKey[generatingEvents.length];

		int count = 0;
		for (EventBean[] eventsPerStream : generatingEvents) 
		{
			// Make a new multikey that contains the sort-by values.
			if (needsGroupByKeys) 
			{
				aggregationService.setCurrentRow(groupByKeys[count]);
			}

			Object[] values = new Object[orderByList.size()];
			int countTwo = 0;
			for (Pair<ExprNode, Boolean> sortPair : orderByList) 
			{
				ExprNode sortNode = sortPair.getFirst();
				values[countTwo++] = sortNode.evaluate(eventsPerStream);
			}

			sortProperties[count] = new MultiKey<Object>(values);
			count++;
		}
		return Arrays.asList(sortProperties);
	}

	private MultiKey generateGroupKey(EventBean[] eventsPerStream) 
	{
		Object[] keys = new Object[groupByNodes.size()];

		int count = 0;
		for (ExprNode exprNode : groupByNodes) 
		{
			keys[count] = exprNode.evaluate(eventsPerStream);
			count++;
		}

		return new MultiKey<Object>(keys);
	}

	private MultiKey[] generateGroupKeys(EventBean[][] generatingEvents) 
	{
		MultiKey keys[] = new MultiKey[generatingEvents.length];

		int count = 0;
		for (EventBean[] eventsPerStream : generatingEvents) 
		{
			keys[count++] = generateGroupKey(eventsPerStream);
		}

		return keys;
	}

	private Boolean[] getIsDescendingValues()
	{
		Boolean[] isDescendingValues  = new Boolean[orderByList.size()];
		int count = 0;
		for(Pair<ExprNode, Boolean> pair : orderByList)
		{
			isDescendingValues[count++] = pair.getSecond();
		}
		return isDescendingValues;
	}
}
