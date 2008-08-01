package com.espertech.esper.client.metric;

public class EngineMetric
{
    private final String engineURI;
    private final long engineTimestamp;
    private final long inputCountExternal;
    private final long inputCountStream;
    private final long scheduleDepth;
    private final long timerJitter;

    public EngineMetric(String engineURI, long engineTimestamp, long inputCountExternal, long inputCountStream, long scheduleDepth, long timerJitter)
    {
        this.engineURI = engineURI;
        this.engineTimestamp = engineTimestamp;
        this.inputCountExternal = inputCountExternal;
        this.inputCountStream = inputCountStream;
        this.scheduleDepth = scheduleDepth;
        this.timerJitter = timerJitter;
    }

    public String getEngineURI()
    {
        return engineURI;
    }

    public long getEngineTimestamp()
    {
        return engineTimestamp;
    }

    public long getInputCountExternal()
    {
        return inputCountExternal;
    }

    public long getInputCountStream()
    {
        return inputCountStream;
    }

    public long getScheduleDepth()
    {
        return scheduleDepth;
    }

    public long getTimerJitter()
    {
        return timerJitter;
    }
}
