/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.AggregationMethodFactory;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;

/**
 * Represents the "lastever" aggregate function is an expression tree.
 */
public class ExprLastEverNode extends ExprAggregateNodeBase
{
    private static final long serialVersionUID = -435756490067654566L;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprLastEverNode(boolean distinct)
    {
        super(distinct);
    }

    public AggregationMethodFactory validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        if (this.getChildNodes().size() > 2)
        {
            throw new ExprValidationException("Last aggregation function must have at most 2 child nodes");
        }
        if (this.getChildNodes().size() == 2) {
            super.validateFilter(this.getChildNodes().get(1).getExprEvaluator());
        }
        return new ExprLastEverNodeFactory(this.getChildNodes().get(0).getExprEvaluator().getType(), this.getChildNodes().size() == 2);
    }

    protected String getAggregationFunctionName()
    {
        return "lastever";
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        return node instanceof ExprLastEverNode;
    }
}