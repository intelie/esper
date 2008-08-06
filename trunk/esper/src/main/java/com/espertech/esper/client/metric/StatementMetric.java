package com.espertech.esper.client.metric;

import java.util.concurrent.atomic.AtomicLong;

public class StatementMetric extends MetricEvent
{
    private long timestamp;
    private String statementName;
    private AtomicLong cpuTime;
    private AtomicLong wallTime;
    private AtomicLong numOutputRStream;
    private AtomicLong numOutputIStream;

    public StatementMetric(String engineURI, String statementName)
    {
        super(engineURI);
        this.statementName = statementName;
        this.cpuTime = new AtomicLong();
        this.wallTime = new AtomicLong();
        this.numOutputIStream = new AtomicLong();
        this.numOutputRStream = new AtomicLong();
    }

    public String getStatementName()
    {
        return statementName;
    }

    public long getCpuTime()
    {
        return cpuTime.get();
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public long getTimestamp()
    {
        return timestamp;
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
        return numOutputRStream.get();
    }

    public long getNumOutputIStream()
    {
        return numOutputIStream.get();
    }

    public void addIStream(int numIStream)
    {
        numOutputIStream.addAndGet(numIStream);
    }

    public void addRStream(int numRStream)
    {
        numOutputRStream.addAndGet(numRStream);
    }
}
