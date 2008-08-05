package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.EngineMetric;

public class MetricExecEngine implements MetricExec
{
    private final MetricEventRouter metricEventRouter;
    private final String engineURI;
    private final MetricScheduleService metricScheduleService;
    private final long interval;

    public MetricExecEngine(MetricEventRouter metricEventRouter, String engineURI, MetricScheduleService metricScheduleService, long interval)
    {
        this.metricEventRouter = metricEventRouter;
        this.engineURI = engineURI;
        this.metricScheduleService = metricScheduleService;
        this.interval = interval;
    }

    public void execute(MetricExecutionContext context)
    {
        long inputCount = context.getServices().getFilterService().getNumEventsEvaluated();
        long schedDepth = context.getServices().getSchedulingService().getScheduleHandleCount();
        EngineMetric metric = new EngineMetric(engineURI, metricScheduleService.getCurrentTime(), inputCount, schedDepth);
        metricEventRouter.route(metric);
        metricScheduleService.add(interval, this);        
    }
}