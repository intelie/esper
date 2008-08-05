package com.espertech.esper.epl.metric;

import com.espertech.esper.client.ConfigurationMetricsReporting;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.metric.MetricEvent;
import com.espertech.esper.core.EPServicesContext;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetricReportingServiceImpl implements MetricReportingService, MetricEventRouter
{
    private static final Log log = LogFactory.getLog(MetricReportingServiceImpl.class);

    private final ConfigurationMetricsReporting specification;
    private final String engineUri;

    private MetricExecutionContext executionContext;

    private boolean isScheduled;
    private final MetricScheduleService schedule;
    private final StatementMetricRepository stmtMetricRepository;

    public MetricReportingServiceImpl(ConfigurationMetricsReporting specification, String engineUri)
    {
        this.specification = specification;
        this.engineUri = engineUri;
        schedule = new MetricScheduleService();

        stmtMetricRepository = new StatementMetricRepository(engineUri, specification);
    }

    public void setContext(EPRuntime runtime, EPServicesContext servicesContext)
    {
        executionContext = new MetricExecutionContext(servicesContext, runtime, stmtMetricRepository);
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
        List<MetricExec> executions = new ArrayList<MetricExec>(2);
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
        
        for (MetricExec execution : executions)
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
        executionContext.getRuntime().sendEvent(metricEvent);
    }

    private void scheduleInitial()
    {
        if (isConsiderSchedule(specification.getEngineMetricsInterval()))
        {
            MetricExecEngine metrics = new MetricExecEngine(this, engineUri, schedule, specification.getEngineMetricsInterval());
            schedule.add(specification.getEngineMetricsInterval(), metrics);
        }

        // schedule each statement group, count the "default" group as the first group  
        int countGroups = 0;
        if (isConsiderSchedule(specification.getDefaultStmtMetricsInterval()))
        {
            MetricExecStatement metrics = new MetricExecStatement(this, schedule, specification.getDefaultStmtMetricsInterval(), countGroups);
            schedule.add(specification.getDefaultStmtMetricsInterval(), metrics);
        }
        countGroups++;

        // schedule each group, counting a group even if not active (0 or negative interval)
        // matches the statement repositpry understanding of group and order
        for (Map.Entry<String, ConfigurationMetricsReporting.StmtGroupMetrics> entry : specification.getStatementGroups().entrySet())
        {
            ConfigurationMetricsReporting.StmtGroupMetrics config = entry.getValue();
            if (isConsiderSchedule(config.getInterval()))
            {
                MetricExecStatement metrics = new MetricExecStatement(this, schedule, config.getInterval(), countGroups);
                schedule.add(config.getInterval(), metrics);                
            }
            countGroups++;
        }

    }

    public void accountTime(StatementMetricHandle metricsHandle, long deltaCPU, long deltaWall)
    {
        stmtMetricRepository.accountTimes(metricsHandle, deltaCPU, deltaWall);
    }

    public StatementMetricHandle getStatementHandle(String statementId, String statementName)
    {
        return stmtMetricRepository.addStatement(statementName);
    }

    private boolean isConsiderSchedule(long value)
    {
        if ((value > 0) && (value < Long.MAX_VALUE))
        {
            return true;
        }
        return false;
    }
}
