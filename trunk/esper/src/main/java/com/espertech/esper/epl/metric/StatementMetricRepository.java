package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.client.ConfigurationMetricsReporting;
import com.espertech.esper.type.StringPatternSet;
import com.espertech.esper.type.StringPatternSetUtil;

import java.util.Map;
import java.util.List;

public class StatementMetricRepository
{
    private final ConfigurationMetricsReporting specification;
    private final StatementMetricArray[] groupMetrics;

    public StatementMetricRepository(String engineURI, ConfigurationMetricsReporting specification)
    {
        this.specification = specification;
        int numGroups = specification.getStatementGroups().size() + 1;  // +1 for default group (remaining stmts)
        this.groupMetrics = new StatementMetricArray[numGroups];

        // default group
        groupMetrics[0] = new StatementMetricArray(engineURI, "group-default", 100);

        // initialize all other groups
        int countGroups = 1;
        for (Map.Entry<String, ConfigurationMetricsReporting.StmtGroupMetrics> entry : specification.getStatementGroups().entrySet())
        {
            ConfigurationMetricsReporting.StmtGroupMetrics config = entry.getValue();

            int initialNumStmts = config.getInitialNumStmts();
            if (initialNumStmts < 10)
            {
                initialNumStmts = 10;
            }
            groupMetrics[countGroups] = new StatementMetricArray(engineURI, "group-" + countGroups, initialNumStmts);
            countGroups++;
        }
    }

    public StatementMetricHandle addStatement(String stmtName)
    {
        // determine group
        int countGroups = 1;
        int groupNumber = -1;
        for (Map.Entry<String, ConfigurationMetricsReporting.StmtGroupMetrics> entry : specification.getStatementGroups().entrySet())
        {
            List<StringPatternSet> patterns = entry.getValue().getPatterns();
            boolean result = StringPatternSetUtil.evaluate(false, patterns, stmtName);

            if (result)
            {
                groupNumber = countGroups;
                break;
            }
            countGroups++;
        }

        // assign to default group if none other apply
        if (groupNumber == -1)
        {
            groupNumber = 0;
        }

        int index = groupMetrics[groupNumber].addStatementGetIndex(stmtName);
        return new StatementMetricHandle(groupNumber, index);
    }

    public void removeStatement(StatementMetricHandle handle, String stmtId)
    {
        groupMetrics[handle.getGroupNum()].removeStatement(stmtId);
    }

    public void accountTimes(StatementMetricHandle handle, long cpu, long wall)
    {
        StatementMetricArray array = groupMetrics[handle.getGroupNum()];
        array.getRwLock().acquireReadLock();
        try
        {
            StatementMetric metric = array.getAddMetric(handle.getIndex());
            metric.addTotalCPU(cpu);
            metric.addTotalWall(wall);
        }
        finally
        {
            array.getRwLock().releaseReadLock();
        }
    }

    public StatementMetric[] reportGroup(int group)
    {
        return groupMetrics[group].flushMetrics();
    }
}
