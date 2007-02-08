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
        override public MultiKey<Object> CurrentRow
        {
            set
            {
                // no action needed - this implementation does not group and the current row is the single group
            }
        }

        /// <summary> Ctor.</summary>
        /// <param name="evaluators">- evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
        /// </param>
        /// <param name="aggregators">- collect the aggregation state that evaluators evaluate to
        /// </param>
        public AggregationServiceGroupAllImpl(ExprEvaluator[] evaluators, Aggregator[] aggregators)
            : base(evaluators, aggregators)
        {
        }

        public override void applyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                aggregators[j].enter(columnResult);
            }
        }

        public override void applyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            for (int j = 0; j < evaluators.Length; j++)
            {
                Object columnResult = evaluators[j].Evaluate(eventsPerStream);
                aggregators[j].leave(columnResult);
            }
        }

        public override Object getValue(int column)
        {
            return aggregators[column].Value;
        }
    }
}