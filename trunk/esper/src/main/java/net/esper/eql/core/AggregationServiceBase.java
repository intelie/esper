/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.eql.core.AggregationService;
import net.esper.eql.expression.ExprEvaluator;

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
