using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.core
{
    /// <summary>
    /// Implementation for handling aggregation with grouping by group-keys.
    /// </summary>
    public class AggregationServiceGroupByImpl : AggregationServiceBase
    {
        /// <summary>
        /// Sets the current row.
        /// </summary>
        /// <value>The current row.</value>
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
                        currentAggregatorRow[j] = aggregators[j].NewAggregator();
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
        /// <param name="evaluators">evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
        /// </param>
        /// <param name="aggregators">collect the aggregation state that evaluators evaluate to, act as factories for new
        /// aggregation states for each group
        /// </param>
        public AggregationServiceGroupByImpl(ExprEvaluator[] evaluators, Aggregator[] aggregators)
            : base(evaluators, aggregators)
        {
            this.aggregatorsPerGroup = new Dictionary<MultiKey<Object>, Aggregator[]>();
        }

        /// <summary>
        /// Applies the enter.
        /// </summary>
        /// <param name="eventsPerStream">The events per stream.</param>
        /// <param name="groupByKey">The group by key.</param>
        public override void ApplyEnter(EventBean[] eventsPerStream, MultiKey<Object> groupByKey)
        {
            Aggregator[] groupAggregators = null;

            // The aggregators for this group do not exist, need to create them from the prototypes
            if (!aggregatorsPerGroup.TryGetValue(groupByKey, out groupAggregators))
            {
                groupAggregators = new Aggregator[this.aggregators.Length];
                for (int j = 0; j < groupAggregators.Length; j++)
                {
                    groupAggregators[j] = aggregators[j].NewAggregator();
                }
                aggregatorsPerGroup[groupByKey] = groupAggregators;
            }

            // For this row, evaluate sub-expressions, enter result
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                groupAggregators[j].Enter(columnResult);
            }
        }

        /// <summary>
        /// Applies the leave.
        /// </summary>
        /// <param name="eventsPerStream">The events per stream.</param>
        /// <param name="groupByKey">The group by key.</param>
        public override void ApplyLeave(EventBean[] eventsPerStream, MultiKey<Object> groupByKey)
        {
            Aggregator[] groupAggregators = null;
            // The aggregators for this group do not exist, need to create them from the prototypes
            if (!aggregatorsPerGroup.TryGetValue(groupByKey, out groupAggregators))
            {
                groupAggregators = new Aggregator[this.aggregators.Length];
                for (int j = 0; j < groupAggregators.Length; j++)
                {
                    groupAggregators[j] = aggregators[j].NewAggregator();
                }
                aggregatorsPerGroup[groupByKey] = groupAggregators;
            }

            // For this row, evaluate sub-expressions, enter result
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                groupAggregators[j].Leave(columnResult);
            }
        }

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
        public override Object GetValue(int column)
        {
            return currentAggregatorRow[column].Value;
        }
    }
}