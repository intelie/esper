package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.type.MinMaxTypeEnum;
import com.espertech.esper.collection.SortedRefCountedSet;

/**
 * Min/max aggregator for all values.
 */
public class MinMaxAggregator implements AggregationMethod
{
    private final MinMaxTypeEnum minMaxTypeEnum;
    private final Class returnType;

    private SortedRefCountedSet<Object> refSet;

    /**
     * Ctor.
     *
     * @param minMaxTypeEnum - enum indicating to return minimum or maximum values
     * @param returnType     - is the value type returned by aggregator
     */
    public MinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Class returnType)
    {
        this.minMaxTypeEnum = minMaxTypeEnum;
        this.returnType = returnType;
        this.refSet = new SortedRefCountedSet<Object>();
    }

    public void clear()
    {
        refSet.clear();
    }

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        refSet.add(object);
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        refSet.remove(object);
    }

    public Object getValue()
    {
        if (minMaxTypeEnum == MinMaxTypeEnum.MAX)
        {
            return refSet.maxValue();
        }
        else
        {
            return refSet.minValue();
        }
    }

    public Class getValueType()
    {
        return returnType;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeMinMaxAggregator(minMaxTypeEnum, returnType);
    }
}