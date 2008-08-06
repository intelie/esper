package com.espertech.esper.client.metric;

/**
 * Base metric event.
 */
public abstract class MetricEvent
{
    private String engineURI;

    /**
     * Ctor.
     * @param engineURI the engine URI
     */
    protected MetricEvent(String engineURI)
    {
        this.engineURI = engineURI;
    }

    /**
     * Returns the engine URI.
     * @return uri
     */
    public String getEngineURI()
    {
        return engineURI;
    }
}
