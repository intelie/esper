package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.MetricEvent;

public interface MetricEventRouter
{
    public void route(MetricEvent metricEvent);
}
