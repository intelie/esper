package com.espertech.esper.epl.metric;

/**
 * Metrics executor executing in-thread.
 */
public class MetricsExecutorUnthreaded implements MetricsExecutor
{
    public void execute(MetricExec execution, MetricExecutionContext executionContext)
    {
        execution.execute(executionContext);
    }

    public void destroy()
    {
        // no action required, nothing to stop
    }
}
