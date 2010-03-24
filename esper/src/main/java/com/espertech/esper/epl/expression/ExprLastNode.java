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
 * Represents the "last" aggregate function is an expression tree.
 */
public class ExprLastNode extends ExprAggregateNode
{
    private static final long serialVersionUID = -435756490067654566L;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprLastNode(boolean distinct)
    {
        super(distinct);
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("Last aggregation function must have 1 child node");
        }
        return methodResolutionService.makeLastValueAggregator(this.getChildNodes().get(0).getType());
    }

    protected String getAggregationFunctionName()
    {
        return "last";
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        return node instanceof ExprLastNode;

    }
}