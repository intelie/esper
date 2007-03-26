using System;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.core
{
    /// <summary>
    /// All aggregation services require evaluation nodes which supply the value to
    /// be aggregated (summed, averaged, etc.) and aggregation state factories to make
    /// new aggregation states.
    /// </summary>
    public abstract class AggregationServiceBase : AggregationService
    {
        /// <summary>
        /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
        /// </summary>
        /// <value></value>
        public abstract MultiKey<Object> CurrentRow { set;}

        /// <summary> Evaluation nodes under.</summary>
        internal ExprEvaluator[] evaluators;

        /// <summary> Aggregation states and factories.</summary>
        internal Aggregator[] aggregators;

        /// <summary> Ctor.</summary>
        /// <param name="evaluators">are the child node of each aggregation function used for computing the value to be aggregated
        /// </param>
        /// <param name="aggregators">aggregation states/factories
        /// </param>
        public AggregationServiceBase(ExprEvaluator[] evaluators, Aggregator[] aggregators)
        {
            this.evaluators = evaluators;
            this.aggregators = aggregators;

            if (evaluators.Length != aggregators.Length)
            {
                throw new ArgumentException("Expected the same number of evaluates as computer prototypes");
            }
        }

        /// <summary> Apply events as entering a window (new events).</summary>
        /// <param name="eventsPerStream">events for each stream entering window
        /// </param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.
        /// </param>
        public abstract void ApplyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow);

        /// <summary> Apply events as leaving a window (old events).</summary>
        /// <param name="eventsPerStream">events for each stream entering window
        /// </param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.
        /// </param>
        public abstract void ApplyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow);

        /// <summary> Returns current aggregation state, for use by expression node representing an aggregation function.</summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row
        /// </param>
        /// <returns> current aggragation state
        /// </returns>
        public abstract Object GetValue(int column);
    }
}