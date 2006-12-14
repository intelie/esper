package net.esper.eql.expression;

import net.esper.collection.RefCountedSet;
import net.esper.eql.core.Aggregator;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

import java.util.Map;
import java.util.Iterator;

/**
 * Represents the avedev(...) aggregate function is an expression tree.
 */
public class ExprAvedevNode extends ExprAggregateNode
{
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprAvedevNode(boolean distinct)
    {
        super(distinct);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        super.validateSingleNumericChild(streamTypeService);
        computer = new DoubleAvedev();
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
        if (!(node instanceof ExprAvedevNode))
        {
            return false;
        }

        return true;
    }

    protected String getAggregationFunctionName()
    {
        return "avedev";
    }

    /**
     * Standard deviation always generates double-types numbers.
     */
    public static class DoubleAvedev implements Aggregator
    {
        private RefCountedSet<Double> valueSet;
        private double sum;

        /**
         * Ctor.
         */
        public DoubleAvedev()
        {
            valueSet = new RefCountedSet<Double>();
        }

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }

            double value = ((Number) object).doubleValue();
            valueSet.add(value);
            sum += value;
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }

            double value = ((Number) object).doubleValue();
            valueSet.remove(value);
            sum -= value;
        }

        public Object getValue()
        {
            int datapoints = valueSet.size();

            if (datapoints == 0)
            {
                return null;
            }
            
            double total = 0;
            double avg = sum / datapoints;

            for (Iterator<Map.Entry<Double, Integer>> it = valueSet.entryIterator(); it.hasNext();)
            {
                Map.Entry<Double, Integer> entry = it.next();
                total += entry.getValue() * Math.abs(entry.getKey() - avg);
            }

            return total / datapoints;
        }

        public Class getValueType()
        {
            return Double.class;
        }

        public Aggregator newAggregator()
        {
            return new DoubleAvedev();
        }
    }
}
