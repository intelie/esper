using System;

using net.esper.collection;
using net.esper.events;
using net.esper.eql.expression;

namespace net.esper.eql.core
{
    /// <summary>
    /// Implementation for handling aggregation without any grouping (no group-by).
    /// </summary>

    public class AggregationServiceGroupAllImpl : AggregationServiceBase
    {
        /// <summary>
        /// Sets the current row.
        /// </summary>
        /// <value>The current row.</value>
        override public MultiKey<Object> CurrentRow
        {
            set
            {
                // no action needed - this implementation does not group and the current row is the single group
            }
        }

        /// <summary> Ctor.</summary>
        /// <param name="evaluators">evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
        /// </param>
        /// <param name="aggregators">collect the aggregation state that evaluators evaluate to
        /// </param>
        public AggregationServiceGroupAllImpl(ExprEvaluator[] evaluators, Aggregator[] aggregators)
            : base(evaluators, aggregators)
        {
        }

        /// <summary>
        /// Apply events as entering a window (new events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public override void ApplyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                aggregators[j].Enter(columnResult);
            }
        }

        /// <summary>
        /// Apply events as leaving a window (old events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public override void ApplyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                aggregators[j].Leave(columnResult);
            }
        }

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
        public override Object GetValue(int column)
        {
            return aggregators[column].Value;
        }
    }
}