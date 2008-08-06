package com.espertech.esper.epl.metric;

public interface MetricsExecutor
{
    public void execute(MetricExec execution, MetricExecutionContext executionContext);
    public void destroy();
}
