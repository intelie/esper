using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.util;

using net.esper.eql;
using net.esper.eql.expression;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.core
{
    /// <summary>
    /// An order-by processor that sorts events according to the expressions
    /// in the order_by clause.
    /// </summary>

    public class OrderByProcessorSimple : OrderByProcessor
    {
        private static readonly Log log = LogFactory.GetLog(typeof(OrderByProcessorSimple));

        private readonly IList<Pair<ExprNode, Boolean>> orderByList;
        private readonly IList<ExprNode> groupByNodes;
        private readonly Boolean needsGroupByKeys;
        private readonly AggregationService aggregationService;

        private readonly IComparer<MultiKey<Object>> comparator;

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
        public OrderByProcessorSimple(IList<Pair<ExprNode, Boolean>> orderByList,
                                      IList<ExprNode> groupByNodes,
                                      Boolean needsGroupByKeys,
                                      AggregationService aggregationService)
        {
            this.orderByList = orderByList;
            this.groupByNodes = groupByNodes;
            this.needsGroupByKeys = needsGroupByKeys;
            this.aggregationService = aggregationService;

            this.comparator = new MultiKeyComparator<Object>(getIsDescendingValues());
        }


        public EventBean[] sort(EventBean[] outgoingEvents, EventBean[][] generatingEvents)
        {
            if (outgoingEvents == null || outgoingEvents.Length < 2)
            {
                return outgoingEvents;
            }

            // Get the group by keys if needed
            MultiKey<Object>[] groupByKeys = null;
            if (needsGroupByKeys)
            {
                groupByKeys = generateGroupKeys(generatingEvents);
            }

            return sort(outgoingEvents, generatingEvents, groupByKeys);
        }

        public EventBean[] sort(
            EventBean[] outgoingEvents,
            EventBean[][] generatingEvents,
            MultiKey<Object>[] groupByKeys)
        {
            log.Debug(".sort");
            if (outgoingEvents == null || outgoingEvents.Length < 2)
            {
                return outgoingEvents;
            }

            // Create the multikeys of sort values
            MultiKey<Object>[] sortValuesMultiKeys = createSortProperties(generatingEvents, groupByKeys);

            // Map the sort values to the corresponding outgoing events
            IDictionary<MultiKey<Object>, IList<EventBean>> sortToOutgoing = new Dictionary<MultiKey<Object>, IList<EventBean>>();
            int countOne = 0;
            foreach (MultiKey<Object> sortValues in sortValuesMultiKeys)
            {
                IList<EventBean> list = null;
                if ( ! sortToOutgoing.TryGetValue( sortValues, out list ) )
                {
                    list = new List<EventBean>();
                }
                list.Add(outgoingEvents[countOne++]);
                sortToOutgoing[sortValues] = list;
            }

            // Sort the sort values
			Array.Sort( sortValuesMultiKeys, comparator );

            // Sort the outgoing events in the same order
            ISet<MultiKey<Object>> sortSet = new LinkedHashSet<MultiKey<Object>>() ;
            sortSet.AddAll( sortValuesMultiKeys );
            
            EventBean[] result = new EventBean[outgoingEvents.Length];
            int countTwo = 0;
            foreach (MultiKey<Object> sortValues in sortSet)
            {
                IList<EventBean> output = null ;
                if ( sortToOutgoing.TryGetValue( sortValues, out output ) )
                {
	                foreach (EventBean ev in output)
	                {
	                    result[countTwo++] = ev;
	                }
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
        protected static int compareValues(Object valueOne, Object valueTwo, Boolean descending)
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

            IComparable comparable1 = valueOne as IComparable;
            if ( comparable1 == null )
            {
            	throw new InvalidCastException("Sort by clause cannot sort objects of type " + valueOne.GetType());
            }

            return comparable1.CompareTo( valueTwo ) ;
        }

        private MultiKey<Object>[] createSortProperties(
            EventBean[][] generatingEvents,
            MultiKey<Object>[] groupByKeys)
        {
            MultiKey<Object>[] sortProperties = new MultiKey<Object>[generatingEvents.Length];

            int count = 0;
            foreach (EventBean[] eventsPerStream in generatingEvents)
            {
                // Make a new multikey that contains the sort-by values.
                if (needsGroupByKeys)
                {
                    aggregationService.CurrentRow = groupByKeys[count];
                }

                Object[] values = new Object[orderByList.Count];
                int countTwo = 0;
                foreach (Pair<ExprNode, Boolean> sortPair in orderByList)
                {
                    ExprNode sortNode = sortPair.First;
                    values[countTwo++] = sortNode.Evaluate(eventsPerStream);
                }

                sortProperties[count] = new MultiKey<Object>(values);
                count++;
            }

            return sortProperties;
        }

        private MultiKey<Object> generateGroupKey(EventBean[] eventsPerStream)
        {
            Object[] keys = new Object[groupByNodes.Count];

            int count = 0;
            foreach (ExprNode exprNode in groupByNodes)
            {
                keys[count] = exprNode.Evaluate(eventsPerStream);
                count++;
            }

            return new MultiKey<Object>(keys);
        }

        private MultiKey<Object>[] generateGroupKeys(EventBean[][] generatingEvents)
        {
            MultiKey<Object>[] keys = new MultiKey<Object>[generatingEvents.Length];

            int count = 0;
            foreach (EventBean[] eventsPerStream in generatingEvents)
            {
                keys[count++] = generateGroupKey(eventsPerStream);
            }

            return keys;
        }

        private Boolean[] getIsDescendingValues()
        {
            Boolean[] isDescendingValues = new Boolean[orderByList.Count];
            int count = 0;
            foreach (Pair<ExprNode, Boolean> pair in orderByList)
            {
                isDescendingValues[count++] = pair.Second;
            }
            return isDescendingValues;
        }
    }
}
