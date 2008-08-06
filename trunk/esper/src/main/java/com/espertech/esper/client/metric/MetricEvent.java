package com.espertech.esper.client.metric;

public abstract class MetricEvent
{
    private String engineURI;

    protected MetricEvent(String engineURI)
    {
        this.engineURI = engineURI;
    }

    public String getEngineURI()
    {
        return engineURI;
    }
}
