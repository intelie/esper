package net.esper.eql.agg;

import net.esper.eql.core.MethodResolutionService;

public abstract class AggregationSupport implements AggregationMethod
{
    protected final String functionName;

    public abstract void validate(Class childNodeType);

    protected AggregationSupport(String functionName)
    {
        this.functionName = functionName;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.getPlugInAggregator(functionName);
    }
}
