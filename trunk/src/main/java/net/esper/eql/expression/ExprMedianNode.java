package net.esper.eql.expression;

import net.esper.collection.SortedDoubleVector;
import net.esper.eql.core.Aggregator;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents the median(...) aggregate function is an expression tree.
 */
public class ExprMedianNode extends ExprAggregateNode
{
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprMedianNode(boolean distinct)
    {
        super(distinct);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        super.validateSingleNumericChild(streamTypeService);
        computer = new DoubleMedian();
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
        return "median";
    }

    public Class getType() throws ExprValidationException
    {
        return computer.getValueType();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprMedianNode))
        {
            return false;
        }

        return true;
    }

    /**
     * Average always generates double-types numbers.
     */
    public static class DoubleMedian implements Aggregator
    {
        private SortedDoubleVector vector;

        /**
         * Ctor.
         */
        public DoubleMedian()
        {
            this.vector = new SortedDoubleVector();
        }

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            double value = ((Number) object).doubleValue();
            vector.add(value);
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            double value = ((Number) object).doubleValue();
            vector.remove(value);
        }

        public Object getValue()
        {
            if (vector.size() == 0)
            {
                return null;
            }
            if (vector.size() == 1)
            {
                return vector.getValue(0);
            }

            int middle = vector.size() / 2;
            if (vector.size() % 2 == 0)
            {
                return (vector.getValue(middle - 1) + vector.getValue(middle)) / 2;
            }
            else
            {
                return vector.getValue(middle);
            }
        }

        public Class getValueType()
        {
            return Double.class;
        }

        public Aggregator newAggregator()
        {
            return new DoubleMedian();
        }
    }
}
