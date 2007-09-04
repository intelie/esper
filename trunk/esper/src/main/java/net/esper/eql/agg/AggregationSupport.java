package net.esper.eql.agg;

import net.esper.eql.core.MethodResolutionService;

/**
 * Base class for use with plug-in aggregation functions.
 */
public abstract class AggregationSupport implements AggregationMethod
{
    /**
     * Provides the aggregation function name.
     */
    protected String functionName;

    /**
     * Implemented by plug-in aggregation functions to allow such functions to validate the
     * type of value passed to the function at statement compile time.
     * @param childNodeType is the class of result of the expression sub-node within the aggregation function, or
     * null if a statement supplies no expression within the aggregation function 
     */
    public abstract void validate(Class childNodeType);

    /**
     * Ctor.
     */
    public AggregationSupport()
    {
    }

    /**
     * Sets the aggregation function name.
     * @param functionName is the name of the aggregation function
     */
    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    /**
     * Returns the name of the aggregation function.
     * @return aggregation function name
     */
    public String getFunctionName()
    {
        return functionName;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makePlugInAggregator(functionName);
    }
}
