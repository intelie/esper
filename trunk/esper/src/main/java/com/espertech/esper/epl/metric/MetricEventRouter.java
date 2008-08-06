package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.MetricEvent;

/**
 * Interface for routing metric events for processing.
 */
public interface MetricEventRouter
{
    /**
     * Process metric event.
     * @param metricEvent metric event to process
     */
    public void route(MetricEvent metricEvent);
}
