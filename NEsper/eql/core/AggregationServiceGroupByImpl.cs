using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.core
{
    /// <summary> Implementation for handling aggregation with grouping by group-keys.</summary>
    public class AggregationServiceGroupByImpl : AggregationServiceBase
    {
        override public MultiKey<Object> CurrentRow
        {
            set
            {
                currentAggregatorRow = null ;
                if ( ! aggregatorsPerGroup.TryGetValue( value, out currentAggregatorRow ) )
                {
                    currentAggregatorRow = new Aggregator[this.aggregators.Length];
                    for (int j = 0; j < currentAggregatorRow.Length; j++)
                    {
                        currentAggregatorRow[j] = aggregators[j].newAggregator();
                    }
                    aggregatorsPerGroup[value] = currentAggregatorRow;
                }
            }
        }

        // maintain for each group a row of aggregator states that the expression node canb pull the data from via index
        private IDictionary<MultiKey<Object>, Aggregator[]> aggregatorsPerGroup;

        // maintain a current row for random access into the aggregator state table
        // (row=groups, columns=expression nodes that have aggregation functions)
        private Aggregator[] currentAggregatorRow;

        /// <summary> Ctor.</summary>
        /// <param name="evaluators">- evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
        /// </param>
        /// <param name="aggregators">- collect the aggregation state that evaluators evaluate to, act as factories for new
        /// aggregation states for each group
        /// </param>
        public AggregationServiceGroupByImpl(ExprEvaluator[] evaluators, Aggregator[] aggregators)
            : base(evaluators, aggregators)
        {
            this.aggregatorsPerGroup = new Dictionary<MultiKey<Object>, Aggregator[]>();
        }

        public override void applyEnter(EventBean[] eventsPerStream, MultiKey<Object> groupByKey)
        {
            Aggregator[] groupAggregators = null;

            // The aggregators for this group do not exist, need to create them from the prototypes
            if (!aggregatorsPerGroup.TryGetValue(groupByKey, out groupAggregators))
            {
                groupAggregators = new Aggregator[this.aggregators.Length];
                for (int j = 0; j < groupAggregators.Length; j++)
                {
                    groupAggregators[j] = aggregators[j].newAggregator();
                }
                aggregatorsPerGroup[groupByKey] = groupAggregators;
            }

            // For this row, evaluate sub-expressions, enter result
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                groupAggregators[j].enter(columnResult);
            }
        }

        public override void applyLeave(EventBean[] eventsPerStream, MultiKey<Object> groupByKey)
        {
            Aggregator[] groupAggregators = null;
            // The aggregators for this group do not exist, need to create them from the prototypes
            if (!aggregatorsPerGroup.TryGetValue(groupByKey, out groupAggregators))
            {
                groupAggregators = new Aggregator[this.aggregators.Length];
                for (int j = 0; j < groupAggregators.Length; j++)
                {
                    groupAggregators[j] = aggregators[j].newAggregator();
                }
                aggregatorsPerGroup[groupByKey] = groupAggregators;
            }

            // For this row, evaluate sub-expressions, enter result
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                groupAggregators[j].leave(columnResult);
            }
        }

        public override Object getValue(int column)
        {
            return currentAggregatorRow[column].Value;
        }
    }
}