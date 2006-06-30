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
import net.esper.event.EventType;
import net.esper.event.EventAdapterService;

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
	private final EventType sortType;
	private final boolean needsGroupByKeys;
	private final AggregationService aggregationService;
    private final EventAdapterService eventAdapterService;

	private final Comparator<EventBean> comparator;

	/**
	 * Ctor.
	 * 
	 * @param orderByList -
	 *            the nodes that generate the keys to sort events on
	 * @param groupByNodes -
	 *            generate the keys for determining aggregation groups
	 * @param sortType -
	 *            event type that represents the event properties to sort on
	 * @param needsGroupByKeys -
	 *            indicates whether this processor needs to have individual
	 *            group by keys to evaluate the sort condition successfully
	 * @param aggregationService -
	 *            used to evaluate aggregate functions in the group-by and
	 *            sort-by clauses
	 */
	public OrderByProcessorSimple(final List<Pair<ExprNode, Boolean>> orderByList,
								  List<ExprNode> groupByNodes, 
								  EventType sortType,
								  boolean needsGroupByKeys, 
								  AggregationService aggregationService,
                                  EventAdapterService eventAdapterService)
	{
		this.orderByList = orderByList;
		this.groupByNodes = groupByNodes;
		this.sortType = sortType;
		this.needsGroupByKeys = needsGroupByKeys;
		this.aggregationService = aggregationService;
        this.eventAdapterService = eventAdapterService;
		this.comparator = getComparator();
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

		// Create the events with sort properties
		List<EventBean> sortEvents = createSortEvents(generatingEvents, groupByKeys);

		// Map the sort events to the corresponding outgoing events
		Map<EventBean, List<EventBean>> sortToOutgoing = new HashMap<EventBean, List<EventBean>>();
		int countOne = 0;
		for (EventBean sortEvent : sortEvents) 
		{
			List<EventBean> list = sortToOutgoing.get(sortEvent);
			if (list == null) 
			{
				list = new ArrayList<EventBean>();
			}
			list.add(outgoingEvents[countOne++]);
			sortToOutgoing.put(sortEvent, list);
		}

		// Sort the sort events
		Collections.sort(sortEvents, comparator);

		// Sort the outgoing events in the same order
		Set<EventBean> sortSet = new LinkedHashSet<EventBean>(sortEvents);
		EventBean[] result = new EventBean[outgoingEvents.length];
		int countTwo = 0;
		for (EventBean sortEvent : sortSet) 
		{
			Collection<EventBean> output = sortToOutgoing.get(sortEvent);
			for(EventBean event : output)
			{
				result[countTwo++] = event;
			}
		}

		return result;
	}

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
	
	private List<EventBean> createSortEvents(EventBean[][] generatingEvents, MultiKey[] groupByKeys) 
	{
		EventBean[] sortEvents = new EventBean[generatingEvents.length];

		int count = 0;
		for (EventBean[] eventsPerStream : generatingEvents) 
		{
			// Make a new event that contains the sort-by values.
			if (needsGroupByKeys) 
			{
				aggregationService.setCurrentRow(groupByKeys[count]);
			}
			Map<String, Object> props = new HashMap<String, Object>();
			for (Pair<ExprNode, Boolean> sortPair : orderByList) 
			{
				ExprNode sortNode = sortPair.getFirst();
				props.put(sortNode.toExpressionString(), sortNode.evaluate(eventsPerStream));
			}
			EventBean sortEvent = eventAdapterService.createMapFromValues(props, sortType);
			sortEvents[count] = sortEvent;
			count++;
		}
		return Arrays.asList(sortEvents);
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

	private Comparator<EventBean> getComparator() 
	{
		Comparator<EventBean> comparator = new Comparator<EventBean>() 
		{
			public int compare(EventBean eventOne, EventBean eventTwo) 
			{
				for (Pair<ExprNode, Boolean> pair : orderByList) 
				{
					String propertyName = pair.getFirst().toExpressionString();
					Object valueOne = eventOne.get(propertyName);
					Object valueTwo = eventTwo.get(propertyName);
					boolean descending = pair.getSecond();

					int comparisonResult = compareValues(valueOne, valueTwo, descending);
					if (comparisonResult != 0) 
					{
						return comparisonResult;
					}
				}
				return 0;
			}
		};
		return comparator;
	}
}
