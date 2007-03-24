package net.esper.eql.agg;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;
import net.esper.collection.SortedDoubleVector;

/**
 * Median aggregation.
 */
public class MedianAggregator implements AggregationMethod
{
    private SortedDoubleVector vector;

    /**
     * Ctor.
     */
    public MedianAggregator()
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

        int middle = vector.size() >> 1;
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

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeMedianAggregator();
    }
}
