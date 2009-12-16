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
import com.espertech.esper.util.JavaClassHelper;

/**
 * Represents the rate(...) and aggregate function is an expression tree.
 */
public class ExprRateSnapshotAggNode extends ExprAggregateNode
{
    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprRateSnapshotAggNode(boolean distinct)
    {
        super(distinct);
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        // TODO
        String message = "The rate aggregation function requires a property or expression returning a non-constant long-type value (timestamp) as the first parameter";
        Class boxedParamOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());
        if (!this.getChildNodes().get(0).isConstantResult()) {
            throw new ExprValidationException(message);
        }
        long msec = (Integer) this.getChildNodes().get(0).evaluate(null, true, null);

        return methodResolutionService.makeRateEverAggregator(msec);
    }

    protected String getAggregationFunctionName()
    {
        return "rate";
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprRateSnapshotAggNode))
        {
            return false;
        }

        return true;
    }
}