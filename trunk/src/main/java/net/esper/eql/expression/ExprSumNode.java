package net.esper.eql.expression;

import net.esper.eql.core.Aggregator;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents the sum(...) aggregate function is an expression tree.
 */
public class ExprSumNode extends ExprAggregateNode
{
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprSumNode(boolean distinct)
    {
        super(distinct);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        Class childType = super.validateSingleNumericChild(streamTypeService);

        computer = getSumComputer(childType);
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
        return "sum";
    }

    public Class getType() throws ExprValidationException
    {
        return computer.getValueType();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprSumNode))
        {
            return false;
        }

        return true;
    }

    /**
     * Creates the right aggregator instance for a given type of input to the aggregator.
     * @param type is the class for which to generate an aggregator for
     * @return aggregator for the type
     */
    private Aggregator getSumComputer(Class type)
    {
        if ((type == Long.class) || (type == long.class))
        {
            return new LongSum();
        }
        if ((type == Integer.class) || (type == int.class))
        {
            return new IntegerSum();
        }
        if ((type == Double.class) || (type == double.class))
        {
            return new DoubleSum();
        }
        if ((type == Float.class) || (type == float.class))
        {
            return new FloatSum();
        }
        return new NumberIntegerSum();
    }

    /**
     * Sum for any number value.
     */
    public static class NumberIntegerSum implements Aggregator
    {
        private int sum;
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints++;
            Number number = (Number) object;
            sum += number.intValue();
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
            Number number = (Number) object;
            sum -= number.intValue();
        }

        public Object getValue()
        {
            if (numDataPoints == 0)
            {
                return null;
            }
            return sum;
        }

        public Class getValueType()
        {
            return Integer.class;
        }

        public Aggregator newAggregator()
        {
            return new NumberIntegerSum();
        }
    }

    /**
     * Sum for integer values.
     */
    public static class IntegerSum implements Aggregator
    {
        private int sum;
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints++;
            sum += (Integer) object;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
            sum -= (Integer) object;
        }

        public Object getValue()
        {
            if (numDataPoints == 0)
            {
                return null;
            }
            return sum;
        }

        public Class getValueType()
        {
            return Integer.class;
        }

        public Aggregator newAggregator()
        {
            return new IntegerSum();
        }
    }

    /**
     * Sum for double values.
     */
    public static class DoubleSum implements Aggregator
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
            sum += (Double) object;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
            sum -= (Double) object;
        }

        public Object getValue()
        {
            if (numDataPoints == 0)
            {
                return null;
            }
            return sum;
        }

        public Class getValueType()
        {
            return Double.class;
        }

        public Aggregator newAggregator()
        {
            return new DoubleSum();
        }
    }

    /**
     * Sum for long values.
     */
    public static class LongSum implements Aggregator
    {
        private long sum;
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints++;
            sum += (Long) object;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
            sum -= (Long) object;
        }

        public Object getValue()
        {
            if (numDataPoints == 0)
            {
                return null;
            }
            return sum;
        }

        public Class getValueType()
        {
            return Long.class;
        }

        public Aggregator newAggregator()
        {
            return new LongSum();
        }
    }

    /**
     * Sum for float values.
     */
    public static class FloatSum implements Aggregator
    {
        private float sum;
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints++;
            sum += (Float) object;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            numDataPoints--;
            sum -= (Float) object;
        }

        public Object getValue()
        {
            if (numDataPoints == 0)
            {
                return null;
            }
            return sum;
        }

        public Class getValueType()
        {
            return Float.class;
        }

        public Aggregator newAggregator()
        {
            return new FloatSum();
        }
    }
}
