/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;

/**
 * Represents the count(...) and count(*) and count(distinct ...) aggregate function is an expression tree.
 */
public class ExprCountNode extends ExprAggregateNode
{
    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprCountNode(boolean distinct)
    {
        super(distinct);
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService) throws ExprValidationException
    {
        // Empty child node list signals count(*), does not ignore nulls
        if (this.getChildNodes().isEmpty())
        {
            return methodResolutionService.getCountAggregator(false);
        }
        else
        {
            // else ignore nulls
            if (this.getChildNodes().size() != 1)
            {
                throw new ExprValidationException("Count node must have zero or 1 child nodes");
            }
            return methodResolutionService.getCountAggregator(true);
        }
    }

    protected String getAggregationFunctionName()
    {
        return "count";
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprCountNode))
        {
            return false;
        }

        return true;
    }
}
