using System;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.core
{

    /// <summary> All aggregation services require evaluation nodes which supply the value to be aggregated (summed, averaged, etc.)
    /// and aggregation state factories to make new aggregation states.
    /// </summary>
    public abstract class AggregationServiceBase : AggregationService
    {
        public abstract MultiKey<Object> CurrentRow { set;}

        /// <summary> Evaluation nodes under.</summary>
        internal ExprEvaluator[] evaluators;

        /// <summary> Aggregation states and factories.</summary>
        internal Aggregator[] aggregators;

        /// <summary> Ctor.</summary>
        /// <param name="evaluators">- are the child node of each aggregation function used for computing the value to be aggregated
        /// </param>
        /// <param name="aggregators">- aggregation states/factories
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
        public abstract void applyEnter(EventBean[] param1, MultiKey<Object> param2);
        public abstract void applyLeave(EventBean[] param1, MultiKey<Object> param2);
        public abstract Object getValue(int param1);
    }
}