package com.espertech.esper.epl.metric;

public class SupportMetricExecution implements MetricExecution
{
    private boolean executed;

    public boolean isExecuted()
    {
        return executed;
    }

    public void execute(MetricExecutionContext context)
    {
        executed = true;
    }
}
