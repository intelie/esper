package com.espertech.esper.epl.metric;

import com.espertech.esper.client.ConfigurationEngineDefaults;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.metric.MetricEvent;
import com.espertech.esper.core.EPServicesContext;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class MetricReportingServiceImpl implements MetricReportingService, MetricEventRouter
{
    private static final Log log = LogFactory.getLog(MetricReportingServiceImpl.class);

    private final ConfigurationEngineDefaults.MetricsReporting specification;
    private final String engineUri;
    private final MetricScheduleService schedule;

    private EPRuntime runtime;
    private MetricExecutionContext executionContext;

    private boolean isScheduled;
    private ArrayList<StatementMetric> stmtMetrics;

    public MetricReportingServiceImpl(ConfigurationEngineDefaults.MetricsReporting specification, String engineUri)
    {
        this.specification = specification;
        this.engineUri = engineUri;
        schedule = new MetricScheduleService();
    }

    public void setContext(EPRuntime runtime, EPServicesContext servicesContext)
    {
        this.runtime = runtime;
        executionContext = new MetricExecutionContext(servicesContext);
    }

    public void processTimeEvent(long timeEventTime)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".processTimeEvent Setting time and evaluating schedules");
        }

        schedule.setTime(timeEventTime);
        if (!isScheduled)
        {
            scheduleInitial();
            isScheduled = true;
        }

        // fast evaluation against nearest scheduled time
        Long nearestTime = schedule.getNearestTime();
        if ((nearestTime == null) || (nearestTime > timeEventTime))
        {
            return;
        }

        // get executions
        List<MetricExecution> executions = new ArrayList<MetricExecution>(3);
        schedule.evaluate(executions);
        if (executions.isEmpty())
        {
            return;
        }

        // execute
        if (executionContext == null)
        {
            log.debug(".processTimeEvent No execution context");
            return;
        }
        
        for (MetricExecution execution : executions)
        {
            execution.execute(executionContext);
        }
    }

    public void destroy()
    {
        // no action yet, no thread started
    }

    public void route(MetricEvent metricEvent)
    {
        runtime.sendEvent(metricEvent);
    }

    private void scheduleInitial()
    {
        if (isConsiderSchedule(specification.getEngineMetricsInterval()))
        {
            EngineMetricExecution metrics = new EngineMetricExecution(this, engineUri, schedule, specification.getEngineMetricsInterval());
            schedule.add(specification.getEngineMetricsInterval(), metrics);
        }

        if (isConsiderSchedule(specification.getStatementMetricsInterval()))
        {
            schedule.add(specification.getStatementMetricsInterval(), null);
        }
    }

    private boolean isConsiderSchedule(long value)
    {
        if ((value > 0) && (value < Long.MAX_VALUE))
        {
            return true;
        }
        return false;
    }

    public void account(StatementMetricHandle metricsHandle)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public StatementMetricHandle getStatementHandle(String statementId, String statementName)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
