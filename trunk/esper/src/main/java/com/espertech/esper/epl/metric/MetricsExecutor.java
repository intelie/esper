package com.espertech.esper.epl.metric;

/**
 * Executor for metrics executions.
 */
public interface MetricsExecutor
{
    /**
     * Execute a metrics execution.
     * @param execution to execute
     * @param executionContext context in which to execute
     */
    public void execute(MetricExec execution, MetricExecutionContext executionContext);

    /**
     * Shut down executor.
     */
    public void destroy();
}
