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
 * Represents the count(...) and count(*) and count(distinct ...) aggregate function is an expression tree.
 */
public class ExprCountNode extends ExprAggregateNode
{
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprCountNode(boolean distinct)
    {
        super(distinct);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        // Empty child node list signals count(*)
        if (this.getChildNodes().isEmpty())
        {
            computer = new DatapointAggregator();
        }
        else
        {
            if (this.getChildNodes().size() != 1)
            {
                throw new ExprValidationException("Count node must have zero or 1 child nodes");
            }
            computer = new NonNullDatapointAggregator();
        }
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
        return "count";
    }

    public Class getType() throws ExprValidationException
    {
        return computer.getValueType();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprCountNode))
        {
            return false;
        }

        return true;
    }

    /**
     * Counts all datapoints including null values.
     */
    public static class DatapointAggregator implements Aggregator
    {
        private long numDataPoints;

        public void enter(Object object)
        {
            numDataPoints++;
        }

        public void leave(Object object)
        {
            numDataPoints--;
        }

        public Object getValue()
        {
            return numDataPoints;
        }

        public Class getValueType()
        {
            return Long.class;
        }

        public Aggregator newAggregator()
        {
            return new DatapointAggregator();
        }
    }

    /**
     * Count all non-null values.
     */
    public static class NonNullDatapointAggregator implements Aggregator
    {
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints++;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
        }

        public Object getValue()
        {
            return numDataPoints;
        }

        public Class getValueType()
        {
            return Long.class;
        }

        public Aggregator newAggregator()
        {
            return new NonNullDatapointAggregator();
        }
    }
}
