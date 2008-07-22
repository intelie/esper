package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.core.MethodResolutionService;

import java.math.BigDecimal;

/**
 * Sum for BigInteger values.
 */
public class BigDecimalSumAggregator implements AggregationMethod
{
    private BigDecimal sum;
    private long numDataPoints;

    /**
     * Ctor.
     */
    public BigDecimalSumAggregator()
    {
        sum = BigDecimal.valueOf(0.0);
    }

    public void clear()
    {
        sum = BigDecimal.valueOf(0.0);
        numDataPoints = 0;
    }

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints++;
        sum = sum.add((BigDecimal)object);
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints--;
        sum = sum.subtract((BigDecimal)object);
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
        return BigDecimal.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeSumAggregator(BigDecimal.class);
    }
}
