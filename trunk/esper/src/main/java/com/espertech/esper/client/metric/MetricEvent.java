package com.espertech.esper.client.metric;

public abstract class MetricEvent
{
    private String engineURI;
    private long timestamp;

    protected MetricEvent(String engineURI, long timestamp)
    {
        this.engineURI = engineURI;
        this.timestamp = timestamp;
    }

    protected MetricEvent(String engineURI)
    {
        this.engineURI = engineURI;
    }

    public String getEngineURI()
    {
        return engineURI;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
