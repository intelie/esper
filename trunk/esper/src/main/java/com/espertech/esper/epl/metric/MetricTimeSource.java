package com.espertech.esper.epl.metric;

/**
 * Interface for the time of the metrics generation.
 */
public interface MetricTimeSource
{
    /**
     * Returns current time for metrics reporting.
     * @return metrics current time
     */
    public long getCurrentTime();
}
