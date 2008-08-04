package com.espertech.esper.epl.metric;

public class StatementMetric
{
    private long cpuTime;

    public StatementMetric(long cpuTime)
    {
        this.cpuTime = cpuTime;
    }

    public long getCpuTime()
    {
        return cpuTime;
    }

    public void setCpuTime(long cpuTime)
    {
        this.cpuTime = cpuTime;
    }
}
