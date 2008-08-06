package com.espertech.esper.epl.metric;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.core.EPServicesContext;

public interface MetricReportingService
{
    public void setContext(EPRuntime runtime, EPServicesContext servicesContext);

    public void processTimeEvent(long currentTime);
    
    public void destroy();

    public void accountTime(StatementMetricHandle metricsHandle, long deltaCPU, long deltaWall);
    public void accountOutput(StatementMetricHandle handle, int numIStream, int numRStream);

    public StatementMetricHandle getStatementHandle(String statementId, String statementName);

    public void setMetricsReportingInterval(String stmtGroupName, long newInterval);
}
