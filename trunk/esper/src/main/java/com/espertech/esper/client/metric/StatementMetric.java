package com.espertech.esper.client.metric;

import java.util.concurrent.atomic.AtomicLong;

public class StatementMetric extends MetricEvent
{
    private String statementName;
    private AtomicLong cpuTime;
    private AtomicLong wallTime;
    private long numOutputRStream;
    private long numOutputIStream;

    public StatementMetric(String engineURI, String statementName)
    {
        super(engineURI);
        this.statementName = statementName;
        this.cpuTime = new AtomicLong();
        this.wallTime = new AtomicLong();
    }

    public String getStatementName()
    {
        return statementName;
    }

    public long getCpuTime()
    {
        return cpuTime.get();
    }

    public void addTotalCPU(long delta)
    {
        cpuTime.addAndGet(delta);
    }

    public void addTotalWall(long wall)
    {
        wallTime.addAndGet(wall);
    }

    public long getWallTime()
    {
        return wallTime.get();
    }

    public long getNumOutputRStream()
    {
        return numOutputRStream;
    }

    public long getNumOutputIStream()
    {
        return numOutputIStream;
    }

}
