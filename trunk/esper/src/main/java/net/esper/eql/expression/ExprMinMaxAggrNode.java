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
import net.esper.type.MinMaxTypeEnum;

/**
 * Represents the min/max(distinct? ...) aggregate function is an expression tree.
 */
public class ExprMinMaxAggrNode extends ExprAggregateNode
{
    private final MinMaxTypeEnum minMaxTypeEnum;

    /**
     * Ctor.
     * @param distinct - indicator whether distinct values of all values min/max
     * @param minMaxTypeEnum - enum for whether to minimum or maximum compute
     */
    public ExprMinMaxAggrNode(boolean distinct, MinMaxTypeEnum minMaxTypeEnum)
    {
        super(distinct);
        this.minMaxTypeEnum = minMaxTypeEnum;
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException(minMaxTypeEnum.toString() + " node must have exactly 1 child node");
        }
        ExprNode child = this.getChildNodes().get(0);
        return methodResolutionService.makeMinMaxAggregator(minMaxTypeEnum, child.getType());
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprMinMaxAggrNode))
        {
            return false;
        }

        ExprMinMaxAggrNode other = (ExprMinMaxAggrNode) node;
        return other.minMaxTypeEnum == this.minMaxTypeEnum;
    }

    /**
     * Returns the indicator for minimum or maximum.
     * @return min/max indicator
     */
    public MinMaxTypeEnum getMinMaxTypeEnum()
    {
        return minMaxTypeEnum;
    }

    protected String getAggregationFunctionName()
    {
        return minMaxTypeEnum.getExpressionText();
    }
}
