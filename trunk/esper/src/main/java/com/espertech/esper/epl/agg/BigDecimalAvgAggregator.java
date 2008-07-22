package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.core.MethodResolutionService;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Average that generates a BigDecimal numbers.
 */
public class BigDecimalAvgAggregator implements AggregationMethod
{
    private BigDecimal sum;
    private long numDataPoints;

    /**
     * Ctor.
     */
    public BigDecimalAvgAggregator()
    {
        sum = new BigDecimal(0.0);
    }

    public void clear()
    {
        sum = new BigDecimal(0.0);
        numDataPoints = 0;
    }

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints++;
        if (object instanceof BigInteger)
        {
            sum = sum.add(new BigDecimal((BigInteger) object));
            return;
        }
        sum = sum.add((BigDecimal) object);
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints--;
        if (object instanceof BigInteger)
        {
            sum = sum.subtract(new BigDecimal((BigInteger) object));
            return;
        }
        sum = sum.subtract((BigDecimal) object);
    }

    public Object getValue()
    {
        if (numDataPoints == 0)
        {
            return null;
        }
        return sum.divide(BigDecimal.valueOf(numDataPoints));
    }

    public Class getValueType()
    {
        return Double.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeAvgAggregator(BigDecimal.class);
    }
}
