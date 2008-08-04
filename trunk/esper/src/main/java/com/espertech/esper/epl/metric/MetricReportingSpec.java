package com.espertech.esper.epl.metric;

public class MetricReportingSpec
{
    private boolean isUseMetricsThreading;
    private long engineMetricsInterval;
    private long statementMetricsInterval;
    private long datawindowMetricsInterval;
    private String statementMetricsFilterRegex;

    public MetricReportingSpec(boolean useMetricsThreading, long engineMetricsInterval, long statementMetricsInterval, long datawindowMetricsInterval, String statementMetricsFilterRegex)
    {
        isUseMetricsThreading = useMetricsThreading;
        this.engineMetricsInterval = engineMetricsInterval;
        this.statementMetricsInterval = statementMetricsInterval;
        this.datawindowMetricsInterval = datawindowMetricsInterval;
        this.statementMetricsFilterRegex = statementMetricsFilterRegex;
    }

    public boolean isUseMetricsThreading()
    {
        return isUseMetricsThreading;
    }

    public long getEngineMetricsInterval()
    {
        return engineMetricsInterval;
    }

    public long getStatementMetricsInterval()
    {
        return statementMetricsInterval;
    }

    public long getDatawindowMetricsInterval()
    {
        return datawindowMetricsInterval;
    }

    public String getStatementMetricsFilterRegex()
    {
        return statementMetricsFilterRegex;
    }
}
