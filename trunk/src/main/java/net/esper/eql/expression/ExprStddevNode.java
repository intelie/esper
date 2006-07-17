package net.esper.eql.expression;

/**
 * Represents the stddev(...) aggregate function is an expression tree.
 */
public class ExprStddevNode extends ExprAggregateNode
{
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprStddevNode(boolean distinct)
    {
        super(distinct);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        super.validateSingleNumericChild(streamTypeService);
        computer = new DoubleStddev();
    }

    public Aggregator getAggregationFunction()
    {
        if (computer == null)
        {
            throw new IllegalStateException("Node has not been initalized through validate call");
        }
        return computer;
    }

    public Class getType() throws ExprValidationException
    {
        return computer.getValueType();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprStddevNode))
        {
            return false;
        }

        return true;
    }


    protected String getAggregationFunctionName()
    {
        return "stddev";
    }

    /**
     * Standard deviation always generates double-types numbers.
     */
    public static class DoubleStddev implements Aggregator
    {
        private double sum;
        private double sumSq;
        private long numDataPoints;

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }

            double value = ((Number) object).doubleValue();

            numDataPoints++;
            sum += value;
            sumSq += value * value;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }

            double value = ((Number) object).doubleValue();

            numDataPoints--;
            sum -= value;
            sumSq -= value * value;
        }

        public Object getValue()
        {
            if (numDataPoints < 2)
            {
                return null;
            }

            double variance = (sumSq - sum * sum / numDataPoints) / (numDataPoints - 1);
            return Math.sqrt(variance);
        }

        public Class getValueType()
        {
            return Double.class;
        }

        public Aggregator newAggregator()
        {
            return new DoubleStddev();
        }
    }
}
