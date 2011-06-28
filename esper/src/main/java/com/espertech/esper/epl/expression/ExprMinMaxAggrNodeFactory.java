package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.type.MinMaxTypeEnum;

public class ExprMinMaxAggrNodeFactory implements AggregationMethodFactory
{
    private final MinMaxTypeEnum minMaxTypeEnum;
    private final Class type;
    private final boolean hasDataWindows;
    private final boolean distinct;
    private final boolean hasFilter;

    public ExprMinMaxAggrNodeFactory(MinMaxTypeEnum minMaxTypeEnum, Class type, boolean hasDataWindows, boolean distinct, boolean hasFilter)
    {
        this.minMaxTypeEnum = minMaxTypeEnum;
        this.type = type;
        this.hasDataWindows = hasDataWindows;
        this.distinct = distinct;
        this.hasFilter = hasFilter;
    }

    public AggregationAccessor getAccessor()
    {
        return null;
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod method = methodResolutionService.makeMinMaxAggregator(minMaxTypeEnum, type, hasDataWindows, hasFilter);
        if (!distinct) {
            return method;
        }
        return methodResolutionService.makeDistinctAggregator(method, type, hasFilter);
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        return null;  // defaults apply
    }

    public Class getResultType()
    {
        return type;
    }
}
