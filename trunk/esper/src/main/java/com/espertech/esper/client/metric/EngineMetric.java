package com.espertech.esper.client.metric;

public class EngineMetric extends MetricEvent
{
    private final long inputCount;
    private final long scheduleDepth;

    public EngineMetric(String engineURI, long engineTimestamp, long inputCount, long scheduleDepth)
    {
        super(engineURI, engineTimestamp);
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
}
