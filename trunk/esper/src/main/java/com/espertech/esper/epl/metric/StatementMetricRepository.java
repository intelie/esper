package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.client.ConfigurationMetricsReporting;
import com.espertech.esper.type.StringPatternSet;
import com.espertech.esper.type.StringPatternSetUtil;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class StatementMetricRepository
{
    private final ConfigurationMetricsReporting specification;
    private final StatementMetricArray[] groupMetrics;
    private final Map<String, Integer> statementGroups;

    public StatementMetricRepository(String engineURI, ConfigurationMetricsReporting specification)
    {
        this.specification = specification;
        int numGroups = specification.getStatementGroups().size() + 1;  // +1 for default group (remaining stmts)
        this.groupMetrics = new StatementMetricArray[numGroups];

        // default group
        groupMetrics[0] = new StatementMetricArray(engineURI, "group-default", 100, false);

        // initialize all other groups
        int countGroups = 1;
        for (Map.Entry<String, ConfigurationMetricsReporting.StmtGroupMetrics> entry : specification.getStatementGroups().entrySet())
        {
            ConfigurationMetricsReporting.StmtGroupMetrics config = entry.getValue();

            int initialNumStmts = config.getNumStatements();
            if (initialNumStmts < 10)
            {
                initialNumStmts = 10;
            }
            groupMetrics[countGroups] = new StatementMetricArray(engineURI, "group-" + countGroups, initialNumStmts, config.isReportInactive());
            countGroups++;
        }

        statementGroups = new HashMap<String, Integer>();
    }

    public StatementMetricHandle addStatement(String stmtName)
    {
        // determine group
        int countGroups = 1;
        int groupNumber = -1;
        for (Map.Entry<String, ConfigurationMetricsReporting.StmtGroupMetrics> entry : specification.getStatementGroups().entrySet())
        {
            List<StringPatternSet> patterns = entry.getValue().getPatterns();
            boolean result = StringPatternSetUtil.evaluate(entry.getValue().isDefaultInclude(), patterns, stmtName);

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

        statementGroups.put(stmtName, groupNumber);

        return new StatementMetricHandle(groupNumber, index);
    }

    public void removeStatement(String stmtName)
    {
        Integer group = statementGroups.remove(stmtName);
        if (group != null)
        {
            groupMetrics[group].removeStatement(stmtName);
        }
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

    public void accountOutput(StatementMetricHandle handle, int numIStream, int numRStream)
    {
        StatementMetricArray array = groupMetrics[handle.getGroupNum()];
        array.getRwLock().acquireReadLock();
        try
        {
            StatementMetric metric = array.getAddMetric(handle.getIndex());
            metric.addIStream(numIStream);
            metric.addRStream(numRStream);
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
