package com.espertech.esper.client;

import com.espertech.esper.type.*;

import java.io.Serializable;
import java.util.*;

public class ConfigurationMetricsReporting implements Serializable
{
    private boolean enableMetricsReporting;
    private boolean isThreading;
    private long engineInterval;
    private long statementInterval;

    private Map<String, StmtGroupMetrics> statementGroups;

    public ConfigurationMetricsReporting()
    {
        enableMetricsReporting = false;
        isThreading = true;
        engineInterval = 10 * 1000; // 10 seconds
        statementInterval = 10 * 1000;
        statementGroups = new LinkedHashMap<String, StmtGroupMetrics>();
    }

    public void addStmtGroup(String name, StmtGroupMetrics config)
    {
        statementGroups.put(name, config);
    }

    public boolean isEnableMetricsReporting()
    {
        return enableMetricsReporting;
    }

    public void setEnableMetricsReporting(boolean enableMetricsReporting)
    {
        this.enableMetricsReporting = enableMetricsReporting;
    }

    public boolean isThreading()
    {
        return isThreading;
    }

    public void setThreading(boolean threading)
    {
        isThreading = threading;
    }

    public long getEngineInterval()
    {
        return engineInterval;
    }

    public void setEngineInterval(long engineInterval)
    {
        this.engineInterval = engineInterval;
    }

    public long getStatementInterval()
    {
        return statementInterval;
    }

    public void setStatementInterval(long statementInterval)
    {
        this.statementInterval = statementInterval;
    }

    public Map<String, StmtGroupMetrics> getStatementGroups()
    {
        return statementGroups;
    }

    public void setStatementGroupInterval(String stmtGroupName, long newInterval)
    {
        StmtGroupMetrics metrics = statementGroups.get(stmtGroupName);
        if (metrics == null)
        {
            metrics.setInterval(newInterval);
        }
    }

    public static class StmtGroupMetrics implements Serializable
    {
        private List<StringPatternSet> patterns;
        private int numStatements;
        private long interval;
        private boolean reportInactive;
        private boolean defaultInclude;

        public StmtGroupMetrics()
        {
            patterns = new ArrayList<StringPatternSet>();
            interval =  10000;
            numStatements = 100;
        }

        public void addIncludeLike(String likeExpression)
        {
            patterns.add(new StringPatternSetIncludeLike(likeExpression));
        }

        public void addExcludeLike(String likeExpression)
        {
            patterns.add(new StringPatternSetExcludeLike(likeExpression));
        }

        public void addIncludeRegex(String regexExpression)
        {
            patterns.add(new StringPatternSetIncludeRegex(regexExpression));
        }

        public void addExcludeRegEx(String regexExpression)
        {
            patterns.add(new StringPatternSetExcludeRegex(regexExpression));
        }

        public long getInterval()
        {
            return interval;
        }

        public void setInterval(long interval)
        {
            this.interval = interval;
        }

        public List<StringPatternSet> getPatterns()
        {
            return patterns;
        }

        public int getNumStatements()
        {
            return numStatements;
        }

        public void setNumStatements(int numStatements)
        {
            this.numStatements = numStatements;
        }

        public boolean isReportInactive()
        {
            return reportInactive;
        }

        public void setReportInactive(boolean reportInactive)
        {
            this.reportInactive = reportInactive;
        }

        public boolean isDefaultInclude()
        {
            return defaultInclude;
        }

        public void setDefaultInclude(boolean defaultInclude)
        {
            this.defaultInclude = defaultInclude;
        }
    }
}
