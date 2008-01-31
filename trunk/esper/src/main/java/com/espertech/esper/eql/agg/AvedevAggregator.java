package com.espertech.esper.eql.agg;

import com.espertech.esper.eql.agg.AggregationMethod;
import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.collection.RefCountedSet;

import java.util.Map;
import java.util.Iterator;

/**
 * Standard deviation always generates double-types numbers.
 */
public class AvedevAggregator implements AggregationMethod
{
    private RefCountedSet<Double> valueSet;
    private double sum;

    public void clear()
    {
        sum = 0;
        valueSet.clear();
    }

    /**
     * Ctor.
     */
    public AvedevAggregator()
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

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeAvedevAggregator();
    }
}
