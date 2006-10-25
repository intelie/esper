package net.esper.eql.core;

import net.esper.eql.expression.ExprEvaluator;
import net.esper.persist.LogContextNode;

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
    protected LogContextNode<Aggregator[]> aggregationState;

    /**
     * Ctor.
     * @param evaluators - are the child node of each aggregation function used for computing the value to be aggregated
     * @param aggregationState - aggregation states/factories
     */
    public AggregationServiceBase(ExprEvaluator evaluators[], LogContextNode<Aggregator[]> aggregationState)
    {
        this.evaluators = evaluators;
        this.aggregationState = aggregationState;

        if (evaluators.length != aggregationState.getState().length)
        {
            throw new IllegalArgumentException("Expected the same number of evaluates as computer prototypes");
        }
    }
}
