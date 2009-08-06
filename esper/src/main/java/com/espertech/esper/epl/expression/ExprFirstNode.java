/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;

/**
 * Represents the "first" aggregate function is an expression tree.
 */
public class ExprFirstNode extends ExprAggregateNode
{
    private static final long serialVersionUID = 1436994080693454617L;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprFirstNode(boolean distinct)
    {
        super(distinct);
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("Count node must have 1 child nodes");
        }
        return methodResolutionService.makeFirstValueAggregator(this.getChildNodes().get(0).getType());
    }

    protected String getAggregationFunctionName()
    {
        return "first";
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprFirstNode))
        {
            return false;
        }

        return true;
    }
}