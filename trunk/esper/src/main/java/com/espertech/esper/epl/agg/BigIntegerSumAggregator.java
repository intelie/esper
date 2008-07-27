package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.core.MethodResolutionService;

import java.math.BigInteger;

/**
 * Sum for BigInteger values.
 */
public class BigIntegerSumAggregator implements AggregationMethod
{
    private BigInteger sum;
    private long numDataPoints;

    /**
     * Ctor.
     */
    public BigIntegerSumAggregator()
    {
        sum = BigInteger.valueOf(0);
    }

    public void clear()
    {
        sum = BigInteger.valueOf(0);
        numDataPoints = 0;
    }

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints++;
        sum = sum.add((BigInteger)object);
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints--;
        sum = sum.subtract((BigInteger)object);
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
        return BigInteger.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeSumAggregator(BigInteger.class);
    }
}
