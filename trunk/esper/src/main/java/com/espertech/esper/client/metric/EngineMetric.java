package com.espertech.esper.client.metric;

public class EngineMetric extends MetricEvent
{
    private final long timestamp;
    private final long inputCount;
    private final long scheduleDepth;

    public EngineMetric(String engineURI, long timestamp, long inputCount, long scheduleDepth)
    {
        super(engineURI);
        this.timestamp = timestamp;
        this.inputCount = inputCount;
        this.scheduleDepth = scheduleDepth;
    }

    public long getInputCount()
    {
        return inputCount;
    }

    public long getScheduleDepth()
    {
        return scheduleDepth;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
