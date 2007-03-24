package net.esper.client;

/**
 * Configuration information for plugging in a custom aggregation function.
 */
public class ConfigurationPlugInAggregationFunction
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

    public String getFunctionClassName()
    {
        return functionClassName;
    }

    public void setFunctionClassName(String functionClassName)
    {
        this.functionClassName = functionClassName;
    }
}
