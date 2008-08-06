package com.espertech.esper.epl.metric;

/**
 * Interface for producing a metric events.
 */
public interface MetricExec
{
    /**
     * Execute the production of metric events.
     * @param context provides services and scheduling
     */
    public void execute(MetricExecutionContext context);
}
