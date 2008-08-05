package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.StatementMetric;

public class MetricExecStatement implements MetricExec
{
    private final MetricEventRouter metricEventRouter;
    private final MetricScheduleService metricScheduleService;
    private final long interval;
    private final int statementGroup;

    public MetricExecStatement(MetricEventRouter metricEventRouter, MetricScheduleService metricScheduleService, long interval, int statementGroup)
    {
        this.metricEventRouter = metricEventRouter;
        this.metricScheduleService = metricScheduleService;
        this.interval = interval;
        this.statementGroup = statementGroup;
    }

    public void execute(MetricExecutionContext context)
    {
        StatementMetric[] metrics = context.getStatementMetricRepository().reportGroup(statementGroup);
        if (metrics == null)
        {
            return;
        }
        for (int i = 0; i < metrics.length; i++)
        {
            if (metrics[i] != null)
            {
                metricEventRouter.route(metrics[i]);
            }
        }
        metricScheduleService.add(interval, this);
    }
}
