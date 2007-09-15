package net.esper.client;

import java.io.Serializable;

/**
 * Configuration information for plugging in a custom aggregation function.
 */
public class ConfigurationPlugInAggregationFunction implements Serializable
{
    private String name;
    private String functionClassName;

    /**
     * Ctor.
     */
    public ConfigurationPlugInAggregationFunction()
    {
    }

    /**
     * Returns the view name.
     * @return view name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the view name.
     * @param name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the aggregation function name.
     * @return name
     */
    public String getFunctionClassName()
    {
        return functionClassName;
    }

    /**
     * Sets the aggregation function's implementation class name.
     * @param functionClassName is the implementation class name
     */
    public void setFunctionClassName(String functionClassName)
    {
        this.functionClassName = functionClassName;
    }
}
