package net.esper.eql.expression;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;

/**
 * All aggregation services require evaluation nodes which supply the value to be aggregated (summed, averaged, etc.)
 * and aggregation state factories to make new aggregation states.
 */
public abstract class AggregationServiceBase implements AggregationService
{
    /**
     * Evaluation nodes under.
     */
    protected ExprEvaluator evaluators[];

    /**
     * Aggregation states and factories.
     */
    protected Aggregator aggregators[];

    /**
     * Ctor.
     * @param evaluators - are the child node of each aggregation function used for computing the value to be aggregated
     * @param aggregators - aggregation states/factories
     */
    public AggregationServiceBase(ExprEvaluator evaluators[], Aggregator aggregators[])
    {
        this.evaluators = evaluators;
        this.aggregators = aggregators;

        if (evaluators.length != aggregators.length)
        {
            throw new IllegalArgumentException("Expected the same number of evaluates as computer prototypes");
        }
    }
}
