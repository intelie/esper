package com.espertech.esper.epl.metric;

public class SupportMetricExecution implements MetricExec
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
