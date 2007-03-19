/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.eql.core.Aggregator;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents the avg(...) aggregate function is an expression tree.
 */
public class ExprAvgNode extends ExprAggregateNode
{
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprAvgNode(boolean distinct)
    {
        super(distinct);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        super.validateSingleNumericChild(streamTypeService);
        computer = new DoubleAvg();
    }

    public Aggregator getAggregationFunction()
    {
        if (computer == null)
        {
            throw new IllegalStateException("Node has not been initalized through validate call");
        }
        return computer;
    }

    protected String getAggregationFunctionName()
    {
        return "avg";
    }

    public Class getType() throws ExprValidationException
    {
        return computer.getValueType();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprAvgNode))
        {
            return false;
        }

        return true;
    }

    /**
     * Average always generates double-types numbers.
     */
    public static class DoubleAvg implements Aggregator
    {
        private double sum;
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints++;
            sum += ((Number) object).doubleValue();
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
            sum -= ((Number) object).doubleValue();
        }

        public Object getValue()
        {
            if (numDataPoints == 0)
            {
                return null;
            }
            return sum / numDataPoints;
        }

        public Class getValueType()
        {
            return Double.class;
        }

        public Aggregator newAggregator()
        {
            return new DoubleAvg();
        }
    }
}
