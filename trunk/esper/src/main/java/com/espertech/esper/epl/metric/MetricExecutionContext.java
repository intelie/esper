package com.espertech.esper.epl.metric;

import com.espertech.esper.core.EPServicesContext;
import com.espertech.esper.client.EPRuntime;

/**
 * Execution context for metrics reporting executions.
 */
public class MetricExecutionContext
{
    private final EPServicesContext epServicesContext;
    private final EPRuntime runtime;
    private final StatementMetricRepository statementMetricRepository;

    /**
     * Ctor.
     * @param epServicesContext services context
     * @param runtime for routing events
     * @param statementMetricRepository for getting statement data
     */
    public MetricExecutionContext(EPServicesContext epServicesContext, EPRuntime runtime, StatementMetricRepository statementMetricRepository)
    {
        this.epServicesContext = epServicesContext;
        this.runtime = runtime;
        this.statementMetricRepository = statementMetricRepository;
    }

    /**
     * Returns services.
     * @return services
     */
    public EPServicesContext getServices()
    {
        return epServicesContext;
    }

    /**
     * Returns runtime
     * @return runtime
     */
    public EPRuntime getRuntime()
    {
        return runtime;
    }

    /**
     * Returns statement metric holder
     * @return holder for metrics
     */
    public StatementMetricRepository getStatementMetricRepository()
    {
        return statementMetricRepository;
    }
}
