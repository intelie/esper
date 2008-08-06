package com.espertech.esper.epl.metric;

public class MetricsExecutorUnthreaded implements MetricsExecutor
{
    public void execute(MetricExec execution, MetricExecutionContext executionContext)
    {
        execution.execute(executionContext);
    }

    public void destroy()
    {
        // no action required, noting to stop
    }
}
