package net.esper.eql.core;

import java.util.*;

import net.esper.collection.Pair;
import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;
import net.esper.util.MultiKeyComparator;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.agg.AggregationService;

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

	private final Comparator<MultiKeyUntyped> comparator;

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

    public MultiKeyUntyped getSortKey(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object[] values = new Object[orderByList.size()];
        int count = 0;
        for (Pair<ExprNode, Boolean> sortPair : orderByList)
        {
            ExprNode sortNode = sortPair.getFirst();
            values[count++] = sortNode.evaluate(eventsPerStream, isNewData);
        }

        return new MultiKeyUntyped(values);
    }

    public MultiKeyUntyped[] getSortKeyPerRow(EventBean[] generatingEvents, boolean isNewData)
    {
        if (generatingEvents == null)
        {
            return null;
        }

        MultiKeyUntyped[] sortProperties = new MultiKeyUntyped[generatingEvents.length];

        int count = 0;
        EventBean[] evalEventsPerStream = new EventBean[1];
        for (EventBean event : generatingEvents)
        {
            Object[] values = new Object[orderByList.size()];
            int countTwo = 0;
            evalEventsPerStream[0] = event;
            for (Pair<ExprNode, Boolean> sortPair : orderByList)
            {
                ExprNode sortNode = sortPair.getFirst();
                values[countTwo++] = sortNode.evaluate(evalEventsPerStream, isNewData);
            }

            sortProperties[count] = new MultiKeyUntyped(values);
            count++;
        }

        return sortProperties;
    }

	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, boolean isNewData)
	{
		if (outgoingEvents == null || outgoingEvents.length < 2) 
		{
			return outgoingEvents;
		}

		// Get the group by keys if needed
		MultiKeyUntyped[] groupByKeys = null;
		if (needsGroupByKeys) 
		{
			groupByKeys = generateGroupKeys(generatingEvents, isNewData);
		}

		return sort(outgoingEvents, generatingEvents, groupByKeys, isNewData);
	}

	public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents, MultiKeyUntyped[] groupByKeys, boolean isNewData)
	{
		log.debug(".sort");
		if (outgoingEvents == null || outgoingEvents.length < 2) 
		{
			return outgoingEvents;
		}

		// Create the multikeys of sort values
		List<MultiKeyUntyped> sortValuesMultiKeys = createSortProperties(generatingEvents, groupByKeys, isNewData);

		// Map the sort values to the corresponding outgoing events
		Map<MultiKeyUntyped, List<EventBean>> sortToOutgoing = new HashMap<MultiKeyUntyped, List<EventBean>>();
		int countOne = 0;
		for (MultiKeyUntyped sortValues : sortValuesMultiKeys)
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
		Set<MultiKeyUntyped> sortSet = new LinkedHashSet<MultiKeyUntyped>(sortValuesMultiKeys);
		EventBean[] result = new EventBean[outgoingEvents.length];
		int countTwo = 0;
		for (MultiKeyUntyped sortValues : sortSet)
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
	
	private List<MultiKeyUntyped> createSortProperties(EventBean[][] generatingEvents, MultiKeyUntyped[] groupByKeys, boolean isNewData)
	{
		MultiKeyUntyped[] sortProperties = new MultiKeyUntyped[generatingEvents.length];

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
				values[countTwo++] = sortNode.evaluate(eventsPerStream, isNewData);
			}

			sortProperties[count] = new MultiKeyUntyped(values);
			count++;
		}
		return Arrays.asList(sortProperties);
	}

	private MultiKeyUntyped generateGroupKey(EventBean[] eventsPerStream, boolean isNewData)
	{
		Object[] keys = new Object[groupByNodes.size()];

		int count = 0;
		for (ExprNode exprNode : groupByNodes) 
		{
			keys[count] = exprNode.evaluate(eventsPerStream, isNewData);
			count++;
		}

		return new MultiKeyUntyped(keys);
	}

    public EventBean[] sort(EventBean[] outgoingEvents, MultiKeyUntyped[] orderKeys)
    {
        TreeMap<MultiKeyUntyped, Object> sort = new TreeMap<MultiKeyUntyped, Object>(comparator);

        if (outgoingEvents == null || outgoingEvents.length < 2)
        {
            return outgoingEvents;
        }

        for (int i = 0; i < outgoingEvents.length; i++)
        {
            Object entry = sort.get(orderKeys[i]);
            if (entry == null)
            {
                sort.put(orderKeys[i], outgoingEvents[i]);
            }
            else if (entry instanceof EventBean)
            {
                List<EventBean> list = new ArrayList<EventBean>();
                list.add((EventBean)entry);
                list.add(outgoingEvents[i]);
                sort.put(orderKeys[i], list);
            }
            else
            {
                List<EventBean> list = (List<EventBean>) entry;
                list.add(outgoingEvents[i]);
            }
        }

        EventBean[] result = new EventBean[outgoingEvents.length];
        int count = 0;
        for (Object entry : sort.values())
        {
            if (entry instanceof List)
            {
                List<EventBean> output = (List<EventBean>) entry;
                for(EventBean event : output)
                {
                    result[count++] = event;
                }
            }
            else
            {
                result[count++] = (EventBean) entry;
            }
        }
        return result;
    }

    private MultiKeyUntyped[] generateGroupKeys(EventBean[][] generatingEvents, boolean isNewData)
	{
		MultiKeyUntyped keys[] = new MultiKeyUntyped[generatingEvents.length];

		int count = 0;
		for (EventBean[] eventsPerStream : generatingEvents) 
		{
			keys[count++] = generateGroupKey(eventsPerStream, isNewData);
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
