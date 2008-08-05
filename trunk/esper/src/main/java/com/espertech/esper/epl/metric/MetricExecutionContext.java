package com.espertech.esper.epl.metric;

import com.espertech.esper.core.EPServicesContext;
import com.espertech.esper.client.EPRuntime;

public class MetricExecutionContext
{
    private final EPServicesContext epServicesContext;
    private final EPRuntime runtime;
    private final StatementMetricRepository statementMetricRepository;

    public MetricExecutionContext(EPServicesContext epServicesContext, EPRuntime runtime, StatementMetricRepository statementMetricRepository)
    {
        this.epServicesContext = epServicesContext;
        this.runtime = runtime;
        this.statementMetricRepository = statementMetricRepository;
    }

    public EPServicesContext getServices()
    {
        return epServicesContext;
    }

    public EPRuntime getRuntime()
    {
        return runtime;
    }

    public StatementMetricRepository getStatementMetricRepository()
    {
        return statementMetricRepository;
    }
}
