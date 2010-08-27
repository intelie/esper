package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.MethodResolutionService;

public class ExprPlugInAggFunctionNodeFactory implements AggregationMethodFactory
{
    private final AggregationSupport aggregationSupport;
    private final boolean distinct;

    public ExprPlugInAggFunctionNodeFactory(AggregationSupport aggregationSupport, boolean distinct)
    {
        this.aggregationSupport = aggregationSupport;
        this.distinct = distinct;
    }

    public Class getResultType()
    {
        return aggregationSupport.getValueType();
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        return null;  // defaults apply
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod method = aggregationSupport;
        if (!distinct) {
            return method;
        }
        return methodResolutionService.makeDistinctAggregator(method, aggregationSupport.getValueType());
    }

    public AggregationAccessor getAccessor()
    {
        return null;  // no accessor
    }
}
