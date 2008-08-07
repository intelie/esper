/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.metric;

import com.espertech.esper.client.ConfigurationMetricsReporting;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.metric.MetricEvent;
import com.espertech.esper.core.EPServicesContext;
import com.espertech.esper.core.StatementLifecycleObserver;
import com.espertech.esper.core.StatementLifecycleEvent;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.MetricUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Metrics reporting.
 * <p>
 * Reports for all statements even if not in a statement group, i.e. statement in default group.
 */
public class MetricReportingServiceImpl implements MetricReportingService, MetricEventRouter, StatementLifecycleObserver
{
    private static final Log log = LogFactory.getLog(MetricReportingServiceImpl.class);

    private final ConfigurationMetricsReporting specification;
    private final String engineUri;

    private MetricExecutionContext executionContext;

    private boolean isScheduled;
    private final MetricScheduleService schedule;
    private final StatementMetricRepository stmtMetricRepository;
    private final Map<String, MetricExecStatement> statementGroupExecutions;
    private final MetricsExecutor metricsExecutor;

    /**
     * Ctor.
     * @param specification configuration
     * @param engineUri engine URI
     */
    public MetricReportingServiceImpl(ConfigurationMetricsReporting specification, String engineUri)
    {
        if (specification.isEnableMetricsReporting())
        {
            MetricUtil.initialize();
        }
        this.specification = specification;
        this.engineUri = engineUri;
        schedule = new MetricScheduleService();

        stmtMetricRepository = new StatementMetricRepository(engineUri, specification);
        statementGroupExecutions = new HashMap<String, MetricExecStatement>();

        if (specification.isThreading())
        {
            metricsExecutor = new MetricsExecutorThreaded(engineUri);
        }
        else
        {
            metricsExecutor = new MetricsExecutorUnthreaded();
        }
    }

    public void setContext(EPRuntime runtime, EPServicesContext servicesContext)
    {
        executionContext = new MetricExecutionContext(servicesContext, runtime, stmtMetricRepository);
    }

    public void processTimeEvent(long timeEventTime)
    {
        if (!MetricReportingPath.isMetricsEnabled)
        {
            return;
        }
        
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
            metricsExecutor.execute(execution, executionContext);
        }
    }

    public void destroy()
    {
        schedule.destroy();
        metricsExecutor.destroy();
    }

    public void route(MetricEvent metricEvent)
    {
        executionContext.getRuntime().sendEvent(metricEvent);
    }

    private void scheduleInitial()
    {
        if (!specification.isEnableMetricsReporting())
        {
            return;
        }

        if (isConsiderSchedule(specification.getEngineInterval()))
        {
            MetricExecEngine metrics = new MetricExecEngine(this, engineUri, schedule, specification.getEngineInterval());
            schedule.add(specification.getEngineInterval(), metrics);
        }

        // schedule each statement group, count the "default" group as the first group  
        int countGroups = 0;
        if (isConsiderSchedule(specification.getStatementInterval()))
        {
            MetricExecStatement metrics = new MetricExecStatement(this, schedule, specification.getStatementInterval(), countGroups);
            schedule.add(specification.getStatementInterval(), metrics);
        }
        countGroups++;

        // schedule each group, counting a group even if not active (0 or negative interval)
        // matches the statement repositpry understanding of group and order
        for (Map.Entry<String, ConfigurationMetricsReporting.StmtGroupMetrics> entry : specification.getStatementGroups().entrySet())
        {
            ConfigurationMetricsReporting.StmtGroupMetrics config = entry.getValue();
            MetricExecStatement metricsExecution = new MetricExecStatement(this, schedule, config.getInterval(), countGroups);
            this.statementGroupExecutions.put(entry.getKey(), metricsExecution);

            if (isConsiderSchedule(config.getInterval()))
            {
                schedule.add(config.getInterval(), metricsExecution);
            }
            countGroups++;
        }
    }

    public void accountTime(StatementMetricHandle metricsHandle, long deltaCPU, long deltaWall)
    {
        stmtMetricRepository.accountTimes(metricsHandle, deltaCPU, deltaWall);
    }

    public void accountOutput(StatementMetricHandle handle, int numIStream, int numRStream)
    {
        stmtMetricRepository.accountOutput(handle, numIStream, numRStream);
    }

    public StatementMetricHandle getStatementHandle(String statementId, String statementName)
    {
        return stmtMetricRepository.addStatement(statementName);
    }

    public void observe(StatementLifecycleEvent event)
    {
        if (!MetricReportingPath.isMetricsEnabled)
        {
            return;   
        }

        if (event.getEventType() == StatementLifecycleEvent.LifecycleEventType.STATECHANGE)
        {
            if (event.getStatement().isDestroyed())
            {
                stmtMetricRepository.removeStatement(event.getStatement().getName());
            }
        }
    }

    public void setMetricsReportingInterval(String stmtGroupName, long newInterval)
    {
        MetricExecStatement exec = this.statementGroupExecutions.get(stmtGroupName);
        if (exec == null)
        {
            throw new IllegalArgumentException("Statement group by name '" + stmtGroupName + "' could not be found");
        }
        exec.setInterval(newInterval);
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
