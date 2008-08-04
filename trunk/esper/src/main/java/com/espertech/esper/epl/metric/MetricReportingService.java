package com.espertech.esper.epl.metric;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.core.EPServicesContext;

public interface MetricReportingService
{
    public void setContext(EPRuntime runtime, EPServicesContext servicesContext);

    public void processTimeEvent(long currentTime);
    
    public void destroy();

    public void account(StatementMetricHandle metricsHandle);

    public StatementMetricHandle getStatementHandle(String statementId, String statementName);
}
